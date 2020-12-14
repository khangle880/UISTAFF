package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class BottomBar implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private Label sceneTitle;

    // made a list button public
    public ObservableList<JFXButton> fetchAllButtons() {
        ObservableList<JFXButton> allButtons = FXCollections.observableArrayList(addBtn, editBtn, deleteBtn);
        return allButtons;
    }

    public ObservableList<Label> fetchLabelTitle() {
        ObservableList<Label> labelTitle = FXCollections.observableArrayList(sceneTitle);
        return labelTitle;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}