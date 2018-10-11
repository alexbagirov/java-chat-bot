public interface IGame {
    String getInitialMessage(int userId);

    ChatBotReply proceedRequest(String request, int userId);

    void markActive(int userId);
    void markInactive(int userId);

    boolean isActive(int userId);
}
