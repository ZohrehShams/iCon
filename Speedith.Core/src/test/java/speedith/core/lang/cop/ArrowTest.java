package speedith.core.lang.cop;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.Comparator;


public class ArrowTest {
	
	private Arrow one = new Arrow("A","B","solid");
	private Arrow two = new Arrow("A","B","dashed");
	private Arrow three = new Arrow("C","D","dashed");
	private Arrow four = new Arrow("C","D","dashed");
	

	@Test
	public void testSameSource() {
		assertTrue(Arrow.sameSource(one,two));
		assertFalse(Arrow.sameSource(one,three));
	}
	

	@Test
	public void testSameArrow() {
		assertTrue(Arrow.sameArrow(three,four));
		assertFalse(Arrow.sameArrow(two,four));
	}


	@Test
	public void testCompareTo() {
		int v1 = three.compareTo(four);
		int v2 = one.compareTo(two);
		assertEquals(v1,0);
		assertNotEquals(v2,0);
	}

	
	@Test
	public void testEqualsObject() {
		assertTrue(three.equals(four));
		assertFalse(two.equals(four));
	}
	
	@Test
	public void testCardinality() {
		//Cardinality cardinality = new Cardinality("=","1");
		Cardinality cardinality = new Cardinality(Comparator.Eq, 1);
		one.setCardinality(cardinality);
		assertEquals(one.getCardinality(),cardinality);
	}
	

	


}
