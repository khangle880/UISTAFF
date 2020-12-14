import controller.Home;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        loader.setController(new Home());
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
