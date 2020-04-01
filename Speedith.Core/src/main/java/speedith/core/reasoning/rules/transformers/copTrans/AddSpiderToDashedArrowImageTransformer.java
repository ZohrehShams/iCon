package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.TreeSet;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.ContourRelations;
import speedith.core.reasoning.util.unitary.ZoneTransfer;

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
            InferenceTargetChecks.assertOperandsAreUnitaryOrConceptDiagram(currentDiagram);

    	    SpiderDiagram arrowDiagram = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, firstArrow);
    	    SpiderDiagram  spiderDiagram= InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, firstArrow);
    	    
    	    InferenceTargetChecks.assertDiagramContainArrow(arrowDiagram,firstArrow.getArrow());
    	    assertFirstArrowIsSuitable(arrowDiagram);
    	    InferenceTargetChecks.assertDiagramContainCurve(arrowDiagram,curve.getContour());
    	    InferenceTargetChecks.assertCurveIsNamed(arrowDiagram,curve.getContour());
    	    
    	    InferenceTargetChecks.assertDiagramContainArrow(spiderDiagram,secondArrow.getArrow());
    	    assertSecondArrowIsSuitable(spiderDiagram);
    	    
    		assertSpiderNotInDestination(arrowDiagram,secondArrow.getArrow().arrowTarget());
    		    		   		
    		
    		if(arrowDiagram instanceof CompleteCOPDiagram){
    			CompleteCOPDiagram compArrowDiagram = (CompleteCOPDiagram) arrowDiagram;
    			
    	    	String[] allPossibleZones = new String[compArrowDiagram.getAllContours().size()];
    	    	allPossibleZones = compArrowDiagram.getAllContours().toArray(allPossibleZones);
    	    	Region regionInsideArrowTarget = new Region(Zones.getZonesInsideAnyContour
    	    		(Zones.allZonesForContours(allPossibleZones),firstArrow.getArrow().arrowTarget()));
    	    	ArrayList<Zone> nonShadedZonesInsideArrowTarget = new ArrayList<Zone>();
    	    	for(Zone zone: regionInsideArrowTarget.sortedZones()){
    	    		if(! compArrowDiagram.getShadedZones().contains(zone)){
    	    			nonShadedZonesInsideArrowTarget.add(zone);
    	    		}
    	    	}
    	    	Region nonShadedRegionInsideArrowTarget = new Region(nonShadedZonesInsideArrowTarget);
    	    	
    	    	if(spiderDiagram instanceof CompleteCOPDiagram){
    	    		CompleteCOPDiagram compSpiderDiagram = (CompleteCOPDiagram) spiderDiagram;
        	    	CompleteCOPDiagram transformedArrowDiagram = (CompleteCOPDiagram) compArrowDiagram.addLUSpider(secondArrow.getArrow().arrowTarget(), nonShadedRegionInsideArrowTarget, 
        	    			compSpiderDiagram.getSpiderLabels().get(secondArrow.getArrow().arrowTarget()));
                    return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedArrowDiagram, compSpiderDiagram, firstArrow, indexOfParent);
    	    	}else{
    	    		ConceptDiagram cdSpiderDiagram = (ConceptDiagram) spiderDiagram;
        	    	CompleteCOPDiagram transformedArrowDiagram = (CompleteCOPDiagram) compArrowDiagram.addLUSpider(secondArrow.getArrow().arrowTarget(), nonShadedRegionInsideArrowTarget, 
        	    			cdSpiderDiagram.getAllSpiderLabels().get(secondArrow.getArrow().arrowTarget()));
                    return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedArrowDiagram, cdSpiderDiagram, firstArrow.getSubDiagramIndex(), indexOfParent);

    	    	}

    		}else if(arrowDiagram instanceof ConceptDiagram){
    			ConceptDiagram cdArrowDiagram = (ConceptDiagram) arrowDiagram;
    			CompleteCOPDiagram arrowTargetDiagram = (CompleteCOPDiagram) cdArrowDiagram.targetDiagram(firstArrow.getArrow());
    			
    	    	String[] allPossibleZones = new String[arrowTargetDiagram.getAllContours().size()];
    	    	allPossibleZones = arrowTargetDiagram.getAllContours().toArray(allPossibleZones);
    	    	Region regionInsideArrowTarget = new Region(Zones.getZonesInsideAnyContour
    	    		(Zones.allZonesForContours(allPossibleZones),firstArrow.getArrow().arrowTarget()));
    	    	ArrayList<Zone> nonShadedZonesInsideArrowTarget = new ArrayList<Zone>();
    	    	for(Zone zone: regionInsideArrowTarget.sortedZones()){
    	    		if(! arrowTargetDiagram.getShadedZones().contains(zone)){
    	    			nonShadedZonesInsideArrowTarget.add(zone);
    	    		}
    	    	}
    	    	Region nonShadedRegionInsideArrowTarget = new Region(nonShadedZonesInsideArrowTarget);
    			
    	    	if(spiderDiagram instanceof CompleteCOPDiagram){
    	    		CompleteCOPDiagram compSpiderDiagram = (CompleteCOPDiagram) spiderDiagram;
    	    		
                	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cdArrowDiagram.getPrimaries());
                	int indexOfTransformedChild = transformedChildren.indexOf(arrowTargetDiagram);
                	CompleteCOPDiagram transformedChild = (CompleteCOPDiagram) arrowTargetDiagram.addLUSpider(secondArrow.getArrow().arrowTarget(), nonShadedRegionInsideArrowTarget, 
      	    			compSpiderDiagram.getSpiderLabels().get(secondArrow.getArrow().arrowTarget()));
                	transformedChildren.set(indexOfTransformedChild, transformedChild);
                	ConceptDiagram transformedConceptDiagram = SpiderDiagrams.createConceptDiagram(cdArrowDiagram.get_cd_Arrows(), transformedChildren, false);
                	
                	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedConceptDiagram, compSpiderDiagram, firstArrow.getSubDiagramIndex(), indexOfParent);
    	    	}else{
    	    		ConceptDiagram cdSpiderDiagram = (ConceptDiagram) spiderDiagram;
    	    		
                	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cdArrowDiagram.getPrimaries());
                	int indexOfTransformedChild = transformedChildren.indexOf(arrowTargetDiagram);
                	CompleteCOPDiagram transformedChild = (CompleteCOPDiagram) arrowTargetDiagram.addLUSpider(secondArrow.getArrow().arrowTarget(), nonShadedRegionInsideArrowTarget, 
                			cdSpiderDiagram.getAllSpiderLabels().get(secondArrow.getArrow().arrowTarget()));
                	transformedChildren.set(indexOfTransformedChild, transformedChild);
                	ConceptDiagram transformedConceptDiagram = SpiderDiagrams.createConceptDiagram(cdArrowDiagram.get_cd_Arrows(), transformedChildren, false);
                	
                	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedConceptDiagram, cdSpiderDiagram, firstArrow.getSubDiagramIndex(), indexOfParent);

    	    	}
    			
    		}else return currentDiagram;
    		
    	}else return null;
    }
    
    
    

    private void assertFirstArrowIsSuitable(SpiderDiagram currentDiagram){
    	if (! firstArrow.getArrow().arrowType().equals("dashed") ){
    		throw new TransformationException("The type of arrow must be dashed.");
    	}
    	
    	if(currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram currentCompCop = (CompleteCOPDiagram) currentDiagram;
    		
	    	if (currentCompCop.getArrowCardinalities().get(firstArrow.getArrow()) != null ){
	    		throw new TransformationException("The arrow should not have any cardinality.");
	    	}
	    	
	    	if (! currentCompCop.arrowSourceSpider(firstArrow.getArrow())){
	    		throw new TransformationException("The source of arrow must be a spider.");
	    	}
	    	
	    	String source = firstArrow.getArrow().arrowSource();
	    	if ((currentCompCop.getSpiderLabels().get(source) == null) ||
	    			(currentCompCop.getSpiderLabels().get(source) == "")){
	    		throw new TransformationException("The source of arrow must be a named spider.");
	    	}
	    	
	    	if (! currentCompCop.arrowTargetContour(firstArrow.getArrow())){
	    		throw new TransformationException("The target of arrow must be a curve.");
	    	}
	    	
	    	String target = firstArrow.getArrow().arrowTarget();
	    	if ((currentCompCop.getCurveLabels().get(target) != null) &&
	    			(currentCompCop.getCurveLabels().get(target) != "")){
	    		throw new TransformationException("The target of arrow must be an unamed curve.");
	    	}
	    	
	    	ContourRelations contourRelations = new ContourRelations(currentCompCop);
	    	if(! contourRelations.contourContainsAnother(curve.getContour(), target)){
	    		throw new TransformationException("The target of arrow must be contained in " + curve.getContour() +"." );}
    	}else{
    		ConceptDiagram currentCd = (ConceptDiagram) currentDiagram;
    	
	    	if (! currentCd.arrowSourceSpider(firstArrow.getArrow())){
	    		throw new TransformationException("The source of arrow must be a spider.");
	    	}
	    	
	    	String source = firstArrow.getArrow().arrowSource();
	    	if ((currentCd.getAllSpiderLabels().get(source) == null) ||
	    			(currentCd.getAllSpiderLabels().get(source) == "")){
	    		throw new TransformationException("The source of arrow must be a named spider.");
	    	}
	    	
	    	if (! currentCd.arrowTargetContour(firstArrow.getArrow())){
	    		throw new TransformationException("The target of arrow must be a curve.");
	    	}
	    	
	    	String target = firstArrow.getArrow().arrowTarget();
	    	if ((currentCd.getAllCurveLabels().get(target) != null) &&
	    			(currentCd.getAllCurveLabels().get(target) != "")){
	    		throw new TransformationException("The target of arrow must be an unamed curve.");
	    	}
	    	
	    	
	    	CompleteCOPDiagram arrowTargetDiagram = (CompleteCOPDiagram) currentCd.targetDiagram(firstArrow.getArrow());
	    	ContourRelations contourRelations = new ContourRelations(arrowTargetDiagram);
	    	if(! contourRelations.contourContainsAnother(curve.getContour(), target)){
	    		throw new TransformationException("The target of arrow must be contained in " + curve.getContour() +"." );
	    	}
	    	}
    }
    	
    	
        
    
    private void assertSecondArrowIsSuitable(SpiderDiagram currentDiagram){
    	
    	if (! secondArrow.getArrow().arrowSource().equals(firstArrow.getArrow().arrowSource())){
    		throw new TransformationException("The arrows must have the same source.");
    	}
    	
    	if (! secondArrow.getArrow().arrowLabel().equals(firstArrow.getArrow().arrowLabel())){
    		throw new TransformationException("The arrows must have the same label.");
    	}

    	if(currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram currentCompCop = (CompleteCOPDiagram) currentDiagram;
    		
        	if (! currentCompCop.arrowTargetSpider(secondArrow.getArrow())){
        		throw new TransformationException("The target of arrow must be a spider.");
        	}
        	
        	//Every zone in the habitat of the spider has to be a part of the chosen curve.
        	Region spiderHabitat = currentCompCop.getHabitats().get(secondArrow.getArrow().arrowTarget());
        	for (Zone zone: spiderHabitat.sortedZones()){
        		if(! Zones.isZonePartOfThisContour(zone, curve.getContour())){
        			throw new TransformationException("The spider cannot have any feet outside" + curve.getContour() + ".");
        		}
        	}
    		
    		
    	}else{
    		ConceptDiagram currentCd = (ConceptDiagram) currentDiagram;
    		
        	if (! currentCd.arrowTargetSpider(secondArrow.getArrow())){
        		throw new TransformationException("The target of arrow must be a spider.");
        	}
        	
        	CompleteCOPDiagram arrowTargetDiagram = (CompleteCOPDiagram) currentCd.targetDiagram(secondArrow.getArrow());
        	
        	//Every zone in the habitat of the spider has to be a part of the chosen curve.
        	Region spiderHabitat = arrowTargetDiagram.getHabitats().get(secondArrow.getArrow().arrowTarget());
        	for (Zone zone: spiderHabitat.sortedZones()){
        		if(! Zones.isZonePartOfThisContour(zone, curve.getContour())){
        			throw new TransformationException("The spider cannot have any feet outside" + curve.getContour() + ".");
        		}
        	}
    		
    	}

    	
    }
   
    

    private void assertSpiderNotInDestination(SpiderDiagram currentDiagram, String spider){
    	
    	if(currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram currentCompCop = (CompleteCOPDiagram) currentDiagram;
	    	if (currentCompCop.getSpiders().contains(secondArrow.getArrow().arrowTarget())){
	    		throw new TransformationException("The spider being added to the dashed arrow image cannot exists in the destination diagram.");
	    	}
    	}else{
    		ConceptDiagram currentCd = (ConceptDiagram) currentDiagram;
	    	if (currentCd.getAllSpiders().contains(secondArrow.getArrow().arrowTarget())){
	    		throw new TransformationException("The spider being added to the dashed arrow image cannot exists in the destination diagram.");
	    	}
    		
    	}
    		
    	
    }
    
    
    

}
















