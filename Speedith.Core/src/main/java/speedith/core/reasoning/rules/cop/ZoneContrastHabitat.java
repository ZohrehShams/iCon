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
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectSingleZoneInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.IncoherenceCurveTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.ZoneContrastHabitatTransformer;

/**
 * This rule operates on compound (conjunction) diagram. It establishes the contradiction when an entirely 
 * shaded zone selected by the user in one diagram has few spiders than its counterpart in another diagram. 
 * @author Zohreh Shams
 */
public class ZoneContrastHabitat extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable {

	private static final long serialVersionUID = -3969226061883681810L;
	public static final String InferenceRuleName = "zone_contrast_habitat";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram);	
	
    @Override
    public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
        return new SelectSingleZoneInstruction();
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
		return Translations.i18n(locale, "ZONE_CONTRAST_HABITAT_DESCRIPTION");
	}

	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "ZONE_CONTRAST_HABITAT_PRETTY_NAME");
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
		return apply(getZoneArgsFrom(args), goals);
	}
	
    private RuleApplicationResult apply(ArrayList<ZoneArg> targetZones, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        ZoneArg inferenceTarget = targetZones.get(0);
        SpiderDiagram targetSubgoal = getSubgoal(inferenceTarget, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(inferenceTarget.getSubDiagramIndex());
        newSubgoals[inferenceTarget.getSubgoalIndex()] = targetSubgoal.transform(new ZoneContrastHabitatTransformer(indexOfParent, targetZones));
        return createRuleApplicationResult(newSubgoals);
    }
    
    
    private ArrayList<ZoneArg> getZoneArgsFrom(RuleArg args) throws RuleApplicationException {
        MultipleRuleArgs multipleRuleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(multipleRuleArgs);
        assertArgumentsNumber(multipleRuleArgs);
        return ZoneArg.getZoneArgsFrom(multipleRuleArgs);
    }
    
    public static void assertArgumentsNumber(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        if (multipleRuleArgs.size() != 1) {
            throw new RuleApplicationException("Only a single zone can be chosen.");
        }
    }





}
