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
	private final String[] characterOrder;
	private List<Integer> answersOrder;

    public WinxQuiz(String fileName) throws FileNotFoundException
	{
		currentQuestionNumber = 0;
		File file = new File(fileName);
		Scanner sc = new Scanner(file); 
		answersCount =  Integer.parseInt(sc.nextLine());
		quizSteps = new ArrayList<>();
		characterOrder = sc.nextLine().split(" ");
		answerStatistic = new ArrayList<>();
		for (int i = 0; i < answersCount; i++)
			answerStatistic.add(0);
	    parseFile(sc);

	    answersOrder = new ArrayList<>();
	    for (int i = 0; i < answersCount; ++i) {
	        answersOrder.add(i);
        }
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
	public ChatBotReply proceedRequest(String request) {
		if (currentQuestionNumber > answersCount)
		{
			markInactive();
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
		return new ChatBotReply(quizSteps.get(currentQuestionNumber - 1).question, getAnswers(answersOrder));
	}
}
