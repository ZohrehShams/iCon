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
import speedith.core.reasoning.rules.transformers.copTrans.ArrowCardinalityInconsTransformer;

/**
 * This rule operates on a conjunction. It establishes the contradiction when a certain domain in one conjunct is meant to have zero 
 * element from an image, but in the other conjunct there is at least one element from the domain that maps to an element of the image.  
 * @author Zohreh Shams
 */
public class ArrowCardinalityIncons extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{

	private static final long serialVersionUID = -8718147988595960013L;
	public static final String InferenceRuleName = "arrow_cardinality_inconsistency";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram, DiagramType.LUCOPDiagram);
	   
	 
	@Override
	public InferenceRule<MultipleRuleArgs> getInferenceRule() {
	    return this;
	}
	 
	 
	@Override
	public String getInferenceName() {
	    return InferenceRuleName;
	}
	
    @Override
    public String getDescription(Locale locale) {
        return Translations.i18n(locale, "ARROW_CARDINALITY_INCONSISTENCY_DESCRIPTION");
    }
    
    @Override
    public String getPrettyName(Locale locale) {
        return Translations.i18n(locale, "ARROW_CARDINALITY_INCONSISTENCY_PRETTY_NAME");
    }

    @Override
    public String getCategory(Locale locale) {
        return Translations.i18n(locale, "INF_RULE_CATEGORY_HETEROGENEOUS");
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
		return apply(args, goals);
	}
	
	      
	
	@Override
	public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        MultipleRuleArgs ruleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(ruleArgs);
        ArrowArg targetArrow = getTargetArrowArgFrom(ruleArgs);
        ArrowArg destinationArrow = getDestinationArrowArgFrom(ruleArgs);
        return apply(targetArrow, destinationArrow, goals);
	}
	
    private ArrowArg getTargetArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(0);
    }
    
    private ArrowArg getDestinationArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(1);
    }

    private RuleApplicationResult apply(ArrowArg targetArrow,  ArrowArg destinationArrow, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(targetArrow, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(targetArrow.getSubDiagramIndex());
        newSubgoals[targetArrow.getSubgoalIndex()] = targetSubgoal.transform(new 
        		ArrowCardinalityInconsTransformer(indexOfParent, targetArrow, destinationArrow));
        return createRuleApplicationResult(newSubgoals);
    }
	    

}
