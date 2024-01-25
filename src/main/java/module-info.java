module com.example.schoolmngmt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
   // requires com.calendarfx.view;
    requires java.sql;
    requires mysql.connector.java;

    opens com.example.schoolmngmt to javafx.fxml;
    exports com.example.schoolmngmt;
}
