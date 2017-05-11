import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: hackerwin7
 * Date: 2017/05/10
 * Time: 2:24 PM
 * Desc:
 * Tips:
 */
public class JDQ2AppToken {

    private static final Logger LOG = Logger.getLogger(JDQ2AppToken.class);

    public static void main(String[] args) {
        produceApply();
    }

    public static void produceApply() {
        HttpClient httpClient = new HttpClient();
        String appid = "jdq.bdp.jd.com";
        String token = "ALZXCRDVR7AYACW3I76VET3VVU";
        String url = "http://192.168.144.98:8900/api/jdqcenter/auth/cert/produce"; // "http://192.168.144.98:8900/api/jdqcenter/auth/generate/produce"
        String data = "{appId:'ylg.bdp.jd.com',token:'UxSYbgwP7zuMDPlZjMAoOQ=='}";
        LOG.info(url);

        NameValuePair[] ndata = new NameValuePair[4];
        ndata[0] = new NameValuePair("appId", appid);
        ndata[1] = new NameValuePair("token", token);
        ndata[2] = new NameValuePair("time", String.valueOf(System.currentTimeMillis()));
        ndata[3] = new NameValuePair("data", data);

        StringBuffer contentBuffer = new StringBuffer();
        try {
            PostMethod postMethod = new PostMethod(url);
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            postMethod.setRequestBody(ndata);
            int statusCode = httpClient.executeMethod(postMethod);
            if(statusCode == HttpStatus.SC_OK) {
                contentBuffer.append(postMethod.getResponseBodyAsString());
                LOG.info(contentBuffer.toString());
            } else {
                LOG.error("error = " + statusCode);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }


    }
}
