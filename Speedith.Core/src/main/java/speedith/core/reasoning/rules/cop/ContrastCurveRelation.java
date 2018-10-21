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
import speedith.core.reasoning.rules.transformers.CopyContoursTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.ContrastCurveRelationTransformer;

/**
 * This rule operates on diagrams that are in a conjunction. Two contours that are disjoint are chosen from the same diagram.
 * If these contours are not disjoint in the other diagram (one subsumes the other or they both subsume each other), then we
 * are able to establish one or both of them are empty.
 * @author Zohreh Shams
 */
public class ContrastCurveRelation extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{
	

	private static final long serialVersionUID = 4409183785000240842L;
	public static final String InferenceRuleName = "contrast_curve_relation";

    //private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.EulerDiagram, DiagramType.SpiderDiagram);
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
        return Translations.i18n(locale, "CONTRAST_CURVE_RELATION_DESCRIPTION");
    }

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    @Override
    public String getPrettyName(Locale locale) {
        return Translations.i18n(locale, "CONTRAST_CURVE_RELATION_PRETTY_NAME");
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
        newSubgoals[inferenceTarget.getSubgoalIndex()] = targetSubgoal.transform(new ContrastCurveRelationTransformer(indexOfParent, targetContours));
        return createRuleApplicationResult(newSubgoals);
    }

    public static void assertArgumentsNumber(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        if (multipleRuleArgs.size() != 2) {
            throw new RuleApplicationException("Not more than two curves can be chosen.");
        }
    }
}
