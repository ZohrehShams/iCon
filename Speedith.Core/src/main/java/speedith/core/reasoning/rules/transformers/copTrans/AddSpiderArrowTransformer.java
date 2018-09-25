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

public class AddSpiderArrowTransformer extends IdTransformer {
	
	private ArrowArg arrowArg;
	private SpiderArg spiderArg;
    private final boolean applyForward;

    public AddSpiderArrowTransformer(ArrowArg arrowArg, SpiderArg spiderArg,boolean applyForward) {
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
        		assertSourceOfTargetArrowIsCurve(compCop);
        		assertTargetOfTargetArrowIsNamedSpiderOrUnamedCurve(compCop);
        		assertDiagramContainTargetSpider(compCop);
        		assertSpiderIsSingleHabitat(compCop);
        		assertSpiderIsInArrowSource(compCop);
        		
        		Arrow oldArrow = arrowArg.getArrow();
        		Arrow newArrow = new Arrow(spiderArg.getSpider(),oldArrow.arrowTarget(),
        				oldArrow.arrowType(),oldArrow.arrowLabel());
        		newArrow.setCardinality(compCop.getArrowCardinalities().get(oldArrow));
        		
        	   	TreeSet<Arrow> newArrows = new TreeSet<Arrow>();
            	newArrows.addAll(compCop.getArrows());
            	newArrows.add(newArrow);
            	
            	TreeMap<Arrow,Cardinality> newCardinalities = new TreeMap<Arrow,Cardinality>();
            	newCardinalities.putAll(compCop.getArrowCardinalities());
            	newCardinalities.put(newArrow,newArrow.getCardinality());
            	
            	return SpiderDiagrams.createCompleteCOPDiagram(compCop.getSpiders(), 
            			compCop.getHabitats(), compCop.getShadedZones(), compCop.getPresentZones(), 
            			newArrows, compCop.getSpiderLabels(), compCop.getCurveLabels(), 
            			newCardinalities, compCop.getSpiderComparators());
        		
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
    
    
    private void assertSourceOfTargetArrowIsCurve(CompleteCOPDiagram currentDiagram) {
    	if (! currentDiagram.arrowSourceContour(arrowArg.getArrow())){
    		throw new TransformationException("The source of arrow must be a curve.");
    	}
    }
    

    private void assertTargetOfTargetArrowIsNamedSpiderOrUnamedCurve(CompleteCOPDiagram currentDiagram) {
    	if (currentDiagram.arrowTargetSpider(arrowArg.getArrow())){
    		if ((currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowTarget()) == null) ||
    				(currentDiagram.getSpiderLabels().get(arrowArg.getArrow().arrowTarget()).equals("")) ){
    			throw new TransformationException("The target of the arrow must be a named spider or an unnamed curve.");
    		}
    	}else{  if ((currentDiagram.getCurveLabels().get(arrowArg.getArrow().arrowTarget()) != null) ||
				(! currentDiagram.getCurveLabels().get(arrowArg.getArrow().arrowTarget()).equals("")) ){
			throw new TransformationException("The target of the arrow must be a named spider or an unnamed curve.");
		}	
    	}	
    }
    
    
    private void assertDiagramContainTargetSpider(CompleteCOPDiagram currentDiagram) {
        if (!currentDiagram.getSpiders().contains(spiderArg.getSpider())) {
            throw new TransformationException("The target diagram does not contain the target spider.");
        }
    }
    
    private void assertSpiderIsSingleHabitat(CompleteCOPDiagram currentDiagram) {
        if (currentDiagram.getSpiderHabitat(spiderArg.getSpider()).getZonesCount() > 1) {
            throw new TransformationException("The spider is not single foot.");
        }
    }
    
    private void assertSpiderIsInArrowSource(CompleteCOPDiagram currentDiagram) {
    	Zone theSingleHabitat = currentDiagram.getSpiderHabitat(spiderArg.getSpider()).sortedZones().first();
    	if (! theSingleHabitat.getInContours().contains(arrowArg.getArrow().arrowSource())){
    		throw new TransformationException("The target spider is not in the curve that is the source of selected arrow.");
    	}
    }

}
