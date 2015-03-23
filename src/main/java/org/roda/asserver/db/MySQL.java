package org.roda.asserver.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 * Created by kvvn on 3/23/15.
 */
public class MySQL extends AbstractDataBase {

    public String getIATA(String city, String country) throws Exception{
        String result = new String();
        reconnect();
        PreparedStatement ps = null;
        try{
            String co_synonym = checkCountrySynonym(country);
            if(co_synonym.length()>0){
                country = co_synonym;
            }
            String ci_synonym = checkCitySynonym(city);
            if(ci_synonym.length()>0){
                city = ci_synonym;
            }
            String sql = "SELECT iata FROM iata WHERE  name = ? AND parent_name = ?;";
            ps = getPreparedStatement(sql);
            ps.setString(1, city);
            ps.setString(2, country);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result = rs.getString("iata");
                System.out.println(result);
            }

        }catch (Exception ex){
            System.out.println(ex);
            throw new DataBaseDriverException(ex.getMessage(), ex.getCause());
        }
        return result;
    }

    private String checkCitySynonym(String city) throws Exception {
        String result = new String();
        reconnect();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT iata_value FROM city_synonyms WHERE  synonym = ?";
            ps = getPreparedStatement(sql);
            ps.setString(1, city);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result = rs.getString("iata_value");
            }

        }catch (Exception ex){
            System.out.println(ex);
            throw new DataBaseDriverException(ex.getMessage(), ex.getCause());
        }
        return result;
    }

    private String checkCountrySynonym(String country) throws Exception {
        String result = new String();
        reconnect();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT iata_value FROM country_synonyms WHERE  synonym = ?";
            ps = getPreparedStatement(sql);
            ps.setString(1, country);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result = rs.getString("iata_value");
            }

        }catch (Exception ex){
            System.out.println(ex);
            throw new DataBaseDriverException(ex.getMessage(), ex.getCause());
        }
        return result;
    }

}
