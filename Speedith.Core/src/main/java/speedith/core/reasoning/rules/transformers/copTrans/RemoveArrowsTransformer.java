package speedith.core.reasoning.rules.transformers.copTrans;

import speedith.core.lang.*;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.args.copArgs.ArrowArg;

import java.util.*;


/**
 * Removes an arrow from a diagram, the diagram has to be a COP diagram. This rule dosen't operate on null, and compound diagrams.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */
public class RemoveArrowsTransformer extends IdTransformer {
	private final ApplyStyle applyStyle;
    private  List<ArrowArg> targetArrows;
    
    public RemoveArrowsTransformer(List<ArrowArg> targetArrows, ApplyStyle applyStyle ) {
        this.targetArrows = targetArrows;
        this.applyStyle = applyStyle;
    }
    
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if(targetArrows.isEmpty()){
    		return psd;
    	}
    	
    	
    	  int subDiagramIndex = targetArrows.get(0).getSubDiagramIndex();
    	  TreeSet<Arrow> newArrows = new TreeSet<>();
    	  
		  PrimarySpiderDiagram temp = psd;
		  
    	  if (diagramIndex == subDiagramIndex){
    		  
    		  if (!(psd instanceof COPDiagram)){
    			  throw new TransformationException("The rule is not applicable to this diagram.");
    		  }
    		  
    		  COPDiagram cop = (COPDiagram) psd;
    		  
    		  if (! cop.getArrows().containsAll(getTargetArrows())) {
    			  throw new TransformationException("The arrows to be removed do not exist in the target diagram");
    		  }
    		  
    		  newArrows.addAll(cop.getArrows());
	    	  newArrows.removeAll(getTargetArrows());

    		  if (temp instanceof CompleteCOPDiagram){
    			  CompleteCOPDiagram compCop = (CompleteCOPDiagram) temp;
    			  TreeMap<Arrow,Cardinality> newCardinalities = new TreeMap<Arrow,Cardinality>();
    			  newCardinalities.putAll(compCop.getArrowCardinalities());
    			  for (Arrow arrow : getTargetArrows()){
                		newCardinalities.remove(arrow);
                		}
    			  return SpiderDiagrams.createCompleteCOPDiagram(compCop.getSpiders(), compCop.getHabitats(), 
  	    				compCop.getShadedZones(), compCop.getPresentZones(), newArrows, compCop.getSpiderLabels(), 
  	    				compCop.getCurveLabels(), newCardinalities, compCop.getSpiderComparators());
    			  }else{
    				  return COPDiagram.createCOPDiagram(cop.getSpiders(), cop.getHabitats(), 
      	    				cop.getShadedZones(), cop.getPresentZones(), newArrows);
    				  }
    		  
//    	    	if(psd instanceof CompleteCOPDiagram){
//    	    		CompleteCOPDiagram compCop = (CompleteCOPDiagram) psd;
//    	    		
//    	    		if (! compCop.getArrows().containsAll(getTargetArrows())) {
//    	    			throw new TransformationException("The arrows to be removed do not exist in the target diagram");
//    	    			}
//    	    		
//    	    		newArrows.addAll(compCop.getArrows());
//    	    		newArrows.removeAll(getTargetArrows());
//    	    		
//                	TreeMap<Arrow,Cardinality> newCardinalities = new TreeMap<Arrow,Cardinality>();
//                	newCardinalities.putAll(compCop.getArrowCardinalities());
//                	
//                	for (Arrow arrow : getTargetArrows()){
//                		newCardinalities.remove(arrow);
//                	}
//                	
//    	    		return SpiderDiagrams.createCompleteCOPDiagram(compCop.getSpiders(), compCop.getHabitats(), 
//    	    				compCop.getShadedZones(), compCop.getPresentZones(), newArrows, compCop.getSpiderLabels(), 
//    	    				compCop.getCurveLabels(), newCardinalities, compCop.getSpiderComparators());
//    	    	}else{
//    	    		COPDiagram cop = (COPDiagram) psd;
//    	    		
//    	    		if (! cop.getArrows().containsAll(getTargetArrows())) {
//    	    			throw new TransformationException("The arrows to be removed do not exist in the target diagram");
//    	    			}
//    	    		newArrows.addAll(cop.getArrows());
//    	    		newArrows.removeAll(getTargetArrows());
//    	    		
//    	    		return COPDiagram.createCOPDiagram(cop.getSpiders(), cop.getHabitats(), 
//    	    				cop.getShadedZones(), cop.getPresentZones(), newArrows);
//    	    	}
    		  
    	  }
    		return psd;
    }
    
    	
    private List<Arrow> getTargetArrows() {
        ArrayList<Arrow> arrows = new ArrayList<>();
        for (ArrowArg targetArrow : targetArrows) {
            arrows.add(targetArrow.getArrow());
        }
        return arrows;
    }
    
}
