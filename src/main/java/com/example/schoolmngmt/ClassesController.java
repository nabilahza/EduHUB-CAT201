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
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {

    @FXML
    private Label ClassActionMessage;
    @FXML
    private Label manual1;
    @FXML
    private Button btnAddClass;

    @FXML
    private Button btnAddSubject;

    @FXML
    private Button btnDeleteClass;

    @FXML
    private Button btnDeleteSubject;

    @FXML
    private TableColumn<ClassesModel, Integer> classIdColumn;

    @FXML
    private TableColumn<ClassesModel, String> classNameColumn;

    @FXML
    private Label subjectActionMessage;

    @FXML
    private TableColumn<SubjectModel, Integer> subjectIdColumn;

    @FXML
    private TableColumn<SubjectModel, String> subjectNameColumn;

    @FXML
    private TableView<ClassesModel> tableClass;

    @FXML
    private TableView<SubjectModel> tableSubject;

    @FXML
    private TextField txtClass;

    @FXML
    private TextField txtSubject;

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


    private final DatabaseConnection databaseConnection = new DatabaseConnection();
    private Connection con;

    @FXML
    void AddData(ActionEvent event) {
        Connect();
        // Check which button triggered the event
        if (event.getSource() == btnAddClass) {
            String className = txtClass.getText();
            if (className.isEmpty()) {
                ClassActionMessage.setText("Please fill in the field");
                return;
            }
            String query = "INSERT INTO class (CLASS_NAME) VALUES (?)";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, className);
                preparedStatement.executeUpdate();

                ClassActionMessage.setText("Class added successfully");
                loadClassesData();// Optionally, you can refresh the table or perform other actions
                txtClass.clear();
            } catch (SQLException e) {
                e.printStackTrace();
                ClassActionMessage.setText("Error adding class");
            }
        } else if (event.getSource() == btnAddSubject) {
            // Add Subject logic
            String subjectName = txtSubject.getText();
            if (subjectName.isEmpty()) {
                subjectActionMessage.setText("Please fill in the field");
                return;
            }
            String query = "INSERT INTO subject (SUBJECT_NAME) VALUES (?)";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, subjectName);
                preparedStatement.executeUpdate();

                subjectActionMessage.setText("Subject added successfully");
                loadSubjectsData();// Optionally, you can refresh the table or perform other actions
                txtSubject.clear();
            } catch (SQLException e) {
                e.printStackTrace();
                subjectActionMessage.setText("Error adding subject");
            }
        }
    }

    @FXML
    void DeleteData(ActionEvent event) {
        Connect();

        // Check which button triggered the event
        if (event.getSource() == btnDeleteClass) {
            // Delete Class logic

            try {
                // Get the selected class ID from the table or another source

                int id = getSelectedClassId(); // Replace this with your actual method
                if (id != -1) {
                    PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM class WHERE CLASS_ID = ?");
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();

                    ClassActionMessage.setText("Class deleted successfully");
                    loadClassesData();// Optionally, you can refresh the table or perform other actions
                } else {
                    ClassActionMessage.setText("Please select a class to delete.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                ClassActionMessage.setText("Error deleting class");
            }
        } else if (event.getSource() == btnDeleteSubject) {
            // Delete Subject logic
            // TODO: Implement the database deletion logic for subject
            try {
                // Get the selected subject ID from the table or another source
                int subjectIdToDelete = getSelectedSubjectId(); // Replace this with your actual method
                if (subjectIdToDelete != -1) {
                    PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM subject WHERE SUBJECT_ID = ?");
                    preparedStatement.setInt(1, subjectIdToDelete);
                    preparedStatement.executeUpdate();

                    subjectActionMessage.setText("Subject deleted successfully");
                    loadSubjectsData();// Optionally, you can refresh the table or perform other actions
                } else {
                    subjectActionMessage.setText("Please select a subject to delete.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                subjectActionMessage.setText("Error deleting subject");
            }
        }
    }

    // TODO: Implement methods to get the selected class and subject IDs from the table
    private int getSelectedClassId() {
        ClassesModel selectedClass = tableClass.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedClass != null) {
            return selectedClass.getClassId();
        } else {
            // Handle the case when no item is selected
            System.out.println("No item selected.");
            return -1; // or any other appropriate value to indicate no selection
        }
    }

    private int getSelectedSubjectId() {
        SubjectModel selectedClass = tableSubject.getSelectionModel().getSelectedItem();

        // Check if an item is selected
        if (selectedClass != null) {
            return selectedClass.getSubjectId();
        } else {
            // Handle the case when no item is selected
            System.out.println("No item selected.");
            return -1; // or any other appropriate value to indicate no selection
        }
    }
    @FXML
    void handleNav(javafx.event.ActionEvent mouseEvent) {
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
        Connect();
        displayUserProfile();
        loadClassesData();
        loadSubjectsData();
        manual1.setText("Only to DELETE data , Please select the row first");
    }

    private void Connect( ) {
        con = databaseConnection.getConnection();
        if (con == null) {
            // Handle the case when the connection is not successful
            System.out.println("Failed to connect to the database.");
        }
    }
    private void loadClassesData() {
        Connect();
        ObservableList<ClassesModel> classesList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM class");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int classId = resultSet.getInt("CLASS_ID");
                String className = resultSet.getString("CLASS_NAME");

                ClassesModel classesModel = new ClassesModel(classId, className);
                classesList.add(classesModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        classIdColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));

        tableClass.setItems(classesList);

    }

    private void loadSubjectsData() {
        Connect();
        ObservableList<SubjectModel> subjectsList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM subject");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int subjectId = resultSet.getInt("SUBJECT_ID");
                String subjectName = resultSet.getString("SUBJECT_NAME");

                SubjectModel subjectModel = new SubjectModel(subjectId, subjectName);
                subjectsList.add(subjectModel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        subjectIdColumn.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));

        tableSubject.setItems(subjectsList);
    }
}
