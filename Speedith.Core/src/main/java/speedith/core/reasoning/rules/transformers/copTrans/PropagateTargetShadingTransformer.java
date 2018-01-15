package speedith.core.reasoning.rules.transformers.copTrans;
/**
 * @author Zohreh Shams
 */
import java.util.ArrayList;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.LUCarCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.Zones;
import speedith.core.reasoning.args.ArrowArg;

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
    		
        	if(psd instanceof LUCarCOPDiagram){
        		LUCarCOPDiagram lucop = (LUCarCOPDiagram) psd;
        		assertDiagramContainTargetArrows(lucop);
        		
        		if (! lucop.arrowSourceContour(arrowArg.getArrow())){
        			throw new TransformationException("The source of arrow must be a curve.");
        		}
        		
        		if (! lucop.arrowTargetContour(arrowArg.getArrow())){
        			throw new TransformationException("The target of arrow must be a curve.");
        		}
        		
          		if((lucop.getArrowCardinalities().get(arrowArg.getArrow()) == null) || 
          				(lucop.getArrowCardinalities().get(arrowArg.getArrow()).getComparator().equals("<=")) ||
          				(lucop.getArrowCardinalities().get(arrowArg.getArrow()).getNumber() < 1 )){
        			throw new TransformationException("The arrow has to have the cardinality of greater or equal to 1 or greater.");
        		}
        		
        		for (Zone zone : Zones.getZonesInsideAnyContour(lucop.getPresentZones(),arrowArg.getArrow().arrowTarget())){
        			if (! lucop.getShadedZones().contains(zone)){
        				throw new TransformationException("The target of arrow should be fully shaded.");
        			}
        		}
        		
        		
        		for (Zone zone : Zones.getZonesInsideAnyContour(lucop.getPresentZones(),arrowArg.getArrow().arrowSource())){
        			if (lucop.getSpiderCountInZone(zone) != 0){
        				throw new TransformationException("The source of arrow should not conatin any spiders.");
        			}
        		}
        	   return lucop.addShading(Zones.getZonesInsideAnyContour(lucop.getPresentZones(),arrowArg.getArrow().arrowSource()));

        	}else{
        		LUCOPDiagram lucop = (LUCOPDiagram) psd;
        		assertDiagramContainTargetArrows(lucop);
        		
        		if (! lucop.arrowSourceContour(arrowArg.getArrow())){
        			throw new TransformationException("The source of arrow must be a curve.");
        		}
        		
        		if (! lucop.arrowTargetContour(arrowArg.getArrow())){
        			throw new TransformationException("The target of arrow must be a curve.");
        		}
        		
        		
        		for (Zone zone : Zones.getZonesInsideAnyContour(lucop.getPresentZones(),arrowArg.getArrow().arrowTarget())){
        			if (! lucop.getShadedZones().contains(zone)){
        				throw new TransformationException("The target of arrow should be fully shaded.");
        			}
        		}
        		
        		
        		for (Zone zone : Zones.getZonesInsideAnyContour(lucop.getPresentZones(),arrowArg.getArrow().arrowSource())){
        			if (lucop.getSpiderCountInZone(zone) != 0){
        				throw new TransformationException("The source of arrow should not conatin any spiders.");
        			}
        		}
        	   return lucop.addShading(Zones.getZonesInsideAnyContour(lucop.getPresentZones(),arrowArg.getArrow().arrowSource()));
        	}	
    	}
    	return psd;
    }
    
    
    
    private void assertDiagramContainTargetArrows(LUCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow(s).");
        }
    }




    	
    
}
