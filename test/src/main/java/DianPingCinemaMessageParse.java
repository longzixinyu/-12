import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.ShopBean;
import util.URLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc10860 on 2015/4/15.
 */
public class DianPingCinemaMessageParse {
             //得到电影院信息的方法
         public List<ArrayList<ShopBean>> parseCinemaMessage() {
             String URL_PREFIX ="http://www.dianping.com";
             ArrayList<ShopBean>shop_list=new ArrayList<ShopBean>();
             List<ArrayList<ShopBean>>city_shop_list=new ArrayList<ArrayList<ShopBean>>();
             DianPingCinemaListParser parseCinemaList=new DianPingCinemaListParser();
             List<String> cinemaList = parseCinemaList.parseCinemaList();
             Elements cinema_li,page_url;
             Document doc;
             String url,str,url1;
             List<String> shop_url_list =new ArrayList<String>();
             int i,j,k,d,page_count;
             for ( i = 0; i <cinemaList.size(); i++) {
                  url = URL_PREFIX + cinemaList.get(i);
                  str = URLParser.getContent(url);
                  doc = Jsoup.parse(str);
                  page_url=doc.select("div.page").select("a[href]");
                  page_count=Integer.parseInt(page_url.get(page_url.size() - 2).text());
                 for ( j = 1; j <page_count+1 ; j++) {
                     url1 = url + "p" + j;
                     shop_url_list.add(URLParser.getContent(url1));
                 }
                 for (d = 0; d<page_count ; d++) {
                     cinema_li = Jsoup.parse(shop_url_list.get(d)).select("div.shop-list.J_shop-list.shop-all-list").select("li");
                     int li_size=cinema_li.size();
                     for ( k = 0; k <li_size ; k++) {
                         ShopBean shop=new ShopBean();
                         shop.setShop_name(cinema_li.get(k).select("div.tit").select("a").get(0).attr("title"));
                         shop.setShop_url(URL_PREFIX+cinema_li.get(k).select("div.tit").select("a").get(0).attr("href"));
                         shop.setShop_rate(cinema_li.get(k).select("div.comment").select("span").attr("title"));
                         shop.setShop_address(cinema_li.get(k).select("span.addr").text());
                         shop_list.add(shop);

                     }
                   }
                      city_shop_list.add(shop_list);
                 }
             return city_shop_list;
             }
         }

