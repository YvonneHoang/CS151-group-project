import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UndoButton extends JButton implements ChangeListener
{
	private MancalaModel model;
	
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
