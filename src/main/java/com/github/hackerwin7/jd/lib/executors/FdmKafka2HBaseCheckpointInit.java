package com.github.hackerwin7.jd.lib.executors;

import com.github.hackerwin7.jlib.utils.drivers.file.FileUtils;
import com.github.hackerwin7.jlib.utils.drivers.zk.ZkClient;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/02/26
 * Time: 6:07 PM
 * Desc: init the tp-1000006.jrdw.jd.com ~ tp-1000074.jrdw.jd.com
 * Tips:
 */
public class FdmKafka2HBaseCheckpointInit {

    public static final String ZK_CONN = "172.19.176.19:2181,172.19.176.20:2181,172.19.176.21:2181,172.19.176.22:2181,172.19.176.23:2181";

    public static final String ZK_ROOT = "/checkpoint/";

    public static final String INIT = "init";

    public static final String TP = "/TP";

    public static void main(String[] args) throws Exception {
        List<String> jobIds = FileUtils.file2List("job.list");
        ZkClient zk = new ZkClient(ZK_CONN);
        for(String jobId : jobIds) {
            if(!zk.exists(ZK_ROOT + jobId))
                zk.create(ZK_ROOT + jobId, INIT);
            if(!zk.exists(ZK_ROOT + jobId + TP))
                zk.create(ZK_ROOT + jobId + TP, INIT);
            else
                zk.set(ZK_ROOT + jobId + TP, INIT);
        }
        zk.close();
    }

}
