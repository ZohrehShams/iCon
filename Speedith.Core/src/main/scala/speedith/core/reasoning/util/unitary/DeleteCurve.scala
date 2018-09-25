package speedith.core.reasoning.util.unitary

import speedith.core.lang._

import speedith.core.reasoning.args.{ContourArg, SubDiagramIndexArg}
import speedith.core.reasoning.rules.util.ReasoningUtils

import scala.collection.JavaConversions._
import scala.collection.SortedMap



import speedith.core.i18n.Translations._
import speedith.core.lang._
import speedith.core.reasoning.ApplyStyle
import speedith.core.reasoning.args.ContourArg
import speedith.core.reasoning.rules.SimpleInferenceRule
import speedith.core.lang.LUCarCOPDiagram.createLUCarCOPDiagram

import scala.collection.JavaConversions._

/**
 * This class allows deleting a curve  in the diagram. 
 * @author Zohreh Shams
 */

class DeleteCurve(psd: PrimarySpiderDiagram, curves: java.util.List[String]) {

  val curvesToDel = curves.toSet
  val commonHabitats = psd.getHabitats.map {case (spider, habitat) => (spider, new Region(regionWithoutContours(habitat.zones)))}
  val commonShadedZones = shadedRegionWithoutContours(psd.getShadedZones.toSet)
  val commonPresentZones = regionWithoutContours(psd.getPresentZones.toSet)
  

  def deletingCurve(): PrimarySpiderDiagram = {
    if (psd.isInstanceOf[CompleteCOPDiagram]){
      val comp = psd.asInstanceOf[CompleteCOPDiagram]
      var arrowsToRemove = scala.collection.immutable.TreeSet[Arrow]()
      for (arrow <- comp.getArrows){
        if(curvesToDel.contains(arrow.arrowSource()) || curvesToDel.contains(arrow.arrowTarget())){
            arrowsToRemove = arrowsToRemove + arrow
          }
      }    
      SpiderDiagrams.createCompleteCOPDiagram(comp.getSpiders,commonHabitats,commonShadedZones,commonPresentZones,comp.getArrows -- arrowsToRemove,
          comp.getSpiderLabels, comp.getCurveLabels -- curvesToDel, comp.getArrowCardinalities -- arrowsToRemove,comp.getSpiderComparators)
    }else{
      SpiderDiagrams.createPrimarySD(psd.getSpiders,commonHabitats,commonShadedZones,commonPresentZones)
    }
  }

      
    private def regionWithoutContours(region: Set[Zone]): Set[Zone] = {
      val result = region.map(zone => new Zone(zone.getInContours -- curvesToDel, zone.getOutContours -- curvesToDel)).filter(zone => zone.getAllContours.nonEmpty)
      if (result.nonEmpty) {
        result
        } else {
          Set(new Zone())
        }  
    }

    
    private def shadedRegionWithoutContours(region: Set[Zone]): Set[Zone] = {
      var shadedRegion = region
      for (contourToRemove <- curvesToDel) {
        shadedRegion = shadedRegion.filter(zone =>
          zone.getInContours.contains(contourToRemove) &&
                    shadedRegion.contains(new Zone(zone.getInContours - contourToRemove, zone.getOutContours + contourToRemove))
                    )
          shadedRegion = shadedRegion.map(zone => new Zone(zone.getInContours - contourToRemove, zone.getOutContours - contourToRemove))          

      }
      shadedRegion
    }
    
  
      

    
    
  
}
