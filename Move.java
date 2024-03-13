package gungi;
//מחלקה אחראית על שמירת נתונים על כל מהלך
public class Move 
{
	private Player lastPlayer;
	private String moveType;
	
	public void move(Player lastPlayer, String moveType)
	{
		this.lastPlayer = lastPlayer;
		this.moveType = moveType;	
	}
	
	public Player getPlayer()
	{
		return this.lastPlayer;
	}

}
