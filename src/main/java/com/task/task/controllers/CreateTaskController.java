package com.task.task.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javafx.scene.control.Label;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.task.task.models.Task;

import javafx.event.ActionEvent;

@Controller
public class CreateTaskController {

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtID;

    @FXML
    private TextArea txtDescription;

    @FXML
    private RadioButton rbDone;

    @FXML
    private RadioButton rbToDo;

    @FXML
    private Button btnCreateTask;

    @FXML
    private Button btnCancel;

    @FXML
    private ToggleGroup isDone;

    @FXML
    private Label lblDescriptionErrorMessage;

    @FXML
    private Label lblTitleErrorMessage;

    @Autowired
    private TaskManagerController taskManagerController;

    @FXML
    public void initialize() {
        isDone = new ToggleGroup();
        rbDone.setToggleGroup(isDone);
        rbToDo.setToggleGroup(isDone);
        rbToDo.setSelected(true);

        txtDescription.textProperty().addListener((_, _, newText) -> {
            String untrimmedText = newText;

            if (untrimmedText.length() > 100) {
                untrimmedText = untrimmedText.substring(0, 100);
                txtDescription.setText(untrimmedText);

                lblDescriptionErrorMessage.setVisible(true);
            } else {
                lblDescriptionErrorMessage.setVisible(false);
            }
        });

        txtTitle.textProperty().addListener((_, _, newText) -> {
            String untrimmedText = newText;

            if (untrimmedText.length() > 25) {
                untrimmedText = untrimmedText.substring(0, 25);
                txtTitle.setText(untrimmedText);

                lblTitleErrorMessage.setVisible(true);
            } else {
                lblTitleErrorMessage.setVisible(false);
            }
        });

    }

    @FXML
    public void handleCreateTask(ActionEvent event) {
        String title;
        String description;
        boolean isTaskDone;

        if ((txtDescription.getText().isEmpty() || txtDescription.getText().isBlank()) || (txtTitle.getText().isEmpty()
                || txtTitle.getText().isBlank())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("El título y la descripción son obligatorios");
            alert.showAndWait();
            return;
        }
        this.taskManagerController
                .addTask(new Task(title = txtTitle.getText().trim(), description = txtDescription.getText().trim(),
                        isTaskDone = rbDone.isSelected()));

        System.out.println("Task Created: " + title + " | " + description + " | Done: "
                + (isTaskDone ? "Completed" : "Incomplete"));

        txtTitle.clear();
        txtDescription.clear();
    }

    public void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
