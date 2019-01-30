//package speedith.ui;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.TreeSet;
//
//import icircles.abstractDescription.AbstractBasicRegion;
//import icircles.abstractDescription.AbstractCurve;
//import icircles.abstractDescription.AbstractDescription;
//import icircles.abstractDescription.AbstractShape;
//import icircles.util.CannotDrawException;
//import speedith.core.lang.*;
//import speedith.ui.abstracts.AbstractArrow;
//import speedith.ui.abstracts.COPAbstractDescription;
//
//public class CDiagramVisualisation {
//	
//    private static Set<AbstractCurve> absContours;
//    private static Map<String, AbstractBasicRegion> toAbsSpiderMap;
//    private static HashMap<String, AbstractCurve> toAbsContourMap;
//    //private static Set<AbstractCOP> COPs;
//
//
//    private CDiagramVisualisation(){
//    }
//
//    private static COPAbstractDescription getAbstractDescription(COPDiagram copDiagram) throws CannotDrawException {
//        PrimarySpiderDiagram psd = (PrimarySpiderDiagram) copDiagram.getPrimarySpiderDiagram();
//        Set<AbstractBasicRegion> highlightedZones = new HashSet<>();
//        AbstractDescription ad;
//        if (psd.isValid()) {
//            ad = DiagramVisualisation.getAbstractDescription(psd);
//            toAbsSpiderMap = new TreeMap<>();
//            absContours.addAll(ad.getCopyOfContours());
//            createContourMap();
//            toAbsSpiderMap.putAll(getSpiderMaps(psd));
//        } else {
//            ad = null;
//        }
//
//        Set<AbstractArrow> abstractArrows = createAbsArrows(COPDiagram.getArrows());
//        
//        List<Equality> equalities = COPDiagram.getEqualities();
//        Set<AbstractEquality> abstractEqualities = new HashSet<>();
//
//        if (equalities != null) {
//            for (Equality equality: equalities) {
//                boolean isKnown = equality.isKnown();
//                abstractEqualities.add(new AbstractEquality(isKnown,
//                        toAbsSpiderMap.get(equality.getArg1()), toAbsSpiderMap.get(equality.getArg2()),
//                        equality.getArg1(), equality.getArg2()));
//            }
//        }
//
//        Set<String> dots = new TreeSet<>(COPDiagram.getDots());
//
//        AbstractCOP abstractCOP= new AbstractCOP(ad, highlightedZones, abstractArrows, abstractEqualities, dots, containsInitialT);
//        if (COPDiagram.isSingleVariableT) {
//            abstractCOP.isSingleVariableT = true;
//        }
//        if (COPDiagram.id > 0) {
//            abstractCOP.id = COPDiagram.id;
//        }
//        return abstractCOP;
//    }
//    
//    
//    
//    
//    private static void createContourMap() {
//        for (AbstractCurve ac : absContours) {
//            toAbsContourMap.put(ac.getLabel(), ac);
//        }
//    }
//    
//    
//    private static HashMap<String, AbstractBasicRegion> getSpiderMaps(PrimarySpiderDiagram sd) {
//        HashMap<String, AbstractBasicRegion> spiderToAbsRegions = new HashMap<>();
//        if (sd.getHabitatsCount() > 0) {
//            SortedMap<String, Region> habitats = sd.getHabitats();
//            for (Map.Entry<String, Region> habitat : habitats.entrySet()) {
//                for (Zone foot : habitat.getValue().sortedZones()) {
//                    toAbsSpiderMap.put(habitat.getKey(), constructABR(foot));
//                }
//            }
//        }
//        return spiderToAbsRegions;
//    }
//    
//    
//    private static AbstractBasicRegion constructABR(Zone zone) {
//        TreeSet<AbstractCurve> inContours = new TreeSet<>();
//        if (zone.getInContoursCount() > 0) {
//            for (String inContour : zone.getInContours()) {
//                inContours.add(toAbsContourMap.get(inContour));
//            }
//        }
//        return AbstractBasicRegion.get(inContours);
//    }
//    
//    
//    private static Set<AbstractArrow> createAbsArrows(List<Arrow> arrows) {
//        Set<AbstractArrow> abstractArrows = new HashSet<>();
//        if (arrows != null) {
//    		for (Arrow arrow : arrows){
//    			AbstractShape arrowSource,arrowTarget;
//    			
//    			if (toAbsContourMap.containsKey(arrow.arrowSource())){
//    				arrowSource = toAbsContourMap.get(arrow.arrowSource());
//    			}else{
//    				arrowSource = toAbsSpiderMap.get(arrow.arrowSource());
//    			}
//    			
//    			
//    			if (contourStrings.contains(arrow.arrowTarget())){
//    				arrowTarget = contourMap.get(arrow.arrowTarget());
//    			}else{
//    				arrowTarget = spiderMap.get(arrow.arrowTarget());
//    			}
//    			
//    			final AbstractArrow abstractArrow = new AbstractArrow(arrowSource,arrowTarget,arrow.arrowType(),arrow.arrowLabel());
//    			arrowMap.put(arrow,abstractArrow);
//    			copAd.addArrow(abstractArrow);
//    			compAd.addArrow(abstractArrow);
//    		} 
//        	
////            for (Arrow a : arrows) {
////                ArrowLabel label = new ArrowLabel(a.getLabel(), a.getCardinalityOperator(), a.getCardinalityArgument());
////                boolean isAnon = a.isDashed();
////                boolean isSourceContour = isContour(a.getSource());
////                boolean isTargetContour = isContour(a.getTarget());
////                AbstractArrow arrow;
////
////                if (isSourceContour && isTargetContour) {
////                    arrow = new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), contourMap.get(a.getTarget()), a.getSource(), a.getTarget());
////                } else if (isSourceContour) {
////                    arrow = new AbstractArrow(label, isAnon, contourMap.get(a.getSource()), spiderMap.get(a.getTarget()), a.getSource(), a.getTarget());
////                } else if (isTargetContour) {
////                    arrow = new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), contourMap.get(a.getTarget()), a.getSource(), a.getTarget());
////                } else {
////                    arrow = new AbstractArrow(label, isAnon, spiderMap.get(a.getSource()), spiderMap.get(a.getTarget()), a.getSource(), a.getTarget());
////                }
////
////                if (a.getSourceId() > 0) {
////                    arrow.setSourceId(a.getSourceId()*-1);
////                }
////
////                if (a.getTargetId() > 0) {
////                    arrow.setTargetId(a.getTargetId()*-1);
////                }
////                abstractArrows.add(arrow);
////            }
//        }
//        return abstractArrows;
//    }
//
//}
