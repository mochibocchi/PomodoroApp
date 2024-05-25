package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.Model.*;
import com.example.pomodoroapp.Model.IAccountDAO;
import com.example.pomodoroapp.Model.SqliteAccountDAO;
import com.example.pomodoroapp.Model.IStudy_SessionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    public Hyperlink loginButton;
    @FXML
    public Button registerButton;

    @FXML
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    private IStudy_SessionDAO Study_SessionDAO;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private VBox accountContainer;
    @FXML
    private ListView<Account> accountsListView;

    public RegisterController() {
        this.firstNameTextField = new TextField();
    }

    @FXML
    private void handleGoToLogin(ActionEvent event) {
        /**
         * Loads the login page
         */
        loadScene("view/login.fxml", event);
    }


    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onRegister(ActionEvent event) {
        /**
         * Registers an account to the database
         */
        final String firstName = firstNameTextField.getText();
        final String lastName = lastNameTextField.getText();
        final String email = emailTextField.getText();
        final String password = passwordTextField.getText();

        if (!isValidEmail(email)) {
            System.out.println("Invalid Email: Please enter a valid email address.");
            return;
        }
        if (!isValidPassword(password)) {
            System.out.println("Invalid Password: Password must be at least 12 characters long containing at least 1 special character or number.");
            return;
        }

        Account newAccount = new Account(firstName, lastName, email, password);
        accountDAO.addAccount(newAccount);
        loadScene("view/login.fxml", event);
    }

    public boolean isValidEmail(String email) {
        /**
         * Checks if the email is a proper address
         */
        return email.contains("@") && email.contains(".");
    }

    public boolean isValidPassword(String password) {
        /**
         * Checks if the password is greater than 12 characters and if it contains any special characters
         */
        if (password.length() < 12) {
            return false;
        }
        boolean security_check = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c) || !Character.isLetterOrDigit(c)) {
                security_check = true;
                break;
            }
        }
        return security_check;
    }


}
