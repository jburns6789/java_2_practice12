package model;

/**
 * users class
 */
public class users {
    private int userId;
    private String userName;
    private String password;

    /**
     *
     * @param userId userId
     * @param userName userName
     * @param password password
     */

    public users(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * return userId
     * @return userId
     */
    public int getUserId() {return userId;}

    /**
     * return username
     * @return userName
     */
    public String getUserName() {return userName;}
    /**
     * return password
     * @return password
     */
    public String getPassword() {return password;}
    /**
     *toString method to translate userId and userName to a string
     * @return translated data
     */
    @Override
    public String toString() {return(userId + " " + userName);}
}
