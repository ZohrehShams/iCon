package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.NullSpiderDiagram;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;
import speedith.core.reasoning.util.unitary.SpiderTransfer;

public class CopySpiderCDTransformer extends IdTransformer {

    private final int indexOfParent;
    private final SpiderArg targetSpider;
    private final SubDiagramIndexArg targetDiagram;

    public CopySpiderCDTransformer(int indexOfParent, SpiderArg targetSpider, SubDiagramIndexArg targetDiagram) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.targetSpider = targetSpider;
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
        	if( targetSpider.getSubDiagramIndex() != indexOfParent +1){
        		// indexOfParent and subDiagramIndex are from the global indexing of goals, whereas getParentIndex gives a local index w.r.t. the currentDiagram.
        		cdParentUniversalIndex = currentDiagram.getParentIndexOf(targetSpider.getSubDiagramIndex()-indexOfParent) + indexOfParent;
        	}
        			
        	SpiderDiagram diagramWithSpider, diagramWithoutSpider;
        	
        	if (cdParentUniversalIndex != 0){
                diagramWithSpider =  InferenceTargetExtraction.getSourceOperandNew(currentDiagram, indexOfParent, cdParentUniversalIndex);
                diagramWithoutSpider = InferenceTargetExtraction.getTargetOperandNew(currentDiagram, indexOfParent, cdParentUniversalIndex);
        	}else{
                diagramWithSpider =  InferenceTargetExtraction.getSourceOperandNew(currentDiagram, indexOfParent, targetSpider.getSubDiagramIndex());
                diagramWithoutSpider = InferenceTargetExtraction.getTargetOperandNew(currentDiagram, indexOfParent, targetSpider.getSubDiagramIndex());
        	}
      
        	
        	assertDiagramContainsTargetSpider(diagramWithSpider);
            
            if ((diagramWithSpider instanceof CompleteCOPDiagram) && (diagramWithoutSpider instanceof CompleteCOPDiagram)){
            	CompleteCOPDiagram psdWithSpider = (CompleteCOPDiagram) diagramWithSpider;            	
            	CompleteCOPDiagram psdWithoutSpider = (CompleteCOPDiagram) diagramWithoutSpider;
            	return copySpider(psdWithSpider, psdWithoutSpider);
            }
            

            if ((diagramWithSpider instanceof ConceptDiagram) && (diagramWithoutSpider instanceof CompleteCOPDiagram)){	
            	ConceptDiagram cdWithSpider = (ConceptDiagram) diagramWithSpider;
            	CompleteCOPDiagram psdWithSpider = (CompleteCOPDiagram) cdWithSpider.targetDiagramForSpider(targetSpider.getSpider());
            	CompleteCOPDiagram psdWithoutSpider = (CompleteCOPDiagram) diagramWithoutSpider;
            	CompleteCOPDiagram transformedDiagram = (CompleteCOPDiagram) new SpiderTransfer(psdWithSpider, psdWithoutSpider).transferSpider(targetSpider.getSpider());
            	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, cdWithSpider, transformedDiagram, cdParentUniversalIndex, indexOfParent);
            }
            
            
            
            if ((diagramWithSpider instanceof CompleteCOPDiagram) && (diagramWithoutSpider instanceof ConceptDiagram)){
            	CompleteCOPDiagram psdWithSpider = (CompleteCOPDiagram) diagramWithSpider;
            	ConceptDiagram cdWithoutSpider = (ConceptDiagram) diagramWithoutSpider;
               	CompleteCOPDiagram psdWithoutSpider = (CompleteCOPDiagram) currentDiagram.getSubDiagramAt(targetDiagram.getSubDiagramIndex()-diagramIndex);
            	
            	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cdWithoutSpider.getPrimaries());
            	int indexOfTransformedChild = transformedChildren.indexOf(psdWithoutSpider);
            	CompleteCOPDiagram transformedChild = (CompleteCOPDiagram) new SpiderTransfer(psdWithSpider, psdWithoutSpider).transferSpider(targetSpider.getSpider());
            	transformedChildren.set(indexOfTransformedChild, transformedChild);
            	ConceptDiagram transformedConceptDiagram = SpiderDiagrams.createConceptDiagram(cdWithoutSpider.get_cd_Arrows(), transformedChildren, false);
            	
            	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, psdWithSpider, transformedConceptDiagram, cdParentUniversalIndex, indexOfParent);
            	}
            
            
            if ((diagramWithSpider instanceof ConceptDiagram) && (diagramWithoutSpider instanceof ConceptDiagram)){
            	ConceptDiagram cdWithSpider = (ConceptDiagram) diagramWithSpider;
            	ConceptDiagram cdWithoutSpider = (ConceptDiagram) diagramWithoutSpider;
            	CompleteCOPDiagram psdWithSpider = (CompleteCOPDiagram) cdWithSpider.targetDiagramForSpider(targetSpider.getSpider());
            	CompleteCOPDiagram psdWithoutSpider = (CompleteCOPDiagram) currentDiagram.getSubDiagramAt(targetDiagram.getSubDiagramIndex()-diagramIndex);
            	
            	ArrayList<PrimarySpiderDiagram> transformedChildren = new ArrayList<>(cdWithoutSpider.getPrimaries());
            	int indexOfTransformedChild = transformedChildren.indexOf(psdWithoutSpider);
            	CompleteCOPDiagram transformedChild = (CompleteCOPDiagram) new SpiderTransfer(psdWithSpider, psdWithoutSpider).transferSpider(targetSpider.getSpider());
            	transformedChildren.set(indexOfTransformedChild, transformedChild);
            	ConceptDiagram transformedConceptDiagram = SpiderDiagrams.createConceptDiagram(cdWithoutSpider.get_cd_Arrows(), transformedChildren, false);
            	
            	return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, cdWithSpider, transformedConceptDiagram, cdParentUniversalIndex, indexOfParent);
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

    
    
    private SpiderDiagram copySpider(PrimarySpiderDiagram diagramWithSpider, PrimarySpiderDiagram diagramWithoutSpider) {
        try {
            PrimarySpiderDiagram transformedDiagram = new SpiderTransfer(diagramWithSpider, diagramWithoutSpider).transferSpider(targetSpider.getSpider());
            return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, diagramWithSpider, transformedDiagram, targetSpider, indexOfParent);
        } catch (Exception e) {
            e.printStackTrace();
        	throw new TransformationException("Could not copy the spider. " + e.getMessage(), e);
        }
    }

    
    
    private void assertDiagramContainsTargetSpider(SpiderDiagram currentDiagram) {
    	if (currentDiagram instanceof ConceptDiagram){
    		ConceptDiagram cd = (ConceptDiagram) currentDiagram;
    		if(! cd.getAllSpiders().contains(targetSpider.getSpider())){
    			throw new TransformationException("The target diagram does not contain the target spider.");
    		}
    	}else{
    		PrimarySpiderDiagram psd = (PrimarySpiderDiagram) currentDiagram;
    		if (! psd.getSpiders().contains(targetSpider.getSpider())) {
                throw new TransformationException("The target diagram does not contain the target spider.");
            }
    	}

    }
    

    private void checkThisDiagramNotTarget(int diagramIndex) {
        if (diagramIndex == indexOfParent) {
            throw new TransformationException("The target of the copy Spider rule must be a conjunctive compound spider diagram.");
        }
    }
}
