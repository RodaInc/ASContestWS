package org.roda.asserver.main;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.roda.asserver.db.MySQL;
import org.roda.asserver.objects.RequestTicketObj;
import org.roda.asserver.utils.ReqestSender;

import java.io.IOException;
import java.net.URL;

/**
 * Created by kvvn on 3/19/15.
 */
public class AviaSalesAPIConnector {

    MySQL db = new MySQL();

    public String getTickets(RequestTicketObj req){
        try {
            //where am i
            String location = getLocation(req.getIp());
            db.initConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/sa_db", "kvvn", "nau08fel");
            //where i want to go
            String iata = db.getIATA(req.getCity(), req.getCountry());
            System.out.println(req.getDate().substring(0, 7));
            System.out.println(location);
            String url = "http://api.travelpayouts.com/v1/prices/calendar?depart_date=" + req.getDate().substring(0, 7) +
                    "&origin=" + location +
                    "&destination=" + iata +
                    "&calendar_type=departure_date&token=fc151c8a1cd5bebf595210752baf1596";

            URL obj = new URL(url);
            ReqestSender rsender = new ReqestSender();
            String json = rsender.sendGet(obj);

            System.out.println(json);
        } catch (Exception ex){
            System.out.println(ex);
        }
        return "";
    }

    public String getHotels(RequestTicketObj req){
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
