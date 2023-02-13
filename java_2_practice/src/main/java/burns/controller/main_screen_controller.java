package burns.controller;

import DBaccess.appointmentQuery;
import DBaccess.userQuery;
import database.jdbc;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.appointments;
import model.users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * main screen for selecting between appointments, customers and reports
 */
public class main_screen_controller implements Initializable {
    /**
     * user name label
     */
    public Label userName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //userName.setText(appointmentQuery.ge);
        }
    /**
     * to the appointments screen
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onAppointment(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/appointment.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 650);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * to the customer screen
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/customer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 400);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Customer");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * to the reports screen
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void onReports(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/reports.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        Node theNode = (Node) actionEvent.getSource();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
/**
 * application exit
 */
    public void onExit(ActionEvent actionEvent) {
        jdbc.closeConnection();
        System.exit(0);
    }
}