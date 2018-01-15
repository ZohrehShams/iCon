package speedith.core.lang;

import static org.junit.Assert.*;

import org.junit.Test;


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

}
