import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Tests
{

	private int extractNumber(String s)
	{
		return Integer.parseInt(s.replaceAll("[^0-9]", ""));
	}
	
	@Test
	void testBinSearch() 
	{
		ChatBot bot = new ChatBot(new GameFactory());
		int[] testCases = {100, 99, 1, 0};
		for (int x : testCases) 
		{
			String botAnswer = bot.Answer("старт");
			int guessNumber;
			do
			{
				if (botAnswer.charAt(0) != 'И' && botAnswer.charAt(0) != 'М')
					fail("Expected guessing number");
				guessNumber = extractNumber(botAnswer);
				if (guessNumber > x) 
					botAnswer = bot.Answer("<");
				else
					botAnswer = bot.Answer(">");
				
			} while(x != guessNumber);	
			bot.Answer("стоп");
		}
	}	
	
	@Test
	void badNumber() 
	{
		ChatBot bot = new ChatBot(new GameFactory());
		String botAnswer = bot.Answer("старт");
		do
		{
			if (botAnswer.charAt(0) != 'И' && botAnswer.charAt(0) != 'М')
				fail("Expected guessing number");
			botAnswer = bot.Answer("<");
		} while(!botAnswer.equals("Ты меня обманываешь"));
	}

	@Test
	void badCommand()
	{
		ChatBot bot = new ChatBot(new GameFactory());
		String botAnswer = bot.Answer("ла-ла-ла");
		assertEquals("Команда не распознана. Попробуй ещё раз или воспользуйся помощью.", botAnswer);
	}
	
}
