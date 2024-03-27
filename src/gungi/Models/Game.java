package gungi.Models;

import gungi.Enums.GameMode;
import gungi.Enums.GameState;
import gungi.Enums.MoveType;
import gungi.Enums.PieceType;
import gungi.Observers.controllerObserver;

import java.awt.Color;
import java.util.ArrayList;

import static gungi.Constants.*;

//מחלקה אחראית על שמירת נתונים על מצב משחק
public class Game
{
	private final Board board;
	private GameState gameState = GameState.StartPhase;
	private ArrayList<Player> playersReadyList = new ArrayList<Player>();

	private GameMode playerMode;

	private controllerObserver gameControllerObj;
	
	public Game(GameMode mode, controllerObserver gameControllerObj)
	{
		if (mode == GameMode.Human)
			board = new Board(new HumanPlayer(), new HumanPlayer());
		else
			board = new Board(new HumanPlayer(), new ComputerPlayer());
		playerMode = mode;
		setGameControllerObserver(gameControllerObj);

	}

	public int getAmountOfReadyPlayers()
	{
		return playersReadyList.size();
	}
	public void setBoardGame()
	{
		board.setGame(this);
	}

	public void setGameControllerObserver( controllerObserver gameControllerObj){
		this.gameControllerObj = gameControllerObj;
	}
	
	public Board getBoard()
	{
		return this.board;
	}

	public GameState getGameState()
	{
		return this.gameState;
	}

	public GameMode getPlayerMode()
	{
		return this.playerMode;
	}

	
	public void addToReadyList(Player player)
	{
		playersReadyList.add(player);
	}
	
	public boolean isStartPhaseOver()
	{
		Player Player1 = board.getPlayer1();
		Player Player2 = board.getPlayer2();
		if (gameState == GameState.StartPhase)
		{
			if ((Player1.isPlayerPiecesMax() && Player2.isPlayerPiecesMax()) 
				|| (playersReadyList.size() == BOTH_PLAYER_READY)
				|| ((playersReadyList.size() == ONE_PLAYER_READY) && ((Player1.isPlayerPiecesMax() || Player2.isPlayerPiecesMax()))))
			{ return true; }
		}
		return false;
	}
	
	public void changePhase(ActivateMove moveObject)
	{
		if (isStartPhaseOver())
		{
			gameState = GameState.PlayPhase;
			Player blackPlayer = board.findPlayerByColor(Color.BLACK);
			moveObject.ChangeTurn(blackPlayer, null, true);

		}	
	}

	public boolean isPlayerOnReady(Player player)
	{
		return !(playersReadyList.indexOf(player) == NOT_FOUND);
	}

	public void putComputerPieceOnBoard(MoveObject moveObject, Color color)
	{
		gameControllerObj.putComputerPiece(moveObject, color);
	}

	public void moveComputerPieceOnBoard(MoveObject moveObject, Color color)
	{
		gameControllerObj.moveComputerPiece(moveObject, color);
	}

	public void attackComputerPieceOnBoard(MoveObject moveObject, Color color)
	{
		gameControllerObj.attackComputerPiece(moveObject, color);
	}

	public void stackComputerPieceOnBoard(MoveObject moveObject, Color color)
	{
		gameControllerObj.stackComputerPiece(moveObject, color);
	}

	public void putReadyForComputer(Color color)
	{
		gameControllerObj.PutReadyComputer(color);
	}
	public boolean isCurrentPlayerComputer()
	{
		return (board.findCurrentPlayer().getClass() == ComputerPlayer.class);
	}


	public boolean isPuttingPieceLegal(int index, PieceType pieceName)
	{
		int row = index / SIZE_OF_BOARD, col = index % SIZE_OF_BOARD;
		if (board.isSquareEmpty(index) && board.isYourSquare(index) && board.isMaxPiecesOnBoardForPlayer())
		{
			if (board.isMarshelOnBoard() || pieceName == PieceType.Marshel)
			{
				if (!board.willMarshelBeInDangerAfterPuttin(pieceName, row, col))
				{
					if (!board.willOpponentMarshelBeInCheckAfterPuttin(pieceName, row, col))
					{
						if (!(pieceName == PieceType.Pawn) || !board.isTherePawnInCol(col))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isSelectingPieceOnBoardLegal(int index)
	{
		if (!board.isSquareEmpty(index) && gameState == GameState.PlayPhase)
		{
			if (board.isValidPiece(board.findPieceColorByIndex(index)))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isMovingLegal(int beginIndex, int endIndex)
	{
		return board.findIfMovePossible(beginIndex, endIndex);
	}

	public boolean isMovingPieceLegal(int oldIndex, int newIndex)
	{
		return (!board.willMarshelBeInDangerAfterMoving(oldIndex, newIndex));
	}

	public boolean isAttackingPieceLegal(int oldIndex, int newIndex)
	{
		if (!board.isPiecedMarshel(newIndex) && !(board.isPieceFortress(oldIndex) && board.findPieceOnBoardByIndex(newIndex).getTier() > FIRST_PIECE_TIER))
		{
			if (!board.isValidPiece(board.findPieceColorByIndex(newIndex)))
			{
				if (!board.willMarshelBeInDangerAfterAttacking(oldIndex, newIndex))
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isStackingPieceLegal(int oldIndex, int newIndex)
	{
		if ( !board.isPiecedMarshel(newIndex) && !board.isPieceFortress(oldIndex))
		{
			if (board.getPieceTier(newIndex) < THIRD_PIECE_TIER)
			{
				if (!board.willMarshelBeInDangerAfterStacking(oldIndex, newIndex))
				{
					return true;

				}
			}
		}
		return false;
	}

	public void put(PieceType chosenPieceType, int row, int col)
	{
		ActivateMove activateMove = board.getActivateMove();
		Player currentPlayer = board.findCurrentPlayer();
		int index = row * SIZE_OF_BOARD + col;
		MoveObject moveObject = new MoveObject(NOT_FOUND, index, MoveType.Put, chosenPieceType);
		if (isPlayerOnReady(activateMove.getPlayer()) && gameState == GameState.StartPhase)
		{
			activateMove.put(currentPlayer, moveObject, false);
		}
		else
			activateMove.put(currentPlayer, moveObject, true);
		changePhase(activateMove);
	}

	public int move(int index_beg, int index_end, int tier)
	{
		Player currentPlayer = board.findCurrentPlayer();
		ActivateMove activateMove = board.getActivateMove();
		MoveObject moveObject = new MoveObject(index_beg, index_end, MoveType.Move, null);
		return activateMove.move(currentPlayer, moveObject, true, tier);
	}

	public int attack(int old_index, int new_index)
	{
		Player currentPlayer = board.findCurrentPlayer();
		ActivateMove activateMove = board.getActivateMove();
		MoveObject moveObject = new MoveObject(old_index, new_index, MoveType.Attack, null);
		return activateMove.attack(currentPlayer, moveObject, false);
	}

	public int stack(int old_index, int new_index)
	{
		Player currentPlayer = board.findCurrentPlayer();
		ActivateMove activateMove = board.getActivateMove();
		MoveObject moveObject = new MoveObject(old_index, new_index, MoveType.Stack, null);
		return activateMove.stack(currentPlayer, moveObject, false);
	}
}
