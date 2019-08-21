package ru.gastroafisha.myappsadmin.cell;

import javafx.scene.control.ListCell;
import ru.gastroafisha.myappsadmin.model.SimpleOrgProxy;

/**
 *
 * @author alex
 */
public class SimpleOrgCell extends ListCell<SimpleOrgProxy> {

  
    @Override
    protected void updateItem(SimpleOrgProxy item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }

}
