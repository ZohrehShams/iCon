package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram;
import speedith.core.lang.cop.SpiderComparator;
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
    	
    	
       	if(!(psd instanceof COPDiagram)){
       		throw new TransformationException("The rule is not applicaple to this diagram.");
       	}
       	
    	
    	if (diagramIndex == spiderArgs.get(0).getSubDiagramIndex()){
    		
    		for (String spider : getSpidersTarget()){
    			Region spiderHabitat = psd.getSpiderHabitat(spider);
        		for(Zone zone : spiderHabitat.sortedZones()){
        			if(psd.getShadedZones().contains(zone)){
        	    		throw new IllegalArgumentException("The habitat of the spider cannot contain any shaded zones.");
        			}
        		}
        		}
    		
    		COPDiagram cop = (COPDiagram) psd;
    		String[] spidersArray = getSpidersTarget().toArray(new String[getSpidersTarget().size()]);
    		return cop.deleteSpider(spidersArray);
    		
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
