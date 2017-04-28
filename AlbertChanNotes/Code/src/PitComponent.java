import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

/**
 * 
 */

/**
 * @author Albert
 *
 */
public class PitComponent extends JComponent{
	
	private int pitID;
	private int stones;
	private BoardStyle style;
	/**
	 * @param pitID
	 * @param stones
	 * @param style
	 */
	public PitComponent(int pitID, int stones, BoardStyle style) {
		super();
		this.pitID = pitID;
		this.stones = stones;
		this.style = style;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		style.drawPit(pitID, stones, g2);
	}


	/**
	 * stones++
	 */
	public void addStones() {
		this.stones++;
	}
	
	

}
