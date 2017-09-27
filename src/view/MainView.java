package view;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
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
    private JSlider slider;
    
    private JRadioButton pushRadioButton;
    private JRadioButton pullRadioButton;
    private JRadioButton pssRadioButton;
    private JList layoutsList;
    
    private final String[] layoutNames = {"Circle Layout", "KK Layout", "FR Layout", "Spring Layout"};
    
    public MainView() {
        frame = new JFrame("DIAS Visualization");
        frame.setBounds(0, 0, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new MigLayout("fill", "[][][]", "[][][][]"));
        
        collection = new NodeInfoCollection("web/");
        gView = new GraphView("CircleLayout", collection, this);
        
        pushRadioButton = new JRadioButton("Push");
        pullRadioButton = new JRadioButton("Pull");
        pssRadioButton = new JRadioButton("Pss");
        
        slider = new JSlider();
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
        
        layoutsList = new JList(layoutNames);
        layoutsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        frame.getContentPane().add(slider, "cell 0 0 3 1,grow");
        frame.getContentPane().add(layoutsList, "cell 1 1, span 1 3, wmax 100, hmax 100");
        frame.getContentPane().add(pushRadioButton, "cell 0 1, wmax 100, hmax 30");
        frame.getContentPane().add(pullRadioButton, "cell 0 2, wmax 100, hmax 30");
        frame.getContentPane().add(pssRadioButton, "cell 0 3, wmax 100, hmax 30");
        frame.getContentPane().add(gView.getView(), "cell 0 4, grow, span, push");
        
        frame.setVisible(true);
    }    
    
    public JRadioButton getPushRadioButton() {
        return pushRadioButton;
    }

    public void setPushRadioButton(JRadioButton pushRadioButton) {
        this.pushRadioButton = pushRadioButton;
    }

    public JRadioButton getPullRadioButton() {
        return pullRadioButton;
    }

    public void setPullRadioButton(JRadioButton pullRadioButton) {
        this.pullRadioButton = pullRadioButton;
    }

    public JRadioButton getPssRadioButton() {
        return pssRadioButton;
    }

    public void setPssRadioButton(JRadioButton pssRadioButton) {
        this.pssRadioButton = pssRadioButton;
    }
    public static void main(String[] args) {
        MainView view = new MainView();
        
    }
    
    
}
