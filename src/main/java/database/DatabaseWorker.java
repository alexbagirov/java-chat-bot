package database;

import java.sql.*;
import java.util.Objects;

public class DatabaseWorker {
    private Connection c;

    public void connect() {
        try {
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            c = DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void reconnect() {
        try {
            c.close();
            connect();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void initDatabase() {
        try {
            if (c.isClosed())
                reconnect();

            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS quiz(" +
                    "id INT PRIMARY KEY NOT NULL, " +
                    "current_question_id INT NOT NULL, " +
                    "answer_statistics INTEGER[6], " +
                    "answers_order INTEGER[6], " +
                    "game_active BOOLEAN)";
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public GameDataSet getGameData(int userId) {
        try {
            if (c.isClosed())
                reconnect();

            PreparedStatement stmt = c.prepareStatement("SELECT * FROM quiz WHERE id = ?;");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            rs.next();
            int id = rs.getInt("id");
            int currentQuestionId = rs.getInt("current_question_id");
            Array answerStatistics = rs.getArray("answer_statistics");
            Array answersOrder = rs.getArray("answers_order");

            rs.close();
            stmt.close();
            return new GameDataSet(id, currentQuestionId,
                    (Integer[]) Objects.requireNonNull(answerStatistics).getArray(),
                    (Integer[]) Objects.requireNonNull(answersOrder).getArray());
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    public void setGameData(int userId, GameDataSet userData) {
        try {
            if (c.isClosed())
                reconnect();

            destroyGameData(userId);

            PreparedStatement stmt = c.prepareStatement("INSERT INTO quiz VALUES(?, ?, ?, ?, TRUE)");
            stmt.setInt(1, userData.userId);
            stmt.setInt(2, userData.currentQuestionId);
            stmt.setArray(3, c.createArrayOf("integer", transformArray(userData.answerStatistics)));
            stmt.setArray(4, c.createArrayOf("integer", transformArray(userData.answersOrder)));
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private Integer[] transformArray(int[] array) {
        Integer[] target = new Integer[array.length];
        for (int i = 0; i < array.length; ++i) {
            target[i] = array[i];
        }
        return target;
    }

    private void runSql (int userId, String query) {
        try {
            if (c.isClosed())
                reconnect();

            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void createGameData(int userId) {
        runSql(userId, "INSERT INTO quiz VALUES(?, 0, '{0,0,0,0,0,0}', '{0,1,2,3,4,5}', TRUE)");
    }

    public void markGameActive(int userId) {
        destroyGameData(userId);
        createGameData(userId);
    }

    public void markGameInactive(int userId) {
        runSql(userId, "UPDATE quiz SET game_active = FALSE WHERE id = ?");
    }

    private void destroyGameData(int userId) {
        runSql(userId, "DELETE FROM quiz WHERE id = ?");
    }

    public boolean isGameActive(int userId) {
        try {
            if (c.isClosed())
                reconnect();

            PreparedStatement stmt = c.prepareStatement("SELECT game_active FROM quiz WHERE id = ?;");
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            boolean gameActive = rs.getBoolean("game_active");

            rs.close();
            stmt.close();
            return gameActive;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }
}
