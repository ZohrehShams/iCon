package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.util.unitary.AddCurve;

public class DashedToInverseArrowTransformer extends IdTransformer {
	
	private ArrowArg arrowArg;
    private final boolean applyForward;

    public DashedToInverseArrowTransformer(ArrowArg arrowArg,boolean applyForward) {
    	this.arrowArg = arrowArg;
    	this.applyForward = applyForward;
    }
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = arrowArg.getSubDiagramIndex();
    	if (diagramIndex == subDiagramIndex) {
        	if(psd instanceof CompleteCOPDiagram){
        		
        		CompleteCOPDiagram compCop = (CompleteCOPDiagram) psd;
        		assertDiagramContainTargetArrow(compCop);
        		assertSuitabilityOfArrow(compCop);
 
        		//Delete the spider that is the source of the arrow. This will automatically delete the arrow.
        		Arrow oldArrow = arrowArg.getArrow();
        		CompleteCOPDiagram compCopNoSpider = (CompleteCOPDiagram) compCop.deleteSpider(oldArrow.arrowSource());
        		
        		
        		//Adding the unnamed curve (it uses introduce contour principle).
        		ArrayList<String> newCurves = new ArrayList<String>();
        		newCurves.add("unnamedCurve");
        		AddCurve addCurve = new AddCurve(compCopNoSpider,newCurves);
        		CompleteCOPDiagram compCopNoSpiderUnCurve = (CompleteCOPDiagram) addCurve.addingCurve();
        		
        		//Adding back the deleted spider such that the habitat is the new unnamed curve.
    	    	ArrayList<Zone> zonesIniseArrowTarget = new ArrayList<Zone>();
    	    	String[] allPossibleZones = new String[compCopNoSpiderUnCurve.getAllContours().size()];
    	    	allPossibleZones = compCopNoSpiderUnCurve.getAllContours().toArray(allPossibleZones);
    	    	for (Zone zone: Zones.allZonesForContours(allPossibleZones)){
    	    		if((Zones.isZonePartOfThisContour(zone,"unnamedCurve"))
    	    				&&(compCopNoSpiderUnCurve.getPresentZones().contains(zone))){
    	    			zonesIniseArrowTarget.add(zone);
    	    		}
    	    	}
    	    	Region regionInsideUnnamedCurve = new Region(zonesIniseArrowTarget);
    	    	CompleteCOPDiagram compCopNoSpiderUnCurveSpider= (CompleteCOPDiagram) 
    	    			compCopNoSpiderUnCurve.addLUSpider(oldArrow.arrowSource(), regionInsideUnnamedCurve, 
    	    			compCop.getSpiderLabels().get(oldArrow.arrowSource()));
   
        		//Add the new Arrow. 
        		String newLabel = oldArrow.arrowLabel() + "-" ;
        		Arrow newArrow = new Arrow(oldArrow.arrowTarget(),"unnamedCurve","solid",newLabel);
        		CompleteCOPDiagram compCopNoSpiderUnCurveSpiderArrow = (CompleteCOPDiagram) compCopNoSpiderUnCurveSpider.addArrow(newArrow);
            	
            	return compCopNoSpiderUnCurveSpiderArrow;
        	}
        	else return psd;
    	}
    	else return null;
    }
    
    
    
    @Override
    public SpiderDiagram transform(ConceptDiagram cd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	// TODO Auto-generated method stub
    	return null;
    	
    }
    
    
    private void assertDiagramContainTargetArrow(CompleteCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow.");
        }
    }
    
    
    
    private void assertSuitabilityOfArrow(CompleteCOPDiagram currentDiagram){
    	
    	if (! arrowArg.getArrow().arrowType().equals("dashed")){
    		throw new TransformationException("The arrow has to be dashed.");
    	}
    	
    	if (!currentDiagram.arrowSourceSpider(arrowArg.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetSpider(arrowArg.getArrow())){
    		throw new TransformationException("The target of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowTarget()) == null) ||
				(currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider.");
		}
    	
    	if (currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()) != null){
    		throw new TransformationException("The arrow should not have any cardinality.");
    	}
    	
    }

   

    



}
