package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.TreeSet;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.SpiderComparator;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.copArgs.SpiderComparatorArg;


public class DeleteQuestionMarkTransformer extends IdTransformer {

    private final boolean applyForward;
    private ArrowArg arrowArg;
    private  SpiderComparatorArg targetSpiderComparator;
    
    
    public DeleteQuestionMarkTransformer(ArrowArg arrowArg, SpiderComparatorArg targetSpiderComparator, boolean applyForward) {
    	this.arrowArg = arrowArg;
    	this.targetSpiderComparator = targetSpiderComparator;
        this.applyForward = applyForward;
    }
    
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	if(targetSpiderComparator == null){
    		return psd;
    	}
    	
    	int subDiagramIndex = targetSpiderComparator.getSubDiagramIndex();
    	
    	if (diagramIndex == subDiagramIndex){
    		
    		if (!(psd instanceof CompleteCOPDiagram)){
    			  throw new TransformationException("The rule is not applicable to this diagram.");
    		  }
    		  
    		CompleteCOPDiagram compCop = (CompleteCOPDiagram) psd;
    		TreeSet<SpiderComparator> newSpiderComparators = new TreeSet<>(compCop.getSpiderComparators());
    		
    		if (! compCop.getArrows().contains(arrowArg.getArrow())) {
    			throw new TransformationException("The arrow does not exist in the diagram.");
    		}
    		
    		if (! compCop.getSpiderComparators().contains(targetSpiderComparator.getSpiderComparator())) {
    			throw new TransformationException("The spider comparator to be removed does not exist in the target diagram.");
    		}
    		
    		if (! targetSpiderComparator.getSpiderComparator().getQuality().equals("?")) {
    			throw new TransformationException("The equality between spiders has to be uknown.");
    		}
    		
    		assertSpidersHabitats(compCop);
    		assertArrowSuitability(compCop);
    		
    		newSpiderComparators.remove(targetSpiderComparator.getSpiderComparator());
    		SpiderComparator newSpiderComparator = new SpiderComparator(targetSpiderComparator.getSpiderComparator().getComparable1(), 
    				targetSpiderComparator.getSpiderComparator().getComparable2(),"=");
    		newSpiderComparators.add(newSpiderComparator);
    		
    		return SpiderDiagrams.createCompleteCOPDiagram(compCop.getSpiders(), compCop.getHabitats(), 
	    				compCop.getShadedZones(), compCop.getPresentZones(), compCop.getArrows(), compCop.getSpiderLabels(), 
	    				compCop.getCurveLabels(), compCop.getArrowCardinalities(), newSpiderComparators);  
    	  }
    		return psd;
    }
    
        
    
    
    private void assertArrowSuitability(CompleteCOPDiagram currentDiagram){
    	
    	if (!currentDiagram.arrowSourceSpider(arrowArg.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	if (!currentDiagram.arrowTargetContour(arrowArg.getArrow())){
    		throw new TransformationException("The target of arrow has to be a curve.");
    	}
    	
    	if( ( currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()).getNumber() != 1) || 
		(! currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()).getComparator().equals("<="))){
    		throw new TransformationException("The arrow cardinality must be <=1.");
    	}

    }
    
    private void assertSpidersHabitats(CompleteCOPDiagram currentDiagram) {
    	
    	String comparable1 = targetSpiderComparator.getSpiderComparator().getComparable1();
    	String comparable2 = targetSpiderComparator.getSpiderComparator().getComparable2();

    	if(! currentDiagram.getSpiderHabitat(comparable1).equals(currentDiagram.getSpiderHabitat(comparable2))){
    		throw new TransformationException("The spiders must have the same habitat.");
    	}
    	
    	//Since spiders have the same habitat, we can check the rest of the conditions for one of them only.
    	String[] allPossibleZones = new String[currentDiagram.getAllContours().size()];
    	allPossibleZones = currentDiagram.getAllContours().toArray(allPossibleZones);
    	Region regionInsideCurve = new Region(Zones.getZonesInsideAnyContour
    	          (Zones.allZonesForContours(allPossibleZones),arrowArg.getArrow().arrowTarget()));
    	
    	if(! currentDiagram.getHabitats().get(comparable1).isSubregionOf(regionInsideCurve)){
    		throw new TransformationException("The spiders must be contained in the target of the arrow.");
    	}

    	

    }
    
    	
    
    
}
