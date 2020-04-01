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

public class ArrowCardinalityInconsTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final ArrowArg targetArrow;
    private final ArrowArg destinationArrow;

    public ArrowCardinalityInconsTransformer(int indexOfParent, ArrowArg targetArrow, ArrowArg destinationArrow) {
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

    	    PrimarySpiderDiagram arrowCurveDiagram = (PrimarySpiderDiagram) InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetArrow);
    	    PrimarySpiderDiagram  arrowSpiderDiagram= (PrimarySpiderDiagram) InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetArrow);
    		
    	    
    	    if((arrowCurveDiagram instanceof CompleteCOPDiagram) && (arrowSpiderDiagram instanceof CompleteCOPDiagram)){
    	    	
    	    	CompleteCOPDiagram compArrowCurveDiagram = (CompleteCOPDiagram) arrowCurveDiagram;
    	    	CompleteCOPDiagram compArrowSpiderDiagram = (CompleteCOPDiagram) arrowSpiderDiagram;
    	    	
    	    	assertDiagramContainTargetArrow(compArrowCurveDiagram,targetArrow.getArrow());
    	    	assertTargetArrowIsSuitable(compArrowCurveDiagram);
    	    	
    	    	assertDiagramContainTargetArrow(compArrowSpiderDiagram,destinationArrow.getArrow());
    	    	assertDestinationArrowIsSuitable(compArrowSpiderDiagram);
    	    	
    	    	
    	    	if (! destinationArrow.getArrow().arrowLabel().equals(targetArrow.getArrow().arrowLabel())){
    	    		throw new TransformationException("The arrows must have the same label.");
    	    	}
    	    	
    	    	
    	    	if (! compArrowCurveDiagram.getSpiders().contains(destinationArrow.getArrow().arrowSource()) ||
    	    			! compArrowCurveDiagram.getSpiders().contains(destinationArrow.getArrow().arrowTarget())){
    	    		throw new TransformationException("The spiders that are the source and target of the destination arrow should be present"
    	    				+ "in the target diagram.");
    	    	}
    	    	
    	    	
    	    	if (! compArrowCurveDiagram.getSpiders().contains(destinationArrow.getArrow().arrowSource())){
    	    		throw new TransformationException("The spider that is the target of arrow cannot exists in the"
    	    				+ "source diagram.");
    	    	}
    	    	
    	    	
    	    	ArrayList<Zone> zonesInsideTargetArrowSource = new ArrayList<Zone>();
    	    	String[] allPossibleZonesInSource = new String[compArrowCurveDiagram.getAllContours().size()];
    	    	allPossibleZonesInSource = compArrowCurveDiagram.getAllContours().toArray(allPossibleZonesInSource);
    	    	for (Zone zone: Zones.allZonesForContours(allPossibleZonesInSource)){
    	    		if((Zones.isZonePartOfThisContour(zone,targetArrow.getArrow().arrowSource()))
    	    				&&(compArrowCurveDiagram.getPresentZones().contains(zone))){
    	    			zonesInsideTargetArrowSource.add(zone);
    	    		}
    	    	}
    	    	Region regionInsideArrowSource = new Region(zonesInsideTargetArrowSource);
    	    	if (! compArrowCurveDiagram.getHabitats().get(destinationArrow.getArrow().arrowSource()).isSubregionOf(regionInsideArrowSource)){
    	    		throw new TransformationException("The spider that is the source of the destination arrow has to be contained in the"
    	    				+ "source of the target arrow.");
    	    	}
    	    	
    	    	
    	    	ArrayList<Zone> zonesInsideTargetArrowTarget = new ArrayList<Zone>();
    	    	String[] allPossibleZonesInTarget = new String[compArrowCurveDiagram.getAllContours().size()];
    	    	allPossibleZonesInTarget = compArrowCurveDiagram.getAllContours().toArray(allPossibleZonesInTarget);
    	    	for (Zone zone: Zones.allZonesForContours(allPossibleZonesInTarget)){
    	    		if((Zones.isZonePartOfThisContour(zone,targetArrow.getArrow().arrowTarget()))
    	    				&&(compArrowCurveDiagram.getPresentZones().contains(zone))){
    	    			zonesInsideTargetArrowTarget.add(zone);
    	    		}
    	    	}
    	    	Region regionInsideArrowTarget = new Region(zonesInsideTargetArrowTarget);
    	    	if (! compArrowCurveDiagram.getHabitats().get(destinationArrow.getArrow().arrowTarget()).isSubregionOf(regionInsideArrowTarget)){
    	    		throw new TransformationException("The spider that is the target of the destination arrow has to be contained in the"
    	    				+ "target of the target arrow.");
    	    	}
    	    	
    	    	
    	    	return SpiderDiagrams.createFalseSD();


    	    }else return currentDiagram;
    		
    	}else return null;
    }
    
    
    
    
    private void assertDiagramContainTargetArrow(CompleteCOPDiagram currentDiagram, Arrow arrow) {
        if (!currentDiagram.getArrows().contains(arrow)) {
            throw new TransformationException("The diagram does not contain the arrow.");
        }
    }
    

    private void assertTargetArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (! currentDiagram.arrowSourceContour(targetArrow.getArrow())){
    		throw new TransformationException("The source of the target arrow must be a curve.");
    	}
    	
    	if (! currentDiagram.arrowTargetContour(targetArrow.getArrow())){
    		throw new TransformationException("The target of the target arrow must be a curve.");
    	}
    	

  		if( (! currentDiagram.getArrowCardinalities().get(targetArrow.getArrow()).getComparator().equals("<=")) ||
  				( currentDiagram.getArrowCardinalities().get(targetArrow.getArrow()).getNumber() != 0 )){
			throw new TransformationException("The target arrow has to have the cardinality of less or equal to 0.");
		}
    	
    	


    }
    
    
    private void assertDestinationArrowIsSuitable(CompleteCOPDiagram currentDiagram){
    	
    	if (! currentDiagram.arrowSourceSpider(destinationArrow.getArrow())){
    		throw new TransformationException("The source of the destination arrow must be a spider.");
    	}
    	
    	if (! currentDiagram.arrowTargetSpider(destinationArrow.getArrow())){
    		throw new TransformationException("The target of the destination arrow must be a spider.");
    	}
    	
    	if(currentDiagram.getArrowCardinalities().get(destinationArrow.getArrow()) !=null){
    		throw new TransformationException("The destination arrow cannot have cardinality.");
    	}
    	
    }
   
    


    
    

}
















