package speedith.core.reasoning.util.unitary;

import java.util.TreeMap;
import java.util.TreeSet;

import speedith.core.lang.Arrow;
import speedith.core.lang.Cardinality;
import speedith.core.lang.Comparator;
import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.SpiderDiagrams;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;

public class TestConceptDiagrams {
	
	public static TreeSet<Arrow> oneArrows = new TreeSet<Arrow>();
	public static final Arrow st1s1R = new Arrow("t1","s1","solid","R");
	public static final Arrow sAXR = new Arrow("A","X","solid","R");
	
	public static TreeSet<Arrow> setArrowsOne(){
		TreeSet<Arrow> arrows = new TreeSet<Arrow>();
		arrows.add(new Arrow("B","X","solid","R"));
		return arrows;
	}
	
	public static TreeSet<Arrow> setArrowsTwo(){
		TreeSet<Arrow> arrows = new TreeSet<Arrow>();
		arrows.add(new Arrow("B","X","solid","R"));
		arrows.add(new Arrow("A","Z","solid","R"));
		return arrows;
	}
	
	public static TreeSet<Arrow> setArrowsThree(){
		TreeSet<Arrow> arrows = new TreeSet<Arrow>();
		//arrows.add(new Arrow("B","X","solid","R"));
		//arrows.add(new Arrow("s1","Z","solid","R"));
		arrows.add(st1s1R);
//		Cardinality cardinality = new Cardinality(Comparator.Geq, 0);
//		st1s1R.setCardinality(cardinality);
		return arrows;
	}
	
	public static TreeSet<Arrow> setArrowsFour(){
		TreeSet<Arrow> arrows = new TreeSet<Arrow>();
		arrows.add(new Arrow("A","X","solid","R"));
		return arrows;
	}
	
	public static TreeMap<Arrow,Cardinality> settingArrowCardinalities_st1s1R_geq0(){
		TreeMap<Arrow,Cardinality> arrowCardinalities = new TreeMap<>();
		Cardinality cardinality = new Cardinality(Comparator.Geq, 0);
		arrowCardinalities.put(st1s1R, cardinality);
		return arrowCardinalities;
	}
	
	public static TreeMap<Arrow,Cardinality> settingArrowCardinalities_sAXR_geq0(){
		TreeMap<Arrow,Cardinality> arrowCardinalities = new TreeMap<>();
		Cardinality cardinality = new Cardinality(Comparator.Geq, 0);
		arrowCardinalities.put(sAXR, cardinality);
		return arrowCardinalities;
	}
	
	public static ConceptDiagram oneCD = SpiderDiagrams.createConceptDiagramNoText(oneArrows, sevenLabArrowLUCarCOP,sevenLabArrowLUCarCOP2);
	public static ConceptDiagram twoCD = SpiderDiagrams.createConceptDiagramNoText(setArrowsOne(), sevenLabArrowLUCarCOP,alternativeLUCarCOP);
	public static ConceptDiagram threeCD = SpiderDiagrams.createConceptDiagramNoText(setArrowsTwo(), sevenLabArrowLUCarCOP,threeColLUCarCOP);
	public static ConceptDiagram fourCD = SpiderDiagrams.createConceptDiagramNoText(setArrowsThree(), sevenLabArrowLUCarCOP,threeColSpLUCarCOP);
	public static ConceptDiagram fourCarCD = SpiderDiagrams.createCarCDiagramNoText(setArrowsThree(), settingArrowCardinalities_st1s1R_geq0(),
			sevenLabArrowLUCarCOP,threeColSpLUCarCOP);
	public static ConceptDiagram fiveCarCD = SpiderDiagrams.createCarCDiagramNoText(setArrowsFour(), settingArrowCardinalities_sAXR_geq0(),
			sevenLabArrowLUCarCOP,threeColSpLUCarCOP);

	

}
