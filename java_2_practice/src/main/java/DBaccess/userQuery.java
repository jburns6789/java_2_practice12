package DBaccess;

import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DB access userQuery class
 */

public abstract class userQuery {

    /**
     * DB access return a list of all users
     * @return user list
     */

    public static ObservableList<users>getAllUsers(){

        ObservableList<users>userList = FXCollections.observableArrayList();

            try{
                String sql = "SELECT User_ID, User_Name, Password FROM users";
                PreparedStatement ps = jdbc.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int userId = rs.getInt("User_ID");
                    String userName = rs.getString("User_Name");
                    String password = rs.getString("Password");

                    System.out.print(userId + " " + userName + " " + password);

                    users u = new users(userId, userName, password);

                    userList.add(u);
                }

            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return userList;
    }

    /**
     * DB access insert a user into the database
     * @param userName username
     * @return rows affected
     */

    public static int insertUser(String userName){
        int rowsAffected = 0;
        try{
            String sql = "INSERT INTO users (user_ID, User_Name) VALUES(NULL,?)";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, userName);

            rowsAffected = ps.executeUpdate();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access update user based on string parameter input
     * @param userName username
     * @return rows affected
     */

    public static int updateUser(String userName){
        int rowsAffected = 0;

        try{
            String sql = "UPDATE users SET User_Name = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1,userName);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rowsAffected;
    }
}
