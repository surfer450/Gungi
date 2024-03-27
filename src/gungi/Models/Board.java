package gungi.Models;

import com.sun.javafx.image.BytePixelSetter;
import gungi.Enums.GameMode;
import gungi.Enums.GameState;
import gungi.Enums.MoveType;
import gungi.Enums.PieceType;
import gungi.Pieces.Captain;
import gungi.Pieces.Fortress;
import gungi.Pieces.Marshel;
import gungi.Pieces.Pawn;

import java.awt.*;
import java.util.ArrayList;
//מחלקה אחראית על כל מה שקשור ללוח המשחק: תורות, שחקנים תזוזה וכדומה
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static gungi.Constants.*;

public class Board {
	private final Player Player1;
	private final Player Player2;
	private final ActivateMove activateMove = new ActivateMove();
	private Game gameObject;

	public Board(Player Player1, Player Player2)
	{
		this.Player1 = Player1;
		this.Player2 = Player2;
		activateMove.setBoard(this);
	}

	public void setGame(Game gameObject)
	{

		this.gameObject = gameObject;
		Color[] colors = { Color.WHITE, Color.BLACK};
		int index = new Random().nextInt(AMOUNT_OF_PLAYERS);
		Player1.setColor(colors[index]);
		Player2.setColor(colors[AMOUNT_OF_PLAYERS - MINUS_INDEX - index]);

		// start move
		if (Player1.getColor() == Color.WHITE)
			activateMove.ChangeTurn(Player1, null, true);
		else
			activateMove.ChangeTurn(Player2, null, true);

		if (this.gameObject.getPlayerMode() == GameMode.Computer)
		{
			if (findCurrentPlayer().getClass() == ComputerPlayer.class)
			{
				playComputerTurn();
			}
		}
	}

	public ActivateMove getActivateMove()
	{
		return activateMove;
	}

	public void activateBestMove(Player computerPlayer, MoveObject bestMove)
	{
		System.out.println(bestMove.getMoveType());
		switch (bestMove.getMoveType())
		{
			//put
			case Put:
				gameObject.putComputerPieceOnBoard(bestMove, computerPlayer.getColor());
				break;

			//move
			case Move:
				gameObject.moveComputerPieceOnBoard(bestMove, computerPlayer.getColor());
				break;

			//attack
			case Attack:
				gameObject.attackComputerPieceOnBoard(bestMove, computerPlayer.getColor());
				break;

			//stack
			case Stack:
				gameObject.stackComputerPieceOnBoard(bestMove, computerPlayer.getColor());
				break;
		}
	}
	public void playComputerTurn()
	{
		ComputerPlayer computerPlayer = (ComputerPlayer) findCurrentPlayer();
		if (computerPlayer.getAmountOfOpeningMoves() > OPENING_NOT_FINISH)
		{
			Map.Entry<Integer, PieceType> currentAdvanced= computerPlayer.advanceInOpening();
			MoveObject moveObject = new MoveObject(NOT_FOUND, currentAdvanced.getKey(), MoveType.Put, currentAdvanced.getValue());
			gameObject.putComputerPieceOnBoard(moveObject, computerPlayer.getColor());
		}

		else if (computerPlayer.getAmountOfOpeningMoves() == OPENING_NOT_FINISH)
		{
			computerPlayer.setAmountOfOpeningMoves(NOT_FOUND);
			gameObject.putReadyForComputer(computerPlayer.getColor());
		}

		else
		{
			MoveObject bestMove = findBestMove();
			activateBestMove(computerPlayer, bestMove);
		}
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
			int valueIndex = piece.getRow()*SIZE_OF_BOARD + piece.getCol();
			if (findCurrentPlayer().getColor() == Color.BLACK) { valueIndex = AMOUNT_OF_SQUARES - valueIndex; }
			pieceValue += kingPositionPoints[valueIndex / SIZE_OF_BOARD][valueIndex % SIZE_OF_BOARD];
		}
		else { pieceValue += regularPiecePositionPoints[piece.getRow()][piece.getCol()];}
		return pieceValue;
	}

	public ArrayList<Piece> allPiecesOfPLayerAsList(Player player)
	{
		HashMap<Integer, Piece> piecesHash = player.getPlayerPieces();
		ArrayList<Piece> pieces = new ArrayList<>();
		Piece piece;
		for (Map.Entry<Integer, Piece> entry: piecesHash.entrySet())
		{
			piece = entry.getValue();
			if (piece == findPieceOnBoardByIndex(piece.getRow() * SIZE_OF_BOARD + piece.getCol()))
			{
				pieces.add(piece);
			}
		}
		return pieces;
	}

	public ArrayList<Piece> findAllPiecesOnBoard()
	{
		ArrayList<Piece> pieces = allPiecesOfPLayerAsList(Player1);
		pieces.addAll(allPiecesOfPLayerAsList(Player2));
		return pieces;
	}

	public ArrayList<Piece> findThreateningPiecesToSqaure(int index)
	{
		ArrayList<Piece> pieces = findAllPiecesOnBoard();
		ArrayList<Piece> threateningPieces = new ArrayList<>();
		int tempIndex;

		for (Piece piece: pieces)
		{
			tempIndex = piece.getRow() * SIZE_OF_BOARD + piece.getCol();
			{
				for (int move : findAllPossibleMoves(tempIndex))
				{
					if (move == index)
					{
						threateningPieces.add(piece);
					}
				}
			}
		}

		return threateningPieces;
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

	public boolean isThreateningPiece(Player player, Piece piece, boolean flag)
	{
		if (flag)
			return player.getClass() == ComputerPlayer.class || !findIfSquareThreatenedByEnemy(piece.getRow() * SIZE_OF_BOARD + piece.getCol());
		else
			return !(player.getClass() == ComputerPlayer.class) || !findIfSquareThreatenedByEnemy(piece.getRow() * SIZE_OF_BOARD + piece.getCol());
	}

	public boolean isPieceGoodColor(Piece piece, boolean flag, Color color)
	{
		if (flag)
			return (!piece.getColor().equals(color));
		else
			return piece.getColor().equals(color);
	}

	public Piece findMinPieceByColor(ArrayList<Piece> ThreateningPieces, Player player, Color color, boolean flag)
	{
		Piece minPiece = null;
		for (Piece piece: ThreateningPieces)
		{
			if (isThreateningPiece(player, piece, flag))
			{
				if (isPieceGoodColor(piece, flag, color))
				{
					if (minPiece == null)
					{
						minPiece = piece;
					}
					else if (getPieceValue(minPiece) > getPieceValue(piece))
					{
						minPiece = piece;
					}
				}
			}
		}
		return minPiece;

	}

	public double increaseSquareValue(Player player, double squareValue, ArrayList<Piece> ThreateningPieces, boolean searchState)
	{
		if (player.getClass() == ComputerPlayer.class) { squareValue *= COMPUTER_SQUARE_PRIORITY;}
		else { squareValue *= HUMAN_SQUARE_PRIORITY;}

		//this part add points for having over protected or attacked square
		if (!ThreateningPieces.isEmpty())
		{
			if (searchState) { squareValue += (double) ThreateningPieces.size() / COMPUTER_SQUARE_PRIORITY;}
			else { squareValue -= (double) ThreateningPieces.size() / COMPUTER_SQUARE_PRIORITY;}
		}
		return squareValue;
	}
	public double evaluateSquare(int index, Player player)
	{

		Piece currPiece = findPieceOnBoardByIndex(index);
		boolean noMoreThreat = false, searchState = true;
		ArrayList<Piece> ThreateningPieces = findThreateningPiecesToSqaure(index);
		Piece minPiece = null;
		double squareValue = DEFAULT_SQUARE_VALUE;

		while (!ThreateningPieces.isEmpty() && !noMoreThreat)
		{
			minPiece = findMinPieceByColor(ThreateningPieces, player, currPiece.getColor(), searchState);
			if (minPiece == null)  { noMoreThreat = true; }
			else
			{
				if (searchState) { squareValue -= getPieceValue(currPiece);}
				else { squareValue += getPieceValue(currPiece);}
				searchState = !searchState;
				currPiece = minPiece;
				ThreateningPieces.remove(currPiece);
			}

		}

		return increaseSquareValue(player, squareValue, ThreateningPieces, searchState);
	}

	public double evaluateOneSide(Player player)
	{
		double points = DEFAULT_SQUARE_VALUE;
		Piece piece;
		int index;


		HashMap<Integer, Piece> allPlayerPieces = player.getPlayerPieces();
		for (Map.Entry<Integer, Piece> entry : allPlayerPieces.entrySet())
		{
			piece = entry.getValue();
			index = piece.getRow() * SIZE_OF_BOARD + piece.getCol();
			if (findPieceOnBoardByIndex(index) == piece)
			{
				points += getPieceValue(piece);
			}

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
		otherPlayerPoints = evaluateOneSide(activateMove.getPlayer());
		return currentPlayerPoints - otherPlayerPoints;
	}

	public MoveObject checkIfBetterMove(MoveObject bestMove, MoveObject tempMove, double tempValue, Score score)
	{
		if (tempValue > score.getScore())
		{
			score.setScore(tempValue);
			return tempMove;
		}
		return bestMove;
	}

	public MoveObject evaluatePut(int index, PieceType pieceName, MoveObject bestMove, Score score)
	{
		MoveObject tempMove = new MoveObject(-1, index, MoveType.Put, pieceName);
		activateMove.demoFwPut(findCurrentPlayer(), tempMove, false);
		double tempValue = evaluateMove();
		activateMove.demoBwPut(findCurrentPlayer(), tempMove, false);
		return checkIfBetterMove(bestMove, tempMove, tempValue, score);
	}
	public MoveObject evaluateMove(int oldIndex, int newIndex, Piece piece, MoveObject bestMove, Score score)
	{
		MoveObject tempMove = new MoveObject(oldIndex, newIndex, MoveType.Move, piece.getPieceType());
		activateMove.demoFwMove(findCurrentPlayer(), tempMove, false);
		double tempValue = evaluateMove();
		activateMove.demoBwMove(findCurrentPlayer(), tempMove, false);
		return checkIfBetterMove(bestMove, tempMove, tempValue, score);
	}

	public MoveObject evaluateAttack(int oldIndex, int newIndex, Piece piece, MoveObject bestMove, Score score)
	{
		MoveObject tempMove = new MoveObject(oldIndex, newIndex, MoveType.Attack, piece.getPieceType());
		Piece attackedPiece = activateMove.demoFwAttack(findCurrentPlayer(), tempMove, false);
		double tempValue = evaluateMove();
		activateMove.demoBwAttack(findCurrentPlayer(), tempMove, false, attackedPiece);
		return checkIfBetterMove(bestMove, tempMove, tempValue, score);
	}

	public MoveObject evaluateStack(int oldIndex, int newIndex, Piece piece, MoveObject bestMove, Score score)
	{
		MoveObject tempMove = new MoveObject(oldIndex, newIndex, MoveType.Stack, piece.getPieceType());
		activateMove.demoFwStack(findCurrentPlayer(), tempMove, false);
		double tempValue = evaluateMove();
		activateMove.demoBwStack(findCurrentPlayer(), tempMove, false);
		return checkIfBetterMove(bestMove, tempMove, tempValue, score);
	}
	public MoveObject findBestMovableMove(Player player, Score score)
	{
		int oldIndex, newIndex;
		int[] allPossibileMovesArr;
		MoveObject bestMove = null;
		Piece tempPiece;

		for (Piece piece: allPiecesOfPLayerAsList(player))
		{
			allPossibileMovesArr = findAllPossibleMoves(piece.getRow() * SIZE_OF_BOARD + piece.getCol());
			for (int k = 0; k < allPossibileMovesArr.length; k++)
			{
				oldIndex = piece.getRow() * SIZE_OF_BOARD + piece.getCol();
				newIndex = allPossibileMovesArr[k];
				tempPiece = findPieceOnBoardByIndex(allPossibileMovesArr[k]);

				if (tempPiece == null)
				{
					if (gameObject.isMovingPieceLegal(oldIndex, newIndex)) { bestMove = evaluateMove(oldIndex, newIndex, piece, bestMove, score);}
				}

				else
				{
					if (gameObject.isAttackingPieceLegal(oldIndex, newIndex)) { bestMove = evaluateAttack(oldIndex, newIndex, piece, bestMove, score);}
					if (gameObject.isStackingPieceLegal(oldIndex, newIndex)) { bestMove = evaluateStack(oldIndex, newIndex, piece, bestMove, score);}
				}
			}
		}
		return bestMove;
	}

	public MoveObject findBestPut(Player player, Score score)
	{
		MoveObject bestMove = null;
		PieceType pieceName;

		if (player.getNumOfUnusedPieces() > NO_MORE_REMAINS_PIECES && isMaxPiecesOnBoardForPlayer())
		{
			HashMap<PieceType, Integer> unusedPieces = player.getPlayerUnusedPieces();
			for (Map.Entry<PieceType, Integer> entry : unusedPieces.entrySet())
			{
				if (entry.getValue() > NO_MORE_REMAINS_PIECES)
				{
					pieceName = entry.getKey();
					for (int i = 0; i <= AMOUNT_OF_SQUARES; i++)
					{
						if (gameObject.isPuttingPieceLegal(i, pieceName)) { bestMove = evaluatePut(i, pieceName, bestMove, score);}
					}
				}
			}
		}
		return bestMove;
	}

	public MoveObject findBestMove()
	{
		Player player = findCurrentPlayer();
		Score score = new Score();
		MoveObject bestPut, bestMove = findBestMovableMove(player, score);
		if ((bestPut = findBestPut(player, score)) != null)
		{
			return bestPut;
		}
		return bestMove;
	}

	public boolean isValidPiece(Color color) {
		// return true if a piece belongs to the current player
		Player currentPlayer = findCurrentPlayer();
		return (currentPlayer.getColor() == color);
	}

	public Player findCurrentPlayer() {

		if (Player1 != activateMove.getPlayer())
			return Player1;
		return Player2;
	}

	public Player findPlayerByColor(Color color)
	{
		if (Player1.getColor() == color)
			return Player1;
		else
			return Player2;
	}

	public Piece findPieceOnBoardByIndex(int index) {

		Piece piece1 = Player1.findPieceByIndex(index);
		Piece piece2 = Player2.findPieceByIndex(index);
		if (piece1 == null)  return piece2;
		else
		{
			if (piece2 == null) return piece1;
			else {

				if (piece1 != null && piece2 != null)
				{
					if (piece1.getTier() > piece2.getTier()) return piece1;
					else return piece2;
				}
			}
		}
		return null;
	}

	public PieceType findPieceTypeByIndex(int index)
	{
		Piece piece = findPieceOnBoardByIndex(index);
		if (piece != null)
		{
			return piece.getPieceType();
		}
		return null;
	}

	public Player getPlayer1() {
		return Player1;
	}

	public Player getPlayer2() {
		return Player2;
	}

		public Color findPieceColorByIndex(int index) {

		Piece indexPiece = findPieceOnBoardByIndex(index);
		if (indexPiece != null) {
			return indexPiece.getColor();
		}
		return null;
	}

	public int findPieceTierByIndex(int index)
	{
		Piece indexPiece = findPieceOnBoardByIndex(index);
		if (indexPiece != null) {
			return indexPiece.getTier();
		}
		return 0;
	}

	public boolean isWhiteSquare(int count)
	{
		if (gameObject.getGameState() == GameState.StartPhase)
		{
			if (count >= WHITE_BEG_SQUARE && count <= AMOUNT_OF_SQUARES)
				return true;
			return false;
		}
		else
		{
			if (count >= WHITE_PLAY_SQUARE && count <= AMOUNT_OF_SQUARES)
				return true;
			return false;
		}
	}

	public boolean isBlackSquare(int count)
	{
		if (gameObject.getGameState() == GameState.StartPhase) {
			if (count >= FIRST_SQUARE && count <= BLACK_BEG_SQUARE)
				return true;
			return false;
		} else {
			if (count >= FIRST_SQUARE && count <= BLACK_PLAY_SQUARE)
				return true;
			return false;
		}
	}

	public boolean isYourSquare(int count)
	{

		Player currentPlayer = findCurrentPlayer();
		if (currentPlayer.getColor() == Color.WHITE) { return isWhiteSquare(count);}
		else { return isBlackSquare(count);}
	}

	public void addPlayerToReadyList() {
		Player currentPlayer = findCurrentPlayer();
		gameObject.addToReadyList(currentPlayer);
		activateMove.ChangeTurn(currentPlayer, null, true);
		if (gameObject.getAmountOfReadyPlayers() == ONE_PLAYER_READY && findCurrentPlayer().getClass() == ComputerPlayer.class)
		{
			playComputerTurn();
		}
		gameObject.changePhase(activateMove);
	}

	public boolean isMarshelOnBoard()
	{
		Player currentPlayer = findCurrentPlayer();
		return currentPlayer.isMarshelExist();
	}

	public boolean isMaxPiecesOnBoardForPlayer() {
		// return true if not more then 18 pieces for current player
		Player currentPlayer = findCurrentPlayer();
		return (currentPlayer.getNumOfActivePieces() < MAX_PIECES_FOR_PLAYER);
	}

	public boolean isSquareEmpty(int index)
	{
		Piece piece = findPieceOnBoardByIndex(index);
		return piece == null;
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

	public Piece findPieceBeneathCaptain(int index) {
		Piece captinPiece = findPieceOnBoardByIndex(index);
		captinPiece.move(captinPiece.getRow(), captinPiece.getCol() + EXTRA_COL);
		Piece indexPiece = findPieceOnBoardByIndex(index);
		captinPiece.move(captinPiece.getRow(), captinPiece.getCol() - EXTRA_COL);
		return indexPiece;
	}

	public int checkNumberSign(int number) {
		if (number > DEFAULT_SQUARE_VALUE) { return EXTRA_COL;}
		else
		{
			if (number < DEFAULT_SQUARE_VALUE) { return -EXTRA_COL;}
			else { return DEFAULT_SQUARE_VALUE; }
		}
	}

	public int[] updateAllPossibleMoves(int[] oldArr, int index) {
		ArrayList<Integer> allPossibleMoves = new ArrayList<Integer>();
		for (int i = 0; i < oldArr.length; i++) {
			allPossibleMoves.add(oldArr[i]);
		}

		int originalRow, originalCol, moveRow, moveCol, removeRow, removeCol;
		originalRow = index / SIZE_OF_BOARD;
		originalCol = index % SIZE_OF_BOARD;
		for (int i = 0; i < allPossibleMoves.size(); i++) {

			if (findPieceOnBoardByIndex(allPossibleMoves.get(i)) != null) {
				moveRow = allPossibleMoves.get(i) / SIZE_OF_BOARD;
				moveCol = allPossibleMoves.get(i) % SIZE_OF_BOARD;

				for (int j = 0; j < allPossibleMoves.size(); j++) {
					removeRow = allPossibleMoves.get(j) / SIZE_OF_BOARD;
					removeCol = allPossibleMoves.get(j) % SIZE_OF_BOARD;

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

		if (indexPiece != null)
		{
			Color old_color = indexPiece.getColor();
			if (indexPiece.getClass() == Captain.class && indexPiece.getTier() > FIRST_PIECE_TIER) {
				Piece captinPiece = indexPiece;
				captinPiece.move(captinPiece.getRow(), captinPiece.getCol() + EXTRA_COL);
				indexPiece = findPieceOnBoardByIndex(index);
				captinPiece.move(captinPiece.getRow(), captinPiece.getCol() - EXTRA_COL);
				indexPiece.setColor(captinPiece.getColor());
			}

			int[] allIndexes = indexPiece.getAllPossibleIndexes();
			if (indexPiece.getClass() == Captain.class && indexPiece.getTier() > FIRST_PIECE_TIER) {
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
			allPossibileMovesArr = findAllPossibleMoves(piece.getRow() * SIZE_OF_BOARD + piece.getCol());
			for (int k = 0; k < allPossibileMovesArr.length; k++) {
				tempPiece = findPieceOnBoardByIndex(allPossibileMovesArr[k]);
				if (tempPiece == null) {
					if (!willMarshelBeInDangerAfterMoving(piece.getRow() * SIZE_OF_BOARD + piece.getCol(), allPossibileMovesArr[k])) {
						return false;
					}
				}
				else
				{
					if (!willMarshelBeInDangerAfterAttacking(piece.getRow() * SIZE_OF_BOARD + piece.getCol(), allPossibileMovesArr[k])){
						return false;
					}
				}
			}
		}

		if (currentPlayer.getNumOfUnusedPieces() > NO_MORE_REMAINS_PIECES) {

			for (int i = 0; i <= AMOUNT_OF_SQUARES; i++) {
				if (isYourSquare(i) && findPieceOnBoardByIndex(i) == null)
				{
					if (!willMarshelBeInDangerAfterPuttin(PieceType.Pawn, i / SIZE_OF_BOARD, i % SIZE_OF_BOARD))
						return false;
				}
			}
		}


		return true;
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
		int marshelIndex = marhselPiece.getRow() * SIZE_OF_BOARD + marhselPiece.getCol();

		Player otherPlayer = activateMove.getPlayer();
		HashMap<Integer, Piece> playerPieces = otherPlayer.getPlayerPieces();

		for (Map.Entry<Integer, Piece> set : playerPieces.entrySet()) {

			Piece piece = set.getValue();
			int[] allPiecePossibleMoves = findAllPossibleMoves(piece.getRow() * SIZE_OF_BOARD + piece.getCol());
			for (int j = 0; j < allPiecePossibleMoves.length; j++)
			{
				if (allPiecePossibleMoves[j] == marshelIndex) { return true;}
			}

		}

		return false;
	}

	public boolean willOpponentMarshelBeInCheckAfterPuttin(PieceType typeKey, int row, int col)
	{
		int index = row*9+col;
		MoveObject moveObject =  new MoveObject(NOT_FOUND, index, MoveType.Put, typeKey);
		activateMove.demoFwPut(findCurrentPlayer(), moveObject, true);
		boolean flag = findIfMarshelInCheck();
		activateMove.ChangeTurn(findCurrentPlayer(), null, true);
		activateMove.demoBwPut(findCurrentPlayer(), moveObject, false);
		return flag;
	}

	public boolean willMarshelBeInDangerAfterPuttin(PieceType typeKey, int row, int col)
	{
		int index = row*9+col;
		MoveObject moveObject =  new MoveObject(NOT_FOUND, index, MoveType.Put, typeKey);
		activateMove.demoFwPut(findCurrentPlayer(), moveObject, false);
		boolean flag = isMarshelInDanger();
		activateMove.demoBwPut(findCurrentPlayer(), moveObject, false);
		return flag;
	}

	public boolean willMarshelBeInDangerAfterMoving(int oldIndex, int newIndex)
	{
		MoveObject moveObject =  new MoveObject(oldIndex, newIndex, MoveType.Move, null);
		activateMove.demoFwMove(findCurrentPlayer(), moveObject, false);
		boolean flag = isMarshelInDanger();
		activateMove.demoBwMove(findCurrentPlayer(), moveObject, false);
		return flag;
	}

	public boolean willMarshelBeInDangerAfterAttacking(int oldIndex, int newIndex)
	{
		MoveObject moveObject =  new MoveObject(oldIndex, newIndex, MoveType.Attack, null);
		Piece attackedPiece = activateMove.demoFwAttack(findCurrentPlayer(), moveObject, false);
		boolean flag = isMarshelInDanger();
		activateMove.demoBwAttack(findCurrentPlayer(), moveObject, false, attackedPiece);
		return flag;
	}




	public boolean willMarshelBeInDangerAfterStacking(int oldIndex, int newIndex)
	{
		MoveObject moveObject =  new MoveObject(oldIndex, newIndex, MoveType.Stack, null);
		activateMove.demoFwStack(findCurrentPlayer(), moveObject, false);
		boolean flag = isMarshelInDanger();
		activateMove.demoBwStack(findCurrentPlayer(), moveObject, false);
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
