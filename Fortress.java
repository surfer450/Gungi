package gungi;

public class Fortress extends Piece
{
	public Fortress(int row, int col)
	{
		super(row, col);
		name = "Fortress";
		addVectorsToList(new int[] {-1,-1,-1,0,0,1,1,1}, new int[] {-1,0,1,-1,1,-1,0,1});
	}
	
	public Fortress()
	{
		super();		
	}
	
	public void move()
	{
		
	}

}