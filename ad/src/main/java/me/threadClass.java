package me;

import java.util.concurrent.Callable;

/**
 * Created by ljc10860 on 2015/4/1.
 */
public class threadClass implements Callable<String> {
    DownloadPage downloadPage = new DownloadPage();
    String url = "http://www.xici.net.co/";

    public String call() {
     return DownloadPage.getContentFormUrl(url);
    }
}


