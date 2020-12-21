package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

public class seeMoreInfoAccount implements Initializable {

    @FXML
    private VBox editForm;

    @FXML
    private GridPane baseInfoPane;

    @FXML
    private GridPane addInfoPane;

    @FXML
    private JFXButton okBtn;

    @FXML
    void handleOkEditForm(ActionEvent event) {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    // variable for constructor
    BaseAccount account = new BaseAccount();

    // set formatter for datetime fields in table
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpFormAutoSize();
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
    public void showAccount(BaseAccount account) {
        if (account.getTypeAccount() == null) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("This Account is empty!");
            alert.showAndWait();
            return;
        }

        // bind data to base info pane
        ObservableList<Node> childrenNodeOfBasePane = baseInfoPane.getChildren();
        ObservableList<Node> nodesShowDataOfBasePane = FXCollections.observableArrayList();
        for (Node node : childrenNodeOfBasePane) {
            if (GridPane.getColumnIndex(node) != null) {
                nodesShowDataOfBasePane.add(node);
            }
        }

        ((Label) nodesShowDataOfBasePane.get(0)).setText(account.getID());
        ((Label) nodesShowDataOfBasePane.get(1)).setText(account.getName());
        ((Label) nodesShowDataOfBasePane.get(2)).setText(account.getEmail());
        ((Label) nodesShowDataOfBasePane.get(3)).setText(account.getPhoneNumber());
        ((Label) nodesShowDataOfBasePane.get(4)).setText(account.getMoneyBalance().toString());
        ((Label) nodesShowDataOfBasePane.get(5)).setText(account.getAddress().getCompleteStreet());
        ((Label) nodesShowDataOfBasePane.get(6)).setText(account.getAddress().getWard());
        ((Label) nodesShowDataOfBasePane.get(7)).setText(account.getAddress().getDistrict());
        ((Label) nodesShowDataOfBasePane.get(8)).setText(account.getAddress().getProvince());
        ((Label) nodesShowDataOfBasePane.get(9)).setText(account.getTypeAccount());
        ((Label) nodesShowDataOfBasePane.get(10)).setText(String.format(account.getJoinTime().format(formatter)));
        ((Label) nodesShowDataOfBasePane.get(11)).setText(account.getStatus() == true ? "UnLock" : "Lock");

        // bind data to additional info pane
        switch (account.getTypeAccount()) {
            case "Customer": {
                // init nodes
                Label titleLabel = new Label("Additional Info");
                Label identifyIDLabel = new Label("Identify ID :");
                Label mobilePhoneLabel = new Label("Mobile Phone :");
                Label cardIDLabel = new Label("Card ID :");
                Label pinNumberLabel = new Label("PIN Number :");
                Label typeCardLabel = new Label("Type Card :");
                Label creationLabel = new Label("Creation Date :");
                Label expiryLabel = new Label("Expiry Date :");

                Label textIdentifyID = new Label();
                Label textMobilePhone = new Label();
                Label textCardID = new Label();
                Label textPinNumber = new Label();
                Label cbbTypeCard = new Label();
                Label dateTimeCreation = new Label();
                Label dateTimeExpiry = new Label();

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

                // bind data
                textIdentifyID.setText(((CustomerAccount) account).getIdentifyID());
                textMobilePhone.setText(((CustomerAccount) account).getMobilePhoneNumber());
                textCardID.setText(((CustomerAccount) account).getCardID());
                textPinNumber.setText(((CustomerAccount) account).getPINNumber());
                cbbTypeCard.setText(((CustomerAccount) account).getTypeCard());
                dateTimeCreation
                        .setText(String.format(((CustomerAccount) account).getCreationDate().format(formatter)));
                dateTimeExpiry.setText(String.format(((CustomerAccount) account).getExpiryDate().format(formatter)));
            }
                break;
            case "Employee": {
                Label titleLabel = new Label("Additional Info");
                Label identifyIDLabel = new Label("Identify ID :");
                Label mobilePhoneLabel = new Label("Mobile Phone :");
                Label passwordLabel = new Label("Password :");
                Label positionLabel = new Label("Position :");

                Label textIdentifyID = new Label();
                Label textMobilePhone = new Label();
                Label textPassword = new Label();
                Label textPosition = new Label();

                // Add new nodes
                int i = 0;
                addInfoPane.addRow(i, titleLabel);
                addInfoPane.addRow(i + 1, identifyIDLabel, textIdentifyID);
                addInfoPane.addRow(i + 2, mobilePhoneLabel, textMobilePhone);
                addInfoPane.addRow(i + 3, passwordLabel, textPassword);
                addInfoPane.addRow(i + 4, positionLabel, textPosition);

                // bind data
                textIdentifyID.setText(((EmployeeAccount) account).getIdentifyID());
                textMobilePhone.setText(((EmployeeAccount) account).getMobilePhoneNumber());
                textPassword.setText(((EmployeeAccount) account).getPassword());
                textPosition.setText(((EmployeeAccount) account).getPosition());
            }
                break;
            case "Organization": {
                Label titleLabel = new Label("Additional Info");
                Label passwordLabel = new Label("Password :");
                Label descriptionLabel = new Label("Description :");
                Label ratingLabel = new Label("Rating :");

                Label textPassword = new Label();
                Label textDescription = new Label();
                Label textRating = new Label();

                // Add new nodes
                int i = 0;
                addInfoPane.addRow(i, titleLabel);
                addInfoPane.addRow(i + 1, passwordLabel, textPassword);
                addInfoPane.addRow(i + 2, descriptionLabel, textDescription);
                addInfoPane.addRow(i + 3, ratingLabel, textRating);

                // bind data
                textPassword.setText(((OrganizationAccount) account).getPassword());
                textDescription.setText(((OrganizationAccount) account).getDescription());
                textRating.setText(((OrganizationAccount) account).getRating());
            }
                break;
            default:
                break;
        }

    }

}
