package org.roda.asserver.main;

import org.roda.asserver.utils.ReqestSender;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kvvn on 3/19/15.
 */
public class AviaSalesAPIConnector {

    public String getTickets(String city, String ip, String date){
        try {
            String location = getLocation(ip);
            System.out.println(location);
        } catch (Exception ex){

        }
        return "";
    }

    private String getLocation(String ip) throws IOException {

        String url = "http://www.travelpayouts.com/whereami?locale=ru&ip=";

        URL obj = new URL(url + ip);
        ReqestSender rsender = new ReqestSender();

        return rsender.sendGet(obj);
    }

}
