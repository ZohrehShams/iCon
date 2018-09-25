package speedith.ui.concretes;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.*;
import icircles.util.CannotDrawException;
import speedith.ui.COPDiagramCreator;



public class ConcreteCOPDiagram extends ConcreteDiagram{

	ArrayList<ConcreteArrow> arrows;

	public ConcreteCOPDiagram(Rectangle2D.Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones,
			ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders, ArrayList<ConcreteArrow> arrows) {
		super(box, circles, shadedZones, unshadedZones, spiders);
		this.arrows = arrows;
	}
	
	
    public ArrayList<ConcreteArrow> getArrows() {
        return arrows;
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
        	//Double ptSeg = Line2D.ptSegDist(arrow.get_xs(), arrow.get_ys(), arrow.get_xt(),arrow.get_yt(), p.getX() , p.getY());
        	Double ptSeg = Line2D.ptLineDist(arrow.get_xs(), arrow.get_ys(), arrow.get_xt(),arrow.get_yt(), p.getX() , p.getY());
        	//if ( (ptSeg < 0.0) && !(ptSeg > 0.0)){
        	  if ( Math.abs(ptSeg) < 4.0){
        		return arrow;
        	}
        }
        return null;
    }
    
    
    
}
