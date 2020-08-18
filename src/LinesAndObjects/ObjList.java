package LinesAndObjects;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

// whole class should just be one column, and add columns(this) when instance variable refers to object
public class ObjList extends JPanel{
	public ArrayList<Obj> mylist = new ArrayList<Obj>();
	public ArrayList<Integer> newMethod = new ArrayList<Integer>();					//add num of objs in main, before switching methods, to keep track of what to delete before coming back to main
	public int numObjs = 0;
	public LinkedList<Line> linesList = new LinkedList<Line>();
	
	//determine whether instance variable, and if so how deep and place column accordingly
	public ObjList() {
		GridLayout layout = new GridLayout(5,1);
		layout.setVgap(10);
		this.setLayout(layout);
	}


	//adds to next column of source obj
	public void addObj(Obj given){
		this.mylist.add(0, given);
		this.add(given, 0);
		this.revalidate();
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
	
	public String toString() {
		String ans = "";
		for(Obj a: mylist) {
			ans += a.Objname;
		}
		
		return ans;
		
	}
}
