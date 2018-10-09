import database.DatabaseWorker;
import database.GameDataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WinxQuiz implements IGame {
	private ArrayList<QuizItem> quizSteps;
	private int answersCount;
	private int currentQuestionNumber;
	private List<Integer> answerStatistic;
	private final String[] characterOrder;
	private List<Integer> answersOrder;

	private DatabaseWorker db = new DatabaseWorker();

    WinxQuiz(String fileName) throws FileNotFoundException
	{
	    db.connect();
	    db.initDatabase();
		File file = new File(fileName);
		Scanner sc = new Scanner(file); 
		answersCount =  Integer.parseInt(sc.nextLine());
		quizSteps = new ArrayList<>();
		characterOrder = sc.nextLine().split(" ");
	    parseFile(sc);
	}

    private void parseFile(Scanner sc) {
        while (sc.hasNextLine())
        {
            String currentQuestion = sc.nextLine();
            ArrayList<Answer> currentAnswers = new ArrayList<>();
            for (int i = 0; i < answersCount; ++i)
                currentAnswers.add(new Answer(sc.nextLine(), i));
            quizSteps.add(new QuizItem(currentAnswers, currentQuestion));
        }
    }

	private void getGameData(int userId) {
        GameDataSet userData = db.getGameData(userId);
        currentQuestionNumber = userData.currentQuestionId;
        answerStatistic = new ArrayList<>();
        for (int i = 0; i < userData.answerStatistics.length; ++i)
            answerStatistic.add(userData.answerStatistics[i]);
        answersOrder = new ArrayList<>();
        for (int i = 0; i < userData.answersOrder.length; ++i)
            answersOrder.add(userData.answersOrder[i]);
    }

	private List<String> getAnswers(List<Integer> order)
	{
        List<Answer> questionAnswers = quizSteps.get(currentQuestionNumber - 1).answers;
	    List<String> orderedAnswers = new ArrayList<>();

	    int symbol = 65;
		for (int index : order) {
			orderedAnswers.add(String.format("%c. %s", (char) symbol, questionAnswers.get(index).answer));
			symbol++;
		}
		return orderedAnswers;
	}
	
	@Override
	public ChatBotReply proceedRequest(String request, int userId) {
        if (currentQuestionNumber == 0) {
            db.destroyGameData(userId);
            db.createGameData(userId);
        }

        getGameData(userId);

		if (currentQuestionNumber > answersCount)
		{
			markInactive(userId);
			return new ChatBotReply(characterOrder[answerStatistic.indexOf(Collections.max(answerStatistic))],
                    null);
		}
		if (currentQuestionNumber > 0) {
            final char firstAnswer = 'A';
            char firstLetter = request.charAt(0);
            if (firstLetter < 'A' || firstLetter > 'F')
                return new ChatBotReply("Подумай ещё раз!", getAnswers(answersOrder));
            int answerIndex = answersOrder.get(request.charAt(0) - firstAnswer);
            answerStatistic.set(answerIndex, answerStatistic.get(answerIndex) + 1);
        }

        currentQuestionNumber++;
		Collections.shuffle(answersOrder);

		db.setGameData(userId, new GameDataSet(userId, currentQuestionNumber, answerStatistic, answersOrder));
		return new ChatBotReply(quizSteps.get(currentQuestionNumber - 1).question, getAnswers(answersOrder));
	}

	@Override
	public void markActive(int userId) {
		db.markGameActive(userId);
	}

	@Override
	public void markInactive(int userId) {
		db.markGameInactive(userId);
	}

	@Override
	public boolean isActive(int userId) {
		return db.isGameActive(userId);
	}

	@Override
	public String getInitialMessage(int userId) {
		markActive(userId);
		return "Привет! Сейчас мы узнаем, кто ты из фей Winx.";
	}
}
