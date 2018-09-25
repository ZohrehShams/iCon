package speedith.draw;

import speedith.core.lang.LUCarCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.reader.ReadingException;
import speedith.core.reasoning.rules.transformers.CopyContoursTransformerTest;
import speedith.core.reasoning.util.unitary.TestSpiderDiagrams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static speedith.core.lang.SpiderDiagrams.createPrimarySD;
import static speedith.core.lang.Zones.*;
import static speedith.core.reasoning.rules.transformers.CopyContoursTransformerTest.diagramWithASpider;
import static speedith.core.reasoning.rules.transformers.DoubleNegationEliminationTransformerTest.doubleNegate;
import static speedith.core.reasoning.util.unitary.CorrespondingRegionsTest.SINGLE_CONTOUR_A_DIAGRAM;
import static speedith.core.reasoning.util.unitary.TestSpiderDiagrams.*;
import static speedith.core.reasoning.util.unitary.TestCOPDiagrams.*;
import static speedith.draw.SpiderDiagramPanelTest.*;
import static speedith.core.reasoning.util.unitary.TestConceptDiagrams.*;

public class DiagramTestPortfolio implements Serializable {
    public final PrimarySpiderDiagram shadedBInsideA = createPrimarySD(null, null, asList(CopyContoursTransformerTest.zoneBMinusA, CopyContoursTransformerTest.zoneBAndA), asList(CopyContoursTransformerTest.zoneBAndA, CopyContoursTransformerTest.zoneAMinusB));
    private ArrayList<SpiderDiagram> spiderDiagrams;

    public DiagramTestPortfolio(){
        this.spiderDiagrams = new ArrayList<>(asList(
                seven,
//                four,
//                ten,
//                eleven,
//                DIAGRAM_SPEEDITH_PAPER_FIG7_1,
                getSDExample3(),
//                sixteen,
//                twenty,
//                five,
//                one,
//                two,
//                three,
//                six,
//                eight,
//                nine,
//                twelve,
//                thirteen,
//                fifteen,
//                oneLU,
//                sevenLabArrow,
//                sevenLabArrowLUCarCOP,
//                alternativeLUCarCOP,
//                threeColLUCarCOP,
//                threeLabArrow,
//                eighteenSp2,
//                andTwelveOne,
//                oneCD,
//                twoCD,
//                threeCD,
//                fourCD,
                //sevenLabArrowLUCarCOP,
                //threeColSpLUCarCOP,
                tenComplete,
                //fourCarCD,
                fiveCarCD,
                getFalseDiagram()
        ));
        //loadFromTestFiles();
    }

    public List<SpiderDiagram> getSpiderDiagrams() {
        return Collections.unmodifiableList(spiderDiagrams);
    }

    public int size() {
        return getSpiderDiagrams().size();
    }

    public SpiderDiagram getDiagramAt(int spiderDiagramIndex) {
        return getSpiderDiagrams().get(spiderDiagramIndex);
    }

    public static void main(String[] args) {
        new LotsOfDiagramsForm().setVisible(true);
    }

    private void loadFromTestFiles() {
        for (int i = 0; i < TestSpiderDiagrams.getSpiderDiagramSDTFilesCount(); i++) {
            try {
                spiderDiagrams.add(TestSpiderDiagrams.readSpiderDiagramFromSDTFile(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}