package me;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;


public class DownloadPage {
         /*
         根据url抓取网页内容
          */
        static HttpClient httpclient = new HttpClient();

    public synchronized static String getContentFormUrl (String url)  {
        GetMethod getMethod = new GetMethod(url);
        String content=null;
      try {
         int status = httpclient.executeMethod(getMethod);
          int resStatus =getMethod.getStatusLine().getStatusCode();
          if (status == HttpStatus.SC_OK) {
             content =getMethod.getResponseBodyAsString();
          }}catch (Exception e) {
          e.printStackTrace();
      }finally {
        getMethod.releaseConnection();
      }
        return content;
  }




}















