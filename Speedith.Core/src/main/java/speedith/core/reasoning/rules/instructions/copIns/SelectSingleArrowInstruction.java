package speedith.core.reasoning.rules.instructions.copIns;

import static java.util.Arrays.asList;

import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SelectSingleArrowStep;


public class SelectSingleArrowInstruction implements RuleApplicationInstruction<ArrowArg> {
	    
    public List<SelectSingleArrowStep> selectionSteps = asList(new SelectSingleArrowStep(false));

    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
        return selectionSteps;
    }

    @Override
    public ArrowArg extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        List<RuleArg> ruleArguments = selectionSequence.getAcceptedSelectionsForStepAt(0);
        ArrowArg arrowArg = (ArrowArg) ruleArguments.get(0);
        return new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow());
    }
}
