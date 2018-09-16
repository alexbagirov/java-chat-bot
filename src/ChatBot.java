enum NumberIs { BIGGER, SMALLER }

class ChatBot {
	boolean gameOn = false;
	GameLogic currentGame = new GameLogic();
	String Answer(String request) throws SecurityException, IllegalArgumentException {
		switch (request) {
            case "старт":
            	currentGame = new GameLogic();
            	int guessNumber;
            	gameOn = true;
                return String.format("Игра началась.\\nПервое число: %d?", currentGame.midle);
            case "об игре":
                return "Когда ты загадаешь число и начнёшь игру, я буду предлагать разные варианты.\\nЕсли твоё число" +
                        "больше моего, ответь \\\"больше\\\", если меньше - \\\"меньше\\\". Если было названо верное число, " +
                        "напиши \\\"угадал\\\".\\nСтарт игры - команда \\\"старт\\\", остановка - \\\"стоп\\\".";
            case "стоп":
            	if (gameOn) 
            	{
            		gameOn = false;
            		return "\"Команда не распознана. Попробуй ещё раз.";
            	}
            case "больше":
            case ">":
            	if (gameOn)
            	{
            		guessNumber = currentGame.generateNewRequest(NumberIs.BIGGER);
            		return String.format("Может, это %d?", guessNumber);
            	}
            case "меньше":
            case "<":
            	if (gameOn) 
            	{
            		guessNumber = currentGame.generateNewRequest(NumberIs.SMALLER);
            		return String.format("Может, это %d?", guessNumber);
            	}
            case "угадал":
            	if (gameOn) 
            	{
            		gameOn = false;
            		return "Ура! Игра закончена. Для продолжения введи следующую команду.";
            	}
            default:
			    return "\"Команда не распознана. Попробуй ещё раз или воспользуйся помощью.";
		}
	}
}