import java.util.ArrayList;
import java.util.List;

public class QuizItem {
	public List<Answer> answers = new ArrayList<Answer>();
	
	public String question = null;
	
	public QuizItem(ArrayList<Answer> answers, String question){
		this.answers = answers;
		this.question = question;
	}
}
