import com.github.hackerwin7.jlib.utils.drivers.file.FileUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2016/01/22
 * Time: 10:19 AM
 * Desc: test file utils for jlib
 */
public class FileUtilTest {
    public static void main(String[] args) throws Exception {
        List<String> rows = FileUtils.file2List("job.list");
        System.out.println(rows);
    }
}
