package gungi;

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
	private Board board = new Board(new HumanPlayer(), new HumanPlayer());
	private String gameState = "start phase";
	private ArrayList<Player> playersReadyList = new ArrayList<Player>();
	
	public Game()
	{				
		board.setGame(this);
	}
	
	public Board getBoard()
	{
		return this.board;
	}

	public String getGameState()
	{
		return this.gameState;
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
	
	

	

	
	
	
	

	

	
	
		
	
}
