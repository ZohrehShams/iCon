package speedith.core.reasoning.rules.instructions.copIns;

import java.util.ArrayList;
import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.ContoursSelectionStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.ArrowsSelectionStep;

public class AddSpiderToDashedArrowImageInstruction implements RuleApplicationInstruction<MultipleRuleArgs>{
	
	@Override
	public List<? extends SelectionStep> getSelectionSteps() {
        ArrowsSelectionStep firstArrowSelectionStep = new ArrowsSelectionStep();
        ContoursSelectionStep contourSelectionStep = new ContoursSelectionStep();
        ArrowsSelectionStep secondArrowSelectionStep = new ArrowsSelectionStep();
        List<SelectionStep> result = new ArrayList<>();
        result.add(firstArrowSelectionStep);
        result.add(contourSelectionStep);
        result.add(secondArrowSelectionStep);
        return result;
	}
	
	
	@Override
	public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
		ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
		
        List<RuleArg> firstArrow = selectionSequence.getAcceptedSelectionsForStepAt(0);
        
        for (RuleArg ruleArg : firstArrow) {
            if (ruleArg instanceof ArrowArg) {
                ArrowArg arrowArg = (ArrowArg) ruleArg;
                ruleArgs.add(new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow()));
            }
        }
        
        List<RuleArg> curve = selectionSequence.getAcceptedSelectionsForStepAt(1);
        
        for (RuleArg ruleArg : curve) {
            if (ruleArg instanceof ContourArg) {
                ContourArg contourArg = (ContourArg) ruleArg;
                ruleArgs.add(new ContourArg(subgoalIndex, contourArg.getSubDiagramIndex(), contourArg.getContour()));
            }
        }
        
        
        List<RuleArg> secondArrow = selectionSequence.getAcceptedSelectionsForStepAt(2);
        
        for (RuleArg ruleArg : secondArrow) {
            if (ruleArg instanceof ArrowArg) {
                ArrowArg arrowArg = (ArrowArg) ruleArg;
                ruleArgs.add(new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow()));
            }
        }
        
        
        return new MultipleRuleArgs(ruleArgs);
	}


}
