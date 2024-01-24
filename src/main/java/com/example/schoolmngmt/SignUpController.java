package com.example.schoolmngmt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class SignUpController implements Initializable{

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnLogIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField firstNamefield;

    @FXML
    private TextField lastNamefield;

    @FXML
    private TextField passWordfield;

    @FXML
    private TextField phoneNumField;

    @FXML
    private Label signupmessageLabel;
    @FXML
    private Label signupmessageLabel2;

    @FXML
    private TextField usernameField;

    @FXML
    void onCancelButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("EduHub Teaching Management System : Log In");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void onLogInButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("EduHub Teaching Management System : Log In");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        String firstName = firstNamefield.getText();
        String lastName = lastNamefield.getText();
        String username = usernameField.getText();
        String password = passWordfield.getText();
        String phoneNum = phoneNumField.getText();

        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty() || phoneNum.isEmpty()) {
            signupmessageLabel.setText("Please fill in all fields");
            return;
        }

        // Insert data into the database
        if (insertUserIntoDatabase(firstName, lastName, username, password, phoneNum)) {
            signupmessageLabel.setText("Sign Up success! Information is stored");
            signupmessageLabel2.setText(" ");
        } else {
            signupmessageLabel.setText("Error: Unable to store information.");
            signupmessageLabel2.setText("Please use the different username");
        }
    }

    private boolean insertUserIntoDatabase(String firstName, String lastName, String username, String password, String phoneNum) {
        // Assuming you have a DatabaseConnection class with getConnection method
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String query = "INSERT INTO user (EMAIL, PWORD, FNAME, LNAME, PHONE_NO) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, phoneNum);

            int result = preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

            // Check if the insertion was successful
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization code goes here
    }
}
