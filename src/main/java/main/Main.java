package main;

import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardFacade;
import mvvm.BoardViewModel;
import view.TrelloView;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        BoardFacade boardFacade = new BoardFacade();
        BoardViewModel boardViewModel = new BoardViewModel(boardFacade);
        TrelloView trelloView = new TrelloView(primaryStage, boardViewModel);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
