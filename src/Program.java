import java.util.Scanner;

public class Program {
	public static void main(String[] args) throws SecurityException, IllegalArgumentException {
		ChatBot chatBot = new ChatBot();
		Scanner sc = new Scanner(System.in);
		System.out.println("Привет! Я поиграю с тобой в игру \"Угадай число\". Загадай какое-нибудь целое число " +
                "до 100 и напиши команду \"старт\".\nОстановить игру можно командой \"стоп\". Команда \"о игре\" " +
                "выведет правила на экран.");

		while(true)
		{
			String request = sc.nextLine();
			System.out.println(chatBot.Answer(request));
		}
	}
}