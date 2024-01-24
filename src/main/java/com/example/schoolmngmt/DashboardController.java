package com.example.schoolmngmt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TableColumn<TTableModel, String> DayColumn;

    @FXML
    private TableColumn<TTableModel, String> LocationColumn;

    @FXML
    private TableColumn<TTableModel, String> TimeColumn;

    @FXML
    private TableView<TTableModel> Ttable;

    @FXML
    private TableColumn<TTableModel, Integer> TtableIdColumn;

    @FXML
    private TableColumn<TTableModel, String> classNameColumn;

    @FXML
    private TableColumn<TTableModel, String> subjectNameColumn;

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
    private Label totClasses;

    @FXML
    private Label totStudents;

    @FXML
    private Label totSubjects;

    @FXML
    private Label userNamelabel;
    private final DatabaseConnection db = new DatabaseConnection();
    private Connection con;
    @FXML
    void handleOnclick(javafx.event.ActionEvent mouseEvent) {
        try {
            if (mouseEvent.getSource() == btnHome) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("EduHub Teaching Management System : Home");
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
    private void updateTotalClassesLabel() {
        Connect();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(CLASS_ID) AS classCount FROM class");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String classCount = resultSet.getString("classCount");
                totClasses.setText(classCount);
            } else {
                totClasses.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    private void updateTotalSubjectLabel() {
        Connect();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(SUBJECT_ID) AS subjectCount FROM subject");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String subjectCount = resultSet.getString("subjectCount");
                totSubjects.setText(subjectCount);
            } else {
                totSubjects.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    private void updateTotalStudentLabel() {
        Connect();
        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT COUNT(STUDENT_ID) AS studentCount FROM student");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String studentCount = resultSet.getString("studentCount");
                totStudents.setText(studentCount);
            } else {
                totStudents.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connect();
        displayUserProfile();
        updateTotalClassesLabel();
        updateTotalSubjectLabel();
        updateTotalStudentLabel();
        loadTTableData();
    }
    private void Connect( ) {
        con = db.getConnection();
        if (con == null) {
            // Handle the case when the connection is not successful
            System.out.println("Failed to connect to the database.");
        }
    }
    private void loadTTableData() {
        Connect();
        ObservableList<TTableModel> ttableList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT timetable.ID, class.CLASS_NAME, subject.SUBJECT_NAME, timetable.TIME, timetable.DAY, timetable.LOCATION " +
                    "FROM timetable " + "JOIN class ON timetable.CLASS_ID = class.CLASS_ID " + "JOIN subject ON timetable.SUBJECT_ID = subject.SUBJECT_ID");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                int tableId = resultSet.getInt("ID");
                String className = resultSet.getString("CLASS_NAME");
                String subjectName = resultSet.getString("SUBJECT_NAME");
                String day = resultSet.getString("DAY");
                String time = resultSet.getString("TIME");
                String location = resultSet.getString("LOCATION");


                TTableModel ttableModel = new TTableModel(tableId, subjectName,className,day,time,location);
                ttableList.add(ttableModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        TtableIdColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        DayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        TimeColumn.setCellValueFactory(new PropertyValueFactory<>("subjectTime"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        Ttable.setItems(ttableList);

    }

}
