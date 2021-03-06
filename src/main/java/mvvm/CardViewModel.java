package mvvm;

import mvvm.command.*;
import direction.Direction;
import model.*;
import javafx.beans.property.*;


public class CardViewModel {

    private final StringProperty cardTitleView = new SimpleStringProperty();

    private final Card card;
    private final BoardFacade boardFacade;


    public CardViewModel(Card card) {
        this.card = card;
        boardFacade = new BoardFacade(card.getBoard());

        // bind title view property to card title
        cardTitleView.set(card.getTitle());

        // set card's title property on model value if changed
        card.titleProperty().addListener((o, oldVal, newVal) -> cardTitleView.set(card.getTitle()));
    }


    // --- properties ---

    public StringProperty cardTitleViewProperty() {
        return cardTitleView;
    }

    public ReadOnlyBooleanProperty btRightDisabledProperty() {
        return card.isColumnLastProperty();
    }

    public ReadOnlyBooleanProperty btLeftDisabledProperty() {
        return card.isColumnFirstProperty();
    }

    public ReadOnlyBooleanProperty btUpDisabledProperty() {
        return card.isFirstProperty();
    }

    public ReadOnlyBooleanProperty btDownDisabledProperty() {
        return card.isLastProperty();
    }


    //--- Commands ---

    public void deleteCard() {
        CommandManager.execute(new DeleteCardCommand(card, boardFacade));
    }

    public void moveCard(Direction direction) {
        if (direction != null) {
            CommandManager.execute(new MoveCardCommand(card, direction, boardFacade));
        }
    }
    public void setTitle(String title) {
        CommandManager.execute(new EditTitleCommand<>(card, title, boardFacade));
    }
}