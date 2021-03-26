package view;

import mvvm.ColumnViewModel;
import view.common.BaseContextMenu;

public class ColumnContextMenu extends BaseContextMenu {
    private final ColumnViewModel cardViewModel;

    public ColumnContextMenu(ColumnViewModel cardViewModel, String title) {
        super(title);
        this.cardViewModel = cardViewModel;
    }

    @Override
    protected void executeDeleteCommand() {
        cardViewModel.deleteColumn();
    }
}
