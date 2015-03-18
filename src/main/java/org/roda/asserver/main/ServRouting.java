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
        get("/", (request, response) -> {
            String eventData = connector.sendGetToSeatgeek(request.queryParams("band"));
            return eventData;
        });

    }
}
