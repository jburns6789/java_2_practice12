package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * data retrival class
 */

public class dataRetrieval {

    /**
     * Observale List of customers
     */
    private static ObservableList<customers> allCustomers = FXCollections.observableArrayList();

    /**
     * return an observable list of customers
     * @return all customers list
     */
    public static ObservableList<customers>getAllCustomers(){
        return allCustomers;
    }
}
