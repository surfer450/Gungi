package gungi;


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
	
	
	

}
