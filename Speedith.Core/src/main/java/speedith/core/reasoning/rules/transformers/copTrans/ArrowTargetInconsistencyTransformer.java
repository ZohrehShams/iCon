package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.Arrow;
import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

public class ArrowTargetInconsistencyTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final ArrowArg targetCurveArrow;
    private final ArrowArg targetSpiderArrow;

    public ArrowTargetInconsistencyTransformer(int indexOfParent, ArrowArg targetCurveArrow, ArrowArg targetSpiderArrow) {
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

    	    PrimarySpiderDiagram targetCurveDiagram = (PrimarySpiderDiagram) InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetCurveArrow);
    	    PrimarySpiderDiagram targetSpiderDiagram = (PrimarySpiderDiagram) InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetCurveArrow);
    		
    	    
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
    	    	
    	    	if(! compTargetCurveDiagram.getSpiders().contains(targetSpiderArrow.getArrow().arrowTarget())){
    	    		throw new TransformationException("Spider"+ targetSpiderArrow.getArrow().arrowTarget() + "has to be present in both conjuncts.");
    	    	}
    	    	
    	    	Region region = compTargetCurveDiagram.getHabitats().get(targetSpiderArrow.getArrow().arrowTarget());
    	    	for (Zone zone : region.sortedZones()){
    	    		if(Zones.isZonePartOfThisContour(zone, targetCurveArrow.getArrow().arrowTarget())){
    	    			throw new TransformationException("The spider cannot have any feet in the target of the arrow.");
    	    		}		
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
    	
    	
    	if (! currentDiagram.arrowSourceSpider(targetCurveArrow.getArrow())){
    		throw new TransformationException("The source of arrow must be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(targetCurveArrow.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(targetCurveArrow.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (! currentDiagram.arrowTargetContour(targetCurveArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a curve.");
    	}
 

    }
    
    
    
    private void assertTargetSpiderArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (! currentDiagram.arrowSourceSpider(targetSpiderArrow.getArrow())){
    		throw new TransformationException("The source of arrow must be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(targetSpiderArrow.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(targetSpiderArrow.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (! currentDiagram.arrowTargetSpider(targetSpiderArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a spider.");
    	}
    	
       	if ((currentDiagram.getSpiderLabels().get(targetSpiderArrow.getArrow().arrowTarget()) == null) ||
    				(currentDiagram.getSpiderLabels().get(targetSpiderArrow.getArrow().arrowTarget()).equals("")) ){
    			throw new TransformationException("The target of the arrow must be a named spider.");
    			}
    }
    
    
    

}
