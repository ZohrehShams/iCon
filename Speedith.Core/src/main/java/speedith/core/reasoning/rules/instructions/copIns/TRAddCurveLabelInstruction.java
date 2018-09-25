package speedith.core.reasoning.rules.instructions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.selection.ContoursSelectionStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;


public class TRAddCurveLabelInstruction implements RuleApplicationInstruction<ContourArg>{
    
    private final List<? extends SelectionStep> steps = Arrays.asList(new ContoursSelectionStep());

    private TRAddCurveLabelInstruction() {
    }
    
    
    public static TRAddCurveLabelInstruction getInstance() {
        return SingletonContainer.TheInstructions;
    }
    
    
    public List<? extends SelectionStep> getSelectionSteps() {
        return Collections.unmodifiableList(steps);
    }
    
    
    
    public ContourArg extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex){
    	
    	
    	
    	ContourArg ca = (ContourArg) selectionSequence.getAcceptedSelectionsForStepAt(0).get(0);
    	
    	return ca;
    	//return new MultipleRuleArgs(ca);
    	
    }
    
    
    
    
    private static final class SingletonContainer {
        private static final TRAddCurveLabelInstruction TheInstructions = new TRAddCurveLabelInstruction();
    }
}
