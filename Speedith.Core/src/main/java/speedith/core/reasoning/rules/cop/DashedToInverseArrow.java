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
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.DashedToInverseArrowTransformer;

/**
 * This rule operates on unitary diagrams, when there is a dashed arrow between two named spiders. We can delete the dashed arrow, add
 * an unnamed curve that contains the source of the arrow, and add a solid arrow from the target of the dashed arrow to this curve, with 
 * a label inverse of the dashed arrow. 
 * @author Zohreh Shams
 */
public class DashedToInverseArrow extends SimpleInferenceRule<ArrowArg> 
implements BasicInferenceRule<ArrowArg>, ForwardRule<ArrowArg>, Serializable{

	private static final long serialVersionUID = 5136262184836167681L;
	public static final String InferenceRuleName = "dashed_to_inverse_arrow";
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
		return Translations.i18n(locale, "DASHED_TO_INVERSE_ARROW_DESCRIPTION");
	}
	
	
	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "DASHED_TO_INVERSE_ARROW_PRETTY_NAME");
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
        newSubgoals[targetArrow.getSubgoalIndex()] = 
        		targetSubgoal.transform(new DashedToInverseArrowTransformer(targetArrow, true));
        return createRuleApplicationResult(newSubgoals);
    }


}
