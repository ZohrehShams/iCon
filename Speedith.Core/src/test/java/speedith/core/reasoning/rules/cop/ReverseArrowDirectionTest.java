package speedith.core.reasoning.rules.cop;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.core.lang.reader.CDiagramsReader;
import speedith.core.lang.reader.ReadingException;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.copArgs.ArrowArg;

public class ReverseArrowDirectionTest {

    @Test
    public void test_apply_CD() throws RuleApplicationException, ReadingException, IOException {
    	ReverseArrowDirection reverseArrowDirection = new ReverseArrowDirection();
    	
    	File file = new File("../examples/CDs/ReverseArrowDirection.sdt");
    	ConceptDiagram cd = (ConceptDiagram) CDiagramsReader.readSpiderDiagram(file);
    	
    	File fileResult = new File("../examples/CDs/ReverseArrowDirectionExpected.sdt");
    	ConceptDiagram cdExpected = (ConceptDiagram) CDiagramsReader.readSpiderDiagram(fileResult);
    	
    	Goals targetOfInference = Goals.createGoalsFrom(cd);	
        RuleApplicationResult applicationResult = reverseArrowDirection.applyForwards(new ArrowArg(0, 0, new Arrow("u","y","dashed","p")), targetOfInference);
        
        assertTrue(applicationResult.getGoals().getGoalAt(0).isSEquivalentTo(cdExpected));
    }

}
