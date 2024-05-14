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
public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    private int loggedInUserId;
    private int accountId;



    @FXML
    private void handleGoToRegister(ActionEvent event) {
        loadScene("view/register.fxml", event,540,400);
    }
    private final Connection connection = SqliteConnection.getInstance();


    private void handleSuccessfulLogin(int accountId) {
        AccountData.getInstance().setAccountId(accountId);
        
    }




    @FXML
    public void handleLogin(ActionEvent event) {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        int accountId = accountDAO.getAccountId(email, password);
        if (accountId != -1) {
            System.out.println("Login successful");
            handleSuccessfulLogin(accountId);
            loadScene("view/timer.fxml", event, 540, 400);

        }
        else {
            System.out.println("Invalid email or password");
        }

    }



    private void loadScene(String fxmlPath, ActionEvent event, int width, int height) {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
