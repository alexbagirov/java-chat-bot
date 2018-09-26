enum NumberIs { BIGGER, SMALLER }

class GuessNumberGame implements IGame {
	private boolean gameActive = false;
	private BinarySearchSuggester currentBinarySearchSuggester = new BinarySearchSuggester();
	private final String gameNotActive = "Игра ещё не началась.";
	private int guessNumber;

	@Override
	public String proceedRequest(String request) {
		switch (request) {
			case "об игре":
				return "Когда ты загадаешь число и начнёшь игру, я буду предлагать разные варианты.\nЕсли твоё число" +
						"больше моего, ответь \"больше\", если меньше - \"меньше\". Если было названо верное число, " +
						"напиши \"угадал\".\nСтарт игры - команда \"старт\", остановка - \"стоп\". " +
						"Максимальное число в игре - 100, минимальное - 0";
			case "больше":
			case ">":
			case "меньше":
			case "<":
				if (!gameActive)
					return gameNotActive;
				String answer;
				try {
					guessNumber = request.equals("больше") || request.equals(">") ?
							currentBinarySearchSuggester.generateNewRequest(NumberIs.BIGGER) :
							currentBinarySearchSuggester.generateNewRequest(NumberIs.SMALLER);
					answer = String.format("Может, это: %d?", guessNumber);
				}
				catch(BorderException e) {
					answer = "Ты меня обманываешь";
					gameActive = false;
				}
				return answer;
			case "угадал":
				if (!gameActive)
					return gameNotActive;
				gameActive = false;
				return "Ура! Игра закончена. Для продолжения введи следующую команду.";
			default:
				return "Команда не распознана. Попробуй ещё раз или воспользуйся помощью.";
		}
	}

    @Override
    public void markActive() {
        gameActive = true;
    }

    @Override
    public void markInactive() {
        gameActive = false;
    }

    @Override
    public boolean isActive() {
        return gameActive;
    }

    @Override
    public String getWelcomeMessage() {
        return "Привет! Я поиграю с тобой в игру \"Угадай число\". Загадай какое-нибудь целое число " +
                "до 100 и напиши команду \"старт\".\nОстановить игру можно командой \"стоп\". Команда \"об игре\" " +
                "выведет правила на экран.";
    }

    @Override
    public String getInitialMessage() {
        currentBinarySearchSuggester = new BinarySearchSuggester();
        gameActive = true;
        return String.format("Игра началась.\nПервое число: %d?", currentBinarySearchSuggester.middle);
    }
}