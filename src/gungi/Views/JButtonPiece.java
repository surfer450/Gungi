package gungi.Views;

import gungi.Enums.PieceType;

import javax.swing.*;

public class JButtonPiece extends JButton
{
    private PieceType pieceType;

    public JButtonPiece()
    {
        super(null, null);
    }

    public JButtonPiece(PieceType pieceType)
    {
        super(null, null);
        this.pieceType = pieceType;
    }

    public PieceType getPieceType()
    {
        return pieceType;
    }


}
