package speedith.core.reasoning.rules.instructions.copIns;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SelectionStepSpiderOrContour;
import speedith.core.reasoning.args.selection.copSelection.SelectionStepSyntax;

public class DeleteSyntaxInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {
	
    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
    	return asList(new SelectionStepSyntax());
    }

    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<RuleArg> ruleArgs = new ArrayList<>();
        List<RuleArg> selections = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg ruleArg : selections) {
            if (ruleArg instanceof ContourArg) {
                ContourArg contourArg = (ContourArg) ruleArg;
                ruleArgs.add(new ContourArg(subgoalIndex, contourArg.getSubDiagramIndex(), contourArg.getContour()));
            }
            if (ruleArg instanceof SpiderArg) {
            	SpiderArg spiderArg = (SpiderArg) ruleArg;
                ruleArgs.add(new SpiderArg(subgoalIndex, spiderArg.getSubDiagramIndex(), spiderArg.getSpider()));
            }
            if (ruleArg instanceof ArrowArg) {
            	ArrowArg arrowArg = (ArrowArg) ruleArg;
                ruleArgs.add(new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
    }


}
