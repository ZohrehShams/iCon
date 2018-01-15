package icircles.gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import icircles.abstractDescription.AbstractDescription;

import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteSpiderFoot;
import icircles.concreteDiagram.ConcreteSpiderLeg;
import icircles.concreteDiagram.ConcreteZone;
import icircles.util.CannotDrawException;
import icircles.util.DEB;


public class CirclesPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ConcreteDiagram cd;
    DiagramPanel dp;

    ConcreteDiagram getDiagram() {
        return cd;
    }
    
    private void init(String desc, 
    		String failureMessage, 
    		ConcreteDiagram diagram, 
    		int size,
    		boolean useColors)
    {
        this.cd = diagram;
        setLayout(new BorderLayout());
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        int labelHeight = 0;
        if (desc != null && !desc.isEmpty()) {
        	//zohreh
            //setBorder(BorderFactory.createLineBorder(Color.black));
            JLabel jl = new JLabel(desc);
            Font f = new Font("Dialog", Font.PLAIN, 12);
            if (desc.length() > 24) {
                f = new Font("Dialog", Font.PLAIN, 8);
            } else if (desc.length() > 16) {
                f = new Font("Dialog", Font.PLAIN, 8);
            } else if (desc.length() > 14) {
                f = new Font("Dialog", Font.PLAIN, 10);
            }
            jl.setFont(f);
            jl.setHorizontalAlignment(JLabel.CENTER);
            add(jl, BorderLayout.NORTH);
            labelHeight = (int)(jl.getPreferredSize().getHeight()) + 1;
        }

        if(diagram!=null)
        	dp = new DiagramPanel(diagram, failureMessage, useColors);
        else
        	dp = new DiagramPanel(size, failureMessage);
        //dp.setBorder(BorderFactory.createLineBorder(Color.black));
    	
        this.setPreferredSize(new Dimension(size, size + labelHeight));
        dp.setPreferredSize(new Dimension(size, size + labelHeight));

        // some retired panel layout / sizing code:
//      dp.setMinimumSize(new Dimension(size, size));
//      dp.setMaximumSize(new Dimension(size, size));
//      this.setMinimumSize(new Dimension(size, size));
//      this.setMaximumSize(new Dimension(size, size));

//      JPanel containsDiag = new JPanel();
//      containsDiag.setLayout(new FlowLayout());
//      containsDiag.add(dp);
      //containsDiag.setBackground(Color.orange);
        add(dp, BorderLayout.CENTER);
    }
    
    // to display failures
    public CirclesPanel(
    		String desc, 
    		String failureMessage, 
    		int size) {
        init(desc, failureMessage, null, size, false/*useColors*/);
    }

    // constructor for non-null ConcreteDiagrams (will dereference for size)
    public CirclesPanel(
    		String desc, 
    		String failureMessage, 
    		ConcreteDiagram diagram, 
    		boolean useColors) {
        int size = diagram.getSize();
        init(desc, failureMessage, diagram, size, useColors);
    }

    private static final class DiagramPanel extends JPanel {

        private static final long serialVersionUID = 1L;
        ConcreteDiagram diagram;
        String failureMessage;
        private boolean useColors;
        double scaleFactor;
        private AffineTransform trans;
        boolean autoRescale;

        private void init(String failureMessage,
        		boolean useColors,
                int size)
        {
            setBackground(Color.white);
            this.failureMessage = failureMessage;
            this.useColors = useColors;
            setScaleFactor(1);
            setPreferredSize(new Dimension(size, size));
        }
        
        DiagramPanel(int size,
                String failureMessage) {
            init(failureMessage, false, size);
        }
        
        DiagramPanel(ConcreteDiagram diagram,
                String failureMessage,
                boolean useColors) {
            this.diagram = diagram;
            int size = diagram.getSize();
            init(failureMessage, useColors, size);
        }

        @Override
        public void doLayout() {
            super.doLayout();

            // Get the current width of this diagram panel and resize contents...
            if (autoRescale) {
                int size = diagram.getSize();
                if (size > 0) {
                    setScaleFactor(Math.min((float) this.getWidth() / size, (float) this.getHeight() / size));
                }
            }
        }

        /**
         * Indicates whether this diagram panel should rescale its drawn
         * contents to fit its current size.
         * @return 
         */
        public boolean isAutoRescale() {
            return autoRescale;
        }

        /**
         * Tells this panel whether it should rescale its drawn
         * contents to fit its current size.
         * @param autoRescale 
         */
        public void setAutoRescale(boolean autoRescale) {
            if (this.autoRescale != autoRescale) {
                this.autoRescale = autoRescale;
                if (this.autoRescale) {
                    this.invalidate();
                }
            }
        }

        /**
         * Sets the scale factor of the drawn contents to the new value.
         * <p>This merely scales the drawn contents (without affecting the
         * thickness of curves, size of spiders or fonts).</p>
         * <p>Note: this method does not change the size of the panel (not
         * even the preferred size).</p>
         * @param newScaleFactor the new factor by which to scale the drawn
         * contents.
         */
        void setScaleFactor(double newScaleFactor) {
            scaleFactor = newScaleFactor;
            recalculateScale();
        }

        private void recalculateScale() {
            this.trans = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
//            if (diagram != null) {
//                Dimension d = new Dimension((int) ((diagram.getBox().width + 5) * scaleFactor),
//                        (int) ((diagram.getBox().height + 5) * scaleFactor));
//                setPreferredSize(d);
//            }
        }

        @Override
        public void paint(Graphics g) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (diagram == null) {
                this.setBackground(Color.red);
                super.paint(g);
                if (failureMessage != null) {
                    g.drawString(failureMessage, 0, (int) (this.getHeight() * 0.5));
                }
                return;
            }
            // draw the diagram
            super.paint(g);

            // shaded zones
            g.setColor(Color.lightGray);
            ArrayList<ConcreteZone> zones = diagram.getShadedZones();
            for (ConcreteZone z : zones) {
                if (z.getColor() != null) {
                    g.setColor(z.getColor());
                } else {
                    g.setColor(Color.lightGray);
                }

                Area a = z.getShape(diagram.getBox());
                Area a_copy = (Area) a.clone();
                a_copy.transform(trans);
                ((Graphics2D) g).fill(a_copy);
            }
            ((Graphics2D) g).setStroke(new BasicStroke(2));
            ArrayList<CircleContour> circles = diagram.getCircles();
            for (CircleContour cc : circles) {
                if (useColors) {
                    Color col = cc.color();
                    if (col == null) {
                        col = Color.black;
                    }
                    g.setColor(col);
                } else {
                    g.setColor(Color.black);
                }
                Ellipse2D.Double circle = cc.getCircle();
                ((Graphics2D) g).draw(transformCircle(trans, circle));
                if (cc.ac.getLabel() == null) {
                    continue;
                }
                if (useColors) {
                    Color col = cc.color();
                    if (col == null) {
                        col = Color.black;
                    }
                    g.setColor(col);
                } else {
                    g.setColor(Color.black);
                }
                if (cc.stroke() != null) {
                    ((Graphics2D) g).setStroke(cc.stroke());
                } else {
                    ((Graphics2D) g).setStroke(new BasicStroke(2));
                }
                // TODO a proper way to place labels - it can't be a method in CircleContour,
                // we need the context in the ConcreteDiagram
                Font f = diagram.getFont();
                if (f != null) {
                    ((Graphics2D) g).setFont(f);
                }
                /*                                
                //TODO: ((Graphics2D) g).getFontMetrics(); //  for a string???
                // use the font metrics to adjust the anchor position
                
                JLabel jl = new JLabel("IGI");
                jl.setFont(font);
                jl.getWidth();
                jl.getHeight();
                jl.setLocation(arg0, arg1);
                 */

                ((Graphics2D) g).drawString(cc.ac.getLabel(),
                        (int) (cc.getLabelXPosition() * trans.getScaleX()),
                        (int) (cc.getLabelYPosition() * trans.getScaleY()));
            }
            g.setColor(Color.black);
            for (ConcreteSpider s : diagram.getSpiders()) {
                for (ConcreteSpiderFoot foot : s.feet) {
                    Ellipse2D.Double blob = foot.getBlob();
                    ((Graphics2D) g).fill(transformCircle(trans, blob));
                }
                for (ConcreteSpiderLeg leg : s.legs) {

                    ((Graphics2D) g).drawLine(
                            (int) (leg.from.getX() * scaleFactor),
                            (int) (leg.from.getY() * scaleFactor),
                            (int) (leg.to.getX() * scaleFactor),
                            (int) (leg.to.getY() * scaleFactor));
                }
                if (s.as.getName() == null) {
                    continue;
                }
                // TODO a proper way to place labels - it can't be a method in ConcreteSpider,
                // we need the context in the ConcreteDiagram
                ((Graphics2D) g).drawString(s.as.getName(),
                        (int) ((s.feet.get(0).getX() - 5) * trans.getScaleX()),
                        (int) ((s.feet.get(0).getY() + 18) * trans.getScaleY()));
            }
        }

        private Shape transformCircle(AffineTransform trans, Ellipse2D.Double circle) {
            // TODO Auto-generated method stub
            return new Ellipse2D.Double(
                    circle.x * scaleFactor,
                    circle.y * scaleFactor,
                    circle.width * scaleFactor,
                    circle.height * scaleFactor);
        }
    }

    /**
     * @param ad the description to be drawn
     * @param diagText the title of the drawing
     * @param size the size of the drawing panel
     * @return a drawing of an abstract diagram.
     */
    public static CirclesPanel makeCirclesPanel(AbstractDescription ad,
            String diagText,
            int size) {
        String failuremessage = "no failure";
        ConcreteDiagram cd = null;
        try {
            cd = ConcreteDiagram.makeConcreteDiagram(ad, size);
        } catch (CannotDrawException ex) {
            failuremessage = ex.message;
        }
        if(cd != null)
        	return new CirclesPanel(diagText, failuremessage, cd, true); // do use colors
        else
        	return new CirclesPanel(diagText, failuremessage, size); // do use colors
    }

    ArrayList<CircleContour> getAllCircles() {
        return cd.getCircles();
    }

    void setColor(CircleContour cc, Color c) {
        cc.setColor(c);
        repaint();
    }

    void setStroke(CircleContour cc, Stroke s) {
        cc.setStroke(s);
        repaint();
    }

    /**
     * @return the size for which the concrete diagram has been drawn.
     * <p>This is the size of the drawn contents of this panel when the {@link
     * CirclesPanel#setScaleFactor(double) scale factor} is set to 1.</p>
     * <p>The size of the drawn diagram can be calculated by multiplying
     * {@link CirclesPanel#getScaleFactor()} with
     * {@link CirclesPanel#getOriginalSize()}.</p>
     */
    int getOriginalSize() {
        return this.cd.getSize();
    }

    public double getScaleFactor() {
        return dp.scaleFactor;
    }

    public void setScaleFactor(double scale) {
        dp.setScaleFactor(scale);
    }

    /**
     * @return a value that indicates whether this diagram panel should rescale its drawn
     * contents to fit its current size.
     */
    public boolean isAutoRescale() {
        return dp.isAutoRescale();
    }

    /**
     * @param autoRescale Tells this panel whether it should rescale its drawn
     * contents to fit its current size.
     */
    public void setAutoRescale(boolean autoRescale) {
        dp.setAutoRescale(autoRescale);
    }
}
