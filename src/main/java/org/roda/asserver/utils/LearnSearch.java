package org.roda.asserver.utils;

import org.roda.asserver.db.MySQL;

/**
 * Created by kvvn on 3/25/15.
 */
public class LearnSearch {
    public void learn(String band, String type) {
        MySQL db = new MySQL();
        try {
            db.initConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/sa_db", "kvvn", "nau08fel");
            boolean isNew = db.isStaffNew(band, type);
            if (isNew == true){
                db.addNewStaff(band, type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
