package com.github.hackerwin7.jd.lib.executors;

import com.jd.bdp.monitors.commons.util.CheckpointUtil;

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
                System.out.println("read checkpoint : jobId = " + jobId + ", cp = " + value);
                break;
            case "write":
                write(jobId, this.value);
                System.out.println("write checkpoint : jobId = " + jobId + ", cp = " + this.value);
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
    }
}
