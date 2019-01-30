package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import speedith.core.lang.Arrow;
import speedith.core.lang.Cardinality;
import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.LUCarCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.util.unitary.AddCurve;
import speedith.core.reasoning.util.unitary.DeleteCurve;

public class ReverseArrowDirectionTransformer extends IdTransformer {
	
	private ArrowArg arrowArg;
    private final boolean applyForward;

    public ReverseArrowDirectionTransformer(ArrowArg arrowArg, boolean applyForward) {
    	this.arrowArg = arrowArg;
    	this.applyForward = applyForward;
    }
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = arrowArg.getSubDiagramIndex();
    	if (diagramIndex == subDiagramIndex) {
        	if(psd instanceof CompleteCOPDiagram){
        		
        		CompleteCOPDiagram compCop = (CompleteCOPDiagram) psd;
        		assertDiagramContainTargetArrow(compCop);
        		assertSuitabilityOfArrow(compCop);

        		
        		//Delete the existing arrow.
        		Arrow oldArrow = arrowArg.getArrow();
        		CompleteCOPDiagram compCopNoOldArrow= (CompleteCOPDiagram) compCop.removeArrow(oldArrow);
        		
        		//Add the new Arrow. 
        		String newLabel;
        		if (oldArrow.arrowLabel().endsWith("-")){
        			newLabel = oldArrow.arrowLabel().substring(0, oldArrow.arrowLabel().length() - 1);
        		}else{
        			newLabel=oldArrow.arrowLabel()+"-";
        		}
        		Arrow newArrow = new Arrow(oldArrow.arrowTarget(),oldArrow.arrowSource(),"dashed",newLabel);
        		CompleteCOPDiagram compCopNewArrow = (CompleteCOPDiagram) compCopNoOldArrow.addArrow(newArrow);
            	
            	return compCopNewArrow;
        	}
        	else return psd;
    	}
    	else return null;
    }
    
    
    
    @Override
    public SpiderDiagram transform(ConceptDiagram cd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	// TODO Auto-generated method stub
    	return null;
    	
    }
    
    
    private void assertDiagramContainTargetArrow(CompleteCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow.");
        }
    }
    
    
    
    private void assertSuitabilityOfArrow(CompleteCOPDiagram currentDiagram){
    	if (! arrowArg.getArrow().arrowType().equals("dashed")){
    		throw new TransformationException("The arrow has to be dashed.");
    	}
    	
    	if (!currentDiagram.arrowSourceSpider(arrowArg.getArrow())){
    		throw new TransformationException("The source of the arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetSpider(arrowArg.getArrow())){
    		throw new TransformationException("The target of the arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowTarget()) == null) ||
				(currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider.");
		}
    	
    	if (currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()) != null){
    		throw new TransformationException("The arrow should not have any cardinality.");
    	}
    	
    }

   

    



}
