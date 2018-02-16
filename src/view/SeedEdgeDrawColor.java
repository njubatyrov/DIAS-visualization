package view;

import java.awt.Color;
import java.awt.Paint;

import com.google.common.base.Function;

import edu.uci.ics.jung.visualization.picking.PickedInfo;

public final class SeedEdgeDrawColor<V> implements Function<V,Paint>
{
    protected PickedInfo<V> pi;
    protected final static float dark_value = 0.8f;
    protected final static float light_value = 0.2f;
    
    public SeedEdgeDrawColor(PickedInfo<V> pi)
    {
        this.pi = pi;
    }
    
    public Paint apply(V v)
    {
        String edge = (String)v;
        
        if (edge.charAt(0) == '@') {
            return new Color(55,94,151);
        }
        else
        {
            return new Color(255,187,0);
        }
    }
}
