package MemDiagramVisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import SpecificObj.StringObj;
import heapAndObjects.Heap;
import heapAndObjects.Line;
import heapAndObjects.Obj;
import heapAndObjects.ObjList;
import wordstream.WordStreamFromFile;

@SuppressWarnings("serial")
public class Visualizer extends JFrame{
	// To do:
	//make new methods new objs on ObjList stack, turn current stack Obj into main Obj
	// Map for var, val pairs in sorted order, then remove pairs of a method when method ends, maybe create map for numVars in method, 
	
	// set of lines that have x1,y1,x2,y2 for each line, shift x1, y1, when adding and removing vars on stack
	
	// -add variable, add method button for stack
	// -add array, add object buttons for heap
	// -able to draw arrow from stack to heap
	// add specific array object, specific String obj
	
	static ArrayList<String> code = new ArrayList<String>();
	static JButton varBtn = new JButton("add Variable");
	static JButton nextBtn = new JButton("Next Line");
	static DefaultListModel<String> textInput = new DefaultListModel<String>();
	static JPanel visDisplay = new JPanel();
	static int x1,x2,y1,y2, objCheck, currHList;
	static LinkedList<Line> linesList = new LinkedList<Line>();
	static Heap heap = new Heap();
	static Obj stack = new Obj("Stack");
	//static LinkedHashMap<String,String> varList = new LinkedHashMap<String, String>(); 		// need to change this to per method call(main as first method call)
	//static LinkedHashMap<String,String>()[] variableList = new LinkedHashMap<>()[];			// cant use arrays for maps, bc the way generics work, type specificity isnt there so classexception error will occur
    static HashMap<String, Obj> linesMap = new HashMap<>();
    static ArrayList<String> keyNames = new ArrayList<>();
    static ObjList allObj = new ObjList();
    static boolean cont = false;
    static JList<String> codeLines = new JList<>(textInput);
	
		//visualizer interpreter methods//
	
	
	// method visualizer
	public static void methodVisualizer(String name) {
		
	}
	
	//obj or class visualizer
	public static void objVisualizer(int index, Obj given) {
		for(int i = index+1;!code.get(i).contains("{");i++) {
			if (code.get(i).contains(" = ")) {
				String objVar = code.get(i);
				objVar=objVar.replace("private", "");
				objVar=objVar.replace("public", "");
				objVar=objVar.replace("static", "");
				objVar=objVar.trim();
				addVar(code.get(i), given);

			}
		}
	}
	
	public void mainVisualizer(int index) {
		int openBrac = 1;
		int closeBrac = 0;
		for(int i = index;openBrac!=closeBrac;i++) {
			
			if (code.get(i).contains(" = ")) {
				addVar(code.get(i), stack);
			}
			this.repaint();
			codeLines.setSelectedIndex(i);
			while(!cont) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			// add more component checks
			
			
			if (code.get(i).contains("}")) {
				closeBrac++;
			}
			if(code.get(i).contains("{") && i!=index) {
				openBrac++;
			}
		cont = false;
		}
	}


	
	
			//visualizer display methods//
	
	
	public static void addVar(String a, Obj given) {
		a=a.replace("private", "");
		a=a.replace("public", "");
		a=a.replace("static", "");
		a = a.trim();
		
		int nameStartIndex = a.indexOf(" ");
		int nameEndIndex = a.indexOf("=");
		String varName = a.substring(nameStartIndex+1, nameEndIndex);
		varName.trim();
		boolean newVar = true;
		int oldIndex = -1;
		for(int i = 0; i<given.objTextVar.getSize();i++) {
			if (varName.equals(given.objTextVar.elementAt(i))) {
				newVar = false;
				oldIndex = i;
			}
		}
		
		if (newVar) {

			if (a.contains(" new ") || a.contains("String")) {
				addObj(a, varName, given);
			} else {
				addPrim(a, varName, given);
			}

			for(Line b: linesList) {				// move line start points down when adding var when new var
				b.y1 += 15;
			}
		} else {
			int valStartIndex = a.indexOf("=");
			int valEndIndex = a.indexOf(";");
			String valName = a.substring(valStartIndex+1, valEndIndex);
			given.objTextVal.remove(oldIndex);
			given.objTextVal.add(oldIndex, valName);
			// change value of existing var	// change value of existing var// change value of existing var// change value of existing var// // change value of existing var// change value of existing var
		}

	}
	
	public static void addPrim(String name, String varName, Obj given) {
		varName.trim();
		given.objTextVar.add(0, varName);
		int valStartIndex = name.indexOf("=");
		int valEndIndex = name.indexOf(";");
		String valName = name.substring(valStartIndex+1, valEndIndex);
		given.objTextVal.add(0, valName);
	}
	
	public static void addObj(String name, String varName, Obj given) {


		name = name.trim();
		int nameStartIndex = name.indexOf(" ");
		String className = name.substring(0, nameStartIndex);
		given.objTextVar.add(0, varName);
		given.objTextVal.add(0, "************");
		String keyName = given.Objname+","+varName;
		keyName.trim();
		Obj currObj;
		if(className.equals("String")) {
			currObj = new StringObj(className+" "+varName);
			currObj.objTextVar.add(0, name.substring(name.indexOf("=")+1,name.indexOf(";")));
		} else {
			currObj = new Obj(className+" "+varName);
		}
		
		objCheck++;
		
		
		
		currHList++;
		if (currHList > heap.mylist.size()) {
			heap.addObjList(new ObjList());
			heap.mylist.get(currHList -1).addObj(currObj);
		} else {
			heap.mylist.get(currHList -1).addObj(currObj);
		}


		
		//class info
		linesMap.put(keyName, currObj);
		keyNames.add(keyName);
		allObj.mylist.add(given);
		
		int classIndex = 0;
		for (int i=0;i<textInput.getSize();i++){
			if(textInput.get(i).contains("class "+className)) {
				classIndex = i;
			}
		}
		objVisualizer(classIndex,currObj);
			// className + varName -> currObj

		currHList--;
	}
	
	public static void addMethod() {
		
	}
	

	
	 //Button controls/actions
	static ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == varBtn) {
				String test1= JOptionPane.showInputDialog("Please input mark for test 1: ");
				addVar(test1, stack);
			} else if (e.getSource() == nextBtn) {
				cont = true;
			}

		}
	};
	
	
	
			//Main - Driver //
	
	
	public static void main(String[] args) throws FileNotFoundException {
		Visualizer frame = new Visualizer();
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		//code display left
		JPanel displayCode = new JPanel();
		displayCode.setLayout(new BorderLayout());
		
		
		
		JScrollPane codeView = new JScrollPane(codeLines);
		codeView.setPreferredSize(new Dimension(400, 150));

		displayCode.add(codeView, BorderLayout.CENTER);

		nextBtn.addActionListener(actionListener);
		displayCode.add(nextBtn, BorderLayout.SOUTH);
		content.add(displayCode,BorderLayout.WEST);


		//frame max size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension max = toolkit.getScreenSize();
		frame.setMinimumSize(new Dimension(max.width-1,max.height-1));
		//frame.setSize(max);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		
		//get code from file and print on left, put code in arrayList
		WordStreamFromFile states = new WordStreamFromFile("C:\\Users\\KrishnaG\\eclipse-workspace\\MemDiagramVis\\src\\MemDiagramVisualizer\\Obj.txt");
		
		int count = 0;
		while(states.hasAnother()) {
			code.add(states.getLine());
			textInput.addElement(code.get(count));
			count++;
		}
		
		//stack display
		
		visDisplay.setLayout(new BorderLayout(50,0));
		visDisplay.add(stack, BorderLayout.WEST);
		
		stack.objVars.setCellRenderer(Obj.getRenderer());
		stack.objVals.setCellRenderer(Obj.getRenderer());
		
		
		//heap display
		


		visDisplay.add(heap, BorderLayout.CENTER); 
		
		content.add(visDisplay,BorderLayout.CENTER);
		frame.pack();
		stack.loc = stack.getLocationOnScreen();
		//control display
		//JPanel controls = new JPanel();
		
		//varBtn.addActionListener(actionListener);
		//JButton methodBtn = new JButton("add Method");
		//methodBtn.addActionListener(actionListener);
		//controls.setLayout(new BorderLayout());
		//controls.add(methodBtn, BorderLayout.NORTH );
		//controls.add(varBtn, BorderLayout.CENTER);
		//visDisplay.add(controls, BorderLayout.SOUTH);

		
		//call visualizer for main of driver class - always first main in file as of now
		int main = textInput.indexOf("	public static void main(String[] args) {");
		currHList = 0;
		frame.mainVisualizer(main);


	}
	

	

	// overall paint method, needs to be changed to paintComponent method override later, but for now just overriding paint since extended JFrame
	public void paint(Graphics g){
		super.paint(g);						//ensures default components are drawn first

		if (objCheck >0) {
			linesList.clear();
			for(String a: keyNames) {
				Point end = linesMap.get(a).getLocationOnScreen();
				//System.out.println("end: "+linesMap.get(a).Objname);
				Point start = null;
				for(Obj b: allObj.mylist) { 
					if (b.Objname.equals(a.subSequence(0, a.indexOf(",")))) {
						int varStartIndex = a.indexOf(",");
						int varEndIndex = a.length();
						String varName = a.substring(varStartIndex+1, varEndIndex);
						int index = b.objTextVar.lastIndexOf(varName);
						start = b.getLocationOnScreen();
						start.x += b.getWidth();
						start.y += 20;
						start.y += b.objVars.getCellBounds(index, b.objVars.countComponents()).y+10;						
						linesList.add(0, new Line(start.x,start.y,end.x,end.y));
						//System.out.println("start: "+b.Objname);
						break;
					}
				}
			}
			for(Line i: linesList) {
				drawArrow(g, i);
			}
		}

	}
	
	
	private void drawArrow(Graphics g2, Line lineIn) {  		
		
		Polygon arrowHead = new Polygon();  
		
		if(Math.abs(lineIn.x2-lineIn.x1) <20) {
			if(lineIn.y2<lineIn.y1) {
				// up
				arrowHead.addPoint( lineIn.x2-10,lineIn.y2+10);
				arrowHead.addPoint( lineIn.x2+10, lineIn.y2+10);
				arrowHead.addPoint( lineIn.x2,lineIn.y2-1);
			} else {
				// down
				arrowHead.addPoint( lineIn.x2-10,lineIn.y2-10);
				arrowHead.addPoint( lineIn.x2+10, lineIn.y2-10);
				arrowHead.addPoint( lineIn.x2,lineIn.y2+1);
			}
		} else if (Math.abs(lineIn.y2-lineIn.y1)<20) {
			if(lineIn.x2<lineIn.x1) {
				// left
				arrowHead.addPoint( lineIn.x2+10,lineIn.y2-10);
				arrowHead.addPoint( lineIn.x2+10, lineIn.y2+10);
				arrowHead.addPoint( lineIn.x2-1,lineIn.y2);
			} else {
				// right
				arrowHead.addPoint( lineIn.x2-10,lineIn.y2-10);
				arrowHead.addPoint( lineIn.x2-10, lineIn.y2+10);
				arrowHead.addPoint( lineIn.x2+1,lineIn.y2);
			}
		} else if (lineIn.y2<lineIn.y1 && lineIn.x2>lineIn.x1) {
			// top right
			arrowHead.addPoint( lineIn.x2-10,lineIn.y2);
			arrowHead.addPoint( lineIn.x2, lineIn.y2+10);
			arrowHead.addPoint( lineIn.x2+1,lineIn.y2-1);
		} else if (lineIn.y2>lineIn.y1 && lineIn.x2>lineIn.x1) {
			//down right
			arrowHead.addPoint( lineIn.x2+0,lineIn.y2-12);
			arrowHead.addPoint( lineIn.x2-10, lineIn.y2);
			arrowHead.addPoint( lineIn.x2+1,lineIn.y2+1);
		} else if (lineIn.y2<lineIn.y1 && lineIn.x2<lineIn.x1) {
			//top left
			arrowHead.addPoint( lineIn.x2+10,lineIn.y2);
			arrowHead.addPoint( lineIn.x2, lineIn.y2+10);
			arrowHead.addPoint( lineIn.x2-1,lineIn.y2-1);
		} else {
			//down left
			arrowHead.addPoint( lineIn.x2+10,lineIn.y2);
			arrowHead.addPoint( lineIn.x2, lineIn.y2-10);
			arrowHead.addPoint( lineIn.x2-1,lineIn.y2+1);
		}
		
		Graphics2D g = (Graphics2D) g2.create();
		g.drawLine(lineIn.x1, lineIn.y1, lineIn.x2, lineIn.y2);
		g.fill(arrowHead);
		g.dispose();
	}
	
	
	// old way to draw line:
//	for(Line b: heap.mylist.get(heap.mylist.size()!=currHList?currHList:currHList-1).linesList) {				// move line start points down when adding var when new var
//	b.y1 += 15;
//}
	//currObj.loc.setLocation(currObj.getLocationOnScreen().getX(), currObj.getLocationOnScreen().getY());
//	currObj.loc= currObj.getLocationOnScreen();
//	System.out.println(currObj.loc);
	//arrow details
//	Point start = given.loc;
//	x1= (int) start.getX()+420;				//use width and height of obj, add instance variable to obj class
//	y1= (int) start.getY()-80;
//	Point end = currObj.loc;
//
//	x2=(int) end.getX();
//	y2=(int) end.getY();
	
//	for(Line a: heap.mylist.get(currHList -1).linesList) {		//shift line end points down when adding obj, heap.mylist.get(currHList -1).mylist.get(0)
//			a.y2 += currObj.getHeight()+10;
//			if(heap.mylist.size()>currHList) {
//				for(Line b: heap.mylist.get(currHList).linesList) {
//					b.y1 +=150;
//				}
//			}
//	}
//	heap.mylist.get(currHList -1).linesList.add(new Line(x1,y1,x2,y2));
//	for(Line i: heap.mylist.get(0).linesList) {
//	i.y1 += 90;
//	i.x1 -= 278;
	// draws arrow given Line that containes x1,y1,x2,y2 int values. Draws arrowhead at the end depending on direction calculated using coordinates
//	for(int index=0;index<heap.mylist.size();index++) {
//	for(Line i: heap.mylist.get(index).linesList) {
//		drawArrow(g, i);
//	}
//}
//}
	
	
	
}
