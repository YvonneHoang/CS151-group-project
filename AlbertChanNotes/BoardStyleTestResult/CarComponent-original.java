import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

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
   }
   
   private class MousePressedListener extends MouseAdapter
   {
      public void mousePressed(MouseEvent event)
      {
         mousePoint = event.getPoint();
         if (!car.contains(mousePoint)) mousePoint = null;
      }
   }

   private class MouseDraggedListener extends MouseMotionAdapter
   {
      public void mouseDragged(MouseEvent event)
      {
         if (mousePoint == null) return;
         Point lastMousePoint = mousePoint;
         mousePoint = event.getPoint();
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
   private Point mousePoint;
}                               
