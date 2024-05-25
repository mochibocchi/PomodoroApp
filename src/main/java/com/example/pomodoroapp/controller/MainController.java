package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.Model.*;
import com.example.pomodoroapp.Model.IAccountDAO;
import com.example.pomodoroapp.Model.SqliteAccountDAO;
import com.example.pomodoroapp.Model.IStudy_SessionDAO;
import com.example.pomodoroapp.Model.SqliteStudy_SessionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;


public class MainController {

    @FXML
    private IAccountDAO accountDAO;
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

    public MainController() {
        accountDAO = new SqliteAccountDAO();
        Study_SessionDAO = new SqliteStudy_SessionDAO();
    }

}
