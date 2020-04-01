package speedith.core.reasoning.rules.transformers.util;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;

public class InferenceTargetChecks {
    public static void assertIsConjunction(CompoundSpiderDiagram currentDiagram) {
        if (currentDiagram.getOperator() != Operator.Conjunction) {
            throw new TransformationException("The target of the inference rule is not a conjunctive compound diagram.");
        }
    }

    public static void assertIsDisjunction(CompoundSpiderDiagram currentDiagram) {
        if (currentDiagram.getOperator() != Operator.Disjunction) {
            throw new TransformationException("The target of the inference rule is not a disjunctive compound diagram.");
        }
    }


    public static void assertOperandsAreUnitary(CompoundSpiderDiagram compoundSpiderDiagram) {
        if (!(compoundSpiderDiagram.getOperand(0) instanceof PrimarySpiderDiagram) ||
            !(compoundSpiderDiagram.getOperand(1) instanceof PrimarySpiderDiagram)) {
            throw new TransformationException("The conjunctively connected spider diagrams are not unitary.");
        }
    }
    
    
    public static void assertOperandsAreUnitaryOrConceptDiagram(CompoundSpiderDiagram compoundSpiderDiagram) {
    	if ((!(compoundSpiderDiagram.getOperand(0) instanceof PrimarySpiderDiagram) &&
    	!(compoundSpiderDiagram.getOperand(0) instanceof ConceptDiagram)) ||
    	(!(compoundSpiderDiagram.getOperand(1) instanceof PrimarySpiderDiagram) && 
    	!(compoundSpiderDiagram.getOperand(1) instanceof ConceptDiagram))){
    		throw new TransformationException("The conjunctively connected spider diagrams are not unitary or concept diagram.");
    	}
    }
     
    
    public static void assertCurveIsFullyShaded(PrimarySpiderDiagram psd, String curve) {
  		for (Zone zone : Zones.getZonesInsideAnyContour(psd.getPresentZones(),curve)){
			if (! psd.getShadedZones().contains(zone)){
				throw new TransformationException("The curve is not fully shaded.");
			}
		}
    }
    
    
    public static Boolean assertCurveIsNotFullyShaded(PrimarySpiderDiagram psd, String curve) {
  		for (Zone zone : Zones.getZonesInsideAnyContour(psd.getPresentZones(),curve)){
  			if (! psd.getShadedZones().contains(zone)){
				return true;
			}
  		}
		return false;
    }
    
    
    public static void assertCurveIsSpiderFree(PrimarySpiderDiagram psd, String curve) {
    	for (Zone zone : Zones.getZonesInsideAnyContour(psd.getPresentZones(),curve)){
    		if (psd.getSpiderCountInZone(zone) != 0){
    			throw new TransformationException("The source of arrow should not conatin any spiders.");
    		}
    	}
    	
    }
    
    
    public static void assertDiagramContainArrow(SpiderDiagram currentDiagram, Arrow arrow) {
    	if (currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram compCur = (CompleteCOPDiagram) currentDiagram;
            if (! compCur.getArrows().contains(arrow)) {
                throw new TransformationException("The target diagram does not contain the target arrow.");
            }
    	}
    	
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cdCur = (ConceptDiagram) currentDiagram;
            if (! cdCur.get_cd_Arrows().contains(arrow)) {
                throw new TransformationException("The target diagram does not contain the target arrow.");
            }
    	}
    }
    
    
    public static void assertSourceOfArrowIsCurve(SpiderDiagram currentDiagram, Arrow arrow) {
    	if (currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram compCur = (CompleteCOPDiagram) currentDiagram;
        	if (! compCur.arrowSourceContour(arrow)){
        		throw new TransformationException("The source of arrow must be a curve.");
        	}
    	}
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cdCur = (ConceptDiagram) currentDiagram;
        	if (! cdCur.arrowSourceContour(arrow)){
        		throw new TransformationException("The source of arrow must be a curve.");
        	}
    	}

    }
    
    
    public static void assertTargetOfArrowIsNamedSpiderOrUnamedCurve(CompleteCOPDiagram currentDiagram, Arrow arrow) {
    	if (currentDiagram.arrowTargetSpider(arrow)){
    		if ((currentDiagram.getSpiderLabels().get(arrow.arrowTarget()) == null) ||
    				(currentDiagram.getSpiderLabels().get(arrow.arrowTarget()).equals("")) ){
    			throw new TransformationException("The target of the arrow must be a named spider or an unnamed curve.");
    		}
    	}else{  if ((currentDiagram.getCurveLabels().get(arrow.arrowTarget()) != null) &&
				(! currentDiagram.getCurveLabels().get(arrow.arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider or an unnamed curve.");
		}	
    	}	
    }
    
    public static void assertTargetOfArrowIsNamedSpiderOrUnamedCurve(ConceptDiagram currentDiagram, Arrow arrow) {
    	if (currentDiagram.arrowTargetSpider(arrow)){
    		if ((currentDiagram.getAllSpiderLabels().get(arrow.arrowTarget()) == null) ||
    				(currentDiagram.getAllSpiderLabels().get(arrow.arrowTarget()).equals("")) ){
    			throw new TransformationException("The target of the arrow must be a named spider or an unnamed curve.");
    		}
    	}else{ if ((currentDiagram.getAllCurveLabels().get(arrow.arrowTarget()) != null) &&
				(! currentDiagram.getAllCurveLabels().get(arrow.arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider or an unnamed curve.");
		}	
    	}	
    }
    
    
    public static void assertDiagramContainSpider(SpiderDiagram currentDiagram, String spider) {
    	if (currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram compCur = (CompleteCOPDiagram) currentDiagram;
            if (!compCur.getSpiders().contains(spider)) {
                throw new TransformationException("The target diagram does not contain the target spider.");
            }
    	}
    		
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cdCur = (ConceptDiagram) currentDiagram;
            if (!cdCur.getAllSpiders().contains(spider)) {
                throw new TransformationException("The target diagram does not contain the target spider.");
            }
    	}
    }
    
    
    public static void assertSpiderIsInArrowSource(CompleteCOPDiagram currentDiagram, Arrow arrow, String spider) {
    	String[] allPossibleZones = new String[currentDiagram.getAllContours().size()];
    	allPossibleZones = currentDiagram.getAllContours().toArray(allPossibleZones);
    	Region regionInsideCurve = new Region(Zones.getZonesInsideAnyContour
    		(Zones.allZonesForContours(allPossibleZones),arrow.arrowSource()));
    	
    	if(! currentDiagram.getHabitats().get(spider).isSubregionOf(regionInsideCurve)){
    		throw new TransformationException("The target spider is not in the curve that is the source of selected arrow.");
    	}
    }
    
    
    public static void assertSpiderIsInArrowSource(ConceptDiagram conceptDiagram,Arrow arrow, String spider) {
    	CompleteCOPDiagram currentDiagram = (CompleteCOPDiagram) conceptDiagram.sourceDiagram(arrow);
    	
    	String[] allPossibleZones = new String[currentDiagram.getAllContours().size()];
    	allPossibleZones = currentDiagram.getAllContours().toArray(allPossibleZones);
    	Region regionInsideCurve = new Region(Zones.getZonesInsideAnyContour
    		(Zones.allZonesForContours(allPossibleZones),arrow.arrowSource()));
    	
    	if(! currentDiagram.getHabitats().get(spider).isSubregionOf(regionInsideCurve)){
    		throw new TransformationException("The target spider is not in the curve that is the source of selected arrow.");
    	}
    }
    
    
//    public static void assertDiagramContainCurve(CompleteCOPDiagram currentDiagram, String curve) {
//        if (!currentDiagram.getAllContours().contains(curve)) {
//            throw new TransformationException("The diagram does not contain the curve.");
//        }
//    }
    
    
    public static void assertDiagramContainCurve(SpiderDiagram currentDiagram, String curve) {
    	if (currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram compCur = (CompleteCOPDiagram) currentDiagram;
            if (! compCur.getAllContours().contains(curve)) {
                throw new TransformationException("The diagram does not contain the curve.");
            }
    	}
    	
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cdCur = (ConceptDiagram) currentDiagram;
            if (! cdCur.getAllContours().contains(curve)) {
            	throw new TransformationException("The diagram does not contain the curve.");
            }
    	}
    }
    
    
    public static void assertCurveIsNamed(SpiderDiagram currentDiagram, String curve) {
    	if (currentDiagram instanceof CompleteCOPDiagram){
    		CompleteCOPDiagram compCur = (CompleteCOPDiagram) currentDiagram;
            if ((compCur.getCurveLabels().get(curve) == null) ||
        			(compCur.getCurveLabels().get(curve) == "")) {
                throw new TransformationException("The chosen curve cannot be unamed.");
            }
    	}
    	
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cdCur = (ConceptDiagram) currentDiagram;
            if ((cdCur.getAllCurveLabels().get(curve) == null) ||
        			(cdCur.getAllCurveLabels().get(curve) == "")) {
            	throw new TransformationException("The chosen curve cannot be unamed.");
            }
    	}
    }
    

    
}