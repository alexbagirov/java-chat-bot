import java.io.FileNotFoundException;

public class GameFactory implements IGameFactory {
    @Override
    public IGame create() {
        try {
            return new WinxQuiz("winx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
