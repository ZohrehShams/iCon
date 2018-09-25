package speedith.core.reasoning.args.selection;

import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.RuleArg;

import java.util.Locale;

/**
 * It checks if what is chosen is an object of type arrow.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */
public class ArrowsSelectionStep extends SelectionStep {
	protected final boolean skippable;

    public ArrowsSelectionStep(boolean skippable) {
        this.skippable = skippable;
    }
    
    /**
     * Creates an unskippable arrow selection step.
     */
    public ArrowsSelectionStep() {
        this(false);
    }

	
    @Override
    public SelectionRejectionExplanation init(SelectionSequence selection, int thisIndex) {
        return null;
    }

    @Override
    public boolean isFinished(SelectionSequence selection, int thisIndex) {
        return false;
    }

    @Override
    public boolean isSkippable(SelectionSequence selection, int thisIndex) {
        return selection.getAcceptedSelectionsCount(thisIndex) > 0;
    }

    @Override
    public String getInstruction(Locale locale, SelectionSequence selection, int thisIndex) {
        return "Select an arrow";
    }

    @Override
    public SelectionRejectionExplanation acceptSelection(RuleArg selection, SelectionSequence selectionSeq, int thisIndex) {
        if (selection instanceof ArrowArg) {
            return null;
        } else {
            return new I18NSelectionRejectionExplanation("SELSTEP_NOT_AN_ARROW");
        }
    }

    @Override
    public boolean cleanSelectionOnStart() {
        return true;
    }

    @Override
    public int getSelectableElements() {
        return SelectionStep.Arrows;
    }
	

}