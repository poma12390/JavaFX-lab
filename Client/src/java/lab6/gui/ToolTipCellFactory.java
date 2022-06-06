package lab6.gui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;

public class ToolTipCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>(){
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                Tooltip tooltip = new Tooltip();
                tooltip.setText("help");
                setTooltip(tooltip);
                if (item==null){
                    setTooltip(null);
                    setText(null);
                }else {
                    setTooltip(new Tooltip(item.toString()));
                    setText(item.toString());
                }
            }
        };
    }
}