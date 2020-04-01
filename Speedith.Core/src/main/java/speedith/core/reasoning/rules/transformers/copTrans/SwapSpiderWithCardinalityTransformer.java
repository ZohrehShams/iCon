package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.util.unitary.AddCurve;


public class SwapSpiderWithCardinalityTransformer extends IdTransformer {

    private final boolean applyForward;
    private ArrowArg targetArrow;
    
    
    public SwapSpiderWithCardinalityTransformer(ArrowArg arrowArg,boolean applyForward) {
    	this.targetArrow = arrowArg;
        this.applyForward = applyForward;
    }
    
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = targetArrow.getSubDiagramIndex();
    	
    	if (diagramIndex == subDiagramIndex){
    		
    		if (!(psd instanceof CompleteCOPDiagram)){
    			  throw new TransformationException("The rule is not applicable to this diagram.");
    		  }
    		  
    		CompleteCOPDiagram compCop = (CompleteCOPDiagram) psd;
    		
    		
    		if (! compCop.getArrows().contains(targetArrow.getArrow())) {
    			throw new TransformationException("The arrow does not exist in the diagram.");
    		}
 
    		assertArrowSuitability(compCop);
    		
    		//Deleting the spider that is the target of the arrow. The arrow will be deleted automatically as the target is deleted.
    		CompleteCOPDiagram compCopNoSpider = (CompleteCOPDiagram) compCop.deleteSpider(targetArrow.getArrow().arrowTarget());
    	
    		
    		//Adding the unnamed curve (it uses introduce contour principle). 
    		ArrayList<String> newCurves = new ArrayList<String>();
    		newCurves.add("unnamedCurve");
    		AddCurve addCurve = new AddCurve(compCopNoSpider,newCurves);
    		CompleteCOPDiagram compCopNoSpiderUnCurve = (CompleteCOPDiagram) addCurve.addingCurve();
    		
	    	//Adding the new arrow and its cardinality.
    		Arrow newArrow = new Arrow(targetArrow.getArrow().arrowSource(),"unnamedCurve",targetArrow.getArrow().arrowType(),
			targetArrow.getArrow().arrowLabel());
	    	CompleteCOPDiagram compCopNoSpiderUnCurveNewArrow = (CompleteCOPDiagram) compCopNoSpiderUnCurve.addArrow(newArrow);
	    	CompleteCOPDiagram compCopNoSpiderUnCurveNewArrowCar = (CompleteCOPDiagram) 
	    			compCopNoSpiderUnCurveNewArrow.addCardinality(newArrow, new Cardinality(">=","1"));

	    	
    		return compCopNoSpiderUnCurveNewArrowCar;

    	  }
    		return null;
    }
    
    
    
    
    
    private void assertArrowSuitability(CompleteCOPDiagram currentDiagram){
    	
    	if (! targetArrow.getArrow().arrowType().equals("dashed")){
    		throw new TransformationException("The arrow has to be dashed.");
    	}
    	
    	if (!currentDiagram.arrowSourceSpider(targetArrow.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(targetArrow.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(targetArrow.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetSpider(targetArrow.getArrow())){
    		throw new TransformationException("The target of arrow has to be a spider.");
    	}
    	
    	
    	if (currentDiagram.getArrowCardinalities().get(targetArrow.getArrow()) != null){
    		throw new TransformationException("The arrow should not have any cardinality.");
    	}

    }
    
    
}
