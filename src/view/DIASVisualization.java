package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout2;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import model.GraphManager;
import model.NodeInfoCollection;

@SuppressWarnings("serial")
public class DIASVisualization extends JApplet {
    
    private JPanel jp;
    private JFrame jf;
    
    private VisualizationViewer<Integer,Number> vv;
    private DefaultModalGraphMouse<Integer,Number> graphMouse;
    
    private  NodeInfoCollection collection;
    private GraphManager graphManager;
    
    private ArrayList<String> pushMessages = new ArrayList<>();
    
    private SeedVertexFillColor<Integer> seedFillColor;
    
    private JSlider slider;
    private JRadioButton pushRadioButton;
    private JRadioButton pullRadioButton;
    private JButton snapButton;
    
    public DIASVisualization() {
        collection = new NodeInfoCollection("web/");
        graphManager = new GraphManager(collection);
        
        jp = getGraphPanel();
        
        jf = new JFrame();
        jf.getContentPane().add(jp);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JPanel getGraphPanel()
    {
        initSlider();
        
        vv = new VisualizationViewer<Integer,Number>(new FRLayout(graphManager.getGraph()));
        
        graphMouse = new DefaultModalGraphMouse<Integer,Number>();
        final ScalingControl scaler = new CrossoverScalingControl();
        final PickedState<Integer> picked_state = vv.getPickedVertexState();
        final PickedState<Number> picked_state2 = vv.getPickedEdgeState();
        
        final SeedVertexFillColor<Integer> seedFillColor = new SeedVertexFillColor<Integer>(picked_state, collection, slider);
        final SeedEdgeDrawColor<Number> seedEdgeColor = new SeedEdgeDrawColor<Number>(picked_state2);
        
        graphMouse.add(new PopupGraphMousePlugin(collection, slider));
        
        vv.getRenderContext().setEdgeDrawPaintTransformer(seedEdgeColor);
        
        vv.getRenderContext().setVertexFillPaintTransformer(seedFillColor);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
      
        vv.setGraphMouse(graphMouse);
        
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        JComboBox modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener(((DefaultModalGraphMouse<Integer,Number>)vv.getGraphMouse()).getModeListener());
        
        JPanel jp = new JPanel();
        jp.setBackground(Color.WHITE);
        jp.setLayout(new BorderLayout());
        jp.add(vv, BorderLayout.CENTER);
        Class[] combos = getCombos();
        final JComboBox jcb = new JComboBox(combos);
        // use a renderer to shorten the layout name presentation
        jcb.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String valueString = value.toString();
                valueString = valueString.substring(valueString.lastIndexOf('.')+1);
                return super.getListCellRendererComponent(list, valueString, index, isSelected,
                        cellHasFocus);
            }
        });
        
        snapButton = new JButton("Make Snapshot");
        snapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                int width = vv.getWidth() * 10;
                int height = vv.getHeight() * 10;

                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = img.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g2d.setTransform(AffineTransform.getScaleInstance(10, 10));
                vv.print(g2d);
                g2d.dispose();
                
                try {
                    final String imageName = "DIAS_" + slider.getValue() + ".png";
                    File image = new File(imageName);

                    ImageIO.write(img, "png", image);
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } 
            }
        });
        jcb.addActionListener(new LayoutChooser(jcb, vv, graphManager.getGraph()));
        jcb.setSelectedItem(FRLayout.class);

        JPanel control_panel = new JPanel(new GridLayout(3,1));
        
        JPanel topControls = new JPanel();
        JPanel middleControls = new JPanel();
        JPanel bottomControls = new JPanel();
        
        
        control_panel.add(topControls);
        control_panel.add(middleControls);
        control_panel.add(bottomControls);
        
        jp.add(control_panel, BorderLayout.NORTH);
        
        JTextArea infoArea = new JTextArea();
        infoArea.setPreferredSize(new Dimension(100, 60));
        
        pushRadioButton = new JRadioButton("Push");
        pullRadioButton = new JRadioButton("Pull");
        pushRadioButton.setOpaque(true);
        pullRadioButton.setOpaque(true);
        pushRadioButton.setFont(new Font("Dialog", Font.BOLD, 15));
        pullRadioButton.setFont(new Font("Dialog", Font.BOLD, 15));
        pushRadioButton.setForeground(new Color(55,94,151));
        pullRadioButton.setForeground(new Color(255,187,0));
        
        middleControls.add(slider);
        topControls.add(jcb);
        topControls.add(pushRadioButton);
        topControls.add(pullRadioButton);
        bottomControls.add(plus);
        bottomControls.add(minus);
        bottomControls.add(modeBox);
        bottomControls.add(snapButton);
        
        return jp;
    }
    
    private void initSlider() {
        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(collection.getRunDuration());
        slider.setPreferredSize(new Dimension(600, 40));
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
//        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(50);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                graphManager.setHasPush(pushRadioButton.isSelected());
                graphManager.setHasPull(pullRadioButton.isSelected());
                graphManager.updateGraph((int)((JSlider)e.getSource()).getValue());
                Layout<Integer,Number> layout = vv.getGraphLayout();
                layout.initialize();
                Relaxer relaxer = vv.getModel().getRelaxer();
                if(relaxer != null) {
                    relaxer.stop();
                    relaxer.prerelax();
                    relaxer.relax();
                }
                vv.repaint();
            }
        });
    }
   
    public void start() {
        this.getContentPane().add(getGraphPanel());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Class<? extends Layout>[] getCombos() {
        List<Class<? extends Layout>> layouts = new ArrayList<Class<? extends Layout>>();
        layouts.add(KKLayout.class);
        layouts.add(FRLayout.class);
        layouts.add(CircleLayout.class);
        layouts.add(SpringLayout.class);
        layouts.add(SpringLayout2.class);
        layouts.add(ISOMLayout.class);
        return layouts.toArray(new Class[0]);
    }

    public static void main(String[] args) {
        DIASVisualization app = new DIASVisualization();
    }
}