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

public class RegisterController {
    @FXML
    public Hyperlink loginButton;
    @FXML
    public Button registerButton;

    @FXML
    private void handleGoToLogin(ActionEvent event) {
        loadScene("view/login.fxml", event);
    }

    @FXML
    private void handleRegister(ActionEvent event) {
        loadScene("view/login.fxml", event);
//        // Registration logic
//        if (register(newUserDetails)) {
//            loadScene("view/login.fxml", event);
//        } else {
//            showError("Registration failed");
//        }
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
