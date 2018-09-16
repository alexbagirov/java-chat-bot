enum NumberIs { BIGGER, SMALLER }

class ChatBot {
	private boolean gameActive = false;
	private Game currentGame = new Game();

	String Answer(String request) throws SecurityException, IllegalArgumentException {
		switch (request) {
            case "старт":
            	currentGame = new Game();
            	int guessNumber;
            	gameActive = true;
                return String.format("Игра началась.\nПервое число: %d?", currentGame.middle);
            case "об игре":
                return "Когда ты загадаешь число и начнёшь игру, я буду предлагать разные варианты.\nЕсли твоё число" +
                        "больше моего, ответь \"больше\", если меньше - \"меньше\". Если было названо верное число, " +
                        "напиши \"угадал\".\nСтарт игры - команда \"старт\", остановка - \"стоп\". " +
						"Максимальное число в игре - 100";
            case "стоп":
            	if (gameActive)
            	{
            		gameActive = false;
            		return "\"Команда не распознана. Попробуй ещё раз.";
            	}
            case "больше":
            case ">":
            	if (gameActive)
            	{
            		guessNumber = currentGame.generateNewRequest(NumberIs.BIGGER);
            		if (guessNumber != -1)
            			return String.format("Может, это %d?", guessNumber);
            		return "Ты меня обманываешь";
            	}
            case "меньше":
            case "<":
            	if (gameActive)
            	{
            		guessNumber = currentGame.generateNewRequest(NumberIs.SMALLER);
            		if (guessNumber != -1)
            			return String.format("Может, это %d?", guessNumber);
            		return "Ты меня обманываешь";
            	}
            case "угадал":
            	if (gameActive)
            	{
            		gameActive = false;
            		return "Ура! Игра закончена. Для продолжения введи следующую команду.";
            	}
            default:
			    return "Команда не распознана. Попробуй ещё раз или воспользуйся помощью.";
		}
	}
}