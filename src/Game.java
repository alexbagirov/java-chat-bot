
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
	
	int generateNewRequest(NumberIs answer)
	{
		if (answer == NumberIs.SMALLER)
			rightBorder = middle;
		else
			leftBorder = middle;
		middle = (leftBorder + rightBorder) / 2;
		return middle;
	}
}
