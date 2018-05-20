package speedith.core.reasoning.util.unitary

import speedith.core.lang._

class SpiderTransfer(sourceDiagram: PrimarySpiderDiagram, destinationDiagram: PrimarySpiderDiagram) {

  def transferSpider(spider: String): PrimarySpiderDiagram = {
    assertSpiderIsInSourceDiagram(spider)
    assertSpiderNotInDestinationDiagram(spider)

    val sourceSpiderHabitat = sourceDiagram.getSpiderHabitat(spider)
    val destinationSpiderHabitat = CorrespondingRegions(sourceDiagram, destinationDiagram).correspondingRegion(sourceSpiderHabitat)
    
    if ((sourceDiagram.isInstanceOf[LUCarCOPDiagram]) && (destinationDiagram.isInstanceOf[LUCarCOPDiagram])){
      val lucarcopSource = sourceDiagram.asInstanceOf[LUCarCOPDiagram]
      val lucarcopDestination = destinationDiagram.asInstanceOf[LUCarCOPDiagram]
      val spiderLabel = lucarcopSource.getSpiderLabels().get(spider)
      lucarcopDestination.addLUSpider(spider,destinationSpiderHabitat,spiderLabel)
    }else{
      destinationDiagram.addSpider(spider, destinationSpiderHabitat)
    }
  }

  private def assertSpiderNotInDestinationDiagram(spider: String) {
    if (destinationDiagram.containsSpider(spider)) {
      throw new IllegalArgumentException("A spider with the name '" + spider + "' is already present in the target diagram.")
    }
  }

  private def assertSpiderIsInSourceDiagram(spider: String) {
    if (!sourceDiagram.containsSpider(spider)) {
      throw new IllegalArgumentException("The spider '" + spider + "' is not in the source diagram.")
    }
  }
}
