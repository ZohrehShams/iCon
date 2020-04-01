package speedith.core.reasoning.rules.cop;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.Zone;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.ZoneArg;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;

public class TRAddSpiderTest {

    private TRAddSpider trAddSpider;
    
    @Before
    public void setUp() {
    	trAddSpider = new TRAddSpider();
    }
	
	
	
	
//	  @Test
//	  public void apply_must_add_a_new_spider_to_the_selected_zone_in_cop_diagram_target()  throws RuleApplicationException {
//	  	COPDiagram target = TestCOPDiagrams.eighteen;
//	      Goals targetOfInference = Goals.createGoalsFrom(target);
//	      SpiderDiagram expectedResult = TestCOPDiagrams.eighteenSp;  
//	      RuleApplicationResult result = trAddSpider.apply(new 
//	      		MultipleRuleArgs(new ZoneArg(0,0,Zone.fromInContours("B").withOutContours("A"))), targetOfInference);
//	      assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));
//	  }
//	  
//	  
//	  @Test
//	  public void apply_must_add_a_new_spider_to_the_selected_ZONES_in_cop_diagram_target()  throws RuleApplicationException {
//	  	COPDiagram target = TestCOPDiagrams.eighteen;
//	      Goals targetOfInference = Goals.createGoalsFrom(target);
//	      SpiderDiagram expectedResult = TestCOPDiagrams.eighteenSp2;  
//	      RuleApplicationResult result = trAddSpider.apply(new 
//	      		MultipleRuleArgs(new ZoneArg(0,0,Zone.fromInContours("B").withOutContours("A")),
//	      				new ZoneArg(0,0,Zone.fromInContours("A").withOutContours("B"))), targetOfInference);
//	      assertThat(result.getGoals().getGoalAt(0),equalTo(expectedResult));
//	  }

}
