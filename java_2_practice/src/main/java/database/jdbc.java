package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * java database connection class
 */

public abstract class jdbc {
    //jdbc url parts
    /**
     * define protocol
     */
    private static final String protocol = "jdbc";
    /**
     *define the vendor
     */
    private static final String vendor = ":mysql:";
    /**
     *determine the location
     */
    private static final String location = "//localhost/";//(database hosted on the VM)
    /**
     *define database name
     */
    private static final String databaseName = "client_schedule";//name of the database
    /**
     *create the jdbc url
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    //concatenate to create the jdbc url
    /**
     *driver reference
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     *userName
     */
    private static final String userName = "sqlUser"; // Username
    /**
     *password
     */
    private static final String password = "Passw0rd!"; // Password
    /**
     *connection interface
     */
    public static Connection connection;  // Connection Interface

    /**
     * open connection method called when the application is opened
     * @return db connection
     */

    public static Connection openConnection(){
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(SQLException e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
           e.printStackTrace();
        }
        return connection;

    }

   // public static Connection getConnection(){
        //return connection;
   // }

    /**
     * close connection when the application is exited
     */

    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
            //do nothing
        }
    }

}
