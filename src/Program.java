import java.util.Scanner;

public class Program {
	public static void main(String[] args) throws SecurityException, IllegalArgumentException {
		ChatBot chatBot = new ChatBot();
		Scanner sc = new Scanner(System.in);
		System.out.println("������! � ������� � ����� � ���� \\\"������ �����\\\". ������� �����-������ ����� ����� � \" +\r\n" + 
				"                \"������ ������� \\\"�����\\\".\\n���������� ���� ����� �������� \\\"����\\\". ������� \\\"�� ����\\\" ������� \" +\r\n" + 
				"                \"������� �� �����.");

		while(true)
		{
			String request = sc.nextLine();
			System.out.println(chatBot.Answer(request));
		}
	}
}