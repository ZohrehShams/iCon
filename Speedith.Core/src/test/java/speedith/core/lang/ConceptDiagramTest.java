package speedith.core.lang;

import static speedith.core.reasoning.util.unitary.TestConceptDiagrams.*;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;


import org.junit.Test;

public class ConceptDiagramTest {
	
	@Test
	public void test_get_Sub_Diagram_Count(){
		assertEquals(3, oneCD.getSubDiagramCount());
	}
	
	@Test 
	public void test_get_sub_diagram_at_primaries(){
		//assertThat(sevenLabArrowLUCarCOP2,equalTo(oneCD.getSubDiagramAt(2)));
		assertSame(sevenLabArrowLUCarCOP,oneCD.getSubDiagramAt(1));
		assertSame(sevenLabArrowLUCarCOP2,oneCD.getSubDiagramAt(2));
		assertSame(alternativeCompCOPDiffLabels,fourCarCDThreePrimaries.getSubDiagramAt(3));
	}

	@Test 
	public void test_get_sub_diagram_at_CD_itself(){
		//assertThat(oneCD,equalTo(oneCD.getSubDiagramAt(0)));
		assertSame(oneCD,oneCD.getSubDiagramAt(0));
	}
	
	
	
	
	@Test
	public void test_is_valid_false(){
		assertFalse(oneCD.isValid());
	}
	
	
	@Test
	public void test_is_valid_true(){
		assertTrue(twoCompCD.isValid());
	}
	
	
	@Test
	public void test_is_SEquivalent_itself(){
		assertTrue(fourCarCD.isSEquivalentTo(fourCarCD));
	}
	
	
	@Test
	public void test_is_SEqual_itself(){
		assertTrue(fourCarCD.equals(fourCarCD));
	}
	
	
	
	// The order of primaries in fourCarCD and fourCarCOPOrder is not the same, 
	// so although they are equivalent, they are not equal.
	@Test
	public void test_is_SEquivalent_true(){
		assertTrue(fourCarCD.isSEquivalentTo(fourCarCDCOPOrder));
	}
	@Test
	public void test_is_SEqual_true(){
		assertFalse(fourCarCD.equals(fourCarCDCOPOrder));
	}
	
	
	@Test
	public void test_is_SEquivalent_false(){
		assertFalse(fourCarCD.isSEquivalentTo(fiveCarCD));
	}
	
	
	@Test
	public void test_is_SEquql_false(){
		assertFalse(fourCarCD.equals(fiveCarCD));
	}
	
	
	
	@Test
	public void test_iterator(){
		checkCDIterator(fiveCarCD);
		checkCDIterator(fourCarCD);
		checkCDIterator(oneCD);
	}
	
	
    
    private void checkCDIterator(SpiderDiagram sd) {
        int i = 0;
        for (SpiderDiagram ssd : sd) {
        	assertSame(sd.getSubDiagramAt(i++), ssd);
        }
        assertEquals(i, sd.getSubDiagramCount());
    }

}
