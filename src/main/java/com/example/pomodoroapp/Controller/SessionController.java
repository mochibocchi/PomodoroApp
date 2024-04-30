package com.example.pomodoroapp.Controller;

import com.example.pomodoroapp.HelloApplication;
import com.example.pomodoroapp.Model.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class SessionController {
    public Button TimerButton;
    public Button sessionButton;
    private final IAccountDAO accountDAO = new SqliteAccountDAO();
    private final IStudy_SessionDAO Study_SessionDAO = new SqliteStudy_SessionDAO();
    public TableView sessionTable;
    public Button ViewSessions;

    @FXML
    private TextField totalTimeTextField;
    @FXML
    private TextField completedWorkTextField;
    @FXML
    private TextField sessionIdTextField;
    private int loggedInUserId;

    public void setLoggedInUserId(int loggedInUserId) {
        this.loggedInUserId = loggedInUserId;
    }

    public int getLoggedInUserId() {
        return AccountData.getInstance().getAccountId();
    }


    public void addSession(ActionEvent event) throws IOException {

        int accountId = getLoggedInUserId();
        int total_time = AccountData.getInstance().getTotalTimeElapsed();
        String completedWork = completedWorkTextField.getText();
        Study_Session study_session = new Study_Session(accountId, total_time, completedWork);
        Study_SessionDAO.addStudy_Session(study_session);
        loadScene("view/timer.fxml", event, 520, 400);
    }

    private void loadScene(String fxmlPath, ActionEvent event, int width, int height) {
        try {
            Parent root = FXMLLoader.load(HelloApplication.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void GoToTimer(ActionEvent event) throws IOException {
        loadScene("view/timer.fxml",event, 520, 400);
    }

    @FXML
    public void showSessions() {
        int accountId = getLoggedInUserId();
        List<Study_Session> sessions = Study_SessionDAO.getStudySessions(accountId);
        ObservableList<Study_Session> sessionList = FXCollections.observableArrayList(sessions);
        sessionTable.getItems().clear();
        sessionTable.setItems(sessionList);
        sessionTable.setVisible(true);
        TableColumn<Study_Session, Integer> sessionIdColumn = new TableColumn<>("Session ID");
        sessionIdColumn.setCellValueFactory(cellData -> {Study_Session session = cellData.getValue();
            return new SimpleIntegerProperty(session.getSessionId()).asObject();
        });

        TableColumn<Study_Session, Integer> totalTimeColumn = new TableColumn<>("Total Time(s)");
        totalTimeColumn.setCellValueFactory(cellData -> {Study_Session session = cellData.getValue();
            return new SimpleIntegerProperty(session.getTotalTime()).asObject();
        });

        TableColumn<Study_Session, String> completedWorkColumn = new TableColumn<>("Completed Work");
        completedWorkColumn.setCellValueFactory(cellData -> {Study_Session session = cellData.getValue();
            return new SimpleStringProperty(session.getCompletedWork());});
        sessionTable.getColumns().setAll(sessionIdColumn, totalTimeColumn, completedWorkColumn);
    }

}
