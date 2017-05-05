import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * View and controller class that contains board, pits, and mancala
 * views and controllers.
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
		selectBoardStyle();
		pitsA = new ArrayList<PitComponent>();
		pitsB = new ArrayList<PitComponent>();	
		mancala = new ArrayList<MancalaComponent>();
		
		this.setPreferredSize(new Dimension(style.getWidth(), style.getHeight()));
		this.setLayout(null);
		
		Insets inset = this.getInsets();
		
		mancala.add(new MancalaComponent('a', style, model));
		mancala.get(0).setBounds(inset.left, inset.top, style.getWidth(), style.getHeight());
		add(mancala.get(0));
		mancala.add(new MancalaComponent('b', style, model));
		mancala.get(1).setBounds(inset.left, inset.top, style.getWidth(), style.getHeight());
		add(mancala.get(1));
		
		for(int i = 0; i < 12; i++)
		{
			if(i < 6)
			{
				pitsA.add(new PitComponent(i, style, model));
				pitsA.get(i).setBounds(inset.left, inset.top, style.getWidth(), style.getHeight());
				add(pitsA.get(i));
			}	
			else
			{
				pitsB.add(new PitComponent(i, style, model));
				pitsB.get(i % 6).setBounds(inset.left, inset.top, style.getWidth(), style.getHeight());
				add(pitsB.get(i % 6));
			}
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
					if(selectedPit >= 0)
						model.performTurn(player, pitNum);
					if(model.checkWinner() == 'a' || model.checkWinner() == 'b')
						endGame();
					else //do nothing
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
	 * Displays a message window to user prompting for
	 * a board style.
	 */
	private void selectBoardStyle()
	{
		Object[] options = {"Fancy Board", "Black Board"};
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
			this.setBoardStyle(new FancyBoard());
		else if(style == JOptionPane.NO_OPTION)
			this.setBoardStyle(new BlackBoard());
	}
	
	/**
	 * Displays a message window declaring which player won
	 * and asking if they would like to play again.
	 */
	private void endGame()
	{
	    int option = JOptionPane.showConfirmDialog(null, "Player " + Character.toUpperCase(model.checkWinner()) + " wins!\nDo you wish to play again?", "Congratulations!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    if (option == JOptionPane.NO_OPTION)
	    {
            System.exit(0);
        }
        else 
        {
            model.reset();
        }
	}
}
