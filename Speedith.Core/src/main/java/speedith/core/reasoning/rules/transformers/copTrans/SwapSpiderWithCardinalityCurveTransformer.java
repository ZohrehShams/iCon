package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.CarCDiagram;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.util.unitary.ZoneTransfer;
import speedith.core.reasoning.util.unitary.ZoneTransferSingle;


public class SwapSpiderWithCardinalityCurveTransformer extends IdTransformer {

    private final boolean applyForward;
    private ArrowArg targetArrow;
    private ContourArg targetCurve;
    
    
    public SwapSpiderWithCardinalityCurveTransformer(ArrowArg arrowArg, ContourArg targetCurve, boolean applyForward) {
    	this.targetArrow = arrowArg;
    	this.targetCurve = targetCurve;
        this.applyForward = applyForward;
    }
    
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = targetArrow.getSubDiagramIndex();
    	
    	if (diagramIndex == subDiagramIndex){
    		
    		if (!(psd instanceof CompleteCOPDiagram)){
    			  throw new TransformationException("The rule is not applicable to this diagram.");
    		  }
    		  
    		CompleteCOPDiagram compCop = (CompleteCOPDiagram) psd;
    		
    		
    		if (! compCop.getArrows().contains(targetArrow.getArrow())) {
    			throw new TransformationException("The arrow does not exist in the diagram.");
    		}
   
    		if (! compCop.getAllContours().contains(targetCurve.getContour())) {
    			throw new TransformationException("The curve does not exists in the diagram.");
    		}
    		
    		assertCurveSuitability(compCop);
    		assertArrowSuitability(compCop);
    		
    		Region region = compCop.getHabitats().get(targetArrow.getArrow().arrowTarget()) ;
    		for (Zone zone : region.sortedZones())	{
    			if(! Zones.isZonePartOfThisContour(zone, targetCurve.getContour())){
    				throw new TransformationException("The target of arrow must be contained in the chosen curve.");
    			}
    		}
    		
    		
    		//Deleting the spider that is the target of the arrow. The arrow will be deleted automatically as the target is deleted.
    		CompleteCOPDiagram compCopNoSpider = (CompleteCOPDiagram) compCop.deleteSpider(targetArrow.getArrow().arrowTarget());
    		

    		//Adding an unnamed curve to the chosen curve. This curve splits every zones inside the chosen curve and is completely
    		//outside the zones outside the chosen curve. So the in (zones completely inside the added curve) and out parameter 
    		//(zones completely outside the added curve), are empty and zones outside the chosen curve, respectively.
    		TreeSet<Zone> inZones = new TreeSet<Zone>();
    		TreeSet<Zone> outZones = new TreeSet<Zone>();
	    	String[] allPossibleZones = new String[compCopNoSpider.getAllContours().size()];
	    	allPossibleZones = compCopNoSpider.getAllContours().toArray(allPossibleZones);
	    	for (Zone zone: Zones.allZonesForContours(allPossibleZones)){
	    		if((Zones.isZoneOutsideContours(zone,targetCurve.getContour()))
	    				&&(compCopNoSpider.getPresentZones().contains(zone))){
	    			outZones.add(zone);
	    		}
	    	}
	    	CompleteCOPDiagram compCopNoSpiderUnCurve = (CompleteCOPDiagram) new 
	        		ZoneTransferSingle(compCopNoSpider).transferContour("unnamedCurve",inZones,outZones);
    		
	    	//Adding the new arrow and its cardinality.
    		Arrow newArrow = new Arrow(targetArrow.getArrow().arrowSource(),"unnamedCurve",targetArrow.getArrow().arrowType(),
			targetArrow.getArrow().arrowLabel());
	    	CompleteCOPDiagram compCopNoSpiderUnCurveNewArrow = (CompleteCOPDiagram) compCopNoSpiderUnCurve.addArrow(newArrow);
	    	CompleteCOPDiagram compCopNoSpiderUnCurveNewArrowCar = (CompleteCOPDiagram) 
	    			compCopNoSpiderUnCurveNewArrow.addCardinality(newArrow, new Cardinality(">=","1"));

	    	
    		return compCopNoSpiderUnCurveNewArrowCar;

    	  }
    		return null;
    }
    
    
    
    @Override
    public SpiderDiagram transform(ConceptDiagram cd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = targetArrow.getSubDiagramIndex();
    	
    	if (diagramIndex == subDiagramIndex){
    		
    		if (! cd.get_cd_Arrows().contains(targetArrow.getArrow())) {
    			throw new TransformationException("The arrow does not exist in the diagram.");
    		}
   
    		if (! cd.getAllContours().contains(targetCurve.getContour())) {
    			throw new TransformationException("The curve does not exists in the diagram.");
    		}
    		
    		assertCurveSuitability(cd);
    		assertArrowSuitability(cd);
    		
    		
    		CompleteCOPDiagram compCop = (CompleteCOPDiagram) cd.targetDiagram(targetArrow.getArrow());
    		Region region = compCop.getHabitats().get(targetArrow.getArrow().arrowTarget()) ;
    		for (Zone zone : region.sortedZones())	{
    			if(! Zones.isZonePartOfThisContour(zone, targetCurve.getContour())){
    				throw new TransformationException("The target of arrow must be contained in the chosen curve.");
    			}
    		}
    		
    		
    		//Deleting the spider that is the target of the arrow. The arrow will be deleted automatically as the target is deleted.
    		CompleteCOPDiagram compCopNoSpider = (CompleteCOPDiagram) compCop.deleteSpider(targetArrow.getArrow().arrowTarget());
    		

    		//Adding an unnamed curve to the chosen curve. This curve splits every zones inside the chosen curve and is completely
    		//outside the zones outside the chosen curve. So the in (zones completely inside the added curve) and out parameter 
    		//(zones completely outside the added curve), are empty and zones outside the chosen curve, respectively.
    		TreeSet<Zone> inZones = new TreeSet<Zone>();
    		TreeSet<Zone> outZones = new TreeSet<Zone>();
	    	String[] allPossibleZones = new String[compCopNoSpider.getAllContours().size()];
	    	allPossibleZones = compCopNoSpider.getAllContours().toArray(allPossibleZones);
	    	for (Zone zone: Zones.allZonesForContours(allPossibleZones)){
	    		if((Zones.isZoneOutsideContours(zone,targetCurve.getContour()))
	    				&&(compCopNoSpider.getPresentZones().contains(zone))){
	    			outZones.add(zone);
	    		}
	    	}
	    	CompleteCOPDiagram compCopNoSpiderUnCurve = (CompleteCOPDiagram) new 
	        		ZoneTransferSingle(compCopNoSpider).transferContour("unnamedCurve",inZones,outZones);
	    	
	    	
	    	//Replacing the cop that was the target of the arrow with one that includes an unlabelled curve instead of the spider that is the target of the arrow. 
        	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cd.getPrimaries());
        	int indexOfTransformedChild = transformedChildren.indexOf(compCop);
        	transformedChildren.set(indexOfTransformedChild, compCopNoSpiderUnCurve);
        	
        	
        	//Deleting the old arrow and adding the new arrow
        	SortedSet<Arrow> newArrows = new TreeSet<Arrow>(cd.get_cd_Arrows());
        	newArrows.remove(targetArrow.getArrow());
    		Arrow newArrow = new Arrow(targetArrow.getArrow().arrowSource(),"unnamedCurve",targetArrow.getArrow().arrowType(),targetArrow.getArrow().arrowLabel());
        	newArrows.add(newArrow);
	    	        	
        	
        	TreeMap<Arrow,Cardinality> newCardinalities = new TreeMap<Arrow,Cardinality>();
        	newCardinalities.put(newArrow, new Cardinality(">=","1"));
        	
        	
        	CarCDiagram transformedConceptDiagram = SpiderDiagrams.createCarCDiagram(newArrows, newCardinalities, transformedChildren,false);
        	
//	    	CarCDiagram transformedConceptDiagram = SpiderDiagrams.createCarCDiagram(newArrows, new TreeMap<Arrow,Cardinality>(), transformedChildren,false);
//	    	
//	    	CarCDiagram transformedConceptDiagramTest = transformedConceptDiagram.add_cd_ArrowCardinality(newArrow, new Cardinality(">=","1"));
	    	
//	        System.out.println(transformedConceptDiagram);
	        return transformedConceptDiagram;
//    		return transformedConceptDiagram.add_cd_ArrowCardinality(newArrow, new Cardinality(">=","1"));

    	  }
    		return null;
    }
    
    
    
    

    
    private void assertCurveSuitability(CompleteCOPDiagram currentDiagram){
    	if ((currentDiagram.getCurveLabels().get(targetCurve.getContour()) == null) ||
				(currentDiagram.getCurveLabels().get(targetCurve.getContour()).equals("")) ){
			throw new TransformationException("The chosen curve must be named.");
		}
    	
    }
    
    
    private void assertCurveSuitability(ConceptDiagram currentDiagram){
    	if ((currentDiagram.getAllCurveLabels().get(targetCurve.getContour()) == null) ||
				(currentDiagram.getAllCurveLabels().get(targetCurve.getContour()).equals("")) ){
			throw new TransformationException("The chosen curve must be named.");
		}
    	
    }
    
    

    
    
    private void assertArrowSuitability(CompleteCOPDiagram currentDiagram){
    	
    	if (! targetArrow.getArrow().arrowType().equals("dashed")){
    		throw new TransformationException("The arrow has to be dashed.");
    	}
    	
    	if (!currentDiagram.arrowSourceSpider(targetArrow.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(targetArrow.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(targetArrow.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetSpider(targetArrow.getArrow())){
    		throw new TransformationException("The target of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(targetArrow.getArrow().arrowTarget()) == null) ||
				(currentDiagram.getSpiderLabels().get(targetArrow.getArrow().arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider.");
		}
    	
    	if (currentDiagram.getArrowCardinalities().get(targetArrow.getArrow()) != null){
    		throw new TransformationException("The arrow should not have any cardinality.");
    	}

    }
    
    
    
    
    private void assertArrowSuitability(ConceptDiagram currentDiagram){
    	
    	if (! targetArrow.getArrow().arrowType().equals("dashed")){
    		throw new TransformationException("The arrow has to be dashed.");
    	}
    	
    	if (!currentDiagram.arrowSourceSpider(targetArrow.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getAllSpiderLabels().get(targetArrow.getArrow().arrowSource()) == null) ||
				(currentDiagram.getAllSpiderLabels().get(targetArrow.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetSpider(targetArrow.getArrow())){
    		throw new TransformationException("The target of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getAllSpiderLabels().get(targetArrow.getArrow().arrowTarget()) == null) ||
				(currentDiagram.getAllSpiderLabels().get(targetArrow.getArrow().arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider.");
		}
    	
    	
    	//This is only available to CarCD
//    	if (currentDiagram.getArrowCardinalities().get(targetArrow.getArrow()) != null){
//    		throw new TransformationException("The arrow should not have any cardinality.");
//    	}
    }
    
    
}
