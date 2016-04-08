package com.github.hackerwin7.jd.lib.executors;

import com.github.hackerwin7.jd.lib.utils.SiteConfService;
import com.github.hackerwin7.jd.lib.utils.TrainConfService;
import com.github.hackerwin7.jlib.utils.drivers.file.FileUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/21
 * Time: 2:49 PM
 * Desc: migrate config from kafka to hbase
 */
public class MigrateConf2HBaseConf {

    /* logger */
    private Logger logger = Logger.getLogger(MigrateConf2HBaseConf.class);

    /* constants */
    public static final String JOB_FILE = "job.list";
    public static final String SOURCE_HOST = "source_host";
    public static final String SOURCE_CHARSET = "source_charset";
    public static final String SOURCE_PASSWORD = "source_password";
    public static final String SOURCE_PORT = "source_port";
    public static final String SOURCE_SLAVEID = "source_slaveId";
    public static final String SOURCE_USER = "source_user";
    public static final String HBASE_TABLENAME = "hbase_tablename";
    public static final String TARGET_QUORUM = "target.quorum";
    public static final String TARGET_CLIENT_PORT = "target.clientport";
    public static final String TARGET_HBASE_ZKROOT = "target.hbase.zkroot";
    public static final String HBASE_FAMILY = "family";
    public static final String HBASE_ROWKEY_TYPE = "rowkey_type";
    public static final String HBASE_POOL_SIZE = "pool.size";
    public static final String HBASE_RETRY_NUM = "retry.num";
    public static final String RBDM_KEY = "rbdm:";
    public static final String TAB_META = "db_tab_meta";

    public static final String META_TABLENAME_KEY = "tablename";

    public static final String HTABLE_KEY = "htable";

    public static final String DEFAULT_CLIENT_PORT = "2181";
    //public static final String DEFAULT_ZKROOT = "/hbase_paris";
    public static final String DEFAULT_ZKROOT = "/hbase112";
    public static final String DEFAULT_FAMILY = "d";
    public static final String DEFAULT_ROW_TYPE = "long";
    public static final String DEFAULT_POOL_SIZE = "50";
    public static final String DEFAULT_RETRY_NUM = "2";

    /* data */
    private String htableName = "jdorders";
    private int jobIndex = 0;
    private String zkConn = "172.17.36.54,172.17.36.55,172.17.36.56";
    private String tableName = "test";
    //private String zkConn = "172.19.186.89,172.19.186.90,172.19.186.91,172.19.186.93,172.19.186.93";

    public static void main(String[] args) throws Exception {
        MigrateConf2HBaseConf mchc = new MigrateConf2HBaseConf();
        if(!StringUtils.isBlank(args[0]))
            mchc.htableName = args[0];
        if(!StringUtils.isBlank(args[1]))
            mchc.tableName = args[1];
        mchc.start();
    }

    /* driver */
    private SiteConfService site = new SiteConfService();

    /**
     * load from kafka conf
     * switches to hbase conf
     * write the hbase conf to site service
     * @throws Exception
     */
    public void start() throws Exception {
        /*load jobId*/
        List<String> jobs = FileUtils.file2List(JOB_FILE);//why??? load the another job.list, mvn jar the package, this current job.list file may be replaced by jlib's job.list, you can mvn inclide and exclude the resources file
        jobIndex = 0;
        for(String job : jobs) {
            JSONObject tConf = loadConf(job);
            JSONObject pConf = loadConf(getParserJobId(job));
            JSONObject conf = switchConf(tConf, pConf);
            writeConf(job + "_test", conf);
            jobIndex++;
        }
    }

    private String getParserJobId(String jobId) {
        int num = Integer.parseInt(jobId);
        return String.valueOf(num + 1);
    }

    /**
     * load kafka config from train bdp
     * @throws Exception
     */
    private JSONObject loadConf(String jobId) throws Exception {
        return TrainConfService.getConf(jobId);
    }

    /**
     * modify the conf to the hBase conf
     * @param trackerConf
     * @param parserConf
     * @return  json
     * @throws Exception
     */
    private JSONObject switchConf(JSONObject trackerConf, JSONObject parserConf) throws Exception {
        parserConf.put(SOURCE_HOST, trackerConf.getString(SOURCE_HOST));
        parserConf.put(SOURCE_CHARSET, trackerConf.getString(SOURCE_CHARSET));
        parserConf.put(SOURCE_PASSWORD, trackerConf.getString(SOURCE_PASSWORD));
        parserConf.put(SOURCE_PORT, trackerConf.getString(SOURCE_PORT));
        parserConf.put(SOURCE_SLAVEID, trackerConf.getString(SOURCE_SLAVEID));
        parserConf.put(SOURCE_USER, trackerConf.getString(SOURCE_USER));
        parserConf.put(HBASE_TABLENAME, RBDM_KEY + htableName + "_" + jobIndex);
        parserConf.put(TARGET_QUORUM, zkConn);
        parserConf.put(TARGET_CLIENT_PORT, DEFAULT_CLIENT_PORT);
        parserConf.put(TARGET_HBASE_ZKROOT, DEFAULT_ZKROOT);
        parserConf.put(HBASE_FAMILY, DEFAULT_FAMILY);
        parserConf.put(HBASE_ROWKEY_TYPE, DEFAULT_ROW_TYPE);
        parserConf.put(HBASE_POOL_SIZE, DEFAULT_POOL_SIZE);
        parserConf.put(HBASE_RETRY_NUM, DEFAULT_RETRY_NUM);

        JSONArray metaArr = parserConf.getJSONArray(TAB_META);
        for(int i = 0; i <= metaArr.size() - 1; i++) {
            JSONObject jmeta =metaArr.getJSONObject(i);
            String table = jmeta.getString(META_TABLENAME_KEY);
            String htable = "default";
            try {
                htable =  table.substring(0, table.indexOf("_"));
            } catch (Exception e) {
                htable = "no_default";
            }
            jmeta.put(HTABLE_KEY, htable);
        }
        return JSONObject.fromObject(parserConf);
    }

    /**
     *  write to config
     * @param conf
     * @throws Exception
     */
    private void writeConf(String jobId, JSONObject conf) throws Exception {
        System.out.println(site.write(jobId, conf.toString()));
    }
}
