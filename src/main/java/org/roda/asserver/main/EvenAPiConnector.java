package org.roda.asserver.main;

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
    private final String USER_AGENT = "Mozilla/5.0";

    public String sendGetToSeatgeek(String band) throws IOException {
        String url = "http://api.seatgeek.com/2/events?performers.slug=";
        URL obj = new URL(url + band);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(response.toString());
        return response.toString();
    }
}
