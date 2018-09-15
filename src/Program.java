import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Program {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		ChatBot chatBot = new ChatBot();
		Scanner sc = new Scanner(System.in);
		while(true)
		{
			String request = sc.nextLine();	
			chatBot.Answer(chatBot.answers.get(request));
		}
	}
}