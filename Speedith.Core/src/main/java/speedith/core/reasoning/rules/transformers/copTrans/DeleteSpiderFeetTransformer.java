package speedith.core.reasoning.rules.transformers.copTrans;

import static speedith.core.i18n.Translations.i18n;

import java.util.ArrayList;

import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.Operator;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zones;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.SpiderRegionArg;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetChecks;
import speedith.core.reasoning.rules.transformers.util.InferenceTargetExtraction;

public class DeleteSpiderFeetTransformer extends IdTransformer{

	private final int indexOfParent;
    private SpiderRegionArg spiderFeet;
    private ContourArg curve;
    
    public DeleteSpiderFeetTransformer(int indexOfParent, SpiderRegionArg spiderFeet, ContourArg curve) {
        if (indexOfParent < 0) {
            throw new TransformationException("The target sub-diagram is not in a conjunction.");
        }
        this.indexOfParent = indexOfParent;
        this.spiderFeet = spiderFeet;
        this.curve = curve;
    }
    
    @Override
    public SpiderDiagram transform(CompoundSpiderDiagram currentDiagram,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if (diagramIndex == indexOfParent) {
    		
            InferenceTargetChecks.assertIsConjunction(currentDiagram);
            InferenceTargetChecks.assertOperandsAreUnitary(currentDiagram);

    	    PrimarySpiderDiagram extraFeetSpiderDiagram = InferenceTargetExtraction.getSourceOperand(currentDiagram, diagramIndex, spiderFeet);
    	    PrimarySpiderDiagram  singleCurveHabitatSpiderDiagram = InferenceTargetExtraction.getTargetOperand(currentDiagram, diagramIndex, spiderFeet);
    		
    	    
    	    if((extraFeetSpiderDiagram instanceof CompleteCOPDiagram) && (singleCurveHabitatSpiderDiagram instanceof CompleteCOPDiagram)){
    	    	
    	    	CompleteCOPDiagram compExtraFeetSpiderDiagram = (CompleteCOPDiagram) extraFeetSpiderDiagram;
    	    	CompleteCOPDiagram compSingleCurveHabitatSpiderDiagram = (CompleteCOPDiagram) singleCurveHabitatSpiderDiagram;
    	    	
    	    	assertSpiderSuitability(compExtraFeetSpiderDiagram);
    	    	assertCurveSuitability(compSingleCurveHabitatSpiderDiagram);

    	    	
    	    	Region newHabitat = compExtraFeetSpiderDiagram.getSpiderHabitat(spiderFeet.getSpider()).subtract(spiderFeet.getRegion());
    	    	
    	    	CompleteCOPDiagram transformedDiagram = (CompleteCOPDiagram) compExtraFeetSpiderDiagram.addLUSpider(spiderFeet.getSpider(), 
    	    			newHabitat,compExtraFeetSpiderDiagram.getSpiderLabels().get(spiderFeet.getSpider()));
    	    	
                return InferenceTargetExtraction.createBinaryDiagram(Operator.Conjunction, transformedDiagram, compSingleCurveHabitatSpiderDiagram, 
                		spiderFeet, indexOfParent);
    	    }else return currentDiagram;
    		
    	}else return null;
    }
    
    
    
    private void assertSpiderSuitability(CompleteCOPDiagram currentDiagram){
        Region regionToDel = spiderFeet.getRegion();
        String spider = spiderFeet.getSpider();
        
    	if (regionToDel == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", regionToDel));
        }
    	
    	if (regionToDel.getZonesCount() < 1 ) {
            throw new IllegalArgumentException("The selected region has to have at least one zone.");
        }
    	
        if (spider == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", spider));
        }
        
        if (! currentDiagram.containsSpider(spider)) {
            throw new IllegalArgumentException(i18n("ERR_SPIDER_NOT_IN_DIAGRAM", spider));
        }
        
        if (!regionToDel.isSubregionOf(currentDiagram.getSpiderHabitat(spider))) {
        	throw new IllegalArgumentException("The selected region is not subset of spider habitat.");
        }
        		
        
        if (currentDiagram.getSpiderHabitat(spider).getZonesCount() < regionToDel.getZonesCount() ) {
        	throw new IllegalArgumentException("The selected region cannot contain the entire spider habitat.");
        }	
        	
        if(currentDiagram.getSpiderHabitat(spider).getZonesCount() < 2){
        	throw new IllegalArgumentException("The chosen spider has to have at least two feet.");
        }
        
        
        if(! currentDiagram.getAllContours().contains(curve.getContour())){
        	throw new IllegalArgumentException("The chosen curve has to be present in both diagrams.");
        }
        
        
    	String[] allPossibleZones = new String[currentDiagram.getAllContours().size()];
    	allPossibleZones = currentDiagram.getAllContours().toArray(allPossibleZones);
    	Region regionInsideCurve = new Region(Zones.getZonesInsideAnyContour
    		(Zones.allZonesForContours(allPossibleZones),curve.getContour()));
    	
        if(! currentDiagram.getSpiderHabitat(spider).intersect(regionInsideCurve)){
        	throw new IllegalArgumentException("The chosen spider has to have at least one feet in" + curve.getContour() + ".");
        }
        
        if(currentDiagram.getHabitats().get(spider).isSubregionOf(regionInsideCurve)){
        	throw new IllegalArgumentException("The chosen spider has to have at least one feet outside" + curve.getContour() + ".");
        }
        
    }
    
    
    private void assertCurveSuitability(CompleteCOPDiagram currentDiagram){
        
    	String spider = spiderFeet.getSpider();
    	
        if(! currentDiagram.getAllContours().contains(curve.getContour())){
        	throw new IllegalArgumentException("The chosen curve is not present in the diagram.");
        }
        
        if(! currentDiagram.getSpiders().contains(spider)){
        	throw new IllegalArgumentException("The chosen spider has to be present in both diagrams.");
        }
        
        
           
    	String[] allPossibleZones = new String[currentDiagram.getAllContours().size()];
    	allPossibleZones = currentDiagram.getAllContours().toArray(allPossibleZones);
    	Region regionInsideCurve = new Region(Zones.getZonesInsideAnyContour
    		(Zones.allZonesForContours(allPossibleZones),curve.getContour()));
    
        if(! currentDiagram.getSpiderHabitat(spider).isSubregionOf(regionInsideCurve)){
        	throw new IllegalArgumentException("The chosen curve must contain spider" + spider + ".");
        }
        
        
    }
    
}
















