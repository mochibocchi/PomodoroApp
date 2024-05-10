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



    @FXML
    private void handleGoToLogin(ActionEvent event) {
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
    private void onRegister(ActionEvent event) {
        final String firstName = firstNameTextField.getText();
        final String lastName = lastNameTextField.getText();
        final String email = emailTextField.getText();
        final String password = passwordTextField.getText();
        Account newAccount = new Account(firstName, lastName, email, password);
        accountDAO.addAccount(newAccount);
        loadScene("view/login.fxml", event);
    }

    @FXML
    private void logout(ActionEvent event) {
        AccountData.getInstance().setAccountId(0);
        loadScene("view/login.fxml", event);
    }
}
