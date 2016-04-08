package com.github.hackerwin7.jd.lib.executors;

import com.github.hackerwin7.jlib.utils.drivers.file.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/03/01
 * Time: 10:26 PM
 * Desc: jobid mapping topic
 * Tips:
 */
public class JobIdTopicMapper {

    public static final int JOBID_INDEX = 4;
    public static final int TRACKER_TOPIC_INDEX = 10;
    public static final int PARSER_TOPIC_INDEX = 12;

    private static Map<String, String> jtm = new HashMap<>();
    private static Map<String, String> jpStrM = new HashMap<>();

    public static void main(String args[]) throws Exception {
        List<String> lines = FileUtils.file2List("desc.list");
        for(String line : lines) {
            String[] lineArr = StringUtils.split(line, " ");
            String jobId = lineArr[JOBID_INDEX];
            String trackerTopic = lineArr[TRACKER_TOPIC_INDEX];
            String parserTopicStr = lineArr[PARSER_TOPIC_INDEX];
            jtm.put(jobId, trackerTopic);
            jpStrM.put(jobId, parserTopicStr);
        }
        showJobParser();
    }

    public static void showJobParser() {
        for(Map.Entry<String, String> entry : jpStrM.entrySet()) {
            String jobId = entry.getKey();
            String parserStr = entry.getValue();
            String[] psArr = StringUtils.split(parserStr, ",");
            for(String topic : psArr) {
                System.out.printf("%-20s", jobId);
                System.out.printf("%-10s", topic);
                System.out.println();
            }
        }
    }
}
