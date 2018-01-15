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
		assertSame(sevenLabArrowLUCarCOP2,oneCD.getSubDiagramAt(2));
	}
	
	@Test 
	public void test_get_sub_diagram_at_CD_itself(){
		//assertThat(oneCD,equalTo(oneCD.getSubDiagramAt(0)));
		assertSame(oneCD,oneCD.getSubDiagramAt(0));
	}
	
	
	@Test
	public void test_iterator(){
		checkSDIterator(oneCD);
	}
	
	
    
    private void checkSDIterator(SpiderDiagram sd) {
        int i = 0;
        for (SpiderDiagram ssd : sd) {
        	assertSame(sd.getSubDiagramAt(i++), ssd);
        }
        assertEquals(i, sd.getSubDiagramCount());
    }

}
