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


public class TimetableController implements Initializable {

    @FXML
    private TableView<TTableModel> Ttable;

    @FXML
    private Label ActionMessage;
    @FXML
    private Label ActionMessage2;
    @FXML
    private Label manual1;
    @FXML
    private Label manual2;

    @FXML
    private TableColumn<TTableModel, String> DayColumn;

    @FXML
    private TableColumn<TTableModel, String> LocationColumn;

    @FXML
    private TableColumn<TTableModel, String> TimeColumn;

    @FXML
    private TableColumn<TTableModel, Integer> TtableIdColumn;
    @FXML
    private TableColumn<TTableModel, String> classNameColumn;

    @FXML
    private TableColumn<TTableModel, String> subjectNameColumn;

    @FXML
    private Button btnAddTT;

    @FXML
    private Button btnDeleteTT;

    @FXML
    private Button btnUpdateTT;

    @FXML
    private TextField txtClass;

    @FXML
    private TextField txtDay;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTime;
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
    private final DatabaseConnection db = new DatabaseConnection();
    private Connection con;
    @FXML
    void AddData(ActionEvent event) {
        Connect();
        String className = txtClass.getText();
        String subjectName = txtSubject.getText();
        String day = txtDay.getText();
        String time = txtTime.getText();
        String location = txtLocation.getText();
        if (subjectName.isEmpty()||className.isEmpty()||day.isEmpty()||time.isEmpty()||location.isEmpty()) {
            ActionMessage.setText("Please fill in the field");
                return;
            }
            String query = "INSERT INTO timetable (CLASS_ID, SUBJECT_ID, DAY, TIME, LOCATION) VALUES ((SELECT CLASS_ID FROM class WHERE CLASS_NAME = ?), (SELECT SUBJECT_ID FROM subject WHERE SUBJECT_NAME = ?), ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, className);
                preparedStatement.setString(2, subjectName);
                preparedStatement.setString(3, day);
                preparedStatement.setString(4, time);
                preparedStatement.setString(5, location);
                preparedStatement.executeUpdate();

                ActionMessage.setText("Schedule added successfully");
                loadTTableData();// Optionally, you can refresh the table or perform other actions
                txtClass.clear();
                txtSubject.clear();
                txtDay.clear();
                txtTime.clear();
                txtLocation.clear();
            } catch (SQLException e) {
                e.printStackTrace();
                ActionMessage.setText("Error adding class OR subject");
                ActionMessage2.setText("Class and subject must be create first");
            }
    }

    @FXML
    void DeleteData(ActionEvent event) {
        try {
            // Get the selected schedule from the table
            TTableModel selectedSchedule = Ttable.getSelectionModel().getSelectedItem();
            // Check if an item is selected
            if (selectedSchedule != null) {
                // Use the selected schedule's data to populate the fields for updating

                int Id = getSelectedTableId();
                PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM timetable WHERE ID = ?");
                preparedStatement.setInt(1, Id);
                preparedStatement.executeUpdate();

                ActionMessage.setText("Schedule deleted successfully");
                loadTTableData();// Optionally, you can refresh the table or perform other actions

            } else {
                ActionMessage.setText("Please select a schedule to delete.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActionMessage.setText("Error deleting schedule");
        }
    }

    @FXML
    void UpdateData(ActionEvent event) {
        try {
            // Get the selected schedule from the table
            TTableModel selectedSchedule = Ttable.getSelectionModel().getSelectedItem();
            // Check if an item is selected
            if (selectedSchedule != null) {
                // Use the selected schedule's data to populate the fields for updating

                String className = txtClass.getText();
                String subjectName = txtSubject.getText();
                String day = txtDay.getText();
                String time = txtTime.getText();
                String location = txtLocation.getText();

                int selectedId = getSelectedTableId();
                String updateQuery = "UPDATE timetable SET CLASS_ID = (SELECT CLASS_ID FROM class WHERE CLASS_NAME = ?), " +
                        "SUBJECT_ID = (SELECT SUBJECT_ID FROM subject WHERE SUBJECT_NAME = ?), " +
                        "DAY = ?, TIME = ?, LOCATION = ? WHERE ID = ?";

                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setString(1, className);
                updateStatement.setString(2, subjectName);
                updateStatement.setString(3, day);
                updateStatement.setString(4, time);
                updateStatement.setString(5, location);
                updateStatement.setInt(6, selectedId);

                updateStatement.executeUpdate();

                ActionMessage.setText("Schedule updated successfully");
                loadTTableData();
                txtClass.clear();
                txtSubject.clear();
                txtDay.clear();
                txtTime.clear();
                txtLocation.clear();
            } else {
                ActionMessage.setText("Please select a schedule to update.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ActionMessage.setText("Error updating schedule");
        }
    }

    private int getSelectedTableId() {
        TTableModel selectedSchedule = Ttable.getSelectionModel().getSelectedItem();
        // Check if an item is selected
        if (selectedSchedule != null) {
            return selectedSchedule.getTableId();
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
        Connect();
        displayUserProfile();
        loadTTableData();
        manual1.setText("Only to UPDATE and DELETE data , Please select the row first");
        manual2.setText("To ADD data , please add the SUBJECT and CLASS data first in CLASSES if it's not exist.");
        Ttable.setOnMouseClicked(e -> {
            TTableModel selectedData = Ttable.getSelectionModel().getSelectedItem();
            if (selectedData != null) {
                // Populate the text fields with the selected data
                txtClass.setText(selectedData.getClassName());
                txtSubject.setText(selectedData.getSubjectName());
                txtDay.setText(selectedData.getDay());
                txtTime.setText(selectedData.getSubjectTime());
                txtLocation.setText(selectedData.getLocation());
            }
        });

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

