package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import speedith.core.lang.Arrow;
import speedith.core.lang.Cardinality;
import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.LUCarCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderComparator;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.Zone;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.util.unitary.ZoneTransferSingle;

public class RemoveSpiderTransformer extends IdTransformer {
	
	private final SpiderArg  arg;
    private final boolean applyForward;

    public RemoveSpiderTransformer(SpiderArg arg, boolean applyForward) {
        this.arg = arg;
        this.applyForward = applyForward;
    }
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	if (diagramIndex == arg.getSubDiagramIndex()){
    		    		
    		Region spiderHabitat = psd.getSpiderHabitat(arg.getSpider());
    		
    		for(Zone zone : spiderHabitat.sortedZones()){
    			if(psd.getShadedZones().contains(zone)){
    	    		throw new IllegalArgumentException("The habitat of the spider cannot contain shaded zones.");
    			}
    		}
    		
    		TreeSet<String> updatedSpiders = new TreeSet<String>(psd.getSpiders());
    		updatedSpiders.remove(arg.getSpider());
    		
    		TreeMap<String,Region> updatedHabitat = new TreeMap<String,Region>(psd.getHabitats());
    		updatedHabitat.remove(arg.getSpider());
    		
    		if (psd instanceof CompleteCOPDiagram){
    			CompleteCOPDiagram cCop = (CompleteCOPDiagram) psd;
    			
        		TreeSet<Arrow> updatedArrows = new TreeSet<Arrow>(cCop.getArrows());
        		TreeMap<Arrow,Cardinality> updatedArrowCar = new TreeMap<Arrow,Cardinality>(cCop.getArrowCardinalities());
                for (Arrow arrow : cCop.getArrows()){
                    if(arg.getSpider().equals(arrow.arrowSource()) || arg.getSpider().equals(arrow.arrowTarget())){
                		updatedArrows.remove(arrow);
                		updatedArrowCar.remove(arrow);
                    }
                  }
        		
        		TreeMap<String,String> updatedSpiderLabel = new TreeMap<String,String>(cCop.getSpiderLabels());
        		updatedSpiderLabel.remove(arg.getSpider());
        		    			
        		TreeSet<SpiderComparator> updatedSpiderComparators = new TreeSet<SpiderComparator>(cCop.getSpiderComparators());
                for (SpiderComparator sc : cCop.getSpiderComparators()){
                    if(arg.getSpider().equals(sc.getComparable1()) || arg.getSpider().equals(sc.getComparable2())){
                    	updatedSpiderComparators.remove(sc);
                    }
                  }
        		
    			return SpiderDiagrams.createCompleteCOPDiagram(
    	    			updatedSpiders, 
    	    			updatedHabitat, 
    	    			cCop.getShadedZones(), 
    	    			cCop.getPresentZones(),  
    	        		updatedArrows,
    	        		updatedSpiderLabel,
    	        		cCop.getCurveLabels(),
    	        		updatedArrowCar,
    	        		updatedSpiderComparators
    	        		);
	
    		}else
    		{
    			LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) psd;
    			TreeSet<Arrow> updatedArrows = new TreeSet<Arrow>(luCarCop.getArrows());
        		TreeMap<Arrow,Cardinality> updatedArrowCar = new TreeMap<Arrow,Cardinality>(luCarCop.getArrowCardinalities());
                for (Arrow arrow : luCarCop.getArrows()){
                    if(arg.getSpider().equals(arrow.arrowSource()) || arg.getSpider().equals(arrow.arrowTarget())){
                		updatedArrows.remove(arrow);
                		updatedArrowCar.remove(arrow);
                    }
                  }
        		
        		TreeMap<String,String> updatedSpiderLabel = new TreeMap<String,String>(luCarCop.getSpiderLabels());
        		updatedSpiderLabel.remove(arg.getSpider());
        			
            
        	return LUCarCOPDiagram.createLUCarCOPDiagram(
        			updatedSpiders, 
        			updatedHabitat, 
        			luCarCop.getShadedZones(), 
        			luCarCop.getPresentZones(),  
            		updatedArrows,
            		updatedSpiderLabel,
            		luCarCop.getCurveLabels(),
            		updatedArrowCar);
    		
    			
    		}
    		
    		
    		
    		
    		
    	
    		

//    		LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) psd;
//    		
//    		Region spiderHabitat = luCarCop.getSpiderHabitat(arg.getSpider());
//    		
//    		for(Zone zone : spiderHabitat.sortedZones()){
//    			if(luCarCop.getShadedZones().contains(zone)){
//    	    		throw new IllegalArgumentException("The habitat of the spider cannot contain shaded zones.");
//    			}
//    		}
//    		
//    		TreeSet<String> updatedSpiders = new TreeSet<String>(luCarCop.getSpiders());
//    		updatedSpiders.remove(arg.getSpider());
//    		
//    		TreeMap<String,Region> updatedHabitat = new TreeMap<String,Region>(luCarCop.getHabitats());
//    		updatedHabitat.remove(arg.getSpider());
//    		
//    		TreeSet<Arrow> updatedArrows = new TreeSet<Arrow>(luCarCop.getArrows());
//    		TreeMap<Arrow,Cardinality> updatedArrowCar = new TreeMap<Arrow,Cardinality>(luCarCop.getArrowCardinalities());
//            for (Arrow arrow : luCarCop.getArrows()){
//                if(arg.getSpider().equals(arrow.arrowSource()) || arg.getSpider().equals(arrow.arrowTarget())){
//            		updatedArrows.remove(arrow);
//            		updatedArrowCar.remove(arrow);
//                }
//              }
//    		
//    		TreeMap<String,String> updatedSpiderLabel = new TreeMap<String,String>(luCarCop.getSpiderLabels());
//    		updatedSpiderLabel.remove(arg.getSpider());
//    			
//        
//    	return LUCarCOPDiagram.createLUCarCOPDiagram(
//    			updatedSpiders, 
//    			updatedHabitat, 
//    			luCarCop.getShadedZones(), 
//    			luCarCop.getPresentZones(),  
//        		updatedArrows,
//        		updatedSpiderLabel,
//        		luCarCop.getCurveLabels(),
//        		updatedArrowCar);
    		
    	}
    	return null;	
    
    }
    

}
