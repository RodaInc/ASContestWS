package org.roda.asserver.utils;

import org.json.simple.JSONObject;
import org.roda.asserver.objects.RequestEventObj;
import org.roda.asserver.objects.RequestTicketObj;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by kvvn on 3/19/15.
 */
public class Tools {
    public RequestEventObj parseTextEventForSearch(String text){
        return null;
    }

    public String signature(String[] incdata) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String data = incdata[0] + ":"
                + incdata[1] + ":"
                + incdata[2] + ":"
                +incdata[3] + ":"
                +incdata[4] + ":"
                +incdata[5] + ":"
                +incdata[6] + ":" +
                incdata[7] + ":" +
                incdata[8] + ":" +
                incdata[9] + ":" +
                incdata[10] + ":" +
                incdata[11] + ":" +
                incdata[12] + ":" +
                incdata[13];

        byte[] bytesOfMessage = data.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);

        return new String(thedigest);
    }

    public String genJsonString() throws IOException {
        JSONObject res = new JSONObject();
        StringWriter out = new StringWriter();


        LinkedHashMap obj = new LinkedHashMap();
        LinkedHashMap pasengers = new LinkedHashMap();
        LinkedList segments = new LinkedList();
        LinkedHashMap from = new LinkedHashMap();
        LinkedHashMap to = new LinkedHashMap();

        from.put("origin", "");
        from.put("destination", "");
        from.put("date", "");

        to.put("origin", "");
        to.put("destination", "");
        to.put("date", "");

        segments.add(from);
        segments.add(to);

        pasengers.put("adults", "");
        pasengers.put("children", "");
        pasengers.put("infants", 0);

        obj.put("signature", "");
        obj.put("marker", "");
        obj.put("host", "");
        obj.put("user_ip", "");
        obj.put("locale", "");
        obj.put("passengers", pasengers);
        obj.put("segments", segments);

        res.writeJSONString(out);
        return out.toString();
    }

}
