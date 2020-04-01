package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.TreeSet;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.copArgs.ZonesInOutArg;

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
    			lucop.getSpiders(), 
        		lucop.getHabitats(), 
        		lucop.getShadedZones(), 
        		lucop.getPresentZones(), 
        		newArrows,
        		lucop.getSpiderLabels(),
        		lucop.getCurveLabels());}
    	
		return lucop;
    }
        
    

}

