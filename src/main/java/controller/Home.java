package controller;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.HostServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXScrollPane;

import classForDB.BaseAccount;
import classForDB.DataTest;

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

    // host Services getter setter
    private HostServices hostServices;

    public HostServices getHostServices() {
        return hostServices;
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

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
                        editUserProfile();
                    });
                    break;
                case "seeMoreBtn":
                    button.setOnAction(actionEvent -> {
                        // AccessDB: get account of user
                        viewInfoAccount(DataTest.getAccountList().get(3));
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
                        // todo: setting handle
                        // load scene for setting
                    });
                    break;
                case "signOutBtn":
                    button.setOnAction(actionEvent -> {
                        // todo: sign out handle
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
                        Hyperlink link = DataTest.getLinkDataBase();
                        getHostServices().showDocument(link.getText());
                    });
                    break;
                case "navSupplierBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("/scene/supplierManagement.fxml"), new SupplierManagement());
                    });
                    break;
                case "navReportProblemBtn":
                    button.setOnAction(actionEvent -> {
                        notifyFuncNotAvailable();
                    });
                    break;
                case "navAccountBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("/scene/accountManagement.fxml"), new AccountManagement());
                    });
                    break;
                case "navMessageBtn":
                    button.setOnAction(actionEvent -> {
                        notifyFuncNotAvailable();
                    });
                    break;
                case "navReportBtn":
                    button.setOnAction(actionEvent -> {
                        loadFXML(getClass().getResource("/scene/report.fxml"), new report());
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

    private void notifyFuncNotAvailable() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Notify");
        alert.setHeaderText(null);
        alert.setContentText("Function Is Not Supported Yet !");
        alert.showAndWait();
    }

    private void viewInfoAccount(BaseAccount account) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/viewInfoAccount.fxml"));
        viewInfoAccount viewInfoAccountCtrl = new viewInfoAccount();
        loader.setController(viewInfoAccountCtrl);

        JFXScrollPane viewInfoPane = new JFXScrollPane();
        try {
            viewInfoPane.setContent(loader.load());

            Label title = new Label("Profile");
            viewInfoPane.getBottomBar().getChildren().add(title);
            title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
            JFXScrollPane.smoothScrolling((ScrollPane) viewInfoPane.getChildren().get(0));

            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(viewInfoPane, 550, 700));
            viewInfoAccountCtrl.showAccount(account);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void editUserProfile() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/editUserProfile.fxml"));
        editUserProfile editUserProfileCtrl = new editUserProfile();
        loader.setController(editUserProfileCtrl);

        JFXScrollPane editUserProfilePane = new JFXScrollPane();
        try {
            editUserProfilePane.setContent(loader.load());

            Label title = new Label("Account Info");
            editUserProfilePane.getBottomBar().getChildren().add(title);
            title.setStyle("-fx-text-fill:WHITE; -fx-font-size: 40;");
            JFXScrollPane.smoothScrolling((ScrollPane) editUserProfilePane.getChildren().get(0));

            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(new Scene(editUserProfilePane, 500, 670));
            editUserProfileCtrl.editAccount(DataTest.getAccountList().get(3));
            newStage.showAndWait();

            DataTest.getAccountList().set(3, editUserProfileCtrl.getNewProfile());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
