package gungi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player
{
	private ArrayList<HashMap<Integer, String>> openings = new ArrayList<HashMap<Integer, String>>();
	private int openingNumber;

	private HashMap<Integer, String> chosenOpening = new HashMap<Integer, String>();

	private int amountOfOpeningMoves;

	private boolean wasMarshelPut = false;

	public ComputerPlayer()
	{
		super();
		//Openings
		createAllOpeningsPatterns();

		//OpeningNumber
		Random random = new Random();
		openingNumber = random.nextInt(openings.size());

		//Chosen opening
		chosenOpening = openings.get(openingNumber);

		//opening moves amount
		amountOfOpeningMoves = chosenOpening.size();
	}

	public Map.Entry<Integer, String> advanceInOpening()
	{
		if (!wasMarshelPut)
		{
			for (Map.Entry<Integer, String> entry : chosenOpening.entrySet())
			{
				if (entry.getValue().equals("Marshel"))
				{
					chosenOpening.remove(entry.getKey());
					amountOfOpeningMoves --;
					return entry;
				}
			}
			wasMarshelPut = true;
		}

		 Map.Entry<Integer, String> entry= chosenOpening.entrySet().iterator().next();
		 chosenOpening.remove(entry.getKey());
		 amountOfOpeningMoves --;
		 return entry;
	}

	public int getAmountOfOpeningMoves()
	{
		return amountOfOpeningMoves;
	}

	public void setAmountOfOpeningMoves(int amountOfOpeningMoves)
	{
		this.amountOfOpeningMoves = amountOfOpeningMoves;
	}
	public void createAllOpeningsPatterns()
	{
		//london opening
		HashMap<Integer, String> op1 = new HashMap<>();
		op1.put(4, "Marshel");
		op1.put(2, "Pawn");
		op1.put(6, "Pawn");
		op1.put(12, "Pawn");
		op1.put(14, "Pawn");
		op1.put(22, "Pawn");
		op1.put(13, "Knight");
		openings.add(op1);

		//my opening 1
		HashMap<Integer, String> op2 = new HashMap<>();
		op2.put(0, "Marshel");
		op2.put(10, "Captain");
		op2.put(1, "Pawn");
		op2.put(9, "Pawn");
		op2.put(13, "Knight");
		op2.put(12, "Archer");
		op2.put(14, "Archer");
		openings.add(op2);

		//my opening 2
		HashMap<Integer, String> op3 = new HashMap<>();
		op3.put(4, "Marshel");
		op3.put(6, "Samurai");
		op3.put(5, "Pawn");
		op3.put(3, "Pawn");
		op3.put(2, "Samurai");
		op3.put(13, "Cannon");
		op3.put(23, "General");
		openings.add(op3);

	}
	
	
}
