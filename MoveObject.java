package gungi;

public class MoveObject
{
    private int oldIndex;
    private int newIndex;
    private int moveType;

    private String pieceMovingName;

    public MoveObject(int oldIndex, int newIndex, int moveType, String pieceMovingName)
    {
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.moveType = moveType;
        this.pieceMovingName = pieceMovingName;
    }

    public MoveObject()
    {
        this.oldIndex = 0;
        this.newIndex = 0;
        this.moveType = 0;
        this.pieceMovingName = null;
    }

    public String getPieceMovingName() {
        return pieceMovingName;
    }

    public void setPieceMovingName(String pieceMovingName) {
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

    public int getMoveType() {
        return moveType;
    }

    public void setMoveType(int moveType) {
        this.moveType = moveType;
    }

    public void changeMove(int oldIndex, int newIndex, int moveType, String pieceMovingName)
    {
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.moveType = moveType;
        this.pieceMovingName = pieceMovingName;
    }
}
