package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalTime;
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
import classComponent.MyNumberStringConverter;
import classComponent.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import classForDB.Address;
import classForDB.BaseAccount;
import classForDB.CustomerAccount;
import classForDB.EmployeeAccount;
import classForDB.OrganizationAccount;

public class editAccountForm implements Initializable {

    @FXML
    private VBox editForm;

    @FXML
    private GridPane baseInfoPane;

    @FXML
    private JFXTextField textID;

    @FXML
    private JFXTextField textName;

    @FXML
    private JFXTextField textEmail;

    @FXML
    private JFXTextField textPhoneNumber;

    @FXML
    private JFXTextField textMoneyBalance;

    @FXML
    private JFXTextField textAddress;

    @FXML
    private JFXComboBox<String> cbbProvince;

    @FXML
    private JFXComboBox<String> cbbDistrict;

    @FXML
    private JFXComboBox<String> cbbWard;

    @FXML
    private JFXComboBox<String> cbbTypeAccount;

    @FXML
    private JFXDatePicker dateTime;

    @FXML
    private JFXToggleButton toggleStatus;

    @FXML
    private GridPane addInfoPane;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton okBtn;

    // ---- force the textField money storage to be numeric only
    @FXML
    void checkValueMoneyText(KeyEvent event) {
        if (!textMoneyBalance.getText().matches("\\d*")) {
            textMoneyBalance.setText(textMoneyBalance.getText().replaceAll("[^\\d]", ""));
        }
    }

    // text formatter for currency
    TextFormatter<Number> textFormatterCurrency;

    // ---- Variable global
    // variable for constructor
    BaseAccount newAccount = new BaseAccount();

    // variable check account is valid
    private boolean AccountIsValid;
    private String AccountErrorText;

    public BaseAccount getNewAccount() {
        return newAccount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // setting textField moneyStorage to currencyFormat
        setTextFieldCurrencyFormat(textMoneyBalance);

        // AccessDB : set data comboBox type account
        cbbTypeAccount.getItems().addAll("Customer", "Employee", "Organization");

        cbbTypeAccount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (cbbTypeAccount.getSelectionModel().isEmpty()) {
                toBaseAccountForm();
            }
            switch (cbbTypeAccount.getSelectionModel().getSelectedItem()) {
                case "Customer":
                    toCustomerAccountForm();
                    break;
                case "Employee":
                    toEmployeeAccountForm();
                    break;
                case "Organization":
                    toOrganizationAccountForm();
                    break;

                default:
                    break;
            }
        });

        initValidatorForBaseInfoForm();
        setUpFormAutoSize();
        bindingDataForComboBoxAddress();

        // setting filter and auto complete of comboBox
        new AutoCompleteComboBoxListener<>(cbbWard);
        new AutoCompleteComboBoxListener<>(cbbDistrict);
        new AutoCompleteComboBoxListener<>(cbbProvince);
    }

    // bind data address
    public void bindingDataForComboBoxAddress() {

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
        cbbProvince.getItems().clear();
        cbbProvince.getItems().addAll(combox1);

        cbbProvince.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbbProvince.getSelectionModel().isEmpty()) {
                ObservableList combox2 = FXCollections.observableArrayList((List) DistrictMap.get(newValue));
                cbbWard.getItems().clear();
                cbbDistrict.getItems().clear();
                cbbDistrict.getItems().addAll(combox2);
            }
        });

        cbbDistrict.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !cbbDistrict.getSelectionModel().isEmpty()) {
                ObservableList combox3 = FXCollections.observableArrayList((List) WardMap.get(newValue));
                cbbWard.getItems().clear();
                cbbWard.getItems().addAll(combox3);
            }
        });
    }

    // validator for comboxBox selection
    private void setValidatorSelectedForCbb(JFXComboBox<String> cbb) {
        ValidatorBase validatorSelected = new ValidatorBase() {
            @Override
            protected void eval() {
                setMessage("ComboBox is not selected!");
                if (cbb.getSelectionModel().isEmpty()) {
                    hasErrors.set(true);
                } else
                    hasErrors.set(false);
            }
        };

        cbb.getValidators().add(validatorSelected);
    }

    // initialize validator for nodes in base info form
    private void initValidatorForBaseInfoForm() {
        // create validators
        RequiredFieldValidator validatorText = new RequiredFieldValidator();
        NumberValidator validatorNumber = new NumberValidator();
        RegexValidator validatorEmail = new RegexValidator();
        validatorEmail.setRegexPattern(
                "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // set text for validators
        validatorText.setMessage("No Input Given!");
        validatorNumber.setMessage("Only Numbers are supported!");
        validatorEmail.setMessage("Email is not valid!");

        // set validator for Nodes
        textID.getValidators().add(validatorText);
        textName.getValidators().add(validatorText);
        textEmail.getValidators().addAll(validatorText, validatorEmail);
        textPhoneNumber.getValidators().addAll(validatorText, validatorNumber);
        textMoneyBalance.getValidators().add(validatorText);
        textAddress.getValidators().add(validatorText);
        cbbProvince.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbProvince);
        cbbDistrict.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbDistrict);
        cbbWard.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbWard);
        cbbTypeAccount.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbTypeAccount);
        dateTime.getValidators().add(validatorText);

        // set event exit focus for Nodes
        addListenerNotFocus(textID);
        addListenerNotFocus(textName);
        addListenerNotFocus(textEmail);
        addListenerNotFocus(textPhoneNumber);
        addListenerNotFocus(textMoneyBalance);
        addListenerNotFocus(textAddress);
        addListenerNotFocus(cbbProvince);
        addListenerNotFocus(cbbDistrict);
        addListenerNotFocus(cbbWard);
        addListenerNotFocus(cbbTypeAccount);
        addListenerNotFocus(dateTime);

    }

    // Create addListener for event exit focus a node
    private void addListenerNotFocus(Node node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    AccountIsValid = (((IFXValidatableControl) node).validate() == false ? false : AccountIsValid);
                }
            }

        });
    }

    // travel through the nodes in editForm
    private void travelThoughNodesInEditForm() {
        // travel through the nodes of base info form
        ObservableList<Node> childrenNodeOfBasePane = baseInfoPane.getChildren();
        for (Node node : childrenNodeOfBasePane) {
            node.requestFocus();
        }

        // travel through the nodes of additional info form
        ObservableList<Node> childrenNodeOfAddPane = addInfoPane.getChildren();
        for (Node node : childrenNodeOfAddPane) {
            node.requestFocus();
        }
        editForm.requestFocus();
    }

    // set for form enable auto size
    private void setUpFormAutoSize() {
        // set for baseInfoPane
        baseInfoPane.setHgap(35);
        baseInfoPane.setVgap(45);
        baseInfoPane.setPadding(new Insets(30, 0, 0, 40));

        ColumnConstraints leftColBaseInfoPane = new ColumnConstraints();
        leftColBaseInfoPane.setHalignment(HPos.RIGHT);
        ColumnConstraints rightColBaseInfoPane = new ColumnConstraints();
        rightColBaseInfoPane.setHgrow(Priority.SOMETIMES);
        baseInfoPane.getColumnConstraints().addAll(leftColBaseInfoPane, rightColBaseInfoPane);

        // set for addInfoPane
        addInfoPane.setHgap(35);
        addInfoPane.setVgap(25);
        addInfoPane.setPadding(new Insets(30, 0, 0, 40));

        ColumnConstraints leftColAddInfoPane = new ColumnConstraints();
        leftColAddInfoPane.setHalignment(HPos.RIGHT);
        ColumnConstraints rightColAddInfoPane = new ColumnConstraints();
        rightColAddInfoPane.setHgrow(Priority.SOMETIMES);
        addInfoPane.getColumnConstraints().addAll(leftColAddInfoPane, rightColAddInfoPane);
    }

    // Edit a Account with handle pass from table view
    public void editAccount(BaseAccount account) {
        if (account.getTypeAccount() == null)
            return;
            
        switch (account.getTypeAccount()) {
            case "Customer":
                newAccount = new CustomerAccount();
                break;
            case "Employee":
                newAccount = new EmployeeAccount();
                break;
            case "Organization":
                newAccount = new OrganizationAccount();
                break;
            default:
                break;
        }
        newAccount = account;

        textID.setText(newAccount.getID());
        textName.setText(newAccount.getName());
        textEmail.setText(newAccount.getEmail());
        textPhoneNumber.setText(newAccount.getPhoneNumber());
        textMoneyBalance.setText(newAccount.getMoneyBalance().toString());
        textAddress.setText(newAccount.getAddress().getCompleteStreet());
        cbbWard.getSelectionModel().select((newAccount.getAddress().getWard()));
        cbbDistrict.getSelectionModel().select((newAccount.getAddress().getDistrict()));
        cbbProvince.getSelectionModel().select((newAccount.getAddress().getProvince()));
        cbbTypeAccount.getSelectionModel().select((newAccount.getTypeAccount()));
        toggleStatus.setSelected(newAccount.getStatus());

        ObservableList<Node> childrenNodeOfAddPane = addInfoPane.getChildren();
        ObservableList<Node> nodesEditableOfAddPane = FXCollections.observableArrayList();
        for (Node node : childrenNodeOfAddPane) {
            if (!(node instanceof Label))
                nodesEditableOfAddPane.add(node);
        }

        switch (cbbTypeAccount.getValue()) {
            case "Customer": {
                ((JFXTextField) nodesEditableOfAddPane.get(0)).setText(((CustomerAccount) newAccount).getIdentifyID());
                ((JFXTextField) nodesEditableOfAddPane.get(1))
                        .setText(((CustomerAccount) newAccount).getMobilePhoneNumber());
                ((JFXTextField) nodesEditableOfAddPane.get(2)).setText(((CustomerAccount) newAccount).getCardID());
                ((JFXTextField) nodesEditableOfAddPane.get(3)).setText(((CustomerAccount) newAccount).getPINNumber());
                ((JFXComboBox<String>) nodesEditableOfAddPane.get(4)).getSelectionModel()
                        .select(((CustomerAccount) newAccount).getTypeCard());
                ((JFXDatePicker) nodesEditableOfAddPane.get(5))
                        .setValue(((CustomerAccount) newAccount).getCreationDate().toLocalDate());
                ((JFXDatePicker) nodesEditableOfAddPane.get(6))
                        .setValue(((CustomerAccount) newAccount).getExpiryDate().toLocalDate());
            }
                break;
            case "Employee": {
                ((JFXTextField) nodesEditableOfAddPane.get(0)).setText(((EmployeeAccount) newAccount).getIdentifyID());
                ((JFXTextField) nodesEditableOfAddPane.get(1))
                        .setText(((EmployeeAccount) newAccount).getMobilePhoneNumber());
                ((JFXTextField) nodesEditableOfAddPane.get(2)).setText(((EmployeeAccount) newAccount).getPassword());
                ((JFXComboBox<String>) nodesEditableOfAddPane.get(3)).getSelectionModel()
                        .select(((EmployeeAccount) newAccount).getPosition());
            }
                break;
            case "Organization": {
                ((JFXTextField) nodesEditableOfAddPane.get(0))
                        .setText(((OrganizationAccount) newAccount).getPassword());
                ((JFXTextField) nodesEditableOfAddPane.get(1))
                        .setText(((OrganizationAccount) newAccount).getDescription());
                ((JFXTextField) nodesEditableOfAddPane.get(2)).setText(((OrganizationAccount) newAccount).getRating());
            }
                break;
            default:
                break;
        }
    }

    // check new data account is valid
    private boolean checkForNewData() {
        // todo: check for primary key
        AccountIsValid = true;
        AccountErrorText = new String("New information of account not valid");
        travelThoughNodesInEditForm();

        if (!AccountIsValid) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText(AccountErrorText + "!");
            alert.showAndWait();
        }

        return AccountIsValid;
    }

    // set text field with currency format
    private void setTextFieldCurrencyFormat(JFXTextField textField) {

        // Setting up the textField with a Formatter
        NumberFormat nFormat = NumberFormat.getInstance();

        // Define the integer and fractional digits
        nFormat.setMinimumIntegerDigits(1);
        nFormat.setMaximumFractionDigits(2);

        // setting up the TextFormatter with the NumberFormat and a Filter to limit the
        textFormatterCurrency = new TextFormatter<>(new MyNumberStringConverter(nFormat), 0l, Util.createFilter());

        textField.setTextFormatter(textFormatterCurrency);
    }

    // convert additional info pane to base
    private void toBaseAccountForm() {
        addInfoPane.getChildren().clear();
    }

    // convert additional info pane to form for customer
    private void toCustomerAccountForm() {
        toBaseAccountForm();

        // Create new nodes
        Label titleLabel = new Label("Additional Info");
        Label identifyIDLabel = new Label("Identify ID :");
        Label mobilePhoneLabel = new Label("Mobile Phone :");
        Label cardIDLabel = new Label("Card ID :");
        Label pinNumberLabel = new Label("PIN Number :");
        Label typeCardLabel = new Label("Type Card :");
        Label creationLabel = new Label("Creation Date :");
        Label expiryLabel = new Label("Expiry Date :");

        JFXTextField textIdentifyID = new JFXTextField("");
        JFXTextField textMobilePhone = new JFXTextField("");
        JFXTextField textCardID = new JFXTextField("");
        JFXTextField textPinNumber = new JFXTextField("");
        JFXComboBox<String> cbbTypeCard = new JFXComboBox<String>();
        JFXDatePicker dateTimeCreation = new JFXDatePicker();
        JFXDatePicker dateTimeExpiry = new JFXDatePicker();

        dateTimeCreation.setPrefWidth(400);
        dateTimeExpiry.setPrefWidth(400);
        cbbTypeCard.setPrefWidth(400);
        // AccessDB: set data for type card comboBox
        cbbTypeCard.getItems().addAll("Student", "Vip", "Premium");

        textIdentifyID.setPromptText("Identify ID");
        textMobilePhone.setPromptText("Mobile Phone");
        textCardID.setPromptText("Card ID");
        textPinNumber.setPromptText("PIN Number");
        cbbTypeCard.promptTextProperty().set("Type Card");
        dateTimeCreation.promptTextProperty().set("Creation Date");
        dateTimeExpiry.promptTextProperty().set("Expiry Date");

        // create validators
        RequiredFieldValidator validatorText = new RequiredFieldValidator();
        NumberValidator validatorNumber = new NumberValidator();

        // set text for validators
        validatorText.setMessage("No Input Given!");
        validatorNumber.setMessage("Only Numbers are supported!");

        // set validator for Nodes
        textIdentifyID.getValidators().addAll(validatorText, validatorNumber);
        textMobilePhone.getValidators().addAll(validatorText, validatorNumber);
        textCardID.getValidators().addAll(validatorText, validatorNumber);
        textPinNumber.getValidators().addAll(validatorText, validatorNumber);
        cbbTypeCard.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbTypeCard);
        dateTimeCreation.getValidators().add(validatorText);
        dateTimeExpiry.getValidators().add(validatorText);

        // set event exit focus for Nodes
        addListenerNotFocus(textIdentifyID);
        addListenerNotFocus(textMobilePhone);
        addListenerNotFocus(textCardID);
        addListenerNotFocus(textPinNumber);
        addListenerNotFocus(cbbTypeCard);
        addListenerNotFocus(dateTimeCreation);
        addListenerNotFocus(dateTimeExpiry);

        // Add new nodes
        int i = 0;
        addInfoPane.addRow(i, titleLabel);
        addInfoPane.addRow(i + 1, identifyIDLabel, textIdentifyID);
        addInfoPane.addRow(i + 2, mobilePhoneLabel, textMobilePhone);
        addInfoPane.addRow(i + 3, cardIDLabel, textCardID);
        addInfoPane.addRow(i + 4, pinNumberLabel, textPinNumber);
        addInfoPane.addRow(i + 5, typeCardLabel, cbbTypeCard);
        addInfoPane.addRow(i + 6, creationLabel, dateTimeCreation);
        addInfoPane.addRow(i + 7, expiryLabel, dateTimeExpiry);
    }

    // convert additional info pane to form for employee
    private void toEmployeeAccountForm() {
        toBaseAccountForm();

        // Create new nodes
        Label titleLabel = new Label("Additional Info");
        Label identifyIDLabel = new Label("Identify ID :");
        Label mobilePhoneLabel = new Label("Mobile Phone :");
        Label passwordLabel = new Label("Password :");
        Label positionLabel = new Label("Position :");

        JFXTextField textIdentifyID = new JFXTextField("");
        JFXTextField textMobilePhone = new JFXTextField("");
        JFXTextField textPassword = new JFXTextField("");
        JFXComboBox<String> cbbPosition = new JFXComboBox<String>();

        cbbPosition.setPrefWidth(400);
        // AccessDB: set data for type card comboBox
        cbbPosition.getItems().addAll("Admin", "Mess Supporter", "OS Manage");

        textIdentifyID.setPromptText("Identify ID");
        textMobilePhone.setPromptText("Mobile Phone");
        textPassword.setPromptText("Password");
        cbbPosition.promptTextProperty().set("Position");

        // create validators
        RequiredFieldValidator validatorText = new RequiredFieldValidator();
        NumberValidator validatorNumber = new NumberValidator();

        // set text for validators
        validatorText.setMessage("No Input Given!");
        validatorNumber.setMessage("Only Numbers are supported!");

        // set validator for Nodes
        textIdentifyID.getValidators().addAll(validatorText, validatorNumber);
        textMobilePhone.getValidators().addAll(validatorText, validatorNumber);
        textPassword.getValidators().add(validatorText);
        cbbPosition.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbPosition);

        // set event exit focus for Nodes
        addListenerNotFocus(textIdentifyID);
        addListenerNotFocus(textMobilePhone);
        addListenerNotFocus(textPassword);
        addListenerNotFocus(cbbPosition);

        // Add new nodes
        int i = 0;
        addInfoPane.addRow(i, titleLabel);
        addInfoPane.addRow(i + 1, identifyIDLabel, textIdentifyID);
        addInfoPane.addRow(i + 2, mobilePhoneLabel, textMobilePhone);
        addInfoPane.addRow(i + 3, passwordLabel, textPassword);
        addInfoPane.addRow(i + 4, positionLabel, cbbPosition);

    }

    // convert additional info pane to form fo organization
    private void toOrganizationAccountForm() {
        toBaseAccountForm();

        Label titleLabel = new Label("Additional Info");
        Label passwordLabel = new Label("Password :");
        Label descriptionLabel = new Label("Description :");
        Label ratingLabel = new Label("Rating :");

        JFXTextField textPassword = new JFXTextField("");
        JFXTextField textDescription = new JFXTextField("");
        JFXTextField textRating = new JFXTextField("");

        textPassword.setPromptText("Password");
        textDescription.setPromptText("Description");
        textRating.setPromptText("Rating");

        // create validators
        RequiredFieldValidator validatorText = new RequiredFieldValidator();
        NumberValidator validatorNumber = new NumberValidator();

        // set text for validators
        validatorText.setMessage("No Input Given!");
        validatorNumber.setMessage("Only Numbers are supported!");

        // set validator for Nodes
        textPassword.getValidators().add(validatorText);
        textDescription.getValidators().add(validatorText);
        textRating.getValidators().add(validatorText);

        // set event exit focus for Nodes
        addListenerNotFocus(textPassword);
        addListenerNotFocus(textDescription);
        addListenerNotFocus(textRating);

        // Add new nodes
        int i = 0;
        addInfoPane.addRow(i, titleLabel);
        addInfoPane.addRow(i + 1, passwordLabel, textPassword);
        addInfoPane.addRow(i + 2, descriptionLabel, textDescription);
        addInfoPane.addRow(i + 3, ratingLabel, textRating);

    }

    @FXML
    void handleCancelEditForm(ActionEvent event) {
        // close window
        newAccount = null;
        Stage stage = (Stage) cancelBtn.getScene().getWindow();
        stage.close();

    }

    @FXML
    void handleOkEditForm(ActionEvent event) {

        if (checkForNewData()) {
            switch (cbbTypeAccount.getValue()) {
                case "Customer":
                    newAccount = new CustomerAccount();
                    break;
                case "Employee":
                    newAccount = new EmployeeAccount();
                    break;
                case "Organization":
                    newAccount = new OrganizationAccount();
                    break;
                default:
                    break;
            }

            newAccount.setID(textID.getText());
            newAccount.setName(textName.getText());
            newAccount.setEmail(textEmail.getText());
            newAccount.setPhoneNumber(textPhoneNumber.getText());
            newAccount.setMoneyBalance(
                    Long.parseLong(textMoneyBalance.getTextFormatter().valueProperty().getValue().toString()) / 100);
            newAccount.setAddress(new Address(textAddress.getText(), cbbWard.getValue(), cbbDistrict.getValue(),
                    cbbProvince.getValue()));
            newAccount.setTypeAccount(cbbTypeAccount.getValue());
            newAccount.setJoinTime(dateTime.getValue().atTime(LocalTime.now()));
            newAccount.setStatus(toggleStatus.isSelected());

            ObservableList<Node> childrenNodeOfAddPane = addInfoPane.getChildren();
            ObservableList<Node> nodesEditableOfAddPane = FXCollections.observableArrayList();
            for (Node node : childrenNodeOfAddPane) {
                if (!(node instanceof Label))
                    nodesEditableOfAddPane.add(node);
            }

            switch (cbbTypeAccount.getValue()) {
                case "Customer": {
                    ((CustomerAccount) newAccount)
                            .setIdentifyID(((JFXTextField) nodesEditableOfAddPane.get(0)).getText());
                    ((CustomerAccount) newAccount)
                            .setMobilePhoneNumber(((JFXTextField) nodesEditableOfAddPane.get(1)).getText());
                    ((CustomerAccount) newAccount).setCardID(((JFXTextField) nodesEditableOfAddPane.get(2)).getText());
                    ((CustomerAccount) newAccount)
                            .setPINNumber(((JFXTextField) nodesEditableOfAddPane.get(3)).getText());
                    ((CustomerAccount) newAccount)
                            .setTypeCard(((JFXComboBox<String>) nodesEditableOfAddPane.get(4)).getValue());
                    ((CustomerAccount) newAccount).setCreationDate(
                            ((JFXDatePicker) nodesEditableOfAddPane.get(5)).getValue().atTime(LocalTime.now()));
                    ((CustomerAccount) newAccount).setExpiryDate(
                            ((JFXDatePicker) nodesEditableOfAddPane.get(6)).getValue().atTime(LocalTime.now()));
                }
                    break;
                case "Employee": {
                    ((EmployeeAccount) newAccount)
                            .setIdentifyID(((JFXTextField) nodesEditableOfAddPane.get(0)).getText());
                    ((EmployeeAccount) newAccount)
                            .setMobilePhoneNumber(((JFXTextField) nodesEditableOfAddPane.get(1)).getText());
                    ((EmployeeAccount) newAccount)
                            .setPassword(((JFXTextField) nodesEditableOfAddPane.get(2)).getText());
                    ((EmployeeAccount) newAccount)
                            .setPosition(((JFXComboBox<String>) nodesEditableOfAddPane.get(3)).getValue());
                }
                    break;
                case "Organization": {
                    ((OrganizationAccount) newAccount)
                            .setPassword(((JFXTextField) nodesEditableOfAddPane.get(0)).getText());
                    ((OrganizationAccount) newAccount)
                            .setDescription(((JFXTextField) nodesEditableOfAddPane.get(1)).getText());
                    ((OrganizationAccount) newAccount)
                            .setRating(((JFXTextField) nodesEditableOfAddPane.get(2)).getText());
                }
                    break;
                default:
                    break;
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully edited an Account");
            alert.showAndWait();

            // close window
            Stage stage = (Stage) cancelBtn.getScene().getWindow();
            stage.close();
        }

    }
}
