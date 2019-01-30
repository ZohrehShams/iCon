package speedith.core.reasoning.rules.transformers.copTrans;

import speedith.core.lang.*;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

import java.util.*;

public class CopyArrowsTransformer extends IdTransformer {
	
    private final int indexOfParent;
    private final List<ArrowArg> targetArrows;

    public CopyArrowsTransformer(int indexOfParent, List<ArrowArg> targetArrows) {
        this.indexOfParent = indexOfParent;
        this.targetArrows = targetArrows;
    }

    
    
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
        if (diagramIndex == indexOfParent) {
        	
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);

            PrimarySpiderDiagram diagramWithArrow = (PrimarySpiderDiagram) InferenceTargetExtraction.getSourceOperand(currentDiagram, indexOfParent, targetArrows.get(0));
            PrimarySpiderDiagram diagramWithoutArrow = (PrimarySpiderDiagram) InferenceTargetExtraction.getTargetOperand(currentDiagram, indexOfParent, targetArrows.get(0));
            
            PrimarySpiderDiagram temp1= diagramWithoutArrow;
            PrimarySpiderDiagram temp2= diagramWithArrow;
            
            if (!(diagramWithArrow  instanceof COPDiagram) || !(diagramWithoutArrow  instanceof COPDiagram)){
			  throw new TransformationException("The diagrams have to be of type COPDiagram.");
			}
            
            COPDiagram copDiagramWithArrow = (COPDiagram) diagramWithArrow;
            COPDiagram copDiagramWithoutArrow = (COPDiagram) diagramWithoutArrow;
		  
            if (! copDiagramWithArrow.getArrows().contains(getTargetArrows().get(0))) {
            	throw new TransformationException("The arrow to be copied do not exist in the target diagram");
            }
            	
            if (copDiagramWithoutArrow.getArrows().contains(getTargetArrows().get(0))) {
            	throw new TransformationException("The arrow cannot exist in the destination diagram");
            }	
            
            if ((! copDiagramWithoutArrow.getSpiders().contains(getTargetArrows().get(0).arrowSource())) && 
            		(! copDiagramWithoutArrow.getAllContours().contains(getTargetArrows().get(0).arrowSource())) ) {
            	throw new TransformationException("The source and target of the arrow has to be present in the destination diagram.");
            }
            
            if ((! copDiagramWithoutArrow.getSpiders().contains(getTargetArrows().get(0).arrowTarget())) && 
            		(! copDiagramWithoutArrow.getAllContours().contains(getTargetArrows().get(0).arrowTarget())) ) {
            	throw new TransformationException("The source and target of the arrow has to be present in the destination diagram.");
            }
            
 
            TreeSet<Arrow> newArrows = new TreeSet<>(copDiagramWithoutArrow.getArrows());
            newArrows.add(getTargetArrows().get(0));
            
            
  		    if ((temp1 instanceof CompleteCOPDiagram) && (temp2 instanceof CompleteCOPDiagram)){
  		    	CompleteCOPDiagram compCopDiagramWithoutArrow = (CompleteCOPDiagram) temp1;
  		    	CompleteCOPDiagram compCopDiagramWithArrow = (CompleteCOPDiagram) temp2;
			    TreeMap<Arrow,Cardinality> newCardinalities = new TreeMap<>(compCopDiagramWithoutArrow.getArrowCardinalities());
			    Cardinality cardinality = compCopDiagramWithArrow.getArrowCardinalities().get(getTargetArrows().get(0));
			    newCardinalities.put(getTargetArrows().get(0),cardinality);
			    
			    CompleteCOPDiagram newDiagramWithoutArrow = SpiderDiagrams.createCompleteCOPDiagram(compCopDiagramWithoutArrow.getSpiders(), compCopDiagramWithoutArrow.getHabitats(), 
		    				compCopDiagramWithoutArrow.getShadedZones(), compCopDiagramWithoutArrow.getPresentZones(), newArrows, compCopDiagramWithoutArrow.getSpiderLabels(), 
		    				compCopDiagramWithoutArrow.getCurveLabels(), newCardinalities, compCopDiagramWithoutArrow.getSpiderComparators());
	            return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, 
	            		compCopDiagramWithArrow , newDiagramWithoutArrow, targetArrows.get(0), indexOfParent);
			  }else{
				    COPDiagram newDiagramWithoutArrow = COPDiagram.createCOPDiagram(copDiagramWithArrow.getSpiders(), copDiagramWithArrow.getHabitats(), 
				    		copDiagramWithArrow.getShadedZones(), copDiagramWithArrow.getPresentZones(), newArrows);
		            return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, 
		            		copDiagramWithArrow , newDiagramWithoutArrow , targetArrows.get(0), indexOfParent);
				  }
            
        }
        return null;
    }
    
    	
    private List<Arrow> getTargetArrows() {
        ArrayList<Arrow> arrows = new ArrayList<>();
        for (ArrowArg targetArrow : targetArrows) {
            arrows.add(targetArrow.getArrow());
        }
        return arrows;
    }
    
}
