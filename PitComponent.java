import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * View and 
 * @author vonnehoang
 *
 */
public class PitComponent extends JComponent implements ChangeListener
{
	int pitID; //0-5 for a, and 6-11 for b
	int stones; //get from model
	BoardStyle style;
	MancalaModel model;
	
	/**
	 * Creates a pit with an ID from which player identity can
	 * be derived.
	 * @param pitID 0 - 5 is for player a, 6 - 11 is for player b
	 * @param style style of board
	 * @param model instance of mancala game
	 */
	public PitComponent(int pitID, BoardStyle style, MancalaModel model) 
	{
		super();
		this.pitID = pitID;
		this.style = style;
		this.model = model;
		model.attach(this);
		char player;
		if(pitID < 6)
			player = 'a';
		else
			player = 'b';
		int pitNumber = pitID % 6;
		this.stones = model.getPitValue(player, pitNumber);
	}

	/**
	 * Draws pit
	 * @param g provided by VM.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		style.drawPit(pitID, stones, g2);
	}

	/**
	 * Updates stone count.
	 */
	@Override
	public void stateChanged(ChangeEvent e) 
	{
		char player;
		if(pitID < 6)
			player = 'a';
		else
			player = 'b';
		int pitNumber = pitID % 6;
		stones = model.getPitValue(player, pitNumber);
		repaint();
	}
}
