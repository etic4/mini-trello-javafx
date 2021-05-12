package view;

import mvvm.ColumnViewModel;
import view.common.BaseDeleteContextMenu;

public class ColumnDeleteContextMenu extends BaseDeleteContextMenu {
    private final ColumnViewModel columnViewModel;

    public ColumnDeleteContextMenu(ColumnViewModel columnViewModel, String title) {
        super(title);
        this.columnViewModel = columnViewModel;
    }

    @Override
    protected void executeDeleteCommand() {
        columnViewModel.deleteColumn();
    }
}
