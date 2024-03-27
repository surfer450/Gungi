package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class Marshel extends Piece
{
	public Marshel(int row, int col)
	{
		super(row, col, PieceType.Marshel);
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
		addVectorsToList(new int[] {-1, -1, -1, 0, 0, 1, 1, 1}, new int[] {-1, 0, 1, -1, 1, -1, 0, 1});
		SetPoints(new double[]{10, 10, 10});
	}
	
	public Marshel()
	{
		super();		
	}

}