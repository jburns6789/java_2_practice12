package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * customers class
 */

public class customers {

    private int customerId; //auto generated
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int stateProvidence; //needs to be combox
    private int country; //needs to be combox

    /**
     *
     * @param customerId customerid
     * @param name name
     * @param address address
     * @param postalCode postal Code
     * @param phoneNumber phone number
     * @param stateProvidence state / providence
     * @param country country
     */

    public customers(int customerId, String name, String address, String postalCode, String phoneNumber, int stateProvidence, int country) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.stateProvidence = stateProvidence;
        this.country = country;
    }

    /**
     * returns customerId
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     * return name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     *return address
     * @return address
     */
    public String getAddress() {
        return address;
    }
    /**
     *return postalCode
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }
    /**
     *return phone number
     * @return phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     *return state or providence
     * @return stateProvidence
     */
    public int getStateProvidence() {return stateProvidence;}
    /**
     *return country
     * @return country
     */
    public int getCountry() {return country;}
    /**
     *toString method to translate data into a readable form
     * @return translated data
     */
    @Override
    public String toString(){
        return(Integer.toString(customerId) + " - " + name);
    }

}
