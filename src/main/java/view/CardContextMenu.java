package view;

import mvvm.CardViewModel;
import view.common.BaseContextMenu;


public class CardContextMenu extends BaseContextMenu {
    private final CardViewModel cardViewModel;

    public CardContextMenu(CardViewModel cardViewModel, String title) {
        super(title);
        this.cardViewModel = cardViewModel;
    }

    @Override
    protected void executeDeleteCommand() {
        cardViewModel.deleteCard();
    }
}
