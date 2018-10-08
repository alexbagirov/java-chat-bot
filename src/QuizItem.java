import java.util.ArrayList;
import java.util.List;

public class QuizItem {
	List<Answer> answers;
	String question;
	
	public QuizItem(ArrayList<Answer> answers, String question){
		this.answers = answers;
		this.question = question;
	}
}
