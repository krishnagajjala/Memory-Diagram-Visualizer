package MemDiagramVisualizer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class DrawRect extends JPanel {
   private int RECT_X = 20;
   private int RECT_Y = 0;
   private int RECT_WIDTH;
   private int RECT_HEIGHT;

   
   public DrawRect(int w, int h) {
	   RECT_WIDTH = w;
	   RECT_HEIGHT = h;
   }
   
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      // draw the rectangle here
      g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
      g.setColor(Color.WHITE);
      g.fillRect(RECT_X+1, RECT_Y+1, RECT_WIDTH-1, RECT_HEIGHT-1);
   }

   @Override
   public Dimension getPreferredSize() {
      // so that our GUI is big enough
      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
   }

}