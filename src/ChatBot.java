class ChatBot {
    private IGame gameInstance;
    private IGameFactory gameFactory;

    ChatBot(IGameFactory gameFactory) {
        this.gameFactory = gameFactory;
        gameInstance = gameFactory.create();
    }

    ChatBotReply answer(String message) {
        switch (message) {
            case "/start":
            case "Старт":
                if (!gameInstance.isActive()) {
                    gameInstance = gameFactory.create();
                    gameInstance.markActive();
                    return new ChatBotReply(gameInstance.getInitialMessage(), null);
                }
                return new ChatBotReply("Игра уже идёт.", null);
            case "/stop":
            case "Стоп":
                if (!gameInstance.isActive())
                    return new ChatBotReply("Игра ещё не началась.", null);
                gameInstance.markInactive();
                return new ChatBotReply("Игра закончена.", null);
            default:
                return gameInstance.proceedRequest(message);
        }
    }
}
