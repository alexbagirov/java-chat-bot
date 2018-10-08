import java.io.FileNotFoundException;

public class GameFactory implements IGameFactory {
    @Override
    public IGame create() {
        try {
            return new QuizWinx("winx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
