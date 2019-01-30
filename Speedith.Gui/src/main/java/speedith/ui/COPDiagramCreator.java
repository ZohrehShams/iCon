package speedith.ui;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import icircles.concreteDiagram.*;
import icircles.decomposition.DecompositionStrategy;
import icircles.recomposition.RecompositionStrategy;
import icircles.util.CannotDrawException;
import speedith.ui.abstracts.AbstractArrow;
import speedith.ui.abstracts.AbstractSpiderComparator;
import speedith.ui.abstracts.COPAbstractDescription;
import speedith.ui.abstracts.CompleteCOPAbstractDescription;
import speedith.ui.concretes.ConcreteArrow;
import speedith.ui.concretes.ConcreteCOPDiagram;
import speedith.ui.concretes.ConcreteCompleteCOPDiagram;
import speedith.ui.concretes.ConcreteSpiderComparator;
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
		
//		if(m_initial_diagram instanceof CompleteCOPAbstractDescription){
//			CompleteCOPAbstractDescription complete_cop_initial_diagram  = (CompleteCOPAbstractDescription) m_initial_diagram;
//			ArrayList<ConcreteArrow> arrows = createArrows(complete_cop_initial_diagram);
//			if(! complete_cop_initial_diagram.getDots().isEmpty()){
//				return new ConcreteCOPDiagram(arrows,complete_cop_initial_diagram.getDots());
//			}
//		}else{ if(m_initial_diagram instanceof COPAbstractDescription){
//        	COPAbstractDescription cop_initial_diagram  = (COPAbstractDescription) m_initial_diagram;
//        	ArrayList<ConcreteArrow> arrows = createArrows(cop_initial_diagram);
//			if(! cop_initial_diagram.getDots().isEmpty()){
//				return new ConcreteCOPDiagram(arrows,cop_initial_diagram.getDots());
//			}
//			}
//		}
//
//		
//		
		
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

//        ArrayList<ConcreteSpider> spiders = createSpiders();
//
//        if(m_initial_diagram instanceof CompleteCOPAbstractDescription){
//    		CompleteCOPAbstractDescription complete_cop_initial_diagram  = (CompleteCOPAbstractDescription) m_initial_diagram;
//        	ArrayList<ConcreteArrow> arrows = createArrows(complete_cop_initial_diagram);
//        	ArrayList<ConcreteSpiderComparator> spiderComparators = createSpiderComparators(complete_cop_initial_diagram);
//        	result = new ConcreteCompleteCOPDiagram(new Rectangle2D.Double(0, 0, size, size),
//        			circles, shadedZones, unshadedZones, spiders,arrows,spiderComparators);
//        }else{ if(m_initial_diagram instanceof COPAbstractDescription){
//        	COPAbstractDescription cop_initial_diagram  = (COPAbstractDescription) m_initial_diagram;
//        	ArrayList<ConcreteArrow> arrows = createArrows(cop_initial_diagram);
//            result = new ConcreteCOPDiagram(new Rectangle2D.Double(0, 0, size, size),
//                	circles, shadedZones, unshadedZones, spiders,arrows);
//        }	
//        }
        
        
      if(m_initial_diagram instanceof CompleteCOPAbstractDescription){
  		CompleteCOPAbstractDescription complete_cop_initial_diagram  = (CompleteCOPAbstractDescription) m_initial_diagram;
      	if(complete_cop_initial_diagram.getDots().isEmpty()){
      		ArrayList<ConcreteSpider> spiders = createSpiders();
          	ArrayList<ConcreteArrow> arrows = createArrows(complete_cop_initial_diagram);
          	ArrayList<ConcreteSpiderComparator> spiderComparators = createSpiderComparators(complete_cop_initial_diagram);
      		result = new ConcreteCompleteCOPDiagram(new Rectangle2D.Double(0, 0, size, size),
      				circles, shadedZones, unshadedZones, spiders,arrows,spiderComparators);
      	}else{
      		ArrayList<ConcreteArrow> arrows = createArrows(complete_cop_initial_diagram);
      		ArrayList<ConcreteSpiderComparator> spiderComparators = createSpiderComparators(complete_cop_initial_diagram);
      		return new ConcreteCompleteCOPDiagram(arrows,complete_cop_initial_diagram.getDots(),spiderComparators);		
      	}
      }else{ if(m_initial_diagram instanceof COPAbstractDescription){
      	COPAbstractDescription cop_initial_diagram  = (COPAbstractDescription) m_initial_diagram;
      	if(cop_initial_diagram.getDots().isEmpty()){
          	ArrayList<ConcreteSpider> spiders = createSpiders();
          	ArrayList<ConcreteArrow> arrows = createArrows(cop_initial_diagram);
            result = new ConcreteCOPDiagram(new Rectangle2D.Double(0, 0, size, size),
                  	circles, shadedZones, unshadedZones, spiders,arrows);
      	}else{
      		ArrayList<ConcreteArrow> arrows = createArrows(cop_initial_diagram);
      		return new ConcreteCOPDiagram(arrows,cop_initial_diagram.getDots());
      	}

      }	
      }
        
        
        result.setFont(new Font("Helvetica", Font.BOLD,  16));
        }
        catch(CannotDrawException x)
        {
        throw x;
        }
        return result;
    }
	
	
    private ArrayList<ConcreteArrow> createArrows(COPAbstractDescription cop_initial_diagram) throws CannotDrawException {
    	ArrayList<ConcreteArrow> result = new ArrayList<ConcreteArrow>();
    	
    	Iterator<AbstractArrow> it = cop_initial_diagram.getArrowIterator();
    	
    	while (it.hasNext()){
    		AbstractArrow aa = it.next();
//    		ConcreteArrow ca = makeConcreteArrow(aa);
    		ConcreteArrow ca = new ConcreteArrow(aa);
    		result.add(ca);
    	}
    
    	return result;
    }
    
    
//    private ConcreteArrow makeConcreteArrow(AbstractArrow aa) throws CannotDrawException {
//    	
//    	ConcreteSpiderFoot feet = null;
//    	double xs = 0,ys=0,xt=0,yt=0;
//    	
//    	if(aa.get_start() instanceof AbstractCurve){
//    		AbstractCurve ac =(AbstractCurve) aa.get_start();
//    		CircleContour cc= map.get(ac);
//    		xs = cc.get_cx();
//    		ys = cc.get_cy()-cc.get_radius();
//    	}else {if (aa.get_start() instanceof AbstractSpider){
//    		AbstractSpider as = (AbstractSpider) aa.get_start();
//    		
//            for (ConcreteSpider concreteSpider : createSpiders()){
//              	if (concreteSpider.get_as().equals(as)){
//              		feet = concreteSpider.get_feet().get(0);
//              	}
//              }
//            
//            xs = feet.getX();
//            ys = feet.getY();
//    	}
//    	}
//    	
//    	
//    	if(aa.get_end() instanceof AbstractCurve){
//    		AbstractCurve ac = (AbstractCurve) aa.get_end(); 
//    		CircleContour cc= map.get(ac);
//    		xt = cc.get_cx();
//    		yt = cc.get_cy()+cc.get_radius();
//    	}else {if (aa.get_end() instanceof AbstractSpider){
//    		AbstractSpider as = (AbstractSpider) aa.get_end();
//    		
//            for (ConcreteSpider concreteSpider : createSpiders()){
//              	if (concreteSpider.get_as().equals(as)){
//              		feet = concreteSpider.get_feet().get(0);
//              	}
//              }
//            
//            xt = feet.getX();
//            yt = feet.getY();
//    	}
//    	}
//    	
//    	ConcreteArrow ca=new ConcreteArrow(xs,ys,xt,yt,aa);
//    	return ca;
//    }
    
    
    
    private ArrayList<ConcreteSpiderComparator> createSpiderComparators(CompleteCOPAbstractDescription 
    		complete_cop_initial_diagram) throws CannotDrawException {
    	
    	ArrayList<ConcreteSpiderComparator> result = new ArrayList<ConcreteSpiderComparator>();
    	Iterator<AbstractSpiderComparator> it = complete_cop_initial_diagram.getSpiderComparatorIterator();
    	
    	while (it.hasNext()){
    		AbstractSpiderComparator asc = it.next();
//    		ConcreteSpiderComparator csc = makeConcreteSpiderComparator(asc);
    		ConcreteSpiderComparator csc = new ConcreteSpiderComparator(asc);
    		result.add(csc);
    	}
    	return result;
    }
    
    
//    private ConcreteSpiderComparator makeConcreteSpiderComparator(AbstractSpiderComparator asc) 
//    		throws CannotDrawException {
//    	
//    	ConcreteSpiderFoot feet = null;
//    	double xs = 0,ys=0,xt=0,yt=0;
//    	
//    	AbstractSpider as1 = (AbstractSpider) asc.getAbsComparable1();
//    	AbstractSpider as2 = (AbstractSpider) asc.getAbsComparable2();
//		
//        for (ConcreteSpider concreteSpider : createSpiders()){
//          	if (concreteSpider.get_as().equals(as1)){
//          		feet = concreteSpider.get_feet().get(0);
//                xs = feet.getX();
//                ys = feet.getY();
//          	}
//          	if (concreteSpider.get_as().equals(as2)){
//          		feet = concreteSpider.get_feet().get(0);
//                xt = feet.getX();
//                yt = feet.getY();
//          	}
//          }
//        
//        ConcreteSpiderComparator csc = new ConcreteSpiderComparator(xs,ys,xt,yt,asc);
//    	return csc;
//    	
//    }


}
