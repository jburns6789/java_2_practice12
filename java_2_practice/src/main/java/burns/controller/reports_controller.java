package burns.controller;

import DBaccess.appointmentQuery;
import DBaccess.contactQuery;
import DBaccess.customerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointments;
import model.contacts;
import model.customers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * reports controller to generate data based on user input
 */
public class reports_controller implements Initializable {
    /**
     * string type combobox
     */
    public ComboBox <String> typeCb;
    /**
     *string month combobox
     */
    public ComboBox <String> monthCb;
    /**
     * type output label
     */
    public Label typeOutputLabel;
    /**
     *appointments table view
     */
    public TableView<appointments> contactDisplayView;
    /**
     *customer combox with customerId
     */
    public ComboBox<customers> customerIdCb;
    /**
     *contact combobox with contacts
     */
    public ComboBox<contacts> contactSelectCB;
    /**
     *appointmentId column
     */
    public TableColumn apptIdCol;
    /**
     *title column
     */
    public TableColumn titleCol;
    /**
     *type column
     */
    public TableColumn typeCol;
    /**
     *description column
     */
    public TableColumn descriptionCol;
    /**
     *start column
     */
    public TableColumn startCol;
    /**
     *end colmn
     */
    public TableColumn endCol;
    /**
     *customerId column
     */
    public TableColumn cusIdCol;
    /**
     * initailize contact diplay view and comboboxes
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("End"));
        cusIdCol.setCellValueFactory((new PropertyValueFactory<>("CustomerId")));
        typeCb.setItems(appointmentQuery.getTypes());
        monthCb.setItems(appointmentQuery.months);
        contactSelectCB.setItems(contactQuery.getAllContacts());
        customerIdCb.setItems((customerQuery.getAllCustomers()));
    }
    /**
     * change to the appointments screen
     * @param actionEvent button click
     * @throws IOException run time error
     */
    public void onAppointmentScreen(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/appointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 650);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * exit to the main screen
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onExit(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/main_screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * display appointments based on type and month combobox selections
     * @param actionEvent button click
     */
    public void displayAppointments(ActionEvent actionEvent) {
        String type = typeCb.getValue();
        String month = monthCb.getValue();

            if((type == null) || (month == null)) {
                typeOutputLabel.setText("No Values");
            } else {
                String answer = appointmentQuery.getMonthType(type, month);

                typeOutputLabel.setText(answer);
            }
    }
    /**
     * displays information based on the contactId selection
     * @param actionEvent button click
     */
    public void displaySchedule(ActionEvent actionEvent) {
        contacts c = contactSelectCB.getSelectionModel().getSelectedItem();
            if(c == null){
                if(c == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("");
                    alert.setContentText("Please select a Contact");
                    alert.showAndWait();
                    return;
            }
        }
        contactDisplayView.setItems(appointmentQuery.getAllAppointmentsByContact(c.getContactId()));
    }
    /**
     * displays information based on the customerId selection
     * @param actionEvent button click
     */
    public void displayCustomerIdAppt(ActionEvent actionEvent) {
        customers c = customerIdCb.getSelectionModel().getSelectedItem();
            if(c == null) {
                if (c == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("");
                    alert.setContentText("Please select an Appointment");
                    alert.showAndWait();
                    return;
                }
            }
            contactDisplayView.setItems(appointmentQuery.getAllCustomerId(c.getCustomerId()));
    }
    /**
     * this lambda will comapre a single apponitment and check to see if it belongs in the filtered list
     * @param actionEvent button click
     */
    public void onCustomerIdSelection(ActionEvent actionEvent) {
        customers c = customerIdCb.getSelectionModel().getSelectedItem();
        ObservableList<appointments> allAppointments = appointmentQuery.getAllAppointments();

        //ObservableList<appointments> flist = filterMe(allAppointments,c);

        ObservableList<appointments> flist = allAppointments.filtered(a -> {
            if(a.getCustomerId() == c.getCustomerId())
                return true;
            return false;
        } );
        contactDisplayView.setItems(flist);
    }
}