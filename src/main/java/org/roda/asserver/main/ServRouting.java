package org.roda.asserver.main;

/**
 * Created by kvvn on 3/18/15.
 */

import static spark.Spark.*;

import org.roda.asserver.objects.RequestObj;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServRouting {
    public static void main(String[] args){



        EvenAPiConnector connector = new EvenAPiConnector();
        AviaSalesAPIConnector asConnecot = new AviaSalesAPIConnector();

        get("/events", (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            String eventData = connector.sendGetToSeatgeek(request.queryParams("band"));
            return eventData;
        });
        get("/tickets", (request, response) -> {
            boolean hotelsearch = false;
            RequestObj req = new RequestObj();
            response.header("Access-Control-Allow-Origin", "*");
            req.setCity(request.queryParams("city"));
            req.setCountry(request.queryParams("country"));
            req.setAdults(request.queryParams("adults"));
            req.setChildren(request.queryParams("children"));
            req.setMaxstay(request.queryParams("maxstay"));
            req.setMinstay(request.queryParams("minstay"));

            if(request.queryParams("hotelsearch").length()>0){
                hotelsearch = true;
            }

            String ip = request.ip();

            // this is only for tests
            if(ip.equals("127.0.0.1")){
                ip = "194.0.88.214";
            }
            req.setIp(ip);
            req.setDate(request.queryParams("date"));

            String tickets = asConnecot.getTickets(req);

            if(hotelsearch){
                String hotels = asConnecot.getHotels(req);
            }
            return "";
        });
    }
}
