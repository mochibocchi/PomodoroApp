package com.example.pomodoroapp.Controller;

import com.example.pomodoroapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    public Hyperlink registerButton;

    @FXML
    private void handleGoToRegister(ActionEvent event) {
        loadScene("view/register.fxml", event, 520, 400);
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        loadScene("view/timer.fxml", event, 520, 400);
//        // Authentication logic
//        if (authenticate(login, password)) {
//            loadScene("view/timer.fxml", event);
//        } else {
//            showError("Login failed");
//        }
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
