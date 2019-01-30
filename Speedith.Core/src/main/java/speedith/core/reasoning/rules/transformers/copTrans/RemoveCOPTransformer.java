package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.List;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

public class RemoveCOPTransformer extends IdTransformer{
	
    private final int indexOfParent;
    private final SubDiagramIndexArg targetCOP;

    public RemoveCOPTransformer(int indexOfParent, SubDiagramIndexArg targetCOP) {
    	this.indexOfParent = indexOfParent;
        this.targetCOP = targetCOP;
    }
    
    
    @Override
    public SpiderDiagram transform(ConceptDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
        	PrimarySpiderDiagram diagramToRemove = currentDiagram.getPrimary(targetCOP.getSubDiagramIndex() - indexOfParent - 1);

            return currentDiagram.deletePrimarySpiderDiagram(diagramToRemove);
        }
        return null;
    }
    
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
        if (diagramIndex == targetCOP.getSubDiagramIndex()) {
            throw new TransformationException("The target of the inference rule is not a concept diagram.");
        } 
        return null;
    }
    
    
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
        if (diagramIndex == indexOfParent) {
            throw new TransformationException("The target of the inference rule is not a concept diagram.");
        }
        return null;
    }

}
