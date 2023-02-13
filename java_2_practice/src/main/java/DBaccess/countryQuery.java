package DBaccess;

import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB country database operation class
 */

public abstract class countryQuery {

    /**
     * DB access return Observable List of countries
     * @return country list
     */

    public static ObservableList<countries> getAllCountries(){
        ObservableList<countries> countryList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");
                System.out.print(countryId + " " + country);

                countries C = new countries(countryId,country);

                countryList.add(C);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return countryList;
    }
}
