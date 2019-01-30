package speedith.core.reasoning.rules.instructions.copIns;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.SelectPrimaryDiagramStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Provides instruction to select cops.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */

public class SelectCOPsInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {
	
	@Override
    public List<? extends SelectionStep> getSelectionSteps() {
        return asList(new SelectPrimaryDiagramStep());
    }
	
    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
        List<RuleArg> selections = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg ruleArg : selections) {
            if (ruleArg instanceof SubDiagramIndexArg) {
            	SubDiagramIndexArg diagramArg = (SubDiagramIndexArg) ruleArg;
                ruleArgs.add(new SubDiagramIndexArg(subgoalIndex, diagramArg.getSubDiagramIndex()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
    }

}











