package speedith.core.reasoning.rules.transformers.copTrans

import speedith.core.lang._
import speedith.core.reasoning.RuleApplicationException
import speedith.core.reasoning.args.{SubDiagramIndexArg, ZoneArg}

import scala.collection.JavaConversions._
import speedith.core.lang.LUCarCOPDiagram.createLUCarCOPDiagram

class AddAllMissingZonesTransformer (target:  SubDiagramIndexArg) extends IdTransformer {
  val subDiagramIndex = target.getSubDiagramIndex

  override def transform(psd: PrimarySpiderDiagram,
                         diagramIndex: Int,
                         parents: java.util.ArrayList[CompoundSpiderDiagram],
                         childIndices: java.util.ArrayList[java.lang.Integer]): SpiderDiagram = {
    
    if (diagramIndex == subDiagramIndex) {
      
      if ( (psd.getShadedZones -- psd.getPresentZones).isEmpty) {
        throw new TransformationException("The diagram does not have any missing zones.")
        }
      
      val commonSpiders = psd.getSpiders
      val commonHabitats = psd.getHabitats
      val commonShadedZones = psd.getShadedZones
      val commonPresentZones = psd.getPresentZones ++ ( psd.getShadedZones -- psd.getPresentZones)
      
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

//      if(psd.isInstanceOf[CompleteCOPDiagram]){
//        val cCop = psd.asInstanceOf[CompleteCOPDiagram]
//         
//            SpiderDiagrams.createCompleteCOPDiagram(
//            cCop.getSpiders,
//            cCop.getHabitats, 
//            cCop.getShadedZones, 
//            cCop.getPresentZones ++ ( cCop.getShadedZones -- cCop.getPresentZones), 
//            cCop.getArrows,
//            cCop.getSpiderLabels, 
//            cCop.getCurveLabels,
//            cCop.getArrowCardinalities,
//            cCop.getSpiderComparators)
//         
//      }
//      else{
//       if(psd.isInstanceOf[LUCarCOPDiagram]){
//         val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
//            createLUCarCOPDiagram(
//            lucarcop.getSpiders,
//            lucarcop.getHabitats, 
//            lucarcop.getShadedZones, 
//            lucarcop.getPresentZones ++ (lucarcop.getShadedZones -- lucarcop.getPresentZones), 
//            lucarcop.getArrows,
//            lucarcop.getSpiderLabels, 
//            lucarcop.getCurveLabels,
//            lucarcop.getArrowCardinalities)
//         
//       }else{
//         SpiderDiagrams.createPrimarySD(psd.getHabitats,psd.getShadedZones, 
//             psd.getPresentZones ++  (psd.getShadedZones -- psd.getPresentZones))
//       }
//      
//    }
      }else{
      null
    }
    
  }
  
}
