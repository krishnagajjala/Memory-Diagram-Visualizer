package LinesAndObjects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import MemDiagramVisualizer.Visualizer;

public class Obj extends JPanel {
	public DefaultListModel<String> objTextVar = new DefaultListModel<String>();
	public JList<String> objVars = new JList<>(objTextVar);
	public DefaultListModel<String> objTextVal = new DefaultListModel<String>();
	public JList<String> objVals = new JList<>(objTextVal);
	public String Objname;
	static int x1,x2,y1,y2, objCheck;
	static LinkedList<Line> linesList = new LinkedList<Line>();
	public Point loc;
	
	
	public Obj(String name) {
		JScrollPane stackL = new JScrollPane(objVars);
		stackL.setPreferredSize(new Dimension(75, 75));
		JScrollPane stackR = new JScrollPane(objVals);
		stackR.setPreferredSize(new Dimension(75, 75));
		this.setLayout(new BorderLayout());
		JPanel boxes = new JPanel();
		boxes.setLayout(new GridLayout(1,2));
		boxes.add(stackL);
		boxes.add(stackR);
		this.add(boxes, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(150,75));
		Objname = name;
		this.add(new JLabel(Objname), BorderLayout.NORTH);
		objVars.setCellRenderer(getRenderer());
		objVals.setCellRenderer(getRenderer());
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		Obj o1 = new Obj("String");
		content.add(o1, BorderLayout.CENTER);	
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension max = toolkit.getScreenSize();
		frame.setMinimumSize(new Dimension(10,5));
		frame.setSize(max);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		o1.objTextVar.add(0, "a");
		o1.objTextVar.add(0, "d");
		o1.objTextVal.add(0, "b");
		o1.objTextVal.add(1, "c");
		frame.setVisible(true);
		
		for(int i =0; i<=o1.objVars.countComponents();i++) {
			int index = o1.objTextVar.lastIndexOf("a");
			System.out.println(o1.objVars.getCellBounds(index, o1.objVars.countComponents()));
		}
		
		
	}
	/**
	public void addVar(String a) {
		a = a.trim();
		int nameStartIndex = a.indexOf(" ");
		int nameEndIndex = a.indexOf("=");
		String varName = a.substring(nameStartIndex+1, nameEndIndex);
		varName.trim();
		boolean newVar = true;
		int oldIndex = -1;
		for(int i = 0; i<objTextVar.getSize();i++) {
			if (varName.equals(objTextVar.elementAt(i))) {
				newVar = false;
				oldIndex = i;
			}
		}
		
		if (newVar) {
			for(Line b: linesList) {				// move line start points down when adding var when new var
				b.y1 += 17;
			}
			if (a.contains(" new ") || a.contains("String")) {
				addObj(a, varName);
			} else {
				addPrim(a, varName);
			}
		} else {
			int valStartIndex = a.indexOf("=");
			int valEndIndex = a.indexOf(";");
			String valName = a.substring(valStartIndex+1, valEndIndex);
			objTextVal.remove(oldIndex);
			objTextVal.add(oldIndex, valName);
			// change value of existing var	// change value of existing var// change value of existing var// change value of existing var// // change value of existing var// change value of existing var
		}

	}
	
	public void addPrim(String name, String varName) {
		varName.trim();
		objTextVar.add(0, varName);
		int valStartIndex = name.indexOf("=");
		int valEndIndex = name.indexOf(";");
		String valName = name.substring(valStartIndex+1, valEndIndex);
		//varList.put(varName, valName);
		objTextVal.add(0, valName);
	}
	
	public void addObj(String name, String varName) {
		// check list of objLists if curr Index + 1 exists
		// if no, then create new objList, add to heap, then add obj to that objList
		
		
		name = name.trim();
		int nameStartIndex = name.indexOf(" ");
		String className = name.substring(0, nameStartIndex);
		objTextVar.add(0, varName);
		objTextVal.add(0, "************");
		Obj currObj = new Obj(className+" "+varName);
		heapList.addObj(currObj);	
		
		//arrow details
		Point start = valLines.getLocationOnScreen();
		x1= (int) start.getX()+97;
		y1= (int) start.getY()+9;
		Point end = heapList.mylist.get(0).getLocationOnScreen();
		x2=(int) end.getX();
		y2=(int) end.getY();
		objCheck++;
		for(Line a: linesList) {		//shift line end points down when adding obj
			a.y2 += 200;
		}
		linesList.add(new Line(x1,y1,x2,y2));
		
		//class info
		

		int classIndex = 0;
		for (int i=0;i<textInput.getSize();i++){
			if(textInput.get(i).contains("class "+className)) {
				classIndex = i;
			}
		}
		objVisualizer(classIndex,currObj);
		
	}
	**/
	
	// adds border around cells in scrollpane 
	public static ListCellRenderer<? super String> getRenderer() {
        return new DefaultListCellRenderer(){
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,Color.BLACK));
                return listCellRendererComponent;
            }
        };
    }
	
	
	
}
