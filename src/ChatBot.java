class ChatBot {
    private IGame gameInstance;
    private IGameFactory gameFactory;

    ChatBot(IGameFactory gameFactory) {
        this.gameFactory = gameFactory;
        gameInstance = gameFactory.create();
    }

    ChatBotReply answer(String message, int userId) {
        switch (message) {
            case "/start":
            case "Старт":
                gameInstance = gameFactory.create();
                gameInstance.markActive(userId);
                ChatBotReply firstQuestion = gameInstance.proceedRequest("", userId);
                return new ChatBotReply(gameInstance.getInitialMessage(userId) +
                        '\n' + firstQuestion.message, firstQuestion.keyboardOptions);
            case "/stop":
            case "Стоп":
                if (!gameInstance.isActive(userId))
                    return new ChatBotReply("Игра ещё не началась.", null);
                gameInstance.markInactive(userId);
                return new ChatBotReply("Игра закончена.", null);
            default:
                if (gameInstance.isActive(userId))
                    return gameInstance.proceedRequest(message, userId);
                else
                    return new ChatBotReply("Игра ещё не началась.", null);
        }
    }
}
