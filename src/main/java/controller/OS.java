package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TreeItem;
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
import java.util.Iterator;
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

    private final ObservableList<Atm> data = FXCollections.observableArrayList(
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
        //bindDataFromTable();

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
                        data.set(getIndex(), newAtm);

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

        atmTable.setItems(data);
        atmTable.getSelectionModel().select(data.get(0));

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
                            data.add(newAtm);
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
                            data.set(atmTable.getSelectionModel().getSelectedIndex(), newAtm);
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
                            data.remove(atmTable.getSelectionModel().getSelectedIndex());
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
        
        try (FileReader reader = new FileReader("employees.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONObject addrDict = (JSONObject) obj;
            System.out.println(addrDict);
             
            //initDataEnableBindingComboBox(addrDict);
            //System.out.println(addDict.get());
            System.out.println();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // List combox1List = new ArrayList();
        // for (int i = 1; i < 10; i++) {
        // combox1List.add(i);
        // }

        // final Map combox2Map = new HashMap();

        // for (int i = 0; i < combox1List.size(); i++) {
        // List l = new ArrayList();
        // for (int j = 1; j < 10; j++) {
        // int k = (int) combox1List.get(i) * 10 + j;
        // l.add(k);
        // }
        // combox2Map.put(combox1List.get(i), l);
        // }
        // final Map combox3Map = new HashMap();
        // for (Object o : combox1List) {
        // for (Object o1 : (List) combox2Map.get(o)) {
        // List l = new ArrayList();
        // for (int i = 1; i < 10; i++) {
        // int value = (int) o1 * 10 + i;
        // l.add(value);
        // }
        // combox3Map.put(o1, l);
        // }
        // }
        // ObservableList combox1 = FXCollections.observableList(combox1List);
        // HBox box = new HBox(20);
        // box.setPadding(new Insets(20, 20, 20, 20));
        // ComboBox cb1 = new ComboBox();
        // final ComboBox cb2 = new ComboBox();
        // final ComboBox cb3 = new ComboBox();
        // cb1.setItems(combox1);
        // cb1.getSelectionModel().selectedItemProperty().addListener(new
        // ChangeListener() {
        // @Override
        // public void changed(ObservableValue ov, Object t, Object t1) {
        // ObservableList combox2 = FXCollections.observableArrayList((List)
        // combox2Map.get(t1));
        // cb2.setItems(combox2);
        // }
        // });

        // cb2.getSelectionModel().selectedItemProperty().addListener(new
        // ChangeListener() {
        // @Override
        // public void changed(ObservableValue ov, Object t, Object t1) {
        // if (t1 != null) {
        // ObservableList combox3 = FXCollections.observableArrayList((List)
        // combox3Map.get(t1));
        // cb3.setItems(combox3);
        // }
        // }
        // });

        // Quynh Luu

        // Quynh Trang
        // Quynh Vinh
        // Quynh Tho
        // Quynh Nu

        comboBoxWard.getItems().addAll("Dong hoa", "Item 2", "Item 3");
        comboBoxDistrict.getItems().addAll("Di an", "Item 2", "Item 3");
        comboBoxProvince.getItems().addAll("Binh duong", "Item 2", "Item 3");
    }

    // parse address dictionary data from json
    private static void initDataEnableBindingComboBox(JSONObject addrDict) {
		
        List provinceList = new ArrayList();
        JSONArray provinceJSONList = (JSONArray) addrDict.get("Province/City");
        // for (JSONObject province : provinceJSONList) {

        // }
        
        // provinceJSONList.forEach( emp -> {

        // } );
        
        final Map combox2Map = new HashMap();

        // for (int i = 0; i < combox1List.size(); i++) {
        //     List l = new ArrayList();
        //     for (int j = 1; j < 10; j++) {
        //         int k = (int) combox1List.get(i) * 10 + j;
        //         l.add(k);
        //     }
        //     combox2Map.put(combox1List.get(i), l);
        // }
        // final Map combox3Map = new HashMap();
        // for (Object o : combox1List) {
        //     for (Object o1 : (List) combox2Map.get(o)) {
        //         List l = new ArrayList();
        //         for (int i = 1; i < 10; i++) {
        //             int value = (int) o1 * 10 + i;
        //             l.add(value);
        //         }
        //         combox3Map.put(o1, l);
        //     }
        // }
        // ObservableList combox1 = FXCollections.observableList(combox1List);
        // HBox box = new HBox(20);
        // box.setPadding(new Insets(20, 20, 20, 20));
        // JFXComboBox cb1 = new JFXComboBox();
        // final JFXComboBox cb2 = new JFXComboBox();
        // final JFXComboBox cb3 = new JFXComboBox();
        // cb1.setItems(combox1);
        // cb1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        //     @Override
        //     public void changed(ObservableValue ov, Object t, Object t1) {
        //         ObservableList combox2 = FXCollections.observableArrayList((List) combox2Map.get(t1));
        //         cb2.setItems(combox2);
        //     }
        // });

        // cb2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        //     @Override
        //     public void changed(ObservableValue ov, Object t, Object t1) {
        //         if (t1 != null) {
        //             ObservableList combox3 = FXCollections.observableArrayList((List) combox3Map.get(t1));
        //             cb3.setItems(combox3);
        //         }
        //     }
        // });
        //box.getChildren().addAll(cb1, cb2, cb3);
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
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(errorText + "!");
            alert.showAndWait();
        }

        return isValid;
    }
}