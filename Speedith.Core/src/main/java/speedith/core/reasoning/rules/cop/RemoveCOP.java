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
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.SelectSingleSubDiagramInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectArrowsInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectCOPsInstruction;
import speedith.core.reasoning.rules.transformers.ConjunctionEliminationTransformer;
import speedith.core.reasoning.rules.transformers.CopyContoursTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.RemoveArrowsTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.RemoveCOPTransformer;

/**
 * Removes a COP from a Concept Diagram.
 * @author Zohreh Shams [zs315@cam.ac.uk]
 * */

public class RemoveCOP extends SimpleInferenceRule<SubDiagramIndexArg> implements Serializable{
	
	private static final long serialVersionUID = 4902246444437604183L;
	public static final String InferenceRuleName = "remove_cops";
    private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.ConceptDiagram);

	@Override
	public InferenceRule<SubDiagramIndexArg> getInferenceRule() {
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
		return Translations.i18n(locale, "REMOVE_COPS_DESCRIPTION");
	}

	@Override
	public String getPrettyName(Locale locale) {
		return Translations.i18n(locale, "REMOVE_COPS_PRETTY_NAME");
	}

	@Override
	public Class<SubDiagramIndexArg> getArgumentType() {
		return SubDiagramIndexArg.class;
	}

	@Override
	public Set<DiagramType> getApplicableTypes() {
		return applicableTypes;
	}
	
	@Override
	public RuleApplicationInstruction<SubDiagramIndexArg> getInstructions() {
		return new SelectSingleSubDiagramInstruction(); 
	}
	
    
    
    
    @Override
    public RuleApplicationResult apply(RuleArg arg, Goals goals) throws RuleApplicationException {
        return apply(SubDiagramIndexArg.getSubDiagramIndexArgFrom(arg), goals);
    }
    
    
    private RuleApplicationResult apply(SubDiagramIndexArg targetCOP, Goals goals) throws RuleApplicationException {
        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
        SpiderDiagram targetSubgoal = getSubgoal(targetCOP, goals);
        int indexOfParent = targetSubgoal.getParentIndexOf(targetCOP.getSubDiagramIndex());
//        System.out.println("SubGoalIndex" + targetCOP.getSubgoalIndex());
//        System.out.println("SubDiagramIndex" + targetCOP.getSubDiagramIndex());
//        System.out.println("ParentIndex" + indexOfParent);
        newSubgoals[targetCOP.getSubgoalIndex()] = targetSubgoal.transform(new RemoveCOPTransformer(indexOfParent, targetCOP));
        return createRuleApplicationResult(newSubgoals);
    }
    
//	SubDiagramIndexArg parentSubDiagramIndexArg = new SubDiagramIndexArg(targetCOP.getSubgoalIndex(),indexOfParent);
//	newSubgoals[indexOfParent] = getSubgoal(parentSubDiagramIndexArg, goals).transform(new RemoveCOPTransformer(indexOfParent, targetCOP));
    
//  newSubgoals[indexOfParent] = targetSubgoal.transform(new RemoveCOPTransformer(indexOfParent, targetCOP));
//  newSubgoals[operatorDiagram.getSubgoalIndex()] = getSubgoal(operatorDiagram, goals).transform(new ConjunctionEliminationTransformer(operatorDiagram.getSubDiagramIndex(), targetChild, applyStyle));
//	System.out.println("parentIndex" + indexOfParent);
//	System.out.println("goals" + goals);

}
