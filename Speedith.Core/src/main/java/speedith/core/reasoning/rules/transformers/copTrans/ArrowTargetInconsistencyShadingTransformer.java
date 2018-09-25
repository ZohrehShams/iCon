package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.Arrow;
import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

public class ArrowTargetInconsistencyShadingTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final ArrowArg targetCurveArrow;
    private final ArrowArg targetSpiderArrow;

    public ArrowTargetInconsistencyShadingTransformer(int indexOfParent, ArrowArg targetCurveArrow, ArrowArg targetSpiderArrow) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetCurveArrow = targetCurveArrow;
        this.targetSpiderArrow = targetSpiderArrow;
    }
    
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
    		
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);

    	    PrimarySpiderDiagram targetCurveDiagram = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetCurveArrow);
    	    PrimarySpiderDiagram targetSpiderDiagram= InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetCurveArrow);
    		
    	    
    	    if((targetCurveDiagram instanceof CompleteCOPDiagram) && (targetSpiderDiagram instanceof CompleteCOPDiagram)){
    	    	
    	    	CompleteCOPDiagram compTargetCurveDiagram = (CompleteCOPDiagram) targetCurveDiagram;
    	    	CompleteCOPDiagram compTargetSpiderDiagram = (CompleteCOPDiagram) targetSpiderDiagram;
    	    	
    	    	assertDiagramContainArrow(compTargetCurveDiagram,targetCurveArrow.getArrow());
    	    	assertTargetCurveArrowIsSuitable(compTargetCurveDiagram);
    	    	
    	    	assertDiagramContainArrow(compTargetSpiderDiagram,targetSpiderArrow.getArrow());
    	    	assertTargetSpiderArrowIsSuitable(compTargetSpiderDiagram);

    	    	if(! targetCurveArrow.getArrow().arrowSource().equals(targetSpiderArrow.getArrow().arrowSource())){
    	    		throw new TransformationException("Arrows must have the same source.");
    	    	}
    	    	
    	    	if(! targetCurveArrow.getArrow().arrowLabel().equals(targetSpiderArrow.getArrow().arrowLabel())){
    	    		throw new TransformationException("Arrows must have the same label.");
    	    	}
    	    	
    	    	return SpiderDiagrams.createFalseSD();
    	    }else return currentDiagram;
    		
    	}else return null;
    }
    
    
    
    private void assertDiagramContainArrow(CompleteCOPDiagram currentDiagram, Arrow arrow) {
        if (!currentDiagram.getArrows().contains(arrow)) {
            throw new TransformationException("The diagram does not contain the arrow.");
        }
    }
    
    
    private void assertTargetCurveArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	if (! targetCurveArrow.getArrow().arrowType().equals("solid") ){
    		throw new TransformationException("The type of arrow must be solid.");
    	}
    	
    	if (targetCurveArrow.getArrow().getCardinality() != null ){
    		throw new TransformationException("The arrow cannot have cardinality.");
    	}
    	
    	if (! currentDiagram.arrowSourceSpider(targetCurveArrow.getArrow())){
    		throw new TransformationException("The source of arrow must be a spider.");
    	}
    	
    	if (! currentDiagram.arrowTargetContour(targetCurveArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a curve.");
    	}
    	
    	String[] allPossibleZones = new String[currentDiagram.getAllContours().size()];
    	allPossibleZones = currentDiagram.getAllContours().toArray(allPossibleZones);
    	
    	for (Zone zone: Zones.allZonesForContours(allPossibleZones)){
    		if((Zones.isZonePartOfThisContour(zone,targetCurveArrow.getArrow().arrowTarget()))
    				&&(currentDiagram.getPresentZones().contains(zone))){
    			if((currentDiagram.getSpiderCountInZone(zone) != 0) 
    					|| (! currentDiagram.getShadedZones().contains(zone))){
    				throw new TransformationException("The target of arrow must be an empty curve.");
    			}
    		}
    	}

    }
    
    
    
    private void assertTargetSpiderArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (targetSpiderArrow.getArrow().getCardinality() != null ){
    		throw new TransformationException("The arrow cannot have cardinality.");
    	}
    	
    	if (! currentDiagram.arrowSourceSpider(targetSpiderArrow.getArrow())){
    		throw new TransformationException("The source of arrow must be a spider.");
    	}
    	
    	if (! currentDiagram.arrowTargetSpider(targetSpiderArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a spider.");
    	}
    }
    
    
    

}
