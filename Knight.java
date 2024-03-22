package gungi;

public class Knight extends Piece
{
	public Knight(int row, int col)
	{
		super(row, col);
		name = "Knight";
		addVectorsToList(new int[] {-2, -2, 0, 0}, new int[] {-1, 1, -1, 1});
		addVectorsToList(new int[] {-2,-2,-1,-1}, new int[] {-1,1,-2,2});
		addVectorsToList(new int[] {-2,-2,-1,-1, 1,1,2,2}, new int[] {-1,1,-2,2,-2,2,-1,1});
		SetPoints(new double[]{2, 2.5, 4});
	}
	
	public Knight()
	{
		super();		
	}
	public void move()
	{
		
	}

}