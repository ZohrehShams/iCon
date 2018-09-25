package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import speedith.core.lang.Arrow;
import speedith.core.lang.COPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.args.ContourArg;


public class TRAddCurveLabelTransformer extends IdTransformer{
	
    private final ArrayList<ContourArg>  arg;
    private final boolean applyForward;

    public TRAddCurveLabelTransformer(ArrayList<ContourArg> arg, boolean applyForward) {
        this.arg = arg;
        this.applyForward = applyForward;
    }
	
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
       	LUCOPDiagram lucop = (LUCOPDiagram) psd;
    	
    	if (diagramIndex == arg.get(0).getSubDiagramIndex()){
    		
            if (arg.size() > 1) {
              throw new IllegalArgumentException("Only a single curve can be chosen.");
              }
    		
    		ContourArg cArg = (ContourArg) arg.get(0);
    		

    		String label = lucop.getCurveLabels().get(cArg.getContour());
    		if (! label.isEmpty()){
              throw new IllegalArgumentException("The chosen curve should be unlabelled.");
            }
    		
    		
    		String newLabel; 
    		newLabel= JOptionPane.showInputDialog(null,"Enter a label for the curve: ");
    		
    		TreeMap<String,String> newCurveLabels = new TreeMap<>(lucop.getCurveLabels());
    		newCurveLabels.remove(cArg.getContour());
    		newCurveLabels.put(cArg.getContour(),newLabel);
    		
    		//Zohreh: I'm not sure this condition is needed?!
//            if (lucop.getAllContours().contains(label)) {
//          	  throw new IllegalArgumentException("The curve has to have a fresh label.");
//            } 
    		
    		
    		
//            TreeMap<String, Region> habitats = new TreeMap<String,Region>();
//            for (String spider : lucop.getHabitatsMod().keySet()){
//            	Region region = lucop.getHabitatsMod().get(spider);
//            	TreeSet<Zone> newZones = new TreeSet<Zone>();
//            	for (Zone zone: region.sortedZones()){
//            		Zone updatedZone = zone.UpdateZone(cArg.getContour(),  label);
//            		newZones.add(updatedZone);	
//            	}
//            	Region newRegion = new Region(newZones);
//            	habitats.put(spider,newRegion);
//            }
//        
//       
//        
//        
//		TreeSet<Zone> shadedZones =  new TreeSet<Zone>();
//        for (Zone zone: cop.getShadedZonesMod()){
//        	shadedZones.add(zone.UpdateZone(cArg.getContour(), label));
//        	}
//
//    	
//    	
//        TreeSet<Zone> presentZones = new TreeSet<Zone>();
//        for (Zone zone: cop.getPresentZonesMod()){
//        	presentZones.add(zone.UpdateZone(cArg.getContour(), label));
//        	}
//        
//        
//        TreeSet<Arrow> arrows = cop.getArrowsMod();
//        for (Arrow arrow: cop.getArrowsMod()){
//        	if(arrow.arrowSource().equals(cArg.getContour())){
//        		arrows.remove(arrow);
//        		arrows.add(new Arrow(label,arrow.arrowTarget(),arrow.arrowType()));
//        	}
//        	
//        	if(arrow.arrowTarget().equals(cArg.getContour())){
//        		arrows.remove(arrow);
//        		arrows.add(new Arrow(arrow.arrowTarget(),label,arrow.arrowType()));
//        	}
//        }
//        
//
//    	return COPDiagram.createCOPDiagram(
//    			cop.getSpidersMod(), 
//    			habitats, 
//    			shadedZones, 
//    			presentZones,  
//        		arrows);
    		
    		
        	return LUCOPDiagram.createLUCOPDiagram(
			lucop.getSpidersMod(), 
			lucop.getHabitatsMod(), 
			lucop.getShadedZonesMod(), 
			lucop.getPresentZonesMod(),  
    		lucop.getArrowsMod(),
    		lucop.getSpiderLabelsMod(),
    		newCurveLabels);
    	}
    	
    	return null;	
    }

}
