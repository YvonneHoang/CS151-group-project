import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * A view class implementing BoardStyle that draws the board,
 * mancala, pits, and labels using Java library shapes.
 * 
 * @author UnHou Chan
 *
 */
public class BlackBoard implements BoardStyle 
{
	
	private final static int BOARD_WIDTH = 400;	//W
	private final static int BOARD_HEIGHT= 150;	//H
	private final static int PIT_DIAMETER= 50;	//d
	private final static int MAN_WIDTH = 50;	//mW
	private final static int MAN_HEIGHT= 150;	//mH
	private final static int STONE_DIAMETER = 5;

	
	private ArrayList<Shape> pitShapes = new ArrayList<Shape>();
	private Shape mancalaShapesA;
	private Shape mancalaShapesB;
	private Shape boardShape;
	
	/**
	 * Initializes the style.
	 *
	 * Creates all shapes that will be drawn.
	 */
	public BlackBoard()
	{
		/*Board*/
		boardShape = new Rectangle2D.Double(0,0,BOARD_WIDTH, BOARD_HEIGHT);
		
		/*Mancala*/
		mancalaShapesA = new Ellipse2D.Double(BOARD_WIDTH - MAN_WIDTH, 0, MAN_WIDTH, MAN_HEIGHT);
		mancalaShapesB = new Ellipse2D.Double(0,0, MAN_WIDTH, MAN_HEIGHT);
		
		/*PlayerA's pit*/
		for (int i=0;i<6;i++)
		{
			/* formula:
			 * x = (i+1)*d
			 * y = H-d
			 * */
			pitShapes.add(new Ellipse2D.Double((i+1)*PIT_DIAMETER, BOARD_HEIGHT-PIT_DIAMETER, PIT_DIAMETER, PIT_DIAMETER));
		}
		/*PlayerB's pit*/
		for (int i=0;i<6;i++)
		{
			/* formula:
			 * x = W - (i+2)*d
			 * y = 0
			 * */
			pitShapes.add(new Ellipse2D.Double(BOARD_WIDTH-((i+2)*PIT_DIAMETER), 0, PIT_DIAMETER, PIT_DIAMETER));
		}
		
	}

	/**
	 * Draws the board
	 * @param g2 provided by Java VM
	 */
	public void drawBoard(Graphics2D g2)
	{
		g2.setColor(new Color(0));
		g2.fill(boardShape);
	}

	/**
	 * Draws a pit
	 * @param pitID label specifying position and owner
	 * precondition: [0~5] is A1~A6, [6~11] is B1~B6
	 * @param stones # of stones in the pit
	 * @param g2 provided by Java VM
	 */
	public void drawPit(int pitID, int stones, Graphics2D g2)
	{
		if (pitID<=11 && pitID >=0)
		{
			g2.setColor(new Color(0x005500));
			g2.fill(pitShapes.get(pitID));
			this.drawStone(g2, pitShapes.get(pitID), stones);
			if (pitID<6)
			{
				int x = (int)pitShapes.get(pitID).getBounds2D().getX();
				int y = (int)pitShapes.get(pitID).getBounds2D().getY();
				g2.setColor(Color.WHITE);
				g2.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
				g2.drawString("A"+String.valueOf((pitID+1)), x + 15, y);
			} 
			else 
			{
				int x = (int)pitShapes.get(pitID).getBounds2D().getX();
				int y = (int)pitShapes.get(pitID).getBounds2D().getY();
				g2.setColor(Color.WHITE);
				g2.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
				g2.drawString("B"+String.valueOf(((pitID)% 6) + 1), x + 15, y + PIT_DIAMETER + 15);
			}
			
		}
	}

	/**
	 * draws a mancala
	 * @param mID label specifying position and owner
	 * precondition: 0 is MancalaA, 1 is MancalaB
	 * @param stones # of stones in the mancala
	 * @param g2 provided by Java VM
	 */
	public void drawMancala(int mID, int stones, Graphics2D g2)
	{
		Shape temp = null;
		String ab = "";
		if (mID == 0) 
			{temp = this.mancalaShapesA; ab = "A";}
		if (mID == 1)
			{temp = this.mancalaShapesB; ab = "B";}
		if (temp!= null) 
		{
			g2.setColor(new Color(0xa3823a));
			g2.fill(temp);
			//drawString
			g2.setColor(new Color(0));
			g2.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
			g2.drawString(ab, (int)temp.getBounds2D().getX()+(int)temp.getBounds2D().getWidth()/2-15,
					MAN_HEIGHT/2);
			//drawStones
			this.drawStone(g2, temp, stones);
		}
	}

	/**
	 * Analyzes which pit has been clicked.
	 * @param p the Point object holding coordinates of mouse click.
	 * @return int values that correspond to pitID.
	 * <br>[0~5] : A1~A6
	 * <br>[6~11] : B1~B6
	 * <br> -1 : no pit selected
	 */
	public int pitSelected(Point p)
	{
		for (int i=0; i<12; i++)
		{
			if (this.pitShapes.get(i).contains(p))
				return i;
		}
		return -1;
	}

	/**
	 * Analyzes which pit has been clicked.
	 * @param x the x coordinate of the click location
	 * @param y the y coordinate of the click location
	 * @return int values that correspond to pitID.
	 * <br>[0~5] : A1~A6
	 * <br>[6~11] : B1~B6
	 * <br> -1 : no pit selected
	 */
	public int pitSelected(int x, int y)
	{
		for (int i=0; i<12; i++)
		{
			if (this.pitShapes.get(i).contains(x, y))
				return i;
		}
		return -1;
	}
	
	/**
	 * Helper method that draws the stones inside a pit.
	 * @param g2 provided by Java VM
	 * @param s either mancalaShapesA, mancalaShapesB or element of pitShapes
	 * @param stones
	 */
	private void drawStone(Graphics2D g2, Shape s, int stones)
	{
		int x = (int) s.getBounds2D().getX();
		int y = (int) s.getBounds2D().getY();
		int w = (int) s.getBounds2D().getWidth();
		int h = (int) s.getBounds2D().getHeight();
		
		Random random = new Random();
		int rx;
		int ry;
		
		for (int i=1; i <= stones; i++)
		{
			switch (i) 
			{
				case 1:
					rx = x+w/2-STONE_DIAMETER/2;
					ry = y+h/2-STONE_DIAMETER/2;
					break;  //mid
				case 2:
					rx = x+w/2-STONE_DIAMETER/2+STONE_DIAMETER;
					ry = y+h/2-STONE_DIAMETER/2;
					break;  //right
				case 3:
					rx = x+w/2-STONE_DIAMETER/2-STONE_DIAMETER;
					ry = y+h/2-STONE_DIAMETER/2; 
					break;  //left
				case 4:
					rx = x+w/2-STONE_DIAMETER/2;
					ry = y+h/2-STONE_DIAMETER/2-STONE_DIAMETER;
					break;  //up
				case 5:
					rx = x+w/2-STONE_DIAMETER/2;
					ry = y+h/2-STONE_DIAMETER/2+STONE_DIAMETER;
					break;  //down
				case 6: 
					rx = x+w/2-STONE_DIAMETER/2-STONE_DIAMETER;
					ry = y+h/2-STONE_DIAMETER/2-STONE_DIAMETER; 
					break;  //up-left
				case 7: 
					rx = x+w/2-STONE_DIAMETER/2+STONE_DIAMETER;
					ry = y+h/2-STONE_DIAMETER/2-STONE_DIAMETER;
					break; //up-right
				case 8: 
					rx = x+w/2-STONE_DIAMETER/2-STONE_DIAMETER;
					ry = y+h/2-STONE_DIAMETER/2+STONE_DIAMETER;
					break; //down-left
				case 9: 
					rx = x+w/2-STONE_DIAMETER/2+STONE_DIAMETER;
					ry = y+h/2-STONE_DIAMETER/2+STONE_DIAMETER;
					break; //down-right
				default: 
					rx = random.nextInt(w) + x;
					ry = random.nextInt(h) + y;
					while (!s.contains(new Rectangle2D.Double(rx,ry,STONE_DIAMETER,STONE_DIAMETER)))
					{
						rx = random.nextInt(w - STONE_DIAMETER) + x;
						ry = random.nextInt(h - STONE_DIAMETER) + y;
					}
			}
			Shape stoneShape = new Ellipse2D.Double(rx, ry, STONE_DIAMETER, STONE_DIAMETER);
			g2.setColor(new Color(0));
			g2.draw(stoneShape);
			g2.setColor(new Color(0xffffff));
			g2.fill(stoneShape);
		}
	}

	/**
	 * Finds width of board
	 * @return Width of the board
	 */
	public int getWidth()
	{
		return BOARD_WIDTH;
	}

	/**
	 * Finds height of board
	 * @return Height of the board
	 */
	public int getHeight()
	{
		return BOARD_HEIGHT;
	}

}