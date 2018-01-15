package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.List;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.NullSpiderDiagram;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zones;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.ContourRelations;
/**
 * @author Zohreh Shams
 */
public class ContrastCurveRelationTransformer extends IdTransformer {
	
	private final int indexOfParent;
    private final List<ContourArg> targetContours;

    public ContrastCurveRelationTransformer(int indexOfParent, List<ContourArg> targetContours) {
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
            
            PrimarySpiderDiagram newDiagramWithDisjointContour = null;
            PrimarySpiderDiagram newDiagramWithoutDisjointContour = null;

            PrimarySpiderDiagram diagramWithDisjointContour = InferenceTargetExtraction.getSourceOperand(currentDiagram, indexOfParent, targetContours.get(0));
            PrimarySpiderDiagram diagramWithoutDisjointContour = InferenceTargetExtraction.getTargetOperand(currentDiagram, indexOfParent, targetContours.get(0));

            assertDiagramContainsTargetContours(diagramWithDisjointContour);
            assertDiagramContainsTargetContours(diagramWithoutDisjointContour);
            
            ContourRelations contourRelationsDiagramWithDisjointContour = new ContourRelations(diagramWithDisjointContour);
            ContourRelations contourRelationsDiagramWithoutDisjointContour = new ContourRelations(diagramWithoutDisjointContour);
            
            if (! contourRelationsDiagramWithDisjointContour.areContoursDisjoint(getTargetContours().get(0), getTargetContours().get(1))){
            	throw new TransformationException("The two curves must be disjoint.");
            }
            
            //If the first chosen contour subsumes the second one in the target diagram all the zones in the subsumed contour will be shaded in both diagrams.
        	if(contourRelationsDiagramWithoutDisjointContour.contourContainsAnother(getTargetContours().get(0), getTargetContours().get(1)) &&
        			!contourRelationsDiagramWithoutDisjointContour.contourContainsAnother(getTargetContours().get(1), getTargetContours().get(0))){
        		newDiagramWithDisjointContour =diagramWithDisjointContour.addShading(Zones.getZonesInsideAnyContour
        				(diagramWithDisjointContour.getPresentZones(),getTargetContours().get(1)));
        		newDiagramWithoutDisjointContour = diagramWithoutDisjointContour.addShading(Zones.getZonesInsideAnyContour
        				(diagramWithoutDisjointContour.getPresentZones(),getTargetContours().get(1)));
        	}
        	
            //If the two chosen contour subsume each other (i.e. they are the same)  all the zones in both contours will be shaded in both diagrams.
        	if(contourRelationsDiagramWithoutDisjointContour.contourContainsAnother(getTargetContours().get(0), getTargetContours().get(1)) &&
        			contourRelationsDiagramWithoutDisjointContour.contourContainsAnother(getTargetContours().get(1), getTargetContours().get(0))){
        		newDiagramWithDisjointContour = diagramWithDisjointContour.addShading(Zones.getZonesInsideAnyContour(diagramWithDisjointContour.getPresentZones(),getTargetContours().get(0)));
        		newDiagramWithoutDisjointContour = diagramWithoutDisjointContour.addShading(Zones.getZonesInsideAnyContour(diagramWithoutDisjointContour.getPresentZones(),getTargetContours().get(0)));
        		
        		newDiagramWithDisjointContour = newDiagramWithDisjointContour.addShading(Zones.getZonesInsideAnyContour(diagramWithDisjointContour.getPresentZones(),getTargetContours().get(1)));
        		newDiagramWithoutDisjointContour = newDiagramWithoutDisjointContour.addShading(Zones.getZonesInsideAnyContour(diagramWithoutDisjointContour.getPresentZones(),getTargetContours().get(1)));
        	}
        	
            //If the second chosen contour subsumes the first one in the target diagram all the zones in the subsumed contour will be shaded in both diagrams.
        	if(!contourRelationsDiagramWithoutDisjointContour.contourContainsAnother(getTargetContours().get(0), getTargetContours().get(1)) &&
        			contourRelationsDiagramWithoutDisjointContour.contourContainsAnother(getTargetContours().get(1), getTargetContours().get(0))){
        		newDiagramWithDisjointContour = diagramWithDisjointContour.addShading(Zones.getZonesInsideAnyContour(diagramWithDisjointContour.getPresentZones(),getTargetContours().get(0)));
        		newDiagramWithoutDisjointContour = diagramWithoutDisjointContour.addShading(Zones.getZonesInsideAnyContour(diagramWithoutDisjointContour.getPresentZones(),getTargetContours().get(0)));
        	}
            
            return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, newDiagramWithDisjointContour, newDiagramWithoutDisjointContour, targetContours.get(0), indexOfParent);
        }
        return null;
    }
    

    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram csd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
        checkThisDiagramNotTarget(diagramIndex);
        return null;
    }

    @Override
    public SpiderDiagram transform(NullSpiderDiagram nsd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
        checkThisDiagramNotTarget(diagramIndex);
        return null;
    }


    private void assertDiagramContainsTargetContours(PrimarySpiderDiagram currentDiagram) {
        if (!currentDiagram.getAllContours().containsAll(getTargetContours())) {
            throw new TransformationException("The target diagram does not contain the target curve.");
        }
    }
    

    private List<String> getTargetContours() {
        ArrayList<String> contours = new ArrayList<>();
        for (ContourArg targetContour : targetContours) {
            contours.add(targetContour.getContour());
        }
        return contours;
    }

    private void checkThisDiagramNotTarget(int diagramIndex) {
        if (diagramIndex == indexOfParent) {
            throw new TransformationException("The target of the rule must be a conjunctive compound diagram.");
        }
    }

}
