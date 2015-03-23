package org.roda.asserver.db;

/**
 * Created by kvvn on 3/23/15.
 */
public class DataBaseConnectionData {

    public String DRIVER;
    public String URL;
    public String USER;
    public String PASS;
    public DataBaseConnectionData SLAVE;

    @Override
    public String toString() {
        return "DataBaseConnection [DRIVER=" + DRIVER + ", URL=" + URL
                + ", USER=" + USER + ", PASS=" + PASS + ", SLAVE=" + SLAVE
                + "]";
    }

}
