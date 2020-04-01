package speedith.core.reasoning.util.unitary

import speedith.core.lang._
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.reasoning.rules.util.ReasoningUtils

import scala.collection.JavaConversions._
import scala.collection.SortedMap

/**
 * This class allows introducing a single unnamed curve such that it splits every existing zones in the diagram. 
 * @author Zohreh Shams
 */

class AddCurve(psd: PrimarySpiderDiagram, curves: java.util.List[String]) {

  val curvesToAdd = curves.toSet
   
  
  private def regionWithNewContours(region: Iterable[Zone]): Set[Zone] = {
    region.map(zone => new Zone(zone.getInContours ++ curvesToAdd, zone.getOutContours )).toSet ++ region.map(zone => new Zone(zone.getInContours , zone.getOutContours ++ curvesToAdd )).toSet
  }

  
  def createHabitats(habitats: Map[String, Region], contoursToAdd: Set[String]): Map[String, Region] = {
    habitats map (s  => (s._1,Region(ReasoningUtils.regionWithNewContours(s._2.zones, curvesToAdd))))
  }
  
  
  def addingCurve(): PrimarySpiderDiagram = {
    if (psd.isInstanceOf[CompleteCOPDiagram]){
      val comp = psd.asInstanceOf[CompleteCOPDiagram]
      SpiderDiagrams.createCompleteCOPDiagram(comp.getSpiders,createHabitats(comp.getHabitats.toMap, curvesToAdd), 
      ReasoningUtils.shadedRegionWithNewContours(comp.getShadedZones.toSet,curvesToAdd),
      ReasoningUtils.regionWithNewContours(comp.getPresentZones,curvesToAdd),comp.getArrows,comp.getSpiderLabels,
      comp.getCurveLabels,comp.getArrowCardinalities,comp.getSpiderComparators)
    }else{
    SpiderDiagrams.createPrimarySD(createHabitats(psd.getHabitats.toMap, curvesToAdd), 
      ReasoningUtils.shadedRegionWithNewContours(psd.getShadedZones.toSet,curvesToAdd),
      ReasoningUtils.regionWithNewContours(psd.getPresentZones,curvesToAdd))}
  }

      
  
  
}
