package LinesAndObjects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

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
	DefaultListModel<String> objTextVar = new DefaultListModel<String>();
	JList<String> objVars = new JList<>(objTextVar);
	DefaultListModel<String> objTextVal = new DefaultListModel<String>();
	JList<String> objVals = new JList<>(objTextVal);
	String Objname;
	
	
	
	public Obj(String name) {
		JScrollPane stackL = new JScrollPane(objVars);
		stackL.setPreferredSize(new Dimension(75, 75));
		JScrollPane stackR = new JScrollPane(objVals);
		stackR.setPreferredSize(new Dimension(75, 75));
		this.setLayout(new BorderLayout());
		this.add(stackL, BorderLayout.WEST);
		this.add(stackR, BorderLayout.EAST);
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
		o1.objTextVar.add(0, "testvar");
		o1.objTextVal.add(0, "testval");
		frame.setVisible(true);
		
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
