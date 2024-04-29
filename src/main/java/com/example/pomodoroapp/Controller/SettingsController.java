package com.example.pomodoroapp.Controller;

import com.example.pomodoroapp.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import java.io.IOException;

public class SettingsController {

    @FXML
    private Button backButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;


    @FXML
    public void backButtonAction() throws IOException{
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/timer.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage.setScene(scene);
    }

    public void addActivityAction() {
    }

    public void deleteActivityAction() {
    }
}
