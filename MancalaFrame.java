import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * View and controller class that contains a BoardPanel and
 * UndoButton. Also sets the initial number of stones per pit.
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
	 * @param model game used by board
	 * @param board BoardPanel containing view of mancala board
	 */
	public MancalaFrame(MancalaModel model, BoardPanel board)
	{
		this.model = model;
		this.board = board;	
		
		model.setStoneCount(0);
		
		setLayout(new BorderLayout());
		setTitle("Mancala");
		
		add(board, BorderLayout.NORTH);
		
		JPanel p = new JPanel();
		p.add(new UndoButton(model));
		add(p);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		
		int stones = selectInitialStones();
		model.setStoneCount(stones);
		board.repaint();
	}
	
	/**
	 * Prompts user for a number from 1 to 4 to initially fill 
	 * the pits with that number of stones.
	 * @return initial number of stones
	 */
	public static int selectInitialStones()
	{
		int stones = 0;
		while(stones == 0 && stones < 5)
		{
			String input = JOptionPane.showInputDialog(null, "Enter the number of stones to be placed in each pit (max 4):");
			if(Character.isDigit(input.charAt(0)))
				stones = Integer.parseInt(input);
		}
		return stones;
	}
	
	

}