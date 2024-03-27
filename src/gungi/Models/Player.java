package gungi.Models;

import gungi.Enums.PieceType;
import gungi.Pieces.PiecesFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static gungi.Constants.*;

public abstract class Player 
{
	private String username;
	private HashMap<Integer, Piece> playerPieces = new HashMap<Integer,Piece>();

	private HashMap<PieceType,Integer> unusedPieces;
	private Color color;
	private int numOfActivePieces;

	private int numOfUnusedPieces = NUM_OF_TOTAL_PIECES;
	
	public Player()
	{
		PiecesFactory factory = PiecesFactory.getInstance();
		unusedPieces = factory.getPanelPiecesRecipe();
	}
	public HashMap<Integer,Piece> getPlayerPieces()
	{
		return playerPieces;
	}

	public  HashMap<PieceType,Integer> getPlayerUnusedPieces()
	{
		return unusedPieces;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
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
	public void createPlayerPiece(PieceType typeKey, int row, int col)
	{
		numOfActivePieces++;
		numOfUnusedPieces --;

		PiecesFactory factory = PiecesFactory.getInstance();	
		Piece p = factory.getPiece(typeKey, row, col);
		p.setColor(color);
		playerPieces.put(numOfActivePieces, p);
		unusedPieces.put(typeKey, unusedPieces.get(typeKey)-1);
	}

	public boolean isMarshelExist()
	{
		if (playerPieces.get(FIRST_PIECE) == null)
			return false;
		return true;		
	}
	
	public boolean isPlayerPiecesMax()
	{
		if (playerPieces.get(MAX_PIECES_FOR_PLAYER) == null)
			return false;
		return true;		
	}
	
	public int findPieceKeyByIndex(int index)
	{
		int key = NOT_FOUND;
		Piece topPiece = new Piece();
		for (Map.Entry<Integer, Piece> set : playerPieces.entrySet()) 
		{

			Piece piece = set.getValue();
			 
			if (piece.getRow() *SIZE_OF_BOARD + piece.getCol() == index)
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
		return playerPieces.get(FIRST_PIECE);
	}

}
