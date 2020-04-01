package speedith.core.lang.cop;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.Test;

import speedith.core.lang.Region;
import speedith.core.lang.Zone;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.COPDiagram;



public class COPDiagramTest {
	
	Arrow firstArrow = new Arrow("A","B","solid");
	Arrow secondArrow = new Arrow("B","A","dashed");
	Arrow thirdArrow = new Arrow("t1","B","dashed");
	Arrow fourthArrow = new Arrow("t2","B","dashed");
	Arrow fifthArrow = new Arrow("t1","t2","dashed");
	Arrow sixthArrow = new Arrow("Q","R","dashed");
	Arrow seventhArrow = new Arrow("A","B","bold");
	
	
	
	public TreeSet<String> settingSpiders(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		spiders.add("t2");
		return spiders;
	}
	
	
	public TreeMap<String, Region> settingHabitats(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B")));
        return habitats;
	}
	
	
	public TreeSet<Zone> settingShadedZones(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B"));
        return shadedZones;
	}

	public TreeSet<Zone> settingPresentZones(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("B").withOutContours("A"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B"));
		presentZones.add(Zone.fromOutContours("A","B"));
        return presentZones;
	}
	
	
	public TreeSet<Arrow> settingArrows(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(firstArrow);
		arrows.add(secondArrow);
		arrows.add(thirdArrow);
        return arrows;
	}
	
	
	
	private COPDiagram one = new COPDiagram(settingSpiders(), settingHabitats(), settingShadedZones(), 
			settingPresentZones(), settingArrows());
	
	
//This test was meaningful when I was checking the validity of the diagram in its constructor, but now we use isValid method.
//    @Test(expected = IllegalArgumentException.class)
//    public void testCOPDiagram() {
//		TreeSet<Arrow> arrows = new TreeSet<>();
//		arrows.add(firstArrow);
//		arrows.add(secondArrow);
//		arrows.add(sixthArrow);
//    	new COPDiagram(settingSpiders(), settingHabitats(), settingShadedZones(), 
//    			settingPresentZones(), arrows);
//    }
    

	
	@Test
	public void testGetArrows() {
		TreeSet<Arrow> arrowsFirstSecondThird = new TreeSet<>(Arrays.asList(firstArrow,secondArrow,thirdArrow));
		TreeSet<Arrow> arrowsFirstSecond = new TreeSet<>(Arrays.asList(firstArrow,secondArrow));
		assertEquals(arrowsFirstSecondThird,one.getArrows());
		assertNotEquals(arrowsFirstSecond,one.getArrows());
	}

	@Test
	public void testContainsArrow() {
		assertTrue(one.containsArrow(firstArrow));
		assertFalse(one.containsArrow(fourthArrow));
	}
	
	
	@Test
	public void testAddArrowEq() {
		COPDiagram newOne = one.addArrow(fifthArrow);
		TreeSet<Arrow> newArrows = new TreeSet<>();
		newArrows.add(firstArrow);
		newArrows.add(secondArrow);
		newArrows.add(thirdArrow);
		newArrows.add(fifthArrow);
		COPDiagram newCOPDiagram = new COPDiagram(settingSpiders(), settingHabitats(), settingShadedZones(), 
				settingPresentZones(), newArrows);
//		assertEquals(newOne,newCOPDiagram);
        assertTrue(newCOPDiagram.equals(newOne));
	}

	
	@Test
	public void testAddArrowUnEq() {
		COPDiagram newOne = one.addArrow(fifthArrow);
		TreeSet<Arrow> newArrows = new TreeSet<>();
		newArrows.add(firstArrow);
		newArrows.add(secondArrow);
		newArrows.add(thirdArrow);
		COPDiagram newCOPDiagram = new COPDiagram(settingSpiders(), settingHabitats(), settingShadedZones(), 
				settingPresentZones(), newArrows);
        assertFalse(newCOPDiagram.equals(newOne));
	}

	
    
	@Test
	public void testaddableArrow() {
		assertTrue(one.validArrow(fifthArrow));
		assertFalse(one.validArrow(sixthArrow));
		assertFalse(one.validArrow(seventhArrow));
	}

	
	@Test
	public void testArrowSourceContour() {
		assertTrue(one.arrowSourceContour(firstArrow));
		assertFalse(one.arrowSourceContour(thirdArrow));
	}
	

	@Test
	public void testArrowSourceSpider() {
		assertFalse(one.arrowSourceSpider(firstArrow));
		assertTrue(one.arrowSourceSpider(thirdArrow));
	}

	@Test
	public void testAddShading(){
		Zone sh = Zone.fromInContours("C").withOutContours("A","B");
		
		//COPDiagram eightClone = COPDiagram.createCOPDiagram(null,null,eight.getShadedZonesMod(),eight.getPresentZonesMod(),eight.getArrowsMod());
		
		COPDiagram eightClone = COPDiagram.createCOPDiagram(null,null,eight.getShadedZones(),eight.getPresentZones(),eight.getArrows());

		COPDiagram eightPrime =  (COPDiagram) eightClone.addShading(sh);
		
		
        assertThat(
                eightPrime.getShadedZones(),
                equalTo(nine.getShadedZones())
        );
        
        
        
        //The following two tests fail if I use eightPrime instead of eightDash because I have only added a shaded zones, 
        // but this zones should be deleted from the present zones so the new diagram is equal to nine.
		TreeSet<Zone> newPresentZones = new TreeSet<>(eightPrime.getPresentZones());
		newPresentZones.remove(sh);
		
		//COPDiagram eightDash = COPDiagram.createCOPDiagram(null,null,eightPrime.getShadedZonesMod(),newPresentZones,eightPrime.getArrowsMod());

		COPDiagram eightDash = COPDiagram.createCOPDiagram(null,null,eightPrime.getShadedZones(),newPresentZones,eightPrime.getArrows());

		
        assertThat(
                newPresentZones,
                equalTo(nine.getPresentZones())
        );
     
       assertThat(eightDash,equalTo(nine));
	}
	
	
	
	@Test 
	public void testGetAllContours(){
		TreeSet<String> contours = new TreeSet<>();
		contours.add("A");
		contours.add("B");
		assertEquals(contours, twentyTwo.getContours());
		assertEquals(contours, twentyTwo.getAllContours());
		
	}
	

	
}
