package burns.controller;

import DBaccess.appointmentQuery;
import DBaccess.contactQuery;
import DBaccess.customerQuery;
import DBaccess.userQuery;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * appointment add modify controller class purpose to determine if data is present based on a selection from the table view,
 * if present the appointment will be modified based on the selection. If the selection is empty then a new appointment is
 * added to the database.
 */
public class appointment_add_modify_controller implements Initializable {
    /**
     *appointment id text field
     */
    public TextField addApptIdTf;
    /**
     *appointment title text field
     */
    public TextField addApptTitleTf;
    /**
     *appointment description text field
     */
    public TextField addApptDescriptionTf;
    /**
     *appointment location text field
     */
    public TextField addApptLocationTf;
    /**
     *appointment type text field
     */
    public TextField addApptTypeTf;
    /**
     *appointment contact combobox
     */
    public ComboBox<contacts> addApptContactNameCb;
    /**
     *appointment datepicker
     */
    public DatePicker addApptStartDatePicker;
    /**
     *appointment customerId combobox
     */
    public ComboBox<customers> addApptCustomerIdCb;
    /**
     *appointment userId combobox
     */
    public ComboBox<users> addApptUserIdCb;
    /**
     *appointment start time combobox
     */
    public ComboBox<LocalTime> apptAddStartTimeCb;
    /**
     *appoinment end time combobox
     */
    public ComboBox<LocalTime> addApptEndTimeCb;
    /**
     *appointment data attribute
     */
    public appointments appointmentPass = null;

    /**
     *initialize method to set values for the comboboxes and to set the timezones based on the
     * machines physical location
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addApptStartDatePicker.setValue(LocalDate.now());

        addApptContactNameCb.setPromptText("Please select a Contact");
        addApptContactNameCb.setItems(contactQuery.getAllContacts());

        addApptCustomerIdCb.setPromptText("Please select a Customer");
        addApptCustomerIdCb.setItems(customerQuery.getAllCustomers());

        addApptUserIdCb.setPromptText("Please select a User");
        addApptUserIdCb.setItems(userQuery.getAllUsers()); // fill in the db access for users

        LocalTime st = LocalTime.of(8, 0);
        LocalDateTime ldtSt = LocalDateTime.of(LocalDate.now(), st);// local machine time
        //combining localtime and date for a localdate time to move forward

        ZoneId etz = ZoneId.of("America/New_York");
        //easternTime zone

        ZonedDateTime ldtStEz = ldtSt.atZone(etz);//setting to eastern time zone from localDateTime to ZoneDateTime
        ZonedDateTime ldtLocalZone = ldtStEz.withZoneSameInstant(ZoneId.systemDefault());
        //zone for the machine
        st = ldtLocalZone.toLocalTime();
        //end of setting local start time
        LocalTime et = LocalTime.of(22, 0);
        LocalDateTime ldtEnd = LocalDateTime.of(LocalDate.now(), et);

        ZonedDateTime ldtzEnd = ldtEnd.atZone(etz);
        ZonedDateTime ldteLocaLZone = ldtzEnd.withZoneSameInstant(ZoneId.systemDefault());
        et = ldteLocaLZone.toLocalTime();

        apptAddStartTimeCb.setPromptText(st.toString());
        addApptEndTimeCb.setPromptText(et.toString());
        while (st.isBefore(et.plusSeconds(0))) {
            apptAddStartTimeCb.getItems().add(st);
            st = st.plusMinutes(15);
        }

        LocalTime St = LocalTime.of(8, 0);
        LocalDateTime ldtSt2 = LocalDateTime.of(LocalDate.now(), St);

        ZonedDateTime ldtStEz2 = ldtSt2.atZone(etz);
        ZonedDateTime ldtLocalZone2 = ldtStEz2.withZoneSameInstant(ZoneId.systemDefault());

        St = ldtLocalZone2.toLocalTime();

        LocalTime Et = LocalTime.of(22, 0);
        LocalDateTime ldtEnd2 = LocalDateTime.of(LocalDate.now(), Et);
        ZonedDateTime ldtzEnd2 = ldtEnd2.atZone(etz);
        ZonedDateTime ldtEndLocalZone2 = ldtzEnd2.withZoneSameInstant(ZoneId.systemDefault());

        Et = ldtEndLocalZone2.toLocalTime();

        apptAddStartTimeCb.setPromptText(st.toString());
        addApptEndTimeCb.setPromptText(Et.toString());

        while (St.isBefore(Et.plusSeconds(0))) {
            addApptEndTimeCb.getItems().add(St);
            St = St.plusMinutes(15);
        }
    }
    /**
     * save method. Data validation and time checking to ensure no appointment time overlaps
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onSave(ActionEvent actionEvent) throws IOException {
        String title = addApptTitleTf.getText();
        if (title.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Must enter a Title");
            alert.showAndWait();
            return;
        }
        String description = addApptDescriptionTf.getText();
        if (description.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Must enter a Description");
            alert.showAndWait();
            return;
        }
        String location = addApptLocationTf.getText();
        if (location.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Must enter a Location");
            alert.showAndWait();
            return;
        }
        String type = addApptTypeTf.getText();
        if (type.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Must enter the Type of Appointment");
            alert.showAndWait();
            return;
        }
        LocalTime startTime = apptAddStartTimeCb.getValue();
        LocalTime endTime = addApptEndTimeCb.getValue();
        LocalDate date = addApptStartDatePicker.getValue();
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        if (startDateTime.isAfter(endDateTime)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Start Time must be before End Time");
            alert.showAndWait();
            return;
        }
        LocalDateTime proSt = startDateTime;// from user input;
        LocalDateTime proEt = endDateTime;

        //LocalDateTime cst = apptTimeCheck.getStart();
        //LocalDateTime cet = apptTimeCheck.getEnd();//trying to check against matching customer ids

        if(addApptCustomerIdCb.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Please select a Customer Id");
            alert.showAndWait();
            return;
        }
        customers customer = addApptCustomerIdCb.getValue();
        if(addApptUserIdCb.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Please select a User Id");
            alert.showAndWait();
            return;
        }
        users user = addApptUserIdCb.getValue();

        if(addApptContactNameCb.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("");
            alert.setContentText("Please select a Contact");
            alert.showAndWait();
            return;
        }
        contacts contact = addApptContactNameCb.getValue();

        for (appointments a : appointmentQuery.getAllAppointments()) {
            if (appointmentPass != null && a.getAppointmentId() == appointmentPass.getAppointmentId()) {
                continue;
            }
            if (a.getCustomerId() == addApptCustomerIdCb.getValue().getCustomerId()) {
                LocalDateTime cst = a.getStart();
                LocalDateTime cet = a.getEnd();
                if ((proSt.isAfter(cst) || proSt.isEqual(cst)) && proSt.isBefore(cet)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("");
                    alert.setContentText("Unable to select time(s) per appointment overlap");
                    alert.showAndWait();
                    return;  //start
                } else if (proEt.isAfter(cst) && (proEt.isBefore(cet) || proEt.isEqual(cet))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("");
                    alert.setContentText("Unable to select time(s) per appointment overlap");
                    alert.showAndWait();
                    return; //end
                } else if ((proSt.isBefore(cst) || proSt.isEqual(cst)) && (proEt.isAfter(cet) || proEt.isEqual(cet))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("");
                    alert.setContentText("Unable to select time(s) per appointment overlap");
                    alert.showAndWait();
                    return; //in between
                }
            }
        }

        if(appointmentPass == null){
            appointmentQuery.insertAppointment(title, description, location, type, startDateTime, endDateTime, customer.getCustomerId(), user.getUserId(), contact.getContactId());
        }
        else{
            appointmentQuery.updateAppointment(appointmentPass.getAppointmentId(), title, description, location, type, startDateTime, endDateTime, customer.getCustomerId(), user.getUserId(), contact.getContactId());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/appointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 650);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * method to transfer data from the selected object to prefill fields
     * @param appointment appointment data from selected object in the customer screen
     */
    public void setAppointment(appointments appointment) {

        appointmentPass = appointment;

        String id = String.valueOf(appointmentPass.getAppointmentId());
        String title = String.valueOf(appointmentPass.getTitle());
        String description = String.valueOf(appointmentPass.getDescription());
        String location = String.valueOf(appointmentPass.getLocation());
        String type = String.valueOf(appointmentPass.getType());

        int customer = appointmentPass.getCustomerId();
        int contact = appointmentPass.getContactId();
        int user = appointmentPass.getUserId();
        LocalDateTime start = appointmentPass.getStart();
        LocalDateTime end = appointmentPass.getEnd();
        LocalDateTime date = appointmentPass.getStart();

        addApptIdTf.setText(id);
        addApptTitleTf.setText(title);
        addApptDescriptionTf.setText(description);
        addApptLocationTf.setText(location);
        addApptTypeTf.setText(type);
        apptAddStartTimeCb.setValue(LocalTime.from(start));
        addApptEndTimeCb.setValue(LocalTime.from(end));
        addApptStartDatePicker.setValue(LocalDate.from(date));

        for(customers c :  addApptCustomerIdCb.getItems()){
            if(customer == c.getCustomerId()){
                addApptCustomerIdCb.setValue(c);
                    break;
            }
        }

        for(contacts c : addApptContactNameCb.getItems()){
            if (contact == c.getContactId()){
                addApptContactNameCb.setValue(c);
                    break;
            }
        }
        for(users u : addApptUserIdCb.getItems()){
            if(user == u.getUserId()){
                addApptUserIdCb.setValue(u);
                    break;
            }
        }

    }
    /**
     * cancel to return to the appointment screen
     * @param actionEvent button click
     * @throws IOException incorrect path
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/appointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 650);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
}