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
			String botAnswer = bot.answer("старт");
			int guessNumber;
			do
			{
				if (botAnswer.charAt(0) != 'И' && botAnswer.charAt(0) != 'М')
					fail("Expected guessing number");
				guessNumber = extractNumber(botAnswer);
				if (guessNumber > x) 
					botAnswer = bot.answer("<");
				else
					botAnswer = bot.answer(">");
				
			} while(x != guessNumber);	
			bot.answer("стоп");
		}
	}	
	
	@Test
	void badNumber() 
	{
		ChatBot bot = new ChatBot(new GameFactory());
		String botAnswer = bot.answer("старт");
		do
		{
			if (botAnswer.charAt(0) != 'И' && botAnswer.charAt(0) != 'М')
				fail("Expected guessing number");
			botAnswer = bot.answer("<");
		} while(!botAnswer.equals("Ты меня обманываешь"));
	}

	@Test
	void badCommand()
	{
		ChatBot bot = new ChatBot(new GameFactory());
		String botAnswer = bot.answer("ла-ла-ла");
		assertEquals("Команда не распознана. Попробуй ещё раз или воспользуйся помощью.", botAnswer);
	}
	
}
