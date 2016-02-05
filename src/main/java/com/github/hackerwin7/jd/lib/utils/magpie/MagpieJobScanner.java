package com.github.hackerwin7.jd.lib.utils.magpie;

import com.github.hackerwin7.jlib.utils.drivers.shell.ShellClient;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/02/02
 * Time: 5:01 PM
 * Desc: scan the magpie cluster and get the job description
 */
public class MagpieJobScanner {
    public static List<String> tJobs = new LinkedList<>();
    public static List<String> pJobs = new LinkedList<>();

    public static List<String> scanMagpieTrackerJob() throws Exception {
        tJobs.clear();
        String hostName = "magpie";
        String magpie1 = "172.22.178.85";
        String magpie2 = "172.19.154.31";

        String cmd = "magpie-client info -list assignments | grep mysql-tracker";
        String cmd1 = "ssh -l " + hostName + " " + magpie1 + " \"" + cmd + "\"";
        String cmd2 = "ssh -l " + hostName + " " + magpie2 + " \"" + cmd + "\"";
        List<String> rets1 = ShellClient.execute(cmd1);
        List<String> rets2 = ShellClient.execute(cmd2);

        List<String> jobIds = new LinkedList<>();
        for(String ret : rets1) {
            String jobId = getJobId(ret);
            if(!StringUtils.containsIgnoreCase(jobId, "tp-"))
                jobIds.add(jobId);
        }
        for(String ret : rets2) {
            String jobId = getJobId(ret);
            if(!StringUtils.containsIgnoreCase(jobId, "tp-"))
                jobIds.add(jobId);
        }
        tJobs.addAll(jobIds);
        return jobIds;
    }

    /**
     * potential bug : if there is only tracker but no corresponding parser, so this method will return not correct parser job id list
     * rule :
     *  if exist first 2002 then 2002 -< 2102
     *  else jobId number plus one
     * @return a list of parser job id
     * @throws Exception
     */
    public static List<String> scanMagpieParserJob() throws Exception {
        if(tJobs.isEmpty())
            scanMagpieTrackerJob();
        if(pJobs.isEmpty()) {
            for(String tjob : tJobs) {
                String pjob = getParserJobId(tjob);
                pJobs.add(pjob);
            }
            return pJobs;
        } else {
            return pJobs;
        }
    }

    /**
     * get parser jobId by tracker jobId
     * @param tid
     * @return parser job id
     */
    private static String getParserJobId(String tid) {
        String pre4Chars = StringUtils.substring(tid, 0 , 4);
        if(StringUtils.equals(pre4Chars, "2002")) {
            return StringUtils.replaceOnce(tid, "2002", "2102");
        } else {
            int tidNum = Integer.parseInt(tid) + 1;
            return String.valueOf(tidNum);
        }
    }

    /**
     * test for command execute
     * @param args
     * @throws Exception
     */
    public static void main1(String[] args) throws Exception {
        String hostName = "magpie";
        String magpie1 = "172.22.178.85";
        String magpie2 = "172.19.154.31";
        String cmd = "magpie-client info -list assignments | grep mysql-tracker | awk -F ' ' '{print $5}'";
        String cmd1 = "ssh -l " + hostName + " " + magpie1 + " \"" + cmd + "\"";
        String cmd2 = "ssh -l " + hostName + " " + magpie2 + " \"" + cmd + "\"";
        List<String> rets1 = ShellClient.execute(cmd1);
        System.out.println("magpie1:");
        for(String ret : rets1) {
            String jobId = getJobId(ret);
            System.out.println(jobId);
        }
        List<String> rets2 = ShellClient.execute(cmd2);
        System.out.println("magpie2:");
        for(String ret : rets2) {
            String jobId = getJobId(ret);
            System.out.println(jobId);
        }
    }

    /**
     * test for jobId array
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        List<String> jobs = scanMagpieTrackerJob();
        for(String job : jobs) {
            System.out.println(job);
        }
    }

    /**
     * get jobId from the string
     * @param str
     * @return jobid
     */
    private static String getJobId(String str) {
        return StringUtils.substringBetween(str, "[INFO] ", ": ");
    }
}
