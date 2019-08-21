package ru.gastroafisha.myappsadmin.cell;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import ru.gastroafisha.myappsadmin.Tuple;
import ru.gastroafisha.myappsadmin.Utils;

/**
 *
 * @author alex
 */
public class TupleCell extends ListCell<Tuple<String, String>> {

    public TupleCell() {
        ListCell<Tuple<String, String>> thisCell = this;

        // Drag/drop feature
        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            ObservableList<Tuple<String, String>> items = getListView().getItems();
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
                ObservableList<Tuple<String, String>> items = getListView().getItems();

                int sourceIdx = Integer.parseInt(db.getString());
                int targetIdx = getIndex();

                Utils.moveItem(sourceIdx, targetIdx, items);
                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        setOnDragDone(DragEvent::consume);
    }
    
    @Override
    protected void updateItem(Tuple<String, String> item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getX() + " -> " + item.getY());
        }
    }

}
