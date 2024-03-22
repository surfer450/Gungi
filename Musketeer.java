package gungi;

public class Musketeer extends Piece
{
	public Musketeer(int row, int col)
	{
		super(row, col);
		name = "Musketeer";
		addVectorsToList(new int[] {-1}, new int[] {0});
		addVectorsToList(new int[] {-2,-1}, new int[] {0,0});
		addVectorsToList(new int[] {-8,-7,-6,-5,-4,-3,-2,-1}, new int[] {0,0,0,0,0,0,0,0});
		SetPoints(new double[]{1, 1.5, 3});
	}
	
	public Musketeer()
	{
		super();		
	}
	public void move()
	{
		
	}

}