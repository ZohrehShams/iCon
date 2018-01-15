package speedith.core.reasoning.rules.cop;

import static speedith.core.i18n.Translations.i18n;

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
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectContoursInstruction;
import speedith.core.reasoning.rules.transformers.TRAddCurveLabelTransformer;
/**
 * This class implements a transformation function that adds a label to an unlabelled curve.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */
public class TRAddCurveLabel extends SimpleInferenceRule<MultipleRuleArgs>
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>{
	
    public static final String InferenceRuleName = "tr_add_curve_label";

    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCOPDiagram);
    
    @Override
    public TRAddCurveLabel getInferenceRule() {
        return this;
    }
     

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    @Override
    public Class<MultipleRuleArgs>  getArgumentType() {
        return MultipleRuleArgs.class;
    }
        

    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }

  
    @Override
    public String getDescription(Locale locale) {
    	return i18n(locale,"TR_ADD_CURVE_LABEL_DESCRIPTION");
    }

    
    @Override
    public String getPrettyName(Locale locale) {
    	return i18n(locale,"TR_ADD_CURVE_LABEL_PRETTY_NAME");
    }
    
    @Override
    public Set<DiagramType> getApplicableTypes() {
        return applicableTypes;
    }
    

    
    @Override
    public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
        return new SelectContoursInstruction();
    }
    
    
    @Override
    public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(args, goals,true);
    }
    
    
    private RuleApplicationResult apply(final RuleArg args, Goals goals, boolean applyForward) throws RuleApplicationException {
    	ArrayList<ContourArg> contourArgs = ContourArg.getContourArgsFrom(getTypedRuleArgs(args));
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        //System.out.println(">>>>>>"+ carg.getSubgoalIndex());
        newSubgoals[contourArgs.get(0).getSubgoalIndex()] = getSubgoal(contourArgs.get(0), goals).transform(new TRAddCurveLabelTransformer(contourArgs, applyForward));
        return createRuleApplicationResult(newSubgoals);
    }
    
 

    
    @Override
    public RuleApplicationResult apply(final RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(args, goals, false);
    }


}
