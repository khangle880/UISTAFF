package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import classComponent.AutoCompleteComboBoxListener;
import classComponent.ExpandableTextArea;
import classComponent.MyNumberStringConverter;
import classComponent.Util;
import classForDB.*;
import classForDB.Report;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TreeView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class addReportForm implements Initializable {
    @FXML
    private VBox addForm;

    @FXML
    private GridPane infoPane;

    @FXML
    private Label subcategoriesLbl;

    @FXML
    private JFXTextField textID;

    @FXML
    private JFXTextField titleText;

    @FXML
    private JFXDatePicker fromDatePicker;

    @FXML
    private JFXDatePicker toDatePicker;

    @FXML
    private JFXTextField frequencyText;

    @FXML
    private TreeView<String> checkTreeView;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton okBtn;

    // ---- Variable global
    // variable check Report is valid
    private boolean ReportIsValid;
    private String ReportErrorText;
    Report newReport = null;
    CheckBoxTreeItem<String> joinedTimeStatisticCheck = new CheckBoxTreeItem<>("Joined Time Statistic");
    CheckBoxTreeItem<String> consumeStatisticCheck = new CheckBoxTreeItem<>("Consume Statistic");
    CheckBoxTreeItem<String> transNumStatisticCheck = new CheckBoxTreeItem<>("Transaction Number Statistic");
    CheckBoxTreeItem<String> transAmountStatisticCheck = new CheckBoxTreeItem<>("Transaction Amount  Statistic");
    CheckBoxTreeItem<String> isAccountReport = new CheckBoxTreeItem<>("Account Report");
    CheckBoxTreeItem<String> isTransReport = new CheckBoxTreeItem<>("Transaction Report");
    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<>("Root");

    // AccessDB: get data of transaction and account and role
    private final ObservableList<Transaction> dataOfTransList = DataTest.getTransactionList();
    private final ObservableList<BaseAccount> dataOfAccountList = DataTest.getAccountList();
    private final ObservableList<TransactionRole> dataOfRoleList = DataTest.getTransRoleList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSubcategoriesReport(checkTreeView);
        initNumericFromTextField(frequencyText);
        setUpResizeForm();
        initValidatorForInfoForm();
        validatorForToDatePicker();
        setHandleAutoFillFrequency();
        setFormatForDatePicker(fromDatePicker);
        setFormatForDatePicker(toDatePicker);
    }

    private void setFormatForDatePicker(JFXDatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null)
                    return "";
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    private void setUpResizeForm() {
        // set for infoPane
        infoPane.setHgap(25);
        infoPane.setVgap(45);
        infoPane.setPadding(new Insets(30, 0, 0, 40));

        ColumnConstraints leftColInfoPane = new ColumnConstraints();
        leftColInfoPane.setHalignment(HPos.RIGHT);
        ColumnConstraints rightColInfoPane = new ColumnConstraints();
        rightColInfoPane.setHgrow(Priority.SOMETIMES);
        infoPane.getColumnConstraints().addAll(leftColInfoPane, rightColInfoPane);

    }

    private void initNumericFromTextField(JFXTextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    private void initSubcategoriesReport(TreeView<String> treeView) {

        isAccountReport.setExpanded(true);
        isTransReport.setExpanded(true);
        // Adding all the child check box items it the parent
        isAccountReport.getChildren().addAll(joinedTimeStatisticCheck, consumeStatisticCheck);
        isTransReport.getChildren().addAll(transNumStatisticCheck, transAmountStatisticCheck);
        // Creating the parent check box item
        root.getChildren().addAll(isAccountReport, isTransReport);
        // Adding the parent check box item to the tree view
        treeView.setRoot(root);
        // Setting the cell factory
        treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        treeView.setPrefHeight(200);
        treeView.setShowRoot(false);

        RowConstraints rowTreeView = new RowConstraints();
        rowTreeView.setValignment(VPos.TOP);
        subcategoriesLbl.setPadding(new Insets(6, 0, 0, 0));
        rowTreeView.setMinHeight((double) 200);
        infoPane.getRowConstraints().set(5, rowTreeView);
    }

    private void setHandleAutoFillFrequency() {
        fromDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (toDatePicker.getValue() != null && newValue != null) {
                frequencyText.setText(
                        String.valueOf(LocalDate.from(toDatePicker.getValue()).until(newValue, ChronoUnit.DAYS)));
            } else
                frequencyText.setText("0");
        });
        toDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (fromDatePicker.getValue() != null && newValue != null) {
                frequencyText.setText(
                        String.valueOf(LocalDate.from(fromDatePicker.getValue()).until(newValue, ChronoUnit.DAYS)));
            } else
                frequencyText.setText("0");

            // set value to not over from
            if (fromDatePicker.getValue().compareTo(newValue) > 0) {
                toDatePicker.setValue(null);
            }
        });
        // toDatePicker
    }

    private void validatorForToDatePicker() {
        toDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            // set value to not over from
            if (fromDatePicker.getValue() != null && newValue != null) {
                if (fromDatePicker.getValue().compareTo(newValue) > 0) {
                    toDatePicker.setValue(null);
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("WARNING");
                    alert.setHeaderText(null);
                    alert.setContentText("To Date Can't Less Than From Date !");
                    alert.showAndWait();
                }
            }

        });
    }

    // Create addListener for event exit focus a node
    private void addListenerNotFocus(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    ReportIsValid = (((IFXValidatableControl) node).validate() == false ? false : ReportIsValid);
                }
            }

        });
    }

    // travel through the nodes in editForm
    private void travelThoughNodesInAddForm() {
        // travel through the nodes of base info form
        ObservableList<Node> childrenNodeOfPane = infoPane.getChildren();
        for (Node node : childrenNodeOfPane) {
            node.requestFocus();
        }
        infoPane.requestFocus();
    }

    // initialize validator for nodes in info form
    private void initValidatorForInfoForm() {
        // create validators
        RequiredFieldValidator validatorText = new RequiredFieldValidator();
        NumberValidator validatorNumber = new NumberValidator();

        // set text for validators
        validatorText.setMessage("No Input Given!");
        validatorNumber.setMessage("Only Numbers are supported!");

        // set validator for Nodes
        textID.getValidators().add(validatorText);
        titleText.getValidators().add(validatorText);
        fromDatePicker.getValidators().add(validatorText);
        toDatePicker.getValidators().add(validatorText);
        frequencyText.getValidators().addAll(validatorText, validatorNumber);

        // set event exit focus for Nodes
        addListenerNotFocus(textID);
        addListenerNotFocus(titleText);
        addListenerNotFocus(fromDatePicker);
        addListenerNotFocus(toDatePicker);
        addListenerNotFocus(frequencyText);

    }

    // check new data account is valid
    private boolean checkForNewData() {
        // todo: check for primary key
        ReportIsValid = true;
        ReportErrorText = new String("New information of report not valid");
        travelThoughNodesInAddForm();

        if (!ReportIsValid) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText(ReportErrorText + "!");
            alert.showAndWait();
        }

        return ReportIsValid;
    }

    public Report getNewReport() {
        return newReport;
    }

    private void createSubReportForReport(Report report) {

        // Account Reporting Subcategories create
        if (isAccountReport.isIndeterminate() || isAccountReport.isSelected()) {
            AccountReportingSubcategories newAccountReport = new AccountReportingSubcategories();
            // Joined Time Statistic create
            if (joinedTimeStatisticCheck.isSelected()) {
                ObservableList<JoinTimeOfAccount> joinTimeAccountList = FXCollections.observableArrayList();
                for (BaseAccount account : dataOfAccountList) {
                    long days = account.getStatus()
                            ? LocalDateTime.from(account.getJoinTime()).until(LocalDateTime.now(), ChronoUnit.DAYS)
                            : 0L;
                    joinTimeAccountList.add(new JoinTimeOfAccount(account, days));
                }
                newAccountReport.setJoinedTimeStatistic(joinTimeAccountList);
            }

            if (consumeStatisticCheck.isSelected()) {
                ObservableList<ConsumeOfAccount> consumeAccountList = FXCollections.observableArrayList();
                for (BaseAccount account : dataOfAccountList) {
                    Long sendAmount = 0L;
                    Long receiveAmount = 0L;
                    for (Transaction transaction : dataOfTransList) {
                        if (transaction.getExecutionDate().compareTo(report.getFromDate()) >= 0
                                && (transaction.getExecutionDate().compareTo(report.getToDate()) <= 0)) {
                            if (transaction.getReceiveAccount() == account) {
                                receiveAmount += transaction.getCashAmount();
                            }
                            if (transaction.getSenderAccount() == account) {
                                sendAmount += transaction.getCashAmount();
                            }
                        }
                    }
                    consumeAccountList.add(new ConsumeOfAccount(account, receiveAmount, sendAmount));
                }
                newAccountReport.setConsumeStatistic(consumeAccountList);
            }
            report.setAccountReport(newAccountReport);
        }

        // Transaction Reporting Subcategories create
        if (isTransReport.isIndeterminate() || isTransReport.isSelected()) {
            TransactionReportingSubcategories newTransReport = new TransactionReportingSubcategories();
            // Transaction Number Statistic create
            if (transNumStatisticCheck.isSelected()) {
                ObservableList<TransNumPerRole> transNumList = FXCollections.observableArrayList();
                for (TransactionRole role : dataOfRoleList) {
                    LocalDate fromDate = report.getFromDate().toLocalDate();
                    LocalDate toDate = report.getToDate().toLocalDate();
                    for (LocalDate date = fromDate; date.compareTo(toDate) <= 0; date = date.plusDays(1L)) {
                        long transNum = 0;
                        for (Transaction transaction : dataOfTransList) {
                            if (transaction.getExecutionDate().toLocalDate().compareTo(date) == 0
                                    && transaction.getTransRole() == role) {
                                transNum++;
                            }
                        }
                        if (transNum != 0) {
                            transNumList.add(new TransNumPerRole(role, date.atStartOfDay(), transNum));
                        }
                    }
                }
                newTransReport.setTransNumStatistic(transNumList);
            }
            // Transaction Amount Statistic create
            if (transAmountStatisticCheck.isIndeterminate() || transAmountStatisticCheck.isSelected()) {
                ObservableList<TransAmountPerRole> transAmountList = FXCollections.observableArrayList();
                for (TransactionRole role : dataOfRoleList) {
                    LocalDate fromDate = report.getFromDate().toLocalDate();
                    LocalDate toDate = report.getToDate().toLocalDate();
                    for (LocalDate date = fromDate; date.compareTo(toDate) <= 0; date = date.plusDays(1L)) {
                        long transAmount = 0;
                        for (Transaction transaction : dataOfTransList) {
                            if (transaction.getExecutionDate().toLocalDate().compareTo(date) == 0
                                    && transaction.getTransRole() == role) {
                                transAmount += transaction.getCashAmount();
                            }
                        }
                        if (transAmount != 0) {
                            transAmountList.add(new TransAmountPerRole(role, date.atStartOfDay(), transAmount));
                        }
                    }
                }
                newTransReport.setTransAmountStatistic(transAmountList);
            }
            report.setTransactionReport(newTransReport);
        }

    }

    @FXML
    void handleCancelEditForm(ActionEvent event) {
        // close window
        newReport = null;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleOkAddForm(ActionEvent event) {
        if (checkForNewData()) {
            newReport = new Report();

            newReport.setReportID(textID.getText());
            newReport.setTitle(titleText.getText());
            newReport.setCreatedDate(LocalDateTime.now());
            newReport.setFromDate(fromDatePicker.getValue().atTime(LocalTime.now()));
            newReport.setToDate(toDatePicker.getValue().atTime(LocalTime.now()));
            newReport.setFrequency(frequencyText.getText() + "Days");

            // new sub-report
            createSubReportForReport(newReport);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Create New Report");
            alert.showAndWait();

            // close window
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }
    }
}
