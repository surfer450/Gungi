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

	public Board(Player Player1, Player Player2) {
		this.Player1 = Player1;
		this.Player2 = Player2;
		String[] colors = { "White", "Black" };
		int index = new Random().nextInt(2);
		Player1.setColor(colors[index]);
		Player2.setColor(colors[1 - index]);

		// start move
		if (Player1.getColor() == "White")
			moveObject.move(Player1, "put");
		else
			moveObject.move(Player2, "put");
	}

	public Board() {
		this.Player1 = null;
		this.Player2 = null;
	}

	public void setGame(Game gameObject) {
		this.gameObject = gameObject;
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

		if (piece1 == null) {

			return piece2;
		} else {
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
		return -1;
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
		gameObject.changePhase(moveObject);
	}

	public boolean isMarshelOnBoard() {
		Player currentPlayer = findCurrentPlayer();
		return currentPlayer.isMarshelExist();
	}

	public boolean isPuttingPieceLegel() {
		// return true if not more then 18 pieces for current player
		Player currentPlayer = findCurrentPlayer();
		return !(currentPlayer.isPlayerPiecesMax());
	}

	public void putPiece(String chosenPiece, int row, int col) {
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

	public boolean isPiecedMarshel(int index) {
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
		Player currentPlayer = findCurrentPlayer();
		currentPlayer.createPlayerPiece(typeKey, row, col);
		moveObject.move(currentPlayer, "demiPut");
		boolean flag = findIfMarshelInCheck();
		moveObject.move(findCurrentPlayer(), "cancleDemiPut");
		currentPlayer.getPlayerPieces().remove(currentPlayer.getNumOfActivePieces());
		currentPlayer.setNumOfActivePieces(currentPlayer.getNumOfActivePieces() - 1);
		currentPlayer.getPlayerUnusedPieces().put(typeKey, currentPlayer.getPlayerUnusedPieces().get(typeKey)+1);
		currentPlayer.setNumOfUnusedPiece(currentPlayer.getNumOfUnusedPieces()+1);
		return flag;
	}
	public boolean willMarshelBeInDangerAfterPuttin(String typeKey, int row, int col) {
		Player currentPlayer = findCurrentPlayer();
		currentPlayer.createPlayerPiece(typeKey, row, col);
		boolean flag = isMarshelInDanger();
		currentPlayer.getPlayerPieces().remove(currentPlayer.getNumOfActivePieces());
		currentPlayer.setNumOfActivePieces(currentPlayer.getNumOfActivePieces() - 1);
		currentPlayer.getPlayerUnusedPieces().put(typeKey, currentPlayer.getPlayerUnusedPieces().get(typeKey)+1);
		currentPlayer.setNumOfUnusedPiece(currentPlayer.getNumOfUnusedPieces()+1);
		return flag;
	}

	public boolean willMarshelBeInDangerAfterMoving(int oldIndex, int newIndex) {

		Piece piece = findPieceOnBoardByIndex(oldIndex);
		int oldTier = piece.getTier();

		piece.move(newIndex / 9, newIndex % 9);
		piece.setTier(1);
		boolean flag = isMarshelInDanger();

		piece.setTier(oldTier);
		piece.move(oldIndex / 9, oldIndex % 9);
		return flag;

	}

	public boolean willMarshelBeInDangerAfterAttacking(int oldIndex, int newIndex) {
		Piece attackingPiece = findPieceOnBoardByIndex(oldIndex);
		int attackingPieceTier = attackingPiece.getTier();
		Piece attackedPiece = findPieceOnBoardByIndex(newIndex);
		int attackedPieceTier = attackedPiece.getTier();

		attackingPiece.setTier(attackedPieceTier);
		attackedPiece.setTier(attackedPieceTier - 1);
		attackingPiece.move(newIndex / 9, newIndex % 9);

		boolean flag = isMarshelInDanger();

		attackedPiece.setTier(attackedPieceTier);
		attackingPiece.setTier(attackingPieceTier);
		attackingPiece.move(oldIndex / 9, oldIndex % 9);
		return flag;
	}

	public boolean willMarshelBeInDangerAfterStacking(int oldIndex, int newIndex) {

		Piece stackingPiece = findPieceOnBoardByIndex(oldIndex);
		int oldTier = stackingPiece.getTier();
		stackingPiece.setTier(findPieceOnBoardByIndex(newIndex).getTier() + 1);
		stackingPiece.move(newIndex / 9, newIndex % 9);

		boolean flag = isMarshelInDanger();
		stackingPiece.move(oldIndex / 9, oldIndex % 9);
		stackingPiece.setTier(oldTier);
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
