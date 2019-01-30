package speedith.ui;

import static speedith.i18n.Translations.i18n;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import icircles.util.CannotDrawException;
import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.ui.abstracts.CDAbstractDescription;
import speedith.ui.concretes.ConcreteArrow;
import speedith.ui.concretes.ConcreteCDiagram;
import speedith.ui.concretes.ConcreteCOPDiagram;


public class CDCirclesPanelEx3 extends javax.swing.JPanel {
	
	private static final long serialVersionUID = -2833382271045896800L;
	
	private static final Dimension MINIMUM_SIZE = new Dimension(500, 400);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 450);
    private ArrowPanel arrowPanel;
    private ConceptDiagram diagram;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private Set<ConcreteArrow> arrows;
    protected double scaleFactor = 1;
    
    private ArrowPanel glassPanel;

    /**
     * Create new diagram panel with nothing displayed in it.
     */
    public CDCirclesPanelEx3() {
        initComponents();
        drawNoDiagramLabel();
        glassPanel = new ArrowPanel();
    }
    
    public CDCirclesPanelEx3(ConceptDiagram diagram) {
    	glassPanel = new ArrowPanel();
        initComponents();
        setDiagram(diagram);
       // setArrowPanel();
    }
    
    

    
    
//    private void setArrowPanel() {
//        this.remove(glassPanel);
//        SwingUtilities.invokeLater(new Runnable() {
//
//      	  public void run() {
//                glassPanel = getArrowGlassPanel();                
//                setGlassPane(glassPanel);
//                getGlassPane().setVisible(true);
//                pack();
//      	  }
//      	});
//        validate();
//        repaint();
//    }
    
    
//    private void setArrowPanel() {
//        this.remove(glassPanel);
//        SwingUtilities.invokeLater(() -> {
//                glassPanel = this.getArrowGlassPanel();                
//                setGlassPane(glassPanel);
//                getGlassPane().setVisible(true);
//                pack();
//        });
//        validate();
//        repaint();
//    }
    

    private void initComponents() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
        setBackground(Color.WHITE);
        arrowPanel = new ArrowPanel();
    }

    public final void setDiagram(ConceptDiagram diagram) {
        if (this.diagram != diagram && diagram instanceof ConceptDiagram) {
            this.diagram = diagram;
            this.removeAll();
            if (this.diagram != null) {
                try {
                    drawDiagram();
                } catch (CannotDrawException e) {
                    drawErrorLabel();
                }
            } else {
                drawNoDiagramLabel();
            }
            validate();
            repaint();
        }
    }

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        this.add(errorLabel);
    }

    private void drawNoDiagramLabel() {
        JLabel noDiagramLbl = new JLabel();
        noDiagramLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDiagramLbl.setText(i18n("CSD_PANEL_NO_DIAGRAM"));
        this.add(noDiagramLbl);
    }

    private void drawDiagram() throws CannotDrawException {
        if (diagram != null) {
        	arrows = new HashSet<>();
            circleMap = new HashMap<>();
            Set<ConcreteCOPDiagram> COPs;
            this.setBorder(new EmptyBorder(20, 20, 20, 20));
            this.setLayout(new GridLayout(1, 0));

            CDAbstractDescription ad = COPDiagramVisualisation.getAbstractDescription(diagram);
                        
//            CDiagramCreator dc = new CDiagramCreator(ad);
//            ConcreteCDiagram conCd = dc.createDiagram(500);
            
            final ConcreteCDiagram conCd;
            conCd = ConcreteCDiagram.makeConcreteDiagram(ad,300);

            arrows.addAll(conCd.getArrows());
            
            
            COPs = conCd.getCOPs();
   
//            for (ConcreteCOPDiagram cop: COPs) {
//                arrows.addAll(cop.arrows);
//            }

            this.setLayout(new GridLayout(1, 0, 75, 25));

            int height = (this.getHeight() - 40);
            if (COPs.size() > 0) {
                List<ConcreteCOPDiagram> copList = new ArrayList<>();
                copList.addAll(COPs);
                if (conCd.getArrowsCount() > 0) {
                        circleMap.clear();
                        // 20 border on each side, x gap 75 between panels
                        int width = (this.getWidth() - 40 - (copList.size() - 1) * 75) / copList.size();
                        int offset = 20;
                        for (ConcreteCOPDiagram cop : copList) {
                            //final SubDiagramPanel panel = new SubDiagramPanel(cop, width, height, offset);
                        	
                            final ACirclesPanelEx panel = new ACirclesPanelEx(cop, width, height, offset);
                            
                        	
                            offset += 75 + width;
                            
                           //System.out.println("panel circle size"+panel.getCircleMap().size());
                            circleMap.putAll(panel.getCircleMap());
                        }

                        addArrows(circleMap, getArrowsClone());
                }

                circleMap.clear();
                int width = (this.getWidth() - 40 - (COPs.size() - 1) * 75) / COPs.size();
                int offset = 20;
                for (final ConcreteCOPDiagram concreteCOP : copList) {
                    //final SubDiagramPanel panel = new SubDiagramPanel(concreteCOP, width, height, offset);
                    final ACirclesPanelEx panel = new ACirclesPanelEx(concreteCOP, width, height, offset);
                    offset += 75 + width;
                    circleMap.putAll(panel.getCircleMap());
                    panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                    panel.setVisible(true);
                    add(panel);
                }
            }
        }
    }

    public ArrowPanel getArrowGlassPanel() {
        return arrowPanel;
    }

    private void addArrows(HashMap<String, Ellipse2D.Double> circleMap, Set<ConcreteArrow> arrows) {
    	//System.out.println("the arrow panel should be there"+circleMap.keySet().size());
        if ((circleMap != null) && (circleMap.keySet().size() > 0)) {
            arrowPanel = new ArrowPanel(arrows, circleMap);
     
        }
    }

    private Set<ConcreteArrow> getArrowsClone() {
        Set<ConcreteArrow> clone = new HashSet<>(arrows.size());
        for (ConcreteArrow arrow : arrows) {
            clone.add(arrow.clone());
        }
        return clone;
    }


}
