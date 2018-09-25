package speedith.core.reasoning.args.selection.copSelection;

import static speedith.core.i18n.Translations.i18n;
import java.util.List;
import java.util.Locale;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;

public class SelectSingleArrowStep extends SelectionStep{
	
	 private final boolean skippable;

    public SelectSingleArrowStep(boolean skippable) {
        this.skippable = skippable;
    }


    public SelectSingleArrowStep() {
        this(false);
    }

    @Override
    public boolean isFinished(SelectionSequence selection, int thisIndex) {
        return isFinished(selection.getAcceptedSelectionsForStepAt(thisIndex)); 
    }

    private static boolean isFinished(List<RuleArg> sels) {
        return sels != null
                && sels.size() == 1
                && sels.get(0) instanceof ArrowArg; 
    }

    public static boolean isSelectionValid(List<RuleArg> sels) {
        return sels == null || sels.isEmpty() || isFinished(sels);
    }

    @Override
    public boolean isSkippable(SelectionSequence selection, int thisIndex) {
        return skippable || isFinished(selection, thisIndex);
    }

    @Override
    public String getInstruction(Locale locale, SelectionSequence selection, int thisIndex) {
        return i18n(locale, "SELSTEP_SINGLE_ARROW");
    }

    @Override
    public SelectionRejectionExplanation acceptSelection(RuleArg selection, SelectionSequence selectionSeq, int thisIndex) {
        if (selection instanceof ArrowArg) {
            if (selectionSeq.getAcceptedSelectionsCount(thisIndex) >= 1) {
                return new I18NSelectionRejectionExplanation("SELSTEP_JUST_ONE_ARROW");
            } else {
                return null;
            }
        } else {
            return new I18NSelectionRejectionExplanation("SELSTEP_NOT_AN_ARROW");
        }
    }

    @Override
    public boolean cleanSelectionOnStart() {
        return false;
    }

    @Override
    public int getSelectableElements() {
        return SelectionStep.Arrows;
    }

    @Override
    public SelectionRejectionExplanation init(SelectionSequence selection, int thisIndex) {
        return isSelectionValid(selection.getAcceptedSelectionsForStepAt(thisIndex))
                ? null
                : new I18NSelectionRejectionExplanation("SELSTEP_SELECTION_INVALID_NOT_AN_ARROW");
    }

}
