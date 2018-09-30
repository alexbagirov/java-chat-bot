class ChatBot {
    private IGame gameInstance;
    private IGameFactory gameFactory;

    ChatBot(IGameFactory gameFactory) {
        this.gameFactory = gameFactory;
        gameInstance = gameFactory.create();
    }

    String answer(String message) {
        switch (message) {
            case "старт":
                if (!gameInstance.isActive()) {
                    gameInstance = gameFactory.create();
                    gameInstance.markActive();
                    return gameInstance.getInitialMessage();
                }
                return "Игра уже идёт.";
            case "стоп":
                if (!gameInstance.isActive())
                    return "Игра ещё не началась.";
                gameInstance.markInactive();
                return "Игра закончена.";
            default:
                return gameInstance.proceedRequest(message);
        }
    }
}
