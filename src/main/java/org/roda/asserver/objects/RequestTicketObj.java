package org.roda.asserver.objects;

import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.roda.asserver.utils.Tools;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by kvvn on 3/24/15.
 */
public class RequestTicketObj {

    private String city;
    private String country;
    private String adults;
    private String children;
    private String maxstay;
    private String minstay;
    private String ip;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date.substring(0 ,10);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getMaxstay() {
        return maxstay;
    }

    public void setMaxstay(String maxstay) {
        this.maxstay = maxstay;
    }

    public String getMinstay() {
        return minstay;
    }

    public void setMinstay(String minstay) {
        this.minstay = minstay;
    }


}
