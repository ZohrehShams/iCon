package speedith.core.reasoning.rules.cop;

import static speedith.core.i18n.Translations.i18n;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.BasicInferenceRule;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectArrowsInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.PropagateTargetShadingTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.TRAddCardinalityTransformer;

/**
 * This class implements a transformation function that adds cardinality to an existing arrow of a diagram.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */
public class TRAddCardinality extends SimpleInferenceRule<MultipleRuleArgs> 
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>, Serializable{
	
	public static final String InferenceRuleName = "add_cardinality";

	//Zohreh: this is not applicable to LUCOP but because I want all these rules to appear in "My Rule" bar in the gui, 
	//I have to add it here.
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCarCOPDiagram,DiagramType.LUCOPDiagram);
    
    @Override
    public TRAddCardinality getInferenceRule() {
        return this;
    }
     

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    
    @Override
    public Class<MultipleRuleArgs> getArgumentType() {
        return MultipleRuleArgs.class;
    }


    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }

    
    @Override
    public String getDescription(Locale locale) {
        return i18n(locale,"TR_ADD_CARDINALITY_DESCRIPTION");
    }
    

    @Override
    public String getPrettyName(Locale locale) {
        return i18n(locale,"TR_ADD_CARDINALITY_PRETTY_NAME");
    }
    
    @Override
    public Set<DiagramType> getApplicableTypes() {
        return applicableTypes;
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
        newSubgoals[inferenceTarget.getSubgoalIndex()] = targetSubgoal.transform(new TRAddCardinalityTransformer(inferenceTarget, applyForward));
        return createRuleApplicationResult(newSubgoals);
    }

    
    @Override
    public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
        return new SelectArrowsInstruction();
    }
    
    
    public static void assertArgumentsNumber(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        if (multipleRuleArgs.size() != 1) {
            throw new RuleApplicationException("Not more than one arrow can be chosen.");
        }
    }
    

}
