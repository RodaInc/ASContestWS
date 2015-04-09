package org.roda.asserver.main;

/**
 * Created by kvvn on 3/18/15.
 */

import static spark.Spark.*;

import org.roda.asserver.objects.RequestEventObj;
import org.roda.asserver.objects.RequestTicketObj;

public class ServRouting {
    public static void main(String[] args){



        EvenAPiConnector connector = new EvenAPiConnector();
        AviaSalesAPIConnector asConnecot = new AviaSalesAPIConnector();

        get("/events", (request, response) -> {
            RequestEventObj req = new RequestEventObj();

            response.header("Access-Control-Allow-Origin", "*");

            req.setCity(request.queryParams("city"));
            req.setBand(request.queryParams("band"));
            req.setCountry(request.queryParams("country"));
            req.setDate_end(request.queryParams("date_end"));
            req.setDate_start(request.queryParams("date_start"));
            req.setType(request.queryParams("type"));

            String eventData = connector.sendGetToSeatgeek(req);

            return eventData;
        });

        get("/tickets", (request, response) -> {
            boolean hotelsearch = false;
            RequestTicketObj req = new RequestTicketObj();
            response.header("Access-Control-Allow-Origin", "*");
            req.setCity(request.queryParams("city"));
            req.setCountry(request.queryParams("country"));
            req.setAdults(request.queryParams("adults"));
            req.setChildren(request.queryParams("children"));
            req.setMaxstay(request.queryParams("maxstay"));
            req.setMinstay(request.queryParams("minstay"));
            System.out.println(req.getAdults());
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

        get("/fulltext", (request, response) ->{
            response.header("Access-Control-Allow-Origin", "*");
            String text = request.queryParams("text");

            return "";
        });

        post("/testpost", (request, response) -> {
            System.out.println(request.body());
            return "test";
        });
    }
}
