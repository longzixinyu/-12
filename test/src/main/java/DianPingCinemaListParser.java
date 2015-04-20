import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.URLParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc10860 on 2015/4/14.
 */
public class DianPingCinemaListParser {
    protected List<String> parseCinemaList() {
        String URL_PREFIX = "http://www.dianping.com";
        DianPingCityListParser dianPingCinemaListParser = new DianPingCityListParser();
        List<String> cityList = dianPingCinemaListParser.parseCityList();  //城市列表
        List<String> cinemaList = new ArrayList<String>(); //电影院列表
        int i;
        String city_url;
        String str;
        Document doc;
        Elements ul;
        Element url;
        String cinema_url_suffix;
        for (i = 0; i < cityList.size(); i++) {
            //得到一个城市所有电影院的url
            city_url = URL_PREFIX + cityList.get(i);  //城市URL
            str = URLParser.getContent(city_url);
            doc = Jsoup.parse(str);
            ul =  doc.select("div.aside.aside-left").select("ul");
            if (ul.size() >= 1) {
                url =  ul.select("li").get(1).select("a[href]").get(2);
                if (url.text().equals("电影院")) {
                    cinema_url_suffix = url.attr("href"); //电影院链接后缀
                    cinemaList.add(cinema_url_suffix); //加入电影院列表
                }
            }
        }
        return cinemaList;
    }
}
