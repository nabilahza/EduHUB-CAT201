package com.example.schoolmngmt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("LogIn.fxml"));
        primaryStage.setTitle("EduHub Teaching Management System");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 1072, 646));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}