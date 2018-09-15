enum NumberIs { BIGGER, SMALLER }

class ChatBot {
	String Answer(String request) throws SecurityException, IllegalArgumentException {
		switch (request) {
            case "старт":
                return String.format("Игра началась.\nПервое число: %d?", GuessNumber());
            case "о игре":
                return "Когда ты загадаешь число и начнёшь игру, я буду предлагать разные варианты.\nЕсли твоё число" +
                        "больше моего, ответь \"больше\", если меньше - \"меньше\". Если было названо верное число, " +
                        "напиши \"угадал\".\nСтарт игры - команда \"старт\", остановка - \"стоп\".";
            case "стоп":
                return "Игра закончена. Для продолжения введи следующую команду.";
            case "больше":
            case ">":
                UpdateBorders(NumberIs.BIGGER);
                return String.format("Может, это %d?", GuessNumber());
            case "меньше":
            case "<":
                UpdateBorders(NumberIs.SMALLER);
                return String.format("Может, это %d?", GuessNumber());
            case "угадал":
                return "Ура! Игра закончена. Для продолжения введи следующую команду.";
			default:
			    return "Команда не распознана. Попробуй ещё раз.";
		}
	}

	private int GuessNumber() {
	    throw new UnsupportedOperationException();
    }

    private void UpdateBorders(NumberIs answer) {
	    throw new UnsupportedOperationException();
    }
}