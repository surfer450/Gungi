package gungi;

import java.util.ArrayList;

//מחלקה אחראית על שמירת תכונות כלי ופעולות על כלי
public class Piece 
{
	protected String name;
	private int row;
	private int col;
	private String color;
	private ArrayList<int[]> allVectorsRow = new ArrayList<int[]>();
	private ArrayList<int[]> allVectorsCol = new ArrayList<int[]>();
	private int tier = 1;


	private double[] Points;
	
	public Piece(int row, int col)
	{
		this.row = row;
		this.col = col;	
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
		this.row = 0;
		this.col = 0;		
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
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public String getColor()
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getAllPossibleIndexes()
	{
		
		ArrayList<Integer> listAllPossibleMoves = new ArrayList<Integer>();
		int new_index;
		if (color == "White")
		{
			for (int i = 0; i< allVectorsRow.get(tier-1).length; i++)
			{
				
				new_index =(row + allVectorsRow.get(tier-1)[i])*9 + (col + allVectorsCol.get(tier-1)[i]);				
				if ((col + allVectorsCol.get(tier-1)[i] >= 0 && col + allVectorsCol.get(tier-1)[i] <=8) && (new_index <= 80 && new_index >=0))
				{
					listAllPossibleMoves.add(new_index);
				}
			}
		}
		
		else
		{
			for (int i = 0; i< allVectorsRow.get(tier-1).length; i++)
			{
				new_index =(row - allVectorsRow.get(tier-1)[i])*9 + (col + allVectorsCol.get(tier-1)[i]);
				if ((col + allVectorsCol.get(tier-1)[i] >= 0 && col + allVectorsCol.get(tier-1)[i] <=8) && (new_index <= 80 && new_index >=0))
				{
					listAllPossibleMoves.add(new_index);
				}
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
