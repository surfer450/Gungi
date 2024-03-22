package gungi;

public class Cannon extends Piece
{
	public Cannon(int row, int col)
	{
		super(row, col);
		name = "Cannon";
		addVectorsToList(new int[] {-1,0,0,1}, new int[] {0,-1,1,0});
		addVectorsToList(new int[] {-2,-1,0,0,0,0,1,2}, new int[] {0,0,-2,-1,1,2,0,0});
		addVectorsToList(new int[] {-8,-7,-6,-5,-4,-3,-2,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,2,3,4,5,6,7,8}, new int[] {0,0,0,0,0,0,0,0,-8,-7,-6,-5,-4,-3,-2,-1,1,2,3,4,5,6,7,8,0,0,0,0,0,0,0,0});
		SetPoints(new double[]{2, 3, 6});
	}
	
	public Cannon()
	{
		super();		
	}
	public void move()
	{
		
	}

}
