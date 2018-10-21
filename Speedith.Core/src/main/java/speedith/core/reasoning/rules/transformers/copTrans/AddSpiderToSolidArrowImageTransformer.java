package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import speedith.core.lang.Arrow;
import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

public class AddSpiderToSolidArrowImageTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final ArrowArg targetArrow;
    private final ArrowArg destinationArrow;

    public AddSpiderToSolidArrowImageTransformer(int indexOfParent, ArrowArg targetArrow, ArrowArg destinationArrow) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetArrow = targetArrow;
        this.destinationArrow = destinationArrow;
    }
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
    		
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);

    	    PrimarySpiderDiagram arrowDiagram = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetArrow);
    	    PrimarySpiderDiagram  spiderDiagram= InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetArrow);
    		
    	    
    	    if((arrowDiagram instanceof CompleteCOPDiagram) && (spiderDiagram instanceof CompleteCOPDiagram)){
    	    	
    	    	CompleteCOPDiagram compArrowDiagram = (CompleteCOPDiagram) arrowDiagram;
    	    	CompleteCOPDiagram compSpiderDiagram = (CompleteCOPDiagram) spiderDiagram;
    	    	
    	    	assertDiagramContainTargetArrow(compArrowDiagram,targetArrow.getArrow());
    	    	assertTargetArrowIsSuitable(compArrowDiagram);
    	    	
    	    	assertDiagramContainTargetArrow(compSpiderDiagram,destinationArrow.getArrow());
    	    	assertDestinationArrowIsSuitable(compSpiderDiagram);

    	    	
    	    	if (compArrowDiagram.getSpiders().contains(destinationArrow.getArrow().arrowTarget())){
    	    		throw new TransformationException("The spider being added to the solid arrow image cannot exists in the destination diagram.");
    	    	}
    	    	
    	    	
    	    	String[] allPossibleZones = new String[compArrowDiagram.getAllContours().size()];
    	    	allPossibleZones = compArrowDiagram.getAllContours().toArray(allPossibleZones);
    	    	Region regionInsideArrowTarget = new Region(Zones.getZonesInsideAnyContour
    	    		(Zones.allZonesForContours(allPossibleZones),targetArrow.getArrow().arrowTarget()));
    	    	ArrayList<Zone> nonShadedZonesInsideArrowTarget = new ArrayList<Zone>();
    	    	for(Zone zone: regionInsideArrowTarget.sortedZones()){
    	    		if(! compArrowDiagram.getShadedZones().contains(zone)){
    	    			nonShadedZonesInsideArrowTarget.add(zone);
    	    		}
    	    	}
    	    	Region nonShadedRegionInsideArrowTarget = new Region(nonShadedZonesInsideArrowTarget);
    	    	    	
    	    	CompleteCOPDiagram transformedArrowDiagram = (CompleteCOPDiagram) compArrowDiagram.addLUSpider(destinationArrow.getArrow().arrowTarget(), nonShadedRegionInsideArrowTarget, 
    	    			compSpiderDiagram.getSpiderLabels().get(destinationArrow.getArrow().arrowTarget()));
    	    	
                return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedArrowDiagram, spiderDiagram, targetArrow, indexOfParent);
    	    }else return currentDiagram;
    		
    	}else return null;
    }
    
    
    
    
    private void assertDiagramContainTargetArrow(CompleteCOPDiagram currentDiagram, Arrow arrow) {
        if (!currentDiagram.getArrows().contains(arrow)) {
            throw new TransformationException("The diagram does not contain the arrow.");
        }
    }
    

    private void assertTargetArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	if (! targetArrow.getArrow().arrowType().equals("solid") ){
    		throw new TransformationException("The type of arrow must be solid.");
    	}
    	
    	if (! currentDiagram.arrowSourceSpider(targetArrow.getArrow())){
    		throw new TransformationException("The source of arrow must be a spider.");
    	}
    	
    	if (! currentDiagram.arrowTargetContour(targetArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a contour.");
    	}
    	
    	if (currentDiagram.getArrowCardinalities().get(targetArrow.getArrow()) != null ){
    		throw new TransformationException("The arrow cannot have cardinality.");
    	}
    }
    
    
    private void assertDestinationArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (! destinationArrow.getArrow().arrowSource().equals(targetArrow.getArrow().arrowSource())){
    		throw new TransformationException("The arrows must have the same source.");
    	}
    	
    	if (! currentDiagram.arrowTargetSpider(destinationArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a spider.");
    	}
    	
    	
    	if (destinationArrow.getArrow().arrowTarget().equals(destinationArrow.getArrow().arrowSource())){
    		throw new TransformationException("The arrows must have a different spider as its target.");
    	}
    	
    	
    	if (! destinationArrow.getArrow().arrowLabel().equals(targetArrow.getArrow().arrowLabel())){
    		throw new TransformationException("The arrows must have the same label.");
    	}
    	
    	if (destinationArrow.getArrow().getCardinality() != null ){
    		throw new TransformationException("The arrow cannot have cardinality.");
    	}
    	
    }
   
    

    
    
    

}
















