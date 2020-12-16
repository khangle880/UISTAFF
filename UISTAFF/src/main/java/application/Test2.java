package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Test2 extends Application {

    static List provinceList = new ArrayList();
    static final Map DistrictMap = new HashMap();
    static final Map WardMap = new HashMap();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("src/main/java/application/data.json")) {
            // Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject addrDict = (JSONObject) obj;

            initDataEnableBindingComboBox(addrDict);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ObservableList combox1 = FXCollections.observableList(provinceList);
        HBox box = new HBox(20);
        box.setPadding(new Insets(20, 20, 20, 20));
        ComboBox cb1 = new ComboBox();
        final ComboBox cb2 = new ComboBox();
        final ComboBox cb3 = new ComboBox();
        cb1.setItems(combox1);
        cb1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList combox2 = FXCollections.observableArrayList((List) DistrictMap.get(newValue));
            cb2.setItems(combox2);
        });

        cb2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                ObservableList combox3 = FXCollections.observableArrayList((List) WardMap.get(newValue));
                cb3.setItems(combox3);
            }
            
        });
        TextArea ta = new TextArea();
            ta.setText("1234567890");
            ta.positionCaret(1);
        box.getChildren().addAll( ta);
        Scene scene = new Scene(box, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private static void initDataEnableBindingComboBox(JSONObject addrDict) {

        // ---- convert json object to map object with only name string

        // get array province in Json file
        JSONArray provinceJsonList = (JSONArray) addrDict.get("Province/City");
        // get name province add into list
        for (Object province : provinceJsonList) {
            provinceList.add(((JSONObject) province).get("Name"));
        }

        for (int i = 0; i < provinceList.size(); i++) {
            List l = new ArrayList();
            // get array district per province in Json file
            JSONArray districtJsonList = (JSONArray) ((JSONObject) provinceJsonList.get(i)).get("District");
            // get name district add into list
            for (Object district : districtJsonList) {
                l.add(((JSONObject) district).get("Name"));
            }
            DistrictMap.put(provinceList.get(i), l);
        }

        for (int i = 0; i < provinceList.size(); i++) {
            Object districts = DistrictMap.get(provinceList.get(i));
            int numberDictrict = ((ArrayList) districts).size();
            // get array district per province in Json file
            JSONArray districtJsonList = (JSONArray) ((JSONObject) provinceJsonList.get(i)).get("District");
            for (int j = 0; j < numberDictrict; j++) {
                Object district = ((ArrayList) districts).get(j);
                List l = new ArrayList();
                // get array ward per district in Json file
                JSONArray wardJsonList = (JSONArray) ((JSONObject) districtJsonList.get(j)).get("Ward");
                // get name ward add into list
                for (Object ward : wardJsonList) {
                    l.add(((JSONObject) ward).get("Name"));
                }
                WardMap.put(district, l);
            }
        }

    }

}
