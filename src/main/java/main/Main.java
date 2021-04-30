package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.TrelloFacade;
import mvvm.TrelloViewModel;
import view.TrelloView;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        TrelloFacade trelloFacade = new TrelloFacade();
        TrelloViewModel.setFacade(trelloFacade);
        new TrelloView(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
