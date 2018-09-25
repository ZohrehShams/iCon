package speedith.core.reasoning.rules.cop;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

import speedith.core.lang.COPDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRules;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.copArgs.ZonesInOutArg;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;

public class TRAddCurveLabelTest {

public TRAddCurveLabelTest(){
		
	}

//@Test
//public void testApplyForward() throws RuleApplicationException{
//	
//	TRAddCurveLabel trAddCurveLabel = (TRAddCurveLabel) InferenceRules.getInferenceRule(TRAddCurveLabel.InferenceRuleName);
//	COPDiagram cop = TestCOPDiagrams.seventeen;
//	
//	RuleApplicationResult result = trAddCurveLabel.applyForwards(new MultipleRuleArgs(new ContourArg(0, 0, "")), Goals.createGoalsFrom(cop));
//    SpiderDiagram expectedResult = TestCOPDiagrams.fiftheen;
//    		
//   assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));		
//}



}
