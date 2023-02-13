package burns.controller;

import DBaccess.appointmentQuery;
import DBaccess.userQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.appointments;
import model.users;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * login to the application, monitor failed and successful logins, display messages of pending appointments and resource
 * bundles
 */
public class login_controller implements Initializable {
    /**
     * user text field
     */
    public TextField userTf;
    /**
     *password text field
     */
    public TextField passwordTf;
    /**
     *defalut timezone label
     */
    public Label defaultTimeZone;
    /**
     *scheduler label
     */
    public Label schedulerLabel;
    /**
     *userLabel
     */
    public Label userLabel;
    /**
     *password Label
     */
    public Label passwordLabel;
    /**
     *login button
     */
    public Button loginButton;
    /**
     *exit button
     */
    public Button exitButton;
    /**
     * get local timezone to display in the login screen
     */
    Locale locale = Locale.getDefault();
    /**
     * resource bundle based on the machines default settings
     */
    ResourceBundle resourcebundle = ResourceBundle.getBundle("language/language",locale);
    /**
     * initialize the resouce bundles based on the machines default settings
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defaultTimeZone.setText(ZoneId.systemDefault().toString());//put string in a label
        schedulerLabel.setText(resourcebundle.getString("SCHEDULER"));
        userLabel.setText(resourcebundle.getString("USER"));
        passwordLabel.setText(resourcebundle.getString("PASSWORD"));
        loginButton.setText(resourcebundle.getString("LOGON_BUTTON"));
        exitButton.setText(resourcebundle.getString("EXIT_BUTTON"));
    }
    /**
     * login method and records of attempts stored in login_activity.txt
     * @param actionEvent button click
     * @throws IOException runtime error
     */
    public void login(ActionEvent actionEvent) throws IOException {
        String userInput = userTf.getText();
        String passwordInput = passwordTf.getText();
        for (users u : userQuery.getAllUsers()) {
            if ((userInput.equals(u.getUserName())) && (passwordInput.equals(u.getPassword()))) {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.hide();
                boolean found = false;
                for (appointments a : appointmentQuery.getAllAppointments()) {
                    if (a.getUserId() == u.getUserId()) {
                        LocalDateTime startTime = a.getStart();
                        LocalDateTime currentTime = LocalDateTime.now();
                        long timeDifference = ChronoUnit.MINUTES.between(currentTime, startTime);
                        long countDown = timeDifference;
                        //System.out.println(countDown);
                        if (countDown > 0 && countDown <= 15) {
                            found = true;
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Waring");
                            alert.setHeaderText("");
                            alert.setContentText("You have an appointment with " + a.getAppointmentId() + " at " + a.getStart() + " in " + countDown + " minute(s)");
                            alert.showAndWait();
                        }
                    }
                }
                if(!found){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("");
                    alert.setContentText("There are no appointments in the next 15 minutes");
                    alert.showAndWait();
                }
                LocalDateTime loginTs = LocalDateTime.now();
                System.out.println("Login Successful");
                String successfulLogin = "login_activity.txt";
                FileWriter fwriter = new FileWriter(successfulLogin, true);
                PrintWriter loginOutputS = new PrintWriter(fwriter);
                String sl = (u.getUserName() + " had a successful Login on " + loginTs);
                loginOutputS.println(sl);
                loginOutputS.flush();
                loginOutputS.close();
                System.out.println("Successful, File Written");

                FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/main_screen.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 600);
                Node theNode = (Node) actionEvent.getSource();
                stage.setTitle("Main Screen");
                stage.setScene(scene);
                stage.show();
                return;
            }
        }

        FileWriter fwriter = new FileWriter("login_activity.txt", true);
            PrintWriter loginOutputF = new PrintWriter(fwriter);

            LocalDateTime loginF = LocalDateTime.now();

            String ul = (userTf.getText() + "attempted to login " + loginF); //add user and time
            loginOutputF.println(ul);
            loginOutputF.flush();
            loginOutputF.close();
            System.out.println("Login Failure, File Written");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");//translate
            alert.setHeaderText("");
            alert.setContentText("Incorrect username or password");
            alert.setContentText(resourcebundle.getString("ERROR_MESSAGE"));//translate
            alert.showAndWait();
    }
    /**
     * onExit close the application
     * @param actionEvent button click
     */
    public void closeProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
