package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.ArrayList;
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
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectArrowsInstruction;
import speedith.core.reasoning.rules.instructions.SelectContoursInstruction;
import speedith.core.reasoning.rules.transformers.TRAddArrowTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.ContrastCurveRelationTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.PropagateTargetShadingTransformer;

/**
 * This rule operates on a unitary diagram. An arrow (solid or dashed) is chosen such that both source and target of the arrow are curves
 * and the target is fully shaded and the source does not contain any spider. If these conditions hold we can establish that the source 
 * is empty too.
 * @author Zohreh Shams
 */
public class PropagateTargetShading extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{

	public static final String InferenceRuleName = "propagate_target_shading";

	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram,DiagramType.LUCarCOPDiagram);
    

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
        return Translations.i18n(locale, "PROPAGATE_TARGET_SHADING_DESCRIPTION");
    }

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    @Override
    public String getPrettyName(Locale locale) {
        return Translations.i18n(locale, "PROPAGATE_TARGET_SHADING_PRETTY_NAME");
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
    
    
    private ArrayList<ArrowArg> getArrowArgsFrom(RuleArg args) throws RuleApplicationException {
        MultipleRuleArgs multipleRuleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(multipleRuleArgs);
        assertArgumentsNumber(multipleRuleArgs);
        return ArrowArg.getArrowArgsFrom(multipleRuleArgs);
    }
    
    
    
    
    @Override
    public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(getArrowArgsFrom(args), goals,true);
    }
    
    
    @Override
    public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
    return apply(args, goals);
        }
    
 
    private RuleApplicationResult apply(ArrayList<ArrowArg> targetArrows, Goals goals, boolean applyForward) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        ArrowArg inferenceTarget = targetArrows.get(0);
        SpiderDiagram targetSubgoal = getSubgoal(inferenceTarget, goals);
        newSubgoals[inferenceTarget.getSubgoalIndex()] = targetSubgoal.transform(new PropagateTargetShadingTransformer(inferenceTarget, applyForward));
        return createRuleApplicationResult(newSubgoals);
    }

    

    public static void assertArgumentsNumber(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        if (multipleRuleArgs.size() != 1) {
            throw new RuleApplicationException("Not more than one arrow can be chosen.");
        }
    }
}
