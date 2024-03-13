package gungi;

public class Spy extends Piece
{
	public Spy(int row, int col)
	{
		super(row, col);
		name = "Spy";
		addVectorsToList(new int[] {-1}, new int[] {0});
		addVectorsToList(new int[] {-1,-1,1,1}, new int[] {-1,1,-1,1});
		addVectorsToList(new int[] {-8,-8,-8,-7,-7,-7,-6,-6,-6,-5,-5,-5,-4,-4,-4,-3,-3,-3,-2,-2,-2,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,6,6,6,7,7,7,8,8,8}, new int[] {-8,0,8,-7,0,7,-6,0,6,-5,0,5,-4,0,4,-3,0,3,-2,0,2,-1,0,1,-8,-7,-6,-5,-4,-3,-2,-1,1,2,3,4,5,6,7,8,-1,0,1,-2,0,2,-3,0,3,-4,0,4,-5,0,5,-6,0,6,-7,0,7,-8,0,8});
	}
	
	public Spy()
	{
		super();		
	}
	public void move()
	{
		
	}

}
