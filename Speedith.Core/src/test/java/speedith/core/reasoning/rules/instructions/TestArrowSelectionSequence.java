package speedith.core.reasoning.rules.instructions;


import speedith.core.lang.NullSpiderDiagram;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.selection.ArrowsSelectionStep;
import speedith.core.reasoning.args.selection.SelectionSequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;


public class TestArrowSelectionSequence extends SelectionSequence {
    private final ArrayList<ArrowArg> arrowArg;

    public TestArrowSelectionSequence(ArrowArg... arrowArg) {
        super(NullSpiderDiagram.getInstance(), asList(new ArrowsSelectionStep()));
        this.arrowArg = new ArrayList<>(asList(arrowArg));
    }

    @Override
    public List<RuleArg> getAcceptedSelectionsForStepAt(int stepIndex) {
        return Collections.<RuleArg>unmodifiableList(arrowArg);
    }

    @Override
    public int getAcceptedSelectionsCount(int stepIndex) {
        return arrowArg.size();
    }

}
