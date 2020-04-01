package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.copArgs.ArrowArg;

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
    	int subDiagramIndex = arrowArg.getSubDiagramIndex();
    	if (diagramIndex == subDiagramIndex) {

    		assertDiagramContainTargetArrow(cd);
    		assertSuitabilityOfArrow(cd);
    		
    		//Delete the existing arrow.
    		Arrow oldArrow = arrowArg.getArrow();
    		ConceptDiagram cdNoOldArrow= cd.deleteArrow(oldArrow);
    	
    		
    		//Add the new Arrow. 
    		String newLabel;
    		if (oldArrow.arrowLabel().endsWith("-")){
    			newLabel = oldArrow.arrowLabel().substring(0, oldArrow.arrowLabel().length() - 1);
    		}else{
    			newLabel=oldArrow.arrowLabel()+"-";
    		}
    		Arrow newArrow = new Arrow(oldArrow.arrowTarget(),oldArrow.arrowSource(),"dashed",newLabel);
    		ConceptDiagram cdNewArrow = cdNoOldArrow.addArrow(newArrow);

        	return cdNewArrow;
    	}
    	else return null;

    	
    }
    
    
    private void assertDiagramContainTargetArrow(CompleteCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow.");
        }
    }
    
    
    private void assertDiagramContainTargetArrow(ConceptDiagram currentDiagram) {
        if (!currentDiagram.get_cd_Arrows().contains(arrowArg.getArrow())) {
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

   
    private void assertSuitabilityOfArrow(ConceptDiagram currentDiagram){
    	if (! arrowArg.getArrow().arrowType().equals("dashed")){
    		throw new TransformationException("The arrow has to be dashed.");
    	}
    	
    	if (!currentDiagram.arrowSourceSpider(arrowArg.getArrow())){
    		throw new TransformationException("The source of the arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getAllSpiderLabels().get(arrowArg.getArrow().arrowSource()) == null) ||
				(currentDiagram.getAllSpiderLabels().get(arrowArg.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetSpider(arrowArg.getArrow())){
    		throw new TransformationException("The target of the arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getAllSpiderLabels().get(arrowArg.getArrow().arrowTarget()) == null) ||
				(currentDiagram.getAllSpiderLabels().get(arrowArg.getArrow().arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider.");
		}
    	
    }
    



}
