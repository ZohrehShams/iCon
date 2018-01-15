package speedith.core.lang;

import static org.junit.Assert.*;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;

import org.junit.Test;

public class LUCOPDiagramTest {

	@Test
	public void testIsValid(){
		assertTrue(oneLU.isValid());
	}
	
}
