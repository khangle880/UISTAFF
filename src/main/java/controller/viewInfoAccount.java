package controller;

import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import classForDB.BaseAccount;
import classForDB.CustomerAccount;
import classForDB.EmployeeAccount;
import classForDB.OrganizationAccount;

public class viewInfoAccount implements Initializable {

    @FXML
    private VBox editForm;

    @FXML
    private GridPane baseInfoPane;

    @FXML
    private GridPane addInfoPane;

    @FXML
    private JFXButton okBtn;

    // text formatter for currency
    TextFormatter<Number> textFormatterCurrency;

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

        // ---- setting the amount storage column
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        ((Label) nodesShowDataOfBasePane.get(0)).setText(account.getID());
        ((Label) nodesShowDataOfBasePane.get(1)).setText(account.getName());
        ((Label) nodesShowDataOfBasePane.get(2)).setText(account.getEmail());
        ((Label) nodesShowDataOfBasePane.get(3)).setText(account.getPhoneNumber());
        ((Label) nodesShowDataOfBasePane.get(4)).setText(currencyFormat.format(account.getAmountBalance()));
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
                Label positionLabel = new Label("Position :");

                Label textIdentifyID = new Label();
                Label textMobilePhone = new Label();
                Label textPosition = new Label();

                // Add new nodes
                int i = 0;
                addInfoPane.addRow(i, titleLabel);
                addInfoPane.addRow(i + 1, identifyIDLabel, textIdentifyID);
                addInfoPane.addRow(i + 2, mobilePhoneLabel, textMobilePhone);
                addInfoPane.addRow(i + 4, positionLabel, textPosition);

                // bind data
                textIdentifyID.setText(((EmployeeAccount) account).getIdentifyID());
                textMobilePhone.setText(((EmployeeAccount) account).getMobilePhoneNumber());
                textPosition.setText(((EmployeeAccount) account).getPosition());
            }
                break;
            case "Organization": {
                Label titleLabel = new Label("Additional Info");
                Label descriptionLabel = new Label("Description :");
                Label ratingLabel = new Label("Rating :");

                Label textDescription = new Label();
                Label textRating = new Label();

                textDescription.setText(((OrganizationAccount) account).getDescription());
                textRating.setText(((OrganizationAccount) account).getRating());

                textDescription.setMaxHeight(Double.MAX_VALUE);
                textRating.setMaxHeight(Double.MAX_VALUE);

                descriptionLabel.setPadding(new Insets(6, 0, 0, 0));
                ratingLabel.setPadding(new Insets(6, 0, 0, 0));

                // Add new nodes
                int i = 0;
                addInfoPane.addRow(i, titleLabel);
                addInfoPane.addRow(i + 2, descriptionLabel, textDescription);
                addInfoPane.addRow(i + 3, ratingLabel, textRating);

                RowConstraints rc = new RowConstraints();
                rc.setValignment(VPos.CENTER);
                addInfoPane.getRowConstraints().addAll(rc, rc, rc, rc);

                // set auto resizing for ExpandableTextArea
                RowConstraints rowDescription = new RowConstraints();
                rowDescription.setValignment(VPos.TOP);
                textDescription.heightProperty().addListener((observable, oldValue, newValue) -> {
                    Text text = new Text(textDescription.getText());
                    text.setFont(textDescription.getFont());
                    double width = text.getBoundsInLocal().getHeight();
                    textDescription.setMinHeight(width);
                });
                addInfoPane.getRowConstraints().set(GridPane.getRowIndex(textDescription), rowDescription);

                RowConstraints rowRating = new RowConstraints();
                rowRating.setValignment(VPos.TOP);
                textRating.heightProperty().addListener((observable, oldValue, newValue) -> {
                    Text text = new Text(textRating.getText());
                    text.setFont(textRating.getFont());
                    double width = text.getBoundsInLocal().getHeight();
                    textRating.setMinHeight(width);
                });
                addInfoPane.getRowConstraints().set(GridPane.getRowIndex(textRating), rowRating);

            }
                break;
            default:
                break;
        }

    }

}
