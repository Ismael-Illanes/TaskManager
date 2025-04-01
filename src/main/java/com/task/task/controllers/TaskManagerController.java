package com.task.task.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.task.task.models.Task;
import com.task.task.repository.TaskRepository;
import com.task.task.services.TaskServiceImpl;

import java.io.IOException;

@Component
public class TaskManagerController {

    @FXML
    private TableView<Task> tblTasks;

    @FXML
    private TableColumn<Task, Long> colID;

    @FXML
    private TableColumn<Task, String> colName;

    @FXML
    private TableColumn<Task, String> colDescription;

    @FXML
    private TableColumn<Task, Boolean> colDone;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private RadioButton rbOpt1;

    @FXML
    private RadioButton rbOpt2;

    @FXML
    private RadioButton rbOpt3;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @Autowired
    private TaskRepository taskRepository;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTitle()));
        colDescription
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        colDone.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isDone()));

        loadExampleTasks();

        tblTasks.setItems(taskList);

        // Listener para la selección del item en la tabla
        tblTasks.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                getSelectedTask();
            }
        });

        // Detectar cuando el ratón entra o sale de una fila específica
        tblTasks.setRowFactory(_ -> {
            TableRow<Task> row = new TableRow<>();

            row.setOnMouseExited(_ -> {
                if (!row.isEmpty()) {
                    tblTasks.getSelectionModel().clearSelection(row.getIndex());
                }
            });

            return row;
        });
    }

    public void getSelectedTask() {
        Task selectedTask = tblTasks.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            System.out.println(selectedTask.getId() + " " + selectedTask.getTitle() + " "
                    + selectedTask.getDescription() + " " + selectedTask.isDone());
        }
    }

    private void loadExampleTasks() {
        Task exampleTask = new Task("Tarea de prueba", "Descripción de prueba", false);
        Task exampleTask2 = new Task("wqweewe de prueba", "Descripción de prueba", false);
        taskRepository.save(exampleTask);
        taskRepository.save(exampleTask2);
        taskList.add(exampleTask);
        taskList.add(exampleTask2);
        tblTasks.setItems(taskList);
    }

    public void showStage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/task/task/views/task-view.fxml"));
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Task Manager");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openCreateTask() {
        System.out.println(this.taskServiceImpl.getAllTasks());
    }
}
