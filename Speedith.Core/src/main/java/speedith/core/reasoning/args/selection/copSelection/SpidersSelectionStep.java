package speedith.core.reasoning.args.selection.copSelection;

import java.util.Locale;

import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.SelectionStep.I18NSelectionRejectionExplanation;
import speedith.core.reasoning.args.selection.SelectionStep.SelectionRejectionExplanation;


public class SpidersSelectionStep extends SelectionStep{
	
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
	        return "Select spiders";
	    }

	    @Override
	    public SelectionRejectionExplanation acceptSelection(RuleArg selection, SelectionSequence selectionSeq, int thisIndex) {
	        if (selection instanceof SpiderArg) {
	            return null;
	        } else {
	            return new I18NSelectionRejectionExplanation("SELSTEP_NOT_A_SPIDER");
	        }
	    }

	    @Override
	    public boolean cleanSelectionOnStart() {
	        return true;
	    }

	    @Override
	    public int getSelectableElements() {
	        return SelectionStep.Spiders;
	    }

	
}
