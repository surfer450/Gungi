package gungi;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public abstract class Player 
{
	private String username;
	private HashMap<Integer,Piece> playerPieces = new HashMap<Integer,Piece>();

	private HashMap<String,Integer> unusedPieces = new HashMap<String,Integer>();
	private String color;
	private int numOfActivePieces;

	private int numOfUnusedPieces = 38;
	
	public Player()
	{
		unusedPieces.put("MajorGeneral", 4);
		unusedPieces.put("General", 6);
		unusedPieces.put("Archer", 2);
		unusedPieces.put("Knight", 2);
		unusedPieces.put("Musketeer", 1);
		unusedPieces.put("Captain", 1);
		unusedPieces.put("Samurai", 2);
		unusedPieces.put("Fortress", 2);
		unusedPieces.put("Cannon", 2);
		unusedPieces.put("Spy", 2);
		unusedPieces.put("LieutenantGeneral", 4);
		unusedPieces.put("Pawn", 9);
		unusedPieces.put("Marshel", 1);
	}
	public HashMap<Integer,Piece> getPlayerPieces()
	{
		return playerPieces;
	}

	public  HashMap<String,Integer> getPlayerUnusedPieces()
	{
		return unusedPieces;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public int getNumOfActivePieces()
	{
		return numOfActivePieces;
	}

	public int getNumOfUnusedPieces() {return numOfUnusedPieces;}
	public void setNumOfActivePieces(int numOfActivePieces)
	{
		this.numOfActivePieces = numOfActivePieces;
	}

	public void setNumOfUnusedPiece(int numOfUnusedPieces) { this.numOfUnusedPieces = numOfUnusedPieces; }
	public void createPlayerPiece(String typeKey, int row, int col)
	{	
		numOfActivePieces++;
		numOfUnusedPieces --;
		HashMap<String,PieceType> allPiecesToCreate = new HashMap<String,PieceType>();
		allPiecesToCreate.put("MajorGeneral", PieceType.MajorGeneral);
		allPiecesToCreate.put("LieutenantGeneral", PieceType.LieutenantGeneral);
		allPiecesToCreate.put("General", PieceType.General);
		allPiecesToCreate.put("Archer", PieceType.Archer);
		allPiecesToCreate.put("Knight", PieceType.Knight);
		allPiecesToCreate.put("Musketeer", PieceType.Musketeer);
		allPiecesToCreate.put("Captain", PieceType.Captain);
		allPiecesToCreate.put("Samurai", PieceType.Samurai);
		allPiecesToCreate.put("Fortress", PieceType.Fortress);
		allPiecesToCreate.put("Cannon", PieceType.Cannon);
		allPiecesToCreate.put("Spy", PieceType.Spy);
		allPiecesToCreate.put("Pawn", PieceType.Pawn);
		allPiecesToCreate.put("Marshel", PieceType.Marshel);
		
		PiecesFactory factory = PiecesFactory.getInstance();	
		Piece p = factory.getPiece(allPiecesToCreate.get(typeKey), row, col);
		p.setColor(color);
		playerPieces.put(numOfActivePieces, p);
		unusedPieces.put(typeKey, unusedPieces.get(typeKey)-1);


	}
	public void printM()
	{
		System.out.println(playerPieces.get(1).getRow() * 9 + playerPieces.get(1).getCol());
	}
	public boolean isMarshelExist()
	{
		if (playerPieces.get(1) == null)
			return false;
		return true;		
	}
	
	public boolean isPlayerPiecesMax()
	{
		if (playerPieces.get(18) == null)
			return false;
		return true;		
	}
	
	public int findPieceKeyByIndex(int index)
	{
		int key = -1;
		Piece topPiece = new Piece();
		for (Map.Entry<Integer, Piece> set : playerPieces.entrySet()) 
		{
			Piece piece = set.getValue();
			 
			if (piece.getRow() *9 + piece.getCol() == index)
			{
				if (piece.getTier() >= topPiece.getTier())
				{
					key = set.getKey();
					topPiece = piece;
					
				}			  	
			}
		}
		return key;
		
	}
	public Piece findPieceByIndex(int index)
	{
		int key = findPieceKeyByIndex(index);
		return (playerPieces.get(key));	
	}
	
	public Piece getMarshel()
	{
		return playerPieces.get(1);
	}

}
