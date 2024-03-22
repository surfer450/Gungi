package gungi;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

//מחלקה אחראית על שמירת נתונים על מצב משחק
public class Game
{
	private Board board;
	private String gameState = "start phase";
	private ArrayList<Player> playersReadyList = new ArrayList<Player>();

	private String playerMode;

	private controllerObserver gameControllerObj;
	
	public Game(String mode, controllerObserver gameControllerObj)
	{
		if (mode.equals("Human"))
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

	public String getGameState()
	{
		return this.gameState;
	}

	public String getPlayerMode()
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
		if (gameState == "start phase")
		{
			if ((Player1.isPlayerPiecesMax() && Player2.isPlayerPiecesMax()) 
				|| (playersReadyList.size() == 2) 
				|| ((playersReadyList.size() == 1) && ((Player1.isPlayerPiecesMax() || Player2.isPlayerPiecesMax()))))
			{
				return true;
			}
		}
		return false;
	}
	
	public void changePhase(Move moveObject)
	{
		if (isStartPhaseOver())
		{
			gameState = "play phase";
			Player blackPlayer = board.findPlayerByColor("Black");
			moveObject.move(blackPlayer, "put");

		}	
	}

	public boolean isPlayerOnReady(Player player)
	{
		return !(playersReadyList.indexOf(player) == -1);		
	}


	public void putComputerPieceOnBoard(MoveObject moveObject, String color)
	{
		gameControllerObj.putComputerPiece(moveObject, color);
	}

	public void moveComputerPieceOnBoard(MoveObject moveObject, String color)
	{
		gameControllerObj.moveComputerPiece(moveObject, color);
	}

	public void attackComputerPieceOnBoard(MoveObject moveObject, String color)
	{
		gameControllerObj.attackComputerPiece(moveObject, color);
	}

	public void stackComputerPieceOnBoard(MoveObject moveObject, String color)
	{
		gameControllerObj.stackComputerPiece(moveObject, color);
	}

	public void putReadyForComputer(String color)
	{
		gameControllerObj.PutReadyComputer(color);
	}
	public boolean isCurrentPlayerComputer()
	{
		return (board.findCurrentPlayer().getClass() == ComputerPlayer.class);
	}

}
