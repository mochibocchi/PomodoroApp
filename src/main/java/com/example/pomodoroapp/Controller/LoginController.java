package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    @FXML
    private void handleGoToRegister(ActionEvent event) {
        loadScene("view/register.fxml", event);
    }
    private final Connection connection = SqliteConnection.getInstance();


    @FXML
    private void handleLogin(ActionEvent event) {
        final String email = emailTextField.getText();
        final String password = passwordTextField.getText();
        int loggedInUserId = accountDAO.getLoggedInUserId(email, password);
        if (loggedInUserId != -1) {
            System.out.println("Login successful");
            loadScene("view/timer.fxml", event);
        } else {
            System.out.println("Invalid email or password");
        }


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




}
