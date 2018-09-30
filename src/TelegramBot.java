import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private static ChatBot chatBot = new ChatBot(new GameFactory());
    private static String BOT_USERNAME = System.getenv("BOT_USERNAME");
    private static String BOT_TOKEN = System.getenv("BOT_TOKEN");

    protected TelegramBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(chatBot.answer(update.getMessage().getText())));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
