import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class WinxQuiz implements IGame {
	private boolean gameActive = false;
	private ArrayList<QuizItem> quizSteps; 
	private int answersCount;
	private int currentQuestionNumber;
	private List<Integer> answerStatistic;
	private final String[] characterOrder = new String[] {"Блум", "Стелла", "Муза", "Текна", "Флора", "Лейла"};
	private List<Answer> previousAnswers = new ArrayList<>();

    public WinxQuiz(String fileName) throws FileNotFoundException
	{
		currentQuestionNumber = 0;
		File file = new File(fileName);
		Scanner sc = new Scanner(file); 
		answersCount =  Integer.parseInt(sc.nextLine());
		quizSteps = new ArrayList<>();
		answerStatistic = new ArrayList<>();
		for (int i = 0; i < answersCount; i++)
			answerStatistic.add(0);
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
	
    @Override
    public void markActive() {
        gameActive = true;
    }

    @Override
    public void markInactive() {
        gameActive = false;
    }

    @Override
    public boolean isActive() {
        return gameActive;
    }

    @Override
	public String getInitialMessage() {
        gameActive = true;
		return "Привет! Сейчас мы узнаем, кто ты из фей Winx.";
	}

	private List<String> getAnswers(List<Answer> answerItems)
	{
		List<String> answers = new ArrayList<>();
		int symbol = 65;
		for (Answer answer: answerItems) {
			answers.add(String.format("%c. %s", (char) symbol, answer.answer));
			symbol++;
		}
		return answers;
	}
	
	@Override
	public ChatBotReply proceedRequest(String request) {
		if (currentQuestionNumber > answersCount)
		{
			markInactive();
			return new ChatBotReply(characterOrder[answerStatistic.indexOf(Collections.max(answerStatistic))],
                    null);
		}
		if (currentQuestionNumber > 0) {
            final char firstAnswer = 'A';
            int answerIndex = request.charAt(0) - firstAnswer;
            answerStatistic.set(previousAnswers.get(answerIndex).characterIndex,
                    answerStatistic.get(previousAnswers.get(answerIndex).characterIndex) + 1);
        }
		List<Answer> answers = new ArrayList<>(quizSteps.get(currentQuestionNumber).answers);
		Collections.shuffle(answers);
		currentQuestionNumber++;
		previousAnswers = answers;
		return new ChatBotReply(quizSteps.get(currentQuestionNumber - 1).question, getAnswers(answers));
	}
}
