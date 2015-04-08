package me;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ljc10860 on 2015/3/31.
 */
public class testClass {
    public static void main(String args[]) {
        String[] content = new String[2000];   //储存用户id
        String[] content1 = new String[2000];  //储存用户昵称
        String[] content2 = new String[2000];  //储存微博id
        String[] content3 = new String[2000];  //储存微博创建时间
        String[] content4 = new String[2000];  //储存微博mid
        String[] content5 = new String[2000];  //储存微博内容
        int NEED_NUMBER=1000;
        ExecutorService threadPool = Executors.newFixedThreadPool(10);  //初始化线程池
        CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);
        //得到线程运行结果
        String[] result = new String[1000];
        for (int i = 0; i < NEED_NUMBER; i++) {
            pool.submit(new threadClass());
            try {
                result[i] = pool.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //解析json对象
        for (int i = 0; i <NEED_NUMBER; i++) {
            JSONObject obj = JSON.parseObject(result[i]);
            JSONArray statusArr = obj.getJSONArray("statuses");
            for (int k = 0; k < statusArr.size(); k++) {
                JSONObject weiboObj = statusArr.getJSONObject(k);
                JSONObject userObj = weiboObj.getJSONObject("user");
                content[i] = userObj.getString("id");  //获取用户id
                content1[i] = userObj.getString("screen_name"); //获取用户昵称
                content2[i] = weiboObj.getString("id"); //获取微博id
                content3[i] = weiboObj.getString("created_at"); //获取微博创建时间
                content4[i] = weiboObj.getString("mid");   //获取微博mid
                content5[i] = weiboObj.getString("text");  //获取微博内容
                }
        }
        threadPool.shutdown();   //关闭线程池
        javasheet sheet = new javasheet();
        sheet.addsheet(content, content1, content2, content3, content4, content5);  //将结果加入sheet表
    }
}


