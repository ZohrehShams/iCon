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
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectZonesInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.TRAddSpiderTransformer;
/**
 * This class implements a transformation function that adds a labelled spider to a diagram.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 */
public class TRAddSpider extends SimpleInferenceRule<MultipleRuleArgs>
implements BasicInferenceRule<MultipleRuleArgs>, ForwardRule<MultipleRuleArgs>{
	
    public static final String InferenceRuleName = "tr_add_spider";

  //Zohreh: In fact this should be DiagramType.LUCOPDiagram, but since TRs are not inference rules, I don't want them 
    //to appear in the GUI. What appears in the GUI is LUCOPDiagram, so I change this to COPDiagram to make it hidden from the GUI.
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram);
    
    @Override
    public TRAddSpider getInferenceRule() {
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

    
    //Zohreh: this could have returned the text directly instead of using locale:return "Introduces new contours into a diagram.";
    @Override
    public String getDescription(Locale locale) {
        return i18n(locale,"TR_ADD_SPIDER_DESCRIPTION");
    }

    
    //Zohreh: this could have retunred return InferenceRuleName;
    @Override
    public String getPrettyName(Locale locale) {
        return i18n(locale,"TR_ADD_SPIDER_PRETTY_NAME");
    }
    
    @Override
    public Set<DiagramType> getApplicableTypes() {
        return applicableTypes;
    }
    

    @Override
    public RuleApplicationInstruction<MultipleRuleArgs> getInstructions() {
    	return new SelectZonesInstruction();
    }
    
    
    @Override
    public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
        return apply(args, goals);
    }
    
    
    @Override
    public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
        MultipleRuleArgs ruleArgs = getTypedRuleArgs(args);
        MultipleRuleArgs.assertArgumentsNotEmpty(ruleArgs);
        ArrayList<ZoneArg> zones = ZoneArg.getZoneArgsFrom(ruleArgs);
        SubDiagramIndexArg target = getTargetDiagramArg(ruleArgs);
        return apply(target, zones, goals );
    }

    private RuleApplicationResult apply(SubDiagramIndexArg target, ArrayList<ZoneArg> targetZones, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(target, goals);
        newSubgoals[target.getSubgoalIndex()] = targetSubgoal.transform(new TRAddSpiderTransformer(target, targetZones));
        return createRuleApplicationResult(newSubgoals);
    }
    
 
    
    private SubDiagramIndexArg getTargetDiagramArg(MultipleRuleArgs args) throws RuleApplicationException {
        return (SubDiagramIndexArg) args.get(0);
    }

    

}
