package gungi.Pieces;


import gungi.Enums.PieceType;
import gungi.Models.Piece;

import java.util.HashMap;

public class PiecesFactory
{
	private static PiecesFactory factory;
	
	private PiecesFactory()	{		
	}
	
	public static PiecesFactory getInstance()
	{
		if (factory == null)
			factory = new PiecesFactory();
		
		return factory;
	}
	
	public Piece getPiece(PieceType name, int row, int col)
	{
		if (name == PieceType.MajorGeneral)
			return new MajorGeneral(row, col);
		
		if (name == PieceType.LieutenantGeneral)
			return new LieutenantGeneral(row, col);
		
		if (name == PieceType.General)
			return new General(row, col);
		
		if (name == PieceType.Archer)
			return new Archer(row, col);
		
		if (name == PieceType.Knight)
			return new Knight(row, col);
		
		if (name == PieceType.Musketeer)
			return new Musketeer(row, col);
		
		if (name == PieceType.Captain)
			return new Captain(row, col);
		
		if (name == PieceType.Samurai)
			return new Samurai(row, col);
		
		if (name == PieceType.Fortress)
			return new Fortress(row, col);
		
		if (name == PieceType.Cannon)
			return new Cannon(row, col);
		
		if (name == PieceType.Spy)
			return new Spy(row, col);
		
		if (name == PieceType.Pawn)
			return new Pawn(row, col);
		
		if (name == PieceType.Marshel)
			return new Marshel(row, col);

				
		return null;
	}

	public HashMap<PieceType, Integer> getPanelPiecesRecipe()
	{
		HashMap<PieceType,Integer> allPiecesToCreate = new HashMap<>();
		allPiecesToCreate.put(PieceType.MajorGeneral, 4);
		allPiecesToCreate.put(PieceType.General, 6);
		allPiecesToCreate.put(PieceType.Archer, 2);
		allPiecesToCreate.put(PieceType.Knight, 2);
		allPiecesToCreate.put(PieceType.Musketeer, 1);
		allPiecesToCreate.put(PieceType.Captain, 1);
		allPiecesToCreate.put(PieceType.Samurai, 2);
		allPiecesToCreate.put(PieceType.Fortress, 2);
		allPiecesToCreate.put(PieceType.Cannon, 2);
		allPiecesToCreate.put(PieceType.Spy, 2);
		allPiecesToCreate.put(PieceType.LieutenantGeneral, 4);
		allPiecesToCreate.put(PieceType.Pawn, 9);
		allPiecesToCreate.put(PieceType.Marshel, 1);
		return allPiecesToCreate;
	}
	
	
	

}
