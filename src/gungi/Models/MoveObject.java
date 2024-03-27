package gungi.Models;

import gungi.Enums.MoveType;
import gungi.Enums.PieceType;

import static gungi.Constants.FIRST_INDEX;

public class MoveObject
{
    private int oldIndex;
    private int newIndex;
    private MoveType moveType;

    private PieceType pieceMovingName;

    public MoveObject(int oldIndex, int newIndex, MoveType moveType, PieceType pieceMovingName)
    {
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.moveType = moveType;
        this.pieceMovingName = pieceMovingName;
    }

    public MoveObject()
    {
        this.oldIndex = FIRST_INDEX;
        this.newIndex = FIRST_INDEX;
        this.moveType = null;
        this.pieceMovingName = null;
    }

    public PieceType getPieceMovingName() {
        return pieceMovingName;
    }

    public void setPieceMovingName(PieceType pieceMovingName) {
        this.pieceMovingName = pieceMovingName;
    }
    public int getOldIndex() {
        return oldIndex;
    }

    public void setOldIndex(int oldIndex) {
        this.oldIndex = oldIndex;
    }

    public int getNewIndex() {
        return newIndex;
    }

    public void setNewIndex(int newIndex) {
        this.newIndex = newIndex;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

}
