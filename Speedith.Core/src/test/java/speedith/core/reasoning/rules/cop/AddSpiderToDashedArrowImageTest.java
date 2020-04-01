package speedith.core.reasoning.rules.cop;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.reader.CDiagramReaderTest;
import speedith.core.lang.reader.CDiagramsReader;
import speedith.core.lang.reader.ReadingException;
import speedith.core.reasoning.Goals;
import speedith.core.reasoning.RuleApplicationException;
import speedith.core.reasoning.RuleApplicationResult;
import speedith.core.reasoning.args.ContourArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.copArgs.ArrowArg;

public class AddSpiderToDashedArrowImageTest {

    @Test
    public void test_apply_CD() throws RuleApplicationException, ReadingException {
    	AddSpiderToDashedArrowImage addSpiderToDashedArrowImage = new AddSpiderToDashedArrowImage();
    	
    	CompoundSpiderDiagram sd = (CompoundSpiderDiagram) CDiagramsReader.readSpiderDiagram(CDiagramReaderTest.CD_AddSpiderToDashedArrowImage);
    	CompoundSpiderDiagram expectedResult = (CompoundSpiderDiagram) CDiagramsReader.readSpiderDiagram(CDiagramReaderTest.CD_AddSpiderToDashedArrowImage_Result);

    	
    	Goals targetOfInference = Goals.createGoalsFrom(sd);
    	
        RuleApplicationResult applicationResult = addSpiderToDashedArrowImage.applyForwards(new MultipleRuleArgs(new ArrowArg(0, 1, new Arrow("s","u","dashed","R")),
        		new ContourArg(0, 1, "C"), new ArrowArg(0,2,new Arrow("s","t", "dashed","R"))), targetOfInference);
        assertTrue(applicationResult.getGoals().getGoalAt(0).isSEquivalentTo(expectedResult));
    }

}
