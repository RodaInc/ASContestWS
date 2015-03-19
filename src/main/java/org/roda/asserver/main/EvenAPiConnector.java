package org.roda.asserver.main;

import org.roda.asserver.utils.ReqestSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kvvn on 3/18/15.
 */
public class EvenAPiConnector {


    public String sendGetToSeatgeek(String band) throws IOException {
        String url = "http://api.seatgeek.com/2/events?performers.slug=";
        URL obj = new URL(url + band);

        ReqestSender rsender = new ReqestSender();
        return rsender.sendGet(obj);
    }
}
