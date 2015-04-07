package me;

import java.util.HashSet;

/**
 * Created by ljc10860 on 2015/3/30.
 */
public class VisitedUrlQueue {
    public static HashSet<String> visitedUrlQueue=new HashSet<String>();
    public synchronized static void addElem( String url){
        visitedUrlQueue.add(url);
    }
    public  static boolean isContains(String url){
        return visitedUrlQueue.contains(url);
    }
    public static int size(){
        return visitedUrlQueue.size();
    }
}


