package com.example.schoolmngmt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LogInController implements Initializable{

    @FXML
    private Button btnSignUp;
    @FXML
    private Label loginmessageLabel;
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    private DatabaseConnection db;

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        db = new DatabaseConnection();
        Connection connection = db.getConnection();
        try {

            // Execute a query to check if the entered credentials exist
            String query = "SELECT * FROM user WHERE EMAIL=? AND PWORD=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // If a matching record is found, open the Home.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Home");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // If no matching record is found, show an error message or take appropriate action
                loginmessageLabel.setText("Invalid username or password");
            }

            // Close the database connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSingUpButtonClick(ActionEvent event) {
        try {
            if (event.getSource() == btnSignUp) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Sign Up ");
                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code goes here
    }
}



