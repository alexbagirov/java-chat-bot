package database;

import com.google.common.primitives.Ints;

import java.util.Arrays;
import java.util.List;

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

    public GameDataSet(int userId, int currentQuestionId, List<Integer> answerStatistics, List<Integer> answersOrder) {
        this.userId = userId;
        this.currentQuestionId = currentQuestionId;
        this.answerStatistics = Ints.toArray(answerStatistics);
        this.answersOrder = Ints.toArray(answersOrder);
    }
}
