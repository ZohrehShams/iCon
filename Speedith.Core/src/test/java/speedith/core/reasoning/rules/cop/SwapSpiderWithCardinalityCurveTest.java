package speedith.core.reasoning.rules.cop;

import static org.junit.Assert.*;


import java.io.File;
import java.io.IOException;

import org.junit.Test;

import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.CarCDiagram;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.lang.reader.CDiagramsReader;
import speedith.core.lang.reader.ReadingException;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.copArgs.ArrowArg;
public class SwapSpiderWithCardinalityCurveTest {
	

    @Test
    public void test_apply_CD() throws RuleApplicationException, ReadingException, IOException {
    	SwapSpiderWithCardinalityCurve swapSpiderWithCardinalityCurve = new SwapSpiderWithCardinalityCurve();
    	
    	File file = new File("../examples/CDs/SwapSpiderWithCardinalityCurve.sdt");
    	ConceptDiagram cd = (ConceptDiagram) CDiagramsReader.readSpiderDiagram(file);
    	
    	
    	File fileResult = new File("../examples/CDs/SwapSpiderWithCardinalityCurveExpected.sdt");
    	CarCDiagram cdExpected = (CarCDiagram) CDiagramsReader.readSpiderDiagram(fileResult);
    	
    	Goals targetOfInference = Goals.createGoalsFrom(cd);
    	    	
        RuleApplicationResult applicationResult = swapSpiderWithCardinalityCurve.applyForwards(new MultipleRuleArgs(new ArrowArg(0, 0, new Arrow("u","v","dashed","p")),
        		new ContourArg(0, 2, "y")), targetOfInference);
        
        assertTrue(applicationResult.getGoals().getGoalAt(0).isSEquivalentTo(cdExpected));
    }
    
    
    

}
