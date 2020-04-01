package speedith.core.reasoning.rules.cop;

import static org.junit.Assert.*;
import static speedith.core.reasoning.util.unitary.TestConceptDiagrams.*;

import org.junit.Test;

import speedith.core.lang.cop.Arrow;
import speedith.core.lang.reader.ReadingException;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.SpiderArg;
import speedith.core.reasoning.args.copArgs.ArrowArg;

public class AddSpiderArrowTest {

    @Test
    public void test_apply_CD() throws RuleApplicationException, ReadingException {
    	ReplaceWithSpiderArrow replaceWithSpiderArrow = new ReplaceWithSpiderArrow();
    	
    	Goals targetOfInference = Goals.createGoalsFrom(twoCompCD);

        RuleApplicationResult applicationResult = replaceWithSpiderArrow.applyForwards(new MultipleRuleArgs(new ArrowArg(0, 0, new Arrow("B","X","solid","R")),
        		new SpiderArg(0, 1, "t1")), targetOfInference);
        assertTrue(applicationResult.getGoals().getGoalAt(0).isSEquivalentTo(twoCompCDAddSpiderArrow));
    }

}
