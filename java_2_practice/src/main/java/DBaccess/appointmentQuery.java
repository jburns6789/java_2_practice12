package DBaccess;

import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointments;
import model.customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * appointment database access operation class
 */

public abstract class appointmentQuery {

    /**
     * Observable List of Strings hard coded month selection to be displayed in a combobox
     */

    public static ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    /**
     * DB access to return a list of all appointments
     * @return appointment list
     */
    public static ObservableList<appointments>getAllAppointments(){

        ObservableList<appointments>appointmentList = FXCollections.observableArrayList();

            try {
                String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID  FROM APPOINTMENTS";
                PreparedStatement ps = jdbc.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int appointmentId = rs.getInt("Appointment_ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Description");
                    String location = rs.getString("Location");
                    String type = rs.getString("Type");

                    Timestamp tsStart = rs.getTimestamp("Start");//you want the java sql library
                    LocalDateTime start = tsStart.toLocalDateTime();
                    Timestamp tsEnd = rs.getTimestamp("End");    //<-------- need to convert a time stamp to local date time
                    LocalDateTime end = tsEnd.toLocalDateTime();

                    int customerId = rs.getInt("Customer_ID");
                    int userId = rs.getInt("User_ID");
                    int contactId = rs.getInt("Contact_ID");
                    //System.out.print(appointmentId + " " + title + " " + description + " " + location + " " + type + " " + start + " " + end + " " + customerId + " " + userId + " " + contactId);

                    appointments a = new appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId); //date.toLocalDate()

                    appointmentList.add(a);
                }

            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return appointmentList;
    }

    /**
     * DB access to return a list of appointments filtered by week
     * @return appointment list
     */

    public static ObservableList<appointments>getAllAppointmentsByWeek(){

        ObservableList<appointments>appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID  FROM APPOINTMENTS WHERE YEARWEEK(start) = YEARWEEK(now())";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");//you want the java sql library
                LocalDateTime start = tsStart.toLocalDateTime();
                Timestamp tsEnd = rs.getTimestamp("End");    //<-------- need to convert a time stamp to local date time
                LocalDateTime end = tsEnd.toLocalDateTime();

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                //System.out.print(appointmentId + " " + title + " " + description + " " + location + " " + type + " " + start + " " + end + " " + customerId + " " + userId + " " + contactId);

                appointments a = new appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId); //date.toLocalDate()

                appointmentList.add(a);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * DB access creation of an appointment based on input parameters
     * @param title create appointment title
     * @param description create appointment description
     * @param location create appointment location
     * @param type create appointment type
     * @param start create appointment start
     * @param end create appointment end
     * @param customerId create appointment customerId
     * @param userId create appointment userId
     * @param contactId create appointment contactId
     * @return rows affected
     */

    public static int insertAppointment(String title, String description, String location, String type, LocalDateTime start,LocalDateTime end, int customerId, int userId, int contactId){

        int rowsAffected = 0;

        try{
            String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(NULL,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps =  jdbc.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);

            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));

            ps.setInt(7, customerId);
            ps.setInt(8,userId);
            ps.setInt(9, contactId);

            rowsAffected = ps.executeUpdate();

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access update a selected appointment based on a selection from the table view
     * @param appointment_id appointment update of appointment id
     * @param title appointment update of title
     * @param description appointment update of description
     * @param location appointment update of location
     * @param type appointment update of type
     * @param start appointment update of start
     * @param end appointment update of end
     * @param customerId appointment update of customerId
     * @param userId appointment update of userId
     * @param contactId appointment update of contactId
     * @return rows affected
     */

    public static int updateAppointment(int appointment_id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){

        int rowsAffected = 0;
        try{
            String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setInt(7, customerId);
            ps.setInt(8, userId);
            ps.setInt(9,contactId);
            ps.setInt(10, appointment_id);

            //rowsAffected = ps.executeUpdate();
            ps.execute();

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access delete based on appointment appointmentId input parameter
     * @param appointmentId delete from the database
     * @return rows affected
     */

    public static int deleteAppointment(int appointmentId){
        int rowsAffected = 0;
        try{
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setInt(1, appointmentId);
            rowsAffected = ps.executeUpdate();

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access delete an appointment based on customerId parameter
     * @param customerId delete based on the customerId
     * @return rows affected
     */

    public static int deleteAppointmentByCustomer(int customerId) {
        int rowsAffected = 0;
        try {
            String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access to return a List based on types
     * @return types
     */

    public static ObservableList<String> getTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();

        try{
            String sql = "SELECT distinct Type FROM client_schedule.appointments";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String type = rs.getString("type");
                types.add(type);
            }

        }catch(SQLException ex) {
            ex.printStackTrace();
        }

        return types;
    }

    /**
     * DB access populating a table view with selections based on parameter input of type and month
     * @param type appointment type
     * @param month appointment month
     * @return 0
     */

    public static String getMonthType(String type, String month) {

        try{
            String sql = "SELECT count(*) as total FROM client_schedule.appointments WHERE Type = ? and monthname(Start) = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1,type);
            ps.setString(2,month);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("total");

        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    /**
     * DB access observable list of appointments based on parameter input of a contactId
     * @param contactId appointment contactId
     * @return appointmentList
     */

    public static ObservableList<appointments> getAllAppointmentsByContact(int contactId) {

        ObservableList<appointments>appointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID  FROM APPOINTMENTS WHERE Contact_Id = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setInt(1,contactId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");//you want the java sql library
                LocalDateTime start = tsStart.toLocalDateTime();
                Timestamp tsEnd = rs.getTimestamp("End");    //<-------- need to convert a time stamp to local date time
                LocalDateTime end = tsEnd.toLocalDateTime();

                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactIdx = rs.getInt("Contact_ID");
                //System.out.print(appointmentId + " " + title + " " + description + " " + location + " " + type + " " + start + " " + end + " " + customerId + " " + userId + " " + contactIdx);

                appointments a = new appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactIdx); //date.toLocalDate()

                appointmentList.add(a);
            }

        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return appointmentList;

    }

    /**
     * DB access observable list of appointments based on parameter input of a customerId
     * @param customerId customerId
     * @return customerIdList
     */

    public static ObservableList<appointments> getAllCustomerId(int customerId) {

        ObservableList<appointments> customerIdList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID  FROM APPOINTMENTS WHERE Contact_Id = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setInt(1,customerId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");

                Timestamp tsStart = rs.getTimestamp("Start");//you want the java sql library
                LocalDateTime start = tsStart.toLocalDateTime();
                Timestamp tsEnd = rs.getTimestamp("End");    //<-------- need to convert a time stamp to local date time
                LocalDateTime end = tsEnd.toLocalDateTime();

                int customerIdx = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactIdx = rs.getInt("Contact_ID");
                //System.out.print(appointmentId + " " + title + " " + description + " " + location + " " + type + " " + start + " " + end + " " + customerId + " " + userId + " " + contactIdx);

                appointments a = new appointments(appointmentId, title, description, location, type, start, end, customerIdx, userId, contactIdx); //date.toLocalDate()

                customerIdList.add(a);
            }
        } catch(SQLException ex){
                ex.printStackTrace();
            }
            return customerIdList;
    }
}
