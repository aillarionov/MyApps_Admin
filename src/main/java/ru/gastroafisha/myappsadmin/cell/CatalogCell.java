package ru.gastroafisha.myappsadmin.cell;

import javafx.scene.control.ListCell;
import ru.gastroafisha.myappsadmin.model.CatalogProxy;

/**
 *
 * @author alex
 */
public class CatalogCell extends ListCell<CatalogProxy> {

  
    @Override
    protected void updateItem(CatalogProxy item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getType().name() + " " + item.getSource().name() + " " + item.getSourceOwner() + " " + item.getSourceAlbum());
        }
    }

}
