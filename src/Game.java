
class Game {
	private int leftBorder;
	private int rightBorder;
	int middle;
	
	Game()
	{
		leftBorder = 0;
		rightBorder =  101;
		middle = (leftBorder + rightBorder) / 2;
	}
	
	int generateNewRequest(NumberIs answer) throws BorderException
	{
		if (answer == NumberIs.SMALLER)
			rightBorder = middle;
		else
			leftBorder = middle;
		if (rightBorder - leftBorder <= 1)
			throw new BorderException("Wrong border values");	
		middle = (leftBorder + rightBorder) / 2;
		return middle;
	}
}

class BorderException extends Exception{

	public BorderException(String message){
        super(message);
    }
}
