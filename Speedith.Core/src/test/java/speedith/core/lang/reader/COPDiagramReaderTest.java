package speedith.core.lang.reader;

import org.junit.*;
import speedith.core.lang.*;
import speedith.core.reasoning.util.unitary.TestCOPDiagrams;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;



public class COPDiagramReaderTest {
	
	public static final String SD_EXAMPLE_1 = "COP { spiders = [], sh_zones = [], habitats = [], present_zones=[([],[])], arrows=[]}";
	public static final String SD_EXAMPLE_2 = "COP { spiders = [\"s\"], sh_zones = [([\"A\"], [\"B\"])], habitats = [(\"s\", [([\"A\"], [\"B\"])], present_zones=[([],[\"A\",\"B\"]),([\"A\",\"B\"],[]),([\"B\"],[\"A\"])], arrows=[]}";
	public static final String SD_EXAMPLE_3 = "COP { spiders = [\"s\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [(\"s\", [([\"A\"], [\"B\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")]}";
	public static final String SD_EXAMPLE_3b = "COP { spiders = [], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")]}";
	public static final String SD_EXAMPLE_3c = "COP { spiders = [], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[]}";

	public static final String SD_EXAMPLE_4 = "COP { spiders = [\"s\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [(\"s\", [([\"A\"], [\"B\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[([\"A\",\"B\",\"Solid\"]),([\"B\",\"A\",\"Solid\"])]}";
    //public static final String SD_EXAMPLE_5 = "PrimarySD { spiders = [], sh_zones = [], habitats = [], present_zones=[([],[])]}";
	public static final String SD_EXAMPLE_5 = "COP { spiders = [\"s\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [(\"s\", [([\"A\"], [\"B\"])])]}";
	
//	public static final String SD_EXAMPLE_6 = "LUSCOP { spiders = [\"s\",\"t\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
//			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], "
//			+ "arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], spiderLabels = [(\"s\",\"Z\"),(\"t\",\"c\")]}";
//	public static final String SD_EXAMPLE_6_order = "LUSCOP { spiders = [\"s\",\"t\"], "
//			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
//			+ "present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], "
//			+ "spiderLabels = [(\"s\",\"Z\"),(\"t\",\"c\")]}";
//
//	public static final String SD_EXAMPLE_7 = "LUSCOP { spiders = [\"s\",\"t\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
//			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], "
//			+ "arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], spiderLabels = []}";
	
	
	public static final String SD_EXAMPLE_6 = "LUCOP { spiders = [\"s\",\"t\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], "
			+ "arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], spiderLabels = [(\"s\",\"Z\"),(\"t\",\"c\")], "
			+ "curveLabels= [(\"A\",\"Fir\"),(\"B\",\"Sec\")]}";
	
	public static final String SD_EXAMPLE_6_order = "LUCOP { spiders = [\"s\",\"t\"], "
			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
			+ "present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], "
			+ "spiderLabels = [(\"s\",\"Z\"),(\"t\",\"c\")], curveLabels= [(\"A\",\"Fir\"),(\"B\",\"Sec\")]}";

	public static final String SD_EXAMPLE_7 = "LUCOP { spiders = [\"s\",\"t\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], "
			+ "arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], spiderLabels = [],curveLabels= [(\"A\",\"Fir\"),(\"B\",\"Sec\")]}";
	
	
	public static final String SD_EXAMPLE_6_order_car = "LUCarCOP { spiders = [\"s\",\"t\"], "
			+ "habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], "
			+ "present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\",\"has\"),(\"B\",\"A\",\"dashed\")], "
			+ "spiderLabels = [(\"s\",\"Z\"),(\"t\",\"c\")], curveLabels= [(\"A\",\"Fir\"),(\"B\",\"Sec\")], "
			+ "arrowCar = [((\"A\",\"B\",\"solid\",\"has\"),(\">=\",\"2\"))]}";
	
	
//	public static final String SD_EXAMPLE_8 = "LUSCOP { spiders = [\"s\",\"t\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], spiderLabels = [(\"s\",\"Z\")]}";
//
//	public static final String SD_EXAMPLE_9 = "LUSCOP { spiders = [\"s\",\"t\"], sh_zones = [([\"A\"], [\"B\"]),([\"A\",\"B\"],[])], habitats = [(\"s\", [([\"A\"], [\"B\"])]),(\"t\", [([\"B\"], [\"A\"])])], present_zones=[([],[\"A\",\"B\"]),([\"B\"],[\"A\"])], arrows=[(\"A\",\"B\",\"solid\"),(\"B\",\"A\",\"dashed\")], spiderLabels = [(\"s\",\"\")]}";

	public static final String fourty = "LUCarCOP{spiders = [], habitats = [], sh_zones = [([\"P\"], "
			+ "[\"X\",\"Y\",\"Z\"]),([\"X\",\"Y\"],[\"Z\",\"P\"]), ([\"X\",\"Z\"],[\"Y\",\"P\"]),([\"X\",\"P\"],"
			+ "[\"Z\",\"Y\"]),([\"X\",\"Y\",\"Z\"],[\"P\"]),([\"X\",\"Y\",\"P\"],[\"Z\"]),([\"X\",\"P\",\"Z\"],[\"Y\"]), "
					+ "([\"X\",\"Y\",\"Z\",\"P\"],[])], present_zones = [([\"X\"], [\"Y\",\"Z\",\"P\"]), ([\"Y\"], [\"X\",\"Z\",\"P\"])"
					+ ", ([\"Z\"], [\"Y\",\"X\",\"P\"]),([\"Y\",\"Z\"],[\"X\",\"P\"]),([\"Y\",\"P\"],[\"X\",\"Z\"]),"
					+ "([\"P\",\"Z\"],[\"X\",\"Y\"]),([\"Y\",\"Z\",\"P\"],[\"X\"]),([],[\"Y\",\"Z\",\"X\",\"P\"])], "
					+ "arrows=[(\"X\",\"P\",\"dashed\",\"R\")],spiderLabels=[],curveLabels=[(\"X\",\"X\"),(\"Y\",\"Y\"),(\"Z\",\"Z\")], "
							+ "arrowCar=[((\"X\",\"Y\",\"dashed\",\"R\"),(\">=\",\"1\"))]}";
	
	 public COPDiagramReaderTest() {
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
	    	
	    	
	        COPDiagram sd = (COPDiagram) COPDiagramReader.readSpiderDiagram(SD_EXAMPLE_3b);
	        
	        
	        String str1 = sd.toString();
	        COPDiagram sd2 = (COPDiagram) COPDiagramReader.readSpiderDiagram(str1);
	        assertEquals(str1, sd2.toString());
	        
	        assertEquals(sd, sd2);
	        
	        //This one fails
	       //assertTrue(sd == sd2);
	    }


	        
	    @Test
	    public void testReadSpiderDiagram_Reader_LUCOP() throws Exception {
	    	
	    	
	        LUCOPDiagram sd = (LUCOPDiagram) COPDiagramReader.readSpiderDiagram(SD_EXAMPLE_6);
	        
	        
	        String str1 = sd.toString();
	        LUCOPDiagram sd2 = (LUCOPDiagram) COPDiagramReader.readSpiderDiagram(str1);
	        assertEquals(str1, sd2.toString());
	        
	        assertEquals(sd, sd2);
	     
	    }     
	    
	    @Test
	    public void testReadSpiderDiagram_Reader_LUCOP_order() throws Exception {
	    	
	    	
	        LUCOPDiagram sd = (LUCOPDiagram) COPDiagramReader.readSpiderDiagram(SD_EXAMPLE_6_order);
	        
	        
	        String str1 = sd.toString();
	        LUCOPDiagram sd2 = (LUCOPDiagram) COPDiagramReader.readSpiderDiagram(str1);
	        assertEquals(str1, sd2.toString());
	        
	        assertEquals(sd, sd2);
	     
	    }    
	    
	    
	    @Test
	    public void testReadSpiderDiagram_Reader_LUCOP_Empty_SpiderLabels() throws Exception {
	    	
	    	
	        LUCOPDiagram sd = (LUCOPDiagram) COPDiagramReader.readSpiderDiagram(SD_EXAMPLE_7);
	        //assertTrue(sd.isValid());
	        
	        String str1 = sd.toString();
	        LUCOPDiagram sd2 = (LUCOPDiagram) COPDiagramReader.readSpiderDiagram(str1);
	        assertEquals(str1, sd2.toString());
	        
	        assertEquals(sd, sd2);
	     
	    }  
	    
	    
	    @Test
	    public void testReadSpiderDiagram_Reader_LUCarCOP() throws Exception {
	    		    	
	        LUCarCOPDiagram sd = (LUCarCOPDiagram) COPDiagramReader.readSpiderDiagram(SD_EXAMPLE_6_order_car);
	        
	        String str1 = sd.toString();
	        LUCarCOPDiagram sd2 = (LUCarCOPDiagram) COPDiagramReader.readSpiderDiagram(str1);
	        assertEquals(str1, sd2.toString());
	        
	        sd.equals(sd2);
	        assertEquals(sd, sd2);
	        assertThat(sd,equalTo(sd2));
	     
	    }  
	    
	    @Test
	    public void testRead() throws Exception {
	    		    	
	        LUCarCOPDiagram sd = (LUCarCOPDiagram) COPDiagramReader.readSpiderDiagram(fourty);
	        
	        String str1 = sd.toString();
	        LUCarCOPDiagram sd2 = (LUCarCOPDiagram) COPDiagramReader.readSpiderDiagram(str1);
	        assertEquals(str1, sd2.toString());
	        
	        sd.equals(sd2);
	        assertEquals(sd, sd2);
	        assertThat(sd,equalTo(sd2));
	     
	    }  
	    

}
