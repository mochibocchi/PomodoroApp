package com.example.pomodoroapp.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MockLoginController {

    private int accountId;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    public void setEmailTextField(TextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public void setPasswordTextField(TextField passwordTextField) {

    }

    public int getAccountId() {
        return accountId;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        // Mock implementation of account verification
        if ("test@example.com".equals(email) && "password".equals(password)) {
            accountId = 123;
        } else {
            accountId = -1;
        }
    }

}