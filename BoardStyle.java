import java.awt.Graphics2D;
import java.awt.Point;

/**
 * An interface that defines the requirements for a
 * BoardStyle: a class that draws pits, mancala, and
 * board of the Mancala game. Used by views: PitComponent,
 * MancalaComponent, and BoardPanel.
 * 
 * @author UnHou Chan
 *
 */
public interface BoardStyle
{
	/**
	 * Draws the board
	 * @param g2 provided by Java VM
	 */
	void drawBoard(Graphics2D g2);
	
	/**
	 * Draws a pit
	 * @param pitID label specifying position and owner
	 * precondition: [0~5] is A1~A6, [6~11] is B1~B6
	 * @param stones # of stones in the pit
	 * @param g2 provided by Java VM
	 */
	void drawPit (int pitID, int stones, Graphics2D g2);
	
	/**
	 * draws a mancala
	 * @param mID label specifying position and owner
	 * precondition: 0 is MancalaA, 1 is MancalaB
	 * @param stones # of stones in the mancala
	 * @param g2 provided by Java VM
	 */
	void drawMancala(int mID, int stones, Graphics2D g2);
	
	/**
	 * Analyzes which pit has been clicked.
	 * @param p the Point object holding coordinates of mouse click.
	 * @return int values that correspond to pitID.
	 * <br>[0~5] : A1~A6
	 * <br>[6~11] : B1~B6
	 * <br> -1 : no pit selected
	 */
	int pitSelected(Point p);
	
	/**
	 * Analyzes which pit has been clicked.
	 * @param x the x coordinate of the click location
	 * @param y the y coordinate of the click location
	 * @return int values that correspond to pitID.
	 * <br>[0~5] : A1~A6
	 * <br>[6~11] : B1~B6
	 * <br> -1 : no pit selected
	 */
	int pitSelected(int x, int y);
	
	/**
	 * Finds width of board
	 * @return Width of the board
	 */
	int getWidth();
	
	/**
	 * Finds height of board
	 * @return Height of the board
	 */
	int getHeight();

}
