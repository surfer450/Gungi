package gungi;

public class LieutenantGeneral extends Piece
{
	public LieutenantGeneral(int row, int col)
	{
		super(row, col);
		name = "LieutenantGeneral";
		addVectorsToList(new int[] {-1, -1, -1, 1, 1}, new int[] {-1, 0, 1, -1, 1});
		addVectorsToList(new int[] {-1,-1,-1,1,1,1}, new int[] {-1,0,1,-1,0,1});
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
	}
	
	public LieutenantGeneral()
	{
		super();		
	}
	
	public void move()
	{
		
	}

}
