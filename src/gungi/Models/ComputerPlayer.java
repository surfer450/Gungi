package gungi.Models;

import gungi.Enums.PieceType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ComputerPlayer extends Player
{
	private ArrayList<HashMap<Integer, PieceType>> openings = new ArrayList<HashMap<Integer, PieceType>>();
	private int openingNumber;

	private HashMap<Integer, PieceType> chosenOpening = new HashMap<Integer, PieceType>();

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

	public Map.Entry<Integer, PieceType> advanceInOpening()
	{
		if (!wasMarshelPut)
		{
			for (Map.Entry<Integer, PieceType> entry : chosenOpening.entrySet())
			{
				if (entry.getValue() == PieceType.Marshel)
				{
					chosenOpening.remove(entry.getKey());
					amountOfOpeningMoves --;
					return entry;
				}
			}
			wasMarshelPut = true;
		}

		 Map.Entry<Integer, PieceType> entry = chosenOpening.entrySet().iterator().next();
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
		HashMap<Integer, PieceType> op1 = new HashMap<>();
		op1.put(4, PieceType.Marshel);
		op1.put(2, PieceType.Pawn);
		op1.put(6, PieceType.Pawn);
		op1.put(12, PieceType.Pawn);
		op1.put(14, PieceType.Pawn);
		op1.put(22, PieceType.Pawn);
		op1.put(13, PieceType.Archer);
		openings.add(op1);

		//my opening 1
		HashMap<Integer, PieceType> op2 = new HashMap<>();
		op2.put(0, PieceType.Marshel);
		op2.put(10, PieceType.Captain);
		op2.put(1, PieceType.Pawn);
		op2.put(9, PieceType.Pawn);
		op2.put(13, PieceType.Knight);
		op2.put(12, PieceType.Archer);
		op2.put(14, PieceType.Archer);
		openings.add(op2);

		//my opening 2
		HashMap<Integer, PieceType> op3 = new HashMap<>();
		op3.put(4, PieceType.Marshel);
		op3.put(6, PieceType.Samurai);
		op3.put(5, PieceType.Pawn);
		op3.put(3, PieceType.Pawn);
		op3.put(2, PieceType.Samurai);
		op3.put(13, PieceType.Cannon);
		op3.put(23, PieceType.General);
		openings.add(op3);

	}
	
	
}
