package gungi;

import java.util.ArrayList;


public class Square
{
    private int index;
    private ArrayList<Piece> piecesAtSquare = new ArrayList<Piece>();
    private ArrayList<Piece> piecesThreateningSquare = new ArrayList<Piece>();

    public Square(int index)
    {
        this.index = index;
    }
    public Square()
    {
        this.index = 0;
    }
    public void addPieceToSquare(Piece piece)
    {
        piecesAtSquare.add(piece);
    }

    public void addPieceThreatening(Piece piece)
    {
        piecesThreateningSquare.add(piece);
    }

}


