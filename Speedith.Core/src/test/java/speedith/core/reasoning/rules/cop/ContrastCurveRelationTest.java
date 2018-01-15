package speedith.core.reasoning.rules.cop;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


import org.junit.Test;

import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.reader.COPDiagramReader;
import speedith.core.lang.reader.ReadingException;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;

public class ContrastCurveRelationTest {

    @Test
    public void apply() throws RuleApplicationException, ReadingException {
    	ContrastCurveRelation contrastCurveRelation = new ContrastCurveRelation();
    	
    	SpiderDiagram sd = COPDiagramReader.readSpiderDiagram(B2XYSwap);
    	Goals targetOfInference = Goals.createGoalsFrom(sd);

    	

        RuleApplicationResult applicationResult = contrastCurveRelation.applyForwards(new MultipleRuleArgs(new ContourArg(0, 1, "X"),new ContourArg(0, 1, "Y")), targetOfInference);
        
        SpiderDiagram expectedGoal = COPDiagramReader.readSpiderDiagram(B2XYEmpX);

        assertTrue(applicationResult.getGoals().getGoalAt(0).isSEquivalentTo(expectedGoal));
    }

}
