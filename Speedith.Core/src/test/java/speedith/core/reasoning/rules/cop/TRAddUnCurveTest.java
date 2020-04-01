package speedith.core.reasoning.rules.cop;

import static org.junit.Assert.*;

import java.util.TreeSet;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import speedith.core.lang.Region;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.lang.reader.SpiderDiagramsReader;
import speedith.core.lang.reader.SpiderDiagramsReaderTest;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRules;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.SpiderRegionArg;
import speedith.core.reasoning.args.copArgs.ZonesInOutArg;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;

public class TRAddUnCurveTest {
	
	public TRAddUnCurveTest(){
		
	}

//@Test
//public void testApplyForward_withoutSpiders() throws RuleApplicationException{
//	TreeSet<Zone> zonesIn = new TreeSet<Zone>();
//	TreeSet<Zone> zonesOut = new TreeSet<Zone>();
//	zonesOut.add(Zone.fromInContours("A").withOutContours("B"));
//	
//	TRAddUnCurve trAddUnCurve = (TRAddUnCurve) InferenceRules.getInferenceRule(TRAddUnCurve.InferenceRuleName);
//	LUCOPDiagram lucop = TestCOPDiagrams.twelve;
//	
//    RuleApplicationResult result = trAddUnCurve.applyForwards(new ZonesInOutArg(0, 0, zonesIn, zonesOut), Goals.createGoalsFrom(lucop));
//    SpiderDiagram expectedResult = TestCOPDiagrams.thirteenLU;
//    		
//    assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));		
//}

//@Test
//public void testApplyForward_withSpiders() throws RuleApplicationException{
//	TreeSet<Zone> zonesIn = new TreeSet<Zone>();
//	TreeSet<Zone> zonesOut = new TreeSet<Zone>();
//	zonesOut.add(Zone.fromInContours("A").withOutContours("B"));
//	
//	TRAddUnCurve trAddUnCurve = (TRAddUnCurve) InferenceRules.getInferenceRule(TRAddUnCurve.InferenceRuleName);
//	COPDiagram cop = TestCOPDiagrams.fourtheen;
//	
//    RuleApplicationResult result = trAddUnCurve.applyForwards(new ZonesInOutArg(0, 0, zonesIn, zonesOut), Goals.createGoalsFrom(cop));
//    SpiderDiagram expectedResult = TestCOPDiagrams.fiftheenUnC;
//    		
//   assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));		
//}


}
