package speedith.ui.concretes;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.TreeSet;

import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteZone;

public class ConcreteCompleteCOPDiagram extends ConcreteCOPDiagram {
	
	ArrayList<ConcreteSpiderComparator> spiderComparators;
	private int id;

	public ConcreteCompleteCOPDiagram(Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones,
			ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders, ArrayList<ConcreteArrow> arrows,
			ArrayList<ConcreteSpiderComparator> spiderComparators) {
		super(box, circles, shadedZones, unshadedZones, spiders, arrows);
		this.spiderComparators=spiderComparators;
	}
	
	
	public ConcreteCompleteCOPDiagram(int id, Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones,
			ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders, ArrayList<ConcreteArrow> arrows,
			ArrayList<ConcreteSpiderComparator> spiderComparators) {
		super(box, circles, shadedZones, unshadedZones, spiders, arrows);
		this.id =id;
		this.spiderComparators=spiderComparators;
	}
	
	
	//This constructor is used for when there are no curves. 
    public ConcreteCompleteCOPDiagram(ArrayList<ConcreteArrow> arrows, TreeSet<String> dots,ArrayList<ConcreteSpiderComparator> spiderComparators) {
        super(arrows, dots);
        this.spiderComparators=spiderComparators;
    }
    
    
    public ArrayList<ConcreteSpiderComparator> getConSpiderComparators() {
        return spiderComparators;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public ConcreteSpiderComparator getSpiderComparatorAtPoint(Point p) {
        for (ConcreteSpiderComparator spiderComparator : this.spiderComparators) {
        	double ptSeg = Line2D.ptLineDist(spiderComparator.get_xs(), spiderComparator.get_ys(), spiderComparator.get_xt(),spiderComparator.get_yt(), p.getX() , p.getY());
        	  if ( Math.abs(ptSeg) < 4.0){
        		return spiderComparator;
        	}
        }
        return null;
    }

}
