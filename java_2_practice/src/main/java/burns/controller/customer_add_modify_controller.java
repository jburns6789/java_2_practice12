package burns.controller;

import DBaccess.countryQuery;
import DBaccess.customerQuery;
import DBaccess.divisionQuery;
import database.jdbc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.countries;
import model.customers;
import model.division;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static DBaccess.divisionQuery.getAllDivisions;

/**
 * customer add modify class customers are updated or created based on data passed from the customer controller
 */
public class customer_add_modify_controller implements Initializable {
    /**
     * customer country combobox
     */
    public Label countryCB;
    /**
     *customer id text field
     */
    public TextField customerIdTf;
    /**
     *customer name text field
     */
    public TextField cusNameTf;
    /**
     *customer address text field
     */
    public TextField cusAddressTf;
    /**
     *customer postal code text field
     */
    public TextField cusPostalCodeTf;
    /**
     *customer phone number text field
     */
    public TextField cusPhoneNumberTf;
    /**
     *customer state combobox
     */
    public ComboBox<division> cusStateCb;
    /**
     *customer country combobox
     */
    public ComboBox<countries> cusCountryCb;
    /**
     *customer data pass
     */
    public customers customerPass = null;
    /**
     * implement general interface to change screens
     */
    public generalInterface gI = (s, a, scene) -> {
        Stage stage = (Stage) ((Node) a.getSource()).getScene().getWindow();
        stage.setTitle(s);
        stage.setScene(scene);
        stage.show();
    };
    /**
     * combobox initialized to have countries being selectable
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //cusStateCb.setItems(divisionQuery.getAllDivisions());
        cusCountryCb.setItems(countryQuery.getAllCountries());
    }
    /**
     * on save with data validation and customer object creation
     * @param actionEvent button click
     * @throws IOException runtime error
     * @throws SQLException sql error
     */
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        String name = cusNameTf.getText();
            if(name.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Field cannot be blank");
                alert.showAndWait();
                return;
            }
        String address = cusAddressTf.getText();
            if(address.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Field cannot be blank");
                alert.showAndWait();
                return;
            }
        String postalCodeS = cusPostalCodeTf.getText();
            if(postalCodeS.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Field cannot be blank");
                alert.showAndWait();
                return;
            }
            int postalCode = 0;
            try{
                postalCode = Integer.parseInt(postalCodeS);
            }catch(NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Field must contain a valid postal code");
                alert.showAndWait();
                return;
            }
        String phoneS = cusPhoneNumberTf.getText();
            if(phoneS.isBlank()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Field cannot be blank");
                alert.showAndWait();
                return;
            }
            int phoneNumber = 0;
            try{
                phoneNumber = Integer.parseInt(phoneS);
            }catch(NumberFormatException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Field must contain a valid phone number");
                alert.showAndWait();
                return;
            }

        division stateProvidence = cusStateCb.getValue();

            if(stateProvidence == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Please select a state");
                alert.showAndWait();
                return;
            }

        int divisionId = stateProvidence.getDivisionId();

        if(customerPass == null){
            customerQuery.insertCustomer(name, address, postalCodeS, phoneS, divisionId);
        }
        else{
            customerQuery.updateCustomer(name, address, postalCodeS, phoneS, divisionId, customerPass.getCustomerId());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/customer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 400);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * using lambda here to change screens
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/customer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 400);

        gI.changeScreens("Main", actionEvent, scene);
    }

    /**
     * setCusotmer method customer object selection based on the customer data passed from the customer controller
     * @param customer customer
     */
    public void setCustomer (customers customer){ //from the customer screen method to pass the football and populate the input fields

        customerPass = customer;

        String id = String.valueOf(customerPass.getCustomerId());
        String name = String.valueOf(customerPass.getName());
        String address = String.valueOf(customerPass.getAddress());
        String postalCode = String.valueOf(customerPass.getPostalCode());
        String phone = String.valueOf(customerPass.getPhoneNumber());
        int country = customerPass.getCountry();
        int stateProvidence = customerPass.getStateProvidence();

        customerIdTf.setText(id);
        cusNameTf.setText(name);
        cusAddressTf.setText(address);
        cusPostalCodeTf.setText(postalCode);
        cusPhoneNumberTf.setText(phone);

        for(countries c : cusCountryCb.getItems()){
            if(country == c.getCountryId()){
                cusCountryCb.setValue(c);
                    break;
            }
        }

        countries c = cusCountryCb.getValue();
        cusStateCb.setItems(divisionQuery.getAllDivisions(c.getCountryId()));
        for(division d : cusStateCb.getItems()){

            if(stateProvidence == d.getDivisionId()){
                cusStateCb.setValue(d);
                break;
            }
        }
    }
    /**
     * onCountry method selection in the combox will prepopulate the state/providence box w/ the matching divisons
     * @param actionEvent button click
     */
    public void onCountry(ActionEvent actionEvent) {
        //selecting the combobox is an onAction event, when selected the state CBox is populated w/ the correct country
        countries C = cusCountryCb.getValue();
        System.out.println(C.getCountryId());
        cusStateCb.setItems(divisionQuery.getAllDivisions(C.getCountryId()));
    }
}