package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

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
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

public class ArrowTargetInconsistencyShadingTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final ArrowArg targetCurveArrow;
    private final ArrowArg targetSpiderOrCurveWithSpiderArrow;

    public ArrowTargetInconsistencyShadingTransformer(int indexOfParent, ArrowArg targetCurveArrow, ArrowArg targetSpiderOrCurveWithSpiderArrow ) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetCurveArrow = targetCurveArrow;
        this.targetSpiderOrCurveWithSpiderArrow = targetSpiderOrCurveWithSpiderArrow ;
    }
    
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
    		
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);

    	    PrimarySpiderDiagram targetCurveDiagram = (PrimarySpiderDiagram) InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetCurveArrow);
    	    PrimarySpiderDiagram targetSpiderOrCurveWithSpiderDiagram = (PrimarySpiderDiagram) InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetCurveArrow);
    		
    	    
    	    if((targetCurveDiagram instanceof CompleteCOPDiagram) && (targetSpiderOrCurveWithSpiderDiagram instanceof CompleteCOPDiagram)){
    	    	
    	    	CompleteCOPDiagram compTargetCurveDiagram = (CompleteCOPDiagram) targetCurveDiagram;
    	    	CompleteCOPDiagram compTargetSpiderOrCurveWithSpiderDiagram = (CompleteCOPDiagram) targetSpiderOrCurveWithSpiderDiagram;
    	    	
    	    	assertDiagramContainArrow(compTargetCurveDiagram,targetCurveArrow.getArrow());
    	    	assertTargetCurveArrowIsSuitable(compTargetCurveDiagram);
    	    	
    	    	assertDiagramContainArrow(compTargetSpiderOrCurveWithSpiderDiagram,targetSpiderOrCurveWithSpiderArrow.getArrow());
    	    	assertTargetSpiderOrCurveWithSpiderArrowIsSuitable(compTargetSpiderOrCurveWithSpiderDiagram);

    	    	if(compTargetSpiderOrCurveWithSpiderDiagram.arrowTargetContour(targetSpiderOrCurveWithSpiderArrow.getArrow())){
    	    	String[] allPossibleZones = new String[compTargetSpiderOrCurveWithSpiderDiagram.getAllContours().size()];
    	    	allPossibleZones = compTargetSpiderOrCurveWithSpiderDiagram.getAllContours().toArray(allPossibleZones);
    	    	Region regionInsideCurve = new Region(Zones.getZonesInsideAnyContour
    	    		(Zones.allZonesForContours(allPossibleZones),targetSpiderOrCurveWithSpiderArrow.getArrow().arrowTarget()));
    	    	
    	    	Boolean found =false;
    	    	for (String spider : compTargetSpiderOrCurveWithSpiderDiagram.getSpiders()){
    	    		if (compTargetSpiderOrCurveWithSpiderDiagram.getHabitats().get(spider).isSubregionOf(regionInsideCurve)){
    	    			found =true;
    	    			break;
    	    			}
    	    		}
    	    	if(! found){
    	    		throw new TransformationException("The curve that is the target of the arrow must fully contain at least one spider.");
    	    	}
    	    	}
    	    	

    	    	
    	    	if(! targetCurveArrow.getArrow().arrowSource().equals(targetSpiderOrCurveWithSpiderArrow.getArrow().arrowSource())){
    	    		throw new TransformationException("Arrows must have the same source.");
    	    	}
    	    	
    	    	if(! targetCurveArrow.getArrow().arrowLabel().equals(targetSpiderOrCurveWithSpiderArrow.getArrow().arrowLabel())){
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
    
    
    
    private void assertTargetSpiderOrCurveWithSpiderArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (targetSpiderOrCurveWithSpiderArrow.getArrow().getCardinality() != null ){
    		throw new TransformationException("The arrow cannot have cardinality.");
    	}
    	
    	if (! currentDiagram.arrowSourceSpider(targetSpiderOrCurveWithSpiderArrow.getArrow())){
    		throw new TransformationException("The source of the arrow must be a spider.");
    	}
    	
    	if (! currentDiagram.arrowTargetSpider(targetSpiderOrCurveWithSpiderArrow.getArrow()) && 
    			! currentDiagram.arrowTargetContour(targetSpiderOrCurveWithSpiderArrow.getArrow())){
    		throw new TransformationException("The target of the arrow must be a spider or a curve.");
    	}
    }
    
    
    
    
    

}
