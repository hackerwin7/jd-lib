package com.github.hackerwin7.jd.lib.executors;

import com.github.hackerwin7.jd.lib.utils.magpie.MagpieUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/03/15
 * Time: 5:46 PM
 * Desc:
 * Tips:
 */
public class ScanWholeConf {
    public static void main(String[] args) throws Exception {
        ScanWholeConf swc = new ScanWholeConf();
        swc.run();
    }

    private void run() throws Exception {
//        List<String> jobs = MagpieUtils.getMagpieMysqlTrackerJob();
//        Collections.sort(jobs);
//        for(String job : jobs) {
//            System.out.println(job);
//        }

        Map<String, JSONObject> jcm = MagpieUtils.getMagpieMysqlTrackerConf();
        for(Map.Entry<String, JSONObject> entry : jcm.entrySet()) {
            String jobid = entry.getKey();
            JSONObject js = entry.getValue();
            System.out.println(jobid + " ============= " + js);
        }
    }
}