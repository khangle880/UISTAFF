package controller;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import classForDB.DataTest;
import classForDB.Report;

public class report implements Initializable {
    @FXML
    private AnchorPane reportManagementPane;

    @FXML
    private VBox vBox;

    @FXML
    private JFXTextField searchBox;

    @FXML
    private JFXButton addBtn;

    @FXML
    private TableView<Report> reportTable;

    @FXML
    private TableColumn<Report, String> idCol;

    @FXML
    private TableColumn<Report, String> titleCol;

    @FXML
    private TableColumn<Report, LocalDateTime> createDateCol;

    @FXML
    private TableColumn<Report, LocalDateTime> fromDateCol;

    @FXML
    private TableColumn<Report, LocalDateTime> toDateCol;

    @FXML
    private TableColumn<Report, Long> frequencyCol;

    // AccessDB: get data of reports
    private final ObservableList<Report> dataOfTable = DataTest.getReportList();

    // set formatter for datetime fields in table
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        setEventDoubleClickOnTable(reportTable);
    }

    private void initTable() {
        // ID COL SETTING
        idCol.setCellValueFactory(new PropertyValueFactory<>("reportID"));

        // NAME COL SETTING
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        // FREQUENCY COL SETTING
        frequencyCol.setCellValueFactory(new PropertyValueFactory<>("frequency"));

        // CREATE DATE COL SETTING
        createDateCol.setCellValueFactory(new PropertyValueFactory<Report, LocalDateTime>("createdDate"));
        createDateCol.setCellFactory(col -> new TableCell<Report, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });

        // FROM DATE COL SETTING
        fromDateCol.setCellValueFactory(new PropertyValueFactory<Report, LocalDateTime>("fromDate"));
        fromDateCol.setCellFactory(col -> new TableCell<Report, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });

        // JOIN TIME COL SETTING
        toDateCol.setCellValueFactory(new PropertyValueFactory<Report, LocalDateTime>("toDate"));
        toDateCol.setCellFactory(col -> new TableCell<Report, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });

        reportTable.setItems(dataOfTable);
        reportTable.getSelectionModel().select(dataOfTable.get(0));

        // set up for search box
        addTextFilter(dataOfTable, searchBox, reportTable);
    }

    private void addTextFilter(ObservableList<Report> allData, JFXTextField filterField, TableView<Report> table) {
        FilteredList<Report> filteredData = new FilteredList<>(allData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(report -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                // Filter matches ID.
                if (report.getReportID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches TITLE.
                } else if (report.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches CREATE DATE.
                } else if (String.format(report.getCreatedDate().format(formatter)).toLowerCase()
                        .indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches FROM DATE.
                } else if (String.format(report.getFromDate().format(formatter)).toLowerCase()
                        .indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches TO DATE.
                } else if (String.format(report.getToDate().format(formatter)).toLowerCase()
                        .indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches FREQUENCY.
                } else if (report.getFrequency().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Report> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    private void setEventDoubleClickOnTable(TableView<Report> tableView) {
        tableView.setRowFactory(new Callback<TableView<Report>, TableRow<Report>>() {
            @Override
            public TableRow<Report> call(TableView<Report> tv) {
                TableRow<Report> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Report rowData = row.getItem();
                        viewInfoReport(rowData);
                    }
                });
                return row;
            }
        });
    }

    private void viewInfoReport(Report report) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/reportView.fxml"));
        reportView reportViewCtrl = new reportView();
        loader.setController(reportViewCtrl);

        try {
            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(loader.load()));
            reportViewCtrl.showReport(report);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleAddReport(ActionEvent event) {

        Report newReport = new Report();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/addReportForm.fxml"));
        addReportForm addReportFormCtrl = new addReportForm();
        loader.setController(addReportFormCtrl);

        JFXScrollPane addReportFormPane = new JFXScrollPane();
        try {
            addReportFormPane.setContent(loader.load());

            Label title = new Label("Report Info");
            addReportFormPane.getBottomBar().getChildren().add(title);
            title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
            JFXScrollPane.smoothScrolling((ScrollPane) addReportFormPane.getChildren().get(0));

            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(addReportFormPane, 600, 700));

            newStage.showAndWait();

            newReport = addReportFormCtrl.getNewReport();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (newReport != null) {
            dataOfTable.add(newReport);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully added an Report");
            alert.showAndWait();
        }

    }
}
