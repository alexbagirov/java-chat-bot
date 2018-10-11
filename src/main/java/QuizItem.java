import java.util.ArrayList;
import java.util.List;

class QuizItem {
	List<Answer> answers;
	String question;
	
	QuizItem(ArrayList<Answer> answers, String question){
		this.answers = answers;
		this.question = question;
	}
}
