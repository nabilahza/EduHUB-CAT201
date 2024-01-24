package com.example.schoolmngmt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class editProfileController implements Initializable {

    @FXML
    private TextField editFname;

    @FXML
    private TextField editLname;

    @FXML
    private TextField editPNum;

    @FXML
    private Label editProfileMessage;

    @FXML
    private TextField editPword;

    @FXML
    private TextField editUsername;

    @FXML
    private Button saveBtn;
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

    public void setUserInfo(String fname, String lname, String email, String pnum, String password) {
        editFname.setText(fname);
        editLname.setText(lname);
        editPNum.setText(pnum);
        editUsername.setText(email);
        editPword.setText(password);
        // You can set email and password similarly if needed
    }
    @FXML
    private void onSaveButtonClick() {
        String editedFname = editFname.getText();
        String editedLname = editLname.getText();
        String editedPNum = editPNum.getText();
        String editedEmail= editUsername.getText();
        String editedPword = editPword.getText();

        boolean isUpdateSuccessful = updateProfileInDatabase(editedFname, editedLname, editedPNum,editedEmail,editedPword);

        if (isUpdateSuccessful) {
            editProfileMessage.setText("Profile updated. Information saved.");
        } else {
            editProfileMessage.setText("Error updating profile. Please try again.");
        }
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
            preparedStatement.setString(1, "user1@gmail.com");

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
        displayUserProfile();
    }

    private boolean updateProfileInDatabase(String editedFname, String editedLname, String editedPNum, String editedEmail, String editedPword ) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        try {
            Connection connection = databaseConnection.getConnection();

            // Assuming your users table has columns 'fname', 'lname', 'pnum', and 'username'
            String query = "UPDATE user SET FNAME=?, LNAME=?, PHONE_NO=? , EMAIL=? , PWORD=? WHERE EMAIL=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, editedFname);
            preparedStatement.setString(2, editedLname);
            preparedStatement.setString(3, editedPNum);
            preparedStatement.setString(4, editedEmail);
            preparedStatement.setString(5, editedPword);
            preparedStatement.setString(6, "user1@gmail.com"); // Replace with the actual username

            int result = preparedStatement.executeUpdate();

            // Close resources
            preparedStatement.close();
            connection.close();

            // Check if the update was successful
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}