package speedith.ui;

import icircles.abstractDescription.*;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.util.CannotDrawException;
import java.util.Map.Entry;
import java.util.*;
import javax.swing.JPanel;
import speedith.core.lang.*;

import static speedith.i18n.Translations.i18n;

public class COPDiagramVisualisation {
    
    private static final int DefaultDiagramSize = 500;

    public COPDiagramVisualisation() {
    }
 

    public static AbstractDescription getAbstractDescription(PrimarySpiderDiagram psd) throws CannotDrawException {

        if (psd == null || !psd.isValid()) {
            throw new CannotDrawException(i18n("DRAW_NOT_VALID_PSD"));
        }
        
        SortedSet<String> contourStrings = psd.getAllContours();
        
        //Extra
        SortedSet<String> spiderStrings = psd.getSpiders();
        TreeSet<AbstractSpider> spiders = new TreeSet<>();
        HashMap<String, AbstractSpider> spiderMap = new HashMap<>();

        if (contourStrings.size() > 10) {
            throw new CannotDrawException(i18n("TOO_MANY_CONTOURS"));
        }

        HashMap<String, AbstractCurve> contourMap = new HashMap<>();
        TreeSet<AbstractCurve> contours = new TreeSet<>();
        TreeSet<AbstractBasicRegion> shadedHabitatZones = new TreeSet<>();
        TreeSet<AbstractBasicRegion> allVisibleZones = new TreeSet<>();

        String[] allContours = contourStrings.toArray(new String[contourStrings.size()]);

  
        int allZonesCount = 1 << contourStrings.size();
    
        byte[] allZones = new byte[allZonesCount];
        final byte IsShaded = 1;
        final byte IsInHabitat = 2;
        final byte IsPresent = 4;

        
        for (String contour : contourStrings) {
        	if (psd instanceof LUCOPDiagram){
        		LUCOPDiagram lucop = (LUCOPDiagram) psd;
                final AbstractCurve abstractCurve = new AbstractCurve(contour,lucop.getCurveLabels().get(contour));
                contourMap.put(contour, abstractCurve);
                contours.add(abstractCurve);
        	}else{
                final AbstractCurve abstractCurve = new AbstractCurve(contour);
                contourMap.put(contour, abstractCurve);
                contours.add(abstractCurve);
        	}
        }
        
        
        

        if (psd.getHabitatsCount() > 0) {
            for (Region region : psd.getHabitats().values()) {
                for (Zone zone : region.sortedZones()) {
                    markZone(allZones, allContours, zone, IsInHabitat);
                }
            }
        }

        if (psd.getPresentZonesCount() > 0) {
            for (Zone zone : psd.getPresentZones()) {
                markZone(allZones, allContours, zone, IsPresent);
            }
        }

        if (psd.getShadedZonesCount() > 0) {
            for (Zone zone : psd.getShadedZones()) {
                markZone(allZones, allContours, zone, IsShaded);
            }
        }

        for (int i = 0; i < allZones.length; i++) {
            byte b = allZones[i];
            if ((b & IsShaded) == 0) {
                final AbstractBasicRegion abr = constructABR(allContours, i, contourMap);
                allVisibleZones.add(abr);
            } else if ((b & IsInHabitat) != 0 || (b & IsPresent) != 0) {
                final AbstractBasicRegion abr = constructABR(allContours, i, contourMap);
                allVisibleZones.add(abr);
                shadedHabitatZones.add(abr);
            }
        }
        
        AbstractDescription ad = new AbstractDescription(contours, allVisibleZones, shadedHabitatZones);
        //ad.putContourMap(contourMap);
        COPAbstractDescription copad = new COPAbstractDescription(contours, allVisibleZones, shadedHabitatZones);   
        copad.putContourMap(contourMap);
        
        if (psd.getHabitatsCount() > 0) {
            SortedMap<String, Region> habitats = psd.getHabitats();
            for (Entry<String, Region> habitat : habitats.entrySet()) {
                TreeSet<AbstractBasicRegion> feet = new TreeSet<>();
                for (Zone foot : habitat.getValue().sortedZones()) {
                    addFeetForZone(feet, contourMap, foot);
                }
                	if (psd instanceof LUCOPDiagram){
                	LUCOPDiagram lucop = (LUCOPDiagram) psd;
                    final AbstractSpider abstractSpider = 
                    		new AbstractSpider(feet, habitat.getKey(), lucop.getSpiderLabels().get(habitat.getKey()));
                    //new AbstractSpider(feet, habitat.getKey(), lucop.getSpiderLabels().get(habitat.getKey()));
                    
                    spiderMap.put(habitat.getKey(),abstractSpider);
                    
                    spiders.add(abstractSpider);
                    
                    ad.addSpider(abstractSpider);
                    copad.addSpider(abstractSpider);  
                	
                }else{
                    final AbstractSpider abstractSpider = new AbstractSpider(feet, habitat.getKey());
                    spiderMap.put(habitat.getKey(),abstractSpider);
                    
                    spiders.add(abstractSpider);
                    
                    ad.addSpider(abstractSpider);
                    copad.addSpider(abstractSpider);  	
                }
            }
        }
        
        copad.putSpiderMap(spiderMap);
         
        if (psd instanceof COPDiagram) {
        	COPDiagram cop = (COPDiagram) psd;
        	if (cop.getArrowsCount() > 0) {
        		TreeSet<Arrow> arrows = cop.getArrowsMod();
        		TreeSet<AbstractArrow> allAbsArrows = new TreeSet<AbstractArrow>();
        		
        		abstractArrowCreator(contourStrings, spiderStrings, spiderMap, contourMap, cop, arrows, allAbsArrows);
        		
        		copad.addAllArrows(allAbsArrows);
        	}
        	
        	
        	return copad;
        	} 
        else{
        	return ad;
        }
        
    }


	private static void abstractArrowCreator(SortedSet<String> contourStrings, SortedSet<String> spiderStrings,
			HashMap<String, AbstractSpider> spiderMap, HashMap<String, AbstractCurve> contourMap, SpiderDiagram cop,
			TreeSet<Arrow> arrows, TreeSet<AbstractArrow> allAbsArrows) {
		for (Arrow arrow : arrows){
			if (contourStrings.contains(arrow.arrowSource()) && contourStrings.contains(arrow.arrowTarget())){
		  		AbstractCurve as = contourMap.get(arrow.arrowSource());
				AbstractCurve at = contourMap.get(arrow.arrowTarget());
				AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
				//copad.addArrow(aa);
				allAbsArrows.add(aa);
				if (cop instanceof LUCarCOPDiagram){
					LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) cop;
					Cardinality cardinality = luCarCop.getArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}    	
				if (cop instanceof CarCDiagram){
					CarCDiagram carCd = (CarCDiagram) cop;
					Cardinality cardinality = carCd.get_cd_ArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
			}
			
			if (spiderStrings.contains(arrow.arrowSource()) && spiderStrings.contains(arrow.arrowTarget())){
		  		AbstractSpider as = spiderMap.get(arrow.arrowSource());
				AbstractSpider at = spiderMap.get(arrow.arrowTarget());
				AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
				//copad.addArrow(aa);
				allAbsArrows.add(aa);
		   		if (cop instanceof LUCarCOPDiagram){
					LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) cop;
					Cardinality cardinality = luCarCop.getArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
				if (cop instanceof CarCDiagram){
					CarCDiagram carCd = (CarCDiagram) cop;
					Cardinality cardinality = carCd.get_cd_ArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
		   		
			}
			
			if (contourStrings.contains(arrow.arrowSource()) && spiderStrings.contains(arrow.arrowTarget())){
		  		AbstractCurve as = contourMap.get(arrow.arrowSource());
		  		AbstractSpider at = spiderMap.get(arrow.arrowTarget());
				AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
//                		copad.addArrow(aa);
				allAbsArrows.add(aa);
		   		if (cop instanceof LUCarCOPDiagram){
					LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) cop;
					Cardinality cardinality = luCarCop.getArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
				if (cop instanceof CarCDiagram){
					CarCDiagram carCd = (CarCDiagram) cop;
					Cardinality cardinality = carCd.get_cd_ArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
			}
			
			if (spiderStrings.contains(arrow.arrowSource()) && contourStrings.contains(arrow.arrowTarget())){
		  		AbstractSpider as = spiderMap.get(arrow.arrowSource());
		  		AbstractCurve at = contourMap.get(arrow.arrowTarget());
				AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
//                		copad.addArrow(aa);
				allAbsArrows.add(aa);
		   		if (cop instanceof LUCarCOPDiagram){
					LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) cop;
					Cardinality cardinality = luCarCop.getArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
				if (cop instanceof CarCDiagram){
					CarCDiagram carCd = (CarCDiagram) cop;
					Cardinality cardinality = carCd.get_cd_ArrowCardinalities().get(arrow);
					aa.setCardinality(cardinality);
				}
			}
		}
	}
    
    
    
    public static CDAbstractDescription getAbstractDescription(ConceptDiagram cd) throws CannotDrawException {
    	
        if (cd == null || !cd.isValid()) {
            throw new CannotDrawException(i18n("DRAW_NOT_VALID_PSD"));
        }
        
        ArrayList<COPAbstractDescription> abstractPrimaries = new ArrayList<>();
        SortedSet<String> contourStringsAll = new TreeSet<String>();
        SortedSet<String> spiderStringsAll = new TreeSet<String>();
        HashMap<String, AbstractCurve> contourMapAll = new HashMap<String, AbstractCurve>();
        HashMap<String, AbstractSpider> spiderMapAll = new HashMap<String, AbstractSpider>();
        
        
    	for (PrimarySpiderDiagram psd : cd.getPrimaries()){
    		abstractPrimaries.add((COPAbstractDescription) getAbstractDescription(psd));
    		contourMapAll.putAll(((COPAbstractDescription) getAbstractDescription(psd)).getContourMap());
    		spiderMapAll.putAll(((COPAbstractDescription) getAbstractDescription(psd)).getSpiderMap());
    		contourStringsAll.addAll(psd.getAllContours());
    		spiderStringsAll.addAll(psd.getSpiders());
    	}
    	
    	
    	CDAbstractDescription cdad = new CDAbstractDescription(abstractPrimaries);
    	
    	if (cd.getArrowCount() > 0){
    		TreeSet<Arrow> arrows = cd.get_cd_ArrowsMod();
    		TreeSet<AbstractArrow> allAbsArrows = new TreeSet<AbstractArrow>();
    		
    		abstractArrowCreator(contourStringsAll, spiderStringsAll, spiderMapAll, contourMapAll, cd, arrows, allAbsArrows);

//    		for (Arrow arrow: arrows){
//    			if (contourStringsAll.contains(arrow.arrowSource()) && contourStringsAll.contains(arrow.arrowTarget())){
//    				AbstractCurve as = contourMapAll.get(arrow.arrowSource());
//            		AbstractCurve at = contourMapAll.get(arrow.arrowTarget());
//            		AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
//            		cdad.addArrow(aa);
//    			}
//    			if (spiderStringsAll.contains(arrow.arrowSource()) && spiderStringsAll.contains(arrow.arrowTarget())){
//              		AbstractSpider as = spiderMapAll.get(arrow.arrowSource());
//            		AbstractSpider at = spiderMapAll.get(arrow.arrowTarget());
//            		AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
//            		cdad.addArrow(aa);
//    			}
//    			if (contourStringsAll.contains(arrow.arrowSource()) && spiderStringsAll.contains(arrow.arrowTarget())){
//              		AbstractCurve as = contourMapAll.get(arrow.arrowSource());
//              		AbstractSpider at = spiderMapAll.get(arrow.arrowTarget());
//            		AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
//            		cdad.addArrow(aa);
//    		    }
//    			if (spiderStringsAll.contains(arrow.arrowSource()) && contourStringsAll.contains(arrow.arrowTarget())){
//              		AbstractSpider as = spiderMapAll.get(arrow.arrowSource());
//              		AbstractCurve at = contourMapAll.get(arrow.arrowTarget());              		
//            		AbstractArrow aa = new AbstractArrow(as,at,arrow.arrowType(),arrow.arrowLabel());
//            		cdad.addArrow(aa);
//    			}	
//    		}
    	cdad.addAllArrows(allAbsArrows);
    	}
    	return cdad;
    }
    
  
    

    /**
     * Creates a {@link ConcreteDiagram concrete diagram} from the given {@link 
     * PrimarySpiderDiagram primary spider diagram}.
     * @param diagram the primary spider diagram from which to create its concrete
     * representation.
     * @return the concrete representation of the given primary spider diagram.
     * @throws CannotDrawException this exception is thrown if the concrete
     * spider diagram could not have been created for whatever reason.
     */
    public static ConcreteDiagram getConcreteDiagram(PrimarySpiderDiagram diagram) throws CannotDrawException {
        return ConcreteCOPDiagram.makeConcreteDiagram(getAbstractDescription(diagram), DefaultDiagramSize);
    }
    
    public static ConcreteCDiagram getConcreteDiagram(ConceptDiagram diagram) throws CannotDrawException {
        return ConcreteCDiagram.makeConcreteDiagram(getAbstractDescription(diagram), DefaultDiagramSize);
    }
    

    /**
     * Creates a panel which is showing the given spider diagram.
     * @param sd the spider diagram to draw.
     * @return the panel which displays the given spider diagram.
     * @throws CannotDrawException this exception is thrown if the diagram cannot
     * be drawn for any reason.
     */
    public static JPanel getSpiderDiagramPanel(SpiderDiagram sd) throws CannotDrawException {
        return getSpiderDiagramPanel(sd, DefaultDiagramSize);
    }

    /**
     * Creates a panel which is showing the given primary spider diagram.
     * @param sd the primary spider diagram to draw.
     * @return the panel which displays the given primary spider diagram.
     * @throws CannotDrawException this exception is thrown if the diagram cannot
     * be drawn for any reason.
     */
    public static SpeedithCirclesPanel getSpiderDiagramPanel(PrimarySpiderDiagram sd) throws CannotDrawException {
        return getSpiderDiagramPanel(sd, DefaultDiagramSize);
    }

    /**
     * Creates a panel which is showing the given spider diagram.
     * @param sd the spider diagram to draw.
     * @param size the size of the diagram panel (the drawn spider diagram).
     * @return the panel which displays the given spider diagram.
     * @throws CannotDrawException this exception is thrown if the diagram cannot
     * be drawn for any reason.
     */
    //Zohreh: compound now returns a COPDiagramPanel
    public static JPanel getSpiderDiagramPanel(SpiderDiagram sd, int size) throws CannotDrawException {
        if (sd == null) {
            throw new IllegalArgumentException(i18n("GERR_NULL_ARGUMENT", "sd"));
        } else {
        	if(sd instanceof ConceptDiagram){
        		//Zohreh: Doesn't matter which one I use, the result is the same. 
        		return getSpiderDiagramPanel((ConceptDiagram) sd, size);
        		//return new COPDiagramPanel((ConceptDiagram) sd);
        	} else
            if (sd instanceof PrimarySpiderDiagram) {
                //return getSpiderDiagramPanel((PrimarySpiderDiagram) sd, size);
                return new COPDiagramPanel((PrimarySpiderDiagram) sd);
            } else if (sd instanceof CompoundSpiderDiagram) {
                //return new SpiderDiagramPanel(sd);
            	return new COPDiagramPanel(sd);
            } else if (sd instanceof NullSpiderDiagram) {
                return new NullSpiderDiagramPanel();
            } else {
                throw new AssertionError(i18n("GERR_ILLEGAL_STATE"));
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Helper Methods">
    /**
     * This method assigns the given code to the given zone in the 'allZones'
     * array.
     * <p>This method effectively flags the zone with the given code.</p>
     */
    private static void markZone(byte[] allZones, String[] allContours, Zone zone, byte code) {
        // We have to mark the zone with the given code. But how do we determine
        // the index of the zone?
        // Well, say we have N contours. Then a zone is specified with N bits,
        // where 1 means that the zone is inside that contour and 0 means that
        // the zone is outside.
        // The resulting number is the index of the zone.
        allZones[getZoneInMask(allContours, zone)] |= code;
    }

    static SpeedithCirclesPanel getSpiderDiagramPanel(AbstractDescription ad, int size) throws CannotDrawException {
        ConcreteDiagram cd = ConcreteDiagram.makeConcreteDiagram(ad, size);
        return new SpeedithCirclesPanel(cd);
    }

    static SpeedithCirclesPanel getSpiderDiagramPanel(PrimarySpiderDiagram diagram, int size) throws CannotDrawException {
        final AbstractDescription ad = getAbstractDescription(diagram);
        ConcreteDiagram cd = ConcreteCOPDiagram.makeConcreteDiagram(ad, size);
        return new SpeedithCirclesPanel(cd);
    }
    
    
    //static SpeedithCirclesPanel getSpiderDiagramPanel(ConceptDiagram diagram, int size) throws CannotDrawException {
    static CDCirclesPanelEx getSpiderDiagramPanel(ConceptDiagram diagram, int size) throws CannotDrawException {
    	//System.out.println("all should be fine now");
    	final CDAbstractDescription ad = getAbstractDescription(diagram);
        ConcreteCDiagram cd = ConcreteCDiagram.makeConcreteDiagram(ad, size);
        //return new SpeedithCirclesPanel(cd);
        return new CDCirclesPanelEx(cd);
    }
    
    

    private static int getZoneInMask(String[] allContours, Zone zone) {
        int mask = 0;
        if (zone.getInContoursCount() > 0) {
            for (String inContour : zone.getInContours()) {
                int contourIndex = Arrays.binarySearch(allContours, inContour);
                mask |= (1 << contourIndex);
            }
        }
        return mask;
    }

    private static AbstractBasicRegion constructABR(String[] allContours, int zoneIndex, HashMap<String, AbstractCurve> contourMap) {
        TreeSet<AbstractCurve> inContours = new TreeSet<>();
        for (int i = 0; i < allContours.length; i++) {
            if ((zoneIndex & (1 << i)) != 0) {
                inContours.add(contourMap.get(allContours[i]));
            }
        }
        return AbstractBasicRegion.get(inContours);
    }

    private static AbstractBasicRegion constructABR(Zone foot, HashMap<String, AbstractCurve> contourMap) {
        TreeSet<AbstractCurve> inContours = new TreeSet<>();
        if (foot.getInContoursCount() > 0) {
            for (String inContour : foot.getInContours()) {
                inContours.add(contourMap.get(inContour));
            }
        }
        return AbstractBasicRegion.get(inContours);
    }

    //Zohreh: Private to protected
    protected static void addFeetForZone(TreeSet<AbstractBasicRegion> feet, HashMap<String, AbstractCurve> contourMap, Zone foot) {
        feet.add(constructABR(foot, contourMap));
    }
    // </editor-fold>
}
