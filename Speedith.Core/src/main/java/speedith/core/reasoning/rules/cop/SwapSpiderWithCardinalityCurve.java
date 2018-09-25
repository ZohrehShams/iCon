package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowSingleCurveInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.SwapSpiderWithCardinalityCurveTransformer;


/**
 * This rule operates on unitary diagrams. When there is a dashed arrow with no cardinality and with a named spider source and target, such 
 * that the target is contained in a named curve, we can swap the spider with an unnamed curve inside the target and add >=1 cardinality to 
 * the arrow from the same source to the unnamed curve.
 * @author Zohreh Shams
 */
public class SwapSpiderWithCardinalityCurve extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable, ForwardRule<MultipleRuleArgs>{

	private static final long serialVersionUID = -1963839956170379105L;
	public static final String InferenceRuleName = "swap_spider_with_arrow_cardinality_curve";
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
			return null;
		}

		@Override
		public String getDescription(Locale locale) {
			return Translations.i18n(locale, "SWAP_SPIDER_WITH_ARROW_CARDINALITY_CURVE_DESCRIPTION");
		}

		@Override
		public String getPrettyName(Locale locale) {
			return Translations.i18n(locale, "SWAP_SPIDER_WITH_ARROW_CARDINALITY_CURVE_PRETTY_NAME");
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
			return new SelectSingleArrowSingleCurveInstruction();
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
	        ContourArg curve = getContourArgFrom(ruleArgs);
	        return apply(arrow, curve, goals);
		}
		
	

	    private RuleApplicationResult apply(ArrowArg targetArrow, ContourArg targetCurve, Goals goals) throws RuleApplicationException {
	        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
	        SpiderDiagram targetSubgoal = getSubgoal(targetArrow, goals);
	        newSubgoals[targetArrow.getSubgoalIndex()] = targetSubgoal.transform(new SwapSpiderWithCardinalityCurveTransformer(targetArrow, targetCurve,true));
	        return createRuleApplicationResult(newSubgoals);
	    }
	    
	    
	    
	    private ArrowArg getArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
	        return (ArrowArg) args.get(0);
	    }
	    
	    private ContourArg getContourArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
	        return (ContourArg) args.get(1);
	    }
	     
	    
	    
	    
	    
	    

}
