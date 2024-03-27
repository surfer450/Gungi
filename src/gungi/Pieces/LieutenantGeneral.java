package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class LieutenantGeneral extends Piece
{
	public LieutenantGeneral(int row, int col)
	{
		super(row, col, PieceType.LieutenantGeneral);
		addVectorsToList(new int[] {-1, -1, -1, 1, 1}, new int[] {-1, 0, 1, -1, 1});
		addVectorsToList(new int[] {-1,-1,-1,1,1,1}, new int[] {-1,0,1,-1,0,1});
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
		SetPoints(new double[]{2.5, 2.75, 3});
	}
	
	public LieutenantGeneral()
	{
		super();		
	}

}
