package ru.gastroafisha.myappsadmin;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author alex
 */
public class MessageDialog {

    public static void show(Alert.AlertType type, String title, String header, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();

    }

    public static boolean ask(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        Optional<ButtonType> option = alert.showAndWait();

        //ButtonType show = new ButtonType("Show in Explorer", ButtonBar.ButtonData.LEFT);
        //alert.getButtonTypes().add(show);
        //ButtonBar.setButtonUniformSize(alert.getDialogPane().lookupButton(show), false);
//        alert.getDialogPane().lookupButton(show).addEventFilter(ActionEvent.ACTION, event -> {
//            try {
//                Desktop.getDesktop().open(new File(new File(file.getPath())));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            event.consume();
//        });
        return ButtonType.OK.equals(option.get());

    }

}
