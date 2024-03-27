package gungi.Views;
import gungi.Constants;
import gungi.Controllers.gameController;
import gungi.Enums.GameMode;
import gungi.Enums.GameState;
import gungi.Enums.PieceType;
import gungi.Models.MoveObject;
import gungi.Pieces.PiecesFactory;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import static gungi.Constants.*;


public class viewClass
{
	private JButtonPiece chosenPieceButton = null;
	private JButtonSquare chosenSquareButton = null;
	private JButtonSquare chosenSquareButtonEnd = null;
	private final JPanel boardPanel = new JPanel();
	private final JPanel piecesPanel1 = new JPanel();
	private final JPanel piecesPanel2 = new JPanel();
	private final JFrame frame = new JFrame();
	private final JButton readyP1 = new JButton();
	private final JButton readyP2 = new JButton();
	private final JButton attackButton = new JButton();
	private final JButton stackButton = new JButton();
	private final JPanel movePanel = new JPanel();
	private final JLabel gameStateLabel = new JLabel();
	private gameController gameControllerObject;
	private JButton humanButton = new JButton();
	private JButton computerButton = new JButton();
	private JButton quitButton = new JButton();

	private JButton ForfeitButton1 = new JButton();
	private JButton ForfeitButton2 = new JButton();

	public void buildButton(JButton button, String text, Color backgroundColor, Color foregroundColor, int x, int y, int w, int h, String path)
	{
		button.setText(text);
		button.setBackground(backgroundColor);
		button.setForeground(foregroundColor);
		button.setBounds(x, y, w, h);
		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.BOTTOM);
		button.setFont(new Font(BUTTON_FONT_TYPE, Font.PLAIN, BUTTON_FONT_SIZE));
		button.setBorder(null);
		button.setFocusable(false);

		if (path != null)
		{
			ImageIcon buttonImage = new ImageIcon(path);
			Image image = buttonImage.getImage();
			Image newimg = image.getScaledInstance(BUTTON_IMAGE_SIZE, BUTTON_IMAGE_SIZE, java.awt.Image.SCALE_SMOOTH);
			buttonImage = new ImageIcon(newimg);
			button.setIcon(buttonImage);
		}
	}

	public void BuildLabel(JLabel label, String text, Color color, Color foregroundColor, int x, int y, int w, int h, String font, int size, int depth)
	{
		label.setText(text);
		label.setBackground(color);
		label.setForeground(foregroundColor);
		label.setBounds(x, y, w, h);
		label.setFont(new Font(font, Font.PLAIN,size));
		frame.add(label,Integer.valueOf(depth));
	}


	public void setFrame()
	{
		frame.setSize(FRAME_W, FRAME_H);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(FRAME_TITLE);
		frame.setLayout(null);
	}




	public void setPanels()
	{
		//board panel
		boardPanel.setBounds(BOARD_PANEL_X, BOARD_PANEL_Y, BOARD_PANEL_SIZE, BOARD_PANEL_SIZE);
		boardPanel.setLayout(new GridLayout(BOARD_PANEL_LAYOUT_SIZE,BOARD_PANEL_LAYOUT_SIZE));
		boardPanel.setOpaque(false);

		//player 1 panel settings
		piecesPanel1.setBounds(PIECE_PANEL_X, PIECE_PANEL1_Y, PIECE_PANEL_W, PIECE_PANEL_H);
		piecesPanel1.setBackground(new Color(STOCKPILE_COLOR_CODE));

		//player 2 panel settings
		piecesPanel2.setBounds(PIECE_PANEL_X, PIECE_PANEL2_Y, PIECE_PANEL_W, PIECE_PANEL_H);
		piecesPanel2.setBackground(new Color(STOCKPILE_COLOR_CODE));

		//move panel settings
		movePanel.setBounds(MOVE_PANEL_X, MOVE_PANEL_Y, MOVE_PANEL_W, MOVE_PANEL_H);
		movePanel.setLayout(new GridLayout(MOVE_PANEL_LAYOUT_R,MOVE_PANEL_LAYOUT_C));
		movePanel.setVisible(false);
	}

	public void setReadyButtons()
	{
		buildButton(readyP1, READY_BUTTON_TEXT, new Color(READY_BUTTON_COLOR_CODE), Color.WHITE, READY_BUTTON_X, READY_BUTTON_Y_1, READY_BUTTON_W, READY_BUTTON_H, null);
		readyP1.addActionListener(e ->gameControllerObject.readyButtonFunction(readyP1));
		readyP1.setFont(new Font(READY_BUTTON_FONT, Font.PLAIN,READY_BUTTON_SIZE));

		buildButton(readyP2, READY_BUTTON_TEXT, new Color(READY_BUTTON_COLOR_CODE), Color.WHITE, READY_BUTTON_X, READY_BUTTON_Y_2, READY_BUTTON_W, READY_BUTTON_H, null);
		readyP2.addActionListener(e ->gameControllerObject.readyButtonFunction(readyP2));
		readyP2.setFont(new Font(READY_BUTTON_FONT, Font.PLAIN,READY_BUTTON_SIZE));
	}

	public void setForfietButtons()
	{
		buildButton(ForfeitButton1, FORFEIT_BUTTON_TEXT, new Color(FORFEIT_BUTTON_COLOR), Color.WHITE,  FORFEIT_BUTTON_X, FORFEIT_BUTTON_Y_1, FORFEIT_BUTTON_W, FORFEIT_BUTTON_H, null);
		ForfeitButton1.addActionListener(e ->gameControllerObject.endGame(Color.WHITE));
		ForfeitButton1.setFont(new Font(FORFEIT_BUTTON_FONT, Font.PLAIN,FORFEIT_BUTTON_SIZE));

		buildButton(ForfeitButton2, FORFEIT_BUTTON_TEXT, new Color(FORFEIT_BUTTON_COLOR), Color.WHITE,  FORFEIT_BUTTON_X, FORFEIT_BUTTON_Y_2, FORFEIT_BUTTON_W, FORFEIT_BUTTON_H, null);
		ForfeitButton2.addActionListener(e ->gameControllerObject.endGame(Color.BLACK));
		ForfeitButton2.setFont(new Font(FORFEIT_BUTTON_FONT, Font.PLAIN,FORFEIT_BUTTON_SIZE));
	}

	public void setAttackAndStackButtons()
	{
		//attack button settings
		attackButton.setText(ATTACK_BUTTON_TEXT);
		attackButton.setBackground(Color.DARK_GRAY);
		attackButton.addActionListener(e ->gameControllerObject.attackButtonFunction(chosenSquareButton, chosenSquareButtonEnd));
		movePanel.add(attackButton);

		//stack button settings
		stackButton.setText(STACK_BUTTON_TEXT);
		stackButton.setBackground(Color.DARK_GRAY);
		stackButton.addActionListener(e ->gameControllerObject.stackButtonFunction(chosenSquareButton, chosenSquareButtonEnd));
		movePanel.add(stackButton);
	}

	public JLabel setBackgroundImage()
	{
		ImageIcon frameBackground = new ImageIcon("Project images\\background.png");
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(frameBackground);
		backgroundLabel.setBounds(BACKGROUND_LABEL_X, BACKGROUND_LABEL_Y, BACKGROUND_LABEL_W, BACKGROUND_LABEL_H);
		return backgroundLabel;
	}
	public void addComponentsToFrame()
	{
		//adding to frame
		JLayeredPane layeredPane = frame.getLayeredPane();
		layeredPane.add(boardPanel,Integer.valueOf(SECOND_DEPTH));
		layeredPane.add(piecesPanel1,Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(piecesPanel2,Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(readyP1,Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(readyP2,Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(ForfeitButton1, Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(ForfeitButton2, Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(gameStateLabel, Integer.valueOf(FIRST_DEPTH));
		layeredPane.add(movePanel,Integer.valueOf(THIRD_DEPTH));
		layeredPane.add(setBackgroundImage(),Integer.valueOf(FIRST_DEPTH));

	}
	public JLabel setOpening()
	{
		JLabel openingLabel = new JLabel();
		ImageIcon frameBackground = new ImageIcon("Project images\\Opening.png");
		openingLabel.setIcon(frameBackground);
		openingLabel.setBounds(BACKGROUND_LABEL_X, BACKGROUND_LABEL_Y, BACKGROUND_LABEL_W, BACKGROUND_LABEL_H);
		return openingLabel;
	}
	public viewClass()
	{
		setFrame();

		buildButton(humanButton, "", Color.BLACK, Color.WHITE, PLAYER_BUTTON_X, HUMAN_BUTTON_Y, HUMAN_BUTTON_W, PLAYER_BUTTON_H, null);
		humanButton.addActionListener(e -> setBoard(GameMode.Human));
		setSquareButtonSettings(humanButton, false, null);
		frame.add(humanButton,Integer.valueOf(SECOND_DEPTH));

		buildButton(computerButton, "", Color.BLACK, Color.WHITE, PLAYER_BUTTON_X, COMPUTER_BUTTON_Y, COMPUTER_BUTTON_W, PLAYER_BUTTON_H, null);
		computerButton.addActionListener(e -> setBoard(GameMode.Computer));
		setSquareButtonSettings(computerButton, false, null);
		frame.add(computerButton,Integer.valueOf(SECOND_DEPTH));

		buildButton(quitButton, "", Color.BLACK, Color.WHITE,  PLAYER_BUTTON_X, QUIT_BUTTON_Y, QUIT_BUTTON_W, PLAYER_BUTTON_H, null);
		quitButton.addActionListener(e -> gameControllerObject.endGame(null));
		setSquareButtonSettings(quitButton, false, null);
		frame.add(quitButton,Integer.valueOf(SECOND_DEPTH));

		frame.add(setOpening(), Integer.valueOf(FIRST_DEPTH));
	}

	public void setBoard(GameMode mode)
	{
		setPanels();
		setReadyButtons();
		setForfietButtons();
		BuildLabel(gameStateLabel, GameState.StartPhase.name(), Color.WHITE, Color.WHITE, GAME_STATE_LABEL_X, GAME_STATE_LABEL_Y, GAME_STATE_LABEL_W, GAME_STATE_LABEL_H, GAME_STATE_LABEL_FONT, GAME_STATE_LABEL_SIZE, FIRST_DEPTH);
		setAttackAndStackButtons();
		addComponentsToFrame();
		gameControllerObject.setGame(mode);
	}

	public void setGameController(gameController gameControllerObject)
	{
		this.gameControllerObject = gameControllerObject;
	}


	public String turnColorToString(Color color)
	{
		if (color == Color.BLACK)
			return BLACK;
		else if (color == Color.WHITE)
			return WHITE;
		return "None";
	}


	public String buildPath(PieceType pieceType, Color color, int tier)
	{
		String strColor = turnColorToString(color);
		return "Project images\\" + pieceType.name() + strColor + tier + ".png";
	}

	public void createPieceButtons()
	{
		createPieceButtonForPlayer(piecesPanel1, Color.BLACK);
		createPieceButtonForPlayer(piecesPanel2, Color.WHITE);
	}

	public void createPieceButtonForPlayer(JPanel piecesPanel,Color color)
	{
		PiecesFactory factory = PiecesFactory.getInstance();
		HashMap<PieceType,Integer> allPiecesToCreate = factory.getPanelPiecesRecipe();
        for (Map.Entry<PieceType, Integer> set : allPiecesToCreate.entrySet())
        {
        	String path = buildPath(set.getKey(), color, FIRST_PIECE_TIER);
 			JButtonPiece button = new JButtonPiece(set.getKey());
			button.addActionListener(e ->gameControllerObject.pieceButtonFunction(chosenSquareButton,button, chosenPieceButton));
			buildButton(button, set.getKey() + " x" + set.getValue(), new Color(STOCKPILE_COLOR_CODE), Color.WHITE, COMPONENT_DEFAULT_SIZE, COMPONENT_DEFAULT_SIZE, COMPONENT_DEFAULT_SIZE, COMPONENT_DEFAULT_SIZE, path);
			piecesPanel.add(button);
		}	
	}
	
	public void setChosenPieceButtonNull()
	{
		if ( chosenPieceButton!=null)
		{
			chosenPieceButton.setBackground(new Color(STOCKPILE_COLOR_CODE));
			chosenPieceButton = null;
		}
	}
	public Color findStarterPieceColor(JButton button)
	{
		for (Component component: piecesPanel1.getComponents())
		{
			JButton cur_button = (JButton)component;
			if (cur_button == button)
				return Color.BLACK;
		}
		return Color.WHITE;
	}
	
	public void chosePieceToPut(int[] allIndexes, JButtonPiece button)
	{	
		if (chosenPieceButton != null)
		{
			chosenPieceButton.setBackground(new Color(STOCKPILE_COLOR_CODE));
		}
		chosenPieceButton = button;	
		chosenPieceButton.setBackground(Color.BLUE);
		setChosenSquareButton(null);
		if (allIndexes!=null)
		{
			turnListOfButtonsToBlue(allIndexes, null);
		}
	}

	public void setSquareButtonSettings(JButton button, boolean flag, Color color)
	{
		button.setForeground(button.getBackground());
		button.setBackground(color);
		button.setContentAreaFilled(flag);
		button.setBorderPainted(flag);
	}
	public void createSquareButtons()
	{
		for (int i = 0; i < SIZE_OF_BOARD; i++)
		{
			for (int j = 0; j < SIZE_OF_BOARD; j++)
			{
				JButtonSquare button = new JButtonSquare(i*SIZE_OF_BOARD+j);
				setSquareButtonSettings(button, false, getSquareButtonColor());
				button.addActionListener(e ->gameControllerObject.squareButtonFunction(button, chosenPieceButton, chosenSquareButton));
				button.setFocusable(false);
				button.setBorder(BorderFactory.createEtchedBorder());
				boardPanel.add(button);
			}
		}	
	}
	
	public int findButtonIndex(JButtonSquare button)
	{
		return button.getIndex();
	}
	
	public void showAttckAndStack(JButtonSquare button)
	{
		movePanel.setVisible(true);	
		movePanel.setBounds(button.getX()-MOVE_PANEL_PADDING,button.getY()-MOVE_PANEL_PADDING, MOVE_PANEL_W, MOVE_PANEL_H);
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
	
	public void setGameStateLabel(GameState text)
	{
		gameStateLabel.setText(text.name());
		readyP1.setEnabled(false);
		readyP2.setEnabled(false);
	}
	
	public void setOneReadyButtonDisabled(JButton readyP)
	{
		readyP.setEnabled(false);
	}
	
	public Color findReadyColor(JButton readyP)
	{
		Color readyColor;
		if (readyP == readyP1)
			readyColor = Color.BLACK;
		else
			readyColor = Color.WHITE;
		return readyColor;
	}
	
	public void setVisible(boolean f)
	{
		frame.setVisible(f);
	}
	
	
	public Color getSquareButtonColor()
	{
		return null;
	}

	
	public void setChosenSquareButton(JButtonSquare button)
	{
		if (button != null)
		{
			chosenSquareButton = button;
			setSquareButtonSettings(chosenSquareButton, true, Color.BLUE);
		}
		else
		{
			if (chosenSquareButton!=null)
			{
				setSquareButtonSettings(chosenSquareButton, false, getSquareButtonColor());
				chosenSquareButton = null;
			}
		}
	}
	

	public void turnListOfButtonsToBlue(int[] allIndexes, JButtonSquare buttonMiddle)
	{
		
		for (int index: allIndexes)
		{
			Component[] components = boardPanel.getComponents();
			for (Component component: components)
			{
				JButtonSquare button = (JButtonSquare)component;
				if (button.getIndex() == index)
				{
					if (buttonMiddle != null)
					{
						setSquareButtonSettings(button, true, Color.ORANGE);
					}
					
					else
					{
						setSquareButtonSettings(button, false, getSquareButtonColor());
					}
					break;
					
					
				}
			}
		}
	}

	
	public PieceType findChosenButtonName()
	{
		return chosenPieceButton.getPieceType();
	}

	public void activateSelectingPiece(Component[] pieceButtons, MoveObject moveObject)
	{
		for (Component component : pieceButtons)
		{
			JButtonPiece currButton =(JButtonPiece) component;
			PieceType pieceName = currButton.getPieceType();
			if (pieceName == moveObject.getPieceMovingName())
			{
				gameControllerObject.pieceButtonFunction(chosenSquareButton, currButton, chosenPieceButton);
			}
		}
	}

	public void activatePlacingPiece(Component[] squareButtons, MoveObject moveObject, Color color)
	{
		int index = moveObject.getNewIndex();
		if (color == Color.WHITE && gameControllerObject.getGameState() == GameState.StartPhase) { index = AMOUNT_OF_SQUARES - index;}
		for (Component component : squareButtons)
		{
			JButtonSquare currButton =(JButtonSquare) component;
			if (currButton.getIndex() == index)
			{
				gameControllerObject.squareButtonFunction(currButton, chosenPieceButton, chosenSquareButton);
			}
		}
	}

	public void activatePut(MoveObject moveObject, Color color)
	{
		JPanel currPannel = piecesPanel1;
		if (color == Color.WHITE) { currPannel = piecesPanel2;}
		Component[] pieceButtons = currPannel.getComponents();
		activateSelectingPiece(pieceButtons, moveObject);

		Component[] squareButtons = boardPanel.getComponents();
		activatePlacingPiece(squareButtons, moveObject, color);
	}

	public void activateMove(MoveObject moveObject, Color color)
	{
		JButtonSquare oldButton = new JButtonSquare(), newButton = new JButtonSquare();
		Component[] squareButtons = boardPanel.getComponents();
		for (Component component : squareButtons)
		{
			JButtonSquare currButton =(JButtonSquare) component;
			if (currButton.getIndex() == moveObject.getOldIndex())
				oldButton = currButton;
			if (currButton.getIndex() == moveObject.getNewIndex())
				newButton = currButton;
		}
		gameControllerObject.squareButtonFunction(oldButton, chosenPieceButton, chosenSquareButton);
		gameControllerObject.squareButtonFunction(newButton, chosenPieceButton, chosenSquareButton);
	}

	public void activateAttack(MoveObject moveObject, Color color)
	{
		activateMove(moveObject, color);
		gameControllerObject.attackButtonFunction(chosenSquareButton, chosenSquareButtonEnd);
	}

	public void activateStack(MoveObject moveObject, Color color)
	{

		activateMove(moveObject, color);
		gameControllerObject.stackButtonFunction(chosenSquareButton, chosenSquareButtonEnd);
	}

	public void activateReady(Color color)
	{
		JButton currReadyBotton;
		if (color == Color.WHITE) { currReadyBotton = readyP2;}
		else { currReadyBotton = readyP1;}
		gameControllerObject.readyButtonFunction(currReadyBotton);
	}

	public void putPiece(JButtonSquare button)
	{
		//changing square image
		
		Color pieceColor = findStarterPieceColor(chosenPieceButton);
		PieceType chosenPieceType = findChosenButtonName();
		ImageIcon buttonImage = new ImageIcon(buildPath(chosenPieceType, pieceColor, FIRST_PIECE_TIER));
		button.setIcon(buttonImage);
		gameControllerObject.putPieceOnBoard(chosenPieceType, button.getIndex()/SIZE_OF_BOARD, button.getIndex()%SIZE_OF_BOARD);
		
		
		//changing piece counter
		String buttonFullName = chosenPieceButton.getText();
        char lastChar = buttonFullName.charAt(buttonFullName.length() - MINUS_INDEX);
        lastChar = (char) (lastChar - MINUS_INDEX);
        buttonFullName = buttonFullName.substring(FIRST_INDEX, buttonFullName.length() - MINUS_INDEX) + lastChar;
        chosenPieceButton.setText(buttonFullName);	

		//checking if counter of a piece is zero
		if (buttonFullName.charAt(buttonFullName.length() - MINUS_INDEX) == '0')
			chosenPieceButton.setVisible(false);			
		
		chosenPieceButton.setBackground(new Color(STOCKPILE_COLOR_CODE));
		chosenPieceButton = null;

	}

}
