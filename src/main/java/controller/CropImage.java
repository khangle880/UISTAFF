
package controller;

import java.io.File;
import java.net.URL;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import classForDB.DataTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.image.WritableImage;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class CropImage implements Initializable {
    private static ObjectProperty<Image> avtImage = new SimpleObjectProperty<>();

    public static ObjectProperty<Image> avtImageProperty() {
        return avtImage;
    }

    @FXML
    private AnchorPane cropPane;

    @FXML
    private AnchorPane imagePane;

    @FXML
    private HBox group;

    @FXML
    private ImageView imageBackground;

    @FXML
    private JFXButton openButton;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton saveButton;

    Image currentAvt = DataTest.getAvt();

    public ObservableList<JFXButton> fetchAllButtons() {
        ObservableList<JFXButton> allButtons = FXCollections.observableArrayList(cancelBtn, saveButton);
        return allButtons;
    }

    //variable for cropPane
    private ScrollPane scrollPane = new ScrollPane();
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);
    private Rectangle cropFrame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageBackground.setImage(currentAvt);
        imagePane.getChildren().remove(group);

        //set up crop frame
        cropFrame = new Rectangle(320, 105, 400, 400);
        cropFrame.setStroke(Color.rgb(60, 203, 244));
        cropFrame.setStrokeWidth(3);
        cropFrame.setStrokeLineCap(StrokeLineCap.ROUND);
        cropFrame.setFill(Color.TRANSPARENT);

        // set up to zoom pane
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background: black; -fx-border-color: black;");
        AnchorPane.setTopAnchor(scrollPane, 10.0d);
        AnchorPane.setRightAnchor(scrollPane, 10.0d);
        AnchorPane.setBottomAnchor(scrollPane, 10.0d);
        AnchorPane.setLeftAnchor(scrollPane, 10.0d);

        // create canvas
        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(group);

        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        scrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();

        //add event to handle image
        scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        cropFrame.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        cropFrame.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        cropFrame.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        cropFrame.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        group.setPrefHeight(imagePane.getPrefHeight());
        group.setPrefWidth(imagePane.getPrefWidth());
        group.setLayoutX(0);
        group.setLayoutY(0);

        // add scroll pane into pane of image needed crop
        imagePane.getChildren().add(scrollPane);
        imagePane.getChildren().add(cropFrame);

        //set location for pane image
        AnchorPane.setTopAnchor(scrollPane, 0.0);
        AnchorPane.setLeftAnchor(scrollPane, 0.0);

    }
    

    //*event click open button
    @FXML
    private void handleOpenAction(ActionEvent actionEvent) {
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(imageFilter);

        File file = fc.showOpenDialog(cropPane.getScene().getWindow());
        if (file != null) { // only proceed, if file was chosen
            Image img = new Image(file.toURI().toString());
            imageBackground.setImage(img);
        }
    }


    //*event of save image
    @FXML
    void handleSaveImage(ActionEvent event) {
        WritableImage wi = crop();
        BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
        BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(), bufImageARGB.getHeight(),
                BufferedImage.OPAQUE);
        Graphics2D graphics = bufImageRGB.createGraphics();
        graphics.drawImage(bufImageARGB, 0, 0, null);
        graphics.dispose();
        Image image = SwingFXUtils.toFXImage(bufImageRGB, null);
        avtImage.set(image);
    }


    //* crop image from frame
    private WritableImage crop() {

        int width = 400;
        int height = 400;

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(320, 105.0, 400.0, 400.0));

        WritableImage wi = new WritableImage(width, height);

        Robot robot = new Robot();
        Bounds bounds = imagePane.localToScreen(imagePane.getBoundsInLocal());
        int x = (int) bounds.getMinX();
        int y = (int) bounds.getMinY();
        robot.getScreenCapture(wi, new Rectangle2D(x + 320, y + 105.0, 400.0, 400.0));

        return wi;
    }


    //* class of pane can zoom

    class PanAndZoomPane extends Pane {

        public static final double DEFAULT_DELTA = 1.3d;
        DoubleProperty myScale = new SimpleDoubleProperty(1.0);
        public DoubleProperty deltaY = new SimpleDoubleProperty(0.0);
        private Timeline timeline;

        public PanAndZoomPane() {

            this.timeline = new Timeline(60);

            // add scale transform
            scaleXProperty().bind(myScale);
            scaleYProperty().bind(myScale);
        }

        public double getScale() {
            return myScale.get();
        }

        public void setScale(double scale) {
            myScale.set(scale);
        }

        public void setPivot(double x, double y, double scale) {
            // note: pivot value must be untransformed, i. e. without scaling
            // timeline that scales and moves the node
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(200), new KeyValue(translateXProperty(), getTranslateX() - x)),
                    new KeyFrame(Duration.millis(200), new KeyValue(translateYProperty(), getTranslateY() - y)),
                    new KeyFrame(Duration.millis(200), new KeyValue(myScale, scale)));
            timeline.play();

        }

        public void fitWidth() {
            double scale = getParent().getLayoutBounds().getMaxX() / getLayoutBounds().getMaxX();
            double oldScale = getScale();

            double f = scale - oldScale;

            double dx = getTranslateX() - getBoundsInParent().getMinX() - getBoundsInParent().getWidth() / 2;
            double dy = getTranslateY() - getBoundsInParent().getMinY() - getBoundsInParent().getHeight() / 2;

            double newX = f * dx + getBoundsInParent().getMinX();
            double newY = f * dy + getBoundsInParent().getMinY();

            setPivot(newX, newY, scale);

        }

        public void resetZoom() {
            double scale = 1.0d;

            double x = getTranslateX();
            double y = getTranslateY();

            setPivot(x, y, scale);
        }

        public double getDeltaY() {
            return deltaY.get();
        }

        public void setDeltaY(double dY) {
            deltaY.set(dY);
        }
    }

    /**
     * Mouse drag context used for scene and nodes.
     */
    class DragContext {

        double mouseAnchorX;
        double mouseAnchorY;

        double translateAnchorX;
        double translateAnchorY;

    }

    /**
     * Listeners for making the scene's canvas draggable and zoomable
     */
    public class SceneGestures {

        private DragContext sceneDragContext = new DragContext();

        PanAndZoomPane panAndZoomPane;

        public SceneGestures(PanAndZoomPane canvas) {
            this.panAndZoomPane = canvas;
        }

        public EventHandler<MouseEvent> getOnMouseClickedEventHandler() {
            return onMouseClickedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
            return onMousePressedEventHandler;
        }

        public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
            return onMouseDraggedEventHandler;
        }

        public EventHandler<ScrollEvent> getOnScrollEventHandler() {
            return onScrollEventHandler;
        }

        private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

            public void handle(MouseEvent event) {

                sceneDragContext.mouseAnchorX = event.getX();
                sceneDragContext.mouseAnchorY = event.getY();

                sceneDragContext.translateAnchorX = panAndZoomPane.getTranslateX();
                sceneDragContext.translateAnchorY = panAndZoomPane.getTranslateY();

            }

        };

        private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {

                panAndZoomPane.setTranslateX(
                        sceneDragContext.translateAnchorX + event.getX() - sceneDragContext.mouseAnchorX);
                panAndZoomPane.setTranslateY(
                        sceneDragContext.translateAnchorY + event.getY() - sceneDragContext.mouseAnchorY);

                event.consume();
            }
        };

        /**
         * Mouse wheel handler: zoom to pivot point
         */
        private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

            @Override
            public void handle(ScrollEvent event) {

                double delta = PanAndZoomPane.DEFAULT_DELTA;

                double scale = panAndZoomPane.getScale(); // currently we only use Y, same value is used for X
                double oldScale = scale;

                panAndZoomPane.setDeltaY(event.getDeltaY());
                if (panAndZoomPane.deltaY.get() < 0) {
                    scale /= delta;
                } else {
                    scale *= delta;
                }

                double f = (scale / oldScale) - 1;
                double dx = (event.getX() - (panAndZoomPane.getBoundsInParent().getWidth() / 2
                        + panAndZoomPane.getBoundsInParent().getMinX()));
                double dy = (event.getY() - (panAndZoomPane.getBoundsInParent().getHeight() / 2
                        + panAndZoomPane.getBoundsInParent().getMinY()));

                panAndZoomPane.setPivot(f * dx, f * dy, scale);

                event.consume();

            }
        };

        /**
         * Mouse click handler
         */
        private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        panAndZoomPane.resetZoom();
                    }
                }
                if (event.getButton().equals(MouseButton.SECONDARY)) {
                    if (event.getClickCount() == 2) {
                        panAndZoomPane.fitWidth();
                    }
                }
            }
        };
    }

}
