package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import scala.collection.immutable.Set;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.rules.RemoveContour;
import speedith.core.reasoning.rules.transformers.RemoveContoursTransformer;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.ShadingTransfer;

public class IncoherenceCurveTransformer extends IdTransformer{
	
	private final int indexOfParent;
    private final List<ContourArg> targetContours;

    public IncoherenceCurveTransformer(int indexOfParent, List<ContourArg> targetContours) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetContours = targetContours;
    }
    
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);
    		
    	    PrimarySpiderDiagram diagramWithCurve = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, targetContours.get(0));
    	    PrimarySpiderDiagram diagramWithExtraShading = InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, targetContours.get(0));
    		
    	    if(InferenceTargetChecks.assertCurveIsNotFullyShaded(diagramWithCurve, targetContours.get(0).getContour())){
        	    InferenceTargetChecks.assertCurveIsSpiderFree(diagramWithCurve, targetContours.get(0).getContour());
        	    InferenceTargetChecks.assertCurveIsSpiderFree(diagramWithExtraShading, targetContours.get(0).getContour());
        	    
        	    //All shading in diagramWithExtraShading will be added to diagramWithCurve.
        	    PrimarySpiderDiagram  diagramWithCurveAndExtraShading = new ShadingTransfer(diagramWithExtraShading,diagramWithCurve).transferShading(diagramWithExtraShading.getShadedZones()); 
        	    InferenceTargetChecks.assertCurveIsFullyShaded(diagramWithCurveAndExtraShading, targetContours.get(0).getContour());
        	    
        	    //All the curves apart from the fully shaded one will be removed. 
        	    SortedSet<String> curves = new TreeSet<String>();
        	    curves.addAll(diagramWithCurveAndExtraShading.getAllContours());
        	    curves.remove(targetContours.get(0).getContour());
        	    PrimarySpiderDiagram singleShadedCurve = new RemoveCurvesTransformer(diagramWithCurveAndExtraShading,curves).removeCurve();
     
        	    return singleShadedCurve;
    	    }

    	    }
    	return null; 
    }

}
