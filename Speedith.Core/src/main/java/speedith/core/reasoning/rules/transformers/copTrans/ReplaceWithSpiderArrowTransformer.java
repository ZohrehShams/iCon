package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;

public class ReplaceWithSpiderArrowTransformer extends IdTransformer {
	
	private ArrowArg arrowArg;
	private SpiderArg spiderArg;
    private final boolean applyForward;

    public ReplaceWithSpiderArrowTransformer(ArrowArg arrowArg, SpiderArg spiderArg,boolean applyForward) {
    	this.arrowArg = arrowArg;
    	this.spiderArg = spiderArg;
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
        		Arrow oldArrow = arrowArg.getArrow();
        		String spider = spiderArg.getSpider();

        		InferenceTargetChecks.assertDiagramContainArrow(compCop,oldArrow);
        		InferenceTargetChecks.assertSourceOfArrowIsCurve(compCop, oldArrow);
        		InferenceTargetChecks.assertTargetOfArrowIsNamedSpiderOrUnamedCurve(compCop, oldArrow);
        		InferenceTargetChecks.assertDiagramContainSpider(compCop,spider);
        		InferenceTargetChecks.assertSpiderIsInArrowSource(compCop,oldArrow,spider);
        		
        		Arrow newArrow = new Arrow(spiderArg.getSpider(),oldArrow.arrowTarget(),
        				oldArrow.arrowType(),oldArrow.arrowLabel());
        		Cardinality cardinality = compCop.getArrowCardinalities().get(oldArrow);
        		
        		// It deletes the arrow from the curve, because it will be replaced with the new one from the spider to curve
        		CompleteCOPDiagram compCopDelArrow = (CompleteCOPDiagram) compCop.removeArrow(oldArrow);
        		
        		CompleteCOPDiagram compCopNewArrow = (CompleteCOPDiagram) compCopDelArrow.addArrow(newArrow);
//        		CompleteCOPDiagram compCopNewArrowCar =  (CompleteCOPDiagram) 
//        				compCopNewArrow.addCardinality(newArrow,compCop.getArrowCardinalities().get(oldArrow));
        		if(cardinality != null){
        		return  compCopNewArrow.addCardinality(newArrow,cardinality);}
        		else {return compCopNewArrow; }

//        		return compCopNewArrowCar;
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
    		Arrow oldArrow = arrowArg.getArrow();
    		String spider = spiderArg.getSpider();
    		
    		InferenceTargetChecks.assertDiagramContainArrow(cd,oldArrow);
    		InferenceTargetChecks.assertSourceOfArrowIsCurve(cd, oldArrow);
    		InferenceTargetChecks.assertTargetOfArrowIsNamedSpiderOrUnamedCurve(cd, oldArrow);
    		InferenceTargetChecks.assertDiagramContainSpider(cd,spider);
    		InferenceTargetChecks.assertSpiderIsInArrowSource(cd,oldArrow,spider);
    	
    	
    	
		Arrow newArrow = new Arrow(spiderArg.getSpider(),oldArrow.arrowTarget(),
				oldArrow.arrowType(),oldArrow.arrowLabel());
		
		ConceptDiagram cdNewArrow = (ConceptDiagram) cd.addArrow(newArrow);
        	
    	return cdNewArrow;
    	}
    	else return null;
    }
    

}
