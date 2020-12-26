package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import classForDB.Address;
import classForDB.BaseAccount;

public class editUserProfile implements Initializable {

    @FXML
    private VBox editProfileForm;

    @FXML
    private GridPane infoPane;

    @FXML
    private JFXTextField textName;

    @FXML
    private JFXTextField textEmail;

    @FXML
    private JFXTextField textPhoneNumber;

    @FXML
    private JFXTextField textAddress;

    @FXML
    private JFXComboBox<String> cbbProvince;

    @FXML
    private JFXComboBox<String> cbbDistrict;

    @FXML
    private JFXComboBox<String> cbbWard;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton okBtn;

    // ---- Variable global
    // variable for constructor
    BaseAccount newAccount = null;
    BaseAccount oldAccount = null;

    // variable check account is valid
    private boolean AccountIsValid;
    private String AccountErrorText;

    public BaseAccount getNewProfile() {
        return newAccount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initValidatorForInfoForm();
        setUpResizeForm();
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
    private void initValidatorForInfoForm() {
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
        textName.getValidators().add(validatorText);
        textEmail.getValidators().addAll(validatorText, validatorEmail);
        textPhoneNumber.getValidators().addAll(validatorText, validatorNumber);
        textAddress.getValidators().add(validatorText);
        cbbProvince.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbProvince);
        cbbDistrict.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbDistrict);
        cbbWard.getValidators().add(validatorText);
        setValidatorSelectedForCbb(cbbWard);

        // set event exit focus for Nodes
        addListenerNotFocus(textName);
        addListenerNotFocus(textEmail);
        addListenerNotFocus(textPhoneNumber);
        addListenerNotFocus(textAddress);
        addListenerNotFocus(cbbProvince);
        addListenerNotFocus(cbbDistrict);
        addListenerNotFocus(cbbWard);
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
    private void travelThoughNodesInForm() {
        // travel through the nodes of info form
        ObservableList<Node> childrenNodeOfInfoPane = infoPane.getChildren();
        for (Node node : childrenNodeOfInfoPane) {
            node.requestFocus();
        }
        editProfileForm.requestFocus();
    }

    // set for form enable auto size
    private void setUpResizeForm() {
        // set for baseInfoPane
        infoPane.setHgap(25);
        infoPane.setVgap(45);
        infoPane.setPadding(new Insets(30, 0, 0, 40));

        ColumnConstraints leftColBaseInfoPane = new ColumnConstraints();
        leftColBaseInfoPane.setHalignment(HPos.RIGHT);
        ColumnConstraints rightColBaseInfoPane = new ColumnConstraints();
        rightColBaseInfoPane.setHgrow(Priority.SOMETIMES);
        infoPane.getColumnConstraints().addAll(leftColBaseInfoPane, rightColBaseInfoPane);
    }

    // Edit a Account with handle pass from table view
    public void editAccount(BaseAccount account) {
        if (account.getTypeAccount() == null)
            return;

        oldAccount = account;
        // JoinTime
        textName.setText(oldAccount.getName());
        textEmail.setText(oldAccount.getEmail());
        textPhoneNumber.setText(oldAccount.getPhoneNumber());
        textAddress.setText(oldAccount.getAddress().getCompleteStreet());
        cbbWard.getSelectionModel().select((oldAccount.getAddress().getWard()));
        cbbDistrict.getSelectionModel().select((oldAccount.getAddress().getDistrict()));
        cbbProvince.getSelectionModel().select((oldAccount.getAddress().getProvince()));

    }

    // check new data account is valid
    private boolean checkForNewData() {
        AccountIsValid = true;
        AccountErrorText = new String("New profile of user not valid");
        travelThoughNodesInForm();

        if (!AccountIsValid) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText(AccountErrorText + "!");
            alert.showAndWait();
        }

        return AccountIsValid;
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
            newAccount = oldAccount;

            newAccount.setName(textName.getText());
            newAccount.setEmail(textEmail.getText());
            newAccount.setPhoneNumber(textPhoneNumber.getText());
            newAccount.setAddress(new Address(textAddress.getText(), cbbWard.getValue(), cbbDistrict.getValue(),
                    cbbProvince.getValue()));

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully edited profile of user");
            alert.showAndWait();

            // close window
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }

    }

}
