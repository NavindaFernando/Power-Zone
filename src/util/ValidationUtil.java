package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: white");
                    ((Pane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: white");
                }
                return textFieldKey;
            }
            textFieldKey.getParent().setStyle("-fx-border-color: #26ff00");
            ((Pane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: #26ff00");
        }
        btn.setDisable(false);
        return true;

    }

    public static Object validate2(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-jfx-focus-color: white");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-prompt-text-fill: #26ff00");
        }
        btn.setDisable(false);
        return true;
    }

    public static Object validate3(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.setStyle("-fx-border-color:  white");
                }
                return textFieldKey;
            }
            textFieldKey.setStyle("-fx-border-color: #26ff00");
        }
        btn.setDisable(false);
        return true;
    }
}
