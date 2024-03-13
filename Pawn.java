package gungi;

public class Pawn extends Piece
{
	public Pawn(int row, int col)
	{
		super(row, col);
		name = "Pawn";
		addVectorsToList(new int[] {-1}, new int[] {0});
		addVectorsToList(new int[] {-1,-1,-1}, new int[] {-1,0,1});
		addVectorsToList(new int[] {-1,-1,-1}, new int[] {-1,0,1});
	}
	
	public Pawn()
	{
		super();		
	}
	public void move()
	{
		
	}

}