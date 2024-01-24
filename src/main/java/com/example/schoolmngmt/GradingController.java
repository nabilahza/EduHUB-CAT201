package com.example.schoolmngmt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GradingController implements Initializable {

    @FXML
    private Label ActionMessage;
    @FXML
    private Label ActionMessage2;
    @FXML
    private Label manual1;
    @FXML
    private Label manual2;

    @FXML
    private TableView<GradingModel> TableGrading;
    @FXML
    private TableColumn<GradingModel, Integer> GrIdColumn;
    @FXML
    private TableColumn<GradingModel, String> stuNameColumn;
    @FXML
    private TableColumn<GradingModel, String> subjectNameColumn;
    @FXML
    private TableColumn<GradingModel, String> TestNameColumn;
    @FXML
    private TableColumn<GradingModel, String> GradeColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

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
    private TextField txtGrade;

    @FXML
    private TextField txtStuName;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTestName;
    private final DatabaseConnection DB = new DatabaseConnection();
    private Connection con;

    @FXML
    void AddData(ActionEvent event) {
        Connect();

        String subjectName = txtSubject.getText();
        String studentName = txtStuName.getText();
        String testName = txtTestName.getText();
        String grade = txtGrade.getText();
        if (subjectName.isEmpty()||studentName.isEmpty()||testName.isEmpty()||grade.isEmpty()) {
            ActionMessage.setText("Please fill in the field");
            return;
        }
        String query = "INSERT INTO grading (STUDENT_ID, SUBJECT_ID,TEST_NAME,GRADE) VALUES ((SELECT STUDENT_ID FROM student WHERE STUDENT_NAME = ?), (SELECT SUBJECT_ID FROM subject WHERE SUBJECT_NAME = ?), ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, studentName);
            preparedStatement.setString(2, subjectName);
            preparedStatement.setString(3, testName);
            preparedStatement.setString(4, grade);
            preparedStatement.executeUpdate();

            ActionMessage.setText("Data added successfully");
            loadGradingData();// Optionally, you can refresh the table or perform other actions
           txtSubject.clear();
            txtStuName.clear();
            txtTestName.clear();
            txtGrade.clear();

        } catch (SQLException e) {
            e.printStackTrace();
            ActionMessage.setText("Error adding subject or student.");
            ActionMessage2.setText("Subject and student must be created first");
        }
    }

    @FXML
    void DeleteData(ActionEvent event) {
        try {
            // Get the selected schedule from the table
            GradingModel selectedData = TableGrading.getSelectionModel().getSelectedItem();
            // Check if an item is selected
            if (selectedData != null) {
                // Use the selected schedule's data to populate the fields for updating

                int Id = getSelectedGradingId();
                PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM grading WHERE GRADING_ID = ?");
                preparedStatement.setInt(1, Id);
                preparedStatement.executeUpdate();

                ActionMessage.setText("Data deleted successfully");
                loadGradingData();// Optionally, you can refresh the table or perform other actions

            } else {
                ActionMessage.setText("Please select a data to delete.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActionMessage.setText("Error deleting data");
        }
    }

    @FXML
    void UpdateData(ActionEvent event) {
        try {
            // Get the selected schedule from the table
            GradingModel selectedData = TableGrading.getSelectionModel().getSelectedItem();
            // Check if an item is selected
            if (selectedData != null) {
                // Use the selected schedule's data to populate the fields for updating
                String subjectName = txtSubject.getText();
                String studentName = txtStuName.getText();
                String testName = txtTestName.getText();
                String grade = txtGrade.getText();

                int selectedId = getSelectedGradingId();
                String updateQuery = "UPDATE grading SET STUDENT_ID = (SELECT STUDENT_ID FROM student WHERE STUDENT_NAME = ?), " +
                        "SUBJECT_ID = (SELECT SUBJECT_ID FROM subject WHERE SUBJECT_NAME = ?), " +
                        "TEST_NAME = ?, GRADE = ? WHERE GRADING_ID = ?";

                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setString(1, studentName);
                updateStatement.setString(2, subjectName);
                updateStatement.setString(3, testName);
                updateStatement.setString(4, grade);
                updateStatement.setInt(5, selectedId);

                updateStatement.executeUpdate();

                ActionMessage.setText("Data updated successfully");
                loadGradingData();
                txtSubject.clear();
                txtStuName.clear();
                txtTestName.clear();
                txtGrade.clear();
            } else {
                ActionMessage.setText("Please select a data to update.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActionMessage.setText("Error updating ");
        }
    }
    private int getSelectedGradingId() {
        GradingModel selectedData = TableGrading.getSelectionModel().getSelectedItem();
        // Check if an item is selected
        if (selectedData != null) {
            return selectedData.getGradingId();
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
    public void initialize(URL location, ResourceBundle resources) {
        Connect();
        displayUserProfile();
        loadGradingData();
        manual1.setText("Only to UPDATE and DELETE data , Please select the row first");
        manual2.setText("To ADD data , please add the SUBJECT and STUDENT data first in CLASSES and STUDENT section if it's not exist.");

        TableGrading.setOnMouseClicked(e -> {
            GradingModel selectedData = TableGrading.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                // Populate the text fields with the selected data
                txtSubject.setText(selectedData.getSubject());
                txtStuName.setText(selectedData.getStudentName());
                txtTestName.setText(selectedData.getTestName());
                txtGrade.setText(selectedData.getGrade());
            }
        });
    }
    private void Connect( ) {
        con = DB.getConnection();
        if (con == null) {
            // Handle the case when the connection is not successful
            System.out.println("Failed to connect to the database.");
        }
    }
    private void loadGradingData() {
        Connect();
        ObservableList<GradingModel> GrList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT grading.GRADING_ID, subject.SUBJECT_NAME,student.STUDENT_NAME, grading.TEST_NAME, grading.GRADE "
                    + "FROM grading " + "JOIN subject ON grading.SUBJECT_ID = subject.SUBJECT_ID " + "JOIN student ON grading.STUDENT_ID = student.STUDENT_ID");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int GrId = resultSet.getInt("GRADING_ID");
                String studentName = resultSet.getString("STUDENT_NAME");
                String subjectName = resultSet.getString("SUBJECT_NAME");
                String testName = resultSet.getString("TEST_NAME");
                String grade = resultSet.getString("GRADE");


                GradingModel GrModel = new GradingModel(GrId, subjectName,studentName,testName,grade);
                GrList.add(GrModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        GrIdColumn.setCellValueFactory(new PropertyValueFactory<>("gradingId"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        stuNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        TestNameColumn.setCellValueFactory(new PropertyValueFactory<>("testName"));
        GradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        TableGrading.setItems(GrList);

    }
}


