package speedith.core.reasoning.args.selection.copSelection;

import static speedith.core.i18n.Translations.i18n;

import java.util.Locale;

import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.SelectionStep.SelectionRejectionExplanation;

//Zohreh: selection of main syntactic elements: spiders, contours and arrows.
public class SelectionStepSyntax  extends SelectionStep {
	
	 public SelectionStepSyntax() {
	    }

	    @Override
	    public boolean isFinished(SelectionSequence selection, int thisIndex) {
	        return false;
	    }

	    @Override
	    public boolean isSkippable(SelectionSequence selection, int thisIndex) {
	        return true;
	    }

	    @Override
	    public String getInstruction(Locale locale, SelectionSequence selection, int thisIndex) {
	        return i18n(locale, "SELECTION_STEP_MSG_ANY");
	    }

	    @Override
	    public SelectionRejectionExplanation acceptSelection(RuleArg selection, SelectionSequence selectionSeq, int thisIndex) {
	        return null;
	    }

	    @Override
	    public boolean cleanSelectionOnStart() {
	        return false;
	    }

	    @Override
	    public SelectionRejectionExplanation init(SelectionSequence selection, int thisIndex) {
	        return null;
	    }

	    @Override
	    public int getSelectableElements() {
	        return SelectionStep.SpidersContoursArrows;
	    }

}
