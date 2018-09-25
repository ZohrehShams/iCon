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
import speedith.core.reasoning.BasicInferenceRule;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectSpidersInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.RemoveSpidersTransformer;

/**
 * This rule operates on a primary diagram. It removes a spider that with a habitat consisting of exclusively 
 * non-shaded zones. 
 * @author Zohreh Shams
 */
public class RemoveSpider extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{

	private static final long serialVersionUID = -2113909123929870124L;
	public static final String InferenceRuleName = "remove_spider";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram);	


	@Override
	public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
		return new SelectSpidersInstruction();
	}
	
	
	@Override
	public InferenceRule<MultipleRuleArgs> getInferenceRule() {
		return this;
	}
	
	
	@Override
	public String getCategory(Locale locale) {
		return null;
	}

	@Override
	public String getDescription(Locale locale) {
		return Translations.i18n(locale, "REMOVE_SPIDER_DESCRIPTION");
	}

	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "REMOVE_SPIDER_PRETTY_NAME");
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
	public String getInferenceName() {
		return InferenceRuleName;
	}
	
	

	@Override
	public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals,ApplyStyle.Forward);
	}
	
	
	@Override
	public RuleApplicationResult apply(final RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals, ApplyStyle.Forward);
	}

	
	
    protected RuleApplicationResult apply(final RuleArg args, Goals goals, ApplyStyle applyStyle) throws RuleApplicationException {
        ArrayList<SpiderArg> spiderArgs = SpiderArg.getSpiderArgsFrom(getTypedRuleArgs(args));
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        newSubgoals[spiderArgs.get(0).getSubgoalIndex()] = getSubgoal(spiderArgs.get(0), goals).transform(new RemoveSpidersTransformer(spiderArgs, applyStyle));
        return createRuleApplicationResult(newSubgoals);
    }

}
