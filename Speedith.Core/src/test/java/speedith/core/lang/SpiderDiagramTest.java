package speedith.core.lang;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static speedith.core.reasoning.util.unitary.TestConceptDiagrams.*;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;



public class SpiderDiagramTest {

    private final CompoundSpiderDiagram compoundSpiderDiagram = SpiderDiagrams.createCompoundSD(Operator.Conjunction, SpiderDiagrams.createNullSD(), SpiderDiagrams.createNullSD());

    @Test
    public void getParentIndexOf_should_return_a_negative_number_when_asked_for_the_parent_of_a_diagram_at_root() throws Exception {
        int parentIndexOf = SpiderDiagrams.createNullSD().getParentIndexOf(0);

        assertEquals(-1, parentIndexOf);
    }

    @Test
    public void getParentIndexOf_should_return_the_root_when_asked_for_the_parent_of_the_first_subdiagram() throws Exception {
        int indexOfParent = compoundSpiderDiagram.getParentIndexOf(1);

        assertEquals(0, indexOfParent);
    }

    @Test
    public void getParentIndexOf_should_return_the_index_of_the_left_conjunct_when_asked_for_parent_of_subdiagram_3() throws Exception {
        CompoundSpiderDiagram bigCompoundSpiderDiagram = SpiderDiagrams.createCompoundSD(Operator.Conjunction, compoundSpiderDiagram, compoundSpiderDiagram);
        int indexOfParent = bigCompoundSpiderDiagram.getParentIndexOf(3);
        assertEquals(1, indexOfParent);
    }

    @Test
    public void getParentIndexOf_should_return_the_index_of_the_right_conjunct_when_asked_for_parent_of_the_last_subdiagram() throws Exception {
        CompoundSpiderDiagram bigCompoundSpiderDiagram = SpiderDiagrams.createCompoundSD(Operator.Conjunction, compoundSpiderDiagram, compoundSpiderDiagram);
        int indexOfParent = bigCompoundSpiderDiagram.getParentIndexOf(6);
        assertEquals(4, indexOfParent);
    }
    
    
    
    //Zohreh: tests for index of parent in presence of conceptDiagram
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_root_when_asked_for_parent_of_the_first_subdiagram() throws Exception {
        int indexOfParent = fourCarCDThreePrimaries.getParentIndexOf(1);
        assertEquals(0, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_root_when_asked_for_parent_of_the_second_subdiagram() throws Exception {
        int indexOfParent = fourCarCDThreePrimaries.getParentIndexOf(2);
        assertEquals(0, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_root_when_asked_for_parent_of_the_third_subdiagram() throws Exception {
        int indexOfParent = fourCarCDThreePrimaries.getParentIndexOf(3);
        assertEquals(0, indexOfParent);
    }
    
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_root_when_asked_for_parent_of_subdiagram1() throws Exception {
    	int indexOfParent = oneCompoundCD.getParentIndexOf(1);
        assertEquals(0, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_left_conjunct_when_asked_for_parent_of_subdiagram2() throws Exception {
        int indexOfParent = oneCompoundCD.getParentIndexOf(2);
        assertEquals(1, indexOfParent);
    }
    
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_left_conjunct_when_asked_for_parent_of_subdiagram3() throws Exception {
        int indexOfParent = oneCompoundCD.getParentIndexOf(3);
        assertEquals(1, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_left_conjunct_when_asked_for_parent_of_subdiagram4() throws Exception {
        int indexOfParent = oneCompoundCD.getParentIndexOf(4);
        assertEquals(1, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_root_when_asked_for_parent_of_subdiagram5() throws Exception {
        int indexOfParent = oneCompoundCD.getParentIndexOf(5);
        assertEquals(0, indexOfParent);
    }
    
    
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_first_conjunction_when_asked_for_parent_of_subdiagram2() throws Exception {
        int indexOfParent = fourCompoundCD.getParentIndexOf(2);
        assertEquals(1, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_first_conncept_diagram_when_asked_for_parent_of_subdiagram4() throws Exception {
        int indexOfParent = fourCompoundCD.getParentIndexOf(4);
        assertEquals(2, indexOfParent);
    }
    
    @Test
    public void getParentIndexOf_should_return_the_index_of_the_second_conncept_diagram_when_asked_for_parent_of_subdiagram10() throws Exception {
        int indexOfParent = fourCompoundCD.getParentIndexOf(10);
        assertEquals(7, indexOfParent);
    }
    //end
    
    
    
    @Test
    public void compund_with_concept_diagrams_get_sub_diagrams_count(){
    	CompoundSpiderDiagram incCD = SpiderDiagrams.createCompoundSD(Operator.Conjunction,one,oneCD);
    	assertEquals(5,incCD.getSubDiagramCount());
    }
    
    @Test
    public void compund_with_concept_diagrams_get_sub_diagrams_at_primary(){
    	CompoundSpiderDiagram incCD = SpiderDiagrams.createCompoundSD(Operator.Conjunction,one,oneCD);
    	assertThat(sevenLabArrowLUCarCOP2,equalTo(incCD.getSubDiagramAt(4)));
    }
    
    @Test
    public void compund_with_concept_diagrams_get_sub_diagrams_at_cd(){
    	CompoundSpiderDiagram incCD = SpiderDiagrams.createCompoundSD(Operator.Conjunction,one,oneCD);
    	CompoundSpiderDiagram incCD2 = SpiderDiagrams.createCompoundSD(Operator.Implication,incCD,one);
    	assertThat(oneCD,equalTo(incCD2.getSubDiagramAt(3)));
    }
    
    
}
