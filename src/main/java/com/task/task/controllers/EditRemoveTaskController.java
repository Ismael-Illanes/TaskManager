package com.task.task.controllers;

import org.springframework.stereotype.Controller;

import com.task.task.models.Task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button btnCreateTask;

    @FXML
    private Button btnDeleteTask;

    @FXML
    private Button btnCancel;

    private Task task;

    public void initialize() {

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
            if (task.isDone()) {
                rbDone.setSelected(true);
            } else {
                rbToDo.setSelected(true);
            }
        }
    }

    public void modifyTask() {

    }

    public void deleteTask() {

    }

    public void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();

    }
}
