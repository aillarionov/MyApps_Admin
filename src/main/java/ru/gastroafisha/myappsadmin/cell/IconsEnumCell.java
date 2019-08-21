package ru.gastroafisha.myappsadmin.cell;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.gastroafisha.myappsadmin.model.IconsEnum;

/**
 *
 * @author alex
 */
public class IconsEnumCell extends ListCell<IconsEnum> {

    private final ImageView image = new ImageView();
    
    @Override
    protected void updateItem(IconsEnum item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            image.setImage(null);
        } else {
            String iconPath = "images/" + item.getCode() + ".png";
            image.setImage(new Image(getClass().getClassLoader().getResourceAsStream(iconPath)));
        }

        setGraphic(image);
    }
}
