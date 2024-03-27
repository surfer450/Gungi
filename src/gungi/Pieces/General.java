package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class General extends Piece
{
	public General(int row, int col)
	{
		super(row, col, PieceType.General);
		addVectorsToList(new int[]{-1, -1, -1, 0, 0, 1}, new int[]{-1, 0, 1, -1, 1, 0});
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
		addVectorsToList(new int[] {-2,-2,-2,-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1,0,1,-1, 0, 1, -1, 1, -1, 0, 1});
		SetPoints(new double[]{2.5, 3, 3.5});
		
	}
	
	public General()
	{
		super();		
	}

}
