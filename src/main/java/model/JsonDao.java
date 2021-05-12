package model;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


class JsonDao implements Backend {
    public static final String FILENAME = "trello.json";
    public static int last_id = 0;

    public Dao<Board> getBoardDao() {
        return new JsonBoardDao();
    }

    public Dao<Column> getColumnDao() {
        return new JsonColumnDao();
    }

    public Dao<Card> getCardDao() {
        return new JsonCardDao();
    }

    public void seedData() {
        var boardObject = new JSONObject();
        boardObject.put("board_id", ++last_id);
        boardObject.put("title", "Board 1");

        var columnsArray = new JSONArray();

        columnsArray.add(getCol1());
        columnsArray.add(getCol2());
        columnsArray.add(getCol3());

        boardObject.put("columns", columnsArray);


        JSONObject trello = new JSONObject();
        trello.put("last_id", last_id);

        var boardsArray = new JSONArray();
        boardsArray.add(boardObject);

        trello.put("boards", boardsArray);

        try (FileWriter file = new FileWriter(FILENAME)) {
            file.write(trello.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getCol1() {
        var colObject = new JSONObject();

        colObject.put("column_id", ++last_id);
        colObject.put("title", "Première colonne");

        var colCardsArray = new JSONArray();

        var card1 = new JSONObject();
        card1.put("card_id", ++last_id);
        card1.put("title", "Une carte");

        colCardsArray.add(card1);

        var card2 = new JSONObject();
        card2.put("card_id", ++last_id);
        card2.put("title", "Autre carte");

        colCardsArray.add(card2);

        colObject.put("cards", colCardsArray);

        return colObject;
    }

    private JSONObject getCol2() {
        var colObject = new JSONObject();

        colObject.put("column_id", ++last_id);
        colObject.put("title", "A faire");

        var colCardsArray = new JSONArray();

        var card1 = new JSONObject();
        card1.put("card_id", ++last_id);
        card1.put("title", "Trouver un titre");

        colCardsArray.add(card1);

        colObject.put("cards", colCardsArray);

        return colObject;
    }

    private JSONObject getCol3() {
        var colObject = new JSONObject();

        colObject.put("column_id", ++last_id);
        colObject.put("title", "A ne pas faire");

        var colCardsArray = new JSONArray();

        var card1 = new JSONObject();
        card1.put("card_id", ++last_id);
        card1.put("title", "Tout mélanger");

        colCardsArray.add(card1);

        var card2 = new JSONObject();
        card2.put("card_id", ++last_id);
        card2.put("title", "Ne pas étudier");

        colCardsArray.add(card2);

        var card3 = new JSONObject();
        card3.put("card_id", ++last_id);
        card3.put("title", "Trop travailler");

        colCardsArray.add(card3);

        colObject.put("cards", colCardsArray);

        return colObject;
    }

    public static void main(String[] args) {
        new JsonDao().seedData();
    }
}
