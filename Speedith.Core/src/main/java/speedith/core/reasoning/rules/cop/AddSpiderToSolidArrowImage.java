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
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectArrowsInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.AddSpiderToSolidArrowImageTransformer;

/**
 * This rule operates on conjunctions. When there is a solid arrow in one conjunct with a spider source and curve target, and
 * there is an arrow in the other conjunct with the same label and source but to a spider target, the spider target from 
 * the latter can be copied to the target of the former arrow.  
 * @author Zohreh Shams
 */
public class AddSpiderToSolidArrowImage extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{
	
	private static final long serialVersionUID = 964529299619787893L;
	public static final String InferenceRuleName = "add_spider_to_solid_arrow_image";
	private static final Set<DiagramType> applicableTypes = 
			EnumSet.of(DiagramType.LUCOPDiagram,DiagramType.ConceptDiagram, DiagramType.SpiderDiagram);

	
	@Override
	public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
		return new SelectArrowsInstruction();
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
		return Translations.i18n(locale, "ADD_SPIDER_TO_SOLID_ARROW_IMAGE_DESCRIPTION");
	}
	
	
	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "ADD_SPIDER_TO_SOLID_ARROW_IMAGE_PRETTY_NAME");
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
        		AddSpiderToSolidArrowImageTransformer(indexOfParent, targetArrow, destinationArrow));
        return createRuleApplicationResult(newSubgoals);
    }
    

    

}
