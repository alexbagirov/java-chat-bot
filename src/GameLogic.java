
public class GameLogic {
	private int leftBoard;
	private int rightBoard;
	public int midle;
	
	public GameLogic() 
	{
		leftBoard = 0;
		rightBoard =  100;
		midle = (leftBoard + rightBoard) / 2;
	}
	
	public int generateNewRequest(NumberIs answer) 
	{
		if (answer == NumberIs.SMALLER)
			rightBoard = midle;
		else
			leftBoard = midle;
		midle = (leftBoard + rightBoard) / 2;
		return midle;
	}


}
