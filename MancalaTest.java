/**
 * Tester class for MancalaModel and associated views
 * and controllers.
 * 
 * @author Yvonne Hoang
 */
public class MancalaTest 
{
	public static void main(String[] args)
	{
		MancalaModel m = new MancalaModel();
		BoardPanel bp = new BoardPanel(m);
		MancalaFrame f = new MancalaFrame(m, bp);
	}
}
