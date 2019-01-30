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
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowSingleSpiderInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.InverseToDashedArrowTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.ReverseArrowDirectionTransformer;

/**
 * This rule operates on unitary diagrams. When there is a dashed arrow  between two named spiders, the direction of the arrow can be reversed whilst 
 * inversing the label of the arrow.
 * @author Zohreh Shams
 */
public class ReverseArrowDirection extends SimpleInferenceRule<ArrowArg> 
implements BasicInferenceRule<ArrowArg>, ForwardRule<ArrowArg>, Serializable{

	private static final long serialVersionUID = -5912949186889241027L;
	public static final String InferenceRuleName = "reverse_arrow_direction";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram,DiagramType.ConceptDiagram);

	@Override
	public RuleApplicationInstruction<ArrowArg> getInstructions() {
		return new SelectSingleArrowInstruction();
	}
	

	@Override
	public InferenceRule<ArrowArg> getInferenceRule() {
		return this;
	}
	
	
	@Override
	public String getCategory(Locale locale) {
		return null;
	}
	
	
	@Override
	public String getDescription(Locale locale) {
		return Translations.i18n(locale, "REVERSE_ARROW_DIRECTION_DESCRIPTION");
	}
	
	
	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "REVERSE_ARROW_DIRECTION_PRETTY_NAME");
	}
	
	@Override
	public Class<ArrowArg> getArgumentType() {
		return ArrowArg.class;
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
	public RuleApplicationResult apply(RuleArg arg, Goals goals) throws RuleApplicationException {
        ArrowArg arrow = (ArrowArg)arg;
        return apply(arrow, goals);
	}
	
    

    private RuleApplicationResult apply(ArrowArg targetArrow, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(targetArrow, goals);
        newSubgoals[targetArrow.getSubgoalIndex()] = targetSubgoal.transform(new ReverseArrowDirectionTransformer(targetArrow, true));
        return createRuleApplicationResult(newSubgoals);
    }


}
