package classComponent;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextFormatter;

public class Util {

    // This will filter the changes
    public static UnaryOperator<TextFormatter.Change> createFilter() {
        //this is a simple Regex to define the acceptable Chars
        String validEditingStateRegex = "[0123456789,.-]*";
        return change -> {
            String text = change.getText();
            //Check if something changed and just return if not
            if (!change.isContentChange()) {
                return change;
            }
            //check if the changed text validates against the regex
            if (text.matches(validEditingStateRegex) || text.isEmpty()) {
                //if valid return the change
                return change;
            }
            //otherwise return null
            return null;
        };
    }
}