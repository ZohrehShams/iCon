package speedith.ui;

import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import icircles.abstractDescription.AbstractDescription;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteZone;
import icircles.util.CannotDrawException;

public class ConcreteCompleteCOPDiagram extends ConcreteCOPDiagram {
	
	ArrayList<ConcreteSpiderComparator> spiderComparators;

	public ConcreteCompleteCOPDiagram(Double box, ArrayList<CircleContour> circles, ArrayList<ConcreteZone> shadedZones,
			ArrayList<ConcreteZone> unshadedZones, ArrayList<ConcreteSpider> spiders, ArrayList<ConcreteArrow> arrows,
			ArrayList<ConcreteSpiderComparator> spiderComparators) {
		super(box, circles, shadedZones, unshadedZones, spiders, arrows);
		this.spiderComparators=spiderComparators;
	}
	
	
    public ArrayList<ConcreteSpiderComparator> getConSpiderComparators() {
        return spiderComparators;
    }
    
//    public static ConcreteDiagram makeConcreteDiagram(AbstractDescription ad, int size) throws CannotDrawException {
//        // TODO
//        if (!ad.checksOk()) {
//            // not drawable
//            throw new CannotDrawException("badly formed diagram spec");
//        }
//        COPDiagramCreator dc = new COPDiagramCreator(ad);
//        return dc.createDiagram(size);
//    }

}
