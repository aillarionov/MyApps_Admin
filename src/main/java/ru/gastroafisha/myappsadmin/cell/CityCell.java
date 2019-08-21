package ru.gastroafisha.myappsadmin.cell;

import javafx.scene.control.ListCell;
import ru.gastroafisha.myappsadmin.model.CityProxy;

/**
 *
 * @author alex
 */
public class CityCell extends ListCell<CityProxy> {

  
    @Override
    protected void updateItem(CityProxy item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }

}
