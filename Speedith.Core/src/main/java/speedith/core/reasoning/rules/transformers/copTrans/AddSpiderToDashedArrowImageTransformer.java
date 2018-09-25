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
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.ContourRelations;

public class AddSpiderToDashedArrowImageTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final ArrowArg firstArrow;
    private final ContourArg curve;
    private final ArrowArg secondArrow;

    public AddSpiderToDashedArrowImageTransformer(int indexOfParent, ArrowArg firstArrow, ContourArg curve, 
    		ArrowArg secondArrow) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.firstArrow = firstArrow;
        this.curve = curve;
        this.secondArrow = secondArrow;
    }
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
    		
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);

    	    PrimarySpiderDiagram arrowDiagram = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, firstArrow);
    	    PrimarySpiderDiagram  spiderDiagram= InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, firstArrow);
    		
    	    
    	    if((arrowDiagram instanceof CompleteCOPDiagram) && (spiderDiagram instanceof CompleteCOPDiagram)){
    	    	
    	    	CompleteCOPDiagram compArrowDiagram = (CompleteCOPDiagram) arrowDiagram;
    	    	CompleteCOPDiagram compSpiderDiagram = (CompleteCOPDiagram) spiderDiagram;
    	    	
    	    	
    	    	
    	    	assertDiagramContainArrow(compArrowDiagram,firstArrow.getArrow());
    	    	assertFirstArrowIsSuitable(compArrowDiagram);
    	    	assertDiagramContainCurve(compArrowDiagram,curve.getContour());
    	    	assertCurveIsSuitable(compArrowDiagram);
    	    	
    	    	
    	    	assertDiagramContainArrow(compSpiderDiagram,secondArrow.getArrow());
    	    	assertSecondArrowIsSuitable(compSpiderDiagram);
    	    	
    	    	if (! compArrowDiagram.getSpiders().contains(secondArrow.getArrow().arrowSource())){
    	    		throw new TransformationException("The spider that is the target of arrow cannot exists in the"
    	    				+ "source diagram.");
    	    	}
    	    	    	    	
    	    	ArrayList<Zone> zonesIniseArrowTarget = new ArrayList<Zone>();
    	    	String[] allPossibleZones = new String[compArrowDiagram.getAllContours().size()];
    	    	allPossibleZones = compArrowDiagram.getAllContours().toArray(allPossibleZones);
    	    	
    	    	
    	    	for (Zone zone: Zones.allZonesForContours(allPossibleZones)){
    	    		if((Zones.isZonePartOfThisContour(zone,firstArrow.getArrow().arrowTarget()))
    	    				&&(compArrowDiagram.getPresentZones().contains(zone))){
    	    			zonesIniseArrowTarget.add(zone);
    	    		}
    	    	}
    	    	
    	    	Region regionInsideArrowTarget = new Region(zonesIniseArrowTarget);
    	    	

    	    	CompleteCOPDiagram transformedArrowDiagram = (CompleteCOPDiagram) compArrowDiagram.addLUSpider(secondArrow.getArrow().arrowTarget(), regionInsideArrowTarget, 
    	    			compSpiderDiagram.getSpiderLabels().get(secondArrow.getArrow().arrowTarget()));
                return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedArrowDiagram, spiderDiagram, firstArrow, indexOfParent);
    	    }else return currentDiagram;
    		
    	}else return null;
    }
    
    
    
    
    private void assertDiagramContainArrow(CompleteCOPDiagram currentDiagram, Arrow arrow) {
        if (!currentDiagram.getArrows().contains(arrow)) {
            throw new TransformationException("The diagram does not contain the arrow."+arrow.arrowTarget());
        }
    }
    
    private void assertDiagramContainCurve(CompleteCOPDiagram currentDiagram, String curve) {
        if (!currentDiagram.getAllContours().contains(curve)) {
            throw new TransformationException("The diagram does not contain the curve.");
        }
    }
    

    private void assertFirstArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	if (! firstArrow.getArrow().arrowType().equals("dashed") ){
    		throw new TransformationException("The type of arrow must be dashed.");
    	}
    	
    	if (! currentDiagram.arrowSourceSpider(firstArrow.getArrow())){
    		throw new TransformationException("The source of arrow must be a spider.");
    	}
    	
    	String source = firstArrow.getArrow().arrowSource();
    	if ((currentDiagram.getSpiderLabels().get(source) == null) ||
    			(currentDiagram.getSpiderLabels().get(source) == "")){
    		throw new TransformationException("The source of arrow must be a named spider.");
    	}
    	
    	if (! currentDiagram.arrowTargetContour(firstArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a curve.");
    	}
    	
    	String target = firstArrow.getArrow().arrowTarget();
    	if ((currentDiagram.getCurveLabels().get(target) != null) &&
    			(currentDiagram.getCurveLabels().get(target) != "")){
    		throw new TransformationException("The target of arrow must be an unamed curve.");
    	}
    	
    	ContourRelations contourRelations = new ContourRelations(currentDiagram);
    	if(! contourRelations.contourContainsAnother(curve.getContour(), target)){
    		throw new TransformationException("The target of arrow must be contained in " + curve.getContour() +"." );
    	}
    	
    	
    }
    
    
    private void assertCurveIsSuitable(CompleteCOPDiagram currentDiagram){
       	if ((currentDiagram.getCurveLabels().get(curve.getContour()) == null) ||
    			(currentDiagram.getCurveLabels().get(curve.getContour()) == "")){
    		throw new TransformationException("The chosen curve cannot be unamed.");
    	}
    }
    
    
    private void assertSecondArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (! secondArrow.getArrow().arrowSource().equals(firstArrow.getArrow().arrowSource())){
    		throw new TransformationException("The arrows must have the same source.");
    	}
    	
    	if (! secondArrow.getArrow().arrowLabel().equals(firstArrow.getArrow().arrowLabel())){
    		throw new TransformationException("The arrows must have the same label.");
    	}

    	if (! currentDiagram.arrowTargetSpider(secondArrow.getArrow())){
    		throw new TransformationException("The target of arrow must be a spider.");
    	}
    	
    	//Every zone in the habitat of the spider has to be a part of the chosen curve.
    	Region spiderHabitat = currentDiagram.getHabitats().get(secondArrow.getArrow().arrowTarget());
    	for (Zone zone: spiderHabitat.sortedZones()){
    		if(! Zones.isZonePartOfThisContour(zone, curve.getContour())){
    			throw new TransformationException("The spider cannot have any feet outside" + curve.getContour() + ".");
    		}
    	}
    	
    }
   
    

    
    
    

}
















