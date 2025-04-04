package com.task.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.task.task.models.Task;
import com.task.task.repository.TaskRepository;
import com.task.task.utils.StringUtils;
import com.task.task.utils.textValidator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class EditRemoveTaskController {

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextArea txtDescription;

    @FXML
    private RadioButton rbDone;

    @FXML
    private RadioButton rbToDo;

    @FXML
    private Button btnEditTask;

    @FXML
    private Button btnDeleteTask;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lblDescriptionErrorMessage;

    @FXML
    private Label lblTitleErrorMessage;

    private Task task;

    @Autowired
    private TaskRepository taskRepository;

    public void initialize() {

        textValidator.maxCharacters(txtDescription, lblDescriptionErrorMessage, 100);

        textValidator.maxCharacters(txtTitle, lblTitleErrorMessage, 25);

    }

    public void setTask(Task task) {
        this.task = task;
        loadTaskData();
    }

    public void loadTaskData() {
        if (task != null) {
            txtID.setText(task.getId().toString());
            txtTitle.setText(task.getTitle());
            txtDescription.setText(task.getDescription());
            rbDone.setSelected(task.isDone());
            rbToDo.setSelected(!task.isDone());
        }
    }

    public void editTask() {
        if (task != null) {
            if (txtTitle.getText().isEmpty() || txtTitle.getText().isBlank() || txtDescription.getText().isEmpty()
                    || txtDescription.getText().isBlank()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Title and description are required.");
                alert.showAndWait();
                return;
            }
            task.setTitle(StringUtils.normalizeWhitespace(txtTitle.getText().trim()));
            task.setDescription(StringUtils.normalizeWhitespace(txtDescription.getText().trim()));
            task.setDone(rbDone.isSelected());
            taskRepository.save(task);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task Updated");
            alert.setHeaderText(null);
            alert.setContentText("Task updated successfully.");
            alert.showAndWait();
            closeWindow();
        }
    }

    public void deleteTask() {
        if (task != null) {
            taskRepository.delete(task);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Task Removed");
            alert.setHeaderText(null);
            alert.setContentText("Task removed successfully.");
            alert.showAndWait();
            closeWindow();
        }
    }

    public void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}