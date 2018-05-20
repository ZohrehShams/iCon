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
      
      if(psd.isInstanceOf[CompleteCOPDiagram]){
        val cCop = psd.asInstanceOf[CompleteCOPDiagram]
         
         if ( (cCop.getShadedZones -- cCop.getPresentZones).isEmpty) {
           throw new TransformationException("The diagram does not have any missing zones.")
         }else{
            SpiderDiagrams.createCompleteCOPDiagram(
            cCop.getSpiders,
            cCop.getHabitats, 
            cCop.getShadedZones, 
            cCop.getPresentZones ++ ( cCop.getShadedZones -- cCop.getPresentZones), 
            cCop.getArrows,
            cCop.getSpiderLabels, 
            cCop.getCurveLabels,
            cCop.getArrowCardinalities,
            cCop.getSpiderComparators)
         }
      }
      else{
       if(psd.isInstanceOf[LUCarCOPDiagram]){
         val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
         
         if ( (lucarcop.getShadedZones -- lucarcop.getPresentZones).isEmpty) {
           throw new TransformationException("The diagram does not have any missing zones.")
         }else{
            createLUCarCOPDiagram(
            lucarcop.getSpiders,
            lucarcop.getHabitats, 
            lucarcop.getShadedZones, 
            lucarcop.getPresentZones ++ (lucarcop.getShadedZones -- lucarcop.getPresentZones), 
            lucarcop.getArrows,
            lucarcop.getSpiderLabels, 
            lucarcop.getCurveLabels,
            lucarcop.getArrowCardinalities)
         }
       }else{
         SpiderDiagrams.createPrimarySD(psd.getHabitats,psd.getShadedZones, 
             psd.getPresentZones ++  (psd.getShadedZones -- psd.getPresentZones))
       }
      
    }}else{
      null
    }
    
  }
  
}
