package gungi.Models;
import gungi.Enums.PieceType;
import gungi.Models.Player;

import static gungi.Constants.*;

//מחלקה אחראית על שמירת נתונים על כל מהלך
public class ActivateMove
{
    private Player lastPlayer;
    private MoveObject moveObject;

    private Board board;

    public void setBoard(Board board)
    {
        this.board = board;
    }


    public void ChangeTurn(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        if (changeTurn) {
            this.lastPlayer = lastPlayer;
            this.moveObject = moveObject;
        }
    }


    public void put(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        int index = moveObject.getNewIndex();
        lastPlayer.createPlayerPiece(moveObject.getPieceMovingName(), index / SIZE_OF_BOARD, index % SIZE_OF_BOARD);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }

    public int move(Player lastPlayer, MoveObject moveObject, boolean changeTurn, int tier)
    {
        Piece piece = board.findPieceOnBoardByIndex(moveObject.getOldIndex());
        int row = moveObject.getNewIndex() / SIZE_OF_BOARD;
        int col = moveObject.getNewIndex() % SIZE_OF_BOARD;
        piece.move(row, col);
        piece.setTier(tier);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
        return moveObject.getNewIndex();
    }

    public int attack(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        Piece piece_attacking = board.findPieceOnBoardByIndex(moveObject.getOldIndex());
        Piece piece_attacked = board.findPieceOnBoardByIndex(moveObject.getNewIndex());
        Player prevPlayer = this.lastPlayer;
        if (piece_attacked != null && piece_attacking != null)
        {
            int key = prevPlayer.findPieceKeyByIndex(moveObject.getNewIndex());
            prevPlayer.getPlayerPieces().remove(key);
            prevPlayer.setNumOfActivePieces(prevPlayer.getNumOfActivePieces() - ActivePiece);
            ChangeTurn(lastPlayer, moveObject, changeTurn);
            return piece_attacked.getTier();
        }
        return NOT_FOUND;
    }

    public int stack(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        Piece piece_stacking = board.findPieceOnBoardByIndex(moveObject.getOldIndex());
        Piece piece_stacked = board.findPieceOnBoardByIndex(moveObject.getNewIndex());

        if (piece_stacking != null)
        {
            ChangeTurn(lastPlayer, moveObject, changeTurn);
            return (piece_stacked.getTier() + FIRST_PIECE_TIER);
        }

        return NOT_FOUND;
    }

    public void demoFwPut(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        put(lastPlayer, moveObject, changeTurn);
    }

    public void demoBwPut(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        lastPlayer.getPlayerPieces().remove(lastPlayer.getNumOfActivePieces());
        lastPlayer.setNumOfActivePieces(lastPlayer.getNumOfActivePieces() - ActivePiece);
        lastPlayer.getPlayerUnusedPieces().put(moveObject.getPieceMovingName(), lastPlayer.getPlayerUnusedPieces().get(moveObject.getPieceMovingName())+ActivePiece);
        lastPlayer.setNumOfUnusedPiece(lastPlayer.getNumOfUnusedPieces()+ActivePiece);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }

    public void demoFwMove(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        Piece piece = board.findPieceOnBoardByIndex(moveObject.getOldIndex());
        piece.move(moveObject.getNewIndex() / SIZE_OF_BOARD, moveObject.getNewIndex() % SIZE_OF_BOARD);
        piece.setTier(FIRST_PIECE_TIER);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }

    public void demoBwMove(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        Piece piece = board.findPieceOnBoardByIndex(moveObject.getNewIndex());
        piece.setTier(board.findPieceTierByIndex(moveObject.getOldIndex())+1);
        piece.move(moveObject.getOldIndex() / SIZE_OF_BOARD, moveObject.getOldIndex() % SIZE_OF_BOARD);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }


    public Piece demoFwAttack(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        Piece attackingPiece = board.findPieceOnBoardByIndex(moveObject.getOldIndex());
        Piece attackedPiece = board.findPieceOnBoardByIndex(moveObject.getNewIndex());
        int attackedPieceTier = attackedPiece.getTier();
        attackingPiece.setTier(attackedPieceTier);
        attackedPiece.setTier(attackedPieceTier - FIRST_PIECE_TIER);
        attackingPiece.move(moveObject.getNewIndex() / SIZE_OF_BOARD, moveObject.getNewIndex() % SIZE_OF_BOARD);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
        return attackedPiece;
    }

    public void demoBwAttack(Player lastPlayer, MoveObject moveObject, boolean changeTurn, Piece attackedPiece)
    {
        Piece attackingPiece = board.findPieceOnBoardByIndex(moveObject.getNewIndex());
        attackingPiece.setTier(board.findPieceTierByIndex(moveObject.getOldIndex())+FIRST_PIECE_TIER);
        attackingPiece.move(moveObject.getOldIndex() / SIZE_OF_BOARD, moveObject.getOldIndex() % SIZE_OF_BOARD);
        int attackedPieceTier = attackedPiece.getTier();
        attackedPiece.setTier(attackedPieceTier+FIRST_PIECE_TIER);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }


    public void demoFwStack(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        Piece stackingPiece = board.findPieceOnBoardByIndex(moveObject.getOldIndex());
        stackingPiece.setTier(board.findPieceOnBoardByIndex(moveObject.getNewIndex()).getTier() + FIRST_PIECE_TIER);
        stackingPiece.move(moveObject.getNewIndex() / SIZE_OF_BOARD, moveObject.getNewIndex() % SIZE_OF_BOARD);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }

    public void demoBwStack(Player lastPlayer, MoveObject moveObject, boolean changeTurn)
    {
        int oldTier = board.findPieceTierByIndex(moveObject.getOldIndex());
        Piece stackingPiece = board.findPieceOnBoardByIndex(moveObject.getNewIndex());
        stackingPiece.move(moveObject.getOldIndex() / SIZE_OF_BOARD, moveObject.getOldIndex() % SIZE_OF_BOARD);
        stackingPiece.setTier(oldTier+1);
        ChangeTurn(lastPlayer, moveObject, changeTurn);
    }

    public Player getPlayer()
    {
        return this.lastPlayer;
    }

}
