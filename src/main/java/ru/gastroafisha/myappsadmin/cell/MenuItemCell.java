package ru.gastroafisha.myappsadmin.cell;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import ru.gastroafisha.myappsadmin.Utils;
import ru.gastroafisha.myappsadmin.model.MenuItemProxy;

/**
 *
 * @author alex
 */
public class MenuItemCell extends ListCell<MenuItemProxy> {

    public MenuItemCell() {
        ListCell<MenuItemProxy> thisCell = this;

        // Drag/drop feature
        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            ObservableList<MenuItemProxy> items = getListView().getItems();
            Integer thisIdx = items.indexOf(getItem());

            content.putString(thisIdx.toString());
            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() != thisCell /* && event.getDragboard().hasString() */) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != thisCell
                    && event.getDragboard().hasString()) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != thisCell
                    && event.getDragboard().hasString()) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                ObservableList<MenuItemProxy> items = getListView().getItems();

                int sourceIdx = Integer.parseInt(db.getString());
                int targetIdx = getIndex();

                Utils.moveItem(sourceIdx, targetIdx, items);

                for (int i = 0; i < items.size(); i++) {
                    items.get(i).setOrder(i + 1);
                }

                getListView().refresh();
                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        setOnDragDone(DragEvent::consume);
    }

    @Override
    protected void updateItem(MenuItemProxy item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getName());
        }
    }

}
