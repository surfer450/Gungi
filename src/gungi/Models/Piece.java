package gungi.Models;

import gungi.Enums.PieceType;

import java.awt.*;
import java.util.ArrayList;

import static gungi.Constants.*;

//מחלקה אחראית על שמירת תכונות כלי ופעולות על כלי
public class Piece {
	private PieceType pieceType;
	private int row;
	private int col;
	private Color color;
	private ArrayList<int[]> allVectorsRow = new ArrayList<int[]>();
	private ArrayList<int[]> allVectorsCol = new ArrayList<int[]>();
	private int tier = 1;


	private double[] Points;

	public Piece(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public Piece(int row, int col, PieceType pieceType)
	{
		this.row = row;
		this.col = col;
		this.pieceType = pieceType;
	}


	public void SetPoints(double[] Points)
	{
		this.Points = Points;
	}
	public double getPoints()
	{
		return Points[tier-1];
	}
	public Piece()
	{
		this.row = FIRST_SQUARE;
		this.col = FIRST_SQUARE;
	}

	
	public void addVectorsToList(int[] vectorRow, int[] vectorCol)
	{
		allVectorsRow.add(vectorRow);
		allVectorsCol.add(vectorCol);
		
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getCol()
	{
		return col;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public int getTier()
	{
		return tier;
	}

	public void setTier(int tier)
	{
		this.tier = tier;
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public int[] getAllPossibleIndexes()
	{
		
		ArrayList<Integer> listAllPossibleMoves = new ArrayList<Integer>();
		int new_index, sign = POSITIVE;
		if (color == Color.BLACK)
		{
			sign = -POSITIVE;
		}
		for (int i = 0; i< allVectorsRow.get(tier-FIRST_PIECE_TIER).length; i++)
		{

			new_index =(row + sign*allVectorsRow.get(tier-FIRST_PIECE_TIER)[i])*SIZE_OF_BOARD + (col + allVectorsCol.get(tier-FIRST_PIECE_TIER)[i]);
			if ((col + allVectorsCol.get(tier-FIRST_PIECE_TIER)[i] >= FIRST_COL && col + allVectorsCol.get(tier-FIRST_PIECE_TIER)[i] <=END_OF_COL) && (new_index <= AMOUNT_OF_SQUARES && new_index >=FIRST_INDEX))
			{
				listAllPossibleMoves.add(new_index);
			}
		}

		
		int[] allIndexes = new int[listAllPossibleMoves.size()];
		for (int i = 0; i<listAllPossibleMoves.size();i++)
		{
			allIndexes[i] = listAllPossibleMoves.get(i);
		}
		
		
		return allIndexes;
		
	}
	
	public void move(int row, int col)
	{
		this.row = row;
		this.col = col;	
	}
	
}
