package org.roda.asserver.main;

/**
 * Created by kvvn on 3/18/15.
 */

import static spark.Spark.*;

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
            response.header("Access-Control-Allow-Origin", "*");
            String city = request.queryParams("city");
            String ip = request.ip();

            // this is only for tests
            if(ip.equals("127.0.0.1")){
                ip = "194.0.88.214";
            }
            String date = request.queryParams("date");
            return asConnecot.getTickets(city, ip, date);
        });
    }
}
