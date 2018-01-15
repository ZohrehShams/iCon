package speedith.core.reasoning.rules.instructions;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.*;

import org.junit.Test;

import speedith.core.lang.Arrow;
import speedith.core.reasoning.args.ArrowArg;
import speedith.core.reasoning.args.MultipleRuleArgs;
import speedith.core.reasoning.args.RuleArg;
import speedith.core.reasoning.args.selection.ArrowsSelectionStep;

public class SelectArrowsInstructionTest {
	
    private SelectArrowsInstruction selectArrowsInstruction = new SelectArrowsInstruction();


	@Test
	public void testGetSelectionSteps() {
        assertThat(
                selectArrowsInstruction.getSelectionSteps(),
                contains(instanceOf(ArrowsSelectionStep.class)));
	}

	@Test
	public void testExtractRuleArg() {
		Arrow arrow = new Arrow ("A","B","dashed");
		MultipleRuleArgs ruleArgs = selectArrowsInstruction.extractRuleArg(new TestArrowSelectionSequence(new ArrowArg(0, 3, arrow)), 1);
        assertThat(ruleArgs.getRuleArgs(),contains(samePropertyValuesAs((RuleArg) new ArrowArg(1, 3, arrow)))
        );
	}

}
