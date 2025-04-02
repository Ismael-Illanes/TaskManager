package com.task.task.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.task.task.models.Task;
import com.task.task.repository.TaskRepository;

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
    private TaskRepository taskRepository;

    private ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        colName.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTitle()));
        colDescription
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDescription()));
        colDone.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isDone()));

        tblTasks.setItems(taskList);

        // Listener para que el TextField detecte los cambios a la hora de simplemente
        // escribir
        txtID.textProperty().addListener((_, _, _) -> {
            searchById();
        });

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
            // Aqui se debería de abrir el formulario de edición de la tarea
            System.out.println(selectedTask.getId() + " " + selectedTask.getTitle() + " "
                    + selectedTask.getDescription() + " " + selectedTask.isDone());
        }
    }

    public void addTask(Task task) {
        taskRepository.save(task);
        taskList.add(task);
        tblTasks.setItems(taskList);
    }

    public void removeTask(Task task) {
        taskRepository.delete(task);
        taskList.remove(task);
        tblTasks.setItems(taskList);
    }

    public void searchById() {
        String idText = txtID.getText();
        if (idText != null && !idText.isEmpty()) {
            try {
                ObservableList<Task> taskListIDFilteredTasks = FXCollections.observableArrayList();
                for (Task task : taskList) {
                    if (task.getId().toString().startsWith(idText)) {
                        taskListIDFilteredTasks.add(task);
                    }
                }

                tblTasks.setItems(taskListIDFilteredTasks);
            } catch (NumberFormatException e) {
                System.out.println("ID no válido");
            }
        } else {
            tblTasks.setItems(taskList);
        }
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

    public void openCreateTask() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/task/task/views/form-create-task.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Create new task");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(tblTasks.getScene().getWindow());
        stage.showAndWait();
    }
}
