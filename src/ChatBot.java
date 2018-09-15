import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChatBot {
	
	public final static Map<String,String> answers = new HashMap<String,String>();
	
	public void Answer(String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method method = this.getClass().getDeclaredMethod(methodName);
        method.invoke(this);
	}
}