import org.apache.commons.lang3.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/28
 * Time: 12:09 PM
 * Desc: test for StringUtils class
 */
public class StirngUtilsTest {
    public static void main(String[] args) throws Exception {
        String jobId = "200220022002213213423";
        System.out.println(StringUtils.replaceOnce(jobId, "2002", "2102"));
    }
}
