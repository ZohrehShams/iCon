package speedith.core.reasoning.rules.transformers.copTrans;

import speedith.core.lang.*;
import speedith.core.lang.Comparator;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.copArgs.SpiderComparatorArg;

import java.util.*;


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
    
    
    
    private void assertSpidersHabitats(CompleteCOPDiagram currentDiagram) {
    	
    	String comparable1 = targetSpiderComparator.getSpiderComparator().getComparable1();
    	String comparable2 = targetSpiderComparator.getSpiderComparator().getComparable2();

    	if(! currentDiagram.getSpiderHabitat(comparable1).equals(currentDiagram.getSpiderHabitat(comparable2))){
    		throw new TransformationException("The spiders must have the same habitat.");
    	}
    	
    	//Since spiders have the same habitat, we can check the rest of the conditions for one of them only.
    	if(currentDiagram.getSpiderHabitat(comparable1).getZonesCount() != 1){
    		throw new TransformationException("The spiders must be single zone habitat.");
    	}
    	
    	SortedSet<String> inContours = currentDiagram.getSpiderHabitat(comparable1).sortedZones().first().getInContours();
    	String curveName = currentDiagram.getCurveLabels().get(inContours.first());
    	
    	if ((inContours.isEmpty()) || (curveName != null)){
    		throw new TransformationException("The spiders must be contained in an unamed curve.");
    	}
    		

    }
    
    
    
    private void assertArrowSuitability(CompleteCOPDiagram currentDiagram){
    	
    	if (!currentDiagram.arrowSourceSpider(arrowArg.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	String comparable1 = targetSpiderComparator.getSpiderComparator().getComparable1();
    	SortedSet<String> inContours = currentDiagram.getSpiderHabitat(comparable1).sortedZones().first().getInContours();
    	if(! arrowArg.getArrow().arrowTarget().equals(inContours.first())){
    		throw new TransformationException("The source of arrow has to be the habitat of the spiders.");
    	}
    	
    	
    	if( ( currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()).getNumber() != 1) || 
		(! currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()).getComparator().equals("<="))){
    		throw new TransformationException("The arrow cardinality must be <=1.");
    	}
    	

    }
    
    
    
    
    
    
    
    
    
    
}
