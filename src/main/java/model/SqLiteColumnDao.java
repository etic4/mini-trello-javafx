package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class SqLiteColumnDao extends SqliteConnection implements Dao<Column> {
    private static final String SQL_GET_BY_ID = "SELECT * FROM `Column` WHERE `column_id` = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM `Column` WHERE `board` = ? " +
                                            "ORDER BY `position`";
    private static final String SQL_INSERT = "INSERT INTO `Column` (`title`, `position`, `board`) VALUES(?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `Column` SET " +
                                            "`title` = ?, " +
                                            "`position` = ?, " +
                                            "`board` = ? " +
                                            "WHERE `column_id` = ?";
    private static final String SQL_UPDATE_POSITION = "UPDATE `Column` SET " +
                                            "`position` = ? " +
                                            "WHERE `column_id` = ?";
    private static final String SQL_DELETE = "DELETE FROM `Column` WHERE `column_id` = ?";

    @Override
    public  Column get(int id) {
        Column column = null;

        try {
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_BY_ID);
            preparedStatement.setInt(1, id);

            var res = preparedStatement.executeQuery();

            if (res.next()) {
                column = getInstance(res);
            }

            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return column;
    }


    @Override
    public List<Column> get_all(int id) {
        ArrayList<Column> columns = new ArrayList<>();

        try {
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_GET_ALL);
            preparedStatement.setInt(1, id);

            var res = preparedStatement.executeQuery();

            while (res.next()) {
                columns.add(getInstance(res));
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return columns;
    }

    @Override
    public Column save(Column column) {
        try {
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT,
                    Statement.RETURN_GENERATED_KEYS);

            setValues(column, preparedStatement);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("L'ajout de la colonne a échoué, pas de ligne ajoutée");
            }

            var columnId = preparedStatement.getGeneratedKeys().getInt(1);
            column.setId(columnId);

            conn.close();
            return column;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Column column) {
        try {
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE);
            setValues(column, preparedStatement);
            // clause WHERE
            preparedStatement.setInt(4, column.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La mise à jour de la colonne a échoué: aucune colonne modifiée.");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updatePositions(List<Column> columns) {
        if (columns.size() > 0) {
            try {
                Connection conn = getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(SQL_UPDATE_POSITION);

                for (var column : columns) {
                    preparedStatement.setInt(1, column.getPosition());
                    preparedStatement.setInt(2, column.getId());
                    preparedStatement.addBatch();
                }

                int[] affectedRows = preparedStatement.executeBatch();
                if (affectedRows.length == 0) {
                    throw new SQLException("La mise à jour des colonnes a échoué: aucune colonne modifiée.");
                }
                conn.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Column column) {
        try {
            Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, column.getId());


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La suppression de la colonne a échoué: aucune colonne modifiée.");
            }
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setValues(Column column, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, column.getTitle());
        preparedStatement.setInt(2, column.getPosition());
        preparedStatement.setInt(3, column.getBoardId());
    }

    private Column getInstance(ResultSet res) throws SQLException {
        var id = res.getInt(1);
        var title = res.getString(2);

        return new Column(id, title);
    }
}
