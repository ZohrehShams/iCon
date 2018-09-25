package speedith.core.reasoning.rules.instructions.copIns;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.Operator;
import speedith.core.lang.Region;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.args.copArgs.ZonesInOutArg;
import speedith.core.reasoning.args.selection.SelectZonesStep;
import speedith.core.reasoning.args.selection.SelectZonesStepOutEmp;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SelectZonesStepInEmp;


public class TRAddUnCurveInstruction implements RuleApplicationInstruction<ZonesInOutArg>{
	
    private final List<? extends SelectionStep> steps = Arrays.asList(SelectZonesStepInEmp.getInstance(), 
    		SelectZonesStepOutEmp.getInstance());

    private TRAddUnCurveInstruction() {
    }
    
    
    public static TRAddUnCurveInstruction getInstance() {
        return SingletonContainer.TheInstructions;
    }
    
    
    public List<? extends SelectionStep> getSelectionSteps() {
        return Collections.unmodifiableList(steps);
    }
    
    
    
    public ZonesInOutArg extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex){
    	
    	TreeSet<Zone> zonesIn = new TreeSet<>();
    	TreeSet<Zone> zonesOut = new TreeSet<>();
 
    	ZoneArg zaIn =null;
    	ZoneArg zaOut =null;
    	
    	
    	if(selectionSequence.getAcceptedSelectionsForStepAt(0) != null){
    		zaIn = (ZoneArg) selectionSequence.getAcceptedSelectionsForStepAt(0).get(0);
    	        for (RuleArg ruleArg : selectionSequence.getAcceptedSelectionsForStepAt(0)) {
    	            ZoneArg zoneArg = (ZoneArg) ruleArg;
    	            zonesIn.add(zoneArg.getZone());
    	        }
    	}
    	
    	
    	if(selectionSequence.getAcceptedSelectionsForStepAt(1) != null){
    		zaOut = (ZoneArg) selectionSequence.getAcceptedSelectionsForStepAt(1).get(0);
    	        for (RuleArg ruleArg : selectionSequence.getAcceptedSelectionsForStepAt(1)) {
    	            ZoneArg zoneArg = (ZoneArg) ruleArg;
    	            zonesOut.add(zoneArg.getZone());
    	        }
    	}
    	
     
    	if (zaIn  == null && zaOut == null){
    		JOptionPane.showMessageDialog(null,"The set of zones in the curve and out of the curve cannot be empty simultaneously.", "Inputerror",JOptionPane.ERROR_MESSAGE);
    	}
    	
      if (zaIn != null) {return new ZonesInOutArg(subgoalIndex, zaIn.getSubDiagramIndex(), zonesIn, zonesOut);}
      else {return new ZonesInOutArg(subgoalIndex, zaOut.getSubDiagramIndex(), zonesIn, zonesOut);} 
        
//          if (zaIn != null) {return new ZonesInOutArg(subgoalIndex, zaIn.getSubDiagramIndex(), zonesIn, zonesOut);}
//          else {if (zaOut != null) {return new ZonesInOutArg(subgoalIndex, zaOut.getSubDiagramIndex(), zonesIn, zonesOut);}
//          else{throw new TransformationException("Both steps cannot be empty.");}
//          }
    }
    
    
    
    
    private static final class SingletonContainer {
        private static final TRAddUnCurveInstruction TheInstructions = new TRAddUnCurveInstruction();
    }
}
