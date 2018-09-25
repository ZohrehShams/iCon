package speedith.core.reasoning.rules.instructions.copIns;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.selection.*;

import java.util.ArrayList;
import java.util.List;

public class SelectSourceAndTargetInstruction implements RuleApplicationInstruction<MultipleRuleArgs> {

    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
    	SelectionStepAny selectSourceStep = new SelectionStepAny();
    	SelectionStepAny selectTargetStep = new SelectionStepAny();
        List<SelectionStep> result = new ArrayList<>();
        result.add(selectSourceStep);
        result.add(selectTargetStep);
        return result;
    }

    @Override
    public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
        ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
        
        List<RuleArg> sources = selectionSequence.getAcceptedSelectionsForStepAt(0);
        for (RuleArg sourceArg : sources) {
            if (sourceArg instanceof ContourArg) {
                ContourArg contourArg = (ContourArg) sourceArg;
                ruleArgs.add(new ContourArg(subgoalIndex, contourArg.getSubDiagramIndex(), contourArg.getContour()));
            }
            if (sourceArg instanceof SpiderArg) {
                SpiderArg spiderArg = (SpiderArg) sourceArg;
                ruleArgs.add(new ContourArg(subgoalIndex, spiderArg.getSubDiagramIndex(), spiderArg.getSpider()));
            } 
        }
        
        
        List<RuleArg> targets = selectionSequence.getAcceptedSelectionsForStepAt(1);
        for (RuleArg targetArg : targets ) {
            if (targetArg instanceof ContourArg) {
                ContourArg contourArg = (ContourArg) targetArg;
                ruleArgs.add(new ContourArg(subgoalIndex, contourArg.getSubDiagramIndex(), contourArg.getContour()));
            }
            if (targetArg instanceof SpiderArg) {
                SpiderArg spiderArg = (SpiderArg) targetArg;
                ruleArgs.add(new ContourArg(subgoalIndex, spiderArg.getSubDiagramIndex(), spiderArg.getSpider()));
            } 
        }
        
        return new MultipleRuleArgs(ruleArgs);
    }
}
