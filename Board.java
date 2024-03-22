package gungi;

import java.util.ArrayList;
//מחלקה אחראית על כל מה שקשור ללוח המשחק: תורות, שחקנים תזוזה וכדומה
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Board {
	private Player Player1;
	private Player Player2;
	private Move moveObject = new Move();
	private Game gameObject;

	public Board(Player Player1, Player Player2)
	{
		this.Player1 = Player1;
		this.Player2 = Player2;
	}
	public Board() {
		this.Player1 = null;
		this.Player2 = null;
	}

	public void setGame(Game gameObject)
	{

		this.gameObject = gameObject;
		String[] colors = { "White", "Black" };
		int index = new Random().nextInt(2);
		Player1.setColor(colors[index]);
		Player2.setColor(colors[1 - index]);

		// start move
		if (Player1.getColor() == "White")
			moveObject.move(Player1, "put");
		else
			moveObject.move(Player2, "put");

		if (this.gameObject.getPlayerMode().equals("Computer"))
		{
			if (findCurrentPlayer().getClass() == ComputerPlayer.class)
			{
				playComputerTurn();
			}
		}
	}

	public void playComputerTurn()
	{
		ComputerPlayer computerPlayer = (ComputerPlayer) findCurrentPlayer();
		if (computerPlayer.getAmountOfOpeningMoves() > 0)
		{
			System.out.println("amount of opening moves left: ");
			System.out.println(computerPlayer.getAmountOfOpeningMoves());
			System.out.println("computer put!");
			Map.Entry<Integer, String> currentAdvanced= computerPlayer.advanceInOpening();
			MoveObject moveObject = new MoveObject(-1, currentAdvanced.getKey(), 1, currentAdvanced.getValue());
			gameObject.putComputerPieceOnBoard(moveObject, computerPlayer.getColor());
		}

		else if (computerPlayer.getAmountOfOpeningMoves() == 0)
		{
			System.out.println("computer ready!");
			computerPlayer.setAmountOfOpeningMoves(-1);
			gameObject.putReadyForComputer(computerPlayer.getColor());
		}

		else
		{
			MoveObject bestMove = findBestMove();
			switch (bestMove.getMoveType())
			{
				//put
				case 1:
					gameObject.putComputerPieceOnBoard(bestMove, computerPlayer.getColor());
					break;

				//move
				case 2:
					gameObject.moveComputerPieceOnBoard(bestMove, computerPlayer.getColor());
					break;

				//attack
				case 3:
					gameObject.attackComputerPieceOnBoard(bestMove, computerPlayer.getColor());
					break;

				//stack
				case 4:
					gameObject.stackComputerPieceOnBoard(bestMove, computerPlayer.getColor());
					break;

			}
			System.out.println("computer moved!");
		}

		System.out.println("your turn");
	}

	public double getPieceValue(Piece piece)
	{
		double pieceValue = piece.getPoints();
		double[][] regularPiecePositionPoints =
				{
						{-0.5,-0.4,-0.4,-0.4,-0.4,-0.4,-0.4,-0.4,-0.5},
						{-0.4,-0.2, 0.0, 0.0, 0.0, 0.0, 0.0,-0.2,-0.4},
						{-0.4, 0.0, 0.1, 0.2, 0.2, 0.2, 0.1, 0.0, -0.4},
						{-0.4, 0.0, 0.2, 0.25, 0.25, 0.25, 0.2, 0.0, -0.4},
						{-0.4, 0.0, 0.2, 0.25, 0.5, 0.25, 0.2, 0.0, -0.4},
						{-0.4, 0.0, 0.2, 0.25, 0.25, 0.25, 0.2, 0.0, -0.4},
						{-0.4, 0.0, 0.1, 0.2, 0.2, 0.2, 0.1, 0.0, -0.4},
						{-0.4,-0.2, 0.0, 0.0, 0.0, 0.0, 0.0,-0.2,-0.4},
						{-0.5,-0.4,-0.4,-0.4,-0.4,-0.4,-0.4,-0.4,-0.5}
				};

		double[][] kingPositionPoints =
				{
						{-0.3, -0.4, -0.4, -0.5, -0.5, -0.5, -0.4, -0.4, -0.3},
						{-0.3, -0.4, -0.4, -0.5, -0.5, -0.5, -0.4, -0.4, -0.3},
						{-0.3, -0.4, -0.4, -0.5, -0.5, -0.5, -0.4, -0.4, -0.3},
						{-0.3, -0.4, -0.4, -0.5, -0.5, -0.5, -0.4, -0.4, -0.3},
						{-0.3, -0.4, -0.4, -0.5, -0.5, -0.5, -0.4, -0.4, -0.3},
						{-0.3, -0.4, -0.4, -0.5, -0.5, -0.5, -0.4, -0.4, -0.3},
						{-0.2, -0.2, -0.2, -0.2, -0.2, -0.2, -0.2, -0.2, -0.2},
						{0.2, 0.2, 0, 0 , 0 , 0, 0, 0.2, 0.2},
						{0.2, 0.3, 0.1, 0, 0, 0, 0.1, 0.3, 0.2}
				};
		if (piece.getClass() == Marshel.class)
		{
			int valueIndex = piece.getRow()*9 + piece.getCol();
			if (findCurrentPlayer().getColor().equals("Black"))
			{
				valueIndex = 80 - valueIndex;
			}
			pieceValue += kingPositionPoints[valueIndex / 9][valueIndex % 9];
		}
		else
		{
			pieceValue += regularPiecePositionPoints[piece.getRow()][piece.getCol()];
		}
		return pieceValue;
	}

	public ArrayList<Piece> findThreateningPiecesToSqaure(int index)
	{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		HashMap<Integer, Piece> piecesPlayer1 = Player1.getPlayerPieces();
		HashMap<Integer, Piece> piecesPlayer2 = Player2.getPlayerPieces();
		Piece piece;
		int tempIndex;

		for (Map.Entry<Integer, Piece> entry: piecesPlayer1.entrySet())
		{
			piece = entry.getValue();
			tempIndex = piece.getRow() * 9 + piece.getCol();
			if (findPieceOnBoardByIndex(tempIndex).equals(piece)) {
				for (int move : findAllPossibleMoves(tempIndex))
				{
					if (move == index)
					{
						pieces.add(piece);
					}
				}
			}
		}

		for (Map.Entry<Integer, Piece> entry: piecesPlayer2.entrySet())
		{
			piece = entry.getValue();
			tempIndex = piece.getRow() * 9 + piece.getCol();
			if (findPieceOnBoardByIndex(tempIndex).equals(piece)) {
				for (int move : findAllPossibleMoves(tempIndex))
				{
					if (move == index)
					{
						pieces.add(piece);
					}
				}
			}
		}
		return pieces;
	}

	public boolean findIfSquareThreatenedByEnemy(int index)
	{
		ArrayList<Piece> ThreateningPieces = findThreateningPiecesToSqaure(index);
		Piece currPiece = findPieceOnBoardByIndex(index);
		for (Piece piece: ThreateningPieces)
		{
			if (!piece.getColor().equals(currPiece.getColor()))
			{
				return true;
			}
		}
		return false;
	}
	public double evaluateSquare(int index, Player player)
	{

		Piece currPiece = findPieceOnBoardByIndex(index);
		boolean noMoreThreat = false, searchState = true;
		ArrayList<Piece> ThreateningPieces = findThreateningPiecesToSqaure(index);
		int tempIndex = 0;
		Piece minPiece = null, tempPiece;


		//defualt value for square is the piece in it.
		//then i am lokking for pieces threatening this square
		//if there are the same amount from both players i will remove the values
		//if not i will add or dub 1 point accordingly to support defance and
		//control of squares
		double squareValue = 0;

		while (!ThreateningPieces.isEmpty() && !noMoreThreat)
		{
			tempPiece = ThreateningPieces.get(tempIndex);
			//if true, I am lokoking for Opponent piece threatening this square with lowest value, it will attack me next;

			if (searchState)
			{
				if (player.getClass() == ComputerPlayer.class || !findIfSquareThreatenedByEnemy(tempPiece.getRow()*9+tempPiece.getCol()))
				{
					if (!ThreateningPieces.get(tempIndex).getColor().equals(currPiece.getColor())) {
						if (minPiece == null) {
							minPiece = ThreateningPieces.get(tempIndex);
						} else if (getPieceValue(minPiece) > getPieceValue(ThreateningPieces.get(tempIndex))) {
							minPiece = ThreateningPieces.get(tempIndex);
						}
					}
				}
			}
			//if false, i am looking for my piece threatening this square with lowest value, it will attack next;
			else
			{
				if (ThreateningPieces.get(tempIndex).getColor().equals(currPiece.getColor()))
				{
					if (minPiece == null)
					{
						minPiece = ThreateningPieces.get(tempIndex);
					}
					else if (getPieceValue(minPiece) > getPieceValue(ThreateningPieces.get(tempIndex)))
					{
						minPiece = ThreateningPieces.get(tempIndex);
					}
				}

			}

			tempIndex ++;
			if (tempIndex == ThreateningPieces.size())
			{
				if (minPiece == null)
				{
					noMoreThreat = true;
				}
				else
				{
					//opposite player moved and eat my piece, lower points
					if (searchState)
					{
						squareValue -= getPieceValue(currPiece);
						searchState = false;
					}
					//I moved and eat opposite player piece, add points
					else
					{
						squareValue += getPieceValue(currPiece);
						searchState = true;
					}
					tempIndex = 0;
					currPiece = minPiece;
					minPiece = null;
					ThreateningPieces.remove(currPiece);
				}
			}
		}
		if (player.getClass() == ComputerPlayer.class)
		{
			squareValue *= 10;
		}
		else
		{
			squareValue *= 5;
		}
		//this part add points for having over protected or attacked square
		currPiece = findPieceOnBoardByIndex(index);
		if (!ThreateningPieces.isEmpty())
		{
			//didnt find opposite pieces
			if (searchState)
			{
				squareValue += ThreateningPieces.size() / 10.0;
			}

			else
			{
				squareValue -= ThreateningPieces.size() / 10.0;
			}
		}
		return squareValue;
	}

	public double evaluateOneSide(Player player)
	{
		double points = 0;
		Piece piece;
		int index;


		HashMap<Integer, Piece> allPlayerPieces = player.getPlayerPieces();
		for (Map.Entry<Integer, Piece> entry : allPlayerPieces.entrySet())
		{
			piece = entry.getValue();
			index = piece.getRow() * 9 + piece.getCol();
			if (findPieceOnBoardByIndex(index) == piece)
			{
				points += getPieceValue(piece);
			}
		}
		for (Map.Entry<Integer, Piece> entry : allPlayerPieces.entrySet())
		{
			piece = entry.getValue();
			index = piece.getRow() * 9 + piece.getCol();
			if (findPieceOnBoardByIndex(index) == piece && findIfSquareThreatenedByEnemy(index))
			{
				points += evaluateSquare(index, player);
			}
		}
		return points;
	}
	public double evaluateMove()
	{
		double currentPlayerPoints, otherPlayerPoints;
		currentPlayerPoints = evaluateOneSide(findCurrentPlayer());
		otherPlayerPoints = evaluateOneSide(moveObject.getPlayer());
		return currentPlayerPoints - otherPlayerPoints;

	}

	public MoveObject findBestMove()
	{
		Player player = findCurrentPlayer();
		double maxValue =  Integer.MIN_VALUE, tempValue;
		int oldIndex, newIndex;
		HashMap<Integer,Piece> allPieces = player.getPlayerPieces();
		int[] allPossibileMovesArr;
		MoveObject bestMove = new MoveObject();
		Piece piece, tempPiece;
		for (Map.Entry<Integer, Piece> set : allPieces.entrySet())
		{

			piece = set.getValue();
			allPossibileMovesArr = findAllPossibleMoves(piece.getRow() * 9 + piece.getCol());
			for (int k = 0; k < allPossibileMovesArr.length; k++)
			{
				oldIndex = piece.getRow() * 9 + piece.getCol();
				newIndex = allPossibileMovesArr[k];
				tempPiece = findPieceOnBoardByIndex(allPossibileMovesArr[k]);
				if (tempPiece == null)
				{
					if (!willMarshelBeInDangerAfterMoving(oldIndex, newIndex)) {
						demoFwMove(oldIndex, newIndex);
						tempValue = evaluateMove();
						demoBwMove(oldIndex, newIndex);
						if (tempValue > maxValue) {
							maxValue = tempValue;
							bestMove.changeMove(oldIndex, newIndex, 2, piece.getName());
						}
					}
				}
				else
				{
					if (!findPieceOnBoardByIndex(newIndex).getColor().equals(player.getColor()) && !willMarshelBeInDangerAfterAttacking(oldIndex, newIndex)) {
						Piece attackedPiece = demoFwAttack(oldIndex, newIndex);
						tempValue = evaluateMove();
						demoBwAttack(oldIndex, newIndex, attackedPiece);
						if (tempValue > maxValue) {
							maxValue = tempValue;
							bestMove.changeMove(oldIndex, newIndex, 3, piece.getName());
						}
					}

					if (findPieceTierByIndex(newIndex) < 3 && !willMarshelBeInDangerAfterStacking(oldIndex, newIndex) && !isPieceFortress(oldIndex))
					{
						demoFwStack(oldIndex, newIndex);
						tempValue = evaluateMove();
						demoBwStack(oldIndex, newIndex);
						if (tempValue > maxValue) {
							maxValue = tempValue;
							bestMove.changeMove(oldIndex, newIndex, 4, piece.getName());
						}
					}
				}
			}
		}

		String pieceName;
		int row, col;
		if (player.getNumOfUnusedPieces() > 0 && isPuttingPieceLegel())
		{
			HashMap<String, Integer> unusedPieces = player.getPlayerUnusedPieces();
			for (Map.Entry<String, Integer> entry : unusedPieces.entrySet())
			{
				if (entry.getValue() > 0)
				{
					pieceName = entry.getKey();
					for (int i = 0; i <= 80; i++)
					{
						if (isYourSquare(i) && findPieceOnBoardByIndex(i) == null)
						{
							if (!pieceName.equals("Pawn") || (pieceName.equals("Pawn") && !isTherePawnInCol(i % 9)))
							{
								row = i / 9;
								col = i % 9;
								if (!willMarshelBeInDangerAfterPuttin(pieceName, row, col) && !willOpponentMarshelBeInCheckAfterPuttin(pieceName, row, col))
								{
									demoFwPut(pieceName, row, col);
									tempValue = evaluateMove();
									demoBwPut(pieceName);
									if (tempValue > maxValue) {
										maxValue = tempValue;
										bestMove.changeMove(-1, i, 1, pieceName);
									}
								}
							}
						}
					}
				}
			}
		}
		return bestMove;
	}

	public boolean isValidPiece(String color) {
		// return true if a piece belongs to the current player
		Player currentPlayer = findCurrentPlayer();
		return (currentPlayer.getColor() == color);
	}

	public Player findCurrentPlayer() {

		if (Player1 != moveObject.getPlayer())
			return Player1;
		return Player2;
	}

	public Player findPlayerByColor(String color) {
		if (Player1.getColor() == color)
			return Player1;
		else
			return Player2;
	}

	public Piece findPieceOnBoardByIndex(int index) {

		Piece piece1 = Player1.findPieceByIndex(index);
		Piece piece2 = Player2.findPieceByIndex(index);

		if (piece1 == null)
		{
			return piece2;
		}
		else
		{
			if (piece2 == null)
				return piece1;
			else {

				if (piece1 != null && piece2 != null) {
					if (piece1.getTier() > piece2.getTier())
						return piece1;
					else
						return piece2;
				}
			}
		}
		return null;
	}

	public String findPieceNameByIndex(int index) {
		Piece indexPiece = findPieceOnBoardByIndex(index);

		if (indexPiece != null) {
			HashMap<Piece, String> allPiecesToCreate = new HashMap<Piece, String>();
			allPiecesToCreate.put(new MajorGeneral(), "MajorGeneral");
			allPiecesToCreate.put(new General(), "General");
			allPiecesToCreate.put(new Archer(), "Archer");
			allPiecesToCreate.put(new Knight(), "Knight");
			allPiecesToCreate.put(new Musketeer(), "Musketeer");
			allPiecesToCreate.put(new Captain(), "Captain");
			allPiecesToCreate.put(new Samurai(), "Samurai");
			allPiecesToCreate.put(new Fortress(), "Fortress");
			allPiecesToCreate.put(new Cannon(), "Cannon");
			allPiecesToCreate.put(new Spy(), "Spy");
			allPiecesToCreate.put(new LieutenantGeneral(), "LieutenantGeneral");
			allPiecesToCreate.put(new Pawn(), "Pawn");
			allPiecesToCreate.put(new Marshel(), "Marshel");

			for (Map.Entry<Piece, String> set : allPiecesToCreate.entrySet()) {
				if (set.getKey().getClass() == indexPiece.getClass()) {
					return set.getValue();
				}
			}
		}

		return null;

	}

	public Player getPlayer1() {
		return Player1;
	}

	public Player getPlayer2() {
		return Player2;
	}

	public String findPieceColorByIndex(int index) {

		Piece indexPiece = findPieceOnBoardByIndex(index);

		if (indexPiece != null) {

			return indexPiece.getColor();
		}
		return null;
	}

	public int findPieceTierByIndex(int index) {
		Piece indexPiece = findPieceOnBoardByIndex(index);
		if (indexPiece != null) {
			return indexPiece.getTier();
		}
		return 0;
	}

	public void setPieceTier(int index, int tier) {
		Piece piece = findPieceOnBoardByIndex(index);
		piece.setTier(tier);
	}

	public int removePieceByIndex(int index_beg, int index_end) {

		Player otherPlayer = moveObject.getPlayer();
		Piece piece_attacking = findPieceOnBoardByIndex(index_beg);
		Piece piece_attacked = findPieceOnBoardByIndex(index_end);

		if (piece_attacked != null && piece_attacking != null) {
			int key = otherPlayer.findPieceKeyByIndex(index_end);
			otherPlayer.getPlayerPieces().remove(key);
			otherPlayer.setNumOfActivePieces(otherPlayer.getNumOfActivePieces() - 1);
			return piece_attacked.getTier();
		}

		return -1;

	}

	public int stackPieceByIndex(int index_beg, int index_end) {

		Piece piece_stacking = findPieceOnBoardByIndex(index_beg);
		Piece piece_stacked = findPieceOnBoardByIndex(index_end);

		if (piece_stacking != null) {
			return (piece_stacked.getTier() + 1);
		}

		return -1;
	}

	public boolean isYourSquare(int count) {

		Player currentPlayer = findCurrentPlayer();
		if (currentPlayer.getColor() == "White") {
			if (gameObject.getGameState() == "start phase") {
				if (count >= 54 && count <= 80)
					return true;
				return false;
			} else {
				if (count >= 27 && count <= 80)
					return true;
				return false;
			}
		}

		else {
			if (gameObject.getGameState() == "start phase") {
				if (count >= 0 && count <= 26)
					return true;
				return false;
			} else {
				if (count >= 0 && count <= 53)
					return true;
				return false;
			}
		}
	}

	public void addPlayerToReadyList() {
		Player currentPlayer = findCurrentPlayer();
		gameObject.addToReadyList(currentPlayer);
		moveObject.move(currentPlayer, "put");
		if (gameObject.getAmountOfReadyPlayers() == 1 && findCurrentPlayer().getClass() == ComputerPlayer.class)
		{
			playComputerTurn();
		}
		gameObject.changePhase(moveObject);
	}

	public boolean isMarshelOnBoard()
	{
		Player currentPlayer = findCurrentPlayer();
		return currentPlayer.isMarshelExist();
	}

	public boolean isPuttingPieceLegel() {
		// return true if not more then 18 pieces for current player
		Player currentPlayer = findCurrentPlayer();
		return !(currentPlayer.isPlayerPiecesMax());
	}

	public void putPiece(String chosenPiece, int row, int col)
	{
		Player currentPlayer = findCurrentPlayer();
		currentPlayer.createPlayerPiece(chosenPiece, row, col);
		if (gameObject.isPlayerOnReady(moveObject.getPlayer()) && gameObject.getGameState() == "start phase")
			moveObject.move(moveObject.getPlayer(), "put");
		else
			moveObject.move(currentPlayer, "put");

		gameObject.changePhase(moveObject);

	}

	public boolean findIfMovePossible(int begin_index, int end_index) {
		Player currentPlayer = findCurrentPlayer();
		int[] allIndexs = findAllPossibleMoves(begin_index);
		for (int curIndex : allIndexs) {
			if (curIndex == end_index) {
				return true;
			}
		}
		return false;
	}

	public int move(int index_beg, int index_end, int tier) {

		Player currentPlayer = findCurrentPlayer();
		Piece piece = findPieceOnBoardByIndex(index_beg);
		int row = index_end / 9;
		int col = index_end % 9;
		piece.move(row, col);
		piece.setTier(tier);

		moveObject.move(currentPlayer, "move");
		return row * 9 + col;
	}

	public Piece findPieceBeneathCaptain(int index) {
		Piece captinPiece = findPieceOnBoardByIndex(index);
		captinPiece.move(captinPiece.getRow(), captinPiece.getCol() + 1);
		Piece indexPiece = findPieceOnBoardByIndex(index);
		captinPiece.move(captinPiece.getRow(), captinPiece.getCol() - 1);
		return indexPiece;
	}

	public int checkNumberSign(int number) {
		if (number > 0) {
			return 1;
		} else {
			if (number < 0) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	public int[] updateAllPossibleMoves(int[] oldArr, int index) {
		ArrayList<Integer> allPossibleMoves = new ArrayList<Integer>();
		for (int i = 0; i < oldArr.length; i++) {
			allPossibleMoves.add(oldArr[i]);
		}

		int originalRow, originalCol, moveRow, moveCol, removeRow, removeCol;
		originalRow = index / 9;
		originalCol = index % 9;
		for (int i = 0; i < allPossibleMoves.size(); i++) {

			if (findPieceOnBoardByIndex(allPossibleMoves.get(i)) != null) {
				moveRow = allPossibleMoves.get(i) / 9;
				moveCol = allPossibleMoves.get(i) % 9;

				for (int j = 0; j < allPossibleMoves.size(); j++) {
					removeRow = allPossibleMoves.get(j) / 9;
					removeCol = allPossibleMoves.get(j) % 9;

					if (checkNumberSign(moveCol - originalCol) == checkNumberSign(removeCol - moveCol)) {
						if (checkNumberSign(moveRow - originalRow) == checkNumberSign(removeRow - moveRow)) {
							allPossibleMoves.remove(allPossibleMoves.get(j));
						}

					}
				}

			}
		}

		int[] newArr = new int[allPossibleMoves.size()];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = allPossibleMoves.get(i);
		}

		return newArr;

	}

	public int[] findAllPossibleMoves(int index) {
		Piece indexPiece = findPieceOnBoardByIndex(index);

		if (indexPiece != null) {
			String old_color = indexPiece.getColor();
			if (indexPiece.getClass() == Captain.class && indexPiece.getTier() > 1) {
				Piece captinPiece = indexPiece;
				captinPiece.move(captinPiece.getRow(), captinPiece.getCol() + 1);
				indexPiece = findPieceOnBoardByIndex(index);
				captinPiece.move(captinPiece.getRow(), captinPiece.getCol() - 1);
				indexPiece.setColor(captinPiece.getColor());
			}

			int[] allIndexes = indexPiece.getAllPossibleIndexes();
			if (indexPiece.getClass() == Captain.class && indexPiece.getTier() > 1) {
				indexPiece.setColor(old_color);
			}
			int[] allTrueIndexes = updateAllPossibleMoves(allIndexes, index);
			return allTrueIndexes;
		}

		return null;
	}

	public boolean findIfMarshelInCheck()
	{
		Player currentPlayer = findCurrentPlayer();
		HashMap<Integer,Piece> allPieces = currentPlayer.getPlayerPieces();
		int[] allPossibileMovesArr;
		Piece piece, tempPiece;
		for (Map.Entry<Integer, Piece> set : allPieces.entrySet())
		{

			piece = set.getValue();
			allPossibileMovesArr = findAllPossibleMoves(piece.getRow() * 9 + piece.getCol());
			for (int k = 0; k < allPossibileMovesArr.length; k++) {
				tempPiece = findPieceOnBoardByIndex(allPossibileMovesArr[k]);
				if (tempPiece == null) {
					if (!willMarshelBeInDangerAfterMoving(piece.getRow() * 9 + piece.getCol(), allPossibileMovesArr[k])) {
						return false;
					}
				}
				else
				{
					if (!willMarshelBeInDangerAfterAttacking(piece.getRow() * 9 + piece.getCol(), allPossibileMovesArr[k])){
						return false;
					}
				}
			}
		}

		if (currentPlayer.getNumOfUnusedPieces() > 0) {

			for (int i = 0; i <= 80; i++) {
				if (isYourSquare(i) && findPieceOnBoardByIndex(i) == null)
				{
					if (!willMarshelBeInDangerAfterPuttin("Pawn", i / 9, i % 9))
						return false;
				}
			}
		}


		return true;
	}

	public void setPieceTier1(int index) {
		Piece piece = findPieceOnBoardByIndex(index);
		piece.setTier(1);
	}

	public int getPieceTier(int index) {
		Piece piece = findPieceOnBoardByIndex(index);
		return piece.getTier();
	}

	public boolean isPiecedMarshel(int index)
	{
		Piece piece = findPieceOnBoardByIndex(index);
		return piece.getClass() == Marshel.class;
	}

	public boolean isPieceFortress(int index) {
		Piece piece = findPieceOnBoardByIndex(index);
		return piece.getClass() == Fortress.class;
	}

	public boolean isMarshelInDanger() {
		Player currentPlayer = findCurrentPlayer();
		Piece marhselPiece = currentPlayer.getMarshel();
		int marshelIndex = marhselPiece.getRow() * 9 + marhselPiece.getCol();

		Player otherPlayer = moveObject.getPlayer();
		HashMap<Integer, Piece> playerPieces = otherPlayer.getPlayerPieces();

		for (Map.Entry<Integer, Piece> set : playerPieces.entrySet()) {

			Piece piece = set.getValue();
			int[] allPiecePossibleMoves = findAllPossibleMoves(piece.getRow() * 9 + piece.getCol());
			for (int j = 0; j < allPiecePossibleMoves.length; j++) {
				if (allPiecePossibleMoves[j] == marshelIndex) {
					return true;
				}
			}

		}

		return false;
	}

	public boolean willOpponentMarshelBeInCheckAfterPuttin(String typeKey, int row, int col)
	{
		demoFwPut(typeKey, row, col);
		moveObject.move(findCurrentPlayer(), "demiPut");
		boolean flag = findIfMarshelInCheck();
		moveObject.move(findCurrentPlayer(), "cancleDemiPut");
		demoBwPut(typeKey);
		return flag;
	}

	public void demoFwPut(String typeKey, int row, int col)
	{
		Player currentPlayer = findCurrentPlayer();
		currentPlayer.createPlayerPiece(typeKey, row, col);
	}

	public void demoBwPut(String typeKey)
	{
		Player currentPlayer = findCurrentPlayer();
		currentPlayer.getPlayerPieces().remove(currentPlayer.getNumOfActivePieces());
		currentPlayer.setNumOfActivePieces(currentPlayer.getNumOfActivePieces() - 1);
		currentPlayer.getPlayerUnusedPieces().put(typeKey, currentPlayer.getPlayerUnusedPieces().get(typeKey)+1);
		currentPlayer.setNumOfUnusedPiece(currentPlayer.getNumOfUnusedPieces()+1);
	}


	public boolean willMarshelBeInDangerAfterPuttin(String typeKey, int row, int col)
	{
		demoFwPut(typeKey, row, col);
		boolean flag = isMarshelInDanger();
		demoBwPut(typeKey);
		return flag;
	}

	public void demoFwMove(int oldIndex, int newIndex)
	{
		Piece piece = findPieceOnBoardByIndex(oldIndex);
		piece.move(newIndex / 9, newIndex % 9);
		piece.setTier(1);
	}

	public void demoBwMove(int oldIndex, int newIndex)
	{
		Piece piece = findPieceOnBoardByIndex(newIndex);
		piece.setTier(findPieceTierByIndex(oldIndex)+1);
		piece.move(oldIndex / 9, oldIndex % 9);
	}


	public boolean willMarshelBeInDangerAfterMoving(int oldIndex, int newIndex)
	{
		demoFwMove(oldIndex, newIndex);
		boolean flag = isMarshelInDanger();
		demoBwMove(oldIndex, newIndex);
		return flag;
	}

	public Piece demoFwAttack(int oldIndex, int newIndex)
	{
		Piece attackingPiece = findPieceOnBoardByIndex(oldIndex);
		Piece attackedPiece = findPieceOnBoardByIndex(newIndex);
		int attackedPieceTier = attackedPiece.getTier();
		attackingPiece.setTier(attackedPieceTier);
		attackedPiece.setTier(attackedPieceTier - 1);
		attackingPiece.move(newIndex / 9, newIndex % 9);
		return attackedPiece;
	}

	public void demoBwAttack(int oldIndex, int newIndex, Piece attackedPiece)
	{
		Piece attackingPiece = findPieceOnBoardByIndex(newIndex);
		attackingPiece.setTier(findPieceTierByIndex(oldIndex)+1);
		attackingPiece.move(oldIndex / 9, oldIndex % 9);
		int attackedPieceTier = attackedPiece.getTier();
		attackedPiece.setTier(attackedPieceTier+1);
	}



	public boolean willMarshelBeInDangerAfterAttacking(int oldIndex, int newIndex)
	{
		Piece attackedPiece = demoFwAttack(oldIndex, newIndex);
		boolean flag = isMarshelInDanger();
		demoBwAttack(oldIndex, newIndex, attackedPiece);
		return flag;
	}

	public void demoFwStack(int oldIndex, int newIndex)
	{
		Piece stackingPiece = findPieceOnBoardByIndex(oldIndex);
		stackingPiece.setTier(findPieceOnBoardByIndex(newIndex).getTier() + 1);
		stackingPiece.move(newIndex / 9, newIndex % 9);
	}

	public void demoBwStack(int oldIndex, int newIndex)
	{
		int oldTier = findPieceTierByIndex(oldIndex);
		Piece stackingPiece = findPieceOnBoardByIndex(newIndex);
		stackingPiece.move(oldIndex / 9, oldIndex % 9);
		stackingPiece.setTier(oldTier+1);
	}
	public boolean willMarshelBeInDangerAfterStacking(int oldIndex, int newIndex)
	{
		demoFwStack(oldIndex, newIndex);
		boolean flag = isMarshelInDanger();
		demoBwStack(oldIndex, newIndex);
		return flag;

	}

	public boolean isTherePawnInCol(int col) {
		Player currentPlayer = findCurrentPlayer();
		HashMap<Integer, Piece> playerPieces = currentPlayer.getPlayerPieces();
		for (Map.Entry<Integer, Piece> set : playerPieces.entrySet()) {
			Piece piece = set.getValue();
			if (piece.getClass() == Pawn.class && piece.getCol() == col)
				return true;
		}
		return false;

	}

}
