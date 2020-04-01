package speedith.core.reasoning.rules.instructions.copIns;

import java.util.ArrayList;
import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.selection.SelectSingleSpiderStep;
import speedith.core.reasoning.args.selection.SelectPrimaryDiagramStep;
import speedith.core.reasoning.args.selection.SelectSubDiagramStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;

public class SelectSpiderAndSingleSubDiagramInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {

    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
    	SelectSingleSpiderStep spiderSelectionStep = new SelectSingleSpiderStep();
        SelectSubDiagramStep selectSubDiagramStep = new SelectPrimaryDiagramStep(false);
        List<SelectionStep> result = new ArrayList<>();
        result.add(spiderSelectionStep);
        result.add(selectSubDiagramStep);
        return result;
    }
	


    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
        
        List<RuleArg> selections = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg ruleArg : selections) {
            if (ruleArg instanceof SpiderArg) {
                SpiderArg spiderArg = (SpiderArg) ruleArg;
                ruleArgs.add(new SpiderArg(subgoalIndex, spiderArg.getSubDiagramIndex(), spiderArg.getSpider()));
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
