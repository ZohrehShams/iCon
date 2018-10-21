package speedith.core.reasoning.rules.instructions.copIns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import speedith.core.lang.Region;
import speedith.core.lang.Zone;
import speedith.core.reasoning.RuleApplicationInstruction;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SpiderRegionArg;
import speedith.core.reasoning.args.SpiderZoneArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.selection.SelectSpiderFeetStep;
import speedith.core.reasoning.args.selection.SelectionSequence;
import speedith.core.reasoning.args.selection.SelectionStep;
import speedith.core.reasoning.args.selection.copSelection.SelectSingleCurveStep;

public class SelectFeetOfSpiderSingleCurveInstruction implements RuleApplicationInstruction<MultipleRuleArgs>{
	
	
	@Override
	public List<? extends SelectionStep> getSelectionSteps() {
		List<? extends SelectionStep> spiderFeetStep = Arrays.asList(SelectSpiderFeetStep.getInstance());
        SelectSingleCurveStep curveSelectionStep = new SelectSingleCurveStep();
        List<SelectionStep> result = new ArrayList<>();
        result.addAll(spiderFeetStep);
        result.add(curveSelectionStep);
        return result;
	}

	@Override
	public MultipleRuleArgs extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
		ArrayList<SubDiagramIndexArg> ruleArgs = new ArrayList<>();
		
//        List<RuleArg> arrow = selectionSequence.getAcceptedSelectionsForStepAt(0);
//        
//        for (RuleArg ruleArg : arrow) {
//            if (ruleArg instanceof ArrowArg) {
//                ArrowArg arrowArg = (ArrowArg) ruleArg;
//                ruleArgs.add(new ArrowArg(subgoalIndex, arrowArg.getSubDiagramIndex(), arrowArg.getArrow()));
//            }
//        }
        List<RuleArg> sel = selectionSequence.getAcceptedSelectionsForStepAt(0);
        SpiderZoneArg firstSelection = (SpiderZoneArg) sel.get(0);
        ruleArgs.add(new SpiderRegionArg(subgoalIndex, firstSelection.getSubDiagramIndex(), firstSelection.getSpider(), getRegionFromFeetSelection(sel)));
        
        
        List<RuleArg> curve = selectionSequence.getAcceptedSelectionsForStepAt(1);
        
        for (RuleArg ruleArg : curve) {
            if (ruleArg instanceof ContourArg) {
            	ContourArg contourArg = (ContourArg) ruleArg;
                ruleArgs.add(new ContourArg(subgoalIndex, contourArg.getSubDiagramIndex(), contourArg.getContour()));
            }
        }
        return new MultipleRuleArgs(ruleArgs);
	}

	
	
	
//    @Override
//    public SpiderRegionArg extractRuleArg(SelectionSequence selectionSequence, int subgoalIndex) {
//        List<RuleArg> sel = selectionSequence.getAcceptedSelectionsForStepAt(0);
//        SpiderZoneArg firstSelection = (SpiderZoneArg) sel.get(0);
//        return new SpiderRegionArg(subgoalIndex, firstSelection.getSubDiagramIndex(), firstSelection.getSpider(), getRegionFromFeetSelection(sel));
//    }

    private static Region getRegionFromFeetSelection(List<RuleArg> selection) {
        ArrayList<Zone> zones = new ArrayList<>();
        for (RuleArg sel : selection) {
            if (sel instanceof SpiderZoneArg) {
                SpiderZoneArg curSel = (SpiderZoneArg) sel;
                zones.add(curSel.getZone());
            }
        }
        return new Region(zones);
    }


}
