package com.task.task;

import com.task.task.controllers.TaskManagerController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TaskApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private TaskManagerController taskManagerController;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(TaskApplication.class);
        taskManagerController = springContext.getBean(TaskManagerController.class);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        taskManagerController.showStage();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}