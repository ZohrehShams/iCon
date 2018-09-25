package speedith.core.reasoning.util.unitary

import speedith.core.lang.SpiderDiagrams.createPrimarySD
import speedith.core.lang.SpiderDiagrams.createCompleteCOPDiagram
import speedith.core.lang.LUCarCOPDiagram.createLUCarCOPDiagram
import speedith.core.lang.{PrimarySpiderDiagram, LUCarCOPDiagram, CompleteCOPDiagram, Region, Zone, Zones}

import scala.collection.JavaConversions._

class ZoneTransfer(sourceDiagram: PrimarySpiderDiagram, destinationDiagram: PrimarySpiderDiagram) {

  val contoursOnlyInSource: java.util.Set[String] = sourceDiagram.getAllContours.diff(destinationDiagram.getAllContours)
  private val sourceContourRelations = new ContourRelations(sourceDiagram)
  private val contoursInSourceDiagram: Set[String] = sourceDiagram.getAllContours.toSet
  private val allVisibleZonesInDestinationDiagram: Set[Zone] = (destinationDiagram.getPresentZones ++ destinationDiagram.getHabitats.values().flatMap(_.zones)).toSet
  private val allPossibleZonesInDestinationDiagram: Set[Zone] = Zones.allZonesForContours(destinationDiagram.getAllContours.toSeq:_*).toSet

  def transferContour(contourFromSource: String): PrimarySpiderDiagram = {
    assertContourOnlyInSource(contourFromSource)

    val zonesIn = zonesInDestinationInsideContour(contourFromSource)
    val zonesOut = zonesInDestinationOutsideContour(contourFromSource)
    val splitZones = allVisibleZonesInDestinationDiagram -- (zonesOut ++ zonesIn)
    val spiderHabitats = destinationDiagram.getHabitats.map {
      case (spider, habitat) => (spider, new Region(
        (zonesOut ++ splitZones).intersect(habitat.zones).map(addOutContourToZone(_, contourFromSource)) ++
        (zonesIn ++ splitZones).intersect(habitat.zones).map(addInContourToZone(_, contourFromSource))
      ))
    }
    
    val shadedZones = Zones.sameRegionWithNewContours(destinationDiagram.getShadedZones, contourFromSource) ++
      zonesOut.map(addInContourToZone(_, contourFromSource)) ++
      zonesIn.map(addOutContourToZone(_, contourFromSource))

    val presentZones = (zonesOut.intersect(allVisibleZonesInDestinationDiagram) ++ splitZones).map(zone => addOutContourToZone(zone, contourFromSource)) ++
      (zonesIn.intersect(allVisibleZonesInDestinationDiagram) ++ splitZones).map(zone => addInContourToZone(zone, contourFromSource))

 
    if (destinationDiagram.isInstanceOf[CompleteCOPDiagram] &&
        sourceDiagram.isInstanceOf[CompleteCOPDiagram]){
      val destination = destinationDiagram.asInstanceOf[CompleteCOPDiagram]
      val source = sourceDiagram.asInstanceOf[CompleteCOPDiagram]
      val curveName = source.getCurveLabels.get(contourFromSource)
      
      val luSpiderHabitats = destination.getHabitats.map {
        
      //if spider is unnamed
      case (spider, habitat) if ((destination.getSpiderLabels().get(spider) == null) || 
          (destination.getSpiderLabels().get(spider).isEmpty)) => 
            (spider, new Region((zonesOut ++ splitZones).intersect(habitat.zones).map(addOutContourToZone(_, contourFromSource)) ++
        (zonesIn ++ splitZones).intersect(habitat.zones).map(addInContourToZone(_, contourFromSource))
      ))

      
      //if spider is named but does not exists in source
      //here we check if the spiders Name (their unique identifier) is the same. Might be better to check for the label)
      case (spider, habitat) if ((destination.getSpiderLabels().get(spider) != null) && 
          (! source.getSpiders().contains(spider))) => 
            (spider, new Region((zonesOut ++ splitZones).intersect(habitat.zones).map(addOutContourToZone(_, contourFromSource)) ++
        (zonesIn ++ splitZones).intersect(habitat.zones).map(addInContourToZone(_, contourFromSource))
      ))
      
            
      //spider is named and exists in source and is inside curveName in source
      case (spider, habitat) if ((destination.getSpiderLabels().get(spider) != null) && 
          (source.getSpiders().contains(spider)) && (source.getHabitats().get(spider).getZonesCount == 1 )
          &&           (source.getSpiders().contains(spider)) && (source.getHabitats().get(spider).getZonesCount == 1 ) 
            && (  source.getHabitats.values().flatMap(_.zones).forall(habitatZone =>
      Zones.isZonePartOfThisContour(habitatZone, curveName)  ) ))=> 
            (spider, new Region((zonesIn ++ splitZones).intersect(habitat.zones).map(addInContourToZone(_, contourFromSource))
      ))

    
      //spider is named and exists in source and is outside curveName in source
      case (spider, habitat) if ((destination.getSpiderLabels().get(spider) != null) && 
          (source.getSpiders().contains(spider)) && (source.getHabitats().get(spider).getZonesCount == 1 ) 
            && (  source.getHabitats.values().flatMap(_.zones).forall(habitatZone =>
      Zones.isZoneOutsideContours(habitatZone, curveName)
    ))) => 
            (spider, new Region((zonesOut ++ splitZones).intersect(habitat.zones).map(addOutContourToZone(_, contourFromSource))
      ))
      

    }
      
      if(curveName != null){
        createCompleteCOPDiagram(spiderHabitats.keySet, luSpiderHabitats, shadedZones, presentZones, destination.getArrows,
            destination.getSpiderLabels, destination.getCurveLabels + (contourFromSource -> curveName), destination.getArrowCardinalities,destination.getSpiderComparators)
        
      }else{
        createCompleteCOPDiagram(spiderHabitats.keySet, luSpiderHabitats, shadedZones, presentZones, destination.getArrows,
            destination.getSpiderLabels, destination.getCurveLabels, destination.getArrowCardinalities,destination.getSpiderComparators)
      }
     
      }
    else {createPrimarySD(spiderHabitats.keySet, spiderHabitats, shadedZones, presentZones)}
  }

  
  
  def zonesInDestinationOutsideContour(sourceContour: String): java.util.Set[Zone] = {
    assertContourOnlyInSource(sourceContour)

    allPossibleZonesInDestinationDiagram.filter(destinationZone =>
      contoursInSourceDiagram.exists(commonContour =>
        (destinationZone.getInContours.contains(commonContour) && sourceContourRelations.areContoursDisjoint(sourceContour, commonContour)) ||
          (destinationZone.getOutContours.contains(commonContour) && sourceContourRelations.contourContainsAnother(commonContour, sourceContour))
      ))
  }

  def zonesInDestinationInsideContour(sourceContour: String): java.util.Set[Zone] = {
    assertContourOnlyInSource(sourceContour)

    allPossibleZonesInDestinationDiagram.filter(destinationZone =>
      contoursInSourceDiagram.exists(contour =>
        destinationZone.getInContours.contains(contour) && sourceContourRelations.contourContainsAnother(sourceContour, contour)
      )
    )
  }

  private def addInContourToZone(zone: Zone, contourFromSource: String): Zone = {
    zone.withInContours((zone.getInContours + contourFromSource).toSeq: _*)
  }

  private def addOutContourToZone(zone: Zone, contourFromSource: String): Zone = {
    zone.withOutContours((zone.getOutContours + contourFromSource).toSeq: _*)
  }

  private def assertContourOnlyInSource(sourceContour: String) {
    if (!contoursOnlyInSource.contains(sourceContour)) {
      throw new IllegalArgumentException("The contour '" + sourceContour + "' must be present only in the source diagram.")
    }
  }
}