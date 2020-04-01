package speedith.core.reasoning.rules.transformers;

import speedith.core.lang.*;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.ZoneTransfer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CopyCurvesTransformer extends IdTransformer {

    private final int indexOfParent;
    private final List<ContourArg> targetContours;
    private final SubDiagramIndexArg targetDiagram;

    public CopyCurvesTransformer(int indexOfParent, List<ContourArg> targetContours, SubDiagramIndexArg targetDiagram) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetContours = targetContours;
        this.targetDiagram = targetDiagram;
    }


    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitaryOrConceptDiagram(currentDiagram);
       
            
            int cdParentUniversalIndex = 0;
            //When the contours are chosen from a cop inside a cd.
        	if( targetContours.get(0).getSubDiagramIndex() != indexOfParent +1){
        		// indexOfParent and subDiagramIndex are from the global indexing of goals, whereas getParentIndex gives a local index w.r.t. the currentDiagram.
        		cdParentUniversalIndex = currentDiagram.getParentIndexOf(targetContours.get(0).getSubDiagramIndex()-indexOfParent) + indexOfParent;
        	}
        			
        	SpiderDiagram diagramWithContour, diagramWithoutContour;
        	
        	if (cdParentUniversalIndex != 0){
                diagramWithContour =  InferenceTargetExtraction.getSourceOperandNew(currentDiagram, indexOfParent, cdParentUniversalIndex);
                diagramWithoutContour = InferenceTargetExtraction.getTargetOperandNew(currentDiagram, indexOfParent, cdParentUniversalIndex);
        	}else{
                diagramWithContour =  InferenceTargetExtraction.getSourceOperandNew(currentDiagram, indexOfParent, targetContours.get(0).getSubDiagramIndex());
                diagramWithoutContour = InferenceTargetExtraction.getTargetOperandNew(currentDiagram, indexOfParent, targetContours.get(0).getSubDiagramIndex());
        	}
      
        	
        	assertDiagramContainsTargetContours(diagramWithContour);
            
            if ((diagramWithContour instanceof CompleteCOPDiagram) && (diagramWithoutContour instanceof CompleteCOPDiagram)){
            	CompleteCOPDiagram psdWithContour = (CompleteCOPDiagram) diagramWithContour;            	
            	CompleteCOPDiagram psdWithoutContour = (CompleteCOPDiagram) diagramWithoutContour;
            	return copyContours(psdWithContour, psdWithoutContour);
            }
            

            if ((diagramWithContour instanceof ConceptDiagram) && (diagramWithoutContour instanceof CompleteCOPDiagram)){	
            	ConceptDiagram cdWithContour = (ConceptDiagram) diagramWithContour;
            	CompleteCOPDiagram psdWithContour = (CompleteCOPDiagram) cdWithContour.targetDiagramForContour(targetContours.get(0).getContour());
            	CompleteCOPDiagram psdWithoutContour = (CompleteCOPDiagram) diagramWithoutContour;
            	CompleteCOPDiagram transformedDiagram = (CompleteCOPDiagram) new ZoneTransfer(psdWithContour, psdWithoutContour).transferContour(getTargetContours().get(0));
            	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, cdWithContour, transformedDiagram, cdParentUniversalIndex, indexOfParent);
            }
            
            
            
            if ((diagramWithContour instanceof CompleteCOPDiagram) && (diagramWithoutContour instanceof ConceptDiagram)){
            	CompleteCOPDiagram psdWithContour = (CompleteCOPDiagram) diagramWithContour;
            	ConceptDiagram cdWithoutContour = (ConceptDiagram) diagramWithoutContour;
//            	CompleteCOPDiagram psdWithoutContour = (CompleteCOPDiagram) currentDiagram.getSubDiagramAt(targetDiagram.getSubDiagramIndex()-3);
            	CompleteCOPDiagram psdWithoutContour = (CompleteCOPDiagram) currentDiagram.getSubDiagramAt(targetDiagram.getSubDiagramIndex()-diagramIndex);

            	
            	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cdWithoutContour.getPrimaries());
            	int indexOfTransformedChild = transformedChildren.indexOf(psdWithoutContour);
            	CompleteCOPDiagram transformedChild = (CompleteCOPDiagram) new ZoneTransfer(psdWithContour, psdWithoutContour).transferContour(getTargetContours().get(0));
            	transformedChildren.set(indexOfTransformedChild, transformedChild);
            	ConceptDiagram transformedConceptDiagram = SpiderDiagrams.createConceptDiagram(cdWithoutContour.get_cd_Arrows(), transformedChildren, false);
            	
            	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, psdWithContour, transformedConceptDiagram, cdParentUniversalIndex, indexOfParent);
            	}
            
            
            if ((diagramWithContour instanceof ConceptDiagram) && (diagramWithoutContour instanceof ConceptDiagram)){
            	ConceptDiagram cdWithContour = (ConceptDiagram) diagramWithContour;
            	ConceptDiagram cdWithoutContour = (ConceptDiagram) diagramWithoutContour;
            	CompleteCOPDiagram psdWithContour = (CompleteCOPDiagram) cdWithContour.targetDiagramForContour(targetContours.get(0).getContour());
//            	CompleteCOPDiagram psdWithoutContour = (CompleteCOPDiagram) currentDiagram.getSubDiagramAt(targetDiagram.getSubDiagramIndex()-3);
            	CompleteCOPDiagram psdWithoutContour = (CompleteCOPDiagram) currentDiagram.getSubDiagramAt(targetDiagram.getSubDiagramIndex()-diagramIndex);

            	
            	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cdWithoutContour.getPrimaries());
            	int indexOfTransformedChild = transformedChildren.indexOf(psdWithoutContour);
            	CompleteCOPDiagram transformedChild = (CompleteCOPDiagram) new ZoneTransfer(psdWithContour, psdWithoutContour).transferContour(getTargetContours().get(0));
            	transformedChildren.set(indexOfTransformedChild, transformedChild);
            	ConceptDiagram transformedConceptDiagram = SpiderDiagrams.createConceptDiagram(cdWithoutContour.get_cd_Arrows(), transformedChildren, false);
            	
            	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, cdWithContour, transformedConceptDiagram, cdParentUniversalIndex, indexOfParent);
            }
            
            
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

    private SpiderDiagram copyContours(PrimarySpiderDiagram diagramWithContour, PrimarySpiderDiagram diagramWithoutContour) {
        try {
            PrimarySpiderDiagram transformedDiagram = new ZoneTransfer(diagramWithContour, diagramWithoutContour).transferContour(getTargetContours().get(0));
            return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, diagramWithContour, transformedDiagram, targetContours.get(0), indexOfParent);
        } catch (Exception e) {
            e.printStackTrace();
        	throw new TransformationException("Could not copy the contour. " + e.getMessage(), e);
        }
    }

    
    
    private void assertDiagramContainsTargetContours(SpiderDiagram currentDiagram) {
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cd = (ConceptDiagram) currentDiagram;
    		if(! cd.getAllContours().containsAll(getTargetContours())){
    			throw new TransformationException("The target diagram does not contain the target contour.");
    		}
    	}else{
    		PrimarySpiderDiagram psd = (PrimarySpiderDiagram) currentDiagram;
    		if (! psd.getAllContours().containsAll(getTargetContours())) {
                throw new TransformationException("The target diagram does not contain the target contour.");
            }
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
            throw new TransformationException("The target of the copy contour rule must be a conjunctive compound spider diagram.");
        }
    }
}
