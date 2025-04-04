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
import com.task.task.utils.StringUtils;
import com.task.task.utils.textValidator;

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

        rbToDo.setSelected(true);

        textValidator.maxCharacters(txtDescription, lblDescriptionErrorMessage, 100);

        textValidator.maxCharacters(txtTitle, lblTitleErrorMessage, 25);

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
                .addTask(new Task(title = StringUtils.normalizeWhitespace(txtTitle.getText().trim()),
                        description = StringUtils.normalizeWhitespace(txtDescription.getText().trim()),
                        isTaskDone = rbDone.isSelected()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(
                "Task Created: " + "\n" + "Title: " + title + "\n" + "Description: " + description + "\n" + "Done: "
                        + (isTaskDone ? "Completed" : "Incomplete"));

        alert.showAndWait();

        txtTitle.clear();
        txtDescription.clear();
        closeWindow();
    }

    public void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
