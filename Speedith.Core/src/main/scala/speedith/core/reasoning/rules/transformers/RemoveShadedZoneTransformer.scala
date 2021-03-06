package speedith.core.reasoning.rules.transformers



import speedith.core.lang._
import speedith.core.lang.cop.CompleteCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram;
import speedith.core.lang.cop.LUCarCOPDiagram.createLUCarCOPDiagram;
import speedith.core.reasoning.RuleApplicationException
import speedith.core.reasoning.args.{SubDiagramIndexArg, ZoneArg}

import scala.collection.JavaConversions._

/**
 *
 * @author Sven Linker [s.linker@brighton.ac.uk]
 *
 */
class RemoveShadedZoneTransformer (target:  SubDiagramIndexArg, zones : java.util.List[ZoneArg]) extends IdTransformer{
  val subDiagramIndex = target.getSubDiagramIndex

  override def transform(psd: PrimarySpiderDiagram,
                         diagramIndex: Int,
                         parents: java.util.ArrayList[CompoundSpiderDiagram],
                         childIndices: java.util.ArrayList[java.lang.Integer]): SpiderDiagram = {
    if (diagramIndex == subDiagramIndex) {
        if (( zones.map( zarg => zarg.getZone) -- (psd.getShadedZones & psd.getPresentZones)).nonEmpty  ) {
          throw new TransformationException("One of the selected zones is not shaded.")
        }
        if (zones.exists(_.getZone.getInContoursCount ==0)) {
          throw new RuleApplicationException("Cannot remove the outer zone")
        }
        if (psd.getHabitats.values exists (r => (r.zones & (zones map (arg => arg.getZone)).toSet).nonEmpty)) {
          throw new RuleApplicationException("Cannot remove zone that contains a spider")
        }
        
        
        if(psd.isInstanceOf[CompleteCOPDiagram]){
        val compCop = psd.asInstanceOf[CompleteCOPDiagram]
            SpiderDiagrams.createCompleteCOPDiagram(
            psd.getSpiders,
            psd.getHabitats, 
            psd.getShadedZones, 
            psd.getPresentZones-- zones.map(_.getZone), 
            compCop.getArrows,
            compCop.getSpiderLabels, 
            compCop.getCurveLabels,
            compCop.getArrowCardinalities,
            compCop.getSpiderComparators)
            }else{
              if(psd.isInstanceOf[LUCarCOPDiagram]){
                val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
                createLUCarCOPDiagram(
                    psd.getSpiders,
                    psd.getHabitats, 
                    psd.getShadedZones, 
                    psd.getPresentZones-- zones.map(_.getZone), 
                    lucarcop.getArrows,
                    lucarcop.getSpiderLabels, 
                    lucarcop.getCurveLabels,
                    lucarcop.getArrowCardinalities)
              }else{
                SpiderDiagrams.createPrimarySD(psd.getHabitats, psd.getShadedZones , psd.getPresentZones-- zones.map(_.getZone))
              }
            }
      
//      if(psd.isInstanceOf[LUCarCOPDiagram]){
//        val lucarcop = psd.asInstanceOf[LUCarCOPDiagram]
//            createLUCarCOPDiagram(
//            psd.getSpiders,
//            psd.getHabitats, 
//            psd.getShadedZones, 
//            psd.getPresentZones-- zones.map(_.getZone), 
//            lucarcop.getArrows,
//            lucarcop.getSpiderLabels, 
//            lucarcop.getCurveLabels,
//            lucarcop.getArrowCardinalities)
//      }else{
//        SpiderDiagrams.createPrimarySD(psd.getHabitats, psd.getShadedZones , psd.getPresentZones-- zones.map(_.getZone))
//      }
        
        
        
    } else {
      null
    }
  }

}
