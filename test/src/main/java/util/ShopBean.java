package util;

/**
 * Created by ljc10860 on 2015/4/16.
 */
public class ShopBean {

    //储存电影院信息Bean
     private String shop_name; //电影院名称
     private String shop_url; //电影院url
     private String shop_rate; //电影院等级
     private String shop_address; //电影院地址

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public String getShop_rate() {
        return shop_rate;
    }

    public void setShop_rate(String shop_rate) {
        this.shop_rate = shop_rate;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
