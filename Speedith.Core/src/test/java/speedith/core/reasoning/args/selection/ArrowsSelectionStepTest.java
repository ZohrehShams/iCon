package speedith.core.reasoning.args.selection;

import static org.junit.Assert.*;

import speedith.core.lang.Arrow;
import speedith.core.reasoning.args.copArgs.ArrowArg;
import speedith.core.reasoning.args.selection.copSelection.ArrowsSelectionStep;
import speedith.core.reasoning.rules.instructions.TestArrowSelectionSequence;

import org.junit.Test;

public class ArrowsSelectionStepTest {
	
	private Arrow arrow = new Arrow("A","B","solid");
    private final TestArrowSelectionSequence singleArrowSelectionSequence = new TestArrowSelectionSequence(new ArrowArg(0, 1, arrow));
    private ArrowsSelectionStep arrowsSelectionStep = new ArrowsSelectionStep();

	@Test
	public void testIsFinished() {
		assertFalse(arrowsSelectionStep.isFinished(singleArrowSelectionSequence, 1));
	}
	
	@Test
	public void testInit() {
		assertNull(arrowsSelectionStep.init(singleArrowSelectionSequence, 1));
	}

	
	@Test
	public void testIsSkippable() {
		assertFalse(arrowsSelectionStep.isSkippable(new TestArrowSelectionSequence(), 0));
		assertTrue(arrowsSelectionStep.isSkippable(singleArrowSelectionSequence, 0));
	}
}
