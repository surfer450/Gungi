package gungi;

import java.util.Map;

public interface controllerObserver
{
    void putComputerPiece(MoveObject moveObject, String color);
    void moveComputerPiece(MoveObject moveObject, String color);
    void attackComputerPiece(MoveObject moveObject, String color);
    void stackComputerPiece(MoveObject moveObject, String color);
    void PutReadyComputer(String color);
}
