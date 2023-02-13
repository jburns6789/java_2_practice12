package burns.controller;

import DBaccess.appointmentQuery;
import database.jdbc;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * appointment controller class to display appointments, appointment filtering and determining if appointments
 * need to be added or modified
 */

public class appointment_controller implements Initializable {
    /**
     * appointment tableview
     */
    public TableView<appointments>appointmentTableView;
    /**
     *appointment table id
     */
    public TableColumn apptIdCol;
    /**
     *appointment table title
     */
    public TableColumn apptTitleCol;
    /**
     *appointment table description
     */
    public TableColumn apptDescriptionCol;
    /**
     *appointment table location
     */
    public TableColumn apptLocationCol;
    /**
     *appointment table contact
     */
    public TableColumn apptContactCol;
    /**
     *appointment table type
     */
    public TableColumn apptTypeCol;
    /**
     *appointment table start date
     */
    public TableColumn apptStartDateCol;
    /**
     *appointment table end date
     */
    public TableColumn apptEndDateCol;
    /**
     *appointment table customer
     */
    public TableColumn apptCustomerCol;
    /**
     *appointment table userId
     */
    public TableColumn apptUserIdCol;
    /**
     * radio button display group
     */
    public ToggleGroup scheduleDisplay;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         apptIdCol.setCellValueFactory(new PropertyValueFactory<>("AppointmentId"));
         apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
         apptDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
         apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
         apptContactCol.setCellValueFactory(new PropertyValueFactory<>("ContactId"));
         apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
         apptStartDateCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
         apptEndDateCol.setCellValueFactory((new PropertyValueFactory<>("End")));
         apptCustomerCol.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
         apptUserIdCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));

         appointmentTableView.setItems(appointmentQuery.getAllAppointments());
    }
    /**
     * on cancel to return to the main screen
     * @param actionEvent button click
     * @throws IOException incorrect path
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
     * change screens from appointments to the reports screen
     * @param actionEvent button click
     * @throws IOException incorrect path
     */
    public void onCreateReports(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/reports.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * method will pass data based on a table view selection, if a selection is made the data is passed into the modify screen and prepopulated
     * if the selection in null the the input field are blank
     * @param actionEvent button click
     * @throws IOException incorrect path
     */
    public void onAddModify(ActionEvent actionEvent) throws IOException {
        appointments appointmentPass = appointmentTableView.getSelectionModel().getSelectedItem();
            if(appointmentPass == null){
                FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/appointment_add_modify.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 850);
                Node theNode = (Node) actionEvent.getSource();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Add/ Modify Appointments");
                stage.setScene(scene);
                stage.show();
        }
        else{
                FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/appointment_add_modify.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 850);

                appointment_add_modify_controller ac = fxmlLoader.getController();
                ac.setAppointment(appointmentPass); // have to create setAppointment method in appointment_add_modify_controller

                Node theNode = (Node) actionEvent.getSource();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Add/ Modify Appointments");
                stage.setScene(scene);
                stage.show();
        }
    }
    /**
     * delete appointments based on table selection
     * @param actionEvent button click
     */
    public void onDeleteAppointment(ActionEvent actionEvent) {
        appointments selectedAppointments = appointmentTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointments == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Please Select an Appointment");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to delete? Action cannot be undone.");
        Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Alert alertD = new Alert(Alert.AlertType.CONFIRMATION);
                alertD.setTitle("Deleted");
                alertD.setHeaderText("");
                alertD.setContentText("Appointment Id " + selectedAppointments.getAppointmentId() + " and " + selectedAppointments.getType() + " has been Deleted");
                alertD.showAndWait();

                appointmentQuery.deleteAppointment(selectedAppointments.getAppointmentId());
                appointmentTableView.setItems(appointmentQuery.getAllAppointments());
            }
    }
    /**
     * method clears selected item to change from a modify case to and add case
     * @param actionEvent button click
     */
    public void onClearSelection(ActionEvent actionEvent) {appointmentTableView.getSelectionModel().clearSelection();}
    /**
     * radio button filter to display all cases
     * @param actionEvent button selection
     */
    public void allApptRb(ActionEvent actionEvent) {
        appointmentTableView.setItems(appointmentQuery.getAllAppointments()); }
    /**
     * radio button filter to display appointments by months
     * @param actionEvent button selection
     */
    public void monthApptRb(ActionEvent actionEvent) { //current month, lambda opportunity, this method relies more on computer code.
        ObservableList<appointments> months = FXCollections.observableArrayList();
        ObservableList<appointments> appointmentsList = appointmentQuery.getAllAppointments();

           for(appointments a : appointmentsList) {
               if(a.getStart().getMonth() == LocalDateTime.now().getMonth()){
                   months.add(a);
               }
           }
           //LocalDateTime months = appointmentsList.get
        appointmentTableView.setItems(months);
    }

    /**
     * radio button filter to display appointments by week
     * @param actionEvent button selection
     */
    public void weekApptRb(ActionEvent actionEvent) {//this method relies more on the database hardware, another sql call method is written
        appointmentTableView.setItems(appointmentQuery.getAllAppointmentsByWeek());
    }
}