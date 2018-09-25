package speedith.core.reasoning.rules.instructions;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SpidersSelectionStep;

import java.util.List;

import static java.util.Arrays.asList;

import java.util.ArrayList;

public class SelectSpidersInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {

    
    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
        return asList(new SpidersSelectionStep());
    }

    
    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<SpiderArg> ruleArgs = new ArrayList<>();
        List<RuleArg> selections = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg ruleArg : selections) {
            if (ruleArg instanceof SpiderArg) {
                SpiderArg spiderArg = (SpiderArg) ruleArg;
                ruleArgs.add(new SpiderArg(subgoalIndex, spiderArg.getSubDiagramIndex(), spiderArg.getSpider()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
    }
}
