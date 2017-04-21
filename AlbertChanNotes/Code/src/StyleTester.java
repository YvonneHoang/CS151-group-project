

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class StyleTester extends JComponent{
	
	private BoardStyle boardStyle;
	
	public StyleTester() {
		boardStyle = new ConcreteBoardv1();
		
		this.addMouseListener(new MouseAdapter(){

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
//				int x = e.getX();
//				int y = e.getY();
//				System.out.println(boardStyle.pitSelected(x, y));
				System.out.println(boardStyle.pitSelected(p));
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		boardStyle.drawBoard(g2);
		boardStyle.drawMancala(1, 0, g2);
		boardStyle.drawMancala(0, 0, g2);
		for (int i=0; i<12; i++){
			boardStyle.drawPit(i, 0, g2);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		StyleTester tester = new StyleTester();
		
		frame.add(tester);
		frame.setSize(420, 300);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
