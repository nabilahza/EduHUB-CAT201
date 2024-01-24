package com.example.schoolmngmt;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private Button btneditProfile;

    @FXML
    private Label displayEmail;

    @FXML
    private Label displayFName;

    @FXML
    private Label displayLName;

    @FXML
    private Label displayPNum;

    @FXML
    private Label displayPassword;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnHome;
    @FXML
    private Button btnGrading;
    @FXML
    private Button btn_Timetable;
    @FXML
    private Button btnClasses;
    @FXML
    private Button btnStudent;
    @FXML
    private Button btnSignOut;
    @FXML
    private Label userNamelabel;

    @FXML
    void onClickEditButton(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-profile.fxml"));
        Parent root = loader.load();

        editProfileController editProfileController = loader.getController();
        editProfileController.setUserInfo(displayFName.getText(), displayLName.getText(), displayEmail.getText(), displayPNum.getText(), displayPassword.getText());

        Stage stage = new Stage();
        stage.setTitle("EduHub Teaching Management System : Edit Profile");
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void handleOnclick(javafx.event.ActionEvent mouseEvent) {
        try {
            if (mouseEvent.getSource() == btnHome) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Dashboard");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else if (mouseEvent.getSource() == btnProfile) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Profile");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else if (mouseEvent.getSource() == btnGrading) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Grading.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Student Grading");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else if (mouseEvent.getSource() == btn_Timetable) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Timetable.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Timetable");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else if (mouseEvent.getSource() == btnSignOut) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Log In");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else if (mouseEvent.getSource() == btnClasses) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Classes.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Classes");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else if (mouseEvent.getSource() == btnStudent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Students.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Student");
                stage.setScene(new Scene(root));
                stage.show();}
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void displayUserProfile() {
        // Assuming you have a DatabaseConnection class with getConnection method
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        // Assuming your users table has columns 'firstName' and 'lastName'
        String query = "SELECT FNAME, LNAME FROM user WHERE EMAIL = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Replace "your_username_here" with the actual username of the logged-in user
            preparedStatement.setString(1, "user1@gmail.com"); //bila kat sini,masuklah siapa punya akan kelaur profile siti aminah dan semua data tu adalah aminah punya takde org lain sebab tak link dgn user

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Assuming your Label id is userNamelabel
                String firstName = resultSet.getString("FNAME");
                String lastName = resultSet.getString("LNAME");
                userNamelabel.setText(firstName + " " + lastName+" !");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fetchAndDisplayUserInfo();
        displayUserProfile();
    }

    private void fetchAndDisplayUserInfo() {
        // Assuming you have a DatabaseConnection class with getConnection method
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        // Assuming your users table has columns 'firstName', 'lastName', 'email', 'phoneNum', and 'password'
        String query = "SELECT FNAME, LNAME, EMAIL, PHONE_NO, PWORD FROM user WHERE EMAIL = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Replace "your_username_here" with the actual username of the logged-in user
            preparedStatement.setString(1, "user1@gmail.com");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Assuming your Label id is displayFName, displayLName, displayEmail, displayPNum, and displayPassword
                displayFName.setText(resultSet.getString("FNAME"));
                displayLName.setText(resultSet.getString("LNAME"));
                displayEmail.setText(resultSet.getString("EMAIL"));
                displayPNum.setText(resultSet.getString("PHONE_NO"));
                displayPassword.setText(resultSet.getString("PWORD"));
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

