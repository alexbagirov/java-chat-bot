enum NumberIs { BIGGER, SMALLER }

class ChatBot {
	private boolean gameActive = false;
	private Game currentGame = new Game();
	private final String gameNotActive = "Игра ещё не началась.";
	private int guessNumber;

	String Answer(String request) throws SecurityException, IllegalArgumentException {
		switch (request) {
            case "старт":
            	currentGame = new Game();
            	gameActive = true;
                return String.format("Игра началась.\nПервое число: %d?", currentGame.middle);
            case "об игре":
                return "Когда ты загадаешь число и начнёшь игру, я буду предлагать разные варианты.\nЕсли твоё число" +
                        "больше моего, ответь \"больше\", если меньше - \"меньше\". Если было названо верное число, " +
                        "напиши \"угадал\".\nСтарт игры - команда \"старт\", остановка - \"стоп\". " +
						"Максимальное число в игре - 100";
            case "стоп":
            	if (!gameActive) return gameNotActive;

				gameActive = false;
				return "Команда не распознана. Попробуй ещё раз.";
            case "больше":
            case ">":
            case "меньше":
            case "<":
	            if (!gameActive) return gameNotActive;
	            String answer = "";
	            try {
					guessNumber = request.equals("больше") || request.equals(">") ?
							currentGame.generateNewRequest(NumberIs.BIGGER) :
							currentGame.generateNewRequest(NumberIs.SMALLER);
					answer = String.format("Может, это %d?", guessNumber);
            	}
            	catch(BorderException e) {
            		answer = "Ты меня обманываешь";
            	}
	            return answer;
            case "угадал":
            	if (!gameActive) return gameNotActive;

				gameActive = false;
				return "Ура! Игра закончена. Для продолжения введи следующую команду.";
            default:
			    return "Команда не распознана. Попробуй ещё раз или воспользуйся помощью.";
		}
	}
}