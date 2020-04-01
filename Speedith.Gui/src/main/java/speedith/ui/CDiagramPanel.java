package speedith.ui;

import static speedith.i18n.Translations.i18n;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import icircles.util.CannotDrawException;
import speedith.core.lang.cop.ConceptDiagram;
import speedith.ui.abstracts.CDAbstractDescription;
import speedith.ui.concretes.ConcreteArrow;
import speedith.ui.concretes.ConcreteCDiagram;
import speedith.ui.concretes.ConcreteCOPDiagram;


public class CDiagramPanel extends JPanel{
	
//	private static final Dimension MINIMUM_SIZE = new Dimension(500, 400);
//    private static final Dimension PREFERRED_SIZE = new Dimension(750, 450);
//    private ArrowPanel arrowPanel;
//    private ConceptDiagram diagram;
//    private HashMap<Integer, HashMap<String, Ellipse2D.Double>> circleMap;
//    private HashMap<String, Ellipse2D.Double> circleMapNid;
//
//    private Set<ConcreteArrow> arrows;
//
//    /**
//     * Create new diagram panel with nothing displayed in it.
//     */
//    public CDiagramPanel() {
//        initComponents();
//        drawNoDiagramLabel();
//    }
//
//    private void initComponents() {
//        setMinimumSize(MINIMUM_SIZE);
//        setPreferredSize(PREFERRED_SIZE);
//        setBackground(Color.WHITE);
//        arrowPanel = new ArrowPanel();
//    }
//
//    public final void setDiagram(ConceptDiagram diagram) {
//        if (this.diagram != diagram && diagram instanceof ConceptDiagram) {
//            this.diagram = diagram;
//            this.removeAll();
//            if (this.diagram != null) {
//                try {
//                    drawDiagram();
//                } catch (CannotDrawException e) {
//                    drawErrorLabel();
//                }
//            } else {
//                drawNoDiagramLabel();
//            }
//            validate();
//            repaint();
//        }
//    }
//
//    private void drawErrorLabel() {
//        JLabel errorLabel = new JLabel();
//        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        errorLabel.setText(i18n("PSD_LABEL_DISPLAY_ERROR"));
//        this.add(errorLabel);
//    }
//
//    private void drawNoDiagramLabel() {
//        JLabel noDiagramLbl = new JLabel();
//        noDiagramLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        noDiagramLbl.setText(i18n("CSD_PANEL_NO_DIAGRAM"));
//        this.add(noDiagramLbl);
//    }
//
//    private void drawDiagram() throws CannotDrawException {
//        if (diagram != null) {
//            arrows = new HashSet<>();
//            circleMap = new HashMap<>();
//            Set<ConcreteCOPDiagram> COPs;
//            this.setBorder(new EmptyBorder(20, 20, 20, 20));
//            this.setLayout(new GridLayout(1, 0));
//
//            final ConcreteCDiagram d;
//            CDAbstractDescription ad = COPDiagramVisualisation.getAbstractDescription(diagram);
//            d = ConcreteCDiagram.makeConcreteDiagram(ad, 300);
//            arrows.addAll(d.getArrows());
//            COPs = d.getCOPs();
//  
//            for (ConcreteCOPDiagram cop: COPs) {
//                arrows.addAll(cop.arrows);
//            }
//
//            int numRows = (int) Math.ceil((double)(COPs.size())/5);
//            this.setLayout(new GridLayout(numRows, 0, 75, 25));
//
//            // calculate the score for each, then draw most optimal
//            int height = (this.getHeight() - 40);
//            if (COPs.size() > 0) {
//                List<ConcreteCOPDiagram> copList = new ArrayList<>();
//                copList.addAll(COPs);
//                List<ConcreteCOPDiagram> optimalPermutation = copList;
//                if (getArrowsBetweenCOPs() > 0) {
//                    List<List<ConcreteCOPDiagram>> copPermutations = Permutation.generatePermutations(copList);
//                    optimalPermutation = copPermutations.get(0);
//                    double currentMinScore = Double.MAX_VALUE;
//                    for (List<ConcreteCOPDiagram> permutation : copPermutations) {
//                        circleMap.clear();
//                        int width = (this.getWidth() - 40 - (permutation.size() - 1) * 75) / permutation.size();
//                        int offset = 20;
//                        for (ConcreteCOPDiagram cop : permutation) {
//                            final SubDiagramPanel panel = new SubDiagramPanel(cop, width, height, offset);
//                            offset += 75 + width;
//                            circleMapNid.put(panel.getCircleMap());
//                        }
//
//                        addArrows(circleMap, getArrowsClone());
//                        double score = arrowPanel.getScore();
//                        if (score < currentMinScore) {
//                            currentMinScore = score;
//                            optimalPermutation = permutation;
//                        }
//                    }
//                }
//
//                circleMap.clear();
//                int width = (this.getWidth() - 40 - (COPs.size() - 1) * 75) / COPs.size();
//                int offset = 20;
//                for (final ConcreteCOPDiagram concreteCOP : optimalPermutation) {
//                    final SubDiagramPanel panel = new SubDiagramPanel(concreteCOP, width, height, offset);
//                    offset += 75 + width;
//                    circleMapNid.put(panel.getCircleMap());
//                    panel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
//                    panel.setVisible(true);
//                    add(panel);
//                }
//            }
//            
//            addArrows(circleMapNid, arrows);
//        }
//    }
//
//    ArrowPanel getArrowGlassPanel() {
//        return arrowPanel;
//    }
//
//    
//    private void addArrows(HashMap<String, Ellipse2D.Double> circleMapNid, Set<ConcreteArrow> arrows) {
//        if ((circleMap != null) && (circleMap.keySet().size() > 0)) {
//            arrowPanel = new ArrowPanel(arrows,circleMapNid);
//        }
//    }
//    
//
//
//    private Set<ConcreteArrow> getArrowsClone() {
//        Set<ConcreteArrow> clone = new HashSet<>(arrows.size());
//        for (ConcreteArrow arrow : arrows) {
//            clone.add(arrow.clone());
//        }
//        return clone;
//    }
//
//    private int getArrowsBetweenCOPs() {
//        int n = 0;
//        for (ConcreteArrow arrow: arrows) {
//            if (arrow.getParentId() == 0) {
//                n++;
//            }
//        }
//        return n;
//    }
//    
//    
//    
//    public static class Permutation {
//        private static List<ConcreteCOPDiagram> mList;
//        private static List<List<ConcreteCOPDiagram>> mPermutations;
//
//        public static List<List<ConcreteCOPDiagram>> generatePermutations(List<ConcreteCOPDiagram> list) {
//            mList = list;
//            mPermutations = new ArrayList<>();
//            helper(list.size(), mList);
//            return mPermutations;
//        }
//
//        private static void helper(int n, List<ConcreteCOPDiagram> list) {
//            if (n <= 1) {
//                mPermutations.add(new ArrayList<>(list));
//            } else {
//                for (int i = 0; i < n - 1; i++) {
//                    helper(n-1, list);
//
//                    if (n % 2 == 0) {
//                        swap(i, n-1);
//                    } else {
//                        swap(0, n-1);
//                    }
//                }
//                helper(n-1, list);
//            }
//        }
//
//        private static void swap(int i1, int i2) {
//            ConcreteCOPDiagram tmp = mList.get(i1);
//            mList.set(i1, mList.get(i2));
//            mList.set(i2, tmp);
//        }
//    }
}



