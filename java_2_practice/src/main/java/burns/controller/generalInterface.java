package burns.controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;

/**
 * functional interface to be implemented in the customer_add_modify_controller
 */
@FunctionalInterface //explains that it is a functional interface
public interface generalInterface {
    /**
     * @param title enter title of screen
     * @param actionEvent button cllick
     * @param scene scence
     */
    void changeScreens (String title, ActionEvent actionEvent, Scene scene);
}
