package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import classForDB.DataTest;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class NavBar implements Initializable {

    @FXML
    private Circle cir2;

    @FXML
    private Label userName;

    @FXML
    private JFXButton changeAvtBtn;

    @FXML
    private JFXButton changeInfoBtn;

    @FXML
    private JFXButton seeMoreBtn;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton settingBtn;

    @FXML
    private JFXButton signOutBtn;

    // AccessDB: get image
    private Image avt = DataTest.getAvt();

    private static BooleanProperty isAvtUpdated = new SimpleBooleanProperty();

    public static BooleanProperty isAvtUpdatedProperty() {
        return isAvtUpdated;
    }

    // made a list button public
    public ObservableList<JFXButton> fetchAllButtons() {
        ObservableList<JFXButton> allButtons = FXCollections.observableArrayList(changeAvtBtn, changeInfoBtn,
                seeMoreBtn, homeBtn, settingBtn, signOutBtn);
        return allButtons;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // avatar
        cir2.setStroke(Color.SEAGREEN);
        cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        UpdateAvtToScene();

        handleAvtUpdate();

    }

    // handle when avt in cropImage scene updated, refresh new avt on navbar
    public void handleAvtUpdate() {
        CropImage.avtImageProperty().addListener((observable, oldValue, newValue) -> {
            avt = newValue;
            UpdateAvtToScene();
            isAvtUpdated.set(true);
        });
    }

    public void UpdateAvtToScene() {
        cir2.setFill(new ImagePattern(avt));
    }

    public Image getAvatar() {
        return avt;
    }

    public void setAvatar(Image image) {
        avt = image;
        UpdateAvtToScene();
    }

}
