package me;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by ljc10860 on 2015/3/31.
 */
public class testClass {
    public static void main(String args[]) {
        String[] content = new String[2000];
        String[] content1 = new String[2000];
        String[] content2 = new String[2000];
        String[] content3 = new String[2000];
        String[] content4 = new String[2000];
        String[] content5 = new String[2000];
        ExecutorService threadPool = Executors.newFixedThreadPool(10);  //初始化线程池
        CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);

        //得到线程运行结果
        String[] result = new String[1000];
        for (int i = 0; i < 1000; i++) {
            pool.submit(new threadClass());
            try {
                result[i] = pool.take().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //通过Map类将json字符串转化为map对象并解析
        for (int i = 0; i < 1000; i++) {
                Map map1= JSONObject.parseObject(result[i], Map.class);
                String s=map1.get("statuses").toString();
                String s1 = null;
                if (!s.substring(0, 1).equals("{")) {
                    s1=s.substring(1,s.length()-1);
                }
                else{
                    s1=s;
                }
                Map map2=JSONObject.parseObject(s1,Map.class);
                Map map3=JSONObject.parseObject((String)map2.get("user").toString(),Map.class);
                content[i]=map3.get("id").toString();
                content1[i]=map3.get("screen_name").toString();
                content2[i]=map2.get("id").toString();
                content3[i]=map2.get("created_at").toString();
                content4[i]=map2.get("mid").toString();
                content5[i]=map2.get("text").toString();
            }

             threadPool.shutdown();   //关闭线程池
             javasheet sheet = new javasheet();
             sheet.addsheet(content,content1,content2,content3,content4,content5);  //将结果加入sheet表

    }

}



