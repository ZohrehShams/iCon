package speedith.core.reasoning.args.selection.copSelection;

import static speedith.core.i18n.Translations.i18n;

import java.util.List;
import java.util.Locale;

import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.SpiderComparatorArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;

public class SelectSingleSpiderComparatorStep extends SelectionStep {

	
	public SelectSingleSpiderComparatorStep() {
    }

    public static SelectSingleSpiderComparatorStep getInstance() {
        return SingletonContainer.TheStep;
    }

    
    
    @Override
    public SelectionRejectionExplanation init(SelectionSequence selection, int thisIndex) {
        return null;
    }

    @Override
    public boolean isFinished(SelectionSequence selection, int thisIndex) {
        return isFinished(selection.getAcceptedSelectionsForStepAt(thisIndex));
    }

    private static boolean isFinished(List<RuleArg> sels) {
        return sels != null
                && sels.size() == 1 
                && sels.get(0) instanceof SpiderComparatorArg; 
    }

    @Override
    public boolean isSkippable(SelectionSequence selection, int thisIndex) {
        return selection.getAcceptedSelectionsCount(thisIndex) > 0;
    }

    @Override
    public String getInstruction(Locale locale, SelectionSequence selection, int thisIndex) {
        return i18n(locale, "SELSTEP_SELECT_SPIDER_COMPARATOR");
    }

    @Override
    public boolean cleanSelectionOnStart() {
        return true;
    }

    
    
    
    @Override
    public SelectionRejectionExplanation acceptSelection(RuleArg selection, SelectionSequence selectionSeq, int thisIndex) {
        if (selection instanceof SpiderComparatorArg) {
            return null;
        } else {
            return new I18NSelectionRejectionExplanation("SELSTEP_NOT_A_SPIDER_COMPARATOR");
        }
    }
    


    @Override
    public int getSelectableElements() {
        return SelectionStep.SpiderComparators;
    }



    private static final class SingletonContainer {
        private static final SelectSingleSpiderComparatorStep TheStep = new SelectSingleSpiderComparatorStep();
    }

    
}
