package com.github.hackerwin7.jd.lib.executors;

import com.github.hackerwin7.jd.lib.utils.TrainConfService;
import com.github.hackerwin7.jd.lib.utils.magpie.MagpieUtils;
import com.github.hackerwin7.jlib.utils.drivers.file.FileUtils;
import com.github.hackerwin7.jlib.utils.drivers.shell.ShellGetMysqlOffset;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/03/08
 * Time: 2:07 PM
 * Desc: init the jobId checkpoint (tracker and parser)
 * Tips:
 */
public class InitMagpieJobCheckpoint {

    private Logger logger = Logger.getLogger(InitMagpieJobCheckpoint.class);

    private List<String> tids = new LinkedList<>();

    private Map<String, String> ttopics = new HashMap<>();
    private Map<String, String> tips = new HashMap<>();

    public static void main(String[] args) throws Exception {
        InitMagpieJobCheckpoint imjc = new InitMagpieJobCheckpoint();
        imjc.mainProc();
    }

    private void mainProc() throws Exception {
        tids = FileUtils.file2List("job.list");
        CheckpointUtilNginx cp = new CheckpointUtilNginx();
        scanKeyVal("tracker_log_topic", "source_host");
        for(String tid : tids) {
            String pid = MagpieUtils.tracker2ParserJobid(tid);
            String ip = tips.get(tid);
            String topic = ttopics.get(tid);
            String myOffset = ShellGetMysqlOffset.showMasterStatus(ip).toString();
            String tcp = myOffset + ":" + "0:0";
            String pcp = topic + ":" + "0:0:0:0:0";
            cp.write(tid, tcp);
            cp.write(pid, pcp);
            System.out.println(tid + " " + ip + " " + topic + " " + tcp + " " + pcp);
        }
    }

    private void scanKeyVal(String key, String key1) throws Exception {
        for(String tid : tids) {
            JSONObject conf = TrainConfService.getConf(tid);
            String val = conf.getString(key);
            String val1 = conf.getString(key1);
            ttopics.put(tid, val);
            tips.put(tid, val1);
            System.out.println("get conf = " + tid + " " + val + " " + val1);
        }
        System.out.println("count = " + ttopics.size() + "topic map  = " + ttopics);
        System.out.println("count = " + tips.size() + "ip map = " + tips);
    }
}
