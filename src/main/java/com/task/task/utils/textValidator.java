package com.task.task.utils;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// Clase para validar el tamaño de un TextArea o TextField
public class textValidator {

    public static void maxCharacters(TextArea textArea, Label labelError, int maxLength) {
        textArea.textProperty().addListener((_, _, newValue) -> {
            String untrimmedText = newValue;

            if (untrimmedText.length() > maxLength) {
                untrimmedText = untrimmedText.substring(0, maxLength);
                textArea.setText(untrimmedText);

                labelError.setVisible(true);
            } else {
                labelError.setVisible(false);
            }
        });
    }

    public static void maxCharacters(TextField textField, Label labelError, int maxLength) {
        textField.textProperty().addListener((_, _, newValue) -> {
            String untrimmedText = newValue;

            if (untrimmedText.length() > maxLength) {
                untrimmedText = untrimmedText.substring(0, maxLength);
                textField.setText(untrimmedText);

                labelError.setVisible(true);
            } else {
                labelError.setVisible(false);
            }
        });
    }
}
