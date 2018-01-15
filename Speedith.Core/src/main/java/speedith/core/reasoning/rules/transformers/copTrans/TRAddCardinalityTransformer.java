package speedith.core.reasoning.rules.transformers.copTrans;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import speedith.core.lang.Cardinality;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.IdTransformer;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.LUCarCOPDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.TransformationException;
import speedith.core.reasoning.args.ArrowArg;

public class TRAddCardinalityTransformer  extends IdTransformer{
	
	private ArrowArg arrowArg;
    private final boolean applyForward;

	
	public TRAddCardinalityTransformer(ArrowArg arrowArg,boolean applyForward)  {
	    this.arrowArg = arrowArg;
	    this.applyForward = applyForward;
	}

    @Override
    public SpiderDiagram transform(PrimarySpiderDiagram psd,
                                   int diagramIndex,
                                   ArrayList<CompoundSpiderDiagram> parents,
                                   ArrayList<Integer> childIndices) {
    	
    	int subDiagramIndex = arrowArg.getSubDiagramIndex();
    	LUCarCOPDiagram luCarCop = (LUCarCOPDiagram) psd;
    	
    	
    	if (diagramIndex == subDiagramIndex) {
 
        		assertDiagramContainTargetArrows(luCarCop);
        		
        		if (! luCarCop.getArrows().contains(arrowArg.getArrow())){
        			throw new TransformationException("The arrow does not exists in the diagram.");
        		}
        		
        		
        		if(luCarCop.getArrowCardinalities().containsKey(arrowArg.getArrow())){
        			throw new TransformationException("The arrow should not have a cardinality.");
        		}
        		
        		String comparator; 
        		comparator = JOptionPane.showInputDialog(null,"Enter one of the following three comparators: >= = <= ");
            	if ((! comparator.equals(">=")) && (! comparator.equals("=")) && (! comparator.equals("<="))){
                	JOptionPane.showMessageDialog(null,"The type of comparator has to be one of the following three "
                			+ "comparators: >= = <= .","Input error",JOptionPane.ERROR_MESSAGE);
        			throw new TransformationException("The type of comparator has to be one of the following three "
                			+ "comparators: >= = <= .");
            	}
            	
            	String number;
        		number = JOptionPane.showInputDialog(null,"Enter a natural number:");
        		if (Integer.parseInt(number) <= 0){
                	JOptionPane.showMessageDialog(null,"Cardinality cannot be a negative number.","Input error",JOptionPane.ERROR_MESSAGE);
        			throw new TransformationException("Cardinality cannot be a negative number.");
            	}

        	   return luCarCop.addCardinality(arrowArg.getArrow(),new Cardinality(comparator,number));
    	}
    	return psd;
    }
    
    
    
    private void assertDiagramContainTargetArrows(LUCOPDiagram currentDiagram) {
        if (!currentDiagram.getArrows().contains(arrowArg.getArrow())) {
            throw new TransformationException("The target diagram does not contain the target arrow(s).");
        }
    }

}
