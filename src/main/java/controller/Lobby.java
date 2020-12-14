package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class Lobby implements Initializable {
    @FXML
    private JFXButton navAtmBtn;

    @FXML
    private JFXButton navDataBaseBtn;

    @FXML
    private JFXButton navReportProblemBtn;

    @FXML
    private JFXButton navSupplierBtn;

    @FXML
    private JFXButton navAccountBtn;

    @FXML
    private JFXButton navMessageBtn;

    @FXML
    private JFXButton navPeriodicReportBtn;

    @FXML
    private JFXButton navUnusualReportBtn;

    public ObservableList<JFXButton> fetchAllButtons() {
        ObservableList<JFXButton> allButtons = FXCollections.observableArrayList(navAtmBtn, navDataBaseBtn,
                navReportProblemBtn, navSupplierBtn, navAccountBtn, navMessageBtn, navPeriodicReportBtn,
                navUnusualReportBtn);
        return allButtons;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
