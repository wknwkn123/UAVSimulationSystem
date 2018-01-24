package config;

/**
 * Created by Ziji Shi on 24/1/18.
 */
public class Config {

    public String inputPathRoot;
    public String pathToMap;
    public String pathToRequest;

    public String airMapType;
    public String requestType;

    public String outputPathRoot;

    public Config(){
        inputPathRoot = "data/input";

        airMapType="PLANAR";
        pathToMap = inputPathRoot+"/AirSpaceMap2D.json";

        requestType = "RANDOM";
        pathToRequest = inputPathRoot+"/RandomRequest.csv";

        outputPathRoot = "data/output";

    }
}
