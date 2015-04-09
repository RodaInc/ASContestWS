package org.roda.asserver.objects;

/**
 * Created by kvvn on 3/31/15.
 */
public class ASApiReqObj {
    private String from_origin;
    private String to_origin;
    private String from_date;
    private String to_date;
    private String from_destination;
    private String to_destination;
    private String adults;
    private String children;
    private String marker;
    private String host;
    private String user_ip;
    private String locale;


    public String getFrom_origin() {
        return from_origin;
    }

    public void setFrom_origin(String from_origin) {
        this.from_origin = from_origin;
    }

    public String getTo_origin() {
        return to_origin;
    }

    public void setTo_origin(String to_origin) {
        this.to_origin = to_origin;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getFrom_destination() {
        return from_destination;
    }

    public void setFrom_destination(String from_destination) {
        this.from_destination = from_destination;
    }

    public String getTo_destination() {
        return to_destination;
    }

    public void setTo_destination(String to_destination) {
        this.to_destination = to_destination;
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

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
