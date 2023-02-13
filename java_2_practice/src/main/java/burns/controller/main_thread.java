package burns.controller;

import database.JDBC2;
import database.jdbc;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**
 * main thread
 */
public class main_thread extends Application {
    /**
     * initial screen
     * @param stage stage
     * @throws IOException run time error
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main_thread.class.getResource("/burns/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * main method
     * @param args args
     * @throws SQLException mapping error
     */
    public static void main(String[] args) throws SQLException {
        jdbc.openConnection();
        launch();
        jdbc.closeConnection();
    }
}