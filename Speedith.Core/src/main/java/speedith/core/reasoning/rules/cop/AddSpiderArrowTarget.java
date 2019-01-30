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
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowSingleSpiderInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.AddSpiderArrowTargetTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.InverseToDashedArrowTransformer;

/**
 * This rule operates on unitary diagrams. When there is an arrow sourced at a named spider (e.g., u) and targeted at an unnamed curve that contains 
 * a named spider (e.g., v), we can swap the arrow with a dashed arrow from u to v. 
 * @author Zohreh Shams
 */
public class AddSpiderArrowTarget extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{

	private static final long serialVersionUID = -5912949186889241027L;
	public static final String InferenceRuleName = "add_spider_arrow_target";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram,DiagramType.ConceptDiagram);

	@Override
	public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
		return new SelectSingleArrowSingleSpiderInstruction();
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
		return Translations.i18n(locale, "ADD_SPIDER_ARROW_TARGET_DESCRIPTION");
	}
	
	
	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "ADD_SPIDER_ARROW_TARGET_PRETTY_NAME");
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
		return apply(args, goals);
	}
	
	
	@Override
	public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        MultipleRuleArgs ruleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(ruleArgs);
        ArrowArg arrow = getArrowArgFrom(ruleArgs);
        SpiderArg spider = getSpiderArgFrom(ruleArgs);
        return apply(arrow, spider, goals);
	}
	
    private ArrowArg getArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(0);
    }
    
    private SpiderArg getSpiderArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (SpiderArg) args.get(1);
    }

    private RuleApplicationResult apply(ArrowArg targetArrow, SpiderArg targetSpider, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(targetArrow, goals);
        newSubgoals[targetArrow.getSubgoalIndex()] = targetSubgoal.transform(new AddSpiderArrowTargetTransformer(targetArrow, targetSpider,true));
        return createRuleApplicationResult(newSubgoals);
    }


}
