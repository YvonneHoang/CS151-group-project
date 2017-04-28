

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class StyleTester extends JComponent{
	
	/*!!!!!!!!!!!!!!Example of free positioning JComponent:
	 * 
	 * step 1: JPanel.setLayout(null);
	 * step 2: Insets inset = JPanel.getInsets();
	 * step 3: JComponent.setBounds(inset.left +x, inset.top +y, w, h);
	 * 
	 * */
	
	private BoardStyle boardStyle;
	private ArrayList<PitComponent> pits = new ArrayList<PitComponent>(); 
	
	public StyleTester() {
		this.setLayout(null);
		Insets inset = this.getInsets();
		
		boardStyle = new ConcreteBoardv1();
		this.setPreferredSize(new Dimension(boardStyle.getWidth(),boardStyle.getHeight()));
		
		for (int i=0;i<12;i++){
			pits.add(new PitComponent(i, i, boardStyle));
			pits.get(i).setBounds(inset.left, inset.top, boardStyle.getWidth(), boardStyle.getHeight());
			this.add(pits.get(i));
		}
		
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
				int i = boardStyle.pitSelected(p);
				if (i>=0 && i < 12) {
					pits.get(i).addStones();
					repaint();
					
				}
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
		boardStyle.drawMancala(1, 4, g2);
		boardStyle.drawMancala(0, 7, g2);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		StyleTester tester = new StyleTester();
		
		frame.add(tester);
		frame.add(new JButton("asdf"),BorderLayout.SOUTH);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
