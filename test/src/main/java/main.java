import java.util.List;

/**
 * Created by ljc10860 on 2015/4/20.
 */
public class main {
    public static void main(String args[]){
        DianPingCityListParser dianPingCityListParser=new DianPingCityListParser();
        List<String> list=dianPingCityListParser.parseCityList();
        for (int i=0;i<list.size();i++){
            System.out.println(list.get(i));


        }


    }
}
