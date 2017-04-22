/**
 * 
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author UnHou Chan
 *
 */
public class ConcreteBoardv1 implements BoardStyle {
	
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
	
	/**Initializing all Shapes that will be drawn
	 * 
	 */
	public ConcreteBoardv1(){
		/*Board*/
		boardShape = new Rectangle2D.Double(0,0,BOARD_WIDTH, BOARD_HEIGHT);
		
		/*Mancala*/
		mancalaShapesA = new Ellipse2D.Double(BOARD_WIDTH - MAN_WIDTH, 0, MAN_WIDTH, MAN_HEIGHT);
		mancalaShapesB = new Ellipse2D.Double(0,0, MAN_WIDTH, MAN_HEIGHT);
		
		/*PlayerA's pit*/
		for (int i=0;i<6;i++){
			/* formula:
			 * x = (i+1)*d
			 * y = H-d
			 * */
			pitShapes.add(new Ellipse2D.Double((i+1)*PIT_DIAMETER, BOARD_HEIGHT-PIT_DIAMETER, PIT_DIAMETER, PIT_DIAMETER));
		}
		/*PlayerB's pit*/
		for (int i=0;i<6;i++){
			/* formula:
			 * x = W - (i+2)*d
			 * y = 0
			 * */
			pitShapes.add(new Ellipse2D.Double(BOARD_WIDTH-((i+2)*PIT_DIAMETER), 0, PIT_DIAMETER, PIT_DIAMETER));
		}
		
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#drawBoard(java.awt.Graphics2D)
	 */
	@Override
	public void drawBoard(Graphics2D g2) {
		g2.setColor(new Color(0xaf8c56));
		g2.fill(boardShape);
	}

	/* (non-Javadoc)
	 * @see finalProject.BoardStyle#drawPit(int, int, java.awt.Graphics2D)
	 */
	@Override
	public void drawPit(int pitID, int stones, Graphics2D g2) {
		if (pitID<=11 && pitID >=0) {
			g2.setColor(new Color(0xff0000));
			g2.fill(pitShapes.get(pitID));
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
			temp = this.mancalaShapesA;
		if (mID == 1)
			temp = this.mancalaShapesB;
		if (temp!= null) {
			g2.setColor(new Color(0x00ffff));
			g2.fill(temp);
		}
		this.drawStone(g2, temp, stones);
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
			while (!s.contains(rx, ry)) {
				rx = random.nextInt(w - STONE_DIAMETER) + x;
				ry = random.nextInt(h - STONE_DIAMETER) + y;
			}
			Shape stoneShape = new Ellipse2D.Double(rx, ry, STONE_DIAMETER, STONE_DIAMETER);
			g2.setColor(new Color(0));
			g2.draw(stoneShape);
			g2.setColor(new Color(0xffffff));
			g2.fill(stoneShape);
		}
	}

}
