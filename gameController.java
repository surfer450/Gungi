package gungi;

import javax.swing.ImageIcon;
import javax.swing.JButton;

//מחלקה אחראית לקשר בין שני חלקי האמ וי סי
public class gameController 
{
	private Game gameObject = new Game();
	private viewClass view = new viewClass();
	public gameController()
	{
		//start view
		view.setGameController(this);
		view.createPieceButtonPart1();
		view.createSquareButtons();
		view.setVisible(true);
	}
	
	public void pieceButtonFunction(JButtonSquare chosenSquareButton, JButton button, JButton chosenPieceButton)
	{			
		if (chosenPieceButton != button)
		{
			String pieceColor = view.findStarterPieceColor(button);			
			if (gameObject.getBoard().isValidPiece(pieceColor))
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
	
	public void squareButtonFunction(JButtonSquare button, JButton chosenPieceButton, JButtonSquare chosenSquareButton)
	{
		
		if (!gameObject.getBoard().isPuttingPieceLegel())
		{
			view.clearChosenPieceButton();
			chosenPieceButton = null;
		}
		
		//putting piece at start phase
		if (gameObject.getGameState() == "start phase")
		{
			if (gameObject.getBoard().isYourSquare(view.findButtonIndex(button)) && chosenPieceButton != null && view.isButtonIconNull(button))
			{
				if (gameObject.getBoard().isMarshelOnBoard() || view.nameOfChosenPiece().equals("Marshel x1"))
				{
					if (!gameObject.getBoard().willMarshelBeInDangerAfterPuttin(view.findChosenButtonName(), view.findButtonIndex(button)/9, view.findButtonIndex(button)%9))
					{

						if (view.findChosenButtonName().equals("Pawn"))
						{
							if (!gameObject.getBoard().isTherePawnInCol(view.findButtonIndex(button)%9))
							{
								view.putPiece(button);
							}
										
						}
						else
						{
							view.putPiece(button);	
						}
					}
				}
			}
		}	
		
		else
		{
			//putting piece at play phase
			if (chosenPieceButton != null)
			{
				if (view.isButtonIconNull(button) && gameObject.getBoard().isYourSquare(view.findButtonIndex(button)))
				{
					if (!gameObject.getBoard().willMarshelBeInDangerAfterPuttin(view.findChosenButtonName(), view.findButtonIndex(button)/9, view.findButtonIndex(button)%9))
					{
						if (!gameObject.getBoard().willOpponentMarshelBeInCheckAfterPuttin(view.findChosenButtonName(), view.findButtonIndex(button)/9, view.findButtonIndex(button)%9))
						{
							if (view.findChosenButtonName().equals("Pawn"))
							{
								if (!gameObject.getBoard().isTherePawnInCol(view.findButtonIndex(button)%9))
								{
									view.putPiece(button);
								}

							}
							else
							{
								view.putPiece(button);
							}
						}

					}
				}
				
			}
			
			//moving piece at play phase
			else
			{
				
				if (chosenSquareButton == null)
				{
					if (!view.isButtonIconNull(button) && gameObject.getBoard().isValidPiece(gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(button))))
					{
						view.setChosenSquareButton(button);
						int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(button));
						view.turnListOfButtonsToBlue(allIndexes, button);
					}
				}
				
				else
				{
					if (chosenSquareButton == button)
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
			}
			
		}
	}
	
	public void putPieceOnBoard(String chosenButtonName, int index_row, int index_col)
	{
		gameObject.getBoard().putPiece(chosenButtonName, index_row, index_col);
		changeGameStateLabel();
	}
	
	public void movePiece(JButtonSquare button, JButton chosenPieceButton, JButtonSquare chosenSquareButton)
	{
		if (gameObject.getBoard().findIfMovePossible(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(button)))
		{
			//move to empty square
			if (view.isButtonIconNull(button))
			{
				if (!gameObject.getBoard().willMarshelBeInDangerAfterMoving(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(button)))
				{

					int old_tier = gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButton));
					int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
					int new_index = gameObject.getBoard().move(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(button), 1);			
					view.movePieceToAnotherSquare(allIndexes, button);
					
					String pieceColor = gameObject.getBoard().findPieceColorByIndex(new_index);
					String pieceName = gameObject.getBoard().findPieceNameByIndex(new_index);
					String path = "Project images\\" + pieceName + pieceColor + "1.png";
					view.setButtonImage(button, path);
					
					if (old_tier != 1)
					{
						pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
						pieceName = gameObject.getBoard().findPieceNameByIndex(view.findButtonIndex(chosenSquareButton));
						int PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButton));
						path = "Project images\\" + pieceName + pieceColor + PieceTier + ".png";
						view.setButtonImage(chosenSquareButton, path);
					}
					if (gameObject.getBoard().findIfMarshelInCheck()){
						System.out.println("game ends");
						System.exit(0);
					}
					

				}
					
			}
			//move piece to a non empty squre(attack or stack)
			else
			{
				view.showAttckAndStack(button);			
			}
		}
	}
	
	public void attackButtonFunction(JButtonSquare chosenSquareButton, JButtonSquare chosenSquareButtonEnd)
	{
		if ( !gameObject.getBoard().isPiecedMarshel(view.findButtonIndex(chosenSquareButtonEnd)))
		{
			if (!gameObject.getBoard().isValidPiece(gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButtonEnd))))
			{	
				if (!gameObject.getBoard().willMarshelBeInDangerAfterAttacking(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd)))
				{
					int old_tier = gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButton));
					int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
					String pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
					String pieceName = gameObject.getBoard().findPieceNameByIndex(view.findButtonIndex(chosenSquareButton));
					int PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButtonEnd));	
					String path = "Project images\\" + pieceName + pieceColor + PieceTier + ".png";				
					int newPieceTier = gameObject.getBoard().removePieceByIndex(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd));	
					gameObject.getBoard().move(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd), newPieceTier);				
					view.attackButtonFunc(allIndexes, path);
					
					if (old_tier != 1)
					{
						pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
						pieceName = gameObject.getBoard().findPieceNameByIndex(view.findButtonIndex(chosenSquareButton));
						PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButton));
						path = "Project images\\" + pieceName + pieceColor + PieceTier + ".png";
						view.setButtonImage(chosenSquareButton, path);
					}

					if (gameObject.getBoard().findIfMarshelInCheck()){
						System.out.println("game ends");
						System.exit(0);
					}
				}
			
			}
		}
	}
	


	public void stackButtonFunction(JButtonSquare chosenSquareButton, JButtonSquare chosenSquareButtonEnd)
	{
		if ( !gameObject.getBoard().isPiecedMarshel(view.findButtonIndex(chosenSquareButtonEnd)) && !gameObject.getBoard().isPieceFortress(view.findButtonIndex(chosenSquareButton)))
		{
			if (gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButtonEnd)) < 3 )
			{
				if (!gameObject.getBoard().willMarshelBeInDangerAfterMoving(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd)))
				{	
					if (!gameObject.getBoard().willMarshelBeInDangerAfterStacking(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd)))
					{
						int old_tier = gameObject.getBoard().getPieceTier(view.findButtonIndex(chosenSquareButton));
						int[] allIndexes = gameObject.getBoard().findAllPossibleMoves(view.findButtonIndex(chosenSquareButton));
						
						String pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
						String pieceName = gameObject.getBoard().findPieceNameByIndex(view.findButtonIndex(chosenSquareButton));
						int PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButtonEnd)) + 1;		
						String path = "Project images\\" + pieceName + pieceColor + PieceTier + ".png";
						int newPieceTier = gameObject.getBoard().stackPieceByIndex(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd));
						gameObject.getBoard().move(view.findButtonIndex(chosenSquareButton), view.findButtonIndex(chosenSquareButtonEnd), newPieceTier);
						view.stackButtonFunc(allIndexes, path);
						
						if (old_tier != 1)
						{
							pieceColor = gameObject.getBoard().findPieceColorByIndex(view.findButtonIndex(chosenSquareButton));
							pieceName = gameObject.getBoard().findPieceNameByIndex(view.findButtonIndex(chosenSquareButton));
							PieceTier = gameObject.getBoard().findPieceTierByIndex(view.findButtonIndex(chosenSquareButton));
							path = "Project images\\" + pieceName + pieceColor + PieceTier + ".png";
							view.setButtonImage(chosenSquareButton, path);
						}

						if (gameObject.getBoard().findIfMarshelInCheck()){
							System.out.println("game ends");
							System.exit(0);
						}
					}
				}
			}
		}
	}
	
	public void readyButtonFunction(JButton readyP)
	{		
		String readyColor = view.findReadyColor(readyP);
		
		if (gameObject.getBoard().isValidPiece(readyColor) && gameObject.getBoard().isMarshelOnBoard())
		{
			
			gameObject.getBoard().addPlayerToReadyList();
			view.setOneReadyButtonDisabled(readyP);
			view.setChosenPieceButtonNull();
		}
		
		changeGameStateLabel();
		
																														
	}
	
	public void changeGameStateLabel()
	{
		if (gameObject.getGameState() == "play phase" && view.getGameStateLabel() == "Start Phase")
			view.setGameStateLabel("play phase");
		
		
	}

}
