package view;

import mvvm.ColumnViewModel;
import view.common.BaseDeleteContextMenu;

public class ColumnDeleteContextMenu extends BaseDeleteContextMenu {
    private final ColumnViewModel cardViewModel;


    public ColumnDeleteContextMenu(ColumnViewModel cardViewModel, String title) {
        super(title);
        this.cardViewModel = cardViewModel;
    }

    @Override
    protected void executeDeleteCommand() {
        cardViewModel.deleteColumn();
    }
}
