import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * View class that contains mancala board panel and
 * undo button.
 * 
 * @author Yvonne Hoang
 */
public class MancalaFrame extends JFrame
{
	private static final int FRAME_WIDTH = 1280;
	private static final int FRAME_HEIGHT = 720;
	
	private MancalaModel model;
	private final BoardPanel board;
	
	/**
	 * Sets BoardStyle and arranges BoardPanel and
	 * UndoButton
	 * @param model MancalaModel used by board
	 * @param board BoardPanel containing view of mancala board
	 */
	public MancalaFrame(MancalaModel model, BoardPanel board)
	{
		this.model = model;
		this.board = board;	
		
		selectBoardStyle();

		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		add(board, BorderLayout.NORTH);
		
		JPanel p = new JPanel();
		p.add(new UndoButton(model));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Displays a message window to user prompting for
	 * a board style.
	 */
	private void selectBoardStyle()
	{
		Object[] options = {"Circle Board", "Rectangle Board"};
		int style = JOptionPane.showOptionDialog(
				this, 
				new JLabel("Choose a board layout.", JLabel.CENTER),
				"Select Board Style",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				null);
		if(style == JOptionPane.YES_OPTION)
			board.setBoardStyle(new ConcreteBoardv1());
//		else if(style == JOptionPane.NO_OPTION)
//			board.setBoardStyle(new RectangleBoard());
	}
}