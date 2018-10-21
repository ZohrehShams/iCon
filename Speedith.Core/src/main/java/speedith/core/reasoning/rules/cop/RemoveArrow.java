package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectArrowsInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.RemoveArrowsTransformer;

/**
 * Removes an arrow from a diagram.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */

public class RemoveArrow extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable, ForwardRule<MultipleRuleArgs>{
	
	private static final long serialVersionUID = 4902246444437604183L;
	public static final String InferenceRuleName = "remove_arrows";
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram,DiagramType.LUCOPDiagram);

	@Override
	public InferenceRule<MultipleRuleArgs> getInferenceRule() {
		return this;
	}
	
    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }

	@Override
	public String getCategory(Locale locale) {
		return Translations.i18n(locale, "INF_RULE_CATEGORY_HETEROGENEOUS");
	}

	@Override
	public String getDescription(Locale locale) {
		return Translations.i18n(locale, "REMOVE_ARROWS_DESCRIPTION");
	}

	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "REMOVE_ARROWS_PRETTY_NAME");
	}

	@Override
	public Class<MultipleRuleArgs> getArgumentType() {
		return MultipleRuleArgs.class;
	}

	@Override
	public Set<DiagramType> getApplicableTypes() {
		return applicableTypes;
	}
	
	@Override
	public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
		return new SelectArrowsInstruction(); 
	}
	
	@Override
	public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals, ApplyStyle.Forward);
	}
	
	@Override
	public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals, ApplyStyle.GoalBased);
	}

    protected RuleApplicationResult apply(final RuleArg args, Goals goals, ApplyStyle applyStyle) throws RuleApplicationException {
        ArrayList<ArrowArg> arrowArgs = ArrowArg.getArrowArgsFrom(getTypedRuleArgs(args));
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        newSubgoals[arrowArgs.get(0).getSubgoalIndex()] = 
        		getSubgoal(arrowArgs.get(0), goals).transform(new RemoveArrowsTransformer(arrowArgs, applyStyle));
        return createRuleApplicationResult(newSubgoals);
    }

}
