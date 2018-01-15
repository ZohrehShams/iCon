package speedith.core.reasoning.rules.transformers.util;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;

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
    
    
}