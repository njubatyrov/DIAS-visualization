package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JSlider;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.awt.Color;
import javax.swing.AbstractListModel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

public class MainView {
    
    private JFrame frame;
    
    public MainView() {
        frame = new JFrame("DIAS Visualization");
        frame.setBounds(0, 0, 600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow]"));
        
        JSlider slider = new JSlider();
        frame.getContentPane().add(slider, "cell 0 0 3 1,grow");
        
        JList list = new JList();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(new AbstractListModel() {
            String[] values = new String[] {"Circle Layout", "FR Layout", "KK Layout", "Spring Layout"};
            public int getSize() {
                return values.length;
            }
            public Object getElementAt(int index) {
                return values[index];
            }
        });
        frame.getContentPane().add(list, "cell 0 1,alignx left");
        
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, "cell 1 1");
        
        JTextArea textArea = new JTextArea();
        frame.getContentPane().add(textArea, "cell 2 1");
        
        frame.setVisible(true);
        
        
    }
    
    public static void main(String[] args) {
        MainView view = new MainView();
        
    }
}
