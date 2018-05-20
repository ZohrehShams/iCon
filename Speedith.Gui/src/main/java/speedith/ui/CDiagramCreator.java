package speedith.ui;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import icircles.abstractDescription.AbstractCurve;
import icircles.abstractDescription.AbstractSpider;
import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteSpiderFoot;
import icircles.util.CannotDrawException;

public class CDiagramCreator{

	private CDAbstractDescription initial_diagram;
	protected HashMap<AbstractCurve, CircleContour> mapAll;
	protected ArrayList<ConcreteSpider> spiderAll;
	
	public CDiagramCreator(CDAbstractDescription cdad){
		this.initial_diagram = cdad;
		mapAll = new HashMap<AbstractCurve, CircleContour>();
		spiderAll = new ArrayList<ConcreteSpider>();
	}
	
	public HashMap<AbstractCurve, CircleContour> getMapAll(){
		return mapAll;
	}
	
	public ArrayList<ConcreteSpider>  getSpiderAll(){
		return spiderAll;
	}
	
	public ConcreteCDiagram createDiagram(int size) throws CannotDrawException {
		ConcreteCDiagram result = null;
		ArrayList<ConcreteCOPDiagram> concretePrimaries = new ArrayList<ConcreteCOPDiagram>();
		for (COPAbstractDescription copad: initial_diagram.getAbstractPrimaries()){
			COPDiagramCreator copc = new COPDiagramCreator(copad);
			concretePrimaries.add((ConcreteCOPDiagram) copc.createDiagram(size));	
			mapAll.putAll(copc.getMap());
			spiderAll.addAll(copc.createSpiders());
		}
		
		//result = new ConcreteCDiagram(concretePrimaries,createArrows()); 
		result = new ConcreteCDiagram(concretePrimaries,new ArrayList<ConcreteArrow>()); 
		
		return result;
	}

	
    private ArrayList<ConcreteArrow> createArrows() throws CannotDrawException {
    	ArrayList<ConcreteArrow> arrows = new ArrayList<ConcreteArrow>();
    	
    	
    	Iterator<AbstractArrow> it = initial_diagram.getArrowIterator();
    	while (it.hasNext()){
    		AbstractArrow aa = it.next();
    		ConcreteArrow ca = makeConcreteArrow(aa);
    		arrows.add(ca);
    	}
    	
    	
    	return arrows;	
    }
    
    
    
 private ConcreteArrow makeConcreteArrow(AbstractArrow aa) throws CannotDrawException {
    		 
    	ConcreteArrow ca=null;
    	ConcreteSpiderFoot feet = null;
    	double xs = 0,ys=0,xt=0,yt=0;
    	    	
    	if(aa.get_start() instanceof AbstractCurve){
    		AbstractCurve ac =(AbstractCurve) aa.get_start();
//    		unfortunately this returns null, as ac is compared with key set of maps based on the compare to that is 
//    		defined in AbstarctCurve class in which, the label and the id play a role in comparison and ultimately 
//    		in determining the equality. ac is not equal to any object's key in the mapAll (due to id reasons), so 
//    		we can't retrieve the value for it. Instead I have to look for  an abstractcurve in the keyset of map 
//    		that has the same label as ac and retrieve the value for that. It is not clear to me why ac and acPrime
//    		don't have the same id. The reason is certainly the fact that the constructor of abstractCurve has been
//    		called twice, which automatically increases the id. But why and where has it been called twice?
//    		CircleContour cc= mapAll.get(ac);
    		
    		
    		AbstractCurve acPrime = null;
    		
    		for (AbstractCurve acnew : mapAll.keySet()){
    			if (acnew.matchesLabel(ac)){
    				acPrime = acnew;
    				break;
    			}
    		}
    		CircleContour cc= mapAll.get(acPrime);
    		xs = cc.get_cx();
    		ys = cc.get_cy()-cc.get_radius();
    	}else {if (aa.get_start() instanceof AbstractSpider){
    		
    		//System.out.println("You should come here for source.");
    		AbstractSpider as = (AbstractSpider) aa.get_start();
    		
            for (ConcreteSpider concreteSpider : spiderAll){
            	if (concreteSpider.get_as().getName().equals(as.getName())){
            		feet = concreteSpider.get_feet().get(0);
            	}            	
            	
            	
//              	if (concreteSpider.get_as().equals(as)){
//              		feet = concreteSpider.get_feet().get(0);
//              	}
              }
            
            xs = feet.getX();
            ys = feet.getY();
    	}
    	}
    	
    	
    	if(aa.get_end() instanceof AbstractCurve){
    		AbstractCurve ac = (AbstractCurve) aa.get_end(); 
    		
    		AbstractCurve acPrime = null;
    		
    		for (AbstractCurve acnew : mapAll.keySet()){
    			if (acnew.matchesLabel(ac)){
    				acPrime = acnew;
    				break;
    			}
    		}

    		CircleContour cc= mapAll.get(acPrime);   		
    		xt = cc.get_cx();
    		yt = cc.get_cy()+cc.get_radius();
    	}else {if (aa.get_end() instanceof AbstractSpider){
    		//System.out.println("You should come here for target.");
    		AbstractSpider as = (AbstractSpider) aa.get_end();
    		
            for (ConcreteSpider concreteSpider : spiderAll){
            	
            	if (concreteSpider.get_as().getName().equals(as.getName())){
            		feet = concreteSpider.get_feet().get(0);
            	} 
            	
//              	if (concreteSpider.get_as().equals(as)){
//              		feet = concreteSpider.get_feet().get(0);
//              	}
              }
            
            xt = feet.getX();
            yt = feet.getY();
    	}
    	}
    	
    	return ca= new ConcreteArrow(xs,ys,xt,yt,aa);
    }
	
}
