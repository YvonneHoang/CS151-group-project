import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * View and controller class that contains pit details
 * such as id, number of stones, and paint command.
 * 
 * @author Yvonne Hoang
 *
 */
public class PitComponent extends JComponent implements ChangeListener
{
	int pitID;
	int stones;
	BoardStyle style;
	MancalaModel model;
	
	/**
	 * Creates a pit and attaches to model as a ChangeListener.
	 * @param pitID id that signifies pit position and owner
	 * precondition: [0~5] is for player a, [6~11] is for player b
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