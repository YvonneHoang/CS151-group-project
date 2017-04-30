import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * View and controller class that contains board, pits, and mancala.
 * 
 * @author Yvonne Hoang
 */
public class BoardPanel extends JPanel
{
	private ArrayList<PitComponent> pitsA;
	private ArrayList<PitComponent> pitsB;
	private ArrayList<MancalaComponent> mancala;
	
	private BoardStyle style;
	private MancalaModel model;
	private int selectedPit;
	
	/**
	 * Creates pits and mancala with MouseListener logic to
	 * read which pit has been selected.
	 * 
	 * @param model instance of a mancala game
	 */
	public BoardPanel(MancalaModel model)
	{
		super();
		this.model = model;
		int stones = selectInitialStones();
		model.setStoneCount(stones);
		pitsA = new ArrayList<PitComponent>();
		pitsB = new ArrayList<PitComponent>();	
		mancala = new ArrayList<MancalaComponent>();
		mancala.add(new MancalaComponent('a', style, model));
		mancala.add(new MancalaComponent('b', style, model));
		
		this.setPreferredSize(new Dimension(style.getWidth(), style.getHeight()));
		this.setLayout(null);
		
		Insets inset = this.getInsets();
		
		for(int i = 0; i < 6; i++)
		{
			pitsA.add(new PitComponent(i, style, model));
			pitsA.get(i).setBounds(inset.left, inset.top, style.getWidth(), style.getHeight());
			add(pitsA.get(i));
		}
		for(int i = 0; i < 6; i++)
		{
			pitsB.add(new PitComponent(i, style, model));
			pitsB.get(i).setBounds(inset.left, inset.top, style.getWidth(), style.getHeight());
			add(pitsB.get(i));
		}
		
		addMouseListener(new MouseAdapter()
			{
				public void mousePressed(MouseEvent e) 
				{
					//Controller logic --> if pit is selected,
					//update model
					Point p = e.getPoint();
					selectedPit = style.pitSelected(p);
					char player;
					if(selectedPit < 6)
						player = 'a';
					else
						player = 'b';
					int pitNum = selectedPit % 6;
					if(selectedPit > 0)
						model.performTurn(player, pitNum);
					if(model.checkWinner() == 'a')
						//draw victory
						;
					else if(model.checkWinner() == 'b')
						//draw victory
						;
					else
						//do nothing
						;
				}
			});		
	}
	
	/**
	 * Draws board
	 * @param g provided by VM.
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		style.drawBoard(g2);
	}
	
	/**
	 * Sets board style
	 * @param style look of board
	 */
	public void setBoardStyle(BoardStyle style)
	{
		this.style = style;
	}
	
	/**
	 * Finds board's style
	 * @return style of board
	 */
	public BoardStyle getBoardStyle()
	{
		return style;
	}
	
	/**
	 * Prompts user for a number from 1 to 4 to initially fill 
	 * the pits with that number of stones.
	 * @return initial number of stones
	 */
	private int selectInitialStones()
	{
		int stones = 0;
		while(stones == 0 && stones < 5)
		{
			String input = JOptionPane.showInputDialog(this, "Enter the number of stones to be placed in each pit (max 4):");
			if(Character.isDigit(input.charAt(0)))
				stones = Integer.parseInt(input);
		}
		return stones;
	}
}
