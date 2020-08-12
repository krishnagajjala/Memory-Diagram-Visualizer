package MemDiagramVisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import com.sun.tools.javac.Main;

import LinesAndObjects.Line;
import LinesAndObjects.Obj;
import wordstream.WordStreamFromFile;

@SuppressWarnings("serial")
public class Visualizer extends JFrame{
	// To do:
	// Map for var, val pairs in sorted order, then remove pairs of a method when method ends, maybe create map for numVars in method
	// set of lines that have x1,y1,x2,y2 for each line, shift x1, y1, when adding and removing vars on stack
	// -add variable, add method button for stack
	// -add array, add object buttons for heap
	// -able to draw arrow from stack to heap
	
	static DrawRect rec = new DrawRect(1000,960);
	static JButton varBtn = new JButton("add Variable");
	static DefaultListModel<String> textInput = new DefaultListModel<String>();
	static DefaultListModel<String> textVar = new DefaultListModel<String>();
	static JList<String> varLines = new JList<>(textVar);
	static DefaultListModel<String> textVal = new DefaultListModel<String>();
	static JList<String> valLines = new JList<>(textVal);
	static int x1,x2,y1,y2, objCheck;
	static LinkedList<Line> linesList = new LinkedList<Line>();
	//static LinkedHashMap<String,String> varList = new LinkedHashMap<String, String>(); 		// need to change this to per method call(main as first method call)
	//static LinkedHashMap<String,String>()[] variableList = new LinkedHashMap<>()[];			// cant use arrays for maps, bc the way generics work, type specificity isnt there so classexception error will occur
    
	
	
		//visualizer interpreter methods//
	
	
	// method visualizer
	public static void methodVisualizer(String name, ArrayList<String> code) {
		
	}
	
	//obj or class visualizer
	public static void objVisualizer(String name, ArrayList<String> code, int index) {
		System.out.print(code.get(index));
	}

	public static void mainVisualizer(ArrayList<String> code, int index) {
		int openBrac = 1;
		int closeBrac = 0;
		for(int i = index;openBrac!=closeBrac;i++) {
			
			if (code.get(i).contains(" = ")) {
				addVar(code.get(i));
			}
			
			// add more component checks
			
			
			if (code.get(i).contains("}")) {
				closeBrac++;
			}
			if(code.get(i).contains("{") && i!=index) {
				openBrac++;
			}
		}
	}


	
	
			//visualizer display methods//
	
	
	public static void addVar(String a) {
		if (a.contains(" new ") || a.contains("String")) {
			addObj(a);
		} else {
			addPrim(a);
		}
	}
	
	public static void addPrim(String name) {
		name = name.trim();
		int nameStartIndex = name.indexOf(" ");
		int nameEndIndex = name.indexOf("=");
		String varName = name.substring(nameStartIndex+1, nameEndIndex);
		textVar.add(0, varName);
		int valStartIndex = name.indexOf("=");
		int valEndIndex = name.indexOf(";");
		String valName = name.substring(valStartIndex+1, valEndIndex);
		//varList.put(varName, valName);
		textVal.add(0, valName);
	}
	
	public static void addObj(String name) {
		name = name.trim();
		int nameStartIndex = name.indexOf(" ");
		int nameEndIndex = name.indexOf("=");
		String varName = name.substring(nameStartIndex+1, nameEndIndex);
		textVar.add(0, varName);
		textVal.add(0, "************");
		Point start = valLines.getLocationOnScreen();
		x1= (int) start.getX()+97;
		y1= (int) start.getY()+9+(3*17);
//		Point end = rec.getLocation();
//		x2=(int) end.getX();
//		y2=(int) end.getY();
//		x1=0;
//		y1=0;
		x2=800;
		y2=800;
		objCheck++;
		linesList.add(new Line(x1,y1,x2,y2));  
		
	}
	
	public static void addMethod() {
		
	}
	

	
	// Button controls/actions
	static ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == varBtn) {
				String test1= JOptionPane.showInputDialog("Please input mark for test 1: ");
				addPrim(test1);
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
		
		JList<String> codeLines = new JList<>(textInput);
		
		JScrollPane codeView = new JScrollPane(codeLines);
		codeView.setPreferredSize(new Dimension(400, 150));

		displayCode.add(codeView);
		content.add(displayCode,BorderLayout.WEST);


		//frame max size
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension max = toolkit.getScreenSize();
		frame.setMinimumSize(new Dimension(400,1000));
		frame.setSize(max);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		
		//get code from file and print on left, put code in arrayList
		WordStreamFromFile states = new WordStreamFromFile("C:\\Users\\KrishnaG\\eclipse-workspace\\MemDiagramVis\\src\\MemDiagramVisualizer\\Obj.txt");
		ArrayList<String> code = new ArrayList<String>();
		int count = 0;
		while(states.hasAnother()) {
			code.add(states.getLine());
			textInput.addElement(code.get(count));
			count++;
		}
		
		//vis display
		JPanel visDisplay = new JPanel();
		visDisplay.setLayout(new BorderLayout());
		//stack display
		JPanel stackDisplay = new JPanel();
		stackDisplay.setLayout(new BorderLayout());
		
		
		JScrollPane stackL = new JScrollPane(varLines);
		stackL.setPreferredSize(new Dimension(100, 900));
		stackDisplay.add(stackL, BorderLayout.WEST);
		
		
		JScrollPane stackR = new JScrollPane(valLines);
		stackR.setPreferredSize(new Dimension(100, 900));
		stackDisplay.add(stackR, BorderLayout.CENTER);
		visDisplay.add(stackDisplay, BorderLayout.WEST);
		
		varLines.setCellRenderer(Obj.getRenderer());
		valLines.setCellRenderer(Obj.getRenderer());
		
		
		//heap display
		
		DrawRect recIn = new DrawRect(1000,1000);
		visDisplay.add(rec, BorderLayout.CENTER); 
		visDisplay.add(recIn, BorderLayout.CENTER); 
		
		content.add(visDisplay,BorderLayout.CENTER);
		frame.pack();
		
		//control display
		JPanel controls = new JPanel();
		
		varBtn.addActionListener(actionListener);
		JButton methodBtn = new JButton("add Method");
		//methodBtn.addActionListener(actionListener);
		controls.setLayout(new BorderLayout());
		controls.add(methodBtn, BorderLayout.NORTH );
		controls.add(varBtn, BorderLayout.CENTER);
		visDisplay.add(controls, BorderLayout.SOUTH);
		
		System.out.println(stackL.getLocationOnScreen());
		//call visualizer for main of driver class - always first main in file as of now
		int main = textInput.indexOf("	public static void main(String[] args) {");
		mainVisualizer(code,main);
	}
	

	

	// overall paint method, needs to be changed to paintComponent method override later, but for now just overriding paint since extended JFrame
	public void paint(Graphics g){
		
		if (objCheck >0) {
			super.paint(g);						//ensures default components are drawn first
			for(Line i: linesList) {
				drawArrow(g, i);
			}
		} else {
			super.paint(g);
		}

	}
	
	// draws arrow given Line that containes x1,y1,x2,y2 int values. Draws arrowhead at the end depending on direction calculated using coordinates
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
}
