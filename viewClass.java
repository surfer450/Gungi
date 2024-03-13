package gungi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class viewClass 
{
	private JButton chosenPieceButton = null;
	private JButtonSquare chosenSquareButton = null;
	private JButtonSquare chosenSquareButtonEnd = null;
	private JPanel boradPanel = new JPanel();
	private JPanel piecesPanel1 = new JPanel();
	private JPanel piecesPanel2 = new JPanel();
	private JFrame frame = new JFrame();
	private JButton readyP1 = new JButton();
	private JButton readyP2 = new JButton();
	private	JButton attackButton = new JButton();
	private JButton stackButton = new JButton();
	private JPanel movePanel = new JPanel();
	private JLabel gameStateLabel = new JLabel();
	private gameController gameControllerObject;
	
	public viewClass()
	{
		//frame settings
		ImageIcon frameImage = new ImageIcon("Project images\\logo.png");	
		frame.setSize(1200, 800);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Gungi");	
		frame.setIconImage(frameImage.getImage());
		frame.setLayout(null);	
			
		//board panel settings
		boradPanel.setBackground(Color.RED);
		boradPanel.setBounds(39, 41, 720, 720);
		boradPanel.setLayout(new GridLayout(9,9));
	
			
		//player 1 panel settings
		piecesPanel1.setBackground(Color.WHITE);
		piecesPanel1.setBounds(790, 123, 380, 150);
			
		//player 2 panel settings
		piecesPanel2.setBackground(Color.WHITE);
		piecesPanel2.setBounds(790, 528, 380, 150);
		
		//ready buttons settings
		readyP1.setText("ready");
		readyP1.setBackground(new Color(0x111e2e));
		readyP1.setForeground(Color.WHITE);
		readyP1.setBounds(978, 289, 100, 28);
		readyP1.setBorder(null);
		readyP1.addActionListener(e ->gameControllerObject.readyButtonFunction(readyP1));
		
		readyP2.setText("ready");
		readyP2.setBackground(new Color(0x111e2e));
		readyP2.setForeground(Color.WHITE);
		readyP2.setBounds(978, 694, 100, 28);
		readyP2.setBorder(null);
		readyP2.addActionListener(e ->gameControllerObject.readyButtonFunction(readyP2));
		
		//game state label settings
		gameStateLabel.setText("Start Phase");
		gameStateLabel.setBackground(Color.WHITE);
		gameStateLabel.setBounds(330, 10, 200, 30);
		gameStateLabel.setFont(new Font("David", Font.PLAIN,30));
		
		//move panel settings
		movePanel.setBounds(100, 100, 200, 30);
		movePanel.setLayout(new GridLayout(1,2));
		movePanel.setVisible(false);
		
		//attack button settings
		attackButton.setText("Attack");
		attackButton.setBackground(Color.DARK_GRAY);
		attackButton.addActionListener(e ->gameControllerObject.attackButtonFunction(chosenSquareButton, chosenSquareButtonEnd));
		movePanel.add(attackButton);
		
		//stack button settings
		stackButton.setText("Stack");
		stackButton.setBackground(Color.DARK_GRAY);
		stackButton.addActionListener(e ->gameControllerObject.stackButtonFunction(chosenSquareButton, chosenSquareButtonEnd));
		movePanel.add(stackButton);
			

			
		ImageIcon frameBackground = new ImageIcon("Project images\\background.png");	
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(frameBackground);
		backgroundLabel.setBounds(0, 0, 1200, 800);

		
		//adding to frame 
		JLayeredPane layeredPane = frame.getLayeredPane();
	    layeredPane.add(boradPanel,Integer.valueOf(0));
	    layeredPane.add(piecesPanel1,Integer.valueOf(0));
	    layeredPane.add(piecesPanel2,Integer.valueOf(0));
	    layeredPane.add(readyP1,Integer.valueOf(0));
	    layeredPane.add(readyP2,Integer.valueOf(0));	
	    layeredPane.add(gameStateLabel,Integer.valueOf(0));
	    layeredPane.add(movePanel,Integer.valueOf(1));
	    layeredPane.add(backgroundLabel,Integer.valueOf(0));
	    

	}
	
	public void setGameController(gameController gameControllerObject)
	{
		this.gameControllerObject = gameControllerObject;
	}
	
	
	public void createPieceButtonPart1()
	{
		createPieceButtonPart2(piecesPanel1, "Black");
		createPieceButtonPart2(piecesPanel2, "White");
		
	}
	
	public void createPieceButtonPart2(JPanel piecesPanel,String color)
	{		HashMap<String,String> allPiecesToCreate = new HashMap<String,String>();

		allPiecesToCreate.put("MajorGeneral", "4");
		allPiecesToCreate.put("General", "6");
		allPiecesToCreate.put("Archer", "2");
		allPiecesToCreate.put("Knight", "2");
		allPiecesToCreate.put("Musketeer", "1");
		allPiecesToCreate.put("Captain", "1");
		allPiecesToCreate.put("Samurai", "2");
		allPiecesToCreate.put("Fortress", "2");
		allPiecesToCreate.put("Cannon", "2");
		allPiecesToCreate.put("Spy", "2");
		allPiecesToCreate.put("LieutenantGeneral", "4");
		allPiecesToCreate.put("Pawn", "9");
		allPiecesToCreate.put("Marshel", "1");

		
	
        for (Map.Entry<String, String> set : allPiecesToCreate.entrySet()) 
        {
        	
			ImageIcon buttonImage = new ImageIcon("Project images\\" + set.getKey() + color + "1.png");	
			Image image = buttonImage.getImage();
			Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);		
			buttonImage = new ImageIcon(newimg);
			
 			JButton button = new JButton();
			button.addActionListener(e ->gameControllerObject.pieceButtonFunction(chosenSquareButton,button, chosenPieceButton));
			button.setText(set.getKey() + " x" + set.getValue());
			button.setFocusable(false);
			button.setIcon(buttonImage);
			button.setHorizontalTextPosition(JButton.CENTER);
			button.setVerticalTextPosition(JButton.BOTTOM);
			button.setFont(new Font("David", Font.PLAIN,10));
			button.setBorder(null);
			button.setBorderPainted(false);
			button.setBackground(Color.WHITE);
			piecesPanel.add(button);	
			
		}	
	}
	
	public void setChosenPieceButtonNull()
	{
		if ( chosenPieceButton!=null)
		{
			chosenPieceButton.setBackground(Color.WHITE);
			chosenPieceButton = null;
		}
	}
	public String findStarterPieceColor(JButton button)
	{
		for (Component component: piecesPanel1.getComponents())
		{
			JButton cur_button = (JButton)component;
			if (cur_button == button)
				return "Black";
		}
		return "White";
	}
	
	public void chosePieceToPut(int[] allIndexes, JButton button)
	{	
		if (chosenPieceButton != null)
		{
			chosenPieceButton.setBackground(Color.WHITE);
		}
		chosenPieceButton = button;	
		chosenPieceButton.setBackground(Color.BLUE);
		setChosenSquareButton(null);
		if (allIndexes!=null)
		{
			turnListOfButtonsToBlue(allIndexes, null);
		}
		System.out.println(chosenPieceButton.getText());	
	}
	
	public String nameOfChosenPiece()
	{
		return chosenPieceButton.getText();
		
	}
	
	
	public void createSquareButtons()
	{
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				JButtonSquare button = new JButtonSquare(i*9+j);
	 			button.setForeground(button.getBackground());
	 			button.setBackground(getSquareButtonColor());
				button.addActionListener(e ->gameControllerObject.squareButtonFunction(button, chosenPieceButton, chosenSquareButton));
				button.setFocusable(false);
				button.setBorder(BorderFactory.createEtchedBorder());
				boradPanel.add(button);			
			}
		}	
	}
	
	public int findButtonIndex(JButtonSquare button)
	{
		return button.getIndex();
	}
	
	public boolean isButtonIconNull(JButtonSquare button)
	{
		return button.getIcon() == null;
	}
	
	
	public void showAttckAndStack(JButtonSquare button)
	{
		movePanel.setVisible(true);	
		movePanel.setBounds(button.getX()-20,button.getY()-20, 200, 30);
		chosenSquareButtonEnd = button;
	}
	
	public void movePieceToAnotherSquare(int[] allIndexes, JButtonSquare button)
	{
		movePanel.setVisible(false);
		button.setIcon(chosenSquareButton.getIcon());
		chosenSquareButton.setIcon(null);
		chosenSquareButtonEnd = null;	
		setChosenSquareButton(null);
		turnListOfButtonsToBlue(allIndexes, null);	
	}
	
	public void setMovePanelInvisible()
	{
		movePanel.setVisible(false);
	}
	
	public void setButtonImage(JButtonSquare button, String path)
	{
		ImageIcon buttonImage = new ImageIcon(path);
		button.setIcon(buttonImage);
	}
	
	public void attackButtonFunc(int[] allIndexes, String path)
	{
		ImageIcon buttonImage = new ImageIcon(path);
		chosenSquareButton.setIcon(buttonImage);
		movePieceToAnotherSquare(allIndexes, chosenSquareButtonEnd);		
	}

	public void stackButtonFunc(int[] allIndexes, String path)
	{
		ImageIcon buttonImage = new ImageIcon(path);
		chosenSquareButton.setIcon(buttonImage);	
		movePieceToAnotherSquare(allIndexes,chosenSquareButtonEnd);
	}
	
	public String getGameStateLabel()
	{
		return gameStateLabel.getText();
	}
	
	public void setGameStateLabel(String text)
	{
		gameStateLabel.setText(text);
		readyP1.setEnabled(false);
		readyP2.setEnabled(false);
	}
	
	public void setOneReadyButtonDisabled(JButton readyP)
	{
		readyP.setEnabled(false);
	}
	
	public String findReadyColor(JButton readyP)
	{
		String readyColor;	
		if (readyP == readyP1)
			readyColor = "Black";
		else
			readyColor = "White";
		return readyColor;
	}
	
	public void setVisible(boolean f)
	{
		frame.setVisible(f);
	}
	
	
	public Color getSquareButtonColor()
	{
		return new Color(0xf1f5b8);
	}
	
	public void setChosenSquareButtonColor(Color color)
	{
		chosenSquareButton.setBackground(color);
	}
	
	public void setChosenSquareButton(JButtonSquare button)
	{
		if (button != null)
		{
			chosenSquareButton = button;
			setChosenSquareButtonColor(Color.BLUE);
		}
		else
		{
			if (chosenSquareButton!=null)
			{
				setChosenSquareButtonColor(getSquareButtonColor());	
				chosenSquareButton = button;	
			}
		}
	}
	

	public void turnListOfButtonsToBlue(int[] allIndexes, JButtonSquare buttonMiddle)
	{
		
		for (int index: allIndexes)
		{
			Component[] components = boradPanel.getComponents();
			for (Component component: components)
			{
				JButtonSquare button = (JButtonSquare)component;
				if (button.getIndex() == index)
				{
					if (buttonMiddle != null)
					{
						button.setBackground(Color.ORANGE);
					}
					
					else
					{
						button.setBackground(getSquareButtonColor());
					}
					break;
					
					
				}
			}
		}
	}
	

	
	public void clearChosenPieceButton()
	{
		chosenPieceButton = null;
	}
	
	public String findChosenButtonName()
	{
		return chosenPieceButton.getText().split(" ", 2)[0];
	}
	
	
	public void putPiece(JButtonSquare button)
	{
		//changing square image
		
		String pieceColor = findStarterPieceColor(chosenPieceButton);
		String chosenButtonName = findChosenButtonName();
		ImageIcon buttonImage = new ImageIcon("Project images\\" + chosenButtonName + pieceColor + "1.png");
		button.setIcon(buttonImage);
		gameControllerObject.putPieceOnBoard(chosenButtonName, button.getIndex()/9, button.getIndex()%9);
		
		
		//changing piece counter
		String buttonFullName = chosenPieceButton.getText();
        char lastChar = buttonFullName.charAt(buttonFullName.length() - 1);
        lastChar = (char) (lastChar - 1);
        buttonFullName = buttonFullName.substring(0, buttonFullName.length() - 1) + lastChar;
        chosenPieceButton.setText(buttonFullName);	

		//checking if counter of a piece is zero
		if (buttonFullName.charAt(buttonFullName.length() - 1) == '0')
			chosenPieceButton.setVisible(false);			
		
		chosenPieceButton.setBackground(Color.WHITE);
		chosenPieceButton = null;
	}


}
