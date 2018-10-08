import java.util.List;

class ChatBotReply {
    String message;
    List<String> keyboardOptions;

    ChatBotReply(String message, List<String> options) {
        this.message = message;
        keyboardOptions = options;
    }
}
