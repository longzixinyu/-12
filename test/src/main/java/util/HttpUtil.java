package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class HttpUtil {

    private static Log log = LogFactory.getLog(HttpUtil.class);

    private static class SelfResponseHandler implements ResponseHandler<String> {
        private String charset;// 编码
        public SelfResponseHandler(String charset) {
            this.charset = charset;
        }
        public String handleResponse(HttpResponse response) throws IOException {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return EntityUtils.toString(entity, charset);
            }
            return null;
        }
    }

    /**
     * @param httpClient httpclient实例
     * @param headers    头信息
     * @param ip
     *@param url        链接
     * @param context    上下文
     * @param charset    编码    @return
     */
    public static String getUrlContent(CloseableHttpClient httpClient, Header headers[], HttpHost ip, String url, HttpContext context, String charset) {
        HttpGet get = new HttpGet(url);
        if (headers != null)
            get.setHeaders(headers);
        if (ip != null) {
            // 依次是代理地址，代理端口号，协议类型
            RequestConfig config = RequestConfig.custom().setProxy(ip).build();
            get.setConfig(config);
        }
        SelfResponseHandler rh = new SelfResponseHandler(charset);
        return ObtainContent(httpClient, get, context, charset, rh);
    }

    /**
     * @param httpClient httpclient实例
     * @param headers    头信息
     * @param url        链接
     * @param params     参数
     * @param context    上下文
     * @param charset    编码
     * @return
     */
    public static String postUrlContent(CloseableHttpClient httpClient, Header headers[], HttpHost ip, String url, Map<String, String> params, HttpContext context, String charset) {
        HttpPost post = new HttpPost(url);
        if (headers != null)
            post.setHeaders(headers);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : params.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        if (ip != null) {
            // 依次是代理地址，代理端口号，协议类型
            RequestConfig config = RequestConfig.custom().setProxy(ip).build();
            post.setConfig(config);
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        post.setEntity(entity);
        SelfResponseHandler rh = new SelfResponseHandler(charset);
        return ObtainContent(httpClient, post, context, charset, rh);
    }

    /**
     * @param httpClient httpclient 实例
     * @param headers    头信息
     * @param ip         代理ip
     * @param url        请求的链接
     * @param xml        xml 内容
     * @param context    上下文环境
     * @param charset    编码
     * @return
     */
    public static String postXml(CloseableHttpClient httpClient, Header headers[], HttpHost ip, String url, String xml, HttpContext context, String charset) {
        HttpPost post = new HttpPost(url);
        StringEntity se = null;
        try {
            se = new StringEntity(xml);
            se.setContentType("text/xml");
            post.setEntity(se);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException:", e);
        }
        if (headers != null)
            post.setHeaders(headers);
        if (ip != null) {
            // 依次是代理地址，代理端口号，协议类型
            RequestConfig config = RequestConfig.custom().setProxy(ip).build();
            post.setConfig(config);
        }
        SelfResponseHandler rh = new SelfResponseHandler(charset);
        return ObtainContent(httpClient, post, context, charset, rh);
    }

    /**
     * @param httpClient httpclient实例
     * @param headers    头信息
     * @param ip         代理
     * @param url        请求链接
     * @param json       json数据
     * @param context    上下文环境
     * @param charset    编码
     * @return
     */
    public static String postJson(CloseableHttpClient httpClient, Header headers[], HttpHost ip, String url, String json, HttpContext context, String charset) {
        HttpPost post = new HttpPost(url);
        StringEntity se = null;
        try {
            se = new StringEntity(json);
            se.setContentType("text/xml");
            post.setEntity(se);
        } catch (UnsupportedEncodingException e) {
            log.error("UnsupportedEncodingException:", e);
        }
        if (headers != null)
            post.setHeaders(headers);
        if (ip != null) {
            // 依次是代理地址，代理端口号，协议类型
            RequestConfig config = RequestConfig.custom().setProxy(ip).build();
            post.setConfig(config);
        }
        SelfResponseHandler rh = new SelfResponseHandler(charset);
        return ObtainContent(httpClient, post, context, charset, rh);
    }


    /**
     * @param httpClient httpclient实例
     * @param request    头信息
     * @param context    上下文
     * @param charset    编码
     * @param resh       自定义返回处理类
     * @return
     */
    public static <T> T ObtainContent(CloseableHttpClient httpClient, HttpRequestBase request, HttpContext context, String charset, ResponseHandler<T> resh) {
        CloseableHttpResponse response = null;
        T result = null;
        try {
            response = httpClient.execute(request, context);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (resh != null)
                    result = resh.handleResponse(response);
            }
        } catch (ClientProtocolException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }

    /**
     * @param httpClient httpclient实例
     * @param request    头信息
     * @param context    上下文
     * @param charset    编码
     * @return
     */
    public static String ObtainContent(CloseableHttpClient httpClient, HttpRequestBase request, HttpContext context, String charset) {
        CloseableHttpResponse response = null;
        String result = null;
        try {
            SelfResponseHandler resh = new SelfResponseHandler(charset);
            response = httpClient.execute(request, context);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                if (resh != null)
                    result = resh.handleResponse(response);
            }
        } catch (ClientProtocolException e) {
            log.error("ClientProtocolException:", e);
        } catch (IOException e) {
            log.error("IOException:", e);
        } finally {
            HttpClientUtils.closeQuietly(response);
        }
        return result;
    }
}
