package org.roda.asserver.utils;

import org.json.simple.JSONObject;
import org.roda.asserver.objects.ASApiReqObj;
import org.roda.asserver.objects.RequestEventObj;
import org.roda.asserver.objects.RequestTicketObj;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;

/**
 * Created by kvvn on 3/19/15.
 */
public class Tools {
    public RequestEventObj parseTextEventForSearch(String text){
        return null;
    }


    public ASApiReqObj TicketObjToASAObj(RequestTicketObj inc, String location, String iata){



        ASApiReqObj res = new ASApiReqObj();

        res.setChildren(inc.getChildren());
        res.setAdults(inc.getAdults());
        res.setUser_ip(inc.getIp());

        res.setFrom_date(inc.getDate());
        res.setTo_date(this.getReturnDate(inc.getDate(), inc.getMaxstay()));

        res.setFrom_origin(location);
        res.setTo_destination(location);

        res.setTo_origin(iata);
        res.setFrom_destination(iata);

        res.setHost("dev.roda.net.ua");
        res.setMarker("75429");
        res.setLocale("ru");

        return res;
    }

    private String signature(ASApiReqObj asaObg) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String data = "fc151c8a1cd5bebf595210752baf1596:"
                + asaObg.getHost() + ":"
                + asaObg.getLocale() + ":"
                + asaObg.getMarker() + ":"
                + asaObg.getAdults() + ":"
                + asaObg.getChildren() + ":"
                + "0" + ":"
                + asaObg.getFrom_date() + ":"
                + asaObg.getFrom_destination() + ":"
                + asaObg.getFrom_origin() + ":"
                + asaObg.getTo_date() + ":"
                + asaObg.getTo_destination() + ":"
                + asaObg.getTo_origin() + ":"
                + "Y:"
                + asaObg.getUser_ip();
        System.out.println(data);
        byte[] bytesOfMessage = data.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);

        BigInteger bigInt = new BigInteger(1, thedigest);
        String md5Hex = bigInt.toString(16);

        while( md5Hex.length() < 32 ){
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    public String genASApiJsonString(ASApiReqObj asaObg) throws IOException, NoSuchAlgorithmException {
        JSONObject res = new JSONObject();
        StringWriter out = new StringWriter();


        LinkedHashMap obj = new LinkedHashMap();
        LinkedHashMap pasengers = new LinkedHashMap();
        LinkedList segments = new LinkedList();
        LinkedHashMap from = new LinkedHashMap();
        LinkedHashMap to = new LinkedHashMap();

        from.put("origin", asaObg.getFrom_origin());
        from.put("destination", asaObg.getFrom_destination());
        from.put("date", asaObg.getFrom_date());

        to.put("origin", asaObg.getTo_origin());
        to.put("destination", asaObg.getTo_destination());
        to.put("date", asaObg.getTo_date());

        segments.add(from);
        segments.add(to);

        pasengers.put("adults", asaObg.getAdults());
        pasengers.put("children", asaObg.getChildren());
        pasengers.put("infants", 0);

        res.put("signature", this.signature(asaObg));
        res.put("marker", asaObg.getMarker());
        res.put("host", asaObg.getHost());
        res.put("user_ip", asaObg.getUser_ip());
        res.put("locale", asaObg.getLocale());
        res.put("passengers", pasengers);
        res.put("segments", segments);
        res.put("trip_class", "Y");



        res.writeJSONString(out);

        System.out.println(out.toString());
        return out.toString();
    }

    private String getReturnDate(String cameDate, String Stay){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-L-d", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(cameDate, formatter);
        LocalDate newdate = date.plusDays(new Long(Stay));
        return newdate.toString();

    }

}
