package gungi;

public class MajorGeneral extends Piece
{
	public MajorGeneral(int row, int col)
	{
		super(row, col);
		name = "MajorGeneral";
		addVectorsToList(new int[] {-1, -1}, new int[] {-1, 1});
		addVectorsToList(new int[] {-1, -1, -1, 1, 1}, new int[] {-1, 0, 1, -1, 1});
		addVectorsToList(new int[]{-1, -1, -1, 0, 0, 1}, new int[]{-1, 0, 1, -1, 1, 0});
	}
	
	public MajorGeneral()
	{
		super();		
	}
	public void move()
	{
		
	}

}
