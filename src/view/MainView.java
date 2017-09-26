package view;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
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
    private JLabel messagesLabel;
    
    public MainView() {
        frame = new JFrame("DIAS Visualization");
        frame.setBounds(0, 0, 800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new MigLayout("fill"));
        
        collection = new NodeInfoCollection("web/");
        gView = new GraphView("CircleLayout", collection);
        
        pushRadioButton = new JRadioButton("Push");
        pullRadioButton = new JRadioButton("Pull");
        pssRadioButton = new JRadioButton("Pss");
        
        messagesLabel = new JLabel("Messages", JLabel.CENTER);
        messagesLabel.setFont(new Font("Serif", Font.PLAIN, 20));
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
        
        frame.getContentPane().add(slider, "span, grow, wrap");
        frame.getContentPane().add(messagesLabel, "span, grow, wrap");
        frame.getContentPane().add(pushRadioButton);
        frame.getContentPane().add(pullRadioButton);
        frame.getContentPane().add(pssRadioButton, "wrap");
        frame.getContentPane().add(gView.getView(), "grow, push, span");
        
       frame.setVisible(true);
    }    
    
    public static void main(String[] args) {
        MainView view = new MainView();
        
    }
}
