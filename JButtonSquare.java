package gungi;

import java.util.ArrayList;

import javax.swing.JButton;

public class JButtonSquare extends JButton
{
	private int index;
	
    public JButtonSquare() 
    {
        super(null, null);
    }
    
    public JButtonSquare(int index) 
    {
        super(null, null);
        this.index = index;
    }

    public int getIndex()
    {
    	return index;
    }
	

}
