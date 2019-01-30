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

public class AddSpiderArrowTargetTransformer extends IdTransformer {
	
	private ArrowArg arrowArg;
	private SpiderArg spiderArg;
    private final boolean applyForward;

    public AddSpiderArrowTargetTransformer(ArrowArg arrowArg, SpiderArg spiderArg,boolean applyForward) {
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
        		assertDiagramContainTargetArrow(compCop);
        		assertSuitabilityOfArrow(compCop);
        		assertDiagramContainTargetSpider(compCop);
        		assertSuitabilityOfSpider(compCop);

        		
        		//Delete the curve that is the target of the arrow. This will automatically delete the arrow.
        		ArrayList<String> newCurves = new ArrayList<String>();
        		Arrow oldArrow = arrowArg.getArrow();
        		newCurves.add(oldArrow.arrowTarget());
        		DeleteCurve deleteCurve = new DeleteCurve(compCop,newCurves);
        		CompleteCOPDiagram compCopNoCurve = (CompleteCOPDiagram) deleteCurve.deletingCurve();
        		
        		//Add the new Arrow. 
        		Arrow newArrow = new Arrow(oldArrow.arrowSource(),spiderArg.getSpider(),"dashed",oldArrow.arrowLabel());
        		CompleteCOPDiagram compCopNoCurveArrow = (CompleteCOPDiagram) compCopNoCurve.addArrow(newArrow);
            	
            	return compCopNoCurveArrow;
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
    
    private void assertDiagramContainTargetSpider(CompleteCOPDiagram currentDiagram) {
        if (!currentDiagram.getSpiders().contains(spiderArg.getSpider())) {
            throw new TransformationException("The target diagram does not contain the target spider.");
        }
    }
    
    
    private void assertSuitabilityOfArrow(CompleteCOPDiagram currentDiagram){
    	
    	if (!currentDiagram.arrowSourceSpider(arrowArg.getArrow())){
    		throw new TransformationException("The source of arrow has to be a spider.");
    	}
    	
    	if ((currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowSource()) == null) ||
				(currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowSource()).equals("")) ){
			throw new TransformationException("The source of the arrow must be a named spider.");
		}
    	
    	if (!currentDiagram.arrowTargetContour(arrowArg.getArrow())){
    		throw new TransformationException("The target of arrow has to be a curve.");
    	}
    	
    	if (currentDiagram.getArrowCardinalities().get(arrowArg.getArrow()) != null){
    		throw new TransformationException("The arrow should not have any cardinality.");
    	}
    	
    }

   
    private void assertSuitabilityOfSpider(CompleteCOPDiagram currentDiagram){
    	
        if (currentDiagram.getSpiderHabitat(spiderArg.getSpider()).getZonesCount() > 1) {
            throw new TransformationException("The spider is not single foot.");
        }
        
    	Zone theSingleHabitat = currentDiagram.getSpiderHabitat(spiderArg.getSpider()).sortedZones().first();
    	if (! theSingleHabitat.getInContours().contains(arrowArg.getArrow().arrowTarget())){
    		throw new TransformationException("The target spider is not in the curve that is the target of selected arrow.");
    	}
    }
    



}
