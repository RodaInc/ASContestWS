package org.roda.asserver.objects;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.roda.asserver.utils.Tools;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kvvn on 3/24/15.
 */
public class RequestEventObj {

    String band;
    String type;
    String city;
    String country;
    String date_start;
    String date_end;

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate_start() {
        return date_start;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }


}
