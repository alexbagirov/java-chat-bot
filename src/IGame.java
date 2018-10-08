public interface IGame {
    String getInitialMessage();

    String proceedRequest(String request);

    void markActive();
    void markInactive();

    boolean isActive();
}
