package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPopupMenu;
import javax.swing.JSlider;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import model.NodeInfoCollection;

public class PopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {

    private  NodeInfoCollection collection;
    private JSlider slider;
    
    public PopupGraphMousePlugin(NodeInfoCollection collection, JSlider slider) {
        this(MouseEvent.BUTTON3_MASK);
        this.collection = collection;
        this.slider = slider;
    }
    
    public PopupGraphMousePlugin(int modifiers) {
        super(modifiers);
    }

    @SuppressWarnings("unchecked")
    protected void handlePopup(MouseEvent e) {
        final VisualizationViewer<Integer,Number> vv =  (VisualizationViewer<Integer,Number>)e.getSource();
        Point2D p = e.getPoint();
        GraphElementAccessor<Integer,Number> pickSupport = vv.getPickSupport();
        
        if (pickSupport != null) {
            final Integer v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if (v != null) {
                JPopupMenu popup = new JPopupMenu();
                ArrayList<String> info = collection.getInfoForEpochAndNode(slider.getValue(), v);
                popup.add(info.get(0));
                popup.add(info.get(1));
                popup.add(info.get(2));
                popup.add(info.get(3));
                popup.add(info.get(4));
                popup.show(vv, e.getX(), e.getY());
            }
        }
    }
}