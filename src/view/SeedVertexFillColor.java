package view;

import java.awt.Color;
import java.awt.Paint;

import javax.swing.JSlider;

import com.google.common.base.Function;

import edu.uci.ics.jung.visualization.picking.PickedInfo;
import model.NodeInfoCollection;

public final class SeedVertexFillColor<V> implements Function<V,Paint>
{
    protected PickedInfo<V> pi;
    protected final static float dark_value = 0.8f;
    protected final static float light_value = 0.2f;
    private NodeInfoCollection collection;
    private JSlider slider;
    
    public SeedVertexFillColor(PickedInfo<V> pi, NodeInfoCollection collection, JSlider slider)
    {
        this.pi = pi;
        this.collection = collection;
        this.slider = slider;
    }
    
    public Paint apply(V v)
    {
        if (pi.isPicked(v))
        {
            return new Color(1f, 1f, 0);
        }
        else
        {
            float hue = (float)1.0 - collection.getErrorForEpochandNode(slider.getValue(), (Integer)v);
            hue = (hue < 0 ? (float)0 : hue);
            return Color.getHSBColor(hue * (float)0.4, (float)1.0, (float)1.0);
        }
    }
}