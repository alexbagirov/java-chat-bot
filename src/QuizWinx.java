import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class QuizWinx implements IGame {
	private boolean gameActive = false;
	private ArrayList<QuizItem> quizSteps; 
	private int questionsCount;
	private int currentQuestionNumber;
	private int[] answerStatistic;
	private final String[] characterOrder = new String[] {"Блум", "Стелла", "Муза", "Текна", "Флора", "Лейла"};
	private List<Answer> previousAnswers = new ArrayList<>();
	private final char firstAnswer = 'A';
	
	public QuizWinx(String fileName) throws FileNotFoundException
	{
		currentQuestionNumber = 0;
		File file = new File(fileName);
		Scanner sc = new Scanner(file); 
		this.questionsCount =  Integer.parseInt(sc.nextLine());
		this.quizSteps = new ArrayList<>();
		answerStatistic = new int[questionsCount];
		for (int i = 0; i < questionsCount; i++)
			answerStatistic[i] = 0;
	    parseFile(sc);
	}
	
	private void parseFile(Scanner sc) {
		int answerNumber = 0;
		while (sc.hasNextLine()) 
	    {
	    	if (answerNumber == 0) {
	    		String currentQuestion = sc.nextLine();
	    		ArrayList<Answer> currentAnswers = new ArrayList<>();
	    		for(int i = 0; i < questionsCount; ++i) 
	    			currentAnswers.add(new Answer(sc.nextLine(), i));
	    		this.quizSteps.add(new QuizItem(currentAnswers, currentQuestion)); 		
	    	}
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
		return "";
	}
		
	private static int max(int[] array) {
        int maximum = array[0];
        for (int anArray : array) if (maximum < anArray) maximum = anArray;
        return maximum;
    }
	
	private int indexOf(int[] array, int element)
	{
		for (int i = 0; i < array.length; i++)
			if (array[i] == element)
				return i;
		return -1;
	}

	private List<String> getAnswers(List<Answer> answerItems)
	{
		List<String> answers = new ArrayList<>();
		for (Answer answer: answerItems)
			answers.add(answer.answer);
		return answers;
	}
	
	@Override
	public String proceedRequest(String request) {
		if (currentQuestionNumber > questionsCount)
		{
			markInactive();
			return characterOrder[indexOf(answerStatistic, max(answerStatistic))];
		}
		int answerIndex = request.charAt(0) - firstAnswer;
		answerStatistic[previousAnswers.get(answerIndex).characterIndex]++;
		List<Answer> answers = new ArrayList<>(quizSteps.get(currentQuestionNumber).answers);
		Collections.shuffle(answers);
		currentQuestionNumber++;
		previousAnswers = answers;
		return quizSteps.get(currentQuestionNumber).question + "\n" + String.join("\n", getAnswers(answers));
	}
}
