package speedith.core.reasoning.rules;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import speedith.core.lang.COPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.rules.instructions.SelectArrowsInstruction;
import speedith.core.reasoning.rules.instructions.SelectContoursInstruction;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;
import speedith.core.reasoning.util.unitary.TestSpiderDiagrams;

/**
*
* @author Zohreh Shams [zs315@cam.ac.uk]
*/

public class RemoveArrowTest {
	
    private RemoveArrow removeArrow;
  
    

    @Before
    public void setUp() {
        removeArrow = new RemoveArrow();
    }

	@Test
	public void testGetInferenceRule() {
		assertSame(removeArrow, removeArrow.getInferenceRule());	
	}
	
    @Test(expected = TransformationException.class)
    public void apply_should_throw_an_exception_when_removing_an_arrow_that_does_not_exist_in_the_cop_diagram() throws RuleApplicationException {
        COPDiagram targetDiagram = TestCOPDiagrams.one;
        Goals targetOfInference = Goals.createGoalsFrom(targetDiagram);
        removeArrow.apply(new MultipleRuleArgs(new ArrowArg(0, 0, TestCOPDiagrams.dQR)), targetOfInference);
    }


    @Test(expected = RuleApplicationException.class)
    public void apply_should_throw_an_exception_when_the_arguments_are_null() throws RuleApplicationException {
        removeArrow.apply(null, null);
    }
    
    
    @Test(expected = RuleApplicationException.class)
    public void apply_should_throw_an_exception_when_the_arguments_are_not_of_the_right_type() throws RuleApplicationException {
        removeArrow.apply(new ContourArg(0, 0, "A"), null);
    }
    
    
    @Test(expected = RuleApplicationException.class)
    public void apply_should_throw_an_exception_when_any_of_the_multiple_args_is_not_an_arrow() throws RuleApplicationException {
        removeArrow.apply(new MultipleRuleArgs(Arrays.asList(new ZoneArg(0, 0, Zone.fromInContours("A")))), null);
    }
    
    @Test(expected = RuleApplicationException.class)
    public void apply_should_throw_an_exception_when_arrow_args_contain_different_sub_diagram_indices() throws RuleApplicationException {
        List<ArrowArg> arrowsFromDifferentSpiderDiagrams = Arrays.asList(new ArrowArg(0, 0, TestCOPDiagrams.sAB), new ArrowArg(0, 1, TestCOPDiagrams.dBA));
        removeArrow.apply(new MultipleRuleArgs(arrowsFromDifferentSpiderDiagrams), null);
    }
    
    
    @Test(expected = RuleApplicationException.class)
    public void apply_should_throw_an_exception_when_arrow_args_contain_different_goal_indices() throws RuleApplicationException {
        List<ArrowArg> arrowsFromDifferentGoals = Arrays.asList(new ArrowArg(1, 0, TestCOPDiagrams.sAB), new ArrowArg(0, 0,TestCOPDiagrams.dt1t2 ));
        removeArrow.apply(new MultipleRuleArgs(arrowsFromDifferentGoals), null);
    }
    
    
    @Test
    public void getArrowArgsFrom_should_return_a_list_of_arrow_args() throws RuleApplicationException {
        List<ArrowArg> expectedArrowArgs = Arrays.asList(new ArrowArg(0, 0,TestCOPDiagrams.sAB), new ArrowArg(0, 0,TestCOPDiagrams.dt1B ));
        ArrayList<ArrowArg> arrowArgs = ArrowArg.getArrowArgsFrom(new MultipleRuleArgs(expectedArrowArgs));
        assertEquals(expectedArrowArgs, arrowArgs);
    }

	@Test
	public void testGetInstructions() {
        assertThat(removeArrow.getInstructions(),instanceOf(SelectArrowsInstruction.class));
	}

	

    @Test
    public void testApplyForwards() throws RuleApplicationException {
    	COPDiagram target = TestCOPDiagrams.one;
        Goals targetOfInference = Goals.createGoalsFrom(target);
        SpiderDiagram expectedResult = TestCOPDiagrams.two;
        
        RuleApplicationResult result = removeArrow.applyForwards(new MultipleRuleArgs(new ArrowArg(0,0,TestCOPDiagrams.sAB)), targetOfInference);
        assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));
    }
    
    
	/**
	 * Because this rule is information weakening it can only be applied in a forward style (as opposed to goal style). 
	 * 
	 */
    @Test(expected = TransformationException.class)
    public void apply_should_throw_exception() throws RuleApplicationException {
    	COPDiagram target = TestCOPDiagrams.one;
        Goals targetOfInference = Goals.createGoalsFrom(target);

        RuleApplicationResult result = removeArrow.apply(new MultipleRuleArgs(new ArrowArg(0,0,TestCOPDiagrams.sAB)), targetOfInference);
    }
        


}
