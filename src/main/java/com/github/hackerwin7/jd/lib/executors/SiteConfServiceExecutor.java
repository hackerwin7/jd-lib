package com.github.hackerwin7.jd.lib.executors;

import com.github.hackerwin7.jd.lib.utils.SiteConfService;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/21
 * Time: 10:16 AM
 * Desc: site config service
 */
public class SiteConfServiceExecutor {

    /* constants */
    public static final String ENCODE_DEFAULT = "UTF-8";
    public static final String APPID_DEFAULT = "bdp.jd.com";
    public static final String TOKEN_DEFAULT = "RQLMPXULF3EG23CPZL3U257B7Y";
    public static final String SITE_URL_WRITE = "http://atom.bdp.jd.local/api/site/save";
    public static final String SITE_URL_READ = "http://atom.bdp.jd.local/api/site/getOrigin";
    public static final String APPID_PARAMETER = "appid";
    public static final String TOKEN_PARAMETER = "token";
    public static final String TIME_PARAMETER = "time";
    public static final String DATA_PARAMETER = "data";

    /**
     *
     * @param args, a string : 'jobId' 'value' 'op'
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String jobId = args[0];
        String value = args[1];
        String op = args[2];
        SiteConfService scs = new SiteConfService();
        switch (op) {
            case "read":
                System.out.println(scs.read(jobId));
                break;
            case "write":
                System.out.println(scs.write(jobId, value));
                break;
        }
    }
}
