package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
import speedith.core.reasoning.ApplyStyle;
import speedith.core.reasoning.ForwardRule;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRule;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.DeleteSyntaxInstruction;
import speedith.core.reasoning.rules.transformers.RemoveContoursTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.RemoveSpiderTransformer;

/**
 * This rule allows deleting multiple curves, spiders and arrows at the same time.
 * @author Zohreh Shams
 */
public class DeleteSyntax extends SimpleInferenceRule<MultipleRuleArgs> implements Serializable, ForwardRule<MultipleRuleArgs>{

	private static final long serialVersionUID = 4925667080412230547L;
	public static final String InferenceRuleName = "delete_syntax ";
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram,DiagramType.LUCOPDiagram);

	@Override
	public InferenceRule<MultipleRuleArgs> getInferenceRule() {
		return this;
	}
	
    @Override
    public String getInferenceName() {
        return InferenceRuleName;
    }

	@Override
	public String getCategory(Locale locale) {
		return Translations.i18n(locale, "INF_RULE_CATEGORY_HETEROGENEOUS");
	}

	@Override
	public String getDescription(Locale locale) {
		return Translations.i18n(locale, "DELETE_SYNTAX_DESCRIPTION");
	}

	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "DELETE_SYNTAX_PRETTY_NAME");
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
		return new DeleteSyntaxInstruction();
	}

	@Override
	public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals, ApplyStyle.Forward);
	}
	
	@Override
	public RuleApplicationResult apply(RuleArg args, Goals goals) throws RuleApplicationException {
		return apply(args, goals, ApplyStyle.GoalBased);
	}

    protected RuleApplicationResult apply(final RuleArg args, Goals goals, ApplyStyle applyStyle) throws RuleApplicationException {
    	ArrayList<ContourArg> contourArgs = getContourArgsFrom(getTypedRuleArgs(args));
    	ArrayList<SpiderArg> spiderArgs = getSpiderArgsFrom(getTypedRuleArgs(args));
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);

        //getSubgoal(contourArgs.get(0), goals).transform(new RemoveContoursTransformer(contourArgs, applyStyle));
      //newSubgoals[spiderArgs.get(0).getSubgoalIndex()] = 
		//getSubgoal(target.get, goals).transform(new RemoveSpiderTransformer(spiderArgs.get(0),true));
        
        newSubgoals[contourArgs.get(0).getSubgoalIndex()] = 
        	//	getSubgoal(contourArgs.get(0), goals).transform(new RemoveContoursTransformer(contourArgs, applyStyle));
        (getSubgoal(contourArgs.get(0), goals).transform(new RemoveContoursTransformer(contourArgs, applyStyle))).transform(new RemoveSpiderTransformer(spiderArgs.get(0),true));
//        newSubgoals[spiderArgs.get(0).getSubgoalIndex()] = 
//        		getSubgoal(spiderArgs.get(0), goals).transform(new RemoveSpiderTransformer(spiderArgs.get(0),true));
        return createRuleApplicationResult(newSubgoals);
    }
    
    
    public static ArrayList<ContourArg> getContourArgsFrom(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        ArrayList<ContourArg> contourArgs = new ArrayList<>();
        int subDiagramIndex = -1;
        int goalIndex = -1;
        for (RuleArg ruleArg : multipleRuleArgs) {
        	if(ruleArg instanceof ContourArg){
        		ContourArg contourArg = ContourArg.getContourArgFrom(ruleArg);
                subDiagramIndex = ContourArg.assertSameSubDiagramIndices(subDiagramIndex, contourArg);
                goalIndex = ContourArg.assertSameGoalIndices(goalIndex, contourArg);
                contourArgs.add(contourArg);
        	}
        }
        return contourArgs;
    }
    
    public static ArrayList<SpiderArg> getSpiderArgsFrom(MultipleRuleArgs multipleRuleArgs) throws RuleApplicationException {
        ArrayList<SpiderArg> spiderArgs = new ArrayList<>();
        int subDiagramIndex = -1;
        int goalIndex = -1;
        for (RuleArg ruleArg : multipleRuleArgs) {
        	if(ruleArg instanceof SpiderArg){
        		SpiderArg spiderArg = SpiderArg.getSpiderArgFrom(ruleArg);
                subDiagramIndex = SpiderArg.assertSameSubDiagramIndices(subDiagramIndex, spiderArg);
                goalIndex = SpiderArg.assertSameGoalIndices(goalIndex, spiderArg);
                spiderArgs .add(spiderArg);
        	}
        }
        return spiderArgs;
    }
}
