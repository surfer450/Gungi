package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class Pawn extends Piece
{
	public Pawn(int row, int col)
	{
		super(row, col, PieceType.Pawn);
		addVectorsToList(new int[] {-1}, new int[] {0});
		addVectorsToList(new int[] {-1,-1,-1}, new int[] {-1,0,1});
		addVectorsToList(new int[] {-1,-1,-1}, new int[] {-1,0,1});
		SetPoints(new double[]{1, 1.5, 1.5});
	}
	
	public Pawn()
	{
		super();		
	}

}