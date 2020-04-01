package speedith.core.reasoning.util.unitary

import speedith.core.lang.SpiderDiagrams.createPrimarySD
import speedith.core.lang.SpiderDiagrams.createCompleteCOPDiagram
import speedith.core.lang.{PrimarySpiderDiagram, Region, Zone, Zones}
import speedith.core.lang.cop.CompleteCOPDiagram

import javax.swing.JOptionPane

import scala.collection.JavaConversions._


//Zohreh: this is to allow recording the situation of a contour w.r.t. other contours in the same diagram.
class ZoneTransferSingle(destinationDiagram: PrimarySpiderDiagram) {

  //val contoursOnlyInSource: java.util.Set[String] = sourceDiagram.getAllContours.diff(destinationDiagram.getAllContours)
  //private val sourceContourRelations = new ContourRelations(sourceDiagram)
  //private val contoursInSourceDiagram: Set[String] = sourceDiagram.getAllContours.toSet
  
  private val contourRelations = new ContourRelations(destinationDiagram)
  private val contoursInDestinationDiagram: Set[String] = destinationDiagram.getAllContours.toSet
  
  private val allVisibleZonesInDestinationDiagram: Set[Zone] = (destinationDiagram.getPresentZones ++ destinationDiagram.getHabitats.values().flatMap(_.zones)).toSet
  private val allPossibleZonesInDestinationDiagram: Set[Zone] = Zones.allZonesForContours(destinationDiagram.getAllContours.toSeq:_*).toSet

  def transferContour(contourFromSource: String,zonesIn: java.util.Set[Zone], zonesOut: java.util.Set[Zone]): PrimarySpiderDiagram = {
    assertContourNotInSource(contourFromSource)

    //val zonesIn = zonesInDestinationInsideContour(contourFromSource)
    //val zonesOut = zonesInDestinationOutsideContour(contourFromSource)
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
      

      if(destinationDiagram.isInstanceOf[CompleteCOPDiagram]){
        val compDes = destinationDiagram.asInstanceOf[CompleteCOPDiagram]
        createCompleteCOPDiagram(spiderHabitats.keySet, spiderHabitats, shadedZones, presentZones,
            compDes.getArrows, compDes.getSpiderLabels,compDes.getCurveLabels,compDes.getArrowCardinalities,compDes.getSpiderComparators)
      }
      else{createPrimarySD(spiderHabitats.keySet, spiderHabitats, shadedZones, presentZones)}
  }

  
  

  def zonesInDestinationOutsideContour(sourceContour: String): java.util.Set[Zone] = {
    allPossibleZonesInDestinationDiagram.filter(destinationZone =>
      contoursInDestinationDiagram.exists(commonContour =>
        (destinationZone.getInContours.contains(commonContour) && contourRelations.areContoursDisjoint(sourceContour, commonContour)) ||
          (destinationZone.getOutContours.contains(commonContour) && contourRelations.contourContainsAnother(commonContour, sourceContour))
      ))
  }

  
  
  def zonesInDestinationInsideContour(sourceContour: String): java.util.Set[Zone] = {
    allPossibleZonesInDestinationDiagram.filter(destinationZone =>
      contoursInDestinationDiagram.exists(contour =>
        destinationZone.getInContours.contains(contour) && contourRelations.contourContainsAnother(sourceContour, contour)
      )
    )
  }

  
  
  private def addInContourToZone(zone: Zone, contourFromSource: String): Zone = {
    zone.withInContours((zone.getInContours + contourFromSource).toSeq: _*)
  }

  
  
  private def addOutContourToZone(zone: Zone, contourFromSource: String): Zone = {
    zone.withOutContours((zone.getOutContours + contourFromSource).toSeq: _*)
  }

  
  
  private def assertContourNotInSource(sourceContour: String) {
    if (contoursInDestinationDiagram.contains(sourceContour)) {
      //throw new IllegalArgumentException("Curve name'" + sourceContour + "' is already present in the diagram.")
      JOptionPane.showMessageDialog(null,"The curve has to have a fresh name.","Input error",JOptionPane.ERROR_MESSAGE)
    }
  }
}