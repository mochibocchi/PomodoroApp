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
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.List;

public class TasklistController {
    @FXML
    private TableView<Study_Tasks> taskTable;
    @FXML
    private TextField taskInputField;
    @FXML
    private Button backButton;
    public int getLoggedInUserId() {
        return AccountData.getInstance().getAccountId();
    }
    public Button ViewTasks;

    @FXML
    private TextField taskDetailsTextField;

    @FXML
    private void GoToTimer(ActionEvent event) throws IOException {
        loadScene("view/timer.fxml",event,520,400);
    }
    private SqliteTasksDAO tasksDAO;

    public TasklistController() {
        tasksDAO = new SqliteTasksDAO();
    }

    @FXML
    private void initialize() {
        showTasks();
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

    public void addTask(ActionEvent event) {
        int accountId = getLoggedInUserId();
        String taskDetails = taskDetailsTextField.getText();
        Study_Tasks study_tasks = new Study_Tasks(accountId, taskDetails);
        tasksDAO.addTask(study_tasks);
        showTasks();
        taskDetailsTextField.clear();
    }

    @FXML
    public void deleteTask(ActionEvent event) {
        Study_Tasks selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasksDAO.deleteTask(getLoggedInUserId(), selectedTask.getTaskDetails());
            showTasks();
        }
    }

    @FXML
    public void showTasks() {
        int accountId = getLoggedInUserId();
        List<Study_Tasks> tasks = tasksDAO.getTasks(accountId);
        ObservableList<Study_Tasks> taskList = FXCollections.observableArrayList(tasks);
        taskTable.getItems().clear();
        taskTable.setItems(taskList);
        taskTable.setVisible(true);


        TableColumn<Study_Tasks, String> taskDetailsColumn = new TableColumn<>("Tasks");
        taskDetailsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaskDetails()));
        taskTable.getColumns().setAll(taskDetailsColumn);
    }
}