import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.event.*;

public class EndTurnButton extends JButton implements ChangeListener
{
	private MancalaModel model;
	
	public EndTurnButton(MancalaModel model)
	{
		super("End Turn");
		this.model = model;
		
		this.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					//when button is clicked, call is made to
					//model to end the current player's turn
					model.endTurn();
					//button is disabled at start of a new turn
					//(current player must make move before button
					//is enabled)
					setEnabled(false);
					
				}
			});
	}
	
	public void stateChanged(ChangeEvent e) 
	{
		//after player does a select pit/perform turn,
		//button is enabled
		setEnabled(true);
	}
	
}
