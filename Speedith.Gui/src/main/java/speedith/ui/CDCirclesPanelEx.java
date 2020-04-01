package speedith.ui;

import javax.swing.JPanel;
import icircles.util.CannotDrawException;
import speedith.ui.concretes.ConcreteCDiagram;
import speedith.ui.concretes.ConcreteCOPDiagram;

import java.awt.BasicStroke;
import java.awt.GridBagConstraints;

	
public class CDCirclesPanelEx extends JPanel{
	
	
    private javax.swing.JPanel diagrams; 
	private ConcreteCDiagram diagram;
	
	final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,
    		10.0f, dash1, 0.0f);
    
    
    public CDCirclesPanelEx(ConcreteCDiagram diagram) {
    	System.out.println("A");
    	initComponents();
    	this.diagram =diagram;
    	try {
			drawCD(diagram);
		} catch (CannotDrawException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//        validate();
//        repaint();
    }
    	
    public CDCirclesPanelEx() {
        this(null);
    }
    
    
    
    protected void drawCD(ConcreteCDiagram diagram) throws CannotDrawException {
        GridBagConstraints gridBagConstraints;
        JPanel result;
        
    	for (ConcreteCOPDiagram cond: diagram.getPrimaries()){
    		System.out.println("are you cominf gere at akk?");
    		ConcreteCOPDiagram conDiagram = (ConcreteCOPDiagram) cond;
    		result =  new SpeedithCirclesPanel(conDiagram);
//    		diagrams.add(result);
//    		add(result);
//    		this.add(result);
    		
            gridBagConstraints = getSubdiagramLayoutConstraints(0, true, result.getPreferredSize().width, 1);
    		diagrams.add(result,gridBagConstraints);
    	}
    }
    
    
    private void initComponents() {
        diagrams = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        diagrams.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout diagramsLayout = new javax.swing.GroupLayout(diagrams);
        diagrams.setLayout(diagramsLayout);
        diagramsLayout.setHorizontalGroup(
                diagramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGap(0, 330, Short.MAX_VALUE)
        );
        diagramsLayout.setVerticalGroup(
                diagramsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGap(0, 234, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(diagrams, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                      .addComponent(diagrams, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }


    
    private GridBagConstraints getSubdiagramLayoutConstraints(int gridx, boolean fill, int weightx, int weighty) {
        GridBagConstraints gridBagConstraints;
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = fill ? java.awt.GridBagConstraints.BOTH : GridBagConstraints.NONE;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.insets.set(3, 2, 3, 2);
        return gridBagConstraints;
    }
    
    
//    private int addSpiderDiagramPanel(int nextSubdiagramIndex, ConceretSpiderDiagram curSD, int gridx) throws CannotDrawException {
//        GridBagConstraints gridBagConstraints;
//        JPanel result;
//        try {
//            result = COPDiagramVisualisation.getSpiderDiagramPanel(curSD);
//            
//        } catch (CannotDrawException e) {
//            result = new SpiderDiagramPanel(curSD);
//            result.setBorder(BorderFactory.createEmptyBorder());
//        }
//        JPanel sdp = registerSubdiagramClickListener(result, nextSubdiagramIndex);
//        
//        gridBagConstraints = getSubdiagramLayoutConstraints(gridx, true, sdp.getPreferredSize().width, 1);
//        this.add(sdp, gridBagConstraints);
//        return nextSubdiagramIndex + curSD.getSubDiagramCount();
//    }
    
    
    

}
