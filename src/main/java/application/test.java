package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test extends Application {

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public void start(Stage primaryStage) {
        LocalDateTime fromDateTime = LocalDateTime.of(1984, 12, 16, 7, 45, 55);
        LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 10, 6, 40, 45);

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);
        System.out.println(tempDateTime);

        // long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        // tempDateTime = tempDateTime.plusYears(years);

        // long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        // tempDateTime = tempDateTime.plusMonths(months);

        
        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

        System.out.println(days + " days " + hours + " hours " + minutes
                + " minutes " + seconds + " seconds.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}