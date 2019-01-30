package speedith.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import speedith.ui.concretes.ConcreteArrow;


public class ArrowPanel extends JComponent {
    private static final Dimension MINIMUM_SIZE = new Dimension(500, 300);
    private static final Dimension PREFERRED_SIZE = new Dimension(750, 300);
    private static float dash[] = {5.0f};
    private static final BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
    private static final Font basicFont =  new Font("Courier", Font.PLAIN, 18);
    private static final Font smallFont = new Font("Courier", Font.PLAIN, 15);
    private static final int PENALTY = 1000;

    private Set<ConcreteArrow> arrows;
    private HashMap<String, Ellipse2D.Double> circleMap;
    private List<Ellipse2D.Double> dotList;
    private HashMap<String, Integer> existingArrowCount;
    private HashMap<String, Integer> offsets;
    private Set<ConcreteArrow> toBeUpdated;
    boolean isPD;
    
    private javax.swing.JLabel lblNullSD;

    public ArrowPanel() {
    	//System.out.println("Empty constrcutor used?");
        this.arrows = new HashSet<>();
        this.circleMap = new HashMap<>();
        this.existingArrowCount = new HashMap<>();
        this.offsets = new HashMap<>();
        this.toBeUpdated = new HashSet<>();
        isPD = false;
    }

    ArrowPanel(Set<ConcreteArrow> arrows,  HashMap<String, Ellipse2D.Double> circleMap) {
    	//System.out.println("is this constrcutor used?");
        this.arrows = arrows;
        this.circleMap = circleMap;
        this.existingArrowCount = new HashMap<>();
        this.offsets = new HashMap<>();
        this.toBeUpdated = new HashSet<>();
        isPD = false;
        init();
    }

    private void init() {
        setMinimumSize(MINIMUM_SIZE);
        setPreferredSize(PREFERRED_SIZE);
        
        lblNullSD = new javax.swing.JLabel();
        lblNullSD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNullSD.setFont(new Font("Serif", Font.PLAIN, 24));
        lblNullSD.setText("hello");
        add(lblNullSD, java.awt.BorderLayout.CENTER);

        if (circleMap.keySet().size() > 0) {
            for (ConcreteArrow a : arrows) {
                    a.initArrow();
                
            }
            //updateOutermostArrows();
            //calculateOffsets();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
    	
    	//System.out.println("you are cming here at all?");
    	
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.translate(0, 22);
        g.setColor(new Color(10, 86, 0, 255));
        g.setFont(basicFont);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        if (arrows != null && circleMap != null) {

            for (ConcreteArrow a : arrows) {

                if (a.aa.get_type() == "dashed") {
                    g2d.setStroke(dashed);
                }
                
                Line2D.Double tmpArrow = new Line2D.Double();
                tmpArrow = a.getScaledArrow(1.0);
                g2d.draw(tmpArrow);
                
                g2d.setStroke(new BasicStroke());

          		double dy = tmpArrow.y2  - tmpArrow.y1;
        		double dx = tmpArrow.x2  - tmpArrow.x1;
        		
        		
        		double phi = Math.toRadians(40);
        		
        		int barb = 20;
        		
        		double theta = Math.atan2(dy, dx);
        		
        		double x, y, rho = theta + phi;
        		
                for(int j = 0; j < 2; j++)
                {
                    x = tmpArrow.x2 - barb * Math.cos(rho);
                    y = tmpArrow.y2 - barb * Math.sin(rho);
                    g2d.draw(new Line2D.Double(tmpArrow.x2, tmpArrow.y2, x, y));
                    rho = theta - phi;
                }

                if (a.aa.getLabel() != null) {
                	g.setFont(new Font("Helvetica", Font.BOLD,  12));
                	if (a.aa.getCardinality() == null){
                        g2d.drawString(a.aa.getLabel(),
                                (int) (a.getLabelXPosition())+5,
                                (int) (a.getLabelYPosition()));
                	}else{
                        g2d.drawString(a.aa.getLabel()+a.aa.getCardinality().toString(),
                                (int) (a.getLabelXPosition())+5,
                                (int) (a.getLabelYPosition()));
                	}
                }  
            }
        }
    }

}
