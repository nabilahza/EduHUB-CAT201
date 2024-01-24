package com.example.schoolmngmt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private Label StudentActionMessage;

    @FXML
    private Label StudentActionMessage2;
    @FXML
    private Label manual1;
    @FXML
    private Label manual2;

    @FXML
    private Button btnAddSubject;

    @FXML
    private Button btnDeleteSubject;


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
    private TableColumn<StudentsModel, Integer> studentIdColumn;

    @FXML
    private TableColumn<StudentsModel, String> studentNameColumn;
    @FXML
    private TableColumn<StudentsModel, String> classNameColumn;


    @FXML
    private TableView<StudentsModel> tableStudent;

    @FXML
    private TextField txtClass;

    @FXML
    private TextField txtStudentName;
    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private Connection con;

    @FXML
    void AddData(ActionEvent event) {
        Connect();
            String studentName = txtStudentName.getText();
            String className = txtClass.getText();
            if (studentName.isEmpty()||className.isEmpty()) {
                StudentActionMessage.setText("Please fill in the field");
                return;
            }
            String query = "INSERT INTO student ( STUDENT_NAME, CLASS_ID) VALUES (?,(SELECT CLASS_ID FROM class WHERE CLASS_NAME = ?))";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, studentName);
                preparedStatement.setString(2, className);
                preparedStatement.executeUpdate();

                StudentActionMessage.setText("Student added successfully");
                loadStudentData();// Optionally, you can refresh the table or perform other actions
                txtStudentName.clear();
                txtClass.clear();

            } catch (SQLException e) {
                e.printStackTrace();
                StudentActionMessage.setText("Error adding class.");
                StudentActionMessage2.setText("Only classes that already created can be used");
            }


    }

    @FXML
    void DeleteData(ActionEvent event) {
        Connect();
            try {
                // Get the selected class ID from the table or another source
                int id = getSelectedStudentId(); // Replace this with your actual method

                PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM student WHERE STUDENT_ID = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();

                StudentActionMessage.setText("Student deleted successfully");
                loadStudentData();// Optionally, you can refresh the table or perform other actions

            } catch (SQLException e) {
                e.printStackTrace();
                StudentActionMessage.setText("Error deleting class");
            }

    }

    // TODO: Implement methods to get the selected class and subject IDs from the table
    private int getSelectedStudentId() {
        StudentsModel selectedStudent = tableStudent.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedStudent != null) {
            return selectedStudent.getStudentId();
        } else {
            // Handle the case when no item is selected
            System.out.println("No item selected.");
            return -1; // or any other appropriate value to indicate no selection
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
        Connect( );
        displayUserProfile();
        loadStudentData();
        manual1.setText("Only to DELETE data , Please select the row first");
        manual2.setText("To ADD data , please add the CLASS data first in CLASS section if it's not exist.");
    }
    private void Connect( ) {
        con = databaseConnection.getConnection();
        if (con == null) {
            // Handle the case when the connection is not successful
            System.out.println("Failed to connect to the database.");
        }
    }
    private void loadStudentData() {
        Connect();
        ObservableList<StudentsModel> studentList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT student.STUDENT_ID, student.STUDENT_NAME, class.CLASS_NAME " +
                    "FROM student " + "JOIN class ON student.CLASS_ID = class.CLASS_ID ");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int studentId = resultSet.getInt("STUDENT_ID");
                String studentName = resultSet.getString("STUDENT_NAME");
                String className = resultSet.getString("CLASS_NAME");

                StudentsModel studentsModel = new StudentsModel(studentId,studentName, className);
                studentList.add(studentsModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));

        tableStudent.setItems(studentList);

    }
}