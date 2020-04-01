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
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.ZoneArg;

public class TRAddShadingTransformer extends IdTransformer{
	private final SubDiagramIndexArg target;
	private ArrayList<ZoneArg> targetZones;
	
	public TRAddShadingTransformer(SubDiagramIndexArg target, ArrayList<ZoneArg> targetZones)  {
	    this.target = target;
	    this.targetZones = targetZones;
	}
	
//    @Override
//    public SpiderDiagram transform(PrimarySpiderDiagram psd,
//                                   int diagramIndex,
//                                   ArrayList<CompoundSpiderDiagram> parents,
//                                   ArrayList<Integer> childIndices) {
//    	
//    	LUCOPDiagram lucop = (LUCOPDiagram) psd;
//    	
//    	int subDiagramIndex = target.getSubDiagramIndex();
//    	TreeSet<Zone> newShadedZones = new TreeSet<Zone>();
//    	TreeSet<Zone> newPresentZones = new TreeSet<Zone>();
//    	
//    	if (diagramIndex == subDiagramIndex) {
//    		if (!lucop.getPresentZonesMod().containsAll(getTargetZones())) {
//    			throw new TransformationException("The zones to be shaded do not exist in the target diagram or it is already shaded.");
//    			}
//    		newShadedZones = lucop.getShadedZonesMod();
//    		newShadedZones.addAll(getTargetZones());
//    		
//    		newPresentZones = lucop.getPresentZonesMod();
//    		newPresentZones.removeAll(getTargetZones());
//    		
//    	return LUCOPDiagram.createLUCOPDiagram(
//    			lucop.getSpidersMod(), 
//        		lucop.getHabitatsMod(), 
//        		newShadedZones, 
//        		newPresentZones, 
//        		lucop.getArrowsMod(),
//        		lucop.getSpiderLabelsMod(),
//        		lucop.getCurveLabelsMod());
//    	}
//    	
//		return lucop;
//      }
    	
        @Override
        public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                       int diagramIndex,
                                       ArrayList<CompoundSpiderDiagram> parents,
                                       ArrayList<Integer> childIndices) {
        	
        	LUCOPDiagram lucop = (LUCOPDiagram) psd;
        	
        	int subDiagramIndex = target.getSubDiagramIndex();
        	
        	if (diagramIndex == subDiagramIndex) {
        		if (!lucop.getPresentZones().containsAll(getTargetZones())) {
        			throw new TransformationException("The zones to be shaded do not exist in the target diagram or it is already shaded.");
        			}
        		
        	return lucop.addShading(getTargetZones());
        	}
    	
		return lucop;
    }
    
    
    private TreeSet<Zone> getTargetZones() {
        TreeSet<Zone> zones = new TreeSet<Zone>();
        for (ZoneArg targetZone : targetZones) {
            zones.add(targetZone.getZone());
        }
        return zones;
    }
    
    

}

