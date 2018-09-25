package speedith.ui.concretes;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.Point;
import org.apache.log4j.Logger;

import speedith.ui.abstracts.AbstractArrow;



public class ConcreteArrow implements Cloneable{
	
    static Logger logger = Logger.getLogger(ConcreteArrow.class.getName());

    Line2D.Double arrow;
    Point midpoint;
    
    double x_s;
    double y_s;
    double x_t;
    double y_t;
    double nudge = 0.1;
    
    Color col;
    Stroke stroke;
    
    public AbstractArrow aa;
    
    Area shape;
    
    
    public ConcreteArrow(double xs, double ys, double xt, double yt, AbstractArrow aa) {
        this.x_s = xs;
        this.y_s = ys;
        this.x_t = xt;
        this.y_t = yt;
        this.aa = aa;
        arrow = makeArrow(x_s, y_s, x_t, y_t);
        shape =null;
    }

    
    
    private Line2D.Double makeArrow(double xs, double ys, double xt, double yt) {
        return new Line2D.Double(xs, ys, xt, yt);
    }
    
    public Line2D.Double initArrow() {
        return new Line2D.Double(x_s, y_s, x_t, y_t);
    }
    

    public Line2D.Double getArrow() {
        return arrow;
    }
    
    public Line2D.Double getScaledArrow(Double scale) {
        return makeArrow(x_s*scale, y_s*scale, x_t*scale, y_t*scale);
    }


//    public Line2D.Double getScaledArrow() {
//        return makeArrow(x_s, y_s, x_t, y_t);
//    }
   
    public Line2D.Double getMyArrow(double x_s, double y_s, double x_t, double y_t) {
        return makeArrow(x_s, y_s, x_t, y_t);
    }
    
    public double getLabelXPosition() {
        return (x_s + x_t)  / 2;
    }

    public double getLabelYPosition() {
    	return (y_s + y_t)  / 2;
    }
    
    public double get_xs(){
    	return x_s;
    }
    
    public double get_ys(){
    	return y_s;
    }
    
    public double get_xt(){
    	return x_t;
    }
    
    public double get_yt(){
    	return y_t;
    }
    
    
    @Override
    public ConcreteArrow clone() {
        ConcreteArrow clone;
        try {
            clone = (ConcreteArrow) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
