package com.github.hackerwin7.jd.lib.executors;

import com.jd.bdp.monitors.commons.util.CheckpointUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/18
 * Time: 3:32 PM
 * Desc: checkpoint utils for nginx
 */
    public class CheckpointUtilNginx {

    /* data */
    private String jobId = null;
    private String value = null;
    private String op = null;

    /* driver */
    private CheckpointUtil cp = new CheckpointUtil();

    /**
     * constructor
     * @param jobId
     * @param value
     * @param op
     */
    public CheckpointUtilNginx(String jobId, String value, String op) {
        this.jobId = jobId;
        this.value = value;
        this.op = op;
    }

    /**
     * instance main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CheckpointUtilNginx cun = new CheckpointUtilNginx(args[0], args[1], args[2]);
        cun.run();
    }

    /**
     * main process running
     */
    public void run() throws Exception {
        switch (op) {
            case "read":
                String value = read(jobId);
                System.out.println("============== OK!!! read checkpoint : jobId = " + jobId + ", cp = " + value);
                break;
            case "write":
                write(jobId, this.value);
                System.out.println("============== OK!!! write checkpoint : jobId = " + jobId + ", cp = " + this.value);
                break;
        }
    }

    /**
     * read jobId and get checkpoint
     * @param jobId
     * @return cp value
     * @throws Exception
     */
    private String read(String jobId) throws Exception {
        return cp.readCheckpoint(jobId, CheckpointUtil.CURRENT_CHECKPOINT);
    }

    /**
     * write jobId and
     * @param jobId
     * @param value
     * @throws Exception
     */
    private void write(String jobId, String value) throws Exception {
        cp.writeCp(jobId, value);
        Thread.sleep(5000);
        int cnt = 0;
        while (!StringUtils.equals(value, read(jobId))) {
            Thread.sleep(3000);
            cnt++;
            if(cnt >= 2) {
                cp.writeCp(jobId, value);
                cnt = 0;
            }
        }
    }
}
