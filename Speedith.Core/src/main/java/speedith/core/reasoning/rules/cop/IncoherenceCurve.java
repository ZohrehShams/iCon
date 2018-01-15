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
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectContoursInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.ContrastCurveRelationTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.IncoherenceCurveTransformer;

/**
 * This rule operates on diagrams that are in a conjunction. A curve that is not entirely shaded, say c, and is not the habitat of
 * any spider is chosen from a diagram (say d1). If the set of zones that are not shaded in c in d1, are shaded in the
 * other diagram, say d2, then we are able to establish c is entirely shaded. Once this is established all other curves 
 * are erased from d1 resulting in d3 that contains only c which is entirely shaded.
 * @author Zohreh Shams
 */
public class IncoherenceCurve extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{
	
	public static final String InferenceRuleName = "incoherence_curve";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram);
	
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
        return Translations.i18n(locale, "INCOHERENCE_CURVE_DESCRIPTION");
    }

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    @Override
    public String getPrettyName(Locale locale) {
        return Translations.i18n(locale, "INCOHERENCE_CURVE_PRETTY_NAME");
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
        return new SelectContoursInstruction();
    }
    
    private ArrayList<ContourArg> getContourArgsFrom(RuleArg args) throws RuleApplicationException {
        MultipleRuleArgs multipleRuleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(multipleRuleArgs);
        assertArgumentsNumber(multipleRuleArgs);
        return ContourArg.getContourArgsFrom(multipleRuleArgs);
    }

    
    @Override
    public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(getContourArgsFrom(args), goals);
    }
    
    
    @Override
    public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
    return apply(args, goals);
        }
    
 
    private RuleApplicationResult apply(ArrayList<ContourArg> targetContours, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        ContourArg inferenceTarget = targetContours.get(0);
        SpiderDiagram targetSubgoal = getSubgoal(inferenceTarget, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(inferenceTarget.getSubDiagramIndex());
        newSubgoals[inferenceTarget.getSubgoalIndex()] = targetSubgoal.transform(new IncoherenceCurveTransformer(indexOfParent, targetContours));
        return createRuleApplicationResult(newSubgoals);
    }

    public static void assertArgumentsNumber(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        if (multipleRuleArgs.size() != 1) {
            throw new RuleApplicationException("Only a single curve can be chosen.");
        }
    }

}
