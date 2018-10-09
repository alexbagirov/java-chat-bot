package database;

import java.sql.*;
import java.util.Arrays;

public class DatabaseWorker {
    private Connection c;

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    System.getenv("DB_URL"),
                    System.getenv("DB_USER"),
                    System.getenv("DB_PASS"));
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

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM quiz WHERE id = %d;", userId));

            int id = 0;
            int currentQuestionId = 0;
            Array answerStatistics = null;
            Array answersOrder = null;
            boolean gameActive = true;

            while (rs.next()) {
                id = rs.getInt("id");
                currentQuestionId = rs.getInt("current_question_id");
                answerStatistics = rs.getArray("answer_statistics");
                answersOrder = rs.getArray("answers_order");
                gameActive = rs.getBoolean("game_active");
            }
            rs.close();
            stmt.close();
            return new GameDataSet(id, currentQuestionId,
                    (Integer[]) answerStatistics.getArray(),
                    (Integer[]) answersOrder.getArray());
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

            Statement stmt = c.createStatement();
            String sql = String
                    .format("INSERT INTO quiz VALUES(%d, %d, '%s', '%s', TRUE)",
                        userData.userId, userData.currentQuestionId,
                        Arrays.toString(userData.answerStatistics),
                        Arrays.toString(userData.answersOrder))
                    .replace('[', '{')
                    .replace(']', '}');
            stmt.executeUpdate(sql);

            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void runSql (int userId, String query) {
        try {
            if (c.isClosed())
                reconnect();

            Statement stmt = c.createStatement();

            String sql = String.format(query, userId);
            stmt.executeUpdate(sql);
            stmt.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void createGameData(int userId) {
        runSql(userId, "INSERT INTO quiz VALUES(%d, 0, '{0,0,0,0,0,0}', '{0,1,2,3,4,5}', TRUE)");
    }

    public void markGameActive(int userId) {
        runSql(userId, "UPDATE quiz SET game_active = TRUE WHERE id = %d");
    }

    public void markGameInactive(int userId) {
        runSql(userId, "UPDATE quiz SET game_active = FALSE WHERE id = %d");
    }

    public void destroyGameData(int userId) {
        runSql(userId, "DELETE FROM quiz WHERE id = %d");
    }

    public boolean isGameActive(int userId) {
        try {
            if (c.isClosed())
                reconnect();

            Statement stmt = c.createStatement();
            boolean gameActive = true;

            ResultSet rs = stmt.executeQuery(String.format("SELECT game_active FROM quiz WHERE id = %d;", userId));
            while (rs.next()) {
                gameActive = rs.getBoolean("game_active");
            }
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
