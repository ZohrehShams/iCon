package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;
import java.util.Scanner;
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
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.ZoneArg;

public class TRAddSpiderTransformer extends IdTransformer{
	private final SubDiagramIndexArg target;
	private ArrayList<ZoneArg> targetZones;
	
	public TRAddSpiderTransformer(SubDiagramIndexArg target, ArrayList<ZoneArg> targetZones)  {
	    this.target = target;
	    this.targetZones = targetZones;
	}
	
    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
        LUCOPDiagram lucop = (LUCOPDiagram) psd;
    	int subDiagramIndex = target.getSubDiagramIndex();
    	if (diagramIndex == subDiagramIndex) {
    		Region region = new Region(getTargetZones());
    		
            String name;
            name = JOptionPane.showInputDialog(null,"Enter a fresh spider name:");
        	if(name.isEmpty()){
        		JOptionPane.showMessageDialog(null,"The spider has to have a fresh non-empty name.","Input error",JOptionPane.ERROR_MESSAGE);
        		throw new IllegalArgumentException("The spiders has to have a fresh non-empty name");
        	}
            
            
            String label;
            label = JOptionPane.showInputDialog(null,"Enter a spider label:");
            
//    		Scanner reader = new Scanner(System.in); 
//    		System.out.println("Enter a fresh spider name: ");
//    		String label = reader.next();
    		
//    		Scanner reader2 = new Scanner(System.in); 
//    		System.out.println("Enter a fresh spider label: ");
//    		String label2 = reader2.next();
    		
    		return (LUCOPDiagram) lucop.addLUSpider(name,region,label);	
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

