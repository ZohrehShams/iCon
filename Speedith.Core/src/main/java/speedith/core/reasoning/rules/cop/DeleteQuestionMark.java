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
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.copArgs.SpiderComparatorArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectFeetOfSpiderSingleCurveInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowSingleSpiderComparatorInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.DeleteQuestionMarkTransformer;


/**
 * This rule operates on primary diagrams. When there is an arrow with cardinality <=1 with a spider source and a curve target, such that 
 * the target contains two spiders with unknown equality, we can establish that the two spiders are the same and delete the question mark.
 * @author Zohreh Shams
 */
public class DeleteQuestionMark extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable, ForwardRule<MultipleRuleArgs>{

	private static final long serialVersionUID = -1963839956170379105L;
	public static final String InferenceRuleName = "delete_question_mark";
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
			return Translations.i18n(locale, "DELETE_QUESTION_MARK_DESCRIPTION");
		}

		@Override
		public String getPrettyName(Locale locale) {
			return Translations.i18n(locale, "DELETE_QUESTION_MARK_PRETTY_NAME");
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
			return new SelectSingleArrowSingleSpiderComparatorInstruction();
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
	        SpiderComparatorArg spiderComparator = getSpiderComparatorArgFrom(ruleArgs);
	        return apply(arrow, spiderComparator, goals);
		}
		
	

	    private RuleApplicationResult apply(ArrowArg targetArrow, SpiderComparatorArg targetSpiderComparator, Goals goals) throws RuleApplicationException {
	        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
	        SpiderDiagram targetSubgoal = getSubgoal(targetArrow, goals);
	        newSubgoals[targetArrow.getSubgoalIndex()] = targetSubgoal.transform(new DeleteQuestionMarkTransformer(targetArrow, targetSpiderComparator,true));
	        return createRuleApplicationResult(newSubgoals);
	    }
	    
	    
	    
	    private ArrowArg getArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
	        return (ArrowArg) args.get(0);
	    }
	    
	    private SpiderComparatorArg getSpiderComparatorArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
	        return (SpiderComparatorArg) args.get(1);
	    }
	     
	    
	    
	    
	    
	    

}
