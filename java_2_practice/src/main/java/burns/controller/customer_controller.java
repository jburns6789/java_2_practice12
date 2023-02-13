package burns.controller;

import DBaccess.appointmentQuery;
import DBaccess.customerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.customers;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * customer controller class to display customers and switch screens based on customer or null selection
 */
public class customer_controller implements Initializable {
    /**
     * customer table view
     */
    public TableView<customers>customerTable;
    /**
     *customer id column
     */
    public TableColumn cusIdCol;
    /**
     * customer name column
     */
    public TableColumn cusNameCol;
    /**
     *customer address column
     */
    public TableColumn cusAddressCol;
    /**
     *customer postal code column
     */
    public TableColumn cusPostalCodeCol;
    /**
     *customer phone number column
     */
    public TableColumn cusPhoneCol;
    /**
     * customer state/providence column
     */
    public TableColumn cusStateProvidenceCol;
    /**
     *customer country column
     */
    public TableColumn cusCountryCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cusIdCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        cusNameCol.setCellValueFactory((new PropertyValueFactory<>("Name")));
        cusAddressCol.setCellValueFactory((new PropertyValueFactory<>("Address")));
        cusPostalCodeCol.setCellValueFactory((new PropertyValueFactory<>("PostalCode")));
        cusPhoneCol.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        cusStateProvidenceCol.setCellValueFactory(new PropertyValueFactory<>("StateProvidence"));
        cusCountryCol.setCellValueFactory(new PropertyValueFactory<>("Country"));

        customerTable.setItems(customerQuery.getAllCustomers());
    }
    /**
     * method for selection of a customer to be passed to the add modify controller or null selection
     * to add a new customer
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onAddModify(ActionEvent actionEvent) throws IOException{
        customers customerPass = customerTable.getSelectionModel().getSelectedItem();
            if(customerPass == null){
                FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/customer_add_modify.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 600);
                Node theNode = (Node) actionEvent.getSource();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Add Customer");
                stage.setScene(scene);
                stage.show();
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/customer_add_modify.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 600);

                customer_add_modify_controller cc = fxmlLoader.getController();
                cc.setCustomer(customerPass);

                Node theNode = (Node) actionEvent.getSource();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Modify Customer");
                stage.setScene(scene);
                stage.show();
            }
    }
    /**
     * onCancel return the main screen
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/main_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * onClear the selected item to change between add and modify options
     * @param actionEvent button click
     */
    public void onClearSelection(ActionEvent actionEvent) {
        customerTable.getSelectionModel().clearSelection();
    }
    /**
     * onDelete customer, order is important the satisfy DB requirements
     * @param actionEvent button click
     */
    public void onDeleteCustomer(ActionEvent actionEvent) {

        customers selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if(selectedCustomer == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Please Select a Customer");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to delete? Action cannot be undone.");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                appointmentQuery.deleteAppointmentByCustomer(selectedCustomer.getCustomerId());
                customerQuery.deleteCustomer(selectedCustomer.getCustomerId());
                //customers.deleteCustomer(selectedCustomer);
                customerTable.setItems(customerQuery.getAllCustomers());//reloading from the database
                Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                alert1.setTitle("Deleted");
                alert1.setHeaderText("");
                alert1.setContentText("Customer Id " + selectedCustomer.getCustomerId() + " has been deleted");
                alert1.showAndWait();
            }
        }
    }
