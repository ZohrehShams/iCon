package speedith.core.reasoning.rules.transformers.copTrans

import speedith.core.lang._
import speedith.core.lang.LUCarCOPDiagram.createLUCarCOPDiagram
import scala.collection.JavaConversions._
import java.util


/**
 * This transformer is used inside IncoherenceCurveTransformer and in terms of what it does, it is identical to RemoveContoursTransformer
 * but it operates on the diagram level rather than argument level: it takes a diagram and a set of contours to be deleted from that diagram. 
 * @author Zohreh Shams
 */

class RemoveCurvesTransformer(psd: PrimarySpiderDiagram,contoursToRemove: util.Collection[String]){
  
  val curvesToRemove = contoursToRemove.toSet
  
  private def regionWithoutContours(region: Set[Zone]): Set[Zone] = {
    val result = region.map(zone => new Zone(zone.getInContours -- curvesToRemove, zone.getOutContours -- curvesToRemove)).filter(zone => zone.getAllContours.nonEmpty)
    if (result.nonEmpty) {
      result
    } else {
      Set(new Zone())
    }
  }

  private def shadedRegionWithoutContours(region: Set[Zone]): Set[Zone] = {
    var shadedRegion = region
    for (contourToRemove <- curvesToRemove) {
      shadedRegion = shadedRegion.filter(zone =>
        zone.getInContours.contains(contourToRemove) &&
          shadedRegion.contains(new Zone(zone.getInContours - contourToRemove, zone.getOutContours + contourToRemove))
      )
      shadedRegion = shadedRegion.map(zone => new Zone(zone.getInContours - contourToRemove, zone.getOutContours - contourToRemove))
    }
    shadedRegion
  }
  
  
  
  def removeCurve(): PrimarySpiderDiagram ={
    if (!psd.getAllContours.containsAll(curvesToRemove)) {
        throw new TransformationException("The curves to be removed do not exist in the target diagram")
      }
    
      if(psd.isInstanceOf[LUCarCOPDiagram]){
        val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
        var arrowsToRemove = scala.collection.immutable.TreeSet[Arrow]()

        for (arrow <- lucarcop.getArrows){
          if(curvesToRemove.contains(arrow.arrowSource()) || curvesToRemove.contains(arrow.arrowTarget())){
            arrowsToRemove = arrowsToRemove + arrow
          }
        }
            createLUCarCOPDiagram(
            lucarcop.getSpiders,
            lucarcop.getHabitats.map {
            case (spider, habitat) => (spider, new Region(regionWithoutContours(habitat.zones)))}, 
            shadedRegionWithoutContours(lucarcop.getShadedZones.toSet), regionWithoutContours(lucarcop.getPresentZones.toSet), 
            lucarcop.getArrows -- arrowsToRemove,
            lucarcop.getSpiderLabels, 
            lucarcop.getCurveLabels -- curvesToRemove,
            lucarcop.getArrowCardinalities -- arrowsToRemove)
            
      }else{
        SpiderDiagrams.createPrimarySD(
          psd.getSpiders,
          psd.getHabitats.map {
            case (spider, habitat) => (spider, new Region(regionWithoutContours(habitat.zones)))
          },
          shadedRegionWithoutContours(psd.getShadedZones.toSet),
          regionWithoutContours(psd.getPresentZones.toSet)
        )}
  }
  
  

}