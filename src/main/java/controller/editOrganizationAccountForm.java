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
import java.util.concurrent.Callable;

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
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import classForDB.Address;
import classForDB.OrganizationAccount;

public class editOrganizationAccountForm implements Initializable {

    @FXML
    private VBox editForm;

    @FXML
    private GridPane InfoPane;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton okBtn;

    // text formatter for currency
    TextFormatter<Number> textFormatterCurrency;

    // ---- Variable global
    // variable for constructor
    OrganizationAccount newAccount = null;
    OrganizationAccount oldAccount = null;

    // variable check account is valid
    private boolean AccountIsValid;
    private String AccountErrorText;

    public OrganizationAccount getNewAccount() {
        return newAccount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpFormAutoSize();

        // create nodes for infoPane
        initNodesForInfoPanel();

    }

    private void initNodesForInfoPanel() {
        Label descriptionLabel = new Label("Description :");
        Label ratingLabel = new Label("Rating :");
        descriptionLabel.setPadding(new Insets(6, 0, 0, 0));
        ratingLabel.setPadding(new Insets(6, 0, 0, 0));

        ExpandableTextArea textDescription = new ExpandableTextArea("");
        ExpandableTextArea textRating = new ExpandableTextArea("");

        textDescription.setPromptText("Description");
        textRating.setPromptText("Rating");

        // create validators
        RequiredFieldValidator validatorText = new RequiredFieldValidator();
        NumberValidator validatorNumber = new NumberValidator();

        // set text for validators
        validatorText.setMessage("No Input Given!");
        validatorNumber.setMessage("Only Numbers are supported!");

        // set validator for Nodes
        textDescription.getValidators().add(validatorText);
        textRating.getValidators().add(validatorText);

        // set event exit focus for Nodes
        addListenerNotFocus(textDescription);
        addListenerNotFocus(textRating);

        // Add new nodes
        InfoPane.addRow(0, descriptionLabel, textDescription);
        InfoPane.addRow(1, ratingLabel, textRating);

        RowConstraints rc = new RowConstraints();
        rc.setValignment(VPos.CENTER);
        InfoPane.getRowConstraints().addAll(rc, rc);

        // set auto resizing for ExpandableTextArea
        RowConstraints rowDescription = new RowConstraints();
        rowDescription.setValignment(VPos.TOP);
        setAutoResizeTextAreaInGridPane(textDescription, rowDescription);
        InfoPane.getRowConstraints().set(GridPane.getRowIndex(textDescription), rowDescription);

        RowConstraints rowRating = new RowConstraints();
        rowRating.setValignment(VPos.TOP);
        setAutoResizeTextAreaInGridPane(textRating, rowRating);
        InfoPane.getRowConstraints().set(GridPane.getRowIndex(textRating), rowRating);
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
        ObservableList<Node> childrenNodeOfPane = InfoPane.getChildren();
        for (Node node : childrenNodeOfPane) {
            node.requestFocus();
        }
    }

    // set for form enable auto size
    private void setUpFormAutoSize() {
        // set for InfoPane
        InfoPane.setHgap(25);
        InfoPane.setVgap(45);
        InfoPane.setPadding(new Insets(30, 0, 0, 40));

        ColumnConstraints leftColInfoPane = new ColumnConstraints();
        leftColInfoPane.setHalignment(HPos.RIGHT);
        ColumnConstraints rightColInfoPane = new ColumnConstraints();
        rightColInfoPane.setHgrow(Priority.SOMETIMES);
        InfoPane.getColumnConstraints().addAll(leftColInfoPane, rightColInfoPane);

    }

    // Edit a Account with handle pass from table view
    public void editAccount(OrganizationAccount account) {
        if (account == new OrganizationAccount())
            return;

        ObservableList<Node> childrenNodeOfPane = InfoPane.getChildren();
        ObservableList<Node> nodesEditableOfPane = FXCollections.observableArrayList();
        for (Node node : childrenNodeOfPane) {
            if (!(node instanceof Label))
                nodesEditableOfPane.add(node);
        }

        ((ExpandableTextArea) nodesEditableOfPane.get(0)).setText(((OrganizationAccount) account).getDescription());
        ((ExpandableTextArea) nodesEditableOfPane.get(1)).setText(((OrganizationAccount) account).getRating());

        oldAccount = account;
        newAccount = null;

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

    private void setAutoResizeTextAreaInGridPane(ExpandableTextArea textArea, RowConstraints rc) {
        textArea.prefHeightProperty().addListener((observable, oldValue, newValue) -> {
            rc.setMinHeight((double) newValue);
        });
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

            ObservableList<Node> childrenNodeOfPane = InfoPane.getChildren();
            ObservableList<Node> nodesEditableOfPane = FXCollections.observableArrayList();
            for (Node node : childrenNodeOfPane) {
                if (!(node instanceof Label))
                    nodesEditableOfPane.add(node);
            }

            ((OrganizationAccount) newAccount)
                    .setDescription(((ExpandableTextArea) nodesEditableOfPane.get(0)).getText());
            ((OrganizationAccount) newAccount).setRating(((ExpandableTextArea) nodesEditableOfPane.get(1)).getText());

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Successfully edited an Account");
            alert.showAndWait();

            // close window
            Stage stage = (Stage) okBtn.getScene().getWindow();
            stage.close();
        }

    }
}
