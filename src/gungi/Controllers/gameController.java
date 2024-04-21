package gungi.Controllers;
import gungi.Enums.GameMode;
import gungi.Enums.GameState;
import gungi.Enums.PieceType;
import gungi.Models.ComputerPlayer;
import gungi.Models.Game;
import gungi.Models.MoveObject;
import gungi.Observers.controllerObserver;
import gungi.Views.JButtonPiece;
import gungi.Views.JButtonSquare;
import gungi.Views.viewClass;
import javax.swing.JButton;
import java.awt.*;
import java.sql.SQLOutput;

import static gungi.Constants.*;

//מחלקה אחראית לקשר בין שני חלקי האמ וי סי
public class gameController implements controllerObserver
{
	private Game gameObject;
	private viewClass view;
	public gameController()
	{
		setView();
	}

	public void putComputerPiece(MoveObject moveObject, Color color)
	{
		view.activatePut(moveObject, color);
	}

	public void  moveComputerPiece(MoveObject moveObject, Color color)
	{
		view.activateMove(moveObject, color);
	}

	public void attackComputerPiece(MoveObject moveObject, Color color)
	{
		view.activateAttack(moveObject, color);
	}

	public void stackComputerPiece(MoveObject moveObject, Color color)
	{
		view.activateStack(moveObject, color);
	}
	public void PutReadyComputer(Color color)
	{
		view.activateReady(color);
	}

	public void setGame(GameMode mode)
	{
		gameObject = new Game(mode, this);
		gameObject.setBoardGame();
	}

	public void setView()
	{
		//start view
		view = new viewClass();
		view.setGameController(this);
		view.createPieceButtons();
		view.createSquareButtons();
		view.setVisible(true);
	}

	public boolean isSelectingPieceFromPanelRequired(JButton button, JButton chosenPieceButton)
	{
		return chosenPieceButton != button;
	}
	
	public void pieceButtonFunction(JButtonSquare chosenSquareButton, JButtonPiece button, JButtonPiece chosenPieceButton)
	{			
		if (isSelectingPieceFromPanelRequired(button, chosenPieceButton))
		{
			if (gameObject.getBoard().isValidPiece(view.findStarterPieceColor(button)))
			{
				int[] allIndexes = null;
				if (chosenSquareButton!=null)
				{
					allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
				}
				view.setMovePanelInvisible();
				view.chosePieceToPut(allIndexes, button);			
			}
		}
			
		else
		{
			view.setMovePanelInvisible();
			view.setChosenPieceButtonNull();
		}
	}

	public boolean isPuttingRequired(JButton chosenPieceButton)
	{
		return chosenPieceButton != null;
	}

	public boolean isSelectingPieceOnBoardRequired(JButtonSquare chosenSquareButton)
	{
		return chosenSquareButton == null;
	}

	public boolean isCancelingSelectingRequired(JButtonSquare chosenSquareButton, JButtonSquare button)
	{
		return chosenSquareButton == button;
	}


	public void squareButtonFunction(JButtonSquare button, JButtonPiece chosenPieceButton, JButtonSquare chosenSquareButton)
	{
		if (isPuttingRequired(chosenPieceButton))
		{
			if (gameObject.isPuttingPieceLegal(view.findButtonIndex(button), view.findChosenButtonName()))
			{
				view.putPiece(button);
				checkForComputerTurn();
			}
		}

		else if (isSelectingPieceOnBoardRequired(chosenSquareButton))
		{
			if (gameObject.isSelectingPieceOnBoardLegal(view.findButtonIndex(button)))
			{
				view.setChosenSquareButton(button);
				int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(button));
				view.turnListOfButtonsToBlue(allIndexes, button);
			}
		}

		else if (isCancelingSelectingRequired(chosenSquareButton, button))
		{
				view.setChosenSquareButton(null);
				view.setMovePanelInvisible();
				int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(button));
				view.turnListOfButtonsToBlue(allIndexes, null);
		}

		else
		{
			movePiece(button, chosenPieceButton, chosenSquareButton);
		}
	}

	public void putPieceOnBoard(PieceType chosenPieceType, int index_row, int index_col)
	{
		gameObject.put(chosenPieceType, index_row, index_col);
		changeGameStateLabel();
	}

	public void movePiece(JButtonSquare button, JButton chosenPieceButton, JButtonSquare chosenSquareButton)
	{
		if (gameObject.isMovingLegal(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(button)))
		{
			//move to empty square
			if (gameObject.getBoard().isSquareEmpty(view.findButtonIndex(button)))
			{
				moveButtonFunction(button, chosenSquareButton);
			}

			//move piece to a non empty squre(attack or stack)
			else
			{
				view.showAttckAndStack(button);			
			}
		}
	}


	public void moveButtonFunction(JButtonSquare button, JButtonSquare chosenSquareButton)
	{
		if (gameObject.isMovingPieceLegal(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(button)))
		{
			int old_tier = gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButton));
			int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
			int new_index = gameObject.move(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(button), 1);
			view.movePieceToAnotherSquare(allIndexes, button);

			Color pieceColor = gameObject.getBoard().findPieceColorByIndex(new_index);
			PieceType pieceType = gameObject.getBoard().findPieceTypeByIndex(new_index);
			String path = view.buildPath(pieceType, pieceColor, FIRST_PIECE_TIER);
			view.setButtonImage(button, path);
			checkForPieceBelow(old_tier, chosenSquareButton);
			checkForCheck();
			checkForComputerTurn();
		}
	}

	public void attackButtonFunction(JButtonSquare chosenSquareButton, JButtonSquare chosenSquareButtonEnd)
	{
		if (gameObject.isAttackingPieceLegal(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd)))
		{
			int old_tier = gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButton));
			int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
			Color pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
			PieceType pieceType = gameObject.getBoard().findPieceTypeByIndex(view.findButtonIndex(chosenSquareButton));
			int PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButtonEnd));
			String path = view.buildPath(pieceType, pieceColor, PieceTier);
			int newPieceTier = gameObject.attack(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd));
			gameObject.move(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd), newPieceTier);
			view.attackButtonFunc(allIndexes, path);

			checkForPieceBelow(old_tier, chosenSquareButton);
			checkForCheck();
			checkForComputerTurn();
		}
	}


	public void stackButtonFunction(JButtonSquare chosenSquareButton, JButtonSquare chosenSquareButtonEnd)
	{
		if (gameObject.isStackingPieceLegal(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd)))
		{
			int old_tier = gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButton));
			int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
			Color pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
			PieceType pieceType = gameObject.getBoard().findPieceTypeByIndex(view.findButtonIndex(chosenSquareButton));
			int PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButtonEnd)) + FIRST_PIECE_TIER;
			String path = view.buildPath(pieceType, pieceColor, PieceTier);
			int newPieceTier = gameObject.stack(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd));
			gameObject.move(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd), newPieceTier);
			view.stackButtonFunc(allIndexes, path);

			checkForPieceBelow(old_tier, chosenSquareButton);
			checkForCheck();
			checkForComputerTurn();
		}
	}

	public void checkForCheck()
	{
		if (gameObject.getBoard().findIfMarshelInCheck()){ endGame(gameObject.getBoard().getActivateMove().getPlayer().getColor());}
	}

	public void endGame(Color color)
	{
		System.out.println(END_GAME_MESSAGE);
		System.out.println(view.turnColorToString(color) + " Wins!");
		System.exit(EXIT_SUCCESSFULLY);
	}

	public void checkForComputerTurn()
	{
		if (gameObject.isCurrentPlayerComputer())
			gameObject.getBoard().playComputerTurn();
	}

	public void checkForPieceBelow(int old_tier, JButtonSquare chosenSquareButton)
	{
		if (old_tier != FIRST_PIECE_TIER)
		{
			Color pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
			PieceType pieceType = gameObject.getBoard().findPieceTypeByIndex(view.findButtonIndex(chosenSquareButton));
			int PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButton));
			String path = view.buildPath(pieceType, pieceColor, PieceTier);
			view.setButtonImage(chosenSquareButton, path);
		}
	}
	
	public void readyButtonFunction(JButton readyP)
	{		
		Color readyColor = view.findReadyColor(readyP);
		
		if (gameObject.getBoard().isValidPiece(readyColor) && gameObject.getBoard().isMarshelOnBoard())
		{
			gameObject.getBoard().addPlayerToReadyList();
			view.setOneReadyButtonDisabled(readyP);
			view.setChosenPieceButtonNull();
		}

		if (gameObject.getGameState() == GameState.PlayPhase && gameObject.getBoard().findCurrentPlayer().getClass() == ComputerPlayer.class)
		{
			gameObject.getBoard().playComputerTurn();
		}
		changeGameStateLabel();
		
																														
	}
	
	public void changeGameStateLabel()
	{
		if (gameObject.getGameState() == GameState.PlayPhase && view.getGameStateLabel().equals(GameState.StartPhase.name()))
			view.setGameStateLabel(GameState.PlayPhase);
	}

	public GameState getGameState()
	{
		return gameObject.getGameState();
	}

}
