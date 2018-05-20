package speedith.core.reasoning.rules.cop;

import static speedith.core.i18n.Translations.i18n;

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
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.ZonesInOutArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.TRAddUnCurveInstruction;
import speedith.core.reasoning.rules.transformers.TRAddUnCurveTransformer;
/**
 * This class implements a transformation function that adds an unlabelled curve to a diagram.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */
public class TRAddUnCurve extends SimpleInferenceRule<ZonesInOutArg>
implements BasicInferenceRule<ZonesInOutArg>, ForwardRule<ZonesInOutArg>{
	
    public static final String InferenceRuleName = "tr_add_unlabelled_curve";

    //Zohreh: In fact this should be DiagramType.LUCOPDiagram, but since TRs are not inference rules, I don't want them 
    //to appear in the GUI. What appears in the GUI is LUCOPDiagram, so I change this to COPDiagram to make it hidden from the GUI.
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram);
    
    @Override
    public TRAddUnCurve getInferenceRule() {
        return this;
    }
     

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    @Override
    public Class<ZonesInOutArg> getArgumentType() {
        return ZonesInOutArg.class;
    }

    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }

    
    //Zohreh: this could have returned the text directly instead of using locale:return "Introduces new contours into a diagram.";
    @Override
    public String getDescription(Locale locale) {
        return i18n(locale,"TR_ADD_UNCURVE_DESCRIPTION");
    }

    
    //Zohreh: this could have retunred return InferenceRuleName;
    @Override
    public String getPrettyName(Locale locale) {
        return i18n(locale,"TR_ADD_UNCURVE_PRETTY_NAME");
    }
    
    @Override
    public Set<DiagramType> getApplicableTypes() {
        return applicableTypes;
    }
    

    @Override
    public RuleApplicationInstruction<ZonesInOutArg> getInstructions() {
    	return TRAddUnCurveInstruction.getInstance();
    }
    
    
    @Override
    public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(args, goals,true);
    }
    
    
    private RuleApplicationResult apply(final RuleArg args, Goals goals, boolean applyForward) throws RuleApplicationException {
        ZonesInOutArg arg = getTypedRuleArgs(args);
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        newSubgoals[arg.getSubgoalIndex()] = getSubgoal(arg, goals).transform(new TRAddUnCurveTransformer(arg, applyForward));
       // System.out.println(">>>>>>"+ arg.getSubgoalIndex());
        return createRuleApplicationResult(newSubgoals);
    }
    
 

    
    @Override
    public RuleApplicationResult apply(final RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(args, goals, false);
    }


}
