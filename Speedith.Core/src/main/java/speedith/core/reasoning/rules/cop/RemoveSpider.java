package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.BasicInferenceRule;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.ZonesInOutArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectSpiderInstruction;
import speedith.core.reasoning.rules.transformers.TRAddUnCurveTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.RemoveSpiderTransformer;

/**
 * This rule operates on a primary diagram. It removes a spider that with a habitat consisting of exclusively 
 * non-shaded zones. 
 * @author Zohreh Shams
 */
public class RemoveSpider extends SimpleInferenceRule<SpiderArg> 
implements BasicInferenceRule<SpiderArg>, ForwardRule<SpiderArg>, Serializable{

	private static final long serialVersionUID = -2113909123929870124L;
	public static final String InferenceRuleName = "remove_spider";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram);	


	@Override
	public RuleApplicationInstruction<SpiderArg> getInstructions() {
		return new SelectSpiderInstruction();
	}
	
	
	@Override
	public InferenceRule<SpiderArg> getInferenceRule() {
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
	public Class<SpiderArg> getArgumentType() {
		return SpiderArg.class;
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
		return apply(args, goals,true);
	}
	
	
	@Override
	public RuleApplicationResult apply(final RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals, false);
	}

	
    private RuleApplicationResult apply(final RuleArg args, Goals goals, boolean applyForward) throws RuleApplicationException {
        SpiderArg arg = getTypedRuleArgs(args);
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        newSubgoals[arg.getSubgoalIndex()] = getSubgoal(arg, goals).transform(new RemoveSpiderTransformer(arg, applyForward));
        return createRuleApplicationResult(newSubgoals);
    }
	


}
