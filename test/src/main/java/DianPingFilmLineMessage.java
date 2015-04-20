import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.FilmLineBean;
import util.FilmMessageBean;
import util.ShopBean;
import util.URLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc10860 on 2015/4/16.
 */
public class DianPingFilmLineMessage {

    public static FilmMessageBean parserFileMessage(String str){
        //得到电影信息Bean
        JSONObject obj = JSON.parseObject(str);
        FilmMessageBean filmMessageBean=new FilmMessageBean();
        String msg=obj.getString("msg");
        Document document=Jsoup.parse(msg);
        Elements film=document.select("div.movie-desc.J-desc");
        String film_name=film.select("h3").text();  //电影名称
        Elements film_dl=film.select(".detail");
        String  movieDirector=film_dl.select("dd").get(0).text();  //电影导演
        String  movieActors=film_dl.select("dd").get(1).text();  //电影主演
        filmMessageBean.setMovieDirector(movieDirector);    //将电影导演加入Bean
        filmMessageBean.setMovieActors(movieActors);   //将电影主演加入Bean
        return filmMessageBean;
    }

     public static FilmLineBean parserFilmLine(String str){
         //得到院线信息Bean
         FilmLineBean filmLineBean=new FilmLineBean();
         JSONObject obj = JSON.parseObject(str);
         int i;
         String msg=obj.getString("msg");
         Elements booking_body=Jsoup.parse(msg).select(".booking-body").select("tbody");
         String startTime;
         String endTime;
         String languageVersion;
         String cinemaRoomName;
         String prize;
         if (booking_body!=null) {
            Elements film_line_tr=booking_body.select("tr");
             for (i = 0; i <film_line_tr.size() ; i++) {
                  startTime=film_line_tr.get(i).select("td").get(0).text();  //得到影片开始时间
                  endTime=film_line_tr.get(i).select("td").get(1).text();  //得到影片结束时间
                  languageVersion=film_line_tr.get(i).select("td").get(2).text();  //得到影片语言版本
                  cinemaRoomName=film_line_tr.get(i).select("td").get(3).text();  //得到放映厅号
                  prize=film_line_tr.get(i).select("td").get(4).text();  //得到价格
                  filmLineBean.setStartTime(startTime);
                  filmLineBean.setEndTime(endTime);
                  filmLineBean.setLanguageVersion(languageVersion);
                  filmLineBean.setCinemaRoomName(cinemaRoomName);
                  filmLineBean.setPrice(prize);
             }
         }
        return filmLineBean;
     }

    public void parseFilmLine() {
             //得到影片信息和院线信息主方法
             DianPingCinemaMessageParse dianPingCinemaMessageParse = new DianPingCinemaMessageParse();
             List< ArrayList<ShopBean>> city_shop_list = dianPingCinemaMessageParse.parseCinemaMessage();
             List<ShopBean> shop_list =new ArrayList<ShopBean>();
             String ID_URL_PREFIX="http://www.dianping.com/ajax/json/shop/movie/showlist?";
             String str,a,b,shop_url,get_shopid_url,id_url,str1,message;
             int i,j,k,l;
             String[] film_date =new String[10];
             String[] file_id=new String[10];
             Document document,document1,document2;
             Elements date,li;
             for ( k = 0; k <city_shop_list.size() ; k++) {
                 shop_list=city_shop_list.get(k);
                 for (l=0;l<shop_list.size();l++){
                 shop_url =shop_list.get(l).getShop_url();   //得到各影院链接shop-url
                 get_shopid_url=shop_url.substring(shop_url.lastIndexOf("/")+1,shop_url.length());
               str = URLParser.getContent(shop_url);
              document= Jsoup.parse(str);
              a=document.select(".J-panels").get(0).toString();
              b= StringUtils.substringBetween(a, "<script type=\"text/panel\" class=\"J-panels\">", "</script>");
              document1=Jsoup.parse(b);
              date=document1.select("div.dates.clearfix.J-dates").select("a"); //日期信息<a>
             for(i=0;i<date.size();i++){

                 //根据电影日期循环遍历
                 film_date[i]=date.get(0).attr("data-date");    //电影日期
                 if (film_date[i]!=null) {
                     id_url = ID_URL_PREFIX + "shopId=" + get_shopid_url + "&date=" + film_date[i];
                     str1=URLParser.getContent(id_url);
                 JSONObject obj = JSON.parseObject(str1);
                  message=obj.getString("msg");
                 document2=Jsoup.parse(message);
                 li=document2.select("li[data-id]");
                 for(j=0;j<li.size();j++){
                     file_id[j]=li.get(j).attr("data-id");//电影id
                     String file_url=id_url+"&movieId="+file_id[j];
                     String str2=URLParser.getContent(file_url);
                     parserFileMessage(str2);
                     parserFilmLine(str2);
                 }
             }
             }
           }
       }
    }
}


