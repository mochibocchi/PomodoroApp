package com.example.pomodoroapp.controller;

import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.model.Account;
import com.example.pomodoroapp.model.IAccountDAO;
import com.example.pomodoroapp.model.SqliteAccountDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }




}