package speedith.ui;

import icircles.abstractDescription.*;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.util.CannotDrawException;
import java.util.Map.Entry;
import java.util.*;
import javax.swing.JPanel;
import speedith.core.lang.*;
import speedith.ui.abstracts.AbstractArrow;
import speedith.ui.abstracts.AbstractSpiderComparator;
import speedith.ui.abstracts.CDAbstractDescription;
import speedith.ui.abstracts.COPAbstractDescription;
import speedith.ui.abstracts.CompleteCOPAbstractDescription;
import speedith.ui.concretes.ConcreteCDiagram;
import speedith.ui.concretes.ConcreteCOPDiagram;

import static speedith.i18n.Translations.i18n;

public class COPDiagramVisualisation extends DiagramVisualisation{
    

    public COPDiagramVisualisation() {
    	super();
    }
 

    public static AbstractDescription getAbstractDescription(PrimarySpiderDiagram psd) throws CannotDrawException {

    	
    	
    	PrimarySpiderDiagram temp1 = psd;
    	PrimarySpiderDiagram temp2 = psd;
    	PrimarySpiderDiagram temp3 = psd;
    	
        if (psd == null || !psd.isValid()) {
            throw new CannotDrawException(i18n("DRAW_NOT_VALID_PSD"));
        }
        
        SortedSet<String> contourStrings = psd.getAllContours();
        
        if (contourStrings.size() > 10) {
            throw new CannotDrawException(i18n("TOO_MANY_CONTOURS"));
        }

        TreeSet<AbstractCurve> abstractContours = new TreeSet<>();
        TreeSet<AbstractSpider> abstractSpiders = new TreeSet<>();
        TreeSet<AbstractSpiderComparator> abstractSpiderCompartors = new TreeSet<>();
        TreeSet<AbstractBasicRegion> shadedHabitatZones = new TreeSet<>();
        TreeSet<AbstractBasicRegion> allVisibleZones = new TreeSet<>();
        HashMap<String, AbstractCurve> contourMap = new HashMap<>();
        HashMap<String, AbstractSpider> spiderMap = new HashMap<>();
        HashMap<Arrow, AbstractArrow> arrowMap = new HashMap<>();

        String[] allContours = contourStrings.toArray(new String[contourStrings.size()]);

  
        int allZonesCount = 1 << contourStrings.size();
    
        byte[] allZones = new byte[allZonesCount];
        final byte IsShaded = 1;
        final byte IsInHabitat = 2;
        final byte IsPresent = 4;
        
        for (String contour : contourStrings) {
            final AbstractCurve abstractCurve = new AbstractCurve(contour);
            contourMap.put(contour, abstractCurve);
            abstractContours.add(abstractCurve);
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
         
        //psd has to be instance of COPDiagram to use this class for generating abstract description. 
		COPDiagram cop = (COPDiagram) psd;
		COPAbstractDescription copAd = new COPAbstractDescription(abstractContours, allVisibleZones, shadedHabitatZones); 
		CompleteCOPAbstractDescription compAd = new CompleteCOPAbstractDescription(abstractContours, allVisibleZones, shadedHabitatZones); 

        
        if (psd.getHabitatsCount() > 0) {
            SortedMap<String, Region> habitats = psd.getHabitats();
            for (Entry<String, Region> habitat : habitats.entrySet()) {
                TreeSet<AbstractBasicRegion> feet = new TreeSet<>();
                for (Zone foot : habitat.getValue().sortedZones()) {
                    addFeetForZone(feet, contourMap, foot);
                }
                final AbstractSpider abstractSpider= new AbstractSpider(feet, habitat.getKey());
                spiderMap.put(habitat.getKey(),abstractSpider);
                abstractSpiders.add(abstractSpider);
                copAd.addSpider(abstractSpider);
                compAd.addSpider(abstractSpider);
            }
        }
        
		
		if (cop.getArrowsCount() > 0) {
    		TreeSet<Arrow> arrows = cop.getArrowsMod();

    		for (Arrow arrow : arrows){
    			AbstractShape arrowSource,arrowTarget;
    			
    			if (contourStrings.contains(arrow.arrowSource())){
    				arrowSource = contourMap.get(arrow.arrowSource());
    			}else{
    				arrowSource = spiderMap.get(arrow.arrowSource());
    			}
    			
    			
    			if (contourStrings.contains(arrow.arrowTarget())){
    				arrowTarget = contourMap.get(arrow.arrowTarget());
    			}else{
    				arrowTarget = spiderMap.get(arrow.arrowTarget());
    			}
    			
    			final AbstractArrow abstractArrow = new AbstractArrow(arrowSource,arrowTarget,arrow.arrowType(),arrow.arrowLabel());
    			arrowMap.put(arrow,abstractArrow);
    			copAd.addArrow(abstractArrow);
    			compAd.addArrow(abstractArrow);
    		}    		
		}
		
		
		if(temp1 instanceof LUCOPDiagram){
			LUCOPDiagram luCop = (LUCOPDiagram) temp1;
			
			for(AbstractCurve abstractCurve : abstractContours){
				abstractCurve.setName(luCop.getCurveLabels().get(abstractCurve.getLabel()));
			}
			
			for(AbstractSpider abstractSpider : abstractSpiders){
				abstractSpider.setLabel(luCop.getSpiderLabels().get(abstractSpider.getName()));
			}	
			
			
			if(temp2 instanceof LUCarCOPDiagram){
				LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) temp2;
				
				for (Arrow arrow : luCarCop.getArrows()){
					AbstractArrow abstractArrow = arrowMap.get(arrow);
					Cardinality cardinality = luCarCop.getArrowCardinalities().get(arrow);
					abstractArrow.setCardinality(cardinality);
				}
				if(temp3 instanceof CompleteCOPDiagram){
					CompleteCOPDiagram  compCop = (CompleteCOPDiagram) temp3;
    	        	
    				for(SpiderComparator sc : compCop.getSpiderComparators()){
    					AbstractSpider as1 = spiderMap.get(sc.getComparable1());
    					AbstractSpider as2 = spiderMap.get(sc.getComparable2());
    					AbstractSpiderComparator asc = new AbstractSpiderComparator(as1,as2,sc.getQuality());
    					abstractSpiderCompartors.add(asc);
    				}
    				compAd.addAllSpiderComparators(abstractSpiderCompartors);	
    				return compAd;
				}	
			}			
		}
		return copAd; 
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
        HashMap<Arrow, AbstractArrow> cdArrowMap = new HashMap<Arrow, AbstractArrow>();
        
        
    	for (PrimarySpiderDiagram psd : cd.getPrimaries()){
    		abstractPrimaries.add((COPAbstractDescription) getAbstractDescription(psd));
    		contourStringsAll.addAll(psd.getAllContours());
    		spiderStringsAll.addAll(psd.getSpiders());
    		contourMapAll.putAll(((COPAbstractDescription) getAbstractDescription(psd)).getContourMap());
    		spiderMapAll.putAll(((COPAbstractDescription) getAbstractDescription(psd)).getSpiderMap());
    	}
    	
    	CDAbstractDescription cdAd = new CDAbstractDescription(abstractPrimaries);
    	
    	if (cd.getArrowCount() > 0){
    		TreeSet<Arrow> cdArrows = cd.get_cd_ArrowsMod();
    		
    		for (Arrow arrow : cdArrows){
    			AbstractShape arrowSource,arrowTarget;
    			
    			if (contourStringsAll.contains(arrow.arrowSource())){
    				arrowSource = contourMapAll.get(arrow.arrowSource());
    			}else{
    				arrowSource = spiderMapAll.get(arrow.arrowSource());
    			}
    			
    			if (contourStringsAll.contains(arrow.arrowTarget())){
    				arrowTarget = contourMapAll.get(arrow.arrowSource());
    			}else{
    				arrowTarget = spiderMapAll.get(arrow.arrowTarget());
    			}
    			
    			final AbstractArrow abstractArrow = new AbstractArrow(arrowSource,arrowTarget,arrow.arrowType(),arrow.arrowLabel());
    			cdArrowMap.put(arrow,abstractArrow);
    			cdAd.addArrow(abstractArrow);
    			
        		if (cd instanceof CarCDiagram){
        			CarCDiagram carCDaigram = (CarCDiagram) cd;
        			Cardinality cardinality = carCDaigram.get_cd_ArrowCardinalities().get(arrow);
					abstractArrow.setCardinality(cardinality);
        		}
    		} 
 
    	}
    	return cdAd;
    }
    
    
//    static CDCirclesPanelEx getSpiderDiagramPanel(ConceptDiagram diagram, int size) throws CannotDrawException {
//    	final CDAbstractDescription ad = getAbstractDescription(diagram);
//        ConcreteCDiagram cd = ConcreteCDiagram.makeConcreteDiagram(ad, size);        
//        return new CDCirclesPanelEx(cd);
//    }
    
    

}
