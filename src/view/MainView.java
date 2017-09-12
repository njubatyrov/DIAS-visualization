package view;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import graphModel.GraphView;
import model.NodeInfoCollection;
import net.miginfocom.swing.MigLayout;

public class MainView {
    
    private JFrame frame;
    private GraphView gView;
    private NodeInfoCollection collection;
    
    public MainView() {
        frame = new JFrame("DIAS Visualization");
        frame.setBounds(0, 0, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow]"));
        
        collection = new NodeInfoCollection("web/");
        gView = new GraphView("CircleLayout", collection);
        
        JSlider slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(collection.getRunDuration());
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                gView.updateGraph((int)((JSlider)e.getSource()).getValue());
            }
        });
        frame.getContentPane().add(slider, "cell 0 0 3 1,grow");
        
        JPanel panel = new JPanel();
        panel.add(gView.getView());
        frame.getContentPane().add(panel, "cell 1 1");
        
        JTextArea textArea = new JTextArea();
        frame.getContentPane().add(textArea, "cell 2 1");
        
        frame.setVisible(true);
    }    
    
    public static void main(String[] args) {
        MainView view = new MainView();
        
    }
}
