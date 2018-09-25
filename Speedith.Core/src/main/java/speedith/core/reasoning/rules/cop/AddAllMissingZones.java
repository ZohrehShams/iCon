package speedith.core.reasoning.rules.cop;

import static speedith.core.i18n.Translations.i18n;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.BasicInferenceRule;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectSingleSubDiagramInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.AddAllMissingZonesTransformer;


/**
 * This rule operates on a unitary diagram. It adds all the missing zones of the diagrams to the diagram as shaded zones. 
 * It in fact venifies a unitary diagram. 
 * @author Zohreh Shams
 */
public class AddAllMissingZones extends SimpleInferenceRule<SubDiagramIndexArg>
implements BasicInferenceRule<SubDiagramIndexArg>, ForwardRule<SubDiagramIndexArg>, Serializable{
	
	public static final String InferenceRuleName = "Add All Missing Zones";
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.LUCarCOPDiagram,DiagramType.LUCOPDiagram);


    @Override
    public InferenceRule<SubDiagramIndexArg> getInferenceRule() {
        return this;
    }

    @Override
    public String getCategory(Locale locale) {
        return null;
    }

    @Override
    public Class<SubDiagramIndexArg> getArgumentType() {
        return SubDiagramIndexArg.class;
    }

    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }
 
   
    @Override
    public String getDescription(Locale locale) {
        return i18n(locale,"ADD_ALL_MISSING_ZONES_DESCRIPTION");
    }
    

    @Override
    public String getPrettyName(Locale locale) {
        return i18n(locale,"ADD_ALL_MISSING_ZONES_PRETTY_NAME");
    }
    
    

    @Override
    public RuleApplicationInstruction<SubDiagramIndexArg> getInstructions() {
        return new SelectSingleSubDiagramInstruction();
    }

    @Override
    public Set<DiagramType> getApplicableTypes() {
        return applicableTypes;
    }
    
    
    @Override
    public RuleApplicationResult applyForwards(RuleArg arg, Goals goals) throws RuleApplicationException {
        return apply(arg, goals);
    }

    @Override
    public RuleApplicationResult apply(RuleArg arg, Goals goals) throws RuleApplicationException {
        RuleArg ruleArg = (SubDiagramIndexArg) arg;
        SubDiagramIndexArg target = (SubDiagramIndexArg) ruleArg;
        return apply(target, goals );
    }

    private RuleApplicationResult apply(SubDiagramIndexArg target, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(target, goals);
        newSubgoals[target.getSubgoalIndex()] = targetSubgoal.transform(new AddAllMissingZonesTransformer(target));
        return createRuleApplicationResult(newSubgoals);
    }


}
