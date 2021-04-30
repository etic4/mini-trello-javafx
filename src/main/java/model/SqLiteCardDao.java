package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class SqLiteCardDao implements Dao<Card> {
    private static final String SQL_GET_BY_ID = "SELECT * FROM `Card` WHERE `card_id` = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM `Card` WHERE `column` = ? " +
                                            "ORDER BY `position`";
    private static final String SQL_INSERT = "INSERT INTO `Card` (`title`, `position`, `column`) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `Card` SET " +
                                            "`title` = ?, " +
                                            "`position` = ?, " +
                                            "`column` = ? " +
                                            "WHERE `card_id` = ?";
    private static final String SQL_DELETE = "DELETE FROM `Card` WHERE `card_id` = ?";


    @Override
    public Card get(int id) {
        Card Card = null;

        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_BY_ID);
            preparedStatement.setInt(1, id);

            var res = preparedStatement.executeQuery();

            if (res.next()) {
                Card = getInstance(res);
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Card;
    }

    @Override
    public List<Card> get_all(int id) {
        ArrayList<Card> Cards = new ArrayList<>();

        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL);
            preparedStatement.setInt(1, id);

            var res = preparedStatement.executeQuery();

            while (res.next()) {
                Cards.add(getInstance(res));
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Cards;
    }

    @Override
    public Card save(Card card) {
        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);

            setValues(card, preparedStatement);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de la carte a échoué, pas de ligne ajoutée");
            }

            var CardId = preparedStatement.getGeneratedKeys().getInt(1);
            card.setId(CardId);

            conn.close();
            return card;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Card card) {
        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            setValues(card, preparedStatement);
            // clause WHERE
            preparedStatement.setInt(4, card.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La mise à jour de la carte a échoué: aucune colonne modifiée.");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(Card card) {
        try {
            Connection conn = SqliteConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, card.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La suppression de la carte a échoué: aucune colonne modifiée.");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setValues(Card Card, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, Card.getTitle());
        preparedStatement.setInt(2, Card.getPosition());
        preparedStatement.setInt(3, Card.getColumnId());
    }

    private Card getInstance(ResultSet res) throws SQLException {
        var id = res.getInt(1);
        var title = res.getString(2);
        var position = res.getInt(3);

        return new Card(id, title, position);
    }
}
