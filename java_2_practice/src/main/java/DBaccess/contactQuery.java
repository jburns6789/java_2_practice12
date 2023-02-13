package DBaccess;

import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * contact database access operation class
 */

public abstract class contactQuery {

    /**
     * DB access return an Observable List of all contacts
     * @return contactList
     */

    public static ObservableList<contacts>getAllContacts(){
        ObservableList<contacts> contactList = FXCollections.observableArrayList();

            try{
                String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
                PreparedStatement ps = jdbc.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int contactId = rs.getInt("Contact_ID");
                    String contactName = rs.getString("Contact_Name");
                    String email = rs.getString("Email");
                    System.out.print(contactId + " " + contactName + " " + email);

                    contacts c = new contacts(contactId, contactName, email);

                    contactList.add(c);
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return contactList;
    }

    /**
     * DB access of inserting a new contact into the database
     * @param contactId contactId
     * @param contactName contactname
     * @param email email
     * @return rows affected
     */

    public static int insertContact(int contactId, String contactName, String email){
        int rowsAffected = 0;
        try{
            String sql = "INSERT INTO contacts (Contact_ID, Contact_Name, Email) VALUES(NULL, ?,?)";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, contactName);
            ps.setString(2,email);
            rowsAffected = ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
          return rowsAffected;
    }

    /**
     * DB access update an existing contact in the database
     * @param contactName contact name
     * @param email email
     * @return rows affected
     */

    public static int updateContact(String contactName, String email){
        int rowsAffected = 0;
        try{
            String sql = "UPDATE contacts SET Contact_Name = ?, Email = ? WHERE Contact_Id = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, contactName);
            ps.setString(2, email);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rowsAffected;
    }
}
