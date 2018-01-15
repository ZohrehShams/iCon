package speedith.core.lang.reader;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.LUCarCOPDiagram;
import static speedith.core.reasoning.util.unitary.TestConceptDiagrams.*;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;

public class CDiagramReaderTest {
	
    public static final String CD_EXAMPLE_1 = "BinaryCD {cd_arg1 = LUCarCOP{spiders = [], habitats = [], sh_zones = [([\"P\"], "
			+ "[\"X\",\"Y\",\"Z\"]),([\"X\",\"Y\"],[\"Z\",\"P\"]), ([\"X\",\"Z\"],[\"Y\",\"P\"]),([\"X\",\"P\"],"
			+ "[\"Z\",\"Y\"]),([\"X\",\"Y\",\"Z\"],[\"P\"]),([\"X\",\"Y\",\"P\"],[\"Z\"]),([\"X\",\"P\",\"Z\"],[\"Y\"]), "
					+ "([\"X\",\"Y\",\"Z\",\"P\"],[])], present_zones = [([\"X\"], [\"Y\",\"Z\",\"P\"]), ([\"Y\"], [\"X\",\"Z\",\"P\"])"
					+ ", ([\"Z\"], [\"Y\",\"X\",\"P\"]),([\"Y\",\"Z\"],[\"X\",\"P\"]),([\"Y\",\"P\"],[\"X\",\"Z\"]),"
					+ "([\"P\",\"Z\"],[\"X\",\"Y\"]),([\"Y\",\"Z\",\"P\"],[\"X\"]),([],[\"Y\",\"Z\",\"X\",\"P\"])], "
					+ "arrows=[(\"X\",\"P\",\"dashed\",\"R\")],spiderLabels=[],curveLabels=[(\"X\",\"X\"),(\"Y\",\"Y\"),(\"Z\",\"Z\")], "
							+ "arrowCar=[((\"X\",\"P\",\"dashed\",\"R\"),(\">=\",\"1\"))]}, cd_arg2 = LUCarCOP{spiders = [], habitats = [], sh_zones = [([\"D\"], "
			+ "[\"A\",\"B\",\"C\"]),([\"A\",\"B\"],[\"C\",\"D\"]), ([\"A\",\"C\"],[\"B\",\"D\"]),([\"A\",\"D\"],"
			+ "[\"C\",\"B\"]),([\"A\",\"B\",\"C\"],[\"D\"]),([\"A\",\"B\",\"D\"],[\"C\"]),([\"A\",\"D\",\"C\"],[\"B\"]), "
					+ "([\"A\",\"B\",\"C\",\"D\"],[])], present_zones = [([\"A\"], [\"B\",\"C\",\"D\"]), ([\"B\"], [\"A\",\"C\",\"D\"])"
					+ ", ([\"C\"], [\"B\",\"A\",\"D\"]),([\"B\",\"C\"],[\"A\",\"D\"]),([\"B\",\"D\"],[\"A\",\"C\"]),"
					+ "([\"D\",\"C\"],[\"A\",\"B\"]),([\"B\",\"C\",\"D\"],[\"A\"]),([],[\"B\",\"C\",\"A\",\"D\"])], "
					+ "arrows=[(\"A\",\"D\",\"solid\",\"R\")],spiderLabels=[],curveLabels=[(\"A\",\"A\"),(\"B\",\"B\"),(\"C\",\"C\")], "
							+ "arrowCar=[((\"A\",\"D\",\"solid\",\"R\"),(\">=\",\"3\"))]}, cd_arrows = [(\"X\",\"A\",\"dashed\",\"R\")] }";

	
	public CDiagramReaderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    
    @Test
    public void testReadSpiderDiagram_Reader() throws Exception {
    	
        ConceptDiagram sd = (ConceptDiagram) COPDiagramReader.readSpiderDiagram(CD_EXAMPLE_1);
        
        String str1 = sd.toString();
        ConceptDiagram sd2 = (ConceptDiagram) COPDiagramReader.readSpiderDiagram(str1);
        assertEquals(str1, sd2.toString());
        
        assertEquals(sd, sd2);
    }


        
    

}
