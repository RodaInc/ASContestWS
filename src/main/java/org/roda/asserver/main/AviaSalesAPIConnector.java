package org.roda.asserver.main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.roda.asserver.db.MySQL;
import org.roda.asserver.utils.ReqestSender;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kvvn on 3/19/15.
 */
public class AviaSalesAPIConnector {

    MySQL db = new MySQL();

    public String getTickets(String city, String country, String ip, String date){
        try {
            String location = getLocation(ip);
            db.initConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/sa_db", "kvvn", "nau08fel");
            String iata = db.getIATA(city, country);
            System.out.println(location);

        } catch (Exception ex){
            System.out.println(ex);
        }
        return "";
    }

    private String getLocation(String ip) throws IOException {

        String url = "http://www.travelpayouts.com/whereami?locale=ru&ip=";

        URL obj = new URL(url + ip);
        ReqestSender rsender = new ReqestSender();
        String json = rsender.sendGet(obj);

        Object json_obj= JSONValue.parse(json);
        System.out.println(json_obj);
        JSONObject jsonObject = (JSONObject) json_obj;

        return (String) jsonObject.get("iata");
    }

}
