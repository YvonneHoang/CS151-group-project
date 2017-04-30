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
	char mID; //0-5 for a, and 6-11 for b
	int stones; //get from model
	BoardStyle style;
	MancalaModel model;
	
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
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		this.stones = model.getMancalaValue(mID);
		repaint();
	}
}
