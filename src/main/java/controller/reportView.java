package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;

import classForDB.ConsumeOfAccount;
import classForDB.DataTest;
import classForDB.JoinTimeOfAccount;
import classForDB.Report;
import classForDB.TransAmountPerRole;
import classForDB.TransNumPerRole;
import classForDB.TransactionRole;

public class reportView implements Initializable {
    @FXML
    private ScrollPane reportViewPane;

    @FXML
    private JFXButton closeWindowBtn;

    @FXML
    private Label titleLbl;

    @FXML
    private Label createDateLbl;

    @FXML
    private Label fromDateLbl;

    @FXML
    private Label toDateLbl;

    @FXML
    private Label frequencyLbl;

    // set formatter for datetime fields in table
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    // AccessDB: get data of reports
    private final ObservableList<TransactionRole> dataOfRoleList = DataTest.getTransRoleList();

    JFXMasonryPane reportPane = new JFXMasonryPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportViewPane.setContent(reportPane);
        reportViewPane.setFitToWidth(true);
        reportPane.setPadding(new Insets(0, 10, 0, 10));
    }

    public ScrollPane getReportViewPane() {
        return reportViewPane;
    }

    @FXML
    void closeWindow(ActionEvent event) {
        ((Stage) closeWindowBtn.getScene().getWindow()).close();
    }

    // Set width for table
    private void setWithForTable(TableView<?> table) {
        int widthTable = 0;
        for (TableColumn column : table.getColumns()) {
            widthTable += column.getWidth();
        }
        table.setPrefWidth(widthTable);
    }

    public void showReport(Report report) {
        titleLbl.setText(report.getTitle());
        createDateLbl.setText("Create: " + String.format(report.getCreatedDate().format(formatter)));
        fromDateLbl.setText("From: " + String.format(report.getFromDate().format(formatter)));
        toDateLbl.setText("To: " + String.format(report.getToDate().format(formatter)));
        frequencyLbl.setText("Frequency: " + report.getFrequency());

        // Account Reporting Subcategories create view
        if (report.getAccountReport() != null) {

            // Joined Time Statistic create view
            if (report.getAccountReport().getJoinedTimeStatistic() != null) {

                TableView<JoinTimeOfAccount> subReportTable = new TableView<>();

                // create Column
                TableColumn<JoinTimeOfAccount, String> idAccountCol = new TableColumn<JoinTimeOfAccount, String>("ID");
                idAccountCol.setPrefWidth(100);
                TableColumn<JoinTimeOfAccount, String> nameAccountCol = new TableColumn<JoinTimeOfAccount, String>(
                        "Name");
                nameAccountCol.setPrefWidth(200);
                TableColumn<JoinTimeOfAccount, String> typeAccountCol = new TableColumn<JoinTimeOfAccount, String>(
                        "Type Account");
                typeAccountCol.setPrefWidth(200);
                TableColumn<JoinTimeOfAccount, Long> numDateJoinedCol = new TableColumn<JoinTimeOfAccount, Long>(
                        "Joined (Days)");
                numDateJoinedCol.setPrefWidth(200);

                // Set binding field for column
                idAccountCol.setCellValueFactory(tf -> tf.getValue().getAccount().getIDProperty());
                nameAccountCol.setCellValueFactory(tf -> tf.getValue().getAccount().getNameProperty());
                typeAccountCol.setCellValueFactory(tf -> tf.getValue().getAccount().getTypeAccountProperty());
                numDateJoinedCol.setCellValueFactory(tf -> tf.getValue().getNumDayJoinedProperty().asObject());
                subReportTable.getColumns().addAll(idAccountCol, nameAccountCol, typeAccountCol, numDateJoinedCol);

                setWithForTable(subReportTable);

                subReportTable.setItems(report.getAccountReport().getJoinedTimeStatistic());
                Label subReportTitle = new Label("Time Joined To Now Statistics Table");
                subReportTitle.getStyleClass().add("title-subReport-label");
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);
                vb.getChildren().addAll(subReportTitle, subReportTable);

                reportPane.getChildren().add(vb);
            }

            // Consume Statistic create view
            if (report.getAccountReport().getConsumeStatistic() != null) {
                Label subReportTitle = new Label("Consume Amount Statistics Table");
                subReportTitle.getStyleClass().add("title-subReport-label");
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);

                // Set content table
                if (report.getAccountReport().getConsumeStatistic().isEmpty()) {
                    Label contentNullTitle = new Label("There are no payments during this period");
                    contentNullTitle.getStyleClass().add("content-subReport-label");
                    vb.getChildren().addAll(subReportTitle, contentNullTitle);
                } else {
                    TableView<ConsumeOfAccount> subReportTable = new TableView<>();

                    // create Column
                    TableColumn<ConsumeOfAccount, String> idAccountCol = new TableColumn<ConsumeOfAccount, String>(
                            "ID");
                    idAccountCol.setPrefWidth(100);
                    TableColumn<ConsumeOfAccount, String> nameAccountCol = new TableColumn<ConsumeOfAccount, String>(
                            "Name");
                    nameAccountCol.setPrefWidth(200);
                    TableColumn<ConsumeOfAccount, String> typeAccountCol = new TableColumn<ConsumeOfAccount, String>(
                            "Type Account");
                    typeAccountCol.setPrefWidth(200);
                    TableColumn<ConsumeOfAccount, Long> consumeAmountCol = new TableColumn<ConsumeOfAccount, Long>(
                            "Consume Amount");
                    consumeAmountCol.setPrefWidth(200);
                    TableColumn<ConsumeOfAccount, Long> receiveAmountCol = new TableColumn<ConsumeOfAccount, Long>(
                            "Receive Amount");
                    receiveAmountCol.setPrefWidth(200);

                    // Set binding field for column
                    idAccountCol.setCellValueFactory(tf -> tf.getValue().getAccount().getIDProperty());
                    nameAccountCol.setCellValueFactory(tf -> tf.getValue().getAccount().getNameProperty());
                    typeAccountCol.setCellValueFactory(tf -> tf.getValue().getAccount().getTypeAccountProperty());
                    consumeAmountCol.setCellValueFactory(tf -> tf.getValue().getSendAmountProperty().asObject());
                    receiveAmountCol.setCellValueFactory(tf -> tf.getValue().getReceiveAmountProperty().asObject());
                    subReportTable.getColumns().addAll(idAccountCol, nameAccountCol, typeAccountCol, receiveAmountCol,
                            consumeAmountCol);

                    // ---- set cell value the send and receive Amount column
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                    consumeAmountCol.setCellFactory(ms -> new TableCell<ConsumeOfAccount, Long>() {
                        @Override
                        protected void updateItem(Long price, boolean empty) {
                            super.updateItem(price, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(currencyFormat.format(price));
                            }
                        }
                    });
                    receiveAmountCol.setCellFactory(ms -> new TableCell<ConsumeOfAccount, Long>() {
                        @Override
                        protected void updateItem(Long price, boolean empty) {
                            super.updateItem(price, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(currencyFormat.format(price));
                            }
                        }
                    });

                    setWithForTable(subReportTable);

                    subReportTable.setItems(report.getAccountReport().getConsumeStatisticProperty());

                    vb.getChildren().addAll(subReportTitle, subReportTable);
                }
                reportPane.getChildren().add(vb);
            }

        }

        // Transaction Reporting Subcategories create view
        if (report.getTransactionReport() != null) {

            // Transaction Amount Statistic create view
            if (report.getTransactionReport().getTransAmountStatistic() != null) {
                Label subReportTitle = new Label("Transaction Amount Statistic Table");
                subReportTitle.getStyleClass().add("title-subReport-label");
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);

                // Set content table
                if (report.getTransactionReport().getTransAmountStatistic().isEmpty()) {
                    Label contentNullTitle = new Label("There are no transactions during this period");
                    contentNullTitle.getStyleClass().add("content-subReport-label");
                    vb.getChildren().addAll(subReportTitle, contentNullTitle);
                } else {

                    TableView<TransAmountPerRole> subReportTable = new TableView<>();

                    // create Column
                    TableColumn<TransAmountPerRole, String> roleNameCol = new TableColumn<TransAmountPerRole, String>(
                            "Role Name");
                    roleNameCol.setPrefWidth(100);
                    TableColumn<TransAmountPerRole, LocalDateTime> transDateCol = new TableColumn<TransAmountPerRole, LocalDateTime>(
                            "Transaction Date");
                    transDateCol.setPrefWidth(200);
                    TableColumn<TransAmountPerRole, Long> amountTransCol = new TableColumn<TransAmountPerRole, Long>(
                            "Transaction Amount");
                    amountTransCol.setPrefWidth(200);

                    // Set binding field for column
                    roleNameCol.setCellValueFactory(tf -> tf.getValue().getTransRole().getNameProperty());
                    transDateCol.setCellValueFactory(
                            new PropertyValueFactory<TransAmountPerRole, LocalDateTime>("transDate"));

                    amountTransCol.setCellValueFactory(tf -> tf.getValue().getAmountTransProperty().asObject());
                    subReportTable.getColumns().addAll(roleNameCol, transDateCol, amountTransCol);

                    // ---- set cell factory Amount column
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                    amountTransCol.setCellFactory(ms -> new TableCell<TransAmountPerRole, Long>() {

                        @Override
                        protected void updateItem(Long price, boolean empty) {
                            super.updateItem(price, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(currencyFormat.format(price));
                            }
                        }
                    });
                    transDateCol.setCellFactory(col -> new TableCell<TransAmountPerRole, LocalDateTime>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty)
                                setText(null);
                            else
                                setText(String.format(item.format(formatter)));
                        }
                    });

                    setWithForTable(subReportTable);

                    subReportTable.setItems(report.getTransactionReport().getTransAmountStatistic());

                    vb.getChildren().addAll(subReportTitle, subReportTable);
                }
                reportPane.getChildren().add(vb);

                // create chart
                if (!report.getTransactionReport().getTransAmountStatistic().isEmpty()) {
                    CategoryAxis xAxis = new CategoryAxis();
                    xAxis.setLabel("Date");
                    NumberAxis yAxis = new NumberAxis();
                    yAxis.setLabel("Amount Transaction");
                    LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
                    chart.setTitle("Transaction Amount Statistic Chart");

                    for (TransactionRole transRole : dataOfRoleList) {
                        ObservableList<Data<String, Number>> data = FXCollections.observableArrayList();
                        SortedList<Data<String, Number>> sortedData = new SortedList<>(data,
                                (data1, data2) -> data1.getXValue().compareTo(data2.getXValue()));
                        ObservableList<TransAmountPerRole> listItems = report.getTransactionReport()
                                .getTransAmountStatistic();
                        for (TransAmountPerRole item : listItems) {
                            if (item.getTransRole() == transRole) {
                                addData(data, String.format(item.getTransDate().format(formatter)),
                                        item.getAmountTrans().doubleValue());
                            }
                        }
                        chart.getData().add(new Series<>(transRole.getName(), sortedData));

                    }
                    reportPane.getChildren().add(chart);
                }

            }

            // Transaction Number Statistic create view
            if (report.getTransactionReport().getTransNumStatistic() != null) {
                Label subReportTitle = new Label("Transaction Number Statistic Table");
                subReportTitle.getStyleClass().add("title-subReport-label");
                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);

                // Set content table
                if (report.getTransactionReport().getTransNumStatistic().isEmpty()) {
                    Label contentNullTitle = new Label("There are no transactions during this period");
                    contentNullTitle.getStyleClass().add("content-subReport-label");
                    vb.getChildren().addAll(subReportTitle, contentNullTitle);
                } else {
                    TableView<TransNumPerRole> subReportTable = new TableView<>();

                    // create Column
                    TableColumn<TransNumPerRole, String> roleNameCol = new TableColumn<TransNumPerRole, String>(
                            "Role Name");
                    roleNameCol.setPrefWidth(100);
                    TableColumn<TransNumPerRole, LocalDateTime> transDateCol = new TableColumn<TransNumPerRole, LocalDateTime>(
                            "Transaction Date");
                    transDateCol.setPrefWidth(200);
                    TableColumn<TransNumPerRole, Long> numberTransCol = new TableColumn<TransNumPerRole, Long>(
                            "Transaction Number");
                    numberTransCol.setPrefWidth(200);

                    // Set binding field for column
                    roleNameCol.setCellValueFactory(tf -> tf.getValue().getTransRole().getNameProperty());
                    transDateCol
                            .setCellValueFactory(new PropertyValueFactory<TransNumPerRole, LocalDateTime>("transDate"));
                    transDateCol.setCellFactory(col -> new TableCell<TransNumPerRole, LocalDateTime>() {
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty)
                                setText(null);
                            else
                                setText(String.format(item.format(formatter)));
                        }
                    });
                    numberTransCol.setCellValueFactory(tf -> tf.getValue().getNumTransProperty().asObject());
                    subReportTable.getColumns().addAll(roleNameCol, transDateCol, numberTransCol);

                    setWithForTable(subReportTable);

                    subReportTable.setItems(report.getTransactionReport().getTransNumStatisticProperty());

                    vb.getChildren().addAll(subReportTitle, subReportTable);
                }
                reportPane.getChildren().add(vb);

                // create chart
                if (!report.getTransactionReport().getTransNumStatistic().isEmpty()) {
                    CategoryAxis xAxis = new CategoryAxis();
                    xAxis.setLabel("Date");
                    NumberAxis yAxis = new NumberAxis();
                    yAxis.setLabel("Number Transaction");
                    LineChart<String, Number> chart = new LineChart<>(xAxis, yAxis);
                    chart.setTitle("Transaction Number Statistic Chart");

                    for (TransactionRole transRole : dataOfRoleList) {
                        ObservableList<Data<String, Number>> data = FXCollections.observableArrayList();
                        SortedList<Data<String, Number>> sortedData = new SortedList<>(data,
                                (data1, data2) -> data1.getXValue().compareTo(data2.getXValue()));
                        ObservableList<TransNumPerRole> listItems = report.getTransactionReport()
                                .getTransNumStatistic();
                        for (TransNumPerRole item : listItems) {
                            if (item.getTransRole() == transRole) {
                                addData(data, String.format(item.getTransDate().format(formatter)),
                                        item.getNumTrans().doubleValue());
                            }
                        }
                        chart.getData().add(new Series<>(transRole.getName(), sortedData));

                    }
                    reportPane.getChildren().add(chart);
                }

            }
        }

        if (reportPane.getChildren().isEmpty()) {
            Label nullReportTitle = new Label("This report does not have any subcategories !");
            nullReportTitle.getStyleClass().add("title-subReport-label");
            VBox vb = new VBox();
            vb.setAlignment(Pos.CENTER);
            vb.setPrefSize(1080, 660);
            vb.getChildren().addAll(nullReportTitle);

            reportPane.getChildren().add(vb);
        }

    }

    private void addData(ObservableList<Data<String, Number>> data, String formattedDate, double value) {
        Data<String, Number> dataAtDate = data.stream().filter(d -> d.getXValue().equals(formattedDate)).findAny()
                .orElseGet(() -> {
                    Data<String, Number> newData = new Data<String, Number>(formattedDate, 0.0);
                    data.add(newData);
                    return newData;
                });
        dataAtDate.setYValue(dataAtDate.getYValue().doubleValue() + value);
    }

}
