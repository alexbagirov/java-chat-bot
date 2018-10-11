package database;

import java.util.Arrays;

public class GameDataSet {
    int userId;
    public int currentQuestionId;
    public int[] answerStatistics;
    public int[] answersOrder;

    GameDataSet(int userId, int currentQuestionId, Integer[] answerStatistics, Integer[] answersOrder) {
        this.userId = userId;
        this.currentQuestionId = currentQuestionId;
        this.answerStatistics = Arrays.stream(answerStatistics).mapToInt(Integer::intValue).toArray();
        this.answersOrder = Arrays.stream(answersOrder).mapToInt(Integer::intValue).toArray();
    }
}
