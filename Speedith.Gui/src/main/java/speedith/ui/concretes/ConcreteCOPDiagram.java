package speedith.ui.concretes;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.*;
import icircles.util.CannotDrawException;
import speedith.ui.COPDiagramCreator;



public class ConcreteCOPDiagram extends ConcreteDiagram{

	ArrayList<ConcreteArrow> arrows;
	TreeSet<String> dots;

	public ConcreteCOPDiagram(Rectangle2D.Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones,
			ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders, ArrayList<ConcreteArrow> arrows) {
		super(box, circles, shadedZones, unshadedZones, spiders);
		this.arrows = arrows;
	}
	
	
	//This constructor is used for when there are no curves. 
    public ConcreteCOPDiagram(ArrayList<ConcreteArrow> arrows, TreeSet<String> dots) {
        super(new Rectangle2D.Double(0.0D, 0.0D, (double)300, (double)300), null, null, null, null);
        this.arrows = arrows;
        this.dots = dots;
    }
    
	
    public ArrayList<ConcreteArrow> getArrows() {
        return arrows;
    }
    
    public TreeSet<String>  getDots() {
        return dots;
    }

    public static ConcreteDiagram makeConcreteDiagram(AbstractDescription ad, int size) throws CannotDrawException {
        // TODO
        if (!ad.checksOk()) {
            // not drawable
            throw new CannotDrawException("badly formed diagram spec");
        }
        COPDiagramCreator dc = new COPDiagramCreator(ad);
        return dc.createDiagram(size);
    }
    
    
    public ConcreteArrow getArrowAtPoint(Point p) {
        for (ConcreteArrow arrow : this.arrows) {
        	double ptSeg = Line2D.ptLineDist(arrow.get_xs(), arrow.get_ys(), arrow.get_xt(),arrow.get_yt(), p.getX() , p.getY());
        	  if ( Math.abs(ptSeg) < 2.0){
        		return arrow;
        	}
        }
        return null;
    }
    
    

    
    
//    public ConcreteArrow getArrowAtPoint(Point p, double tolerance) {
//        for (ConcreteArrow arrow : this.arrows) {
//        	double A = p.getX() - arrow.x_s; 
//        	double B = p.getY() - arrow.y_s; 
//        	double C = arrow.x_t - arrow.x_s; 
//        	double D = arrow.y_t - arrow.y_s; 
//        	
//        	double E = -D;
//        	double F = C;
//        	
//        	double dot = A * E + B * F;
//        	double len_sq = E * E + F * F;
//        	
//        	double dis = Math.abs(dot) / Math.sqrt(len_sq);
//        	
//        	System.out.println(dis);
//        	
//        	if (dis < tolerance){
//        		return arrow;
//        	}
//        }
//        return null;
//    }
    
    public ConcreteArrow getArrowAtPoint(Point p, double tolerance) {
        for (ConcreteArrow arrow : this.arrows) {
    
        	double x = (arrow.x_t + arrow.x_s)/2; 
        	double y = (arrow.y_t + arrow.y_s)/2; 
        	
        	
            double dis = Math.sqrt((p.x - x) * (p.x - x)
                    + (p.y - y) * (p.y - y));
            

        	System.out.println(dis);
        	
        	if (dis < 100.0){
        		return arrow;
        	}
        }
        return null;
    }
    

    
}
