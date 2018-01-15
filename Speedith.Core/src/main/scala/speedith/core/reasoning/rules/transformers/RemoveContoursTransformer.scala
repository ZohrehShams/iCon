package speedith.core.reasoning.rules.transformers

import speedith.core.i18n.Translations._
import speedith.core.lang._
import speedith.core.reasoning.ApplyStyle
import speedith.core.reasoning.args.ContourArg
import speedith.core.reasoning.rules.SimpleInferenceRule
import speedith.core.lang.LUCarCOPDiagram.createLUCarCOPDiagram

import scala.collection.JavaConversions._

case class RemoveContoursTransformer(contourArgs: java.util.List[ContourArg], applyStyle: ApplyStyle) extends IdTransformer {

  val subDiagramIndex = contourArgs(0).getSubDiagramIndex
  val contoursToRemove = contourArgs.map(_.getContour).toSet

  private def regionWithoutContours(region: Set[Zone]): Set[Zone] = {
    val result = region.map(zone => new Zone(zone.getInContours -- contoursToRemove, zone.getOutContours -- contoursToRemove)).filter(zone => zone.getAllContours.nonEmpty)
    if (result.nonEmpty) {
      result
    } else {
      Set(new Zone())
    }
  }

  private def shadedRegionWithoutContours(region: Set[Zone]): Set[Zone] = {
    var shadedRegion = region
    for (contourToRemove <- contoursToRemove) {
      shadedRegion = shadedRegion.filter(zone =>
        zone.getInContours.contains(contourToRemove) &&
          shadedRegion.contains(new Zone(zone.getInContours - contourToRemove, zone.getOutContours + contourToRemove))
      )
      shadedRegion = shadedRegion.map(zone => new Zone(zone.getInContours - contourToRemove, zone.getOutContours - contourToRemove))
    }
    shadedRegion
  }

  override def transform(psd: PrimarySpiderDiagram,
                         diagramIndex: Int,
                         parents: java.util.ArrayList[CompoundSpiderDiagram],
                         childIndices: java.util.ArrayList[java.lang.Integer]): SpiderDiagram = {
    if (subDiagramIndex == diagramIndex) {
      if (!SimpleInferenceRule.isAtFittingPosition(parents, childIndices, applyStyle, true)) {
        if (applyStyle == ApplyStyle.GoalBased) {
          throw new TransformationException(i18n("RULE_NOT_NEGATIVE_POSITION"))
        } else {
          throw new TransformationException(i18n("RULE_NOT_POSITIVE_POSITION"))
        }
      }
      if (!psd.getAllContours.containsAll(contoursToRemove)) {
        throw new TransformationException("The contours to be removed do not exist in the target diagram")
      }
      
       //Zohreh
      if(psd.isInstanceOf[LUCarCOPDiagram]){
        val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
        //val newCurveLabelsMap=lucarcop.getCurveLabels.toMap
        //var newCurveLabelsTreeMap = scala.collection.immutable.TreeMap[String,String]()
        
        var arrowsToRemove = scala.collection.immutable.TreeSet[Arrow]()

        for (arrow <- lucarcop.getArrows){
          if(contoursToRemove.contains(arrow.arrowSource()) || contoursToRemove.contains(arrow.arrowTarget())){
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
            //newCurveLabelsTreeMap ++ (newCurveLabelsMap -- contoursToRemove), 
            lucarcop.getCurveLabels -- contoursToRemove,
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

        
        
        
    } else {
      null
    }
  }

  
  
}
