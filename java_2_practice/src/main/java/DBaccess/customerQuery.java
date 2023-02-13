package DBaccess;

import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointments;
import model.customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB access customer class
 */

public abstract class customerQuery {

    /**
     * DB access return Observable List of customers
     * @return customer list
     */
    public static ObservableList<customers>getAllCustomers(){

        ObservableList<customers> customerList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT customer_id, customer_name, address, Postal_Code, Phone, CUSTOMERS.Division_ID, Country_ID from CUSTOMERS, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                int country = rs.getInt("Country_ID");
                System.out.print(customerId + " | " + customerName + " | " + address + " | " + postalCode + " | " + phone + " | " + divisionId + " | " + country);

                customers c = new customers(customerId, customerName, address, postalCode, phone, divisionId, country);

                customerList.add(c);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }

        return customerList;
    }

    /**
     * DB access create a customer based on parameter input
     * @param customerName customer name
     * @param address address
     * @param postalCode postal code
     * @param phone phone
     * @param divisionId didvsionid
     * @return rows affected
     */

    public static int insertCustomer(String customerName, String address, String postalCode, String phone, int divisionId) {

        int rowsAffected = 0;

        try {
            String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID)  VALUES(NULL,?,?,?,?,?)";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);

            rowsAffected = ps.executeUpdate();

            /*
            Return keys to add to the inserted colum of the created customers object
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int customerId = rs.getInt(1);

            mySQL
            String sql = "insert into customers VALUES(NULL, ?,?,?,?,?);
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);

            rowsAffected = ps.executeUpdate();
             */

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access update a selected customer based on parameter input
     * @param customerName customer name
     * @param address address
     * @param postalCode postal code
     * @param phone phone
     * @param divisionId divsionId
     * @param customerId customerId
     * @return rows affected
     */

    public static int updateCustomer(String customerName, String address, String postalCode, String phone, int divisionId, int customerId) {

        int rowsAffected = 0;

        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionId);
            ps.setInt(6,customerId);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * DB access delete a customer based on parameter input  of a customerId
     * @param customerId customer id
     * @return rows affected
     */

    public static int deleteCustomer(int customerId) {
        int rowsAffected = 0;
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            rowsAffected = ps.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rowsAffected;

        //delete apt and delete customer
    }

    /*

    public static void selectCustomers() throws SQLException {
        String sql = "SELECT * customers";
        PreparedStatement ps = jdbc.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int divsionId = rs.getInt("Division_ID");
            System.out.print(customerId + " | " + customerName + " | " + address + " | " + postalCode + " | " + phone + " | " + divsionId);

        }

        //select all customers with the same divsionId

        public static void select(int divisionId) throws SQLException {
            String sql = "SELECT * FROM customers WHERE Division_ID = ?";
            PreparedStatement ps = jdbc.connection.prepareStatement(sql);
            ps.setInt(1, divisionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int divisionId =  rs.getInt("Division_ID");
                System.out.print(divisionId + " | " + "\n");
            }
        }
        */
    }
