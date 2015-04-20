package util;

/**
 * Created by ljc10860 on 2015/4/17.
 */
public class FilmMessageBean {

    //储存电影信息Bean
    private String movieDirector; //电影导演
    private String movieActors;  //电影主演

    public String getMovieActors() {
        return movieActors;
    }

    public void setMovieActors(String movieActors) {
        this.movieActors = movieActors;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }
}
