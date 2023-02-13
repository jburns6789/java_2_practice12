module burns.java_2_practice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens burns.controller to javafx.fxml;
    exports burns.controller;
    opens model to java.base;
    exports model;

}