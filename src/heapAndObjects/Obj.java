package heapAndObjects;

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
