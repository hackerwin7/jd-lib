//package com.github.hackerwin7.jd.lib.executors;
//
//import com.github.hackerwin7.jd.lib.utils.magpie.MagpieConfScanner;
//import com.github.hackerwin7.jlib.utils.drivers.file.FileUtils;
//import net.sf.json.JSONObject;
//import org.apache.log4j.Logger;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by IntelliJ IDEA.
// * User: hackerwin7
// * Date: 2016/02/01
// * Time: 6:07 PM
// * Desc: switch ip for mysql job
// * old ip :
// *  1.get all jobId list (running job) and jobId-ip map and ip-jobId map
// *  2.build switch jobId list and switch jodId-oldIp map and switch jobId-newIp map
// *  3.loop switch jobId:
// *      1.find new Ip master status (then you can sleep 1min)
// *      2.stop job (sync stop) (tracker job)
// *      3.build new checkpoint and set it
// *      4.verify the config service switched new ip
// *      5.start the job
// * new ip :
// *  1.get all fdm jobId list and jobId-ip map and ip-jobId map
// *  2.build switch jobId list and switch jobId-oldIp map and switch jobId-newIp map
// *  3.loop switch jobId:
// *      1.find new Ip master status (then you can sleep 1min)
// *      2.stop tracker job and sleep 1min
// *      3.stop parser job (before stop parser, you must insure that the parser have transfer all data from tracker(stopped))
// *      4.build new checkpoint for tracker (such as serverId timestamp rowId etc..) and set it
// *      5.build new checkpoint for parser (mysql-bin.000000:xxx:xxx:xxx:uniqueId(must not changed)_MAP_......) and set it, topic checkpoint set "init"
// *      6.verify the config service switched new ip
// *      7.start tracker job and parser job
// */
//public class SwitchMysqlSource {
//
//    /* logger */
//    private Logger logger = Logger.getLogger(SwitchMysqlSource.class);
//
//    /* data */
//    private Map<String, String> jobIps = new HashMap<>();
//    private Map<String, String> ipJobs = new HashMap<>();
//    private Map<String, String> switchedJobOldIps = new HashMap<>();
//    private Map<String, String> switchedJobNewIps = new HashMap<>();
//    private List<String> switchedJobs = new LinkedList<>();
//
//    /**
//     * main process for running
//     * @throws Exception
//     */
//    public void run() throws Exception {
//        getInfo();
//        buildSwitchedInfo();
//        for(String jobId : switchedJobs) {
//            switchJob(jobId);
//        }
//    }
//
//    /**
//     * scan the info from magpie
//     * @throws Exception
//     */
//    private void getInfo() throws Exception {
//        // load config from config service
//        Map<String, JSONObject> confs = MagpieConfScanner.scanMagpieTrackerConf();
//        for(Map.Entry<String, JSONObject> entry : confs.entrySet()) {
//            String jobId = entry.getKey();
//            String ip = entry.getValue().getString("source_host");
//            jobIps.put(jobId, ip);
//            ipJobs.put(ip, jobId);
//        }
//    }
//
//    /**
//     * build the switch data collector
//     * @throws Exception
//     */
//    private void buildSwitchedInfo() throws Exception {
//        List<String> switchedOld = FileUtils.file2List("switchOld.list");
//        List<String> switchedNew = FileUtils.file2List("switchNew.list");
//        for(int i = 0; i <= switchedOld.size() - 1; i++) {
//
//        }
//    }
//}
