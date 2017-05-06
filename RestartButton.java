//package mancala;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RestartButton extends JButton implements ChangeListener
{
    private MancalaModel model;
    
    public RestartButton(MancalaModel model)
    {
        super("Restart");
        this.model = model;
        model.attach(this);
        this.addActionListener(new ActionListener()
        {
            //button clicked --> call to model reset
            public void actionPerformed(ActionEvent e) 
            {
                    model.reset();
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
