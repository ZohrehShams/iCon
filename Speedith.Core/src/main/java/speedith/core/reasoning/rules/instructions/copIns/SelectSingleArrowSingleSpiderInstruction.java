package speedith.core.reasoning.rules.instructions.copIns;

import java.util.ArrayList;
import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.copArgs.SpiderComparatorArg;
import speedith.core.reasoning.args.selection.SelectSingleSpiderStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.ArrowsSelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SelectSingleSpiderComparatorStep;

public class SelectSingleArrowSingleSpiderInstruction implements RuleApplicationInstruction<MultipleRuleArgs>{
	
	
	@Override
	public List<? extends SelectionStep> getSelectionSteps() {
        ArrowsSelectionStep arrowSelectionStep = new ArrowsSelectionStep();
        SelectSingleSpiderStep spiderSelectionStep = new SelectSingleSpiderStep();
        List<SelectionStep> result = new ArrayList<>();
        result.add(arrowSelectionStep);
        result.add(spiderSelectionStep);
        return result;
	}

	@Override
	public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
		ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
		
        List<RuleArg> arrow = selectionSequence.getAcceptedSelectionsForStepAt(0);
        
        for (RuleArg ruleArg : arrow) {
            if (ruleArg instanceof ArrowArg) {
                ArrowArg arrowArg = (ArrowArg) ruleArg;
                ruleArgs.add(new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow()));
            }
        }
        
        List<RuleArg> spider = selectionSequence.getAcceptedSelectionsForStepAt(1);
        
        for (RuleArg ruleArg : spider) {
            if (ruleArg instanceof SpiderArg) {
                SpiderArg spiderArg = (SpiderArg) ruleArg;
                ruleArgs.add(new SpiderArg(subgoalIndex, spiderArg.getSubDiagramIndex(), spiderArg.getSpider()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
	}



}
