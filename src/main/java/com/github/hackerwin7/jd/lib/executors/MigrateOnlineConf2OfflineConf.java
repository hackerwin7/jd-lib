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
public class MigrateOnlineConf2OfflineConf {

    /* logger */
    private Logger logger = Logger.getLogger(MigrateOnlineConf2OfflineConf.class);
    /* job file */
    public static final String JOB_FILE = "job.list";

    public static final String SOURCE_HOST = "source_host";
    public static final String SOURCE_CHARSET = "source_charset";
    public static final String SOURCE_PASSWORD = "source_password";
    public static final String SOURCE_PORT = "source_port";
    public static final String SOURCE_SLAVEID = "slaveId";
    public static final String SOURCE_USER = "source_user";
    public static final String JOB_ID = "job_id";
    public static final String KAFKA_ZKSERVER = "kafka_zkserver";
    public static final String TRACKER_JDQ_ZKSERVKER = "tracker_jdq_zkserver";

    public static final String JOB_ID_REAR = "-test.kafka";
    public static final String KAFKA_ZKSERVER_TEST = "172.17.36.54:2181,172.17.36.55:2181,172.17.36.56:2181";

    /* data */
    private String newJob = "";
    private String newPJob = "";

    public static void main(String[] args) throws Exception {
        MigrateOnlineConf2OfflineConf mchc = new MigrateOnlineConf2OfflineConf();
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
        for(String job : jobs) {
            String pJob = getParserJobId(job);
            newJob = job + JOB_ID_REAR;
            newPJob = pJob + JOB_ID_REAR;
            JSONObject tConf = loadConf(job);
            JSONObject pConf = loadConf(pJob);
            switchConf(tConf, pConf);
            writeConf(newJob, tConf);
            writeConf(newPJob, pConf);
        }
    }

    private String getParserJobId(String jobId) throws Exception {
        return StringUtils.replaceOnce(jobId, "2002", "2102");
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
     * @throws Exception
     */
    private void switchConf(JSONObject trackerConf, JSONObject parserConf) throws Exception {
        //tracker conf modify
        trackerConf.put(SOURCE_SLAVEID, trackerConf.get(SOURCE_SLAVEID) + "1742");
        trackerConf.put(JOB_ID, newJob);
        trackerConf.put(KAFKA_ZKSERVER, KAFKA_ZKSERVER_TEST);

        //parser conf modify
        parserConf.put(TRACKER_JDQ_ZKSERVKER, KAFKA_ZKSERVER_TEST);
        parserConf.put(JOB_ID, newPJob);
        parserConf.put(KAFKA_ZKSERVER, KAFKA_ZKSERVER_TEST);
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
