package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.TrelloFacade;
import mvvm.TrelloViewModel;
import view.TrelloView;


public class Main {

    @Override
    public void start(Stage primaryStage) throws Exception {
        TrelloFacade trelloFacade = new TrelloFacade();
        TrelloViewModel.init(trelloFacade);
        new TrelloView(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
