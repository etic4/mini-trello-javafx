package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardFacade;
import model.TrelloFacade;
import mvvm.TrelloViewModel;
import view.TrelloView;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        TrelloFacade trelloFacade = new TrelloFacade();
        TrelloViewModel trelloViewModel = new TrelloViewModel(trelloFacade);
        TrelloView trelloView = new TrelloView(primaryStage, trelloViewModel);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
