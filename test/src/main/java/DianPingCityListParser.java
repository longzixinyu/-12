import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import util.URLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc10860 on 2015/4/14.
 */
public class DianPingCityListParser {

    //得到城市列表的方法
    public List<String> parseCityList() {
        List<String> citylist=new ArrayList<String>();
        String str= URLParser.getContent("http://www.dianping.com/citylist");
        int i;
        String city_name;
        Document document = Jsoup.parse(str);
        Elements city_href=document.select("div.section").select("ul").get(1).select("a[href]");
         for ( i = 0; i <city_href.size() ; i++) {
              city_name=city_href.get(i).attr("href");
             if (!city_name.equals("#")) {
                citylist.add(city_name);
             }
         }return citylist;
     }
}
