package speedith.core.reasoning.rules.cop;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import speedith.core.i18n.Translations;
import speedith.core.lang.DiagramType;
import speedith.core.lang.SpiderDiagram;
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
import speedith.core.reasoning.args.selection.copSelection.ArrowsSelectionStep;
import speedith.core.reasoning.rules.SimpleInferenceRule;
import speedith.core.reasoning.rules.instructions.copIns.SelectArrowsInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowInstruction;
import speedith.core.reasoning.rules.instructions.copIns.SelectSingleArrowSingleCurveInstruction;
import speedith.core.reasoning.rules.transformers.copTrans.SwapSpiderWithCardinalityCurveTransformer;
import speedith.core.reasoning.rules.transformers.copTrans.SwapSpiderWithCardinalityTransformer;


/**
 * This rule operates on unitary diagrams. When there is a dashed arrow with no cardinality and with a named spider source and a spider 
 * target, we can swap the spider with an unnamed curve and add >=1 cardinality to the arrow that targets the unnamed curve.
 * @author Zohreh Shams
 */
public class SwapSpiderWithCardinality extends SimpleInferenceRule<ArrowArg> implements Serializable, ForwardRule<ArrowArg>{

	private static final long serialVersionUID = -1963839956170379105L;
	public static final String InferenceRuleName = "swap_spider_with_arrow_cardinality";
	private static final Set<DiagramType> applicableTypes = EnumSet.of(DiagramType.COPDiagram,DiagramType.LUCOPDiagram);

		@Override
		public InferenceRule<ArrowArg> getInferenceRule() {
			return this;
		}
		
	    @Override
	    public String getInferenceName() {
	        return InferenceRuleName;
	    }

		@Override
		public String getCategory(Locale locale) {
			return null;
		}

		@Override
		public String getDescription(Locale locale) {
			return Translations.i18n(locale, "SWAP_SPIDER_WITH_ARROW_CARDINALITY_DESCRIPTION");
		}

		@Override
		public String getPrettyName(Locale locale) {
			return Translations.i18n(locale, "SWAP_SPIDER_WITH_ARROW_CARDINALITY_PRETTY_NAME");
		}

		@Override
		public Class<ArrowArg> getArgumentType() {
			return ArrowArg.class;
		}

		@Override
		public Set<DiagramType> getApplicableTypes() {
			return applicableTypes;
		}
		
		@Override
		public RuleApplicationInstruction<ArrowArg> getInstructions() {
			return new SelectSingleArrowInstruction();
		}
		
	    @Override
		public RuleApplicationResult applyForwards(RuleArg args, Goals goals) throws RuleApplicationException {
			return apply(args, goals);
		}
		
		
		@Override
		public RuleApplicationResult apply(RuleArg arg, Goals goals) throws RuleApplicationException {
	        ArrowArg arrow = (ArrowArg) arg;
	        return apply(arrow, goals);
		}
		

	    private RuleApplicationResult apply(ArrowArg targetArrow, Goals goals) throws RuleApplicationException {
	        SpiderDiagram[] newSubgoals = goals.getGoals().toArray(new SpiderDiagram[goals.getGoalsCount()]);
	        SpiderDiagram targetSubgoal = getSubgoal(targetArrow, goals);
	        newSubgoals[targetArrow.getSubgoalIndex()] = targetSubgoal.transform(new SwapSpiderWithCardinalityTransformer(targetArrow, true));
	        return createRuleApplicationResult(newSubgoals);
	    }
	        

}
