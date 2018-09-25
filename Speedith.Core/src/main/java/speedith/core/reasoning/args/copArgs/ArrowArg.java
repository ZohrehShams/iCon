package speedith.core.reasoning.args.copArgs;

import speedith.core.lang.Arrow;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the arrow that should be passed to an inference rule.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */

public class ArrowArg extends SubDiagramIndexArg{
	private final Arrow arrow;
	
	 /**
     * Initialises an argument object for a spider-diagrammatic inference rule.
     *
     * @param subgoalIndex the index of the subgoal to target by the rule.
     * @param primarySDIndex the index of the sub-diagram to target by the rule.
     * @param arrow the arrow that should be passed to an
     * inference rule. This parameter must not be
     * {@code null} or empty, otherwise an exception will be thrown.
     */
    public ArrowArg(int subgoalIndex, int primarySDIndex, Arrow arrow) {
        super(subgoalIndex, primarySDIndex);
        if (arrow == null) {
            throw new IllegalArgumentException(speedith.core.i18n.Translations.i18n("GERR_EMPTY_ARGUMENT", "arrow"));
        }
        this.arrow = arrow;
    }

    /**
     * The arrow that should be passed to an inference rule.
     * <p>This property is guaranteed to return a non-null value.</p>
     *
     * @return the arrow that should be passed to an inference rule.
     */
    public Arrow getArrow() {
        return arrow;
    }
    
    
    /**If we choose multiple arrows they all must be from the same diagrams.*/
    public static int assertSameSubDiagramIndices(int previousSubDiagramIndex, ArrowArg arrowArg) throws RuleApplicationException {
        if (previousSubDiagramIndex != -1 && previousSubDiagramIndex != arrowArg.getSubDiagramIndex()) {
            throw new RuleApplicationException("The arrows must be from the same unitary spider diagram.");
        } else {
            previousSubDiagramIndex = arrowArg.getSubDiagramIndex();
        }
        return previousSubDiagramIndex;
    }
    
    
    public static ArrowArg getArrowArgFrom(RuleArg ruleArg) throws RuleApplicationException {
        if (!(ruleArg instanceof ArrowArg)) {
            throw new RuleApplicationException("The copy arrows rule takes only arrows as arguments.");
        }
        return (ArrowArg)ruleArg;
    }
    
    
    public static ArrayList<ArrowArg> getArrowArgsFrom(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        ArrayList<ArrowArg> arrowArgs = new ArrayList<>();
        int subDiagramIndex = -1;
        int goalIndex = -1;
        for (RuleArg ruleArg : multipleRuleArgs) {
            ArrowArg arrowArg = getArrowArgFrom(ruleArg);
            subDiagramIndex = assertSameSubDiagramIndices(subDiagramIndex, arrowArg);
            goalIndex = assertSameGoalIndices(goalIndex, arrowArg);
            arrowArgs.add(arrowArg);
        }
        return arrowArgs;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrowArg arrowArg = (ArrowArg) o;

        if (getSubDiagramIndex() != arrowArg.getSubDiagramIndex()
                || getSubgoalIndex() != arrowArg.getSubgoalIndex()) {
            return false;
        }
        return getArrow() != null ? getArrow().equals(arrowArg.getArrow()) : arrowArg.getArrow() == null;
    }

    @Override
    public int hashCode() {
        return getArrow() != null ? getArrow().hashCode() + getSubDiagramIndex() +getSubgoalIndex() : 0;
    }
}

