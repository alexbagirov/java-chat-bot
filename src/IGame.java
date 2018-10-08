public interface IGame {
    String getInitialMessage();

    ChatBotReply proceedRequest(String request);

    void markActive();
    void markInactive();

    boolean isActive();
}
