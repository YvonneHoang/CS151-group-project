/**
 * 
 */
package finalProject;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 * @author UnHou Chan
 *
 */
public interface BoardStyle {
	/**Draw the Board
	 * @param g2
	 */
	void drawBoard(Graphics2D g2);
	
	/**draw a pit
	 * @param pitID 
	 * <br>[0~5] is A1~A6
	 * <br>[6~11] is B1~B6
	 * @param stones
	 * <br># of stones in the pit
	 * @param g2
	 */
	void drawPit (int pitID, int stones, Graphics2D g2);
	
	/**draw a Mancala
	 * @param mID
	 * <br>0 is MancalaA
	 * <br>1 is MancalaB
	 * @param stones
	 * <br># of stones in the Mancala
	 * @param g2
	 */
	void drawMancala(int mID, int stones, Graphics2D g2);
	
	/**Analyze which pit has been clicked.
	 * @param p the Point
	 * @return
	 * <br>[0~5] : A1~A6
	 * <br>[6~11] : B1~B6
	 * <br> -1 : no pit selected
	 */
	int pitSelected(Point p);
	
	/**Analyze which pit has been clicked.
	 * @param x
	 * @param y
	 * @return
	 * <br>[0~5] : A1~A6
	 * <br>[6~11] : B1~B6
	 * <br> -1 : no pit selected
	 */
	int pitSelected(int x, int y);

}
