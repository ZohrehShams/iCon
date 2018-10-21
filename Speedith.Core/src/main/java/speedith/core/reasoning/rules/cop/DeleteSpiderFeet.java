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
import speedith.core.reasoning.args.SpiderRegionArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectFeetOfSpiderSingleCurveInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.DeleteSpiderFeetTransformer;



/**
 * This rule operates on diagrams in conjunction. When there is a named spider in one conjunct with feet inside and outside a named curve C, while 
 * the same spider in the other conjunct has all its feet inside C, we can delete the spider feet outside C in the former diagram.
 * @author Zohreh Shams
 */
public class DeleteSpiderFeet extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable, ForwardRule<MultipleRuleArgs>{

	private static final long serialVersionUID = 1897000087629281426L;
	public static final String InferenceRuleName = "delete_spider_feet";
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
			return Translations.i18n(locale, "DELETE_SPIDER_FEET_DESCRIPTION");
		}

		@Override
		public String getPrettyName(Locale locale) {
			return Translations.i18n(locale, "DELETE_SPIDER_FEET_PRETTY_NAME");
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
			return new SelectFeetOfSpiderSingleCurveInstruction();
		}
		
	    @Override
		public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
			return apply(args, goals);
		}
		
		
		@Override
		public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
	        MultipleRuleArgs ruleArgs = getTypedRuleArgs(args);
	        MultipleRuleArgs.assertArgumentsNotEmpty(ruleArgs);
	        SpiderRegionArg spiderFeet = (SpiderRegionArg) ruleArgs.get(0);
	        ContourArg curve = (ContourArg) ruleArgs.get(1);
	        return apply(spiderFeet, curve, goals);
		}
		
	

	    private RuleApplicationResult apply(SpiderRegionArg spiderFeet, ContourArg curve, Goals goals) throws RuleApplicationException {
	        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
	        SpiderDiagram targetSubgoal = getSubgoal(spiderFeet, goals);
	        int indexOfParent = targetSubgoal.getParentIndexOf(spiderFeet.getSubDiagramIndex());
	        newSubgoals[spiderFeet.getSubgoalIndex()] = targetSubgoal.transform(new 
	        		DeleteSpiderFeetTransformer(indexOfParent,spiderFeet, curve));
	        return createRuleApplicationResult(newSubgoals);
	    }
	    

}
