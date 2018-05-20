package speedith.core.reasoning.rules.transformers;

import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.args.ZonesInOutArg;
import speedith.core.reasoning.util.unitary.ZoneTransferSingle;


public class TRAddUnCurveTransformer extends IdTransformer{
	
    private final ZonesInOutArg  arg;
    private final boolean applyForward;

    public TRAddUnCurveTransformer(ZonesInOutArg arg, boolean applyForward) {
        this.arg = arg;
        this.applyForward = applyForward;
    }
	
    
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
       	LUCOPDiagram lucop = (LUCOPDiagram) psd;
    	
    	if (diagramIndex == arg.getSubDiagramIndex()){
    		
    	String name;
    	name = JOptionPane.showInputDialog(null,"Enter a fresh name for the curve:");
    	if(name.isEmpty()){
    		JOptionPane.showMessageDialog(null,"The curve has to have a fresh non-empty name.","Input error",JOptionPane.ERROR_MESSAGE);
    		throw new IllegalArgumentException("The curve has to have a fresh non-empty name");
    	}
    		
        PrimarySpiderDiagram transformedDiagram = new 
        		ZoneTransferSingle(psd).transferContour(name,arg.getZonesIn(),arg.getZonesOut());
        
        TreeMap<String,String> curveLabels = new TreeMap<>(lucop.getCurveLabels());
        curveLabels.put(name,"");
        
    	return LUCOPDiagram.createLUCOPDiagram(
    			transformedDiagram.getSpidersMod(), 
    			transformedDiagram.getHabitatsMod(), 
    			transformedDiagram.getShadedZonesMod(), 
    			transformedDiagram.getPresentZonesMod(),  
        		lucop.getArrowsMod(),
        		lucop.getSpiderLabelsMod(),
        		curveLabels);
    		
    	}
    	return null;	
    
    }
    
    


}
