package view;

import mvvm.CardViewModel;
import view.common.BaseDeleteContextMenu;


public class CardDeleteContextMenu extends BaseDeleteContextMenu {
    private final CardViewModel cardViewModel;

    public CardDeleteContextMenu(CardViewModel cardViewModel, String title) {
        super(title);
        this.cardViewModel = cardViewModel;
    }

    @Override
    protected void executeDeleteCommand() {
        cardViewModel.deleteCard();
    }
}
