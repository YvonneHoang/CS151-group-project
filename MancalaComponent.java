import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * View class that contains mancala details, such as
 * player, stones, and paint command.
 * 
 * @author Yvonne Hoang
 *
 */
public class MancalaComponent extends JComponent implements ChangeListener
{
	char mID;
	int stones;
	BoardStyle style;
	MancalaModel model;
	
	/**
	 * Creates a mancala and attaches to model as a ChangeListener.
	 * @param mID signifies the owner
	 * precondition: 'a' or 'b'
	 * @param style style of board set by BoardPanel class
	 * @param model instance of the game
	 */
	public MancalaComponent(char mID, BoardStyle style, MancalaModel model) 
	{
		super();
		this.mID = mID;
		this.style = style;
		this.model = model;
		model.attach(this);
		this.stones = model.getMancalaValue(mID);
	}
	
	/**
	 * Draws mancala
	 * @param g provided by VM.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int id;
		if(mID == 'a')
			id = 0;
		else
			id = 1;
		style.drawMancala(id, stones, g2);
	}

	/**
	 * Updates stone count.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		this.stones = model.getMancalaValue(mID);
		repaint();
	}
}