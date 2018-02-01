package speedith.ui;

import icircles.util.CannotDrawException;
import speedith.core.lang.COPDiagram;
import speedith.core.lang.CompoundSpiderDiagram;
import speedith.core.lang.ConceptDiagram;
import speedith.core.lang.LUCOPDiagram;
import speedith.core.lang.NullSpiderDiagram;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.core.lang.SpiderDiagram;
import speedith.core.lang.reader.COPDiagramReader;
import speedith.core.lang.reader.ReadingException;
import speedith.core.lang.reader.SpiderDiagramsReader;
import speedith.core.reasoning.args.selection.SelectionStep;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import static speedith.i18n.Translations.i18n;

public class COPDiagramPanel extends javax.swing.JPanel {

    private static final Dimension PrimaryDiagramSize = new Dimension(150, 150);

    private javax.swing.JPanel diagrams;

    private SpiderDiagram diagram;

    private int highlightMode = SelectionStep.None;


    public COPDiagramPanel() {
        this(null);
    }


    public COPDiagramPanel(SpiderDiagram diagram) {
        initComponents();
        setDiagram(diagram);
    }

    public SpiderDiagram getDiagram() {
        return diagram;
    }

    
    public final void setDiagram(SpiderDiagram diagram) {
        if (this.diagram != diagram) {
            this.diagram = diagram;
            diagrams.removeAll();
            if (this.diagram != null) {
                try {
                    drawDiagram();
                } catch (Exception ex) {
                	ex.printStackTrace();
                    drawErrorLabel();
                }
            } else {
                drawNoDiagramLabel();
            }
            applyHighlightModeToPanels();
            validate();
            repaint();
        }
    }

    public String getDiagramString() {
        return this.diagram == null ? "" : this.diagram.toString();
    }
    
    //Zohreh: I'm using COPDiagramReader instead of Spider's one
    public void setDiagramString(String diagram) throws ReadingException {
        if (diagram == null || diagram.isEmpty()) {
            setDiagram(null);
        } else {
            //setDiagram(SpiderDiagramsReader.readSpiderDiagram(diagram));
            setDiagram(COPDiagramReader.readSpiderDiagram(diagram));
        }
    }


    public int getHighlightMode() {
        return highlightMode;
    }


    public void setHighlightMode(int highlightMode) {
        if (this.highlightMode != (highlightMode & SelectionStep.All)) {
            this.highlightMode = highlightMode & SelectionStep.All;
            applyHighlightModeToPanels();
        }
    }

    public void addSpiderDiagramClickListener(SpiderDiagramClickListener l) {
        this.listenerList.add(SpiderDiagramClickListener.class, l);
    }


    public void removeSpiderDiagramClickListener(SpiderDiagramClickListener l) {
        this.listenerList.remove(SpiderDiagramClickListener.class, l);
    }


    public SpiderDiagramClickListener[] getSpiderDiagramClickListeners() {
        return listenerList.getListeners(SpiderDiagramClickListener.class);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        diagrams = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        diagrams.setBackground(new java.awt.Color(255, 255, 255));
        diagrams.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onMouseClicked(evt);
            }
        });

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
    }// </editor-fold>//GEN-END:initComponents

    private void onMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMouseClicked
        fireSpiderDiagramClicked(0, null);
    }//GEN-LAST:event_onMouseClicked

    protected void fireSpiderDiagramClicked(int subDiagramIndex, DiagramClickEvent info) {
        SpiderDiagramClickListener[] ls = listenerList.getListeners(SpiderDiagramClickListener.class);
        if (ls != null && ls.length > 0) {
            SpiderDiagramClickEvent e = new SpiderDiagramClickEvent(this, diagram, info, subDiagramIndex);
            for (int i = 0; i < ls.length; i++) {
                ls[i].spiderDiagramClicked(e);
            }
        }
    }

    private void drawErrorLabel() {
        JLabel errorLabel = new JLabel();
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
        diagrams.add(errorLabel);
    }


    private void drawNoDiagramLabel() {
        JLabel noDiagramLbl = new JLabel();
        noDiagramLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        noDiagramLbl.setText(i18n("CSD_PANEL_NO_DIAGRAM"));
        diagrams.add(noDiagramLbl);
    }


    private void drawDiagram() throws CannotDrawException {
        if (diagram != null) {
        	if (diagram instanceof ConceptDiagram){
        		//drawConceptDiagram2((ConceptDiagram) diagram);
        		drawPrimaryCDiagram2((ConceptDiagram) diagram);
        	}else
            if (diagram instanceof CompoundSpiderDiagram) {
                CompoundSpiderDiagram csd = (CompoundSpiderDiagram) diagram;
                switch (csd.getOperator()) {
                    case Conjunction:
                    case Disjunction:
                    case Equivalence:
                    case Implication:
                        drawInfixDiagram(csd);
                        break;
                    case Negation:
                        drawPrefixDiagram(csd);
                        break;
                    default:
                        throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
                }
            } else if (diagram instanceof PrimarySpiderDiagram) {
            	if (diagram instanceof COPDiagram){
            		drawCOPDiagram((COPDiagram) diagram);
            	} else {
            		drawPrimaryDiagram((PrimarySpiderDiagram) diagram);
            	}
            } else if (diagram instanceof NullSpiderDiagram) {
                drawNullSpiderDiagram();
            } else {
                throw new IllegalArgumentException(i18n("SD_PANEL_UNKNOWN_DIAGRAM_TYPE"));
            }
        }
    }


    private void drawInfixDiagram(CompoundSpiderDiagram csd) throws CannotDrawException {
        if (csd != null && csd.getOperandCount() > 0) {
            int gridx = 0, nextSubdiagramIndex = 1;
            diagrams.setLayout(new GridBagLayout());

            // Now start adding the panels of operand diagrams onto the surface
            Iterator<SpiderDiagram> sdIter = csd.getOperands().iterator();
            nextSubdiagramIndex = addSpiderDiagramPanel(nextSubdiagramIndex, sdIter.next(), gridx);
            while (sdIter.hasNext()) {
                addOperatorPanel(csd, ++gridx);
                nextSubdiagramIndex = addSpiderDiagramPanel(nextSubdiagramIndex, sdIter.next(), ++gridx);
            }
        } else {
            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        }
    }

    
    
    private void addOperatorPanel(CompoundSpiderDiagram csd, int gridx) {
        diagrams.add(registerSubdiagramClickListener(new OperatorPanel(csd.getOperator(), getFont()), 0), getSubdiagramLayoutConstraints(gridx, false, 0, 0));
    }
    

    
    //Zohreh: I'm abusing compound method without operator to do the primaries (operands in compound) for CD
    private void drawPrimaryCDiagram(ConceptDiagram cd) throws CannotDrawException {
        if (cd != null && cd.getPrimaryCount() > 0) {
            int gridx = 0, nextSubdiagramIndex = 1;

            GridBagConstraints gbc = getSubdiagramLayoutConstraints(0, true, 1, 1);
            
            // Now start adding the panels of operand diagrams onto the surface
            Iterator<PrimarySpiderDiagram> sdIter = cd.getPrimaries().iterator();
            nextSubdiagramIndex = addSpiderDiagramPanel(nextSubdiagramIndex, sdIter.next(), gridx);
            while (sdIter.hasNext()) {
                //addOperatorPanel(cd, ++gridx);
                nextSubdiagramIndex = addSpiderDiagramPanel(nextSubdiagramIndex, sdIter.next(), ++gridx);
            }
        } else {
            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        }
    }
    
    
    private void drawPrimaryCDiagram2(ConceptDiagram cd) throws CannotDrawException {
        if (cd != null && cd.getPrimaryCount() > 0) {
            int gridx = 0, nextSubdiagramIndex = 1;
            diagrams.setLayout(new GridBagLayout());
            //GridBagConstraints gbc = getSubdiagramLayoutConstraints(0, true, 1, 1);
            
            // Now start adding the panels of operand diagrams onto the surface
            Iterator<PrimarySpiderDiagram> sdIter = cd.getPrimaries().iterator();
            nextSubdiagramIndex = addSpiderDiagramPanel(nextSubdiagramIndex, sdIter.next(), gridx);
            while (sdIter.hasNext()) {
                //addOperatorPanel(cd, ++gridx);
                nextSubdiagramIndex = addSpiderDiagramPanel(nextSubdiagramIndex, sdIter.next(), ++gridx);
            }
            //diagrams.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd), 0),gbc);
            
            //diagrams.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd), 0));
            
//            Window frame = SwingUtilities.windowForComponent(diagrams);
//            JPanel glass = (JPanel) frame.getGlassPane(); 
            JRootPane glass = diagrams.getRootPane();
            JPanel arrows= (JPanel) glass.getGlassPane();
            
            arrows.setVisible(true);
            //arrows.setLayout(diagrams.getLayout());
            arrows.setLayout(new GridBagLayout());
//            JButton glassButton = new JButton("Hide");
//            arrows.add(glassButton);
            arrows.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd), 0));

        } else {
            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        }
    }
    
    
    private void drawConceptDiagram(ConceptDiagram cd) throws CannotDrawException {
    	if (cd != null && cd.getPrimaryCount() > 0) {
            diagrams.setLayout(new GridBagLayout());
            GridBagConstraints gbc = getSubdiagramLayoutConstraints(0, true, 1, 1);
            diagrams.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd), 0), gbc);	
    	}else{
    		throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
    	}
    }
    
    private void drawConceptDiagram2(ConceptDiagram cd) throws CannotDrawException {
    	if (cd != null && cd.getPrimaryCount() > 0) {
            diagrams.setLayout(new GridBagLayout());
            GridBagConstraints gbc = getSubdiagramLayoutConstraints(0, true, 1, 1);
            JPanel mother = registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd), 0);
            diagrams.add(mother, gbc);	
            mother.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd.getPrimary(0)), 1));
            mother.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cd.getPrimary(0)), 2));
            
    	}else{
    		throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
    	}
    }
    
    
    //Zohreh: we are using COPDiagramVisualisation instead of DiagramVisualisation 
    private int addSpiderDiagramPanel(int nextSubdiagramIndex, SpiderDiagram curSD, int gridx) throws CannotDrawException {
        GridBagConstraints gridBagConstraints;
        JPanel result;
        // a CannotDrawException can only be thrown, if curSD is a PrimarySpiderDiagram
        // (only then do we create a SpeedithCirclesPanel). If drawing fails, explicitly create
        // a SpiderDiagramPanel for the primary diagram. This triggers the error label
        // drawing in the constructor.
        // This is a hack to catch the exception as early as possible, so that the rest
        // of a compound diagram can still be drawn. To change this, the structure of SpiderDiagramPanel
        // and the logic of drawing SpiderDiagrams would have to be changed significantly.
        // One disadvantage of this solution: If a diagram cannot be drawn, iCircles is
        // called twice to draw it (one time here, and the other time in the constructor in the
        // catch clause)
        try {
        	//Zohreh
            //result = DiagramVisualisation.getSpiderDiagramPanel(curSD);
            result = COPDiagramVisualisation.getSpiderDiagramPanel(curSD);
            //result.setBorder(BorderFactory.createEtchedBorder());
        } catch (CannotDrawException e) {
            result = new SpiderDiagramPanel(curSD);
            result.setBorder(BorderFactory.createEmptyBorder());
        }
        //Zohreh: sdp only registers a click listener for a given panel.
        JPanel sdp = registerSubdiagramClickListener(result, nextSubdiagramIndex);
        
        gridBagConstraints = getSubdiagramLayoutConstraints(gridx, true, sdp.getPreferredSize().width, 1);
        diagrams.add(sdp, gridBagConstraints);
        return nextSubdiagramIndex + curSD.getSubDiagramCount();
    }

    /**
     * This function registers a click listener to the given panel. The
     * registered listener will invoke the {@link SpiderDiagramPanel#addSpiderDiagramClickListener(speedith.ui.SpiderDiagramClickListener)
     * spider diagram click event} of this panel.
     */
    //Zohreh: diagramPanel instanceof SpiderDiagramPanel has changed to instanceof COPDiagramPanel
    private JPanel registerSubdiagramClickListener(JPanel diagramPanel, final int subdiagramIndex) {
        if (diagramPanel instanceof SpeedithCirclesPanel) {
            SpeedithCirclesPanel cp = (SpeedithCirclesPanel) diagramPanel;
            cp.setPreferredSize(PrimaryDiagramSize);
            cp.setMinimumSize(PrimaryDiagramSize);
            cp.addDiagramClickListener(new DiagramClickListener() {

                public void spiderClicked(SpiderClickedEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex, e);
                }

                public void zoneClicked(ZoneClickedEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex, e);
                }

                public void contourClicked(ContourClickedEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex, e);
                }
                
                public void arrowClicked(ArrowClickedEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex, e);
                }
            }); 
        } else if (diagramPanel instanceof COPDiagramPanel) {
        	//System.out.println("Do you ever use this?");
            COPDiagramPanel sdp = (COPDiagramPanel) diagramPanel;
            sdp.addSpiderDiagramClickListener(new SpiderDiagramClickListener() {

                public void spiderDiagramClicked(SpiderDiagramClickEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex + e.getSubDiagramIndex(), e.getDetailedEvent());
                }
            });
        }else if ((diagramPanel instanceof NullSpiderDiagramPanel) || (diagramPanel instanceof CDCirclesPanelEx) ) {
            diagramPanel.setFont(getFont());
            diagramPanel.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex, null);
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });
        } else if (diagramPanel instanceof OperatorPanel) {
            diagramPanel.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    fireSpiderDiagramClicked(subdiagramIndex, null);
                }

                public void mousePressed(MouseEvent e) {
                }

                public void mouseReleased(MouseEvent e) {
                }

                public void mouseEntered(MouseEvent e) {
                }

                public void mouseExited(MouseEvent e) {
                }
            });
        } else {
            throw new IllegalStateException(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        }
        return diagramPanel;
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


    private void drawPrefixDiagram(CompoundSpiderDiagram csd) throws CannotDrawException {
        if (csd != null && csd.getOperandCount() == 1) {
            diagrams.setLayout(new GridBagLayout());
            addOperatorPanel(csd, 0);
            addSpiderDiagramPanel(1, csd.getOperand(0), 1);
        } else {
            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        }
    }
    
    
    //Zohreh:I'm not sure if it plays a role at all, but DiagramVis has changed to COPDiagramVis 
    private void drawPrimaryDiagram(PrimarySpiderDiagram psd) throws CannotDrawException {
        if (psd == null) {
            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        } else {
            diagrams.setLayout(new GridBagLayout());
            GridBagConstraints gbc = getSubdiagramLayoutConstraints(0, true, 1, 1);
//            JPanel result;
//            result = DiagramVisualisation.getSpiderDiagramPanel(psd);
//            result.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
//            diagrams.add(registerSubdiagramClickListener(result, 0), gbc);
            diagrams.add(registerSubdiagramClickListener(DiagramVisualisation.getSpiderDiagramPanel(psd), 0), gbc);
        }
    }
    
    
    private void drawCOPDiagram(COPDiagram cop) throws CannotDrawException {
        if (cop == null) {
            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
        } else {
            diagrams.setLayout(new GridBagLayout());
            GridBagConstraints gbc = getSubdiagramLayoutConstraints(0, true, 1, 1);
            diagrams.add(registerSubdiagramClickListener(COPDiagramVisualisation.getSpiderDiagramPanel(cop), 0), gbc);
        }
    }
    
    

    
  
//     private void drawConceptDiagram(ConceptDiagram cd) throws CannotDrawException {
//    	 System.out.println("there must be something wrong here");
//        if (cd != null && cd.getPrimaryCount() > 0) {
//            diagrams.setLayout(new GridBagLayout());
//            addSpiderDiagramPanel(1, cd.getPrimary(0), 0);
//            addSpiderDiagramPanel(2, cd.getPrimary(1), 2);
//        } else {
//            throw new AssertionError(speedith.core.i18n.Translations.i18n("GERR_ILLEGAL_STATE"));
//        }
//    }


    private void drawNullSpiderDiagram() {
        diagrams.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new java.awt.GridBagConstraints();
        diagrams.add(registerSubdiagramClickListener(new NullSpiderDiagramPanel(getFont()), 0), gbc);
    }


    private void applyHighlightModeToPanels() {
        if (this.diagram != null) {
            for (Component component : this.diagrams.getComponents()) {
                if (component instanceof SpeedithCirclesPanel) {
                    ((SpeedithCirclesPanel) component).setHighlightMode(highlightMode);
                } else if (component instanceof SpiderDiagramPanel) {
                    ((SpiderDiagramPanel) component).setHighlightMode(highlightMode);
                } else if(component instanceof COPDiagramPanel){
                	((COPDiagramPanel) component).setHighlightMode(highlightMode);
                }else if (component instanceof OperatorPanel) {
                    OperatorPanel operatorPanel = (OperatorPanel) component;
                    operatorPanel.setHighlighting((highlightMode & SelectionStep.Operators) == SelectionStep.Operators);
                } else if (component instanceof NullSpiderDiagramPanel) {
                    NullSpiderDiagramPanel nsdp = (NullSpiderDiagramPanel) component;
                    nsdp.setHighlighting((highlightMode & SelectionStep.NullSpiderDiagrams) == SelectionStep.NullSpiderDiagrams);
                }
            }
        }
    }
    // </editor-fold>
}
