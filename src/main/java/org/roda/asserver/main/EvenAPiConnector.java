package org.roda.asserver.main;


import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.roda.asserver.objects.Event;
import org.roda.asserver.objects.RequestEventObj;
import org.roda.asserver.utils.ReqestSender;

import java.io.IOException;

import java.io.StringWriter;
import java.net.URL;
import java.util.*;

/**
 * Created by kvvn on 3/18/15.
 */
public class EvenAPiConnector {

    JSONParser parser = new JSONParser();

    ContainerFactory containerFactory = new ContainerFactory(){
        public List creatArrayContainer() {
            return new LinkedList();
        }

        public Map createObjectContainer() {
            return new LinkedHashMap();
        }

    };

    public String sendGetToSeatgeek(RequestEventObj req) throws IOException, ParseException {
        JSONArray resevents = new JSONArray();

        String url = "http://api.seatgeek.com/2/events?performers.slug=";

        URL obj = new URL(url + req.getBand());
        ReqestSender rsender = new ReqestSender();
        Map json = (Map)parser.parse(rsender.sendGet(obj), containerFactory);
        Iterator iter = json.entrySet().iterator();

        while(iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            if (entry.getKey().equals("events")){
                LinkedList events = (LinkedList) entry.getValue();
                for (Object event: events){
                    Event ourEvent = new Event();
                    LinkedHashMap eventmap = (LinkedHashMap)event;
                    LinkedHashMap venue = (LinkedHashMap)eventmap.get("venue");
                    ourEvent.setCity((String)venue.get("city"));
                    ourEvent.setCountry((String)venue.get("country"));
                    ourEvent.setType((String)eventmap.get("type"));
                    ourEvent.setTitle((String)eventmap.get("title"));
                    ourEvent.setDate((String)eventmap.get("datetime_utc"));
                    ourEvent.setUrl((String)eventmap.get("url"));
                    resevents.add(ourEvent);
                }
            }
        }
        StringWriter out = new StringWriter();
        resevents.writeJSONString(out);
        System.out.println(out.toString());
        return out.toString();

    }
}
