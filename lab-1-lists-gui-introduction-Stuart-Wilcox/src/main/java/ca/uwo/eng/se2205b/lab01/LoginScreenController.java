package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class LoginScreenController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label displayText;

@FXML protected void buttonClick(ActionEvent event){

    usernameField.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent arg0) {
            displayText.setText("");
        }
    });

    passwordField.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle( MouseEvent arg0) {

            displayText.setText("");

        }
    });


    //validate username and pword to be "admin" and "hunter2"
    if(usernameField.getText().equals("admin") && passwordField.getText().equals("hunter2")){
        displayText.setText("Login success.");
        displayText.setTextFill(Color.GREEN);
    } else {
        displayText.setText("Incorrect username/password. Please re-enter");
        displayText.setTextFill(Color.RED);
    }
}
}
