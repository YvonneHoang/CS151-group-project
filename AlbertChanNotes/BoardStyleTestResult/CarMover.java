import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
   A program that allows users to move a car with the mouse.
*/
public class CarMover
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      
      final CarComponent carComponent = new CarComponent();
      carComponent.setSize(100, 100);
//      carComponent.addMouseListener(new MouseAdapter(){
//		/* (non-Javadoc)
//		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
//		 * Experimenting JComponent.contains(Point) method.
//		 */
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			// TODO Auto-generated method stub
//			super.mouseClicked(e);
//			System.out.println("clicked!");
//			carComponent.setMousePoint(e.getPoint());
//			System.out.printf("Component range from: (%f,%f) ; (%f,%f)",
//					carComponent.getLocation().getX(),carComponent.getLocation().getY(),
//					carComponent.getLocation().getX()+carComponent.getSize().getWidth(),
//					carComponent.getLocation().getY()+carComponent.getSize().getHeight());
//			System.out.printf(" MousePoint: (%f,%f)\n",e.getPoint().getX(), e.getPoint().getY());
//			if (!carComponent.contains(e.getPoint())) {
//				System.out.println("Not Contatin!");
//				carComponent.setMousePoint(null);
//			}
//			if (!carComponent.contains(e.getPoint().x, e.getPoint().y)) {
//				System.out.println("Not Contatin!");
//				carComponent.setMousePoint(null);
//			}
//		}
//      });
      

      frame.add(carComponent, BorderLayout.CENTER);
      frame.add(new JButton("Stub1"), BorderLayout.SOUTH);
      frame.add(new JButton("Stub2"), BorderLayout.NORTH);
      frame.add(new JButton("Stub3"), BorderLayout.WEST);
      frame.add(new JButton("Stub4"), BorderLayout.EAST);
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
      frame.setVisible(true);
   }

   private static final int FRAME_WIDTH = 400;
   private static final int FRAME_HEIGHT = 400;
}


