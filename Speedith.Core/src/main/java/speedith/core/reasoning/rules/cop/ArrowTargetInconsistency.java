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
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectArrowsInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.ArrowTargetInconsistencyShadingTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.ArrowTargetInconsistencyTransformer;

/**
 * This rule operates on conjunctions. It establishes the contradiction, when there is a solid arrow in one conjunct with a spider source and a curve target, and  
 * there is an arrow in the other conjunct with the same label and source but to a spider target, such that the target spider is present in the first conjunct as 
 * well but it is outside the target of the solid arrow.
 * @author Zohreh Shams
 */
public class ArrowTargetInconsistency extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{
	
	private static final long serialVersionUID = 3827795297103192595L;
	public static final String InferenceRuleName = "arrow_target_inconsistency";
	private static final Set<DiagramType> applicableTypes = 
			EnumSet.of(DiagramType.LUCOPDiagram,DiagramType.ConceptDiagram, DiagramType.SpiderDiagram);


	
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
		return Translations.i18n(locale, "ARROW_TARGET_INCONSISTENCY_DESCRIPTION");
	}
	
	
	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "ARROW_TARGET_INCONSISTENCY_PRETTY_NAME");
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
	public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
		return new SelectArrowsInstruction();
	}
	
	@Override
	public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals);
	}
	
	
	@Override
	public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        MultipleRuleArgs ruleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(ruleArgs);
        ArrowArg firstArrow = getFirstArrowArgFrom(ruleArgs);
        ArrowArg secondArrow = getSecondArrowArgFrom(ruleArgs);
        return apply(firstArrow, secondArrow, goals);
	}
	
    private ArrowArg getFirstArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(0);
    }
    
    private ArrowArg getSecondArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(1);
    }

    private RuleApplicationResult apply(ArrowArg firstArrow,  ArrowArg secondArrow, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(firstArrow, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(firstArrow.getSubDiagramIndex());
        newSubgoals[firstArrow.getSubgoalIndex()] = targetSubgoal.transform(new 
        		ArrowTargetInconsistencyTransformer(indexOfParent, firstArrow, secondArrow));
        return createRuleApplicationResult(newSubgoals);
    }
    

    

}
