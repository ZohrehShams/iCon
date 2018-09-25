package speedith.core.reasoning.rules.instructions.copIns;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.ArrowsSelectionStep;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Provides instruction to select arrows.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */

public class SelectArrowsInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {
	
	@Override
    public List<? extends SelectionStep> getSelectionSteps() {
        return asList(new ArrowsSelectionStep());
    }
	
    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<ArrowArg> ruleArgs = new ArrayList<>();
        List<RuleArg> selections = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg ruleArg : selections) {
            if (ruleArg instanceof ArrowArg) {
                ArrowArg arrowArg = (ArrowArg) ruleArg;
                ruleArgs.add(new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
    }

}
