package speedith.core.reasoning.util.unitary;

import speedith.core.lang.*;
import speedith.core.lang.cop.Arrow;
import speedith.core.lang.cop.COPDiagram;
import speedith.core.lang.cop.Cardinality;
import speedith.core.lang.cop.Comparator;
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.LUCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram;
import speedith.core.lang.cop.SpiderComparator;

//import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.test;

import java.util.*;


public class TestCOPDiagrams {
	
	//Arrow naming convention: small s or d refers to solid or dashed and 
	//the other two characters are source and target of the arrow
	//if the arrow has a label, it will appear last.
	public static final Arrow sAB = new Arrow("A","B","solid");
	public static final Arrow sABhas = new Arrow("A","B","solid","has");
	public static final Arrow dBA = new Arrow("B","A","dashed");
	public static final Arrow dt1B = new Arrow("t1","B","dashed");
	public static final Arrow dt2B = new Arrow("t2","B","dashed");
	public static final Arrow dt1t2 = new Arrow("t1","t2","dashed");
	public static final Arrow dQR = new Arrow("Q","R","dashed");
	public static final Arrow bAB = new Arrow("A","B","bold");
	public static final Arrow dAC = new Arrow("A","C","dashed");
	public static final Arrow sAC = new Arrow("A","C","solid");
	public static final Arrow dAB = new Arrow("A","B","dashed");
	public static final Arrow ds1X = new Arrow("s1","X","solid");
	
	public static TreeSet<Arrow> arrows = new TreeSet<Arrow>();
	
	
	
	public static TreeSet<String> settingSpiderst1(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		return spiders;
	}
	
	public static TreeSet<String> settingSpiderss1(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("s1");
		return spiders;
	}
	
	public static TreeSet<String> settingSpiderst1t2(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		spiders.add("t2");
		return spiders;
	}
	
	public static TreeSet<String> settingSpiderst1t2t3(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("t1");
		spiders.add("t2");
		spiders.add("t3");
		return spiders;
	}
	
	public static TreeSet<String> settingSpiderss1s2(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("s1");
		spiders.add("s2");
		return spiders;
	}
	
	public static TreeSet<String> settingSpidersg1g2(){
		TreeSet<String> spiders = new TreeSet<>();
		spiders.add("g1");
		spiders.add("g2");
		return spiders;
	}
	
	
	public static TreeMap<String, Region> settingHabitats_t1BA_t2AB(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B")));
        return habitats;
	}
	
	public static TreeMap<String, Region> settingHabitats_t1BA(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
        return habitats;
	}
	
	//spider t1 is in X and is outside Y and Z
	public static TreeMap<String, Region> settingHabitats_s1_X_YZ(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("s1", new Region(Zone.fromInContours("X").withOutContours("Y","Z")));
        return habitats;
	}
	
	public static TreeMap<String, Region> settingHabitats_t1_reg_BA_AB(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A"),Zone.fromInContours("A").withOutContours("B")));
        return habitats;
	}
	
	public static TreeMap<String, Region> settingHabitats_t1BA_t2AB_t3AB(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B")));
        habitats.put("t3", new Region(Zone.fromInContours("A").withOutContours("B")));
        return habitats;
	}
	
	public static TreeMap<String, Region> settingHabitats_s1YX_s2XY(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("s1", new Region(Zone.fromInContours("Y").withOutContours("X")));
        habitats.put("s2", new Region(Zone.fromInContours("X").withOutContours("Y")));
        return habitats;
	}
	
	public static TreeMap<String, Region> settingHabitats_g1QP_g2PQ(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("g1", new Region(Zone.fromInContours("Q").withOutContours("P")));
        habitats.put("g2", new Region(Zone.fromInContours("P").withOutContours("Q")));
        return habitats;
	}
	
	//Zohreh: t1 is in a region that includes zone([B],[A,C]) and zone([B,C],[A]) 
	public static TreeMap<String, Region> settingHabitats_t1_reg_B_AC_BC_A_t2_A_BC(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A","C"),Zone.fromInContours("B","C").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B","C")));
        return habitats;
	}
	
	//Zohreh: U refers to the unnamed curve
	public static TreeMap<String, Region> settingHabitats_t1_reg_B_AU_BU_A_t2_A_BU(){
        TreeMap<String, Region> habitats = new TreeMap<>();
        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A",""),Zone.fromInContours("B","").withOutContours("A")));
        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B","")));
        return habitats;
	}
	
//	public static TreeMap<String, Region> settingHabitats4(){
//        TreeMap<String, Region> habitats = new TreeMap<>();
//        habitats.put("t1", new Region(Zone.fromInContours("B").withOutContours("A",""),Zone.fromInContours("B","").withOutContours("A")));
//        habitats.put("t2", new Region(Zone.fromInContours("A").withOutContours("B","")));
//        return habitats;
//	}
	
		
	
	public static TreeSet<Zone> settingShadedZones_A_B(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_X_Y(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("X","Y"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_P_Q(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("P","Q"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_A_B_B_A_AB__(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A").withOutContours("B"));
		shadedZones.add(Zone.fromInContours("B").withOutContours("A"));
		shadedZones.add(Zone.fromInContours("A","B"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_AB_C_ABC__AC_B_C_AB(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours("C"));
		shadedZones.add(Zone.fromInContours("A","B","C"));
		shadedZones.add(Zone.fromInContours("A","C").withOutContours("B"));
		shadedZones.add(Zone.fromInContours("C").withOutContours("A","B"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_AB_C_ABC__AC_B(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours("C"));
		shadedZones.add(Zone.fromInContours("A","B","C"));
		shadedZones.add(Zone.fromInContours("A","C").withOutContours("B"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZonesFourUnC_AB_U_ABU__AU_B(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours(""));
		shadedZones.add(Zone.fromInContours("A","B",""));
		shadedZones.add(Zone.fromInContours("A","").withOutContours("B"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_AB_C_ABC__AC_B_BC_A(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours("C"));
		shadedZones.add(Zone.fromInContours("A","B","C"));
		shadedZones.add(Zone.fromInContours("A","C").withOutContours("B"));
		shadedZones.add(Zone.fromInContours("B","C").withOutContours("A"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_XY_Z_XYZ__XZ_Y_YZ_X(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("X","Y").withOutContours("Z"));
		shadedZones.add(Zone.fromInContours("X","Y","Z"));
		shadedZones.add(Zone.fromInContours("X","Z").withOutContours("Y"));
		shadedZones.add(Zone.fromInContours("Y","Z").withOutContours("X"));
        return shadedZones;
	}
	
	public static TreeSet<Zone> settingShadedZones_AB_C_ABC__AC_B_BC_A_C_AB(){
		TreeSet<Zone> shadedZones = new TreeSet<>();
		shadedZones.add(Zone.fromInContours("A","B").withOutContours("C"));
		shadedZones.add(Zone.fromInContours("A","B","C"));
		shadedZones.add(Zone.fromInContours("A","C").withOutContours("B"));
		shadedZones.add(Zone.fromInContours("B","C").withOutContours("A"));
		shadedZones.add(Zone.fromInContours("C").withOutContours("A","B"));
        return shadedZones;
	}

//	public static TreeSet<Zone> settingShadedZonesSeven(){
//	TreeSet<Zone> shadedZones = new TreeSet<>();
//	shadedZones.add(Zone.fromInContours("A","B").withOutContours(""));
//	shadedZones.add(Zone.fromInContours("A","B",""));
//	shadedZones.add(Zone.fromInContours("A","").withOutContours("B"));
//    return shadedZones;
//}

	public static TreeSet<Zone> settingPresentZones_B_A_A_B__AB(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("B").withOutContours("A"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B"));
		presentZones.add(Zone.fromOutContours("A","B"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones_Y_X_X_Y__XY(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("Y").withOutContours("X"));
		presentZones.add(Zone.fromInContours("X").withOutContours("Y"));
		presentZones.add(Zone.fromOutContours("X","Y"));
        return presentZones;
	}
	
	
	public static TreeSet<Zone> settingPresentZones_Q_P_P_Q__PQ(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromInContours("Q").withOutContours("P"));
		presentZones.add(Zone.fromInContours("P").withOutContours("Q"));
		presentZones.add(Zone.fromOutContours("P","Q"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones__AB(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones__ABC_A_BC_B_AC_BC_A(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B","C"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B","C"));
		presentZones.add(Zone.fromInContours("B").withOutContours("A","C"));
		presentZones.add(Zone.fromInContours("B","C").withOutContours("A"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones__ABC_A_BC_B_AC_BC_A_C_AB(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B","C"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B","C"));
		presentZones.add(Zone.fromInContours("B").withOutContours("A","C"));
		presentZones.add(Zone.fromInContours("B","C").withOutContours("A"));
		presentZones.add(Zone.fromInContours("C").withOutContours("A","B"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones__ABU_A_BU_B_AU_BU_A_U_AB(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B",""));
		presentZones.add(Zone.fromInContours("A").withOutContours("B",""));
		presentZones.add(Zone.fromInContours("B").withOutContours("A",""));
		presentZones.add(Zone.fromInContours("B","").withOutContours("A"));
		presentZones.add(Zone.fromInContours("").withOutContours("A","B"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones__ABC_A_BC_B_AC_C_AB(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B","C"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B","C"));
		presentZones.add(Zone.fromInContours("B").withOutContours("A","C"));
		presentZones.add(Zone.fromInContours("C").withOutContours("A","B"));
        return presentZones;
	}
	
	
	public static TreeSet<Zone> settingPresentZones__XYZ_X_YZ_Y_XZ_Z_XY(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("X","Y","Z"));
		presentZones.add(Zone.fromInContours("X").withOutContours("Y","Z"));
		presentZones.add(Zone.fromInContours("Y").withOutContours("X","Z"));
		presentZones.add(Zone.fromInContours("Z").withOutContours("X","Y"));
        return presentZones;
	}
	
	public static TreeSet<Zone> settingPresentZones__ABC_A_BC_B_AC(){
		TreeSet<Zone> presentZones = new TreeSet<>();
		presentZones.add(Zone.fromOutContours("A","B","C"));
		presentZones.add(Zone.fromInContours("A").withOutContours("B","C"));
		presentZones.add(Zone.fromInContours("B").withOutContours("A","C"));
        return presentZones;
	}
	
//	public static TreeSet<Zone> settingPresentZonesFourUnC(){
//	TreeSet<Zone> presentZones = new TreeSet<>();
//	presentZones.add(Zone.fromOutContours("A","B",""));
//	presentZones.add(Zone.fromInContours("A").withOutContours("B",""));
//	presentZones.add(Zone.fromInContours("B").withOutContours("A",""));
//	presentZones.add(Zone.fromInContours("B","").withOutContours("A"));
//	presentZones.add(Zone.fromInContours("").withOutContours("A","B"));
//    return presentZones;
//}
	
	public static TreeSet<Arrow> settingArrows_sAB_dBA_dt1B(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sAB);
		arrows.add(dBA);
		arrows.add(dt1B);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_dBA_dt1B(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(dBA);
		arrows.add(dt1B);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_sAB_dAC(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sAB);
		arrows.add(dAC);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_sABhas_dAC(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sABhas);
		arrows.add(dAC);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_sAB(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sAB);
		//arrows.add(secondArrow);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_sABhas(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sABhas);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_empty(){
		TreeSet<Arrow> arrows = new TreeSet<>();
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_sAB_sAC(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sAB);
		arrows.add(sAC);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_sAB_dt1t2(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(sAB);
		arrows.add(dt1t2);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_dt1t2(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(dt1t2);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_dt2B(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		//arrows.add(thirdArrow);
		arrows.add(dt2B);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_dAB(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(dAB);
        return arrows;
	}
	
	public static TreeSet<Arrow> settingArrows_ds1X(){
		TreeSet<Arrow> arrows = new TreeSet<>();
		arrows.add(ds1X);
        return arrows;
	}
	
	
	
	
	public static TreeMap<String,String> settingSpiderLabels_t1__(){
		TreeMap<String, String> spiderLabels = new TreeMap<>();
		spiderLabels.put("t1","");
		return spiderLabels;
	}
	
	
	public static TreeMap<String,String> settingSpiderLabels_s1_s1_(){
		TreeMap<String, String> spiderLabels = new TreeMap<>();
		spiderLabels.put("s1","s1");
		return spiderLabels;
	}
	
	public static TreeMap<String,String> settingSpiderLabels_s1_sp_s2__(){
		TreeMap<String, String> spiderLabels = new TreeMap<>();
		spiderLabels.put("s1","sp");
		spiderLabels.put("s2","");
		return spiderLabels;
	}
	
	public static TreeMap<String,String> settingSpiderLabels_g1_sp_g2__(){
		TreeMap<String, String> spiderLabels = new TreeMap<>();
		spiderLabels.put("g1","sp");
		spiderLabels.put("g2","");
		return spiderLabels;
	}
	
	public static TreeMap<String,String> settingSpiderLabels_t1_t1_t2_t2_t3_t3(){
		TreeMap<String, String> spiderLabels = new TreeMap<>();
		spiderLabels.put("t1","t1");
		spiderLabels.put("t2","t2");
		spiderLabels.put("t3","t3");
		return spiderLabels;
	}
	
	
	
	public static TreeMap<String,String> settingCurveLabels_A_First_B_Second(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("A","First");
		curveLabels.put("B","Second");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_A_A_B_B(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("A","A");
		curveLabels.put("B","B");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_X_Red_Y_Blue(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("X","Red");
		curveLabels.put("Y","Blue");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_X__Y_Blue(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("Y","Blue");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_P_Red_Q_Blue(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("P","Red");
		curveLabels.put("Q","Blue");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_P__Q_Blue(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("Q","Blue");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_X_Red_Y_Blue_Z_Yellow(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("X","Red");
		curveLabels.put("Y","Blue");
		curveLabels.put("Z","yellow");
		return curveLabels;
	}
	
	public static TreeMap<String,String> settingCurveLabels_A_First_B_Second_C__(){
		TreeMap<String, String> curveLabels = new TreeMap<>();
		curveLabels.put("A","First");
		curveLabels.put("B","Second");
		curveLabels.put("C","");
		return curveLabels;
	}
	
	
	
	public static TreeMap<Arrow,Cardinality> settingArrowCardinalities_sABhas_geq0(){
		TreeMap<Arrow,Cardinality> arrowCardinalities = new TreeMap<>();
		Cardinality cardinality = new Cardinality(Comparator.Geq, 0);
		arrowCardinalities.put(sABhas, cardinality);
		return arrowCardinalities;
	}
	
	public static TreeMap<Arrow,Cardinality> settingArrowCardinalities_sABhas_geq2(){
		TreeMap<Arrow,Cardinality> arrowCardinalities = new TreeMap<>();
		Cardinality cardinality = new Cardinality(Comparator.Geq, 2);
		arrowCardinalities.put(sABhas, cardinality);
		return arrowCardinalities;
	}
	
	public static TreeMap<Arrow,Cardinality> settingArrowCardinalities_empty(){
		TreeMap<Arrow,Cardinality> arrowCardinalities = new TreeMap<>();
		return arrowCardinalities;
	}
	
	public static TreeSet<SpiderComparator> settingSpiderEquality_t2_t3_unknown(){
		TreeSet<SpiderComparator> spiderComparators = new TreeSet<SpiderComparator>();
		SpiderComparator sc = new SpiderComparator("t2","t3","?");
		spiderComparators.add(sc);
		return spiderComparators;
	}
	
	
	public static COPDiagram one = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), settingArrows_sAB_dBA_dt1B());
//	public static LUSCOPDiagram oneLUS = LUSCOPDiagram.createLUSCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), settingShadedZones_A_B(), 
//			settingPresentZones_B_A_A_B__AB(), settingArrows_sAB_dBA_dt1B(),settingSpiderLabels_t1__());
	public static LUCOPDiagram oneLU = LUCOPDiagram.createLUCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), settingArrows_sAB_dBA_dt1B(),settingSpiderLabels_t1__(), settingCurveLabels_A_First_B_Second());
	
	public static COPDiagram two = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), settingArrows_dBA_dt1B());
	
	public static COPDiagram three = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_C_AB(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A(), settingArrows_sAB_dAC());
	public static COPDiagram threeLabArrow = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_C_AB(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A(), settingArrows_sABhas_dAC());
	public static LUCOPDiagram threeLU = LUCOPDiagram.createLUCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_C_AB(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A(), settingArrows_sAB_dAC(),null,settingCurveLabels_A_First_B_Second());

	public static COPDiagram four = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A_C_AB(), settingArrows_sAB_dAC());
	public static LUCOPDiagram fourLU = LUCOPDiagram.createLUCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A_C_AB(), settingArrows_sAB_dAC(),null,settingCurveLabels_A_First_B_Second());
	
	public static COPDiagram five = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_BC_A(), 
			settingPresentZones__ABC_A_BC_B_AC_C_AB(), settingArrows_sAB_dAC());
	
	public static COPDiagram six = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_BC_A_C_AB(), 
			settingPresentZones__ABC_A_BC_B_AC(), settingArrows_sAB_dAC());

	public static final COPDiagram seven = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_sAB());
	public static final COPDiagram sevenLabArrow = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), settingArrows_sABhas());
	public static final LUCarCOPDiagram sevenLabArrowLUCarCOP = LUCarCOPDiagram.createLUCarCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_sABhas(), settingSpiderLabels_t1__(), 
			settingCurveLabels_A_First_B_Second(),settingArrowCardinalities_sABhas_geq0());
	public static final CompleteCOPDiagram sevenLabArrowCompCOP = SpiderDiagrams.createCompleteCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_sABhas(), settingSpiderLabels_t1__(), 
			settingCurveLabels_A_First_B_Second(),settingArrowCardinalities_sABhas_geq0(),null);
	public static final LUCarCOPDiagram sevenLabArrowLUCarCOP2 = LUCarCOPDiagram.createLUCarCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_sABhas(), settingSpiderLabels_t1__(), 
			settingCurveLabels_A_First_B_Second(),settingArrowCardinalities_sABhas_geq2());
	
	public static final LUCarCOPDiagram alternativeLUCarCOP = LUCarCOPDiagram.createLUCarCOPDiagram(settingSpiderss1s2(), settingHabitats_s1YX_s2XY(), 
			settingShadedZones_X_Y(), settingPresentZones_Y_X_X_Y__XY(), settingArrows_empty(), settingSpiderLabels_s1_sp_s2__(), 
			settingCurveLabels_X_Red_Y_Blue(),settingArrowCardinalities_empty());
	public static final CompleteCOPDiagram alternativeCompCOP = SpiderDiagrams.createCompleteCOPDiagram(settingSpiderss1s2(), settingHabitats_s1YX_s2XY(), 
			settingShadedZones_X_Y(), settingPresentZones_Y_X_X_Y__XY(), settingArrows_empty(), settingSpiderLabels_s1_sp_s2__(), 
			settingCurveLabels_X_Red_Y_Blue(),settingArrowCardinalities_empty(),null);
	public static final CompleteCOPDiagram alternativeCompCOPXUnLab = SpiderDiagrams.createCompleteCOPDiagram(settingSpiderss1s2(), settingHabitats_s1YX_s2XY(), 
			settingShadedZones_X_Y(), settingPresentZones_Y_X_X_Y__XY(), settingArrows_empty(), settingSpiderLabels_s1_sp_s2__(), 
			settingCurveLabels_X__Y_Blue(),settingArrowCardinalities_empty(),null);
	public static final CompleteCOPDiagram alternativeCompCOPDiffLabels = SpiderDiagrams.createCompleteCOPDiagram(settingSpidersg1g2(), 
			settingHabitats_g1QP_g2PQ(),settingShadedZones_P_Q(), settingPresentZones_Q_P_P_Q__PQ(), settingArrows_empty(), 
			settingSpiderLabels_g1_sp_g2__(), settingCurveLabels_P_Red_Q_Blue(),settingArrowCardinalities_empty(),null);
	public static final CompleteCOPDiagram alternativeCompCOPPUnLab= SpiderDiagrams.createCompleteCOPDiagram(settingSpidersg1g2(), 
			settingHabitats_g1QP_g2PQ(),settingShadedZones_P_Q(), settingPresentZones_Q_P_P_Q__PQ(), settingArrows_empty(), 
			settingSpiderLabels_g1_sp_g2__(), settingCurveLabels_P__Q_Blue(),settingArrowCardinalities_empty(),null);
	
	
	
	public static final LUCarCOPDiagram threeColLUCarCOP = LUCarCOPDiagram.createLUCarCOPDiagram(null, null, 
			settingShadedZones_XY_Z_XYZ__XZ_Y_YZ_X(), settingPresentZones__XYZ_X_YZ_Y_XZ_Z_XY(), settingArrows_empty(), null, 
			settingCurveLabels_X_Red_Y_Blue_Z_Yellow(),settingArrowCardinalities_empty());
	
	public static final LUCarCOPDiagram threeColSpLUCarCOP = LUCarCOPDiagram.createLUCarCOPDiagram(settingSpiderss1(), 
			settingHabitats_s1_X_YZ(), settingShadedZones_XY_Z_XYZ__XZ_Y_YZ_X(), settingPresentZones__XYZ_X_YZ_Y_XZ_Z_XY(), 
			settingArrows_empty(), settingSpiderLabels_s1_s1_(), settingCurveLabels_X_Red_Y_Blue_Z_Yellow(),
			settingArrowCardinalities_empty());
	public static final CompleteCOPDiagram threeColSpCompCOP = SpiderDiagrams.createCompleteCOPDiagram(settingSpiderss1(), 
			settingHabitats_s1_X_YZ(), settingShadedZones_XY_Z_XYZ__XZ_Y_YZ_X(), settingPresentZones__XYZ_X_YZ_Y_XZ_Z_XY(), 
			settingArrows_empty(), settingSpiderLabels_s1_s1_(), settingCurveLabels_X_Red_Y_Blue_Z_Yellow(),
			settingArrowCardinalities_empty(),null);
	public static final CompleteCOPDiagram threeColSpCompCOPWithArrow = SpiderDiagrams.createCompleteCOPDiagram(settingSpiderss1(), 
			settingHabitats_s1_X_YZ(), settingShadedZones_XY_Z_XYZ__XZ_Y_YZ_X(), settingPresentZones__XYZ_X_YZ_Y_XZ_Z_XY(), 
			settingArrows_ds1X(), settingSpiderLabels_s1_s1_(), settingCurveLabels_X_Red_Y_Blue_Z_Yellow(),
			settingArrowCardinalities_empty(),null);
	
	
	public static COPDiagram eight = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_BC_A(), 
			settingPresentZones__ABC_A_BC_B_AC_C_AB(), settingArrows_sAB_sAC());
	
	public static COPDiagram nine = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B_BC_A_C_AB(), 
			settingPresentZones__ABC_A_BC_B_AC(), settingArrows_sAB_sAC());
	
	public static COPDiagram ten = COPDiagram.createCOPDiagram(settingSpiderst1t2t3(), settingHabitats_t1BA_t2AB_t3AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_sAB_dt1t2());
	public static CompleteCOPDiagram tenComplete = SpiderDiagrams.createCompleteCOPDiagram(settingSpiderst1t2t3(),
			settingHabitats_t1BA_t2AB_t3AB(), settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), 
			settingArrows_sAB_dt1t2(), settingSpiderLabels_t1_t1_t2_t2_t3_t3(), settingCurveLabels_A_A_B_B(), 
			null, settingSpiderEquality_t2_t3_unknown());
	
	public static COPDiagram eleven = COPDiagram.createCOPDiagram(settingSpiderst1t2t3(), settingHabitats_t1BA_t2AB_t3AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_dt1t2());
	
	public static LUCOPDiagram twelve = LUCOPDiagram.createLUCOPDiagram(null, null, settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), settingArrows_sAB(),null,settingCurveLabels_A_First_B_Second());

	public static COPDiagram thirteen = COPDiagram.createCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A_C_AB(), settingArrows_sAB());
	public static COPDiagram thirteenUnC = COPDiagram.createCOPDiagram(null, null, settingShadedZonesFourUnC_AB_U_ABU__AU_B(), 
			settingPresentZones__ABU_A_BU_B_AU_BU_A_U_AB(), settingArrows_sAB());
	public static LUCOPDiagram thirteenLU = LUCOPDiagram.createLUCOPDiagram(null, null, settingShadedZones_AB_C_ABC__AC_B(), 
			settingPresentZones__ABC_A_BC_B_AC_BC_A_C_AB(), settingArrows_sAB(), null,settingCurveLabels_A_First_B_Second_C__());

//This is identical to seven	
//	public static COPDiagram fourtheen = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
//			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_sAB());

	public static COPDiagram fifteen = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1_reg_B_AC_BC_A_t2_A_BC(), 
			settingShadedZones_AB_C_ABC__AC_B(), settingPresentZones__ABC_A_BC_B_AC_BC_A_C_AB(), settingArrows_sAB());
	public static COPDiagram fifteenUnC = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1_reg_B_AU_BU_A_t2_A_BU(), 
			settingShadedZonesFourUnC_AB_U_ABU__AU_B(), settingPresentZones__ABU_A_BU_B_AU_BU_A_U_AB(), settingArrows_sAB());

	public static COPDiagram sixteen = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_dt2B());
	
// This is identical to fifteenUnC.
//	public static COPDiagram seventeen = COPDiagram.createCOPDiagram(settingSpiderst1t2(), settingHabitats_t1_reg_B_AU_BU_A_t2_A_BU(), 
//			settingShadedZonesFourUnC_AB_U_ABU__AU_B(), settingPresentZones__ABU_A_BU_B_AU_BU_A_U_AB(), settingArrows_sAB());
	
	public static COPDiagram eighteen = COPDiagram.createCOPDiagram(null, null, settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), arrows);
	public static COPDiagram eighteenSp = COPDiagram.createCOPDiagram(settingSpiderst1(), settingHabitats_t1BA(), settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), arrows);
	public static COPDiagram eighteenSp2 = COPDiagram.createCOPDiagram(settingSpiderst1(), settingHabitats_t1_reg_BA_AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), arrows);
	public static LUCOPDiagram eighteenLU = LUCOPDiagram.createLUCOPDiagram(null, null, settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), arrows,null,settingCurveLabels_A_First_B_Second());
	public static COPDiagram eighteenSpLU = LUCOPDiagram.createLUCOPDiagram(settingSpiderst1(), settingHabitats_t1BA(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), arrows, settingSpiderLabels_t1__(), settingCurveLabels_A_First_B_Second());

	public static LUCOPDiagram nineteen = LUCOPDiagram.createLUCOPDiagram(null, null, settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), settingArrows_dAB(),null,settingCurveLabels_A_First_B_Second());

	public static LUCOPDiagram twenty = LUCOPDiagram.createLUCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), settingShadedZones_A_B(), 
			settingPresentZones_B_A_A_B__AB(), settingArrows_dt1t2(),settingSpiderLabels_t1__(),settingCurveLabels_A_First_B_Second());
	
	public static LUCOPDiagram twentyOne = LUCOPDiagram.createLUCOPDiagram(settingSpiderst1t2(), settingHabitats_t1BA_t2AB(), 
			settingShadedZones_A_B(), settingPresentZones_B_A_A_B__AB(), arrows,settingSpiderLabels_t1__(),settingCurveLabels_A_First_B_Second());

	public static COPDiagram twentyTwo = COPDiagram.createCOPDiagram(null,null, settingShadedZones_A_B_B_A_AB__(), settingPresentZones__AB(),null);
	
	
	public static CompoundSpiderDiagram andTwelveOne= SpiderDiagrams.createCompoundSD(Operator.Conjunction, twelve, one);
	
	public static final String B2XY = "BinarySD{operator = \"op &\", arg1=PrimarySD{spiders = [], habitats = [], sh_zones = [([\"X\"], [\"Y\"])], "
			+ "present_zones = [([\"Y\",\"X\"], []), ([\"Y\"], [\"X\"]), ([], [\"Y\",\"X\"])]},arg2=PrimarySD{spiders = [], habitats = [], "
					+ "sh_zones = [([\"X\",\"Y\"], [])], present_zones = [([\"Y\"], [\"X\"]), ([\"X\"], [\"Y\"]), ([], [\"Y\",\"X\"])]}}";
	
	public static final String B2XYEmpX = "BinarySD{operator = \"op &\", arg1=PrimarySD{spiders = [], habitats = [], "
			+ "sh_zones = [([\"Y\",\"X\"], []),([\"X\"], [\"Y\"])], present_zones = [([\"Y\"], [\"X\"]), ([], [\"Y\",\"X\"])]},"
			+ "arg2=PrimarySD{spiders = [], habitats = [], sh_zones = [([\"X\",\"Y\"], []), ([\"X\"], [\"Y\"])], "
			+ "present_zones = [([\"Y\"], [\"X\"]), ([], [\"Y\",\"X\"])]}}";
	
	public static final String B2XYSwap = "BinarySD{operator = \"op &\", arg1=PrimarySD{spiders = [], habitats = [], "
			+ "sh_zones = [([\"X\",\"Y\"], [])], present_zones = [([\"Y\"], [\"X\"]), ([\"X\"], [\"Y\"]), ([], [\"Y\",\"X\"])]}, "
			+ "arg2=PrimarySD{spiders = [], habitats = [], sh_zones = [([\"X\"], [\"Y\"])], "
			+ "present_zones = [([\"Y\",\"X\"], []), ([\"Y\"], [\"X\"]), ([], [\"Y\",\"X\"])]}}";
	
	public static final String Fourty = "LUCarCOP{spiders = [], habitats = [], sh_zones = [([\"P\"], "
			+ "[\"X\",\"Y\",\"Z\"]),([\"X\",\"Y\"],[\"Z\",\"P\"]), ([\"X\",\"Z\"],[\"Y\",\"P\"]),([\"X\",\"P\"],"
			+ "[\"Z\",\"Y\"]),([\"X\",\"Y\",\"Z\"],[\"P\"]),([\"X\",\"Y\",\"P\"],[\"Z\"]),([\"X\",\"P\",\"Z\"],[\"Y\"]), "
					+ "([\"X\",\"Y\",\"Z\",\"P\"],[])], present_zones = [([\"X\"], [\"Y\",\"Z\",\"P\"]), ([\"Y\"], [\"X\",\"Z\",\"P\"])"
					+ ", ([\"Z\"], [\"Y\",\"X\",\"P\"]),([\"Y\",\"Z\"],[\"X\",\"P\"]),([\"Y\",\"P\"],[\"X\",\"Z\"]),"
					+ "([\"P\",\"Z\"],[\"X\",\"Y\"]),([\"Y\",\"Z\",\"P\"],[\"X\"]),([],[\"Y\",\"Z\",\"X\",\"P\"])], "
					+ "arrows=[(\"X\",\"P\",\"dashed\",\"R\")],spiderLabels=[],curveLabels=[(\"X\",\"X\"),(\"Y\",\"Y\"),(\"Z\",\"Z\")], "
							+ "arrowCar=[((\"X\",\"P\",\"dashed\",\"R\"),(\">=\",\"1\"))]}";
	
	
	
	
	


					
					
}
