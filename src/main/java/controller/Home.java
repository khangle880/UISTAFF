package controller;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;

public class Home implements Initializable {

    @FXML
    private BorderPane root;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private BorderPane currentPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer navBarPane;

    // Declaring ctrlNavBar globally
    NavBar insNavBar = new NavBar();

    // Get all buttons from ctrlNavBar
    ObservableList<JFXButton> navBarButtons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDrawer();
        NavBar.isAvtUpdatedProperty().addListener((observable, oldValue, newValue) -> {
            Lobby insLobby = new Lobby();
            loadFXML(getClass().getResource("/scene/lobby.fxml"), insLobby);
            ObservableList<JFXButton> lobbyButtons = insLobby.fetchAllButtons();
            sideSceneFromLobby(lobbyButtons);
            NavBar.isAvtUpdatedProperty().set(false);
        });
        sideSceneFromNavBar();

        // Init pane first
        Lobby insLobby = new Lobby();
        loadFXML(getClass().getResource("/scene/lobby.fxml"), insLobby);
        ObservableList<JFXButton> lobbyButtons = insLobby.fetchAllButtons();
        sideSceneFromLobby(lobbyButtons);

    }

    private void sideSceneFromNavBar() {
        for (JFXButton button : navBarButtons) {
            switch (button.getId()) {
                case "changeAvtBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("/scene/cropImage.fxml"), new CropImage());
                    });
                    break;
                case "changeInfoBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for change info
                    });
                    break;
                case "seeMoreBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for see more
                    });
                    break;
                case "homeBtn":
                    button.setOnAction(actionEvent -> {
                        Lobby insLobby = new Lobby();
                        loadFXML(getClass().getResource("/scene/lobby.fxml"), insLobby);
                        ObservableList<JFXButton> lobbyButtons = insLobby.fetchAllButtons();
                        sideSceneFromLobby(lobbyButtons);
                    });
                    break;
                case "settingBtn":
                    button.setOnAction(actionEvent -> {
                        // load scene for setting
                    });
                    break;
                case "signOutBtn":
                    button.setOnAction(actionEvent -> {
                        // action for sign out
                    });
                    break;
            }
        }
    }

    private void sideSceneFromLobby(ObservableList<JFXButton> lobbyButtons) {
        for (JFXButton button : lobbyButtons) {
            switch (button.getId()) {
                case "navAtmBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("/scene/os.fxml"), new OS());
                    });
                    break;
                case "navDataBaseBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navSupplierBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navReportProblemBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                case "navAccountBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("/scene/accountManagement.fxml"), new AccountManagement());
                    });
                    break;
                case "navMessageBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;s
                    });
                    break;
                case "navReportBtn":
                    button.setOnAction(actionEvent -> {
                        // loadFXML(getClass().getResource(""), new ;
                    });
                    break;
                default:
                    break;
            }
        }
    }

    private void loadFXML(URL url, Object ctrl) {
        try {
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(ctrl);
            currentPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDrawer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/navBar.fxml"));
            loader.setController(insNavBar);
            VBox navBarBox = loader.load();
            navBarPane.setSidePane(navBarBox);
            navBarButtons = insNavBar.fetchAllButtons();
            navBarPane.setMinWidth(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
            if (navBarPane.isClosed())
                navBarPane.open();
        });
    }

    @FXML
    void handleExitDrawer(MouseEvent event) {
        if (navBarPane.isOpened()) {
            navBarPane.close();
        }
    }

}
