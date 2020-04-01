package speedith.core.reasoning.rules.cop;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.lang.Zone;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;

public class TRAddShadingTest {
	
	
    private TRAddShading trAddShading;
  
    @Before
    public void setUp() {
        trAddShading = new TRAddShading();
    }
    

    @Test(expected = TransformationException.class)
    public void apply_should_throw_an_exception_when_adding_a_zone_that_is_already_shaded_in_the_COP_diagram() throws RuleApplicationException {
        COPDiagram targetDiagram = TestCOPDiagrams.threeLU;
        Goals targetOfInference = Goals.createGoalsFrom(targetDiagram);
        trAddShading.apply(new MultipleRuleArgs(new ZoneArg(0, 0, Zone.fromInContours("C").withOutContours("A","B"))), targetOfInference);
    }
    

    @Test(expected = TransformationException.class)
    public void apply_should_throw_an_exception_when_adding_shading_to_a_zone_that_does_not_exist_in_the_COP_diagram() throws RuleApplicationException {
        COPDiagram targetDiagram = TestCOPDiagrams.threeLU;
        Goals targetOfInference = Goals.createGoalsFrom(targetDiagram);
        trAddShading.apply(new MultipleRuleArgs(new ZoneArg(0, 0, Zone.fromInContours("D").withOutContours("A","B"))), targetOfInference);
    }
    
    
  @Test
  public void apply_must_add_a_new_shaded_zone_to_the_set_of_shaded_zones_of_cop_diagram_target()  throws RuleApplicationException {
  	LUCOPDiagram target = (LUCOPDiagram) TestCOPDiagrams.fourLU;
      Goals targetOfInference = Goals.createGoalsFrom(target);
      SpiderDiagram expectedResult = TestCOPDiagrams.threeLU;  
      RuleApplicationResult result = trAddShading.apply(new 
      		MultipleRuleArgs(new ZoneArg(0,0,Zone.fromInContours("C").withOutContours("A","B"))), targetOfInference);
      //assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));
      assertTrue(result.getGoals().getGoalAt(0).isSEquivalentTo(expectedResult));
  }
    
    
    
}
