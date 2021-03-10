package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mvvm.BoardViewModel;

public class TrelloView extends VBox {

    private static final Color BACKGROUND_COLOR = Color.web("#E5E5E5");

    private final BoardViewModel boardViewModel;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONSTRUCTOR

    public TrelloView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        Scene scene = new Scene(this);
        primaryStage.setTitle("Trello");
        primaryStage.setScene(scene);
        build();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  CONFIG GRAPHIC COMPONENTS

    public void build() {
        makeComponentsHierarchy();
        configStylesVBow();
    }

    private void makeComponentsHierarchy() {
        this.getChildren().add(new BoardView(boardViewModel));
    }

    private void configStylesVBow() {
        CornerRadii corners = new CornerRadii(0);
        BackgroundFill backgroundFill = new BackgroundFill(BACKGROUND_COLOR, corners, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        setBackground(background);
        setPrefSize(1000, 750);
        setSpacing(15);
        setPadding(new Insets(25));
        setVgrow(this.getChildren().get(0), Priority.ALWAYS);
    }

}