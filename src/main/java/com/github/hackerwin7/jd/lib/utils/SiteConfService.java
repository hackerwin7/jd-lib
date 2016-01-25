package com.github.hackerwin7.jd.lib.utils;

import com.github.hackerwin7.jlib.utils.drivers.url.HttpClient;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/21
 * Time: 10:35 AM
 * Desc:
 */
public class SiteConfService {

    /* logger */
    private Logger logger = Logger.getLogger(SiteConfService.class);

    /* constants */
    public static final String ENCODE_DEFAULT = "UTF-8";
    public static final String APPID_DEFAULT = "bdp.jd.com";
    public static final String TOKEN_DEFAULT = "RQLMPXULF3EG23CPZL3U257B7Y";
    public static final String SITE_URL_WRITE = "http://atom.bdp.jd.local/api/site/save";
    public static final String SITE_URL_READ = "http://atom.bdp.jd.local/api/site/get";
    public static final String APPID_PARAMETER = "appId";
    public static final String TOKEN_PARAMETER = "token";
    public static final String TIME_PARAMETER = "time";
    public static final String DATA_PARAMETER = "data";

    /* driver */
    private HttpClient client = new HttpClient();

    /* data */
    private Map<String, String> paras = new HashMap<>();

    public SiteConfService() {
        paras.put(APPID_PARAMETER, APPID_DEFAULT);
        paras.put(TOKEN_PARAMETER, TOKEN_DEFAULT);
    }

    /**
     * write the k/v to site service
     * @param key
     * @param value
     * @throws Exception
     */
    public String write(String key, String value) throws Exception {
        value = StringEscapeUtils.escapeJson(value);
        paras.put(TIME_PARAMETER, String.valueOf(System.currentTimeMillis()));
        /* build value format */
        String data = "{" +
                "\"model\":\"rpc\"," +
                "\"key\":\""+ key +"\"," +
                "\"synchronous\":true," +
                "\"value\":\""+ value +"\"," +
                "\"erp\":\"fff\"" +
                "}";
        paras.put(DATA_PARAMETER, data);
        return client.post(SITE_URL_WRITE, paras);
    }

    /**
     * get the value according to key
     * @param key
     * @throws Exception
     */
    public String read(String key) throws Exception {
        paras.put(TIME_PARAMETER, String.valueOf(System.currentTimeMillis()));
        /* build value format */
        String data = "{" +
                "\"model\":\"rpc\"," +
                "\"key\":\""+ key +"\"," +
                "\"erp\":\"fff\"" +
                "}";
        paras.put(DATA_PARAMETER, data);
        String ret = client.post(SITE_URL_READ, paras);
        logger.debug(ret);
        String value = JSONObject.fromObject(ret).getString("obj");
        value = StringEscapeUtils.unescapeJson(value);
        return value;
    }
}
