package speedith.core.reasoning.rules.instructions.copIns;

import java.util.ArrayList;
import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.selection.ContoursSelectionStep;
import speedith.core.reasoning.args.selection.SelectPrimaryDiagramStep;
import speedith.core.reasoning.args.selection.SelectSubDiagramStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;

public class SelectCurvesAndSingleSubDiagramInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {

    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
        ContoursSelectionStep contoursSelectionStep = new ContoursSelectionStep();
        SelectSubDiagramStep selectSubDiagramStep = new SelectPrimaryDiagramStep(false);
        List<SelectionStep> result = new ArrayList<>();
        result.add(contoursSelectionStep);
        result.add(selectSubDiagramStep);
        return result;
    }
	


    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
        
        List<RuleArg> selections = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg ruleArg : selections) {
            if (ruleArg instanceof ContourArg) {
                ContourArg contourArg = (ContourArg) ruleArg;
                ruleArgs.add(new ContourArg(subgoalIndex, contourArg.getSubDiagramIndex(), contourArg.getContour()));
            }
        }
        
        List<RuleArg> subDiagrams = selectionSequence.getAcceptedSelectionsForStepAt(1);
        for (RuleArg selectedDiagram : subDiagrams) {
            if (selectedDiagram instanceof SubDiagramIndexArg) {
                SubDiagramIndexArg subDiagramIndexArg = (SubDiagramIndexArg) selectedDiagram;
                ruleArgs.add(new SubDiagramIndexArg(subgoalIndex, subDiagramIndexArg.getSubDiagramIndex()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
    }
}
