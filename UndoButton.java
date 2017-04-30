import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controller class expressly used for calling model's undo function,
 * changing the board to a prior state.
 * 
 * @author vonnehoang
 *
 */
public class UndoButton extends JButton implements ChangeListener
{
	private MancalaModel model;
	
	/**
	 * Creates an active button labeled 'Undo'
	 * @param model instance of the mancala game
	 */
	public UndoButton(MancalaModel model)
	{
		super("Undo");
		this.model = model;
		
		this.addActionListener(new ActionListener()
		{
			//button clicked --> call to model undo
			public void actionPerformed(ActionEvent e) 
			{
					model.undo();
			}
		});
	}

	/**
	 * Changes whether the button is enabled or not,
	 * depending on values obtained from model.
	 */
	public void stateChanged(ChangeEvent e) 
	{
		//if model's undo variable changes, check their states
		//if user can undo, button is enabled
		//else, button is disabled
		if(model.undoTruth() && model.getUndoCount() <= 3)
			setEnabled(true);
		else
			setEnabled(false);
	}
}
