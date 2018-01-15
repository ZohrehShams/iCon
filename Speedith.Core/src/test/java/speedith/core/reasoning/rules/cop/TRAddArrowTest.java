package speedith.core.reasoning.rules.cop;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.TreeSet;

import org.junit.Test;

import speedith.core.lang.Arrow;
import speedith.core.lang.COPDiagram;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.InferenceRules;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.SubDiagramIndexArg;
import speedith.core.reasoning.args.ZonesInOutArg;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;

public class TRAddArrowTest {
	
	public TRAddArrowTest(){
		
	}


	@Test
	public void testApplyForward_solidArrow() throws RuleApplicationException{
		Arrow arrow = new Arrow("A","B","solid");
		
		TRAddArrow addUnSolArrow = (TRAddArrow) InferenceRules.getInferenceRule(TRAddArrow.InferenceRuleName);
		LUCOPDiagram lucop = TestCOPDiagrams.eighteenLU;
		
	    RuleApplicationResult result = addUnSolArrow.applyForwards(new ArrowArg(0, 0, arrow), Goals.createGoalsFrom(lucop));
	    SpiderDiagram expectedResult = TestCOPDiagrams.twelve;
	    		
	    assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));		
	}

	@Test
	public void testApplyForward_dashedArrow() throws RuleApplicationException{
		Arrow arrow = new Arrow("A","B","dashed");
		
		TRAddArrow addSolArrow = (TRAddArrow) InferenceRules.getInferenceRule(TRAddArrow.InferenceRuleName);
		LUCOPDiagram lucop = TestCOPDiagrams.eighteenLU;
		
	    RuleApplicationResult result = addSolArrow.applyForwards(new ArrowArg(0, 0, arrow), Goals.createGoalsFrom(lucop));
	    SpiderDiagram expectedResult = TestCOPDiagrams.nineteen;
	    		
	    assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));		
	}

	
	@Test
	public void testApplyForward_dashedArrow_spiders() throws RuleApplicationException{
		Arrow arrow = new Arrow("t1","t2","dashed");
		
		TRAddArrow addSolArrow = (TRAddArrow) InferenceRules.getInferenceRule(TRAddArrow.InferenceRuleName);
		LUCOPDiagram lucop = TestCOPDiagrams.twentyOne;
		
	    RuleApplicationResult result = addSolArrow.applyForwards(new ArrowArg(0, 0, arrow), Goals.createGoalsFrom(lucop));
	    SpiderDiagram expectedResult = TestCOPDiagrams.twenty;
	    		
	    assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));		
	}

}
