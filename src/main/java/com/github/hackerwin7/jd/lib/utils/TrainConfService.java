package com.github.hackerwin7.jd.lib.utils;

import com.github.hackerwin7.jlib.utils.drivers.url.URLClient;
import net.sf.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/21
 * Time: 3:05 PM
 * Desc: query train bdp service
 */
public class TrainConfService {

    /* constants */
    public static final String BDP_URL = "http://train.bdp.jd.com/api/ztc/job/getJobConfig.ajax?jobId=";
    public static final String DATA_KEY = "data";

    /**
     * get config json
     * @param jobId
     * @return json
     * @throws Exception
     */
    public static JSONObject getConf(String jobId) throws Exception {
        return JSONObject.fromObject(URLClient.getFromUrl(BDP_URL + jobId)).getJSONObject(DATA_KEY);
    }

}