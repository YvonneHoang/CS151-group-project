import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * A view class implementing BoardStyle that draws the board,
 * mancala, pits, and labels using .png files.
 * 
 * @author UnHou Chan
 *
 */
public class FancyBoard implements BoardStyle 
{
	
	private final static int BOARD_WIDTH = 500;	//W
	private final static int BOARD_HEIGHT= 300;	//H
	
	private final static int PIT_DIAMETER= 50;	//d
	private final static int PITX[] = {44,116,188,261,333,405};
	private final static int PITYA  = 168;
	private final static int PITYB  = 82;
	
	private final static int MAN_WIDTH = 250;	//mW
	private final static int MAN_HEIGHT= 54;	//mH
	private final static int MAN_X = 125;
	private final static int MAN_YA= 225;
	private final static int MAN_YB= 20 ;
	
	private final static int STONE_DIAMETER = 10;
	
	private String boardIMG_Path = "BoardV2.png";
	private String stoneIMG_Path = "stoneV2.png";
	private BufferedImage boardIMG;
	private BufferedImage stoneIMG;
	
	private ArrayList<Shape> pitShapes = new ArrayList<Shape>();
	private Shape mancalaShapesA;
	private Shape mancalaShapesB;
	
	/**
	 * Initializes the style. 
	 * 
	 * By default the Image name is "BoardV2.png" & "stoneV2.png"
	 */
	public FancyBoard()
	{
		/*Mancala*/
		mancalaShapesA = new Ellipse2D.Double(MAN_X, MAN_YA, MAN_WIDTH, MAN_HEIGHT);
		mancalaShapesB = new Ellipse2D.Double(MAN_X, MAN_YB, MAN_WIDTH, MAN_HEIGHT);
		
		this.getClass().getResource(boardIMG_Path);
		
		/*PlayerA's pit*/
		for (int i=0;i<6;i++)
		{
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYA, PIT_DIAMETER, PIT_DIAMETER));
		}
		/*PlayerB's pit*/
		for (int i=5;i>=0;i--)
		{
			/* formula:
			 * x = W - (i+2)*d
			 * y = 0
			 * */
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYB, PIT_DIAMETER, PIT_DIAMETER));
		}
		
		/*Reading from system and Storing Image data*/
		
		try
		{
			this.boardIMG = ImageIO.read(this.getClass().getResource(this.boardIMG_Path));
			this.stoneIMG = ImageIO.read(this.getClass().getResource(this.stoneIMG_Path));
		} 
		catch (IOException e) 
		{
			System.out.println("Image not found.");
		}
		
	}
	
	/**
	 * Initializes the style with user specified .png files
	 * 
	 * @param boardIMG_Path the path to the board image
	 * @param stoneIMG_Path the path to the stone image
	 */
	public FancyBoard(String boardIMG_Path, String stoneIMG_Path)
	{
		this.boardIMG_Path = boardIMG_Path;
		this.stoneIMG_Path = boardIMG_Path;
		
		/*Mancala*/
		mancalaShapesA = new Ellipse2D.Double(MAN_X, MAN_YA, MAN_WIDTH, MAN_HEIGHT);
		mancalaShapesB = new Ellipse2D.Double(MAN_X, MAN_YB, MAN_WIDTH, MAN_HEIGHT);
		
		/*PlayerA's pit*/
		for (int i=0;i<6;i++)
		{
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYA, PIT_DIAMETER, PIT_DIAMETER));
		}
		/*PlayerB's pit*/
		for (int i=5;i>=0;i--)
		{
			/* formula:
			 * x = W - (i+2)*d
			 * y = 0
			 * */
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYB, PIT_DIAMETER, PIT_DIAMETER));
		}
		
		/*Reading from system and Storing Image data*/
		
		try 
		{
			this.boardIMG = ImageIO.read(this.getClass().getResource(this.boardIMG_Path));
			this.stoneIMG = ImageIO.read(this.getClass().getResource(this.stoneIMG_Path));
		} 
		catch (IOException e) 
		{
			System.out.println("Image not found.");
		}
		
	}

	/**
	 * Draws the board
	 * @param g2 provided by Java VM
	 */
	public void drawBoard(Graphics2D g2)
	{
		g2.drawImage(boardIMG, 0, 0, null);
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
			this.drawStone(g2, pitShapes.get(pitID), stones);
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
		
		if (mID == 0) 
			{temp = this.mancalaShapesA;}
		if (mID == 1)
			{temp = this.mancalaShapesB;}
		if (temp!= null) 
		{
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
		
		
		for (int i=1; i <= stones; i++)
		{
			switch (i) 
			{
				case 1: 
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2, y+h/2-STONE_DIAMETER/2, null); break; //mid
				case 2:
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2+STONE_DIAMETER, y+h/2-STONE_DIAMETER/2, null); break;  //right
				case 3:
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2-STONE_DIAMETER, y+h/2-STONE_DIAMETER/2, null); break;  //left
				case 4:
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2, y+h/2-STONE_DIAMETER/2-STONE_DIAMETER, null); break;  //up
				case 5:
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2, y+h/2-STONE_DIAMETER/2+STONE_DIAMETER, null); break;  //down
				case 6: 
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2-STONE_DIAMETER, y+h/2-STONE_DIAMETER/2-STONE_DIAMETER, null); break;  //up-left
				case 7: 
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2+STONE_DIAMETER, y+h/2-STONE_DIAMETER/2-STONE_DIAMETER, null); break; //up-right
				case 8: 
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2-STONE_DIAMETER, y+h/2-STONE_DIAMETER/2+STONE_DIAMETER, null); break; //down-left
				case 9: 
					g2.drawImage(stoneIMG, x+w/2-STONE_DIAMETER/2+STONE_DIAMETER, y+h/2-STONE_DIAMETER/2+STONE_DIAMETER, null); break; //down-right
				default: 
					int rx = random.nextInt(w) + x;
					int ry = random.nextInt(h) + y;
					while (!s.contains(new Rectangle2D.Double(rx,ry,STONE_DIAMETER,STONE_DIAMETER))) 
					{
						rx = random.nextInt(w - STONE_DIAMETER) + x;
						ry = random.nextInt(h - STONE_DIAMETER) + y;
					}
					g2.drawImage(stoneIMG, rx, ry, null);
			}
			
			
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
