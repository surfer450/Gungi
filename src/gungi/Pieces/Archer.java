package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class Archer extends Piece
{
	public Archer(int row, int col)
	{
		super(row, col, PieceType.Archer);
		addVectorsToList(new int[] {-1,-1,-1,0,0,1,1,1}, new int[] {-1,0,1,-1,1,-1,0,1});
		addVectorsToList(new int[] {-2,-2,-2,-2,-2,-1,-1,0,0,1,1,2,2,2,2,2}, new int[] {-2,-1,0,1,2,-2,2,-2,2,-2,2,-2,-1,0,1,2});
		addVectorsToList(new int[] {-3,-3,-3,-3,-3,-3,-3,-2,-2,-1,-1,0,0,1,1,2,2,3,3,3,3,3,3,3}, new int[] {-3,-2,-1,0,1,2,3,-3,3,-3,3,-3,3,-3,3,-3,3,-3,-2,-1,0,1,2,3});
		SetPoints(new double[]{3, 3.5, 4});
	}
	
	public Archer()
	{
		super();		
	}

}
