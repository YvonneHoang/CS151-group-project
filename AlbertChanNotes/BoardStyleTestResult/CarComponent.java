import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
   A component that shows a car.
*/
public class CarComponent extends JComponent
{
   public CarComponent()
   {
      car = new CarShape(20, 20, 50);
      addMouseListener(new MousePressedListener());
      addMouseMotionListener(new MouseDraggedListener());
      for (int i = 0; i < this.getSize().getWidth(); i++)
    	  for (int j = 0; j < this.getSize().getHeight(); j++) {
    		  if (this.contains(i, j))
        		  System.out.printf("%d,%d: True\n", i,j);
    		  else
    			  System.out.printf("%d,%d: False\n", i,j);
    	  }
      
      System.out.printf("%f,%f\n", this.getSize().getWidth(), this.getSize().getHeight());
      System.out.println(this.contains(11, 11));
      System.out.println("hi");
   }
   
   public CarComponent getReference() {
	   return this;
   }
   
   private class MousePressedListener extends MouseAdapter
   {
      public void mousePressed(MouseEvent event)
      {
    	  super.mouseClicked(event);
    	  System.out.println("clicked!");
         mousePoint = event.getPoint();
         System.out.printf("Component range from: (%f,%f) ; (%f,%f)",
					getReference().getLocation().getX(),getReference().getLocation().getY(),
					getReference().getLocation().getX()+getReference().getSize().getWidth(),
					getReference().getLocation().getY()+getReference().getSize().getHeight());
         //if (!car.contains(mousePoint)) mousePoint = null;
         if (!getReference().contains(mousePoint)) mousePoint = null;
      }
   }

   private class MouseDraggedListener extends MouseMotionAdapter
   {
      public void mouseDragged(MouseEvent event)
      {
         if (mousePoint == null) return;
         Point lastMousePoint = mousePoint;
         //System.out.printf("(%f,%f);", lastMousePoint.getX(),lastMousePoint.getY());
         
         mousePoint = event.getPoint();
         //System.out.printf("(%f,%f)\n", mousePoint.getX(),mousePoint.getY());
         double dx = mousePoint.getX() - lastMousePoint.getX();
         double dy = mousePoint.getY() - lastMousePoint.getY();
         car.translate((int) dx, (int) dy);
         repaint();
      }
   }
   
   public void paintComponent(Graphics g)
   {
      Graphics2D g2 = (Graphics2D) g;
      car.draw(g2);
   }

   private CarShape car;
   /**
 * @return the car
 */
public CarShape getCar() {
	return car;
}

/**
 * @param car the car to set
 */
public void setCar(CarShape car) {
	this.car = car;
}

/**
 * @return the mousePoint
 */
public Point getMousePoint() {
	return mousePoint;
}

/**
 * @param mousePoint the mousePoint to set
 */
public void setMousePoint(Point mousePoint) {
	this.mousePoint = mousePoint;
}

private Point mousePoint;
}                               
