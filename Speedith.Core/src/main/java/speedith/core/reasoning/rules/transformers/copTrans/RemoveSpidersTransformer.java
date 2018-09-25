package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.List;

import speedith.core.lang.COPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.args.SpiderArg;

public class RemoveSpidersTransformer extends IdTransformer {
	
	private final List<SpiderArg>  spiderArgs;
    private final ApplyStyle  applyStyle;

    public RemoveSpidersTransformer(List<SpiderArg> args, ApplyStyle applyStyle) {
        this.spiderArgs = args;
        this.applyStyle = applyStyle;
    }
    
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	if(spiderArgs.isEmpty()){
    		return psd;
    	}
    	
       	//PrimarySpiderDiagram temp = psd;
       	if(!(psd instanceof COPDiagram)){
       		throw new TransformationException("The rule is not applicaple to this diagram.");
       	}
       	
    	COPDiagram cop = (COPDiagram) psd;
    	COPDiagram temp = cop;
    	
    	if (diagramIndex == spiderArgs.get(0).getSubDiagramIndex()){
    		
    		for (String spider : getSpidersTarget()){
    			Region spiderHabitat = psd.getSpiderHabitat(spider);
        		for(Zone zone : spiderHabitat.sortedZones()){
        			if(psd.getShadedZones().contains(zone)){
        	    		throw new IllegalArgumentException("The habitat of the spider cannot contain any shaded zones.");
        			}
        		}
        		temp = temp.deleteSpider(spider);
        		}
    		
    		return temp;
    		
//    		TreeSet<String> updatedSpiders = new TreeSet<String>(psd.getSpiders());
//    		TreeMap<String,Region> updatedHabitat = new TreeMap<String,Region>(psd.getHabitats());
//    		
//      		TreeSet<Arrow> updatedArrows = new TreeSet<Arrow>(luCarCop.getArrows());
//    		TreeMap<Arrow,Cardinality> updatedArrowCar = new TreeMap<Arrow,Cardinality>(luCarCop.getArrowCardinalities());
//    		TreeMap<String,String> updatedSpiderLabel = new TreeMap<String,String>(luCarCop.getSpiderLabels());
//
//    		
//    		for (String spider : getSpidersTarget()){
//    			Region spiderHabitat = psd.getSpiderHabitat(spider);
//    			
//        		for(Zone zone : spiderHabitat.sortedZones()){
//        			if(psd.getShadedZones().contains(zone)){
//        	    		throw new IllegalArgumentException("The habitat of the spider cannot contain shaded zones.");
//        			}
//        		}
//        		
//        		updatedSpiders.remove(spider);
//        		updatedHabitat.remove(spider);
//			
//			
//            for (Arrow arrow : luCarCop.getArrows()){
//                if(spider.equals(arrow.arrowSource()) || spider.equals(arrow.arrowTarget())){
//            		updatedArrows.remove(arrow);
//            		updatedArrowCar.remove(arrow);
//                }
//              }
//    		
//    		updatedSpiderLabel.remove(spider);}
//    		
//    		if (temp instanceof CompleteCOPDiagram){
//    			CompleteCOPDiagram compCop = (CompleteCOPDiagram) temp;
//    			TreeSet<SpiderComparator> updatedSpiderComparators = new TreeSet<SpiderComparator>(compCop.getSpiderComparators());
//
//    			for (String spider : getSpidersTarget()){	
//    				for (SpiderComparator sc : compCop.getSpiderComparators()){
//    					if(spider.equals(sc.getComparable1()) ||spider.equals(sc.getComparable2())){
//                    	updatedSpiderComparators.remove(sc);
//                    }
//                  }}
//    			
//    			
//    			return SpiderDiagrams.createCompleteCOPDiagram(
//    	    			updatedSpiders, 
//    	    			updatedHabitat, 
//    	    			compCop.getShadedZones(), 
//    	    			compCop.getPresentZones(),  
//    	        		updatedArrows,
//    	        		updatedSpiderLabel,
//    	        		compCop.getCurveLabels(),
//    	        		updatedArrowCar,
//    	        		updatedSpiderComparators
//    	        		);
//    		}else{
//    			return LUCarCOPDiagram.createLUCarCOPDiagram(
//            			updatedSpiders, 
//            			updatedHabitat, 
//            			luCarCop.getShadedZones(), 
//            			luCarCop.getPresentZones(),  
//                		updatedArrows,
//                		updatedSpiderLabel,
//                		luCarCop.getCurveLabels(),
//                		updatedArrowCar);
//    		}
    				
    		
    	}
    	return null;	
    
    }
    
    
    private List<String> getSpidersTarget() {
        ArrayList<String> spiders = new ArrayList<>();
        for (SpiderArg spiderArg : spiderArgs) {
        	spiders.add(spiderArg.getSpider());
        }
        return spiders;
    }
    

}
