package speedith.core.reasoning.rules.transformers;

import java.util.ArrayList;
import java.util.TreeSet;

import speedith.core.lang.Arrow;
import speedith.core.lang.COPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.args.ZonesInOutArg;

public class TRAddArrowTransformer extends IdTransformer{
	//private final SubDiagramIndexArg target;
	private ArrowArg arrowArg;
    private final boolean applyForward;


	
	public TRAddArrowTransformer(ArrowArg arrowArg,boolean applyForward)  {
	    this.arrowArg = arrowArg;
	    this.applyForward = applyForward;
	}
	
	
	
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	LUCOPDiagram lucop = (LUCOPDiagram) psd;
    	
    	int subDiagramIndex = arrowArg.getSubDiagramIndex();
    	TreeSet<Arrow> newArrows = new TreeSet<Arrow>();
    	newArrows.addAll(lucop.getArrows());
    	

    	
    	
    	if (diagramIndex == subDiagramIndex) {
    		    		
    		if ( (! lucop.getAllContours().contains(arrowArg.getArrow().arrowSource())) && (! lucop.getSpiders().contains(arrowArg.getArrow().arrowSource()))) {
    			throw new TransformationException("The source of arrow must be a contour or a spider.");
    			}
    		
    		if ( (! lucop.getAllContours().contains(arrowArg.getArrow().arrowTarget())) && (! lucop.getSpiders().contains(arrowArg.getArrow().arrowTarget()))) {
    			throw new TransformationException("The source of arrow must be a contour or a spider.");
    			}
    		
    		
    		
    		newArrows.add(arrowArg.getArrow());
    		
    	return LUCOPDiagram.createLUCOPDiagram(
    			lucop.getSpidersMod(), 
        		lucop.getHabitatsMod(), 
        		lucop.getShadedZonesMod(), 
        		lucop.getPresentZonesMod(), 
        		newArrows,
        		lucop.getSpiderLabelsMod(),
        		lucop.getCurveLabelsMod());}
    	
		return lucop;
    }
        
    

}
