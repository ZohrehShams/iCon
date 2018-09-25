package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.FalseSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.util.HabitatUtils;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.ShadingTransfer;

public class ZoneContrastHabitatTransformer extends IdTransformer {
	
	private final int indexOfParent;
    private final List<ZoneArg> targetZones;

    public ZoneContrastHabitatTransformer(int indexOfParent, List<ZoneArg> targetZones) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetZones = targetZones;
    }
    
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);
    		
    	    PrimarySpiderDiagram diagramWithShadedZone = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetZones.get(0));
    	    PrimarySpiderDiagram diagramWithExtraSpiders = InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetZones.get(0));
    		
    	    if (!diagramWithShadedZone.getAllContours().equals(diagramWithExtraSpiders.getAllContours())) {
    	        throw new TransformationException("Could not apply the 'ZoneContrastHabitat' rule. The unitary diagrams do not contain the same contours.");
    	      }
    	    if (!HabitatUtils.habitatsAreSingleZoned(diagramWithShadedZone) || !HabitatUtils.habitatsAreSingleZoned(diagramWithExtraSpiders)) {
    	        throw new TransformationException("Could not apply the 'ZoneContrastHabitat' rule. The unitary diagrams contain spiders with multi-zoned habitats.");
    	      }
    	    
    	    if(! diagramWithShadedZone.getShadedZones().contains(targetZones.get(0).getZone())){
    	    	throw new TransformationException("Could not apply the 'ZoneContrastHabitat' rule. The chosen zone should be"
    	    			+ "fully shaded");
    	    }
    	    
    	    if (! (diagramWithExtraSpiders.getSpiderCountInZone(targetZones.get(0).getZone()) >
    	    diagramWithShadedZone.getSpiderCountInZone(targetZones.get(0).getZone())))
    	    		{
    	    	throw new TransformationException("Could not apply the 'ZoneContrastHabitat' rule. The number of spiders in this zone is not less than its counterpart zone.");
    	    }
    	    
    	    return combineToFalse(diagramWithShadedZone,diagramWithExtraSpiders);
    	    
//    	    if(InferenceTargetChecks.assertCurveIsNotFullyShaded(diagramWithCurve, targetContours.get(0).getContour())){
//        	    InferenceTargetChecks.assertCurveIsSpiderFree(diagramWithCurve, targetContours.get(0).getContour());
//        	    InferenceTargetChecks.assertCurveIsSpiderFree(diagramWithExtraShading, targetContours.get(0).getContour());
//        	    
//        	    //All shading in diagramWithExtraShading will be added to diagramWithCurve.
//        	    PrimarySpiderDiagram  diagramWithCurveAndExtraShading = new ShadingTransfer(diagramWithExtraShading,diagramWithCurve).transferShading(diagramWithExtraShading.getShadedZones()); 
//        	    InferenceTargetChecks.assertCurveIsFullyShaded(diagramWithCurveAndExtraShading, targetContours.get(0).getContour());
//        	    
//        	    //All the curves apart from the fully shaded one will be removed. 
//        	    SortedSet<String> curves = new TreeSet<String>();
//        	    curves.addAll(diagramWithCurveAndExtraShading.getAllContours());
//        	    curves.remove(targetContours.get(0).getContour());
//        	    PrimarySpiderDiagram singleShadedCurve = new RemoveCurvesTransformer(diagramWithCurveAndExtraShading,curves).removeCurve();
//     
//        	    return singleShadedCurve;
    	    }
    	return null;
    	}
    
    
    public static FalseSpiderDiagram combineToFalse(PrimarySpiderDiagram left, PrimarySpiderDiagram right){
    	return SpiderDiagrams.createFalseSD();
    }

    }

