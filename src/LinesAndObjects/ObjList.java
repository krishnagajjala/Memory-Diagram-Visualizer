package LinesAndObjects;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

// whole class should just be one column, and add columns(this) when instance variable refers to object
public class ObjList extends JPanel{
	ArrayList<Obj> mylist = new ArrayList<Obj>();
	ArrayList<Integer> newMethod = new ArrayList<Integer>();					//add num of objs in main, before switching methods, to keep track of what to delete before coming back to main
	int numObjs = 0;

	
	//determine whether instance variable, and if so how deep and place column accordingly
	public ObjList(int col) {
		int start = (col-1)*1000/5;
		int end = col*1000/5;
		this.setLayout(new GridLayout(5,1));
		this.setLocation(608, 31);
		int count = 0;
	}
	
	
	

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		ObjList o1 = new ObjList(1);
		content.add(o1, BorderLayout.CENTER);	
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension max = toolkit.getScreenSize();
		frame.setMinimumSize(new Dimension(10,5));
		frame.setSize(max);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	

	//adds to next column of source obj
	public void addObj(Obj given, Obj source){
		this.mylist.add(given);
		this.add(given);
		numObjs++;
	}
	
	public void addMethod() {
		newMethod.add(numObjs);
		numObjs = 0;
	}

	public void removeMethod() {
		
	}

}
