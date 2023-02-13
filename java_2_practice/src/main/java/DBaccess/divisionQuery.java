package DBaccess;

import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB access division class
 */

public abstract class divisionQuery {

    /**
     * DB access return Observable List of of divisions
     * @param countryId country id
     * @return divsionList
     */

    public static ObservableList<division> getAllDivisions(int countryId) {

     ObservableList<division> divisionList = FXCollections.observableArrayList();

     try{
         String sql = "SELECT * FROM first_level_divisions where country_Id = ?";
         PreparedStatement ps = jdbc.connection.prepareStatement(sql);
         ps.setInt(1, countryId);
         ResultSet rs = ps.executeQuery();
         while(rs.next()){
             int divisionId = rs.getInt("Division_ID");
             String division = rs.getString("Division");
             //System.out.print(divisionId + " | " + division);

             division D = new division(divisionId, division);

             divisionList.add(D);
         }

     }catch(SQLException ex){
         ex.printStackTrace();
     }
     return divisionList;
    }
}
