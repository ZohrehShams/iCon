package speedith.ui;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import icircles.concreteDiagram.*;
import icircles.decomposition.DecompositionStrategy;
import icircles.recomposition.RecompositionStrategy;
import icircles.util.CannotDrawException;
import icircles.abstractDescription.AbstractBasicRegion;
import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractDescription;
import icircles.abstractDescription.AbstractSpider;
import icircles.concreteDiagram.DiagramCreator;

public class COPDiagramCreator extends DiagramCreator{

	public COPDiagramCreator(AbstractDescription ad) {
		super(ad);
	}
	
	public COPDiagramCreator(AbstractDescription ad,DecompositionStrategy decomp_strategy,
			RecompositionStrategy recomp_strategy) {
		super(ad);
	}
	
	
	@Override
	public ConcreteDiagram createDiagram(int size) throws CannotDrawException {
		
        make_guide_sizes(); 
        
        circles = new ArrayList<CircleContour>();
        ConcreteDiagram result = null;
        try
        {
        boolean ok = createCircles(size);

        if (!ok) {
            circles = null;
            return null;
        }

        CircleContour.fitCirclesToSize(circles, size);

        ArrayList<ConcreteZone> shadedZones = new ArrayList<ConcreteZone>();
        ArrayList<ConcreteZone> unshadedZones = new ArrayList<ConcreteZone>();
        createZones(shadedZones, unshadedZones);

        ArrayList<ConcreteSpider> spiders = createSpiders();
        ArrayList<ConcreteArrow> arrows = createArrows();
      
        result = new ConcreteCOPDiagram(new Rectangle2D.Double(0, 0, size, size),
        		circles, shadedZones, unshadedZones, spiders,arrows);
        
        result.setFont(new Font("Helvetica", Font.BOLD,  12));
        }
        catch(CannotDrawException x)
        {
        throw x;
        }
        return result;
    }
	
	
    private ArrayList<ConcreteArrow> createArrows() throws CannotDrawException {
    	ArrayList<ConcreteArrow> result = new ArrayList<ConcreteArrow>();

    	if (m_initial_diagram instanceof COPAbstractDescription){
    	COPAbstractDescription initial_diagram  = (COPAbstractDescription) m_initial_diagram;
    	Iterator<AbstractArrow> it = initial_diagram.getArrowIterator();
    	
    	while (it.hasNext()){
    		AbstractArrow aa = it.next();
    		ConcreteArrow ca = makeConcreteArrow(aa);
    		result.add(ca);
    	}}
    	return result;
    }
    
    
    private ConcreteArrow makeConcreteArrow(AbstractArrow aa) throws CannotDrawException {
    	
    	ConcreteArrow ca=null;
    	ConcreteSpiderFoot feet = null;
    	double xs = 0,ys=0,xt=0,yt=0;
    	
    	if(aa.get_start() instanceof AbstractCurve){
    		AbstractCurve ac =(AbstractCurve) aa.get_start();
    		CircleContour cc= map.get(ac);
    		xs = cc.get_cx();
    		ys = cc.get_cy()-cc.get_radius();
    	}else {if (aa.get_start() instanceof AbstractSpider){
    		AbstractSpider as = (AbstractSpider) aa.get_start();
    		
            for (ConcreteSpider concreteSpider : createSpiders()){
              	if (concreteSpider.get_as().equals(as)){
              		feet = concreteSpider.get_feet().get(0);
              	}
              }
            
            xs = feet.getX();
            ys = feet.getY();
    	}
    	}
    	
    	
    	if(aa.get_end() instanceof AbstractCurve){
    		AbstractCurve ac = (AbstractCurve) aa.get_end(); 
    		CircleContour cc= map.get(ac);
    		xt = cc.get_cx();
    		yt = cc.get_cy()+cc.get_radius();
    	}else {if (aa.get_end() instanceof AbstractSpider){
    		AbstractSpider as = (AbstractSpider) aa.get_end();
    		
            for (ConcreteSpider concreteSpider : createSpiders()){
              	if (concreteSpider.get_as().equals(as)){
              		feet = concreteSpider.get_feet().get(0);
              	}
              }
            
            xt = feet.getX();
            yt = feet.getY();
    	}
    	}
    	
    	return ca= new ConcreteArrow(xs,ys,xt,yt,aa);
    }
    
    


}
