package speedith.core.reasoning.rules.transformers.copTrans;
/**
 * @author Zohreh Shams
 */
import java.util.ArrayList;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram;
import speedith.core.reasoning.args.copArgs.ArrowArg;

public class PropagateTargetShadingTransformer extends IdTransformer{
	private ArrowArg arrowArg;
    private final boolean applyForward;


	
	public PropagateTargetShadingTransformer(ArrowArg arrowArg,boolean applyForward)  {
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
    		 
    		if (! (psd instanceof LUCarCOPDiagram)){
    			throw new TransformationException("The rule does not operate on this type of diagram.");
    		}
    		
    		LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) psd;
    		
    		assertDiagramContainTargetArrows(luCarCop);
    		
    		if (! luCarCop.arrowSourceContour(arrowArg.getArrow())){
    			throw new TransformationException("The source of arrow must be a curve.");
    		}
    		
    		if (! luCarCop.arrowTargetContour(arrowArg.getArrow())){
    			throw new TransformationException("The target of arrow must be a curve.");
    		}
    		
      		if((luCarCop.getArrowCardinalities().get(arrowArg.getArrow()) == null) || 
      				(luCarCop.getArrowCardinalities().get(arrowArg.getArrow()).getComparator().equals("<=")) ||
      				(luCarCop.getArrowCardinalities().get(arrowArg.getArrow()).getNumber() < 1 )){
    			throw new TransformationException("The arrow has to have the cardinality of greater or equal to 1 or grrater than 1.");
    		}
    		
    		for (Zone zone : Zones.getZonesInsideAnyContour(luCarCop.getPresentZones(),arrowArg.getArrow().arrowTarget())){
    			if (! luCarCop.getShadedZones().contains(zone)){
    				throw new TransformationException("The target of arrow should be fully shaded.");
    			}
    		}
    		
    		for (Zone zone : Zones.getZonesInsideAnyContour(luCarCop.getPresentZones(),arrowArg.getArrow().arrowSource())){
    			if (luCarCop.getSpiderCountInZone(zone) != 0){
    				throw new TransformationException("The source of arrow should not conatin any spiders.");
    			}
    		}
    		
    		return psd.addShading(Zones.getZonesInsideAnyContour(psd.getPresentZones(),arrowArg.getArrow().arrowSource()));
    	}
    	return psd;
    }
    
    
    
    private void assertDiagramContainTargetArrows(LUCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow(s).");
        }
    }




    	
    
}
