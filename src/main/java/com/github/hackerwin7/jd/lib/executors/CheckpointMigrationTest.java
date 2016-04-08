package com.github.hackerwin7.jd.lib.executors;

import com.jd.bdp.monitors.commons.util.CheckpointUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/03/28
 * Time: 2:52 PM
 * Desc:
 * Tips: only for 2002/2102 job
 */
public class CheckpointMigrationTest {
    public static void main(String[] args) throws Exception {
        //read old cp
        String tid = args[0];
        String pid = StringUtils.replaceOnce(tid, "2002", "2102");
        CheckpointUtil cp = new CheckpointUtil();
        String tcp = cp.readCheckpoint(tid, CheckpointUtil.CURRENT_CHECKPOINT);
        String pcp = cp.readCheckpoint(pid, CheckpointUtil.CURRENT_CHECKPOINT);

        //split old cp
        String[] tcpArr = StringUtils.split(tcp, ":");
        String[] pcpArr = StringUtils.split(pcp, ":");
        String logFile = tcpArr[0];
        String logOffset = tcpArr[1];
        long lastMid =  Long.parseLong(pcpArr[5]);

        //new cp
        String ntcp = logFile + ":" + logOffset + ":0:0:0:0:" + (lastMid + 1);
        String npcp = "init";
        System.out.println(ntcp);
        System.out.println(npcp);
    }
}
