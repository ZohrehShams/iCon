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
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.AddSpiderToDashedArrowImageInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectArrowsInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.AddSpiderToDashedArrowImageTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.AddSpiderToDashedArrowImageTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.AddSpiderToSolidArrowImageTransformer;

/**
 * This rule operates on conjunctions. When there is a dashed arrow in one conjunct with a spider source and unnamed curve target 
 * that is inside a named curve (e.g.; c), and there is an arrow in the other conjunct with the same label and source but to a 
 * spider target that is contained in a curve named c, the spider target from the latter can be copied to the target of the former 
 * arrow.  
 * @author Zohreh Shams
 */
public class AddSpiderToDashedArrowImage extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{

	private static final long serialVersionUID = -8090522495105570322L;
	public static final String InferenceRuleName = "add_spider_to_dashed_arrow_image";
	private static final Set<DiagramType> applicableTypes = 
			EnumSet.of(DiagramType.LUCOPDiagram,DiagramType.ConceptDiagram, DiagramType.SpiderDiagram);

	
	@Override
	public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
		return new AddSpiderToDashedArrowImageInstruction();
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
		return Translations.i18n(locale, "ADD_SPIDER_TO_DASHED_ARROW_IMAGE_DESCRIPTION");
	}
	
	
	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "ADD_SPIDER_TO_DASHED_ARROW_IMAGE_PRETTY_NAME");
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
        ArrowArg firstArrow = getFirstArrowArgFrom(ruleArgs);
        ContourArg curve = getContourArgFrom(ruleArgs);
        ArrowArg secondArrow = getSecondArrowArgFrom(ruleArgs);
        return apply(firstArrow, curve, secondArrow, goals);
	}
	
    private ArrowArg getFirstArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(0);
    }
    
    
    private ContourArg getContourArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ContourArg) args.get(1);
    }
    
    private ArrowArg getSecondArrowArgFrom(MultipleRuleArgs args) throws RuleApplicationException {
        return (ArrowArg) args.get(2);
    }

    private RuleApplicationResult apply(ArrowArg firstArrow,  ContourArg curve, ArrowArg secondArrow, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(firstArrow, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(firstArrow.getSubDiagramIndex());
        newSubgoals[firstArrow.getSubgoalIndex()] = targetSubgoal.transform(new 
        		AddSpiderToDashedArrowImageTransformer(indexOfParent, firstArrow, curve, secondArrow));
        return createRuleApplicationResult(newSubgoals);
    }
    

    

}
