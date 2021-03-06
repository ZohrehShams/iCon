package speedith.core.reasoning.rules.transformers


import speedith.core.i18n.Translations._
import speedith.core.lang._
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram.createLUCarCOPDiagram;
import speedith.core.reasoning.rules.SimpleInferenceRule
import speedith.core.reasoning.{ApplyStyle, RuleApplicationException}
import speedith.core.reasoning.args.{SubDiagramIndexArg, ZoneArg}

import scala.collection.JavaConversions._

/**
  * Transforms the [[PrimarySpiderDiagram]] referenced by the given [[ZoneArg]] by
  * removing the zone saved in this [[ZoneArg]] from the set of shaded zones.
  *
 * @author Sven Linker [s.linker@brighton.ac.uk]
 */
class RemoveShadingTransformer (target : SubDiagramIndexArg, zones :  java.util.List[ZoneArg], applyStyle: ApplyStyle) extends IdTransformer{
  val subDiagramIndex = target.getSubDiagramIndex

  override def transform(psd: PrimarySpiderDiagram,
                         diagramIndex: Int,
                         parents: java.util.ArrayList[CompoundSpiderDiagram],
                         childIndices: java.util.ArrayList[java.lang.Integer]): SpiderDiagram = {
    if (diagramIndex == subDiagramIndex) {
      if (!SimpleInferenceRule.isAtFittingPosition(parents, childIndices, applyStyle, true)) {
        if (applyStyle == ApplyStyle.GoalBased) {
          throw new TransformationException(i18n("RULE_NOT_NEGATIVE_POSITION"))
        } else {
          throw new TransformationException(i18n("RULE_NOT_POSITIVE_POSITION"))
        }
      }
      if ((zones.map(zarg => zarg.getZone) -- (psd.getShadedZones & psd.getPresentZones)).nonEmpty) {
        throw new RuleApplicationException("One of the selected zones is not shaded.")
      }
      
      val commonSpiders = psd.getSpiders
      val commonHabitats = psd.getHabitats
      val commonShadedZones = psd.getShadedZones -- zones.map(zarg => zarg.getZone)
      val commonPresentZones = psd.getPresentZones
      
      if(psd.isInstanceOf[LUCarCOPDiagram]){
        val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
        
        val commonArrows = lucarcop.getArrows
        val commonSpiderLabels = lucarcop.getSpiderLabels
        val commonCurveLabels = lucarcop.getCurveLabels 
        val commonArrowCardinalities = lucarcop.getArrowCardinalities 
        
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
      
      //Zohreh
//      if(psd.isInstanceOf[CompleteCOPDiagram]){
//        val cCop = psd.asInstanceOf[CompleteCOPDiagram]
//        SpiderDiagrams.createCompleteCOPDiagram(
//            cCop.getSpiders, 
//            cCop.getHabitats(), 
//            cCop.getShadedZones -- zones.map(zarg => zarg.getZone), 
//            cCop.getPresentZones, 
//            cCop.getArrows(), 
//            cCop.getSpiderLabels(), 
//            cCop.getCurveLabels(), 
//            cCop.getArrowCardinalities(), 
//            cCop.getSpiderComparators())
//      }else{if(psd.isInstanceOf[LUCarCOPDiagram]){
//        val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
//        createLUCarCOPDiagram(
//        lucarcop.getSpiders,
//        lucarcop.getHabitats, 
//        lucarcop.getShadedZones -- zones.map(zarg => zarg.getZone), 
//        lucarcop.getPresentZones, 
//        lucarcop.getArrows,
//        lucarcop.getSpiderLabels, 
//        lucarcop.getCurveLabels,
//        lucarcop.getArrowCardinalities) 
//      }else {
//        SpiderDiagrams.createPrimarySD(psd.getHabitats, psd.getShadedZones -- zones.map(zarg => zarg.getZone), psd.getPresentZones) 
//      }
//      }
            }
    else {
      null
    }
  }

}
