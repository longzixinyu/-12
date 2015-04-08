package me;

import java.util.concurrent.Callable;

/**
 * Created by ljc10860 on 2015/4/1.
 */
public class threadClass implements Callable<String> {
    DownloadPage downloadPage = new DownloadPage();
    String url = "https://api.weibo.com/2/statuses/public_timeline.json?access_token=2.00tsi5tFbmaFjD5f29431b07Z5F7nD&count=1";

    public String call() {
     return DownloadPage.getContentFormUrl(url);
    }
}


