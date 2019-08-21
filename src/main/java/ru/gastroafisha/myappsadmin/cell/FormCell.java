package ru.gastroafisha.myappsadmin.cell;

import javafx.scene.control.ListCell;
import ru.gastroafisha.myappsadmin.model.FormProxy;

/**
 *
 * @author alex
 */
public class FormCell extends ListCell<FormProxy> {

  
    @Override
    protected void updateItem(FormProxy item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }

}
