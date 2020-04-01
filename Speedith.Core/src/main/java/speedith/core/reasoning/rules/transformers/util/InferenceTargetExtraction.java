package speedith.core.reasoning.rules.transformers.util;

import speedith.core.lang.*;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.SubDiagramIndexArg;

public class InferenceTargetExtraction {
	
    
    public static SpiderDiagram getSourceOperand(CompoundSpiderDiagram currentDiagram, int currentDiagramIndex, SubDiagramIndexArg inferenceTarget) {
        return currentDiagram.getOperand(isLeftOperand(inferenceTarget, currentDiagramIndex) ? 0 : 1);
    }
    
    
	
	public static SpiderDiagram getTargetOperand(CompoundSpiderDiagram currentDiagram, int currentDiagramIndex, SubDiagramIndexArg inferenceTarget) {
	  return  currentDiagram.getOperand(isLeftOperand(inferenceTarget, currentDiagramIndex) ? 1 : 0);
	}


    public static boolean isLeftOperand(SubDiagramIndexArg treeIndexOfChild, int treeIndexOfParent) {
        return treeIndexOfChild.getSubDiagramIndex() == treeIndexOfParent + 1;
    }
     

    
    //The following three are for when one of the source and operand are cds.  
    public static SpiderDiagram getSourceOperandNew(CompoundSpiderDiagram currentDiagram, int currentDiagramIndex, int subDiagramIndex) {
        return currentDiagram.getOperand(isLeftOperandNew(subDiagramIndex, currentDiagramIndex) ? 0 : 1);
    }
    
	public static SpiderDiagram getTargetOperandNew(CompoundSpiderDiagram currentDiagram, int currentDiagramIndex, int subDiagramIndex) {
	  return  currentDiagram.getOperand(isLeftOperandNew(subDiagramIndex, currentDiagramIndex) ? 1 : 0);
	}
	
    public static boolean isLeftOperandNew(int treeIndexOfChild, int treeIndexOfParent) {
        return treeIndexOfChild == treeIndexOfParent + 1;
    }
	
	
    
    public static SpiderDiagram createBinaryDiagram(Operator operator,
                                                    PrimarySpiderDiagram sourceDiagram,
                                                    PrimarySpiderDiagram targetDiagram,
                                                    SubDiagramIndexArg previousTargetDiagramIndex,
                                                    int previousParentIndex) {
        if (isLeftOperand(previousTargetDiagramIndex, previousParentIndex)) {
            return SpiderDiagrams.createCompoundSD(operator, sourceDiagram, targetDiagram);
        } else {
            return SpiderDiagrams.createCompoundSD(operator, targetDiagram, sourceDiagram);
        }
    }
    
    
    
    
    public static SpiderDiagram createBinaryDiagram(Operator operator,
    		ConceptDiagram sourceDiagram,
            PrimarySpiderDiagram targetDiagram,
            int previousTargetDiagramIndex,
            int previousParentIndex){
    	if (isLeftOperandNew(previousTargetDiagramIndex, previousParentIndex)) {
    		return SpiderDiagrams.createCompoundSD(operator, sourceDiagram, targetDiagram);
    	}else{
    		return SpiderDiagrams.createCompoundSD(operator, targetDiagram, sourceDiagram);
    	}	
    }
    
    
    
    public static SpiderDiagram createBinaryDiagram(Operator operator,
    		ConceptDiagram sourceDiagram,
            ConceptDiagram targetDiagram,
            int previousTargetDiagramIndex,
            int previousParentIndex){
    	if (isLeftOperandNew(previousTargetDiagramIndex, previousParentIndex)) {
    		return SpiderDiagrams.createCompoundSD(operator, sourceDiagram, targetDiagram);
    	}else{
    		return SpiderDiagrams.createCompoundSD(operator, targetDiagram, sourceDiagram);
    	}	
    }
    
    
    
    public static SpiderDiagram createBinaryDiagram(Operator operator,
    		PrimarySpiderDiagram sourceDiagram,
            ConceptDiagram targetDiagram,
            int previousTargetDiagramIndex,
            int previousParentIndex){
    	if (isLeftOperandNew(previousTargetDiagramIndex, previousParentIndex)) {
    		return SpiderDiagrams.createCompoundSD(operator, sourceDiagram, targetDiagram);
    	}else{
    		return SpiderDiagrams.createCompoundSD(operator, targetDiagram, sourceDiagram);
    	}	
    }
    
    
}
