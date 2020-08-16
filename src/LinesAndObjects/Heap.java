	package LinesAndObjects;

	import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Heap extends JPanel {
	// whole class should just be one column, and add columns(this) when instance variable refers to object
		public ArrayList<ObjList> mylist = new ArrayList<ObjList>();
		public ArrayList<Integer> newMethod = new ArrayList<Integer>();					//add num of objs in main, before switching methods, to keep track of what to delete before coming back to main
		public int numObjs = 0;
		static int x1,x2,y1,y2;

		
		//determine whether instance variable, and if so how deep and place column accordingly
		public Heap() {
			GridLayout layout = new GridLayout(1,5);
			layout.setHgap(10);
			this.setLayout(layout);
		}
		

		//adds to next column of source obj
		public void addObjList(ObjList given){
			this.mylist.add(given);
			this.add(given);
			numObjs++;
		}
		
		public void addMethod() {
			newMethod.add(numObjs);
			numObjs = 0;
		}

		public void removeMethod() {
			for(int i = 0; i<newMethod.get(newMethod.size()-1); i++) {		//remove all objects in method
				mylist.remove(mylist.size()-1);
			}
			newMethod.remove(newMethod.size()-1);			// remove count of objs in method
		}
		
		
		public static void main(String[] args) {
			JFrame frame = new JFrame();
			Container content = frame.getContentPane();
			content.setLayout(new BorderLayout());
			ObjList o1 = new ObjList();
			Obj ob1 = new Obj("a");
			o1.addObj(ob1);
			ObjList o2 = new ObjList();
			Obj ob2 = new Obj("b");
			o2.addObj(ob2);
			ObjList o3 = new ObjList();
			Obj ob3 = new Obj("c");
			o3.addObj(ob3);
			
			
			Obj ob4 = new Obj("d");
			
			Heap a = new Heap();
			a.addObjList(o1);
			a.mylist.get(0).addObj(ob4);
			a.addObjList(o2);
			a.addObjList(o3);

			
			frame.pack();
			content.add(a, BorderLayout.CENTER);
			content.add(new Obj("sd"), BorderLayout.WEST);
			
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension max = toolkit.getScreenSize();
			frame.setMinimumSize(new Dimension(10,5));
			frame.setSize(max);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		
			Point start = o1.getLocationOnScreen();
			x1= (int) start.getX();
			y1= (int) start.getY();
			Point end = o2.getLocationOnScreen();
			x2=(int) end.getX();
			y2=(int) end.getY();
			
			System.out.println(ob1.getLocationOnScreen());
			System.out.println(ob2.getLocationOnScreen());
			System.out.println(a.mylist.get(2).getLocationOnScreen());
		}
		
		public void paint(Graphics g){
			super.paint(g);
			g.drawLine(x1, y1, x2, y2);


		}
		public Point Offset(Obj given) {
			return given.getLocation();
		}
}
