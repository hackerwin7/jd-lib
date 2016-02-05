package com.github.hackerwin7.jd.lib.utils.magpie;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/02/02
 * Time: 5:45 PM
 * Desc: magpie util sets
 */
public class MagpieUtils {

    /**
     * get the mysql tracker job id
     * @return a list of job id
     * @throws Exception
     */
    public static List<String> getMagpieMysqlTrackerJob() throws Exception {
        return MagpieJobScanner.scanMagpieTrackerJob();
    }

    /**
     * get the mysql tracker config
     * @return a map of jobId and corresponding config
     * @throws Exception
     */
    public static Map<String, JSONObject> getMagpieMysqlTrackerConf() throws Exception {
        return MagpieConfScanner.scanMagpieTrackerConf();
    }


}
