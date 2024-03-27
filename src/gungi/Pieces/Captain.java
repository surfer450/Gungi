package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class Captain extends Piece
{
	public Captain(int row, int col)
	{
		super(row, col, PieceType.Captain);
		addVectorsToList(new int[] {-1,-1,-1,0,0,1,1,1}, new int[] {-1,0,1,-1,1,-1,0,1});
		SetPoints(new double[]{3, -1, -1});
	}
	
	public Captain()
	{
		super();		
	}

}
