package gungi.Pieces;

import gungi.Models.Piece;
import gungi.Enums.PieceType;

public class Samurai extends Piece
{
	public Samurai(int row, int col)
	{
		super(row, col, PieceType.Samurai);
		addVectorsToList( new int[] {-1, -1, 1, 1}, new int[] {-1,1,-1,1});
		addVectorsToList(new int[] {-2,-2,2,2}, new int[] {-2,2,-2,2});
		addVectorsToList(new int[] {-8,-8,-7,-7,-6,-6,-5,-5,-4,-4,-3,-3,-2,-2,-1,-1,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8}, new int[] {-8,8,-7,7,-6,6,-5,5,-4,4,-3,3,-2,2,-1,1,-1,1,-2,2,-3,3,-4,4,-5,5,-6,6,-7,7,-8,8});
		SetPoints(new double[]{2, 3, 4});
	}
	
	public Samurai()
	{
		super();		
	}

}
