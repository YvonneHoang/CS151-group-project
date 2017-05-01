/**
 * 
 */


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
 * @author UnHou Chan
 *
 */
public class FancyBoard implements BoardStyle {
	
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
	private Shape boardShape;
	
	/**Initializing Style. 
	 * <br>By default the Image name is "BoardV2.png" & "stoneV2.png"
	 * 
	 */
	public FancyBoard(){
		/*Mancala*/
		mancalaShapesA = new Ellipse2D.Double(MAN_X, MAN_YA, MAN_WIDTH, MAN_HEIGHT);
		mancalaShapesB = new Ellipse2D.Double(MAN_X, MAN_YB, MAN_WIDTH, MAN_HEIGHT);
		
		this.getClass().getResource(boardIMG_Path);
		
		/*PlayerA's pit*/
		for (int i=0;i<6;i++){
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYA, PIT_DIAMETER, PIT_DIAMETER));
		}
		/*PlayerB's pit*/
		for (int i=5;i>=0;i--){
			/* formula:
			 * x = W - (i+2)*d
			 * y = 0
			 * */
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYB, PIT_DIAMETER, PIT_DIAMETER));
		}
		
		/*Reading from system and Storing Image data*/
		
		try {
			this.boardIMG = ImageIO.read(this.getClass().getResource(this.boardIMG_Path));
			this.stoneIMG = ImageIO.read(this.getClass().getResource(this.stoneIMG_Path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**Initialize Style.
	 * @param boardIMG_Path the path to the board image
	 * @param stoneIMG_Path the path to the stone image
	 */
	public FancyBoard(String boardIMG_Path, String stoneIMG_Path){
		this.boardIMG_Path = boardIMG_Path;
		this.stoneIMG_Path = boardIMG_Path;
		
		/*Mancala*/
		mancalaShapesA = new Ellipse2D.Double(MAN_X, MAN_YA, MAN_WIDTH, MAN_HEIGHT);
		mancalaShapesB = new Ellipse2D.Double(MAN_X, MAN_YB, MAN_WIDTH, MAN_HEIGHT);
		
		/*PlayerA's pit*/
		for (int i=0;i<6;i++){
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYA, PIT_DIAMETER, PIT_DIAMETER));
		}
		/*PlayerB's pit*/
		for (int i=5;i>=0;i--){
			/* formula:
			 * x = W - (i+2)*d
			 * y = 0
			 * */
			pitShapes.add(new Ellipse2D.Double(PITX[i], PITYB, PIT_DIAMETER, PIT_DIAMETER));
		}
		
		/*Reading from system and Storing Image data*/
		
		try {
			this.boardIMG = ImageIO.read(this.getClass().getResource(this.boardIMG_Path));
			this.stoneIMG = ImageIO.read(this.getClass().getResource(this.stoneIMG_Path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#drawBoard(java.awt.Graphics2D)
	 */
	@Override
	public void drawBoard(Graphics2D g2) {
		g2.drawImage(boardIMG, 0, 0, null);
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#drawPit(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void drawPit(int pitID, int stones, Graphics2D g2) {
		if (pitID<=11 && pitID >=0) {
			this.drawStone(g2, pitShapes.get(pitID), stones);
		}
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#drawMancala(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void drawMancala(int mID, int stones, Graphics2D g2) {
		Shape temp = null;
		
		if (mID == 0) 
			{temp = this.mancalaShapesA;}
		if (mID == 1)
			{temp = this.mancalaShapesB;}
		if (temp!= null) {
			//drawStones
			this.drawStone(g2, temp, stones);
		}
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#pitSelected(java.awt.Point)
	 */
	@Override
	public int pitSelected(Point p) {
		for (int i=0; i<12; i++){
			if (this.pitShapes.get(i).contains(p))
				return i;
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#pitSelected(int, int)
	 */
	@Override
	public int pitSelected(int x, int y) {
		for (int i=0; i<12; i++){
			if (this.pitShapes.get(i).contains(x, y))
				return i;
		}
		return -1;
	}
	
	
	public void drawStone(Graphics2D g2, Shape s, int stones){
		int x = (int) s.getBounds2D().getX();
		int y = (int) s.getBounds2D().getY();
		int w = (int) s.getBounds2D().getWidth();
		int h = (int) s.getBounds2D().getHeight();
		
		Random random = new Random();
		
		
		for (int i=0; i < stones; i++){
			int rx = random.nextInt(w) + x;
			int ry = random.nextInt(h) + y;
			while (!s.contains(new Rectangle2D.Double(rx,ry,STONE_DIAMETER,STONE_DIAMETER))) {
				rx = random.nextInt(w - STONE_DIAMETER) + x;
				ry = random.nextInt(h - STONE_DIAMETER) + y;
			}
			g2.drawImage(stoneIMG, rx, ry, null);
		}
	}

	@Override
	public int getWidth() {
		return BOARD_WIDTH;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return BOARD_HEIGHT;
	}

}
