package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test2 {

    public static void main(String[] args) {
        JSONParser jsonParser = new JSONParser();
        
        try
        {
            //Read JSON file
            JSONObject addrDict = (JSONObject) jsonParser.parse(new FileReader("src/main/java/application/data.json"));
            //Object obj = jsonParser.parse(reader);
 
            // JSONObject addrDict = (JSONObject) obj;
            System.out.println(addrDict);
             
            //initDataEnableBindingComboBox(addrDict);
            //System.out.println(addrDict.get("Province/City"));
            //System.out.println();
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
