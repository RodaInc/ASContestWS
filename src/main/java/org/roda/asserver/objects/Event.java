package org.roda.asserver.objects;

import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Created by kvvn on 3/19/15.
 */
public class Event implements JSONStreamAware {
    private String type;
    private String title;
    private String country;
    private String city;
    private String url;
    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public void writeJSONString(Writer writer) throws IOException {
        LinkedHashMap obj = new LinkedHashMap();
        obj.put("title", title);
        obj.put("date", date);
        obj.put("type", type);
        obj.put("url", url);
        obj.put("city", city);
        obj.put("country", country);

        JSONValue.writeJSONString(obj, writer);
    }
}
