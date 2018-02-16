package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;

import javax.swing.JComboBox;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;

public final class LayoutChooser implements ActionListener
{
    private final JComboBox<?> jcb;
    private final VisualizationViewer<Integer,Number> vv;
    private Graph<Integer, String> graph;
    
    LayoutChooser(JComboBox<?> jcb, VisualizationViewer<Integer,Number> vv, Graph<Integer, String> graph)
    {
        super();
        this.jcb = jcb;
        this.vv = vv;
        this.graph = graph;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent arg0)
    {
        Object[] constructorArgs =
            { graph };

        Class<? extends Layout<Integer,Number>> layoutC = 
            (Class<? extends Layout<Integer,Number>>) jcb.getSelectedItem();
        try
        {
            Constructor<? extends Layout<Integer, Number>> constructor = layoutC
                    .getConstructor(new Class[] {Graph.class});
            Object o = constructor.newInstance(constructorArgs);
            Layout<Integer,Number> l = (Layout<Integer,Number>) o;
            l.setInitializer(vv.getGraphLayout());
            l.setSize(vv.getSize());
            
            LayoutTransition<Integer,Number> lt =
                new LayoutTransition<Integer,Number>(vv, vv.getGraphLayout(), l);
            Animator animator = new Animator(lt);
            animator.start();
            vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
            vv.repaint();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}