package triatlon.controller;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class util {
    private static Logger logger = LogManager.getLogger(util.class.getName());

    public static void showWarning(String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MPP triatlon");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();

    }
}
