package speedith.core.lang;

import static org.junit.Assert.*;
import org.junit.Test;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.sevenLabArrowLUCarCOP;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.sevenLabArrowLUCarCOP2;

public class LUCarCOPDiagramTest {

	private Arrow sABhas = new Arrow("A","B","solid","has");
	
	@Test
	public void testCardinalityEnum() {
		Cardinality cardinal = sevenLabArrowLUCarCOP.getArrowCardinalities().get(sABhas); 
		Cardinality cardinality = new Cardinality(Comparator.Geq, 0);
		assertEquals(cardinal,cardinality);
	}
	
	@Test
	public void testCardinalityString() {
		Cardinality cardinal = sevenLabArrowLUCarCOP.getArrowCardinalities().get(sABhas); 
		Cardinality cardinality = new Cardinality(">=", "0");
		assertEquals(cardinal,cardinality);
	}
	
//	@Test
//	public void testArrowCardinalityString() {
//		Cardinality cardinal  = sevenLabArrowLUCarCOP.getArrows().first().getCardinality();
//		Cardinality cardinality = new Cardinality(">=", "0");
//		assertEquals(cardinal,cardinality);
//	}
	

}
