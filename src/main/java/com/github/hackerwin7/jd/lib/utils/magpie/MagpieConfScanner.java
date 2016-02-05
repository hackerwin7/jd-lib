package com.github.hackerwin7.jd.lib.utils.magpie;

import com.github.hackerwin7.jd.lib.utils.TrainConfService;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/02/02
 * Time: 5:40 PM
 * Desc: scan the bdp config for magpie job
 */
public class MagpieConfScanner {
    public static Map<String, JSONObject> scanMagpieTrackerConf() throws Exception {
        Map<String, JSONObject> confs = new HashMap<>();
        List<String> jobIds = MagpieJobScanner.scanMagpieTrackerJob();
        for(String jobId : jobIds) {
            JSONObject conf = TrainConfService.getConf(jobId);
            confs.put(jobId, conf);
        }
        return confs;
    }

    public static Map<String, JSONObject> scanMagpieParserConf() throws Exception {
        Map<String, JSONObject> confs = new HashMap<>();
        List<String> jobIds = MagpieJobScanner.scanMagpieParserJob();
        for(String jobId : jobIds) {
            JSONObject conf = TrainConfService.getConf(jobId);
            confs.put(jobId, conf);
        }
        return confs;
    }
}
