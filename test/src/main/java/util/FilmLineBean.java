package util;

/**
 * Created by ljc10860 on 2015/4/17.
 */
public class FilmLineBean {

    //储存电影院线信息的Bean
    private String startTime;  //电影开始时间
    private String endTime;  //电影结束时间
    private String languageVersion; //电影语言版本
    private String cinemaRoomName;  //电影放映厅
    private String price;  //电影价格

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCinemaRoomName() {
        return cinemaRoomName;
    }

    public void setCinemaRoomName(String cinemaRoomName) {
        this.cinemaRoomName = cinemaRoomName;
    }

    public String getLanguageVersion() {
        return languageVersion;
    }

    public void setLanguageVersion(String languageVersion) {
        this.languageVersion = languageVersion;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
