package speedith.core.lang.reader;

import static speedith.core.i18n.Translations.i18n;
import static speedith.core.lang.COPDiagram.SDTextArrowsAttribute;
import static speedith.core.lang.CarCDiagram.CDTextArrowCardinalitiesAttribute;
import static speedith.core.lang.CompleteCOPDiagram.SDTextSpiderComparatorAttribute;
import static speedith.core.lang.ConceptDiagram.CDTextArrowsAttribute;
import static speedith.core.lang.LUCOPDiagram.SDTextCurveLabelsAttribute;
import static speedith.core.lang.LUCOPDiagram.SDTextSpiderLabelsAttribute;
import static speedith.core.lang.LUCarCOPDiagram.SDTextArrowCardinalitiesAttribute;
import static speedith.core.lang.PrimarySpiderDiagram.SDTextHabitatsAttribute;
import static speedith.core.lang.PrimarySpiderDiagram.SDTextPresentZonesAttribute;
import static speedith.core.lang.PrimarySpiderDiagram.SDTextShadedZonesAttribute;
import static speedith.core.lang.PrimarySpiderDiagram.SDTextSpidersAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.antlr.runtime.tree.CommonTree;

import speedith.core.lang.Arrow;
import speedith.core.lang.COPDiagram;
import speedith.core.lang.CarCDiagram;
import speedith.core.lang.Cardinality;
import speedith.core.lang.CompleteCOPDiagram;
import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.FalseSpiderDiagram;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.LUCarCOPDiagram;
import speedith.core.lang.PDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.Region;
import speedith.core.lang.SpiderComparator;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.SpiderDiagrams;
import speedith.core.lang.Zone;





public class CDiagramsReader extends SpiderDiagramsReader{

	private CDiagramsReader(){
		super();
	}
	
    private static class ArrowTranslator extends ElementTranslator<Arrow> {

        public static final ArrowTranslator Instance = new ArrowTranslator();
        private ListTranslator<String> translator;

        private ArrowTranslator() {
            translator = new ListTranslator<>(SpiderDiagramsParser.SLIST, new StringTranslator());
        }

        @Override
        public Arrow fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<String> arrowSTT = translator.fromASTNode(treeNode);
            if ((arrowSTT == null) || (arrowSTT.size() > 4) || (arrowSTT.size() < 3)) {
                throw new ReadingException(i18n("ERR_TRANSLATE_ARROW"), treeNode);
            }
            
            if(arrowSTT.size() == 3){
            	return new Arrow(arrowSTT.get(0), arrowSTT.get(1), arrowSTT.get(2));
            }else  return new Arrow(arrowSTT.get(0), arrowSTT.get(1), arrowSTT.get(2), arrowSTT.get(3));     
            
        }
    }
    
    
    private static class SpiderComparatorTranslator extends ElementTranslator<SpiderComparator> {

        public static final SpiderComparatorTranslator Instance = new SpiderComparatorTranslator();
        private ListTranslator<String> translator;

        private SpiderComparatorTranslator() {
            translator = new ListTranslator<>(SpiderDiagramsParser.SLIST, new StringTranslator());
        }

        @Override
        public SpiderComparator fromASTNode(CommonTree treeNode) throws ReadingException {
        	
            ArrayList<String> spiderComparatorSTT = translator.fromASTNode(treeNode);
            
            if ((spiderComparatorSTT == null) || (spiderComparatorSTT.size() !=3)) {
                throw new ReadingException(i18n("ERR_TRANSLATE_SPIDER_COMPARATOR"), treeNode);
            }
            
            return new SpiderComparator(spiderComparatorSTT .get(0), spiderComparatorSTT .get(1), spiderComparatorSTT .get(2));          
        }
    }
    
    
    private static class CardinalityTranslator extends ElementTranslator<Cardinality> {

        public static final CardinalityTranslator Instance = new CardinalityTranslator();
        private ListTranslator<String> translator;
        
        @SuppressWarnings("unchecked")
        private CardinalityTranslator() {
            translator = new ListTranslator<>(SpiderDiagramsParser.SLIST, new StringTranslator());
        }

        @Override
        @SuppressWarnings("unchecked")
        public Cardinality fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<String> rawCardinalities = translator.fromASTNode(treeNode);
            if (rawCardinalities  == null || rawCardinalities .size() < 2 || rawCardinalities .size() > 2) {
                throw new ReadingException(i18n("ERR_TRANSLATE_CARDINALITY"), treeNode);
            }

        	   return new Cardinality(rawCardinalities .get(0), rawCardinalities.get(1));
  
        }
            
    }
    
    
    private static class LabelTranslator extends ElementTranslator<Map<String, String>> {

        public static final LabelTranslator Instance = new LabelTranslator();
        private ListTranslator<ArrayList<Object>> spiderLabelListTranslator;

        @SuppressWarnings("unchecked")
        private LabelTranslator() {
        	spiderLabelListTranslator = new ListTranslator<>(new TupleTranslator<>(new ElementTranslator<?>[]{StringTranslator.Instance, StringTranslator.Instance}));
        }

        @Override
        @SuppressWarnings("unchecked")
        public Map<String, String> fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<ArrayList<Object>> rawspiderLabels = spiderLabelListTranslator.fromASTNode(treeNode);
            if (rawspiderLabels == null || rawspiderLabels.size() < 1) {
                return null;
            }
            HashMap<String, String> spiderNameLabelMap = new HashMap<>();
            for (ArrayList<Object> rawspiderLabel : rawspiderLabels) {
                if (rawspiderLabel.size() == 2) {
                	spiderNameLabelMap.put((String) rawspiderLabel.get(0), (String) rawspiderLabel.get(1));
                }
            }
            return spiderNameLabelMap;
        }
    }
    
    
    
    
    private static class ArrowCardinalityTranslator extends ElementTranslator<Map<Arrow, Cardinality>> {
        public static final ArrowCardinalityTranslator Instance = new ArrowCardinalityTranslator();
        private ListTranslator<ArrayList<Object>> ArrowCardinalityListTranslator;

        @SuppressWarnings("unchecked")
        private ArrowCardinalityTranslator() {
        	ArrowCardinalityListTranslator = new ListTranslator<>(new TupleTranslator<>(new ElementTranslator<?>[]{ArrowTranslator.Instance, CardinalityTranslator.Instance}));
        }
        
        
        @Override
        @SuppressWarnings("unchecked")
        public Map<Arrow, Cardinality> fromASTNode(CommonTree treeNode) throws ReadingException {
            ArrayList<ArrayList<Object>> rawArrowCardinalities = ArrowCardinalityListTranslator.fromASTNode(treeNode);
            if (rawArrowCardinalities == null || rawArrowCardinalities.size() < 1) {
                return null;
            }
            HashMap<Arrow, Cardinality> arrowCardinalities = new HashMap<>();
            for (ArrayList<Object> rawArrowCardinality : rawArrowCardinalities) {
                if (rawArrowCardinality.size() == 2) {
                	arrowCardinalities.put((Arrow) rawArrowCardinality.get(0), (Cardinality) rawArrowCardinality.get(1));
                }
            }
            return arrowCardinalities;
        }
    	
    }
    

    public static class FalseSDTranslator extends GeneralSDTranslator<FalseSpiderDiagram> {

        public static final FalseSDTranslator Instance = new FalseSDTranslator();

        private FalseSDTranslator() {
            super(SpiderDiagramsParser.SD_FALSE);
        }

        @Override
        FalseSpiderDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            return FalseSpiderDiagram.getInstance();
        }
    }
    
    
  
    public static class COPTranslator extends  GeneralSDTranslator<COPDiagram>{
    	
        public static final COPTranslator Instance = new COPTranslator();

        private COPTranslator() {
            super(SpiderDiagramsParser.COP);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addMandatoryAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addMandatoryAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addMandatoryAttribute(SDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
        }

        @Override
        @SuppressWarnings("unchecked")
        COPDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            return COPDiagram.createCOPDiagram((Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
            		(Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    (Collection<Arrow>) attributes.get(SDTextArrowsAttribute).getKey());
        }
    }
    
    

    
    
    public static class LUCOPTranslator extends  GeneralSDTranslator<LUCOPDiagram>{
    	
        public static final LUCOPTranslator Instance = new LUCOPTranslator();

        private LUCOPTranslator() {
            super(SpiderDiagramsParser.LUCOP);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addMandatoryAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addMandatoryAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addMandatoryAttribute(SDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
            addMandatoryAttribute(SDTextSpiderLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextCurveLabelsAttribute,  LabelTranslator.Instance);
        }

        @Override
        @SuppressWarnings("unchecked")
        LUCOPDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            return LUCOPDiagram.createLUCOPDiagram((Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
            		(Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    (Collection<Arrow>) attributes.get(SDTextArrowsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextSpiderLabelsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextCurveLabelsAttribute).getKey()
                    );
        }
    }
    
    
    
    public static class LUCarCOPTranslator extends  GeneralSDTranslator<LUCarCOPDiagram>{
    	
        public static final LUCarCOPTranslator Instance = new LUCarCOPTranslator();

        private LUCarCOPTranslator() {
            super(SpiderDiagramsParser.LUCarCOP);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addMandatoryAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addMandatoryAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addMandatoryAttribute(SDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
            addMandatoryAttribute(SDTextSpiderLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextCurveLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextArrowCardinalitiesAttribute,  ArrowCardinalityTranslator.Instance);
        }

        @Override
        @SuppressWarnings("unchecked")
        LUCarCOPDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            return LUCarCOPDiagram.createLUCarCOPDiagram((Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
            		(Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    (Collection<Arrow>) attributes.get(SDTextArrowsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextSpiderLabelsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextCurveLabelsAttribute).getKey(),
                    (Map<Arrow, Cardinality>) attributes.get(SDTextArrowCardinalitiesAttribute).getKey()
                    );
        }
    }
    
    
    
    public static class CompleteCOPTranslator extends  GeneralSDTranslator<CompleteCOPDiagram>{
    	
        public static final CompleteCOPTranslator Instance = new CompleteCOPTranslator();

        private CompleteCOPTranslator() {
            super(SpiderDiagramsParser.CompleteCOP);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addMandatoryAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addMandatoryAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addMandatoryAttribute(SDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
            addMandatoryAttribute(SDTextSpiderLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextCurveLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextArrowCardinalitiesAttribute,  ArrowCardinalityTranslator.Instance);
            addMandatoryAttribute(SDTextSpiderComparatorAttribute,  
            		new ListTranslator<>(SpiderComparatorTranslator.Instance));
        }

        @Override
        @SuppressWarnings("unchecked")
        CompleteCOPDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            return SpiderDiagrams.createCompleteCOPDiagram((Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
            		(Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    (Collection<Arrow>) attributes.get(SDTextArrowsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextSpiderLabelsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextCurveLabelsAttribute).getKey(),
                    (Map<Arrow, Cardinality>) attributes.get(SDTextArrowCardinalitiesAttribute).getKey(),
                    (Collection<SpiderComparator>) attributes.get(SDTextSpiderComparatorAttribute).getKey()   
                    );
        }
    }
    
    
    
    
    
    
    public static class CDTranslator extends GeneralSDTranslator<ConceptDiagram> {

        public static final CDTranslator BinaryTranslator = new CDTranslator(SpiderDiagramsParser.CD_BINARY);

        public CDTranslator(int headTokenType) {
            super(headTokenType);
          //Zohreh: this could have been an instance of LUCarCOP
            addDefaultAttribute(SDTranslator.Instance);
            addMandatoryAttribute(CDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
        }
        
        @SuppressWarnings("unchecked")
		@Override
        ConceptDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            //String operator = (String) attributes.remove(SDTextOperatorAttribute).getKey();
        	Collection<Arrow> cdArrows = (Collection<Arrow>) attributes.remove(CDTextArrowsAttribute).getKey();
            ArrayList<PrimarySpiderDiagram> primaries = new ArrayList<>();
            int i = 1;
            Entry<Object, CommonTree> curCD, lastCD = null;
            while ((curCD = attributes.remove(ConceptDiagram.CDTextArgAttribute + i++)) != null && curCD.getKey() instanceof SpiderDiagram) {
            	primaries.add((PrimarySpiderDiagram) curCD.getKey());
                lastCD = curCD;
            }
            
            if (curCD != null) {
                throw new ReadingException(i18n("GERR_ILLEGAL_STATE"), (CommonTree) curCD.getValue().getChild(0));
            }
            
            if (!attributes.isEmpty()) {
                throw new ReadingException(i18n("ERR_TRANSLATE_UNKNOWN_ATTRIBUTES", attributes.keySet()), (CommonTree) attributes.values().iterator().next().getValue().getChild(0));
            }
            
            try {
//                return SpiderDiagrams.createConceptDiagram( primaries,(Collection<Arrow>) attributes.get(ConceptDiagram.CDTextArrowsAttribute).getKey());
                return SpiderDiagrams.createConceptDiagram( cdArrows,primaries);

            } catch (Exception e) {
                throw new ReadingException(e.getLocalizedMessage(), lastCD == null ? mainNode : (CommonTree) lastCD.getValue().getChild(0));
            }
        }
    }
    
    

    public static class CDCarTranslator extends GeneralSDTranslator<CarCDiagram> {

        public static final CDCarTranslator BinaryTranslator = new CDCarTranslator(SpiderDiagramsParser.CD_Car_BINARY);

        public CDCarTranslator(int headTokenType) {
            super(headTokenType);
          //Zohreh: this could have been an instance of LUCarCOP
            addDefaultAttribute(SDTranslator.Instance);
            addMandatoryAttribute(CDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
            addMandatoryAttribute(CDTextArrowCardinalitiesAttribute,  ArrowCardinalityTranslator.Instance);
        }
        
        @SuppressWarnings("unchecked")
		@Override
        CarCDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            //String operator = (String) attributes.remove(SDTextOperatorAttribute).getKey();
        	Collection<Arrow> cdArrows = (Collection<Arrow>) attributes.remove(CDTextArrowsAttribute).getKey();
        	Map<Arrow,Cardinality> cdArrowsCardinality = (Map<Arrow, Cardinality>) attributes.remove(CDTextArrowCardinalitiesAttribute).getKey(); 
            ArrayList<PrimarySpiderDiagram> primaries = new ArrayList<>();
            int i = 1;
            Entry<Object, CommonTree> curCD, lastCD = null;
            while ((curCD = attributes.remove(ConceptDiagram.CDTextArgAttribute + i++)) != null && curCD.getKey() instanceof SpiderDiagram) {
            	primaries.add((PrimarySpiderDiagram) curCD.getKey());
                lastCD = curCD;
            }
            
            if (curCD != null) {
                throw new ReadingException(i18n("GERR_ILLEGAL_STATE"), (CommonTree) curCD.getValue().getChild(0));
            }
            
            if (!attributes.isEmpty()) {
                throw new ReadingException(i18n("ERR_TRANSLATE_UNKNOWN_ATTRIBUTES", attributes.keySet()), (CommonTree) attributes.values().iterator().next().getValue().getChild(0));
            }
            
            try {
//                return SpiderDiagrams.createConceptDiagram( primaries,(Collection<Arrow>) attributes.get(ConceptDiagram.CDTextArrowsAttribute).getKey());
                return SpiderDiagrams.createCarCDiagram(cdArrows,cdArrowsCardinality,primaries);

            } catch (Exception e) {
                throw new ReadingException(e.getLocalizedMessage(), lastCD == null ? mainNode : (CommonTree) lastCD.getValue().getChild(0));
            }
        }
    }
    
    

    
    private static class PDTranslator extends  GeneralSDTranslator<LUCarCOPDiagram>{
    	
        public static final PDTranslator Instance = new PDTranslator();

        private PDTranslator() {
            super(SpiderDiagramsParser.PD);
            addMandatoryAttribute(SDTextSpidersAttribute, ListTranslator.StringListTranslator);
            addMandatoryAttribute(SDTextHabitatsAttribute, HabitatTranslator.Instance);
            addMandatoryAttribute(SDTextShadedZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addOptionalAttribute(SDTextPresentZonesAttribute, new ListTranslator<>(ZoneTranslator.Instance));
            addMandatoryAttribute(SDTextArrowsAttribute,  new ListTranslator<>(ArrowTranslator.Instance));
            addMandatoryAttribute(SDTextSpiderLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextCurveLabelsAttribute,  LabelTranslator.Instance);
            addMandatoryAttribute(SDTextArrowCardinalitiesAttribute,  ArrowCardinalityTranslator.Instance);
        }

        @Override
        @SuppressWarnings("unchecked")
        PDiagram createSD(Map<String, Entry<Object, CommonTree>> attributes, CommonTree mainNode) throws ReadingException {
            Entry<Object, CommonTree> presentZonesAttribute = attributes.get(SDTextPresentZonesAttribute);
            return PDiagram.createPDiagram((Collection<String>) attributes.get(SDTextSpidersAttribute).getKey(),
            		(Map<String, Region>) attributes.get(SDTextHabitatsAttribute).getKey(),
                    (Collection<Zone>) attributes.get(SDTextShadedZonesAttribute).getKey(),
                    presentZonesAttribute == null ? null : (Collection<Zone>) presentZonesAttribute.getKey(),
                    (Collection<Arrow>) attributes.get(SDTextArrowsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextSpiderLabelsAttribute).getKey(),
                    (Map<String, String>) attributes.get(SDTextCurveLabelsAttribute).getKey(),
                    (Map<Arrow, Cardinality>) attributes.get(SDTextArrowCardinalitiesAttribute).getKey()
                    );
        }
    }
    
    
    
    
    
}
