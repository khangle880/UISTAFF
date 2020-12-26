package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;

import classForDB.Address;
import classForDB.BaseAccount;
import classForDB.DataTest;

public class AccountManagement implements Initializable {

    @FXML
    private AnchorPane accountManagementPane;

    @FXML
    private JFXTextField searchBox;

    @FXML
    private TableView<BaseAccount> accountTable;

    @FXML
    private TableColumn<BaseAccount, String> idCol;

    @FXML
    private TableColumn<BaseAccount, String> nameCol;

    @FXML
    private TableColumn<BaseAccount, String> phoneNumberCol;

    @FXML
    private TableColumn<BaseAccount, String> typeCol;

    @FXML
    private TableColumn<BaseAccount, Address> addressCol;

    @FXML
    private TableColumn<BaseAccount, LocalDateTime> joinTimeCol;

    @FXML
    private TableColumn<BaseAccount, Long> balanceCol;

    @FXML
    private TableColumn<BaseAccount, Boolean> statusCol;

    @FXML
    private AnchorPane bottomBar;

    // Get all buttons from ctrlBottomBar
    ObservableList<JFXButton> bottomBarButtons;

    // set formatter for datetime fields in table
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    private final ObservableList<BaseAccount> dataOfTable = DataTest.getAccountList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // setup bottomBar
        BottomBar insBottomBar = new BottomBar();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/bottomBar.fxml"));
            loader.setController(insBottomBar);
            bottomBar.getChildren().add(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        bottomBarButtons = insBottomBar.fetchAllButtons();

        // set text for title
        insBottomBar.fetchLabelTitle().get(0).setText("Account Management");

        // set handler click for buttons in bottom bar
        setHandleForClickButtonInBottomBar();

        // init
        initTable();
        setEventDoubleClickOnTable(accountTable);

    }

    private void initTable() {
        // ID COL SETTING
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        // NAME COL SETTING
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // PHONE NUMBER COL SETTING
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // TYPE COL SETTING
        typeCol.setCellValueFactory(new PropertyValueFactory<>("typeAccount"));

        // ADDRESS COL SETTING
        addressCol.setCellValueFactory(new PropertyValueFactory<BaseAccount, Address>("address"));

        // ---- setting the cell factory for the address column
        Callback<TableColumn<BaseAccount, Address>, TableCell<BaseAccount, Address>> cellAddressFactory = param -> {
            final TableCell<BaseAccount, Address> addressCell = new TableCell<BaseAccount, Address>() {
                @Override
                public void updateItem(Address item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(null);
                        setText(String.format("%s - %s", item.getDistrict(), item.getProvince()));
                    }
                }
            };
            return addressCell;
        };
        addressCol.setCellFactory(cellAddressFactory);

        // JOIN TIME COL SETTING
        joinTimeCol.setCellValueFactory(new PropertyValueFactory<BaseAccount, LocalDateTime>("JoinTime"));
        joinTimeCol.setCellFactory(col -> new TableCell<BaseAccount, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(String.format(item.format(formatter)));
            }
        });

        // BALANCE AMOUNT COL SETTING
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("amountBalance"));

        // ---- setting the Amount storage column
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        balanceCol.setCellFactory(ms -> new TableCell<BaseAccount, Long>() {

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

        // STATUS COL SETTING
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // ---- setting the cell factory for the status column
        Callback<TableColumn<BaseAccount, Boolean>, TableCell<BaseAccount, Boolean>> cellStatusFactory = param -> {

            final TableCell<BaseAccount, Boolean> cell = new TableCell<BaseAccount, Boolean>() {

                final JFXButton btn = new JFXButton("");

                @Override
                public void updateItem(Boolean item, boolean empty) {

                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        String STANDARD_BUTTON_STYLE;
                        String HOVERED_BUTTON_STYLE;
                        setGraphic(btn);
                        setText(null);
                        if (item) {
                            btn.setText("UnLock");
                            STANDARD_BUTTON_STYLE = "-fx-background-color: transparent;";
                            HOVERED_BUTTON_STYLE = "-fx-background-color: linear-gradient(to bottom, #3884d1, #0c75d6, #0064d8, #0051d8, #233ad3);";
                        } else {
                            btn.setText("Lock");
                            STANDARD_BUTTON_STYLE = "-fx-background-color: transparent;";
                            HOVERED_BUTTON_STYLE = "-fx-background-color: linear-gradient(to bottom, #787b7e, #808486, #898d8d, #939695, #9e9f9d);";
                        }

                        // set style hover of btn
                        btn.styleProperty()
                                .bind(Bindings.when(btn.hoverProperty())
                                        .then(new SimpleStringProperty(HOVERED_BUTTON_STYLE))
                                        .otherwise(new SimpleStringProperty(STANDARD_BUTTON_STYLE)));
                    }

                    btn.setOnAction(event -> {
                        // AccessDB : update status account when click button
                        BaseAccount newAccount = accountTable.getItems().get(getIndex());
                        newAccount.setStatus(!item.booleanValue());
                        dataOfTable.set(getIndex(), newAccount);

                        // autoselect row when click button
                        accountTable.getSelectionModel().clearSelection();
                        accountTable.getSelectionModel().select(getIndex());
                    });
                }
            };

            return cell;
        };

        statusCol.setCellFactory(cellStatusFactory);

        accountTable.setItems(dataOfTable);
        accountTable.getSelectionModel().select(dataOfTable.get(0));

        // set up for search box
        addTextFilter(dataOfTable, searchBox, accountTable);
    }

    private void setEventDoubleClickOnTable(TableView<BaseAccount> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<BaseAccount> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BaseAccount rowData = row.getItem();
                    viewInfoAccount(rowData);
                }
            });
            return row;
        });
    }

    private void viewInfoAccount(BaseAccount account) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/viewInfoAccount.fxml"));
        viewInfoAccount viewInfoAccountCtrl = new viewInfoAccount();
        loader.setController(viewInfoAccountCtrl);

        JFXScrollPane viewInfoPane = new JFXScrollPane();
        try {
            viewInfoPane.setContent(loader.load());

            Label title = new Label("Account Info");
            viewInfoPane.getBottomBar().getChildren().add(title);
            title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
            JFXScrollPane.smoothScrolling((ScrollPane) viewInfoPane.getChildren().get(0));

            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(viewInfoPane, 550, 700));
            viewInfoAccountCtrl.showAccount(account);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addTextFilter(ObservableList<BaseAccount> allData, JFXTextField filterField,
            TableView<BaseAccount> table) {
        FilteredList<BaseAccount> filteredData = new FilteredList<>(allData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(account -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                // Filter matches first ID.
                if (account.getID().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches first NAME.
                } else if (account.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches first NAME.
                } else if (account.getPhoneNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches TYPE ACCOUNT.
                } else if (account.getTypeAccount().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches province.
                } else if (account.getAddress().getProvince().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches join time.
                } else if (String.format(account.getJoinTime().format(formatter)).toLowerCase()
                        .indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches amount balance.
                } else if (account.getAmountBalance().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<BaseAccount> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    private void setHandleForClickButtonInBottomBar() {
        for (JFXButton button : bottomBarButtons) {
            switch (button.getId()) {
                case "addBtn":
                    button.setOnAction(actionEvent -> {
                        BaseAccount newAccount = editAccount(new BaseAccount());
                        if (newAccount != null) {
                            dataOfTable.add(newAccount);
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully added an Account");
                            alert.showAndWait();
                        }
                    });
                    break;
                case "editBtn":
                    button.setOnAction(actionEvent -> {
                        if (accountTable.getSelectionModel().isEmpty()) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Failed");
                            alert.setHeaderText(null);
                            alert.setContentText("Please select account before editing");
                            alert.showAndWait();
                        } else {
                            BaseAccount newAccount = editAccount(accountTable.getSelectionModel().getSelectedItem());
                            if (newAccount != null) {
                                dataOfTable.set(accountTable.getSelectionModel().getSelectedIndex(), newAccount);
                            }
                        }
                    });
                    break;
                case "deleteBtn":
                    button.setOnAction(actionEvent -> {
                        if (accountTable.getSelectionModel().isEmpty()) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Failed");
                            alert.setHeaderText(null);
                            alert.setContentText("Please select account before deleting");
                            alert.showAndWait();
                        } else {
                            dataOfTable.remove(accountTable.getSelectionModel().getSelectedIndex());
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully deleted an Account");
                            alert.showAndWait();
                        }
                    });
            }
        }
    }

    private BaseAccount editAccount(BaseAccount account) {

        BaseAccount newAccount = new BaseAccount();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/editAccountForm.fxml"));
        editAccountForm editAccountFormCtrl = new editAccountForm();
        loader.setController(editAccountFormCtrl);

        JFXScrollPane editAccountFormPane = new JFXScrollPane();
        try {
            editAccountFormPane.setContent(loader.load());

            Label title = new Label("Account Info");
            editAccountFormPane.getBottomBar().getChildren().add(title);
            title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
            JFXScrollPane.smoothScrolling((ScrollPane) editAccountFormPane.getChildren().get(0));

            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(editAccountFormPane, 500, 700));
            editAccountFormCtrl.editAccount(account);
            newStage.showAndWait();

            newAccount = editAccountFormCtrl.getNewAccount();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return newAccount;
    }
}
