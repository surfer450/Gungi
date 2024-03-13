package gungi;

public class General extends Piece
{
	public General(int row, int col)
	{
		super(row, col);
		name = "General";
		addVectorsToList(new int[]{-1, -1, -1, 0, 0, 1}, new int[]{-1, 0, 1, -1, 1, 0});
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
		addVectorsToList(new int[] {-2,-2,-2,-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1,0,1,-1, 0, 1, -1, 1, -1, 0, 1});
		
	}
	
	public General()
	{
		super();		
	}
	public void move()
	{
		
	}

}
