package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
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
import speedith.core.reasoning.rules.instructions.SelectContoursInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectArrowsInstruction;
import speedith.core.reasoning.rules.transformers.CopyContoursTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.CopyArrowsTransformer;


/**
 * This rule operates on a conjunction. It copies an arrow from one conjunct to the other one. 
 * @author Zohreh Shams
 */
public class CopyArrow extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable {

	private static final long serialVersionUID = 1385286212241735924L;
	public static final String InferenceRuleName = "copy_arrows";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram, DiagramType.LUCOPDiagram);
	 
	@Override
	public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(getArrowArgsFrom(args), goals);
	}
	        
	 
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
        return Translations.i18n(locale, "COPY_ARROWS_DESCRIPTION");
    }
    
    @Override
    public String getPrettyName(Locale locale) {
        return Translations.i18n(locale, "COPY_ARROWS_PRETTY_NAME");
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

    private ArrayList<ArrowArg> getArrowArgsFrom(RuleArg args) throws RuleApplicationException {
        MultipleRuleArgs multipleRuleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(multipleRuleArgs);
        if (multipleRuleArgs.size() != 1) {
            throw new RuleApplicationException("Not more than one arrow can be chosen.");
        }
        return ArrowArg.getArrowArgsFrom(multipleRuleArgs);
    }

    private RuleApplicationResult apply(ArrayList<ArrowArg> targetArrows, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        ArrowArg inferenceTarget = targetArrows.get(0);
        SpiderDiagram targetSubgoal = getSubgoal(inferenceTarget, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(inferenceTarget.getSubDiagramIndex());
        newSubgoals[inferenceTarget.getSubgoalIndex()] = targetSubgoal.transform(new CopyArrowsTransformer(indexOfParent, targetArrows));
        return createRuleApplicationResult(newSubgoals);
    }
	    

}
