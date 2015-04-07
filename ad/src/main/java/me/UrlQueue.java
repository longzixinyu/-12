package me;

import java.util.LinkedList;

/**
 * Created by ljc10860 on 2015/3/30.
 */
public class UrlQueue {
    /*
    对未访问url的队列的操作
     */
    public static LinkedList<String> urlQueue=new LinkedList<String>();
    public static final int MAX_SIZE = 10000;

    public synchronized static void addElem( String url){
        urlQueue.add(url);
    }

    public synchronized static String outElem(){
        return urlQueue.removeFirst();
    }
    public synchronized static boolean isEmpty(){
        return urlQueue.isEmpty();
    }
    public static int size(){
        return urlQueue.size();
    }
    public static boolean isContains(String url){
        return urlQueue.contains(url);
    }
}
