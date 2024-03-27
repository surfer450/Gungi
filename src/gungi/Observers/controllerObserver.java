package gungi.Observers;

import gungi.Models.MoveObject;

import java.awt.*;

public interface controllerObserver
{
    void putComputerPiece(MoveObject moveObject, Color color);
    void moveComputerPiece(MoveObject moveObject, Color color);
    void attackComputerPiece(MoveObject moveObject, Color color);
    void stackComputerPiece(MoveObject moveObject, Color color);
    void PutReadyComputer(Color color);
}
