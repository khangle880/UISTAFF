package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import classComponent.AutoCompleteComboBoxListener;
import classComponent.MyNumberStringConverter;
import classComponent.Util;
import classForDB.Address;
import classForDB.Atm;

public class OS implements Initializable {

    @FXML
    private AnchorPane osPane;

    @FXML
    private JFXTextField searchBox;

    @FXML
    private TableView<Atm> atmTable;

    @FXML
    private TableColumn<Atm, String> codeCol;

    @FXML
    private TableColumn<Atm, String> nameCol;

    @FXML
    private TableColumn<Atm, Address> addressCol;

    @FXML
    private TableColumn<Atm, Long> moneyCol;

    @FXML
    private TableColumn<Atm, Boolean> statusCol;

    @FXML
    private AnchorPane bottomBar;

    @FXML
    private JFXTextField textCode;

    @FXML
    private JFXTextField textName;

    @FXML
    private JFXTextField textMoneyStorage;

    @FXML
    private JFXTextField textAddress;

    @FXML
    private JFXComboBox<String> comboBoxWard;

    @FXML
    private JFXComboBox<String> comboBoxDistrict;

    @FXML
    private JFXComboBox<String> comboBoxProvince;

    @FXML
    private JFXToggleButton toggleActive;

    // Get all buttons from ctrlBottomBar
    ObservableList<JFXButton> bottomBarButtons;

    // text formatter for currency
    TextFormatter<Number> textFormatterCurrency;

    private final ObservableList<Atm> dataOfTable = FXCollections.observableArrayList(
            new Atm("ab6632", "Atm so 3", new Address("26", "dong hoa", "Di an", "Binh duong"), 2200000L, false),
            new Atm("ab6633", "Atm so 4", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6634", "Atm so 5", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6635", "Atm so 6", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true),
            new Atm("ab6636", "Atm so 7", new Address("26", "Dong hoa", "Di an", "Binh duong"), 2200000L, true));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        insBottomBar.fetchLabelTitle().get(0).setText("Operating system");

        // set handler click for buttons in bottom bar
        setHandleForClickButtonInBottomBar();

        // setting textField moneyStorage to currencyFormat
        setTextFieldCurrencyFormat(textMoneyStorage);

        // setting filter and auto complete of comboBox
        new AutoCompleteComboBoxListener<>(comboBoxWard);
        new AutoCompleteComboBoxListener<>(comboBoxDistrict);
        new AutoCompleteComboBoxListener<>(comboBoxProvince);

        // init
        setValueFromTableToTextField();
        initTable();
        bindingDataForComboBox();

    }

    private void setTextFieldCurrencyFormat(JFXTextField textField) {

        // Setting up the textField with a Formatter
        NumberFormat nFormat = NumberFormat.getInstance();

        // Define the integer and fractional digits
        nFormat.setMinimumIntegerDigits(1);
        nFormat.setMaximumFractionDigits(2);

        // setting up the TextFormatter with the NumberFormat and a Filter to limit the
        // inputChars
        textFormatterCurrency = new TextFormatter<>(new MyNumberStringConverter(nFormat), 0l, Util.createFilter());

        textField.setTextFormatter(textFormatterCurrency);
    }

    // * Initialize table view

    public void initTable() {

        // CODE COL SETTING
        codeCol.setPrefWidth(100);
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));

        // NAME COL SETTING
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // MONEY COL SETTING
        moneyCol.setPrefWidth(150);
        moneyCol.setCellValueFactory(new PropertyValueFactory<>("moneyStorage"));

        // ---- setting the money storage column
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        moneyCol.setCellFactory(ms -> new TableCell<Atm, Long>() {

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

        // ADDRESS COL SETTING
        addressCol.setCellValueFactory(new PropertyValueFactory<Atm, Address>("address"));

        // ---- setting the cell factory for the address column
        Callback<TableColumn<Atm, Address>, TableCell<Atm, Address>> cellAddressFactory = param -> {
            final TableCell<Atm, Address> addressCell = new TableCell<Atm, Address>() {
                @Override
                public void updateItem(Address item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(null);
                        setText(String.format("%s - %s - %s - %s", item.getCompleteStreet(), item.getWard(),
                                item.getDistrict(), item.getProvince()));
                    }
                }
            };
            return addressCell;
        };
        addressCol.setCellFactory(cellAddressFactory);

        // STATUS COL SETTING
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // ---- setting the cell factory for the status column
        Callback<TableColumn<Atm, Boolean>, TableCell<Atm, Boolean>> cellStatusFactory = param -> {

            final TableCell<Atm, Boolean> cell = new TableCell<Atm, Boolean>() {

                final JFXButton btn = new JFXButton("");

                @Override
                public void updateItem(Boolean item, boolean empty) {

                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        setGraphic(btn);
                        setText(null);
                        if (item)
                            btn.setText("Active");
                        else
                            btn.setText("Pause");
                    }

                    btn.setOnAction(event -> {
                        // AccessDB : update status atm when click button
                        Atm newAtm = atmTable.getItems().get(getIndex());
                        newAtm.setStatus(!item.booleanValue());
                        dataOfTable.set(getIndex(), newAtm);

                        // ! change to toggle button

                        // autoselect row when click button
                        atmTable.getSelectionModel().clearSelection();
                        atmTable.getSelectionModel().select(getIndex());
                    });
                }
            };

            return cell;
        };

        statusCol.setCellFactory(cellStatusFactory);

        atmTable.setItems(dataOfTable);
        atmTable.getSelectionModel().select(dataOfTable.get(0));

        // set up for search box
        addTextFilter(dataOfTable, searchBox, atmTable);

    }

    public static <T> void addTextFilter(ObservableList<Atm> allData, JFXTextField filterField, TableView<Atm> table) {

        FilteredList<Atm> filteredData = new FilteredList<>(allData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(atm -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (atm.getCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches first code.
                } else if (atm.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches name.
                } else if (atm.getAddress().getProvince().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches province.
                } else if (atm.getAddress().getDistrict().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches district.
                } else if (atm.getAddress().getWard().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches ward.
                } else if (atm.getAddress().getCompleteStreet().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches complete street.
                } else if (atm.getMoneyStorage().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                    // Filter matches money storage.
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Atm> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    // AccessDB : update data when add edit delete

    private void setHandleForClickButtonInBottomBar() {
        for (JFXButton button : bottomBarButtons) {
            switch (button.getId()) {
                case "addBtn":
                    button.setOnAction(actionEvent -> {
                        if (checkForNewData()) {
                            Atm newAtm = new Atm();
                            newAtm.setCode(textCode.getText());
                            newAtm.setName(textName.getText());
                            newAtm.setMoneyStorage(Long.parseLong(
                                    textMoneyStorage.getTextFormatter().valueProperty().getValue().toString()) / 100);
                            newAtm.setAddress(new Address(textAddress.getText(), comboBoxWard.getValue(),
                                    comboBoxDistrict.getValue(), comboBoxProvince.getValue()));
                            newAtm.setStatus(toggleActive.isSelected());
                            dataOfTable.add(newAtm);
                            clearField();
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully added an atm");
                            alert.showAndWait();
                        }
                    });
                    break;
                case "editBtn":
                    button.setOnAction(actionEvent -> {
                        if (atmTable.getSelectionModel().isEmpty()) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Failed");
                            alert.setHeaderText(null);
                            alert.setContentText("Please select atm before editing");
                            alert.showAndWait();
                        } else if (checkForNewData()) {
                            Atm newAtm = new Atm();
                            newAtm.setCode(textCode.getText());
                            newAtm.setName(textName.getText());
                            newAtm.setMoneyStorage(Long.parseLong(
                                    textMoneyStorage.getTextFormatter().valueProperty().getValue().toString()) / 100);
                            newAtm.setAddress(new Address(textAddress.getText(), comboBoxWard.getValue(),
                                    comboBoxDistrict.getValue(), comboBoxProvince.getValue()));
                            newAtm.setStatus(toggleActive.isSelected());
                            dataOfTable.set(atmTable.getSelectionModel().getSelectedIndex(), newAtm);
                            clearField();
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully edited an atm");
                            alert.showAndWait();
                        }
                    });
                    break;
                case "deleteBtn":
                    button.setOnAction(actionEvent -> {
                        if (atmTable.getSelectionModel().isEmpty()) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Failed");
                            alert.setHeaderText(null);
                            alert.setContentText("Please select atm before deleting");
                            alert.showAndWait();
                        } else {
                            dataOfTable.remove(atmTable.getSelectionModel().getSelectedIndex());
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully deleted an atm");
                            alert.showAndWait();
                        }
                    });
            }
        }
    }

    // bind data address
    public void bindingDataForComboBox() {

        // ---- Get JSON for comboBox

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/resources/resource/data.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject addrDict = (JSONObject) obj;

            initDataEnableBindingComboBox(addrDict);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // comboBoxWard.getItems().addAll("Dong hoa", "Item 2", "Item 3");
        // comboBoxDistrict.getItems().addAll("Di an", "Item 2", "Item 3");
        // comboBoxProvince.getItems().addAll("Binh duong", "Item 2", "Item 3");
    }

    // parse address dictionary data from json
    public void initDataEnableBindingComboBox(JSONObject addrDict) {

        // ---- convert json object to map object with only name string
        List provinceList = new ArrayList();

        // get array province in Json file
        JSONArray provinceJsonList = (JSONArray) addrDict.get("Province/City");

        // get name province add into list
        for (Object province : provinceJsonList) {
            provinceList.add(((JSONObject) province).get("Name"));
        }

        final Map DistrictMap = new HashMap();

        for (int i = 0; i < provinceList.size(); i++) {
            List l = new ArrayList();

            // get array district per province in Json file
            JSONArray districtJsonList = (JSONArray) ((JSONObject) provinceJsonList.get(i)).get("District");

            // get name district add into list
            for (Object district : districtJsonList) {
                l.add(((JSONObject) district).get("Name"));
            }
            DistrictMap.put(provinceList.get(i), l);
        }

        final Map WardMap = new HashMap();
        for (int i = 0; i < provinceList.size(); i++) {
            Object districts = DistrictMap.get(provinceList.get(i));
            int numberDictrict = ((ArrayList) districts).size();
            // get array district per province in Json file
            JSONArray districtJsonList = (JSONArray) ((JSONObject) provinceJsonList.get(i)).get("District");
            for (int j = 0; j < numberDictrict; j++) {
                Object district = ((ArrayList) districts).get(j);
                List l = new ArrayList();
                // get array ward per district in Json file
                JSONArray wardJsonList = (JSONArray) ((JSONObject) districtJsonList.get(j)).get("Ward");
                // get name ward add into list
                for (Object ward : wardJsonList) {
                    l.add(((JSONObject) ward).get("Name"));
                }
                WardMap.put(district, l);
            }
        }

        // binding data to comboBox
        ObservableList combox1 = FXCollections.observableList(provinceList);
        comboBoxProvince.getItems().clear();
        comboBoxProvince.getItems().addAll(combox1);

        comboBoxProvince.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !comboBoxProvince.getSelectionModel().isEmpty()) {
                ObservableList combox2 = FXCollections.observableArrayList((List) DistrictMap.get(newValue));
                comboBoxDistrict.getItems().clear();
                comboBoxDistrict.getItems().addAll(combox2);
            }
        });

        comboBoxDistrict.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !comboBoxDistrict.getSelectionModel().isEmpty()) {
                ObservableList combox3 = FXCollections.observableArrayList((List) WardMap.get(newValue));
                comboBoxWard.getItems().clear();
                comboBoxWard.getItems().addAll(combox3);
            }
        });
    }

    // * setting for binding value when selected row

    public void setValueFromTableToTextField() {
        atmTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Atm atm = atmTable.getItems().get(atmTable.getSelectionModel().getSelectedIndex());
                textCode.setText(atm.getCode());
                textName.setText(atm.getName());
                textMoneyStorage.setText(atm.getMoneyStorage().toString());
                textAddress.setText(atm.getAddress().getCompleteStreet());
                comboBoxWard.getSelectionModel().select((atm.getAddress().getWard().toString()));
                comboBoxDistrict.getSelectionModel().select((atm.getAddress().getDistrict().toString()));
                comboBoxProvince.getSelectionModel().select((atm.getAddress().getProvince().toString()));
                toggleActive.setSelected(atm.getStatus());
            }
        });
    }

    @FXML
    void checkValueMoneyText(KeyEvent event) {

        // ---- force the textField money storage to be numeric only
        if (!textMoneyStorage.getText().matches("\\d*")) {
            textMoneyStorage.setText(textMoneyStorage.getText().replaceAll("[^\\d]", ""));
        }

    }

    // ---- clear data when action for fillTable
    void clearField() {

        atmTable.getSelectionModel().clearSelection();
        textCode.clear();
        textName.clear();
        textMoneyStorage.setText("0");
        textAddress.clear();
        comboBoxWard.setValue(null);
        comboBoxDistrict.setValue(null);
        comboBoxProvince.setValue(null);
        toggleActive.setSelected(false);
    }

    // ---- check new data is valid
    boolean checkForNewData() {
        // todo: check for primary key

        boolean isValid = true;
        String errorText = new String("You can not empty fields : ");

        // -- check for text code
        if (textCode.getText().isEmpty()) {
            errorText += "Code - ";
            isValid = false;
        }

        // -- check for text name
        if (textName.getText().isEmpty()) {
            errorText += "Name - ";
            isValid = false;
        }

        // -- check for text address
        if (textAddress.getText().isEmpty()) {
            errorText += "Address - ";
            isValid = false;
        }

        // -- check for select Ward
        if (comboBoxWard.getSelectionModel().isEmpty()) {
            System.out.println("fuck");
            errorText += "Ward - ";
            isValid = false;
        }

        // -- check for select district
        if (comboBoxDistrict.getSelectionModel().isEmpty()) {
            errorText += "District - ";
            isValid = false;
        }

        // -- check for text address
        if (comboBoxProvince.getSelectionModel().isEmpty()) {
            errorText += "Province - ";
            isValid = false;
        }

        if (!isValid) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText(errorText + "!");
            alert.showAndWait();
        }

        return isValid;
    }
}