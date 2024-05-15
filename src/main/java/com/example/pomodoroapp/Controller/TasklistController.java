package com.example.pomodoroapp.Controller;

import com.example.pomodoroapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TasklistController {
    @FXML
    private ListView<String> taskListView;
    @FXML
    private TextField taskInputField;
    @FXML
    private Button backButton;

    @FXML
    private void GoToTimer(ActionEvent event) throws IOException {
        loadScene("view/timer.fxml",event,520,400);
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

    public void handleAddTask() {
        String task = taskInputField.getText();
        if (!task.isEmpty()) {
            taskListView.getItems().add(task);
            taskInputField.clear();
        }
    }

    public void handleDeleteTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            taskListView.getItems().remove(selectedIndex);
        }
    }

    public void handleSaveTasks() {
        // Save the tasks to the database (implementation depends on the database setup)
    }
}
