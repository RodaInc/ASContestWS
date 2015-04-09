package org.roda.asserver.main;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.roda.asserver.db.MySQL;
import org.roda.asserver.objects.ASApiReqObj;
import org.roda.asserver.objects.RequestTicketObj;
import org.roda.asserver.utils.ReqestSender;
import org.roda.asserver.utils.Tools;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kvvn on 3/19/15.
 */
public class AviaSalesAPIConnector {

    MySQL db = new MySQL();
    Tools tools = new Tools();

    public String getTickets(RequestTicketObj req){
        try {
            //where am i
            String location = getLocation(req.getIp());
            db.initConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/sa_db", "kvvn", "nau08fel");
            //where i want to go
            String iata = db.getIATA(req.getCity(), req.getCountry());

            String url = "http://api.travelpayouts.com/v1/flight_search";
//            String url = "http://localhost:4567/testpost";
            String body = tools.genASApiJsonString(tools.TicketObjToASAObj(req, location, iata));
            URL obj = new URL(url);
            ReqestSender rsender = new ReqestSender();
//            String json = rsender.sendPost(obj, body);//rsender.sendGet(obj);
//
//            System.out.println(json);
            // {"geoip_city": "", "host": "dev.roda.net.ua", "passengers": {"adults": 1, "infants": 0, "children": 0}, "geoip_country": "UA", "locale": "ru", "search_id": "d51bea55-32c0-4f86-931b-611f8366949f", "gates_count": 0, "search_depth": 44, "affiliate_has_sales": false, "show_ads": true, "_ga": null, "city_distance": 8649, "origin_country": "UA", "marker": "75429", "user_ip": "194.0.88.214", "average_price": {"not_direct": 63505, "direct": null}, "segments": [{"origin_country": "UA", "date": "2015-05-15", "original_origin": "IEV", "origin": "IEV", "destination": "YVR", "original_destination": "YVR", "destination_country": "CA"}, {"origin_country": "CA", "date": "2015-05-20", "original_origin": "YVR", "origin": "YVR", "destination": "IEV", "original_destination": "IEV", "destination_country": "UA"}], "destination_country": "CA", "meta": {"uuid": "d51bea55-32c0-4f86-931b-611f8366949f"}, "range": "false", "banner_info": {}, "internal": false, "know_english": "false", "signature": "f7d1fd1c46197fac04cc3f795ef35695", "affiliate": true, "auid": null, "currency_rates": {"uah": 2.959108637, "lbp": 0.0404906, "ang": 34.0094, "sdg": 10.6942, "mxn": 3.95138, "kyd": 74.2401, "qar": 16.711, "php": 1.3767, "shp": 91.2806, "ars": 6.93639, "cny": 10.0094, "egp": 7.97956, "iqd": 0.0519931, "rsd": 0.535534, "bif": 0.0387786, "crc": 0.113941, "mad": 6.06538, "uyu": 2.42007, "yer": 0.283076, "lsl": 4.99905, "byr": 0.00416476, "nad": 4.99905, "pab": 60.8769, "kgs": 1.0176399999999999, "fjd": 29.2453, "kmf": 0.131497, "hkd": 7.83635, "mzn": 1.7941, "mro": 0.20817, "jpy": 0.516605, "sll": 0.013869, "dkk": 8.96154, "vef": 9.5875, "bhd": 161.503, "mkd": 1.04964, "mdl": 3.3880899999999996, "gtq": 7.98647, "awg": 34.0094, "bgn": 34.1411, "tnd": 30.5739, "krw": 0.0556009, "mwk": 0.138567, "vuv": 0.559882, "usd": 62.6797, "mur": 1.71972, "sek": 7.2981, "lkr": 0.455351, "omr": 158.122, "clp": 0.0955267, "hrk": 8.46063, "uzs": 0.0253354, "brl": 20.2075, "btn": 0.973831, "mnt": 0.0306712, "tjs": 11.4682, "vnd": 0.00285889, "std": 0.00262063, "zar": 5.1083300000000005, "tzs": 0.0330459, "myr": 16.5005, "gnf": 0.00830909, "nzd": 45.143, "kwd": 203.258, "syp": 0.322221, "aed": 16.5744, "twd": 1.92577, "npr": 0.608308, "szl": 4.99905, "thb": 1.8557, "pyg": 0.0127458, "dzd": 0.625893, "gip": 91.2806, "mmk": 0.0588466, "bnd": 44.1553, "xcd": 22.547, "bdt": 0.778923, "cup": 2.29724, "aoa": 0.569754, "eur": 66.9168, "try": 23.9739, "ngn": 0.305071, "wst": 24.022, "ttd": 9.57468, "irr": 0.00217452, "gmd": 1.41914, "bmd": 60.8769, "lak": 0.00746168, "kpw": 0.440854, "kzt": 0.33815100000000003, "djf": 0.342313, "inr": 0.998999, "htg": 1.29035, "tmt": 17.911, "ltl": 18.7361, "xpf": 0.54212, "cad": 49.4397, "bsd": 60.8769, "xdr": 86.5362, "etb": 2.98694, "fkp": 91.2806, "jod": 85.9722, "ugx": 0.0196274, "bob": 8.81043, "afn": 1.0547, "cve": 0.58222, "gel": 27.8255, "pen": 19.6599, "mga": 0.020339, "bbd": 30.4384, "jmd": 0.52831, "srd": 18.4518, "idr": 0.00463014, "dop": 1.36008, "cop": 0.0231911, "lrd": 0.658129, "ern": 5.81441, "zwl": 0.0, "sgd": 45.1746, "mop": 7.60811, "zmk": 0.00838526, "bam": 33.0766, "bzd": 30.5165, "azn": 59.712, "xof": 0.0986225, "chf": 62.5296, "isk": 0.438058, "ils": 15.1379, "ron": 15.0408, "czk": 2.4496700000000002, "lyd": 44.595, "nok": 7.71101, "pgk": 22.981, "amd": 0.129664, "sbd": 7.75571, "scr": 4.48951, "pln": 16.1151, "mvr": 3.96076, "bwp": 6.03032, "gbp": 94.4896, "pkr": 0.598446, "hnl": 2.8989, "gyd": 0.293804, "all": 0.457427, "rub": 1, "xaf": 0.0986225, "kes": 0.664208, "rwf": 0.0847893, "cdf": 0.0655602, "huf": 0.21839200000000003, "ghs": 16.8169, "aud": 47.7807, "khr": 0.0150804, "sos": 0.0859921, "sar": 16.2326, "top": 29.2513, "lvl": 92.0491, "nio": 2.26712}, "currency": "rub", "travelpayouts_api_request": true, "trip_class": "Y"}
            Map<String, String> params = new HashMap<>();
            params.put("Accept-Encoding", "gzip,deflate,sdch");
            String tickets = rsender.sendGet(new URL("http://api.travelpayouts.com/v1/flight_search_results?uuid=" + "d51bea55-32c0-4f86-931b-611f8366949f"), params);
            System.out.println(tickets);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    public String getHotels(RequestTicketObj req){
        return "";
    }


    private String getLocation(String ip) throws IOException {

        String url = "http://www.travelpayouts.com/whereami?locale=ru&ip=";

        URL obj = new URL(url + ip);
        ReqestSender rsender = new ReqestSender();
        String json = rsender.sendGet(obj);

        Object json_obj= JSONValue.parse(json);
        System.out.println(json_obj);
        JSONObject jsonObject = (JSONObject) json_obj;

        return (String) jsonObject.get("iata");
    }

}
