package speedith.core.reasoning.rules.instructions.copIns;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.copArgs.SpiderComparatorArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SelectSingleSpiderComparatorStep;

public class SelectSpiderComparatorInstruction implements RuleApplicationInstruction<SpiderComparatorArg> {

//	SelectSingleSpiderComparatorStep spiderComparatorSelectionStep = new SelectSingleSpiderComparatorStep();
    private List<? extends SelectionStep> spiderComparatorSelectionStep = Collections.unmodifiableList(Arrays.asList(new SelectSingleSpiderComparatorStep()));

	

    @Override
    public List<? extends SelectionStep> getSelectionSteps() {
        return spiderComparatorSelectionStep;
    }
    

    @Override
    public SpiderComparatorArg extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
    	SpiderComparatorArg sca = (SpiderComparatorArg) selectionSequence.getAcceptedSelectionsForStepAt(0).get(0);
        return new SpiderComparatorArg(subgoalIndex, sca.getSubDiagramIndex(),sca.getSpiderComparator());
    }
    
}


