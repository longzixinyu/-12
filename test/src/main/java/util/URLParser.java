package util;

import org.apache.http.HttpHost;

/**
 * Description Of The Class<br/>
 *
 * @author Quinn Wang(王中奎)
 * @version 1.0.0, 2015/3/10 12:54
 * @since 2015/3/10 12:54
 */
public class URLParser {
    public static String getContent(String pageUrl) {
        if (null == pageUrl) {
            return null;
        }
        return HttpUtil.getUrlContent(HttpClientManager.getCloseableHttpClient(), null, new HttpHost("172.16.58.188", 9999, "http"), pageUrl, null, "UTF-8");
    }
}
