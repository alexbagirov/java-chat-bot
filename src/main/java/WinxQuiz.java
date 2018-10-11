import com.google.common.primitives.Ints;
import database.DatabaseWorker;
import database.GameDataSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WinxQuiz implements IGame {
	private ArrayList<QuizItem> quizSteps;
	private int answersCount;
	private String[] characterOrder;

	private DatabaseWorker db = new DatabaseWorker();

    WinxQuiz(String fileName) throws FileNotFoundException
	{
	    db.connect();
	    db.initDatabase();
		File file = new File(fileName);
		Scanner sc = new Scanner(file);
		parseQuizRules(sc);
		quizSteps = parseQuizSteps(sc);
		sc.close();
	}

	private void parseQuizRules(Scanner sc) {
		answersCount =  Integer.parseInt(sc.nextLine());
		characterOrder = sc.nextLine().split(" ");
	}

    private ArrayList<QuizItem> parseQuizSteps(Scanner sc) {
    	ArrayList<QuizItem> steps = new ArrayList<>();

        while (sc.hasNextLine())
        {
            String currentQuestion = sc.nextLine();
            ArrayList<Answer> currentAnswers = new ArrayList<>();
            for (int i = 0; i < answersCount; ++i)
                currentAnswers.add(new Answer(sc.nextLine(), i));
            steps.add(new QuizItem(currentAnswers, currentQuestion));
        }

        return steps;
    }

	private GameDataSet getGameData(int userId) {
        return db.getGameData(userId);
    }

	private List<String> getAnswersList(int[] order, int currentQuestionNumber)
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

	private void shuffleAnswers(int[] answers) {
        Random rand = new Random();

        for (int i = 0; i < answers.length; ++i) {
            int position = rand.nextInt(answers.length);
            int t = answers[i];
            answers[i] = answers[position];
            answers[position] = t;
        }
    }
	
	@Override
	public ChatBotReply proceedRequest(String request, int userId) {
        GameDataSet gameData = getGameData(userId);

		if (gameData.currentQuestionId > answersCount)
		{
			markInactive(userId);
			return new ChatBotReply(String.format("Всё понятно. Ты %s",
					characterOrder[Ints.indexOf(gameData.answerStatistics,
                    Ints.max(gameData.answerStatistics))]), null);
		}
		if (gameData.currentQuestionId > 0) {
            final char firstAnswer = 'A';
            char firstLetter = request.charAt(0);
            if (firstLetter < 'A' || firstLetter > 'F')
                return new ChatBotReply("Подумай ещё раз!", getAnswersList(gameData.answersOrder, gameData.currentQuestionId));
            int answerIndex = gameData.answersOrder[request.charAt(0) - firstAnswer];
            gameData.answerStatistics[answerIndex]++;
        }

        gameData.currentQuestionId++;
		shuffleAnswers(gameData.answersOrder);

		db.setGameData(userId, gameData);
		return new ChatBotReply(quizSteps.get(gameData.currentQuestionId - 1).question, getAnswersList(gameData.answersOrder, gameData.currentQuestionId));
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
		return "Привет! Сейчас мы узнаем, кто ты из фей Winx.";
	}
}
