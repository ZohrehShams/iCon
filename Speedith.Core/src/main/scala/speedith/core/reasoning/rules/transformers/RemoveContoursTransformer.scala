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
      
      
      val commonSpiders = psd.getSpiders
      val commonHabitats = psd.getHabitats.map {case (spider, habitat) => (spider, new Region(regionWithoutContours(habitat.zones)))}
      val commonShadedZones = shadedRegionWithoutContours(psd.getShadedZones.toSet)
      val commonPresentZones = regionWithoutContours(psd.getPresentZones.toSet)
      
      if(psd.isInstanceOf[LUCarCOPDiagram]){
        val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
        var arrowsToRemove = scala.collection.immutable.TreeSet[Arrow]()

        for (arrow <- lucarcop.getArrows){
          if(contoursToRemove.contains(arrow.arrowSource()) || contoursToRemove.contains(arrow.arrowTarget())){
            arrowsToRemove = arrowsToRemove + arrow
          }
        }
        
        val commonArrows = lucarcop.getArrows -- arrowsToRemove
        val commonSpiderLabels = lucarcop.getSpiderLabels
        val commonCurveLabels = lucarcop.getCurveLabels -- contoursToRemove
        val commonArrowCardinalities = lucarcop.getArrowCardinalities -- arrowsToRemove
        
        if(psd.isInstanceOf[CompleteCOPDiagram]){
          val compCop = psd.asInstanceOf[CompleteCOPDiagram]
          SpiderDiagrams.createCompleteCOPDiagram(commonSpiders,commonHabitats,commonShadedZones,commonPresentZones,commonArrows,commonSpiderLabels,
              commonCurveLabels,commonArrowCardinalities,compCop.getSpiderComparators)
          } else{
          createLUCarCOPDiagram(commonSpiders,commonHabitats,commonShadedZones,commonPresentZones,commonArrows,commonSpiderLabels,
              commonCurveLabels,commonArrowCardinalities)} 
      }else{
        SpiderDiagrams.createPrimarySD(commonSpiders,commonHabitats,commonShadedZones,commonPresentZones)
        }

   
        
    } else {
      null
    }
  }

  
  
}
