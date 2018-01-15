package speedith.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import icircles.concreteDiagram.CircleContour;
import icircles.concreteDiagram.ConcreteDiagram;
import icircles.concreteDiagram.ConcreteDiagrams;
import icircles.concreteDiagram.ConcreteSpider;
import icircles.concreteDiagram.ConcreteSpiderFoot;
import icircles.concreteDiagram.ConcreteSpiderLeg;
import icircles.concreteDiagram.ConcreteZone;
import icircles.gui.CirclesPanelEx;
import speedith.core.lang.PrimarySpiderDiagram;
import speedith.icircles.util.ICirclesToSpeedith;

public class ACirclesPanelEx extends JPanel{
	
	/**
     * This stroke is used to draw contours if no special stroke is specified
     * for them.
     */
    protected static final BasicStroke DEFAULT_CONTOUR_STROKE = new BasicStroke(2);
    /**
     * This stroke is used to draw highlighted legs, outlines, and contours.
     */
    protected static final BasicStroke HIGHLIGHT_STROKE = new BasicStroke(3.5f);
    
    
    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER,
    		10.0f, dash1, 0.0f);
    
    
    protected static final Color HIGHLIGHT_LEG_COLOUR = Color.BLUE;
    protected static final Color HIGHLIGHTED_FOOT_COLOUR = Color.RED;
    protected static final Color HIGHLIGHT_STROKE_COLOUR = Color.RED;
    protected static final Color HIGHLIGHT_ZONE_COLOUR = new Color(0x70ff0000, true); // Color.RED;
    protected static final double HIGHLIGHTED_FOOT_SCALE = 1.4;
    protected static final long serialVersionUID = 0x5b7fd085e1dff1a0L;
    // Get a DOMImplementation.
    private DOMImplementation domImpl =
            GenericDOMImplementation.getDOMImplementation();
    // Create an instance of org.w3c.dom.Document.
    private String svgNS = "http://www.w3.org/2000/svg";
    private Document document = domImpl.createDocument(svgNS, "svg", null);
    // Create an instance of the SVG Generator.
    private SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
    /**
     * The diagram that will actually be drawn in this panel.
     */
    
    protected ConcreteDiagrams diagram;
    /**
     * The scale that should be applied to the circles in this diagram (due to
     * the resizing of this panel).
     */
    protected double scaleFactor = 1;
    
    protected AffineTransform trans = new AffineTransform();
    
    protected AffineTransform tx = new AffineTransform();
    
    private CircleContour highlightedContour = null;
    private ConcreteZone highlightedZone = null;
    private ConcreteSpiderFoot highlightedFoot = null;
    
    //Zohreh
    private ConcreteArrow highlightedArrow = null;

    /**
     * Creates new panel that will draw the given diagram.
     *
     * @param diagram the diagram to be drawn by this panel.
     */
    public ACirclesPanelEx(ConcreteDiagrams diagram) {
        initComponents();
        resetDiagram(diagram);
        resizeContents();
    }

    /**
     * Creates new panel with no diagram.
     */
    public ACirclesPanelEx() {
        this(null);
    }

    /**
     * Gets the diagram that is currently being displayed by this panel.
     *
     * @return the diagram that is currently being displayed by this panel.
     */
    public ConcreteDiagrams getDiagram() {
        return diagram;
    }

    /**
     * Sets the diagram that should be displayed by this panel.
     *
     * @param diagram the diagram that should be displayed by this panel.
     */
    public void setDiagram(ConcreteDiagrams diagram) {
        if (this.diagram != diagram) {
            resetDiagram(diagram);
        }
    }

    /**
     * Returns the factor by which the {@link ConcreteDiagram drawn concrete
     * diagram} is scaled. The scaling is done so that the diagram fits the panel
     * nicely.
     *
     * @return the factor by which the {@link ConcreteDiagram drawn concrete
     *         diagram} is scaled.
     */
    public final double getScaleFactor() {
        return scaleFactor;
    }

    /**
     * Sets the scale factor of the drawn contents to the new value. <p>This
     * merely scales the drawn contents (without affecting the thickness of
     * curves, size of spiders or fonts).</p> <p>Note: this method does not
     * change the size of the panel (not even the preferred size).</p>
     *
     * @param newScaleFactor the new factor by which to scale the drawn
     *                       contents.
     */
    private void setScaleFactor(double newScaleFactor) {
        scaleFactor = newScaleFactor;
        recalculateTransform();
        repaint();
    }

    /**
     * Converts the given point from the coordinate system of this panel to the
     * coordinate system of the {@link CirclesPanelEx#getDiagram() displayed
     * diagram}. <p>Use this method to issue queries on which diagrammatic
     * element is located under the given point.</p>
     *
     * @param p the coordinates which to convert.
     * @return the coordinates of the corresponding point in the coordinate
     *         system of the {@link CirclesPanelEx#getDiagram() displayed diagram}.
     */
    public Point toDiagramCoordinates(Point p) {
        p.x -= getCenteringTranslationX();
        p.x /= scaleFactor;
        p.y -= getCenteringTranslationY();
        p.y /= scaleFactor;
        return p;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (diagram == null) {
            this.setBackground(Color.red);
            g2d.clearRect(0, 0, getWidth(), getHeight());
            super.paint(g);
        } else {
        	
            super.paint(g);
            
            g2d.setBackground(Color.white);
            g2d.clearRect(0, 0, getWidth(), getHeight());
            
        	if(diagram instanceof ConcreteDiagram){
        		ConcreteDiagram conDiagram = (ConcreteDiagram) diagram;
            
            // This centers the diagram onto the drawing area.
            g.translate(getCenteringTranslationX(), getCenteringTranslationY());

            
            //Zohreh: This line adds a box around every primary diagram.
//            g.setColor(Color.red);
//            g2d.drawRect(0, 0, getWidth()-8, getHeight()-1);
            
            
            g.setColor(Color.black);
            Area az = new Area(conDiagram.getBox());
            g2d.setStroke(new BasicStroke(3F));
            Area naz = az.createTransformedArea(trans);
          g2d.draw(new Line2D.Double(naz.getBounds().getX()+naz.getBounds().getWidth(), naz.getBounds().getY(), naz.getBounds().getX()+naz.getBounds().getWidth(), naz.getBounds().getY()+naz.getBounds().getHeight()));
          g2d.draw( new Line2D.Double(naz.getBounds().getX(),  naz.getBounds().getY(), naz.getBounds().getX()+naz.getBounds().getWidth(),  naz.getBounds().getY()));
          g2d.draw(new Line2D.Double( naz.getBounds().getX(), naz.getBounds().getY(), naz.getBounds().getX(), naz.getBounds().getY()+naz.getBounds().getHeight()));
          g2d.draw(new Line2D.Double( naz.getBounds().getX(), naz.getBounds().getY()+naz.getBounds().getHeight(), naz.getBounds().getX()+naz.getBounds().getWidth(), naz.getBounds().getY()+naz.getBounds().getHeight()));

          
//            g2d.drawLine((int)(naz.getBounds().getX()+naz.getBounds().getWidth()), (int) naz.getBounds().getY(), (int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()));
//            g2d.drawLine((int) naz.getBounds().getX(), (int) naz.getBounds().getY(), (int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) naz.getBounds().getY());
//            g2d.drawLine((int) naz.getBounds().getX(), (int) naz.getBounds().getY(), (int) naz.getBounds().getX(), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()));
//            g2d.drawLine((int) naz.getBounds().getX(), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()), (int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()));
//            g2d.draw(naz.getBounds());
            
            
            // Draw shaded zones:
            g.setColor(Color.lightGray);
            for (ConcreteZone z : conDiagram.getShadedZones()) {
                if (z.getColor() != null) {
                    g.setColor(z.getColor());
                } else {
                    g.setColor(Color.lightGray);
                }

                // TODO: The box of the diagram should not change. Put the box
                // into the constructor? NOTE: It would not add much to execution
                // speed. The 'getShape' function already caches the calculated
                // shape.
                Area a = z.getShape(conDiagram.getBox());
                g2d.fill(a.createTransformedArea(trans));
            }

            // Draw the highlighted zone:
            if (getHighlightedZone() != null) {
                Color oldColour = g2d.getColor();
                g2d.setColor(HIGHLIGHT_ZONE_COLOUR);
                g2d.fill(getHighlightedZone().getShape(conDiagram.getBox()).createTransformedArea(trans));
                //Zohreh
                g2d.setColor(Color.BLACK);
                g2d.draw(getHighlightedZone().getShape(conDiagram.getBox()).createTransformedArea(trans));
                g2d.setColor(oldColour);
            }

            // Draw contours:
            g2d.setStroke(DEFAULT_CONTOUR_STROKE);
            ArrayList<CircleContour> circles = conDiagram.getCircles();
            Ellipse2D.Double tmpCircle = new Ellipse2D.Double();
            for (CircleContour cc : circles) {
                Color col = cc.color();
                if (col == null) {
                    col = Color.black;
                }
                g.setColor(col);
                transformCircle(scaleFactor, cc.getCircle(), tmpCircle);
                g2d.draw(tmpCircle);
                if (cc.ac.getLabel() == null) {
                    continue;
                }
                g.setColor(col);
                if (cc.stroke() != null) {
                    g2d.setStroke(cc.stroke());
                } else {
                    g2d.setStroke(DEFAULT_CONTOUR_STROKE);
                }
                // TODO a proper way to place labels - it can't be a method in CircleContour,
                // we need the context in the ConcreteDiagram
                Font f = conDiagram.getFont();
                if (f != null) {
                    g2d.setFont(f);
                }
                /*
                 * //TODO: g2d.getFontMetrics(); // for a string??? // use the
                 * font metrics to adjust the anchor position
                 *
                 * JLabel jl = new JLabel("IGI"); jl.setFont(font);
                 * jl.getWidth(); jl.getHeight(); jl.setLocation(arg0, arg1);
                 */

                //Zohreh: Instead of printing the unique identifier of curves (unlike for spiders, they are called labels!) 
                //we print the curve names, that is if it's not null: the curve is not unlabelled.
//                g2d.drawString(cc.ac.getLabel(),
//                               (int) (cc.getLabelXPosition() * trans.getScaleX()) + 5,
//                               (int) (cc.getLabelYPosition() * trans.getScaleY()) + 5);
                
                if (cc.ac.getName() != null) {
                    g2d.drawString(cc.ac.getName(),
                            (int) (cc.getLabelXPosition() * trans.getScaleX()) + 5 ,
                            (int) (cc.getLabelYPosition() * trans.getScaleY()) + 5);
                }
            }

            ConcreteSpider highlightedSpider = getHighlightedFoot() == null ? null : getHighlightedFoot().getSpider();
            g.setColor(Color.black);
            for (ConcreteSpider s : conDiagram.getSpiders()) {
                // Reset the stroke and the colour if the spider is highlighted.
                Color oldColor = null;
                Stroke oldStroke = null;
                if (highlightedSpider == s) {
                    oldColor = g2d.getColor();
                    g2d.setColor(HIGHLIGHT_LEG_COLOUR);
                    oldStroke = g2d.getStroke();
                    g2d.setStroke(HIGHLIGHT_STROKE);
                }

                for (ConcreteSpiderLeg leg : s.legs) {

                    g2d.drawLine(
                            (int) (leg.from.getX() * scaleFactor),
                            (int) (leg.from.getY() * scaleFactor),
                            (int) (leg.to.getX() * scaleFactor),
                            (int) (leg.to.getY() * scaleFactor));
                }

                for (ConcreteSpiderFoot foot : s.feet) {
                    foot.getBlob(tmpCircle);
                    Color oldColor2 = g2d.getColor();
                    translateCircleCentre(scaleFactor, tmpCircle, tmpCircle);
                    if (getHighlightedFoot() == foot) {
                        oldColor2 = g2d.getColor();
                        g2d.setColor(HIGHLIGHTED_FOOT_COLOUR);
                        scaleCircleCentrally(tmpCircle, HIGHLIGHTED_FOOT_SCALE);
                    }
                    g2d.fill(tmpCircle);
                    if (getHighlightedFoot() == foot) {
                        g2d.setColor(oldColor2);
                    }
                }
                
                
                //Zohreh
                if (s.as.getName() == null) {
                    continue;
                }
                
                

                
                
                
                // TODO a proper way to place labels - it can't be a method in ConcreteSpider,
                // we need the context in the ConcreteDiagram
//                g2d.drawString(s.as.getName(),
//                               (int) ((s.feet.get(0).getX()) * trans.getScaleX()) - 5,
//                               (int) ((s.feet.get(0).getY()) * trans.getScaleY()) - 10);
                
                
                //Zohreh
              if (s.as.getLabel() != null) {
                  g2d.drawString(s.as.getLabel(),
                          (int) ((s.feet.get(0).getX()) * trans.getScaleX()) - 5,
                          (int) ((s.feet.get(0).getY()) * trans.getScaleY()) - 10);
              }

                
                

                // Reset the stroke and colour appropriatelly.
                if (highlightedSpider == s) {
                    g2d.setColor(oldColor);
                    g2d.setStroke(oldStroke);
                }
            }

            // Draw the highlighted circle contour
            if (getHighlightedContour() != null) {
                // Reset the stroke and the colour of the highlighted outline.
                g2d.setColor(HIGHLIGHT_STROKE_COLOUR);
                g2d.setStroke(HIGHLIGHT_STROKE);
                transformCircle(scaleFactor, getHighlightedContour().getCircle(), tmpCircle);
                g2d.draw(tmpCircle);
            }
            
            
            //Draw arrows
            if (conDiagram instanceof ConcreteCOPDiagram){
            	ConcreteCOPDiagram copDiagram = (ConcreteCOPDiagram) conDiagram;
            	
            	Line2D.Double tmpArrow = new Line2D.Double();
            	
            	for (ConcreteArrow a : copDiagram.getArrows()){
            		
            		if(a.aa.get_type().equals("solid")){
                		//Line2D.Double tmpArrow = new Line2D.Double();
                		g2d.setStroke(DEFAULT_CONTOUR_STROKE);
                		g2d.setColor(Color.black);  	
                		tmpArrow = a.getScaledArrow(scaleFactor);
                		g2d.draw(tmpArrow);
                		
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
            		}
            		
            		if(a.aa.get_type().equals("dashed")){
                		//Line2D.Double tmpArrow = new Line2D.Double();
                		g2d.setStroke(dashed);
                		g2d.setColor(Color.black);  	
                		tmpArrow = a.getScaledArrow(scaleFactor);
                		g2d.draw(tmpArrow);
                		
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
            		}
            		
            		
            		
                    if (a.aa.getLabel() != null) {
                    	g.setFont(new Font("Helvetica", Font.BOLD,  12));
                    	if (a.aa.getCardinality() == null){
                            g2d.drawString(a.aa.getLabel(),
                                    (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                                    (int) (a.getLabelYPosition() * trans.getScaleY()));
                    	}else{
                            g2d.drawString(a.aa.getLabel()+a.aa.getCardinality().toString(),
                                    (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                                    (int) (a.getLabelYPosition() * trans.getScaleY()));
                    	}
                    }  		
            	}
            	

                //Zohreh: Draw the highlighted arrows 
                if (getHighlightedArrow()!= null) {
              		g2d.setColor(Color.black);
            		g2d.setStroke(HIGHLIGHT_STROKE);
            		tmpArrow = getHighlightedArrow().getScaledArrow(scaleFactor);
            		g2d.draw(tmpArrow);
 	
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
                }
            	
            }
            
        }
            if (diagram instanceof ConcreteCDiagram){
            	ConcreteCDiagram cDiagram = (ConcreteCDiagram) diagram;
            
            	Line2D.Double tmpArrow = new Line2D.Double();
            
            	
            	///////////////////////////////////////////
       
            	for (ConcreteCOPDiagram cond: cDiagram.getPrimaries()){
            		ConcreteCOPDiagram conDiagram = (ConcreteCOPDiagram) cond;
            		
            		//new ACirclesPanelEx(cond);
            		//this.add(new ACirclesPanelEx(conDiagram));
                    
                    // This centers the diagram onto the drawing area.
                    //g.translate(getCenteringTranslationX(), getCenteringTranslationY());

                    
                    //Zohreh: This line adds a box around every primary diagram.
//                    g.setColor(Color.red);
//                    g2d.drawRect(0, 0, getWidth()-8, getHeight()-1);
                    
                    g.setColor(Color.black);
                    Area az = new Area(conDiagram.getBox());
                    g2d.setStroke(new BasicStroke(3F));
                    Area naz = az.createTransformedArea(trans);
                  g2d.draw(new Line2D.Double(naz.getBounds().getX()+naz.getBounds().getWidth(), naz.getBounds().getY(), naz.getBounds().getX()+naz.getBounds().getWidth(), naz.getBounds().getY()+naz.getBounds().getHeight()));
                  g2d.draw( new Line2D.Double(naz.getBounds().getX(),  naz.getBounds().getY(), naz.getBounds().getX()+naz.getBounds().getWidth(),  naz.getBounds().getY()));
                  g2d.draw(new Line2D.Double( naz.getBounds().getX(), naz.getBounds().getY(), naz.getBounds().getX(), naz.getBounds().getY()+naz.getBounds().getHeight()));
                  g2d.draw(new Line2D.Double( naz.getBounds().getX(), naz.getBounds().getY()+naz.getBounds().getHeight(), naz.getBounds().getX()+naz.getBounds().getWidth(), naz.getBounds().getY()+naz.getBounds().getHeight()));
                  
//                    g2d.drawLine((int)(naz.getBounds().getX()+naz.getBounds().getWidth()), (int) naz.getBounds().getY(), (int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()));
//                    g2d.drawLine((int) naz.getBounds().getX(), (int) naz.getBounds().getY(), (int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) naz.getBounds().getY());
//                    g2d.drawLine((int) naz.getBounds().getX(), (int) naz.getBounds().getY(), (int) naz.getBounds().getX(), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()));
//                    g2d.drawLine((int) naz.getBounds().getX(), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()), (int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) (naz.getBounds().getY()+naz.getBounds().getHeight()));
//                    g2d.draw(naz.getBounds());
                    
                    
                    // Draw shaded zones:
                    g.setColor(Color.lightGray);
                    for (ConcreteZone z : conDiagram.getShadedZones()) {
                        if (z.getColor() != null) {
                            g.setColor(z.getColor());
                        } else {
                            g.setColor(Color.lightGray);
                        }

                        // TODO: The box of the diagram should not change. Put the box
                        // into the constructor? NOTE: It would not add much to execution
                        // speed. The 'getShape' function already caches the calculated
                        // shape.
                        Area a = z.getShape(conDiagram.getBox());
                        g2d.fill(a.createTransformedArea(trans));
                    }

                    // Draw the highlighted zone:
                    if (getHighlightedZone() != null) {
                        Color oldColour = g2d.getColor();
                        g2d.setColor(HIGHLIGHT_ZONE_COLOUR);
                        g2d.fill(getHighlightedZone().getShape(conDiagram.getBox()).createTransformedArea(trans));
                        //Zohreh
                        g2d.setColor(Color.BLACK);
                        g2d.draw(getHighlightedZone().getShape(conDiagram.getBox()).createTransformedArea(trans));
                        g2d.setColor(oldColour);
                    }

                    // Draw contours:
                    g2d.setStroke(DEFAULT_CONTOUR_STROKE);
                    ArrayList<CircleContour> circles = conDiagram.getCircles();
                    Ellipse2D.Double tmpCircle = new Ellipse2D.Double();
                    for (CircleContour cc : circles) {
                        Color col = cc.color();
                        if (col == null) {
                            col = Color.black;
                        }
                        g.setColor(col);
                        transformCircle(scaleFactor, cc.getCircle(), tmpCircle);
                        g2d.draw(tmpCircle);
                        if (cc.ac.getLabel() == null) {
                            continue;
                        }
                        g.setColor(col);
                        if (cc.stroke() != null) {
                            g2d.setStroke(cc.stroke());
                        } else {
                            g2d.setStroke(DEFAULT_CONTOUR_STROKE);
                        }
                        // TODO a proper way to place labels - it can't be a method in CircleContour,
                        // we need the context in the ConcreteDiagram
                        Font f = conDiagram.getFont();
                        if (f != null) {
                            g2d.setFont(f);
                        }
                        /*
                         * //TODO: g2d.getFontMetrics(); // for a string??? // use the
                         * font metrics to adjust the anchor position
                         *
                         * JLabel jl = new JLabel("IGI"); jl.setFont(font);
                         * jl.getWidth(); jl.getHeight(); jl.setLocation(arg0, arg1);
                         */

                        //Zohreh: Instead of printing the unique identifier of curves (unlike for spiders, they are called labels!) 
                        //we print the curve names, that is if it's not null: the curve is not unlabelled.
//                        g2d.drawString(cc.ac.getLabel(),
//                                       (int) (cc.getLabelXPosition() * trans.getScaleX()) + 5,
//                                       (int) (cc.getLabelYPosition() * trans.getScaleY()) + 5);
                        
                        if (cc.ac.getName() != null) {
                            g2d.drawString(cc.ac.getName(),
                                    (int) (cc.getLabelXPosition() * trans.getScaleX()) + 5 ,
                                    (int) (cc.getLabelYPosition() * trans.getScaleY()) + 5);
                        }
                    }

                    ConcreteSpider highlightedSpider = getHighlightedFoot() == null ? null : getHighlightedFoot().getSpider();
                    g.setColor(Color.black);
                    for (ConcreteSpider s : conDiagram.getSpiders()) {
                        // Reset the stroke and the colour if the spider is highlighted.
                        Color oldColor = null;
                        Stroke oldStroke = null;
                        if (highlightedSpider == s) {
                            oldColor = g2d.getColor();
                            g2d.setColor(HIGHLIGHT_LEG_COLOUR);
                            oldStroke = g2d.getStroke();
                            g2d.setStroke(HIGHLIGHT_STROKE);
                        }

                        for (ConcreteSpiderLeg leg : s.legs) {

                            g2d.drawLine(
                                    (int) (leg.from.getX() * scaleFactor),
                                    (int) (leg.from.getY() * scaleFactor),
                                    (int) (leg.to.getX() * scaleFactor),
                                    (int) (leg.to.getY() * scaleFactor));
                        }

                        for (ConcreteSpiderFoot foot : s.feet) {
                            foot.getBlob(tmpCircle);
                            Color oldColor2 = g2d.getColor();
                            translateCircleCentre(scaleFactor, tmpCircle, tmpCircle);
                            if (getHighlightedFoot() == foot) {
                                oldColor2 = g2d.getColor();
                                g2d.setColor(HIGHLIGHTED_FOOT_COLOUR);
                                scaleCircleCentrally(tmpCircle, HIGHLIGHTED_FOOT_SCALE);
                            }
                            g2d.fill(tmpCircle);
                            if (getHighlightedFoot() == foot) {
                                g2d.setColor(oldColor2);
                            }
                        }
                        
                        
                        //Zohreh
                        if (s.as.getName() == null) {
                            continue;
                        }
                        
                        

                        
                        
                        
                        // TODO a proper way to place labels - it can't be a method in ConcreteSpider,
                        // we need the context in the ConcreteDiagram
//                        g2d.drawString(s.as.getName(),
//                                       (int) ((s.feet.get(0).getX()) * trans.getScaleX()) - 5,
//                                       (int) ((s.feet.get(0).getY()) * trans.getScaleY()) - 10);
                        
                        
                        //Zohreh
                      if (s.as.getLabel() != null) {
                          g2d.drawString(s.as.getLabel(),
                                  (int) ((s.feet.get(0).getX()) * trans.getScaleX()) - 5,
                                  (int) ((s.feet.get(0).getY()) * trans.getScaleY()) - 10);
                      }

                        
                        

                        // Reset the stroke and colour appropriatelly.
                        if (highlightedSpider == s) {
                            g2d.setColor(oldColor);
                            g2d.setStroke(oldStroke);
                        }
                    }

                    // Draw the highlighted circle contour
                    if (getHighlightedContour() != null) {
                        // Reset the stroke and the colour of the highlighted outline.
                        g2d.setColor(HIGHLIGHT_STROKE_COLOUR);
                        g2d.setStroke(HIGHLIGHT_STROKE);
                        transformCircle(scaleFactor, getHighlightedContour().getCircle(), tmpCircle);
                        g2d.draw(tmpCircle);
                    }
                    
                    
                    	
                    	Line2D.Double tmpArrows = new Line2D.Double();
                    	
                    	for (ConcreteArrow a : conDiagram.getArrows()){
                    		
                    		if(a.aa.get_type().equals("solid")){
                        		//Line2D.Double tmpArrow = new Line2D.Double();
                        		g2d.setStroke(DEFAULT_CONTOUR_STROKE);
                        		g2d.setColor(Color.black);  	
                        		tmpArrows = a.getScaledArrow(scaleFactor);
                        		g2d.draw(tmpArrows);
                        		
                        		double dy = tmpArrows.y2  - tmpArrows.y1;
                        		double dx = tmpArrows.x2  - tmpArrows.x1;
                        		
                        		double phi = Math.toRadians(40);
                        		
                        		int barb = 20;
                        		
                        		double theta = Math.atan2(dy, dx);
                        		
                        		double x, y, rho = theta + phi;
                        		
                                for(int j = 0; j < 2; j++)
                                {
                                    x = tmpArrows.x2 - barb * Math.cos(rho);
                                    y = tmpArrows.y2 - barb * Math.sin(rho);
                                    g2d.draw(new Line2D.Double(tmpArrows.x2, tmpArrows.y2, x, y));
                                    rho = theta - phi;
                                }
                    		}
                    		
                    		if(a.aa.get_type().equals("dashed")){
                        		//Line2D.Double tmpArrow = new Line2D.Double();
                        		g2d.setStroke(dashed);
                        		g2d.setColor(Color.black);  	
                        		tmpArrows = a.getScaledArrow(scaleFactor);
                        		g2d.draw(tmpArrows);
                        		
                        		double dy = tmpArrows.y2  - tmpArrows.y1;
                        		double dx = tmpArrows.x2  - tmpArrows.x1;
                        		
                        		double phi = Math.toRadians(40);
                        		
                        		int barb = 20;
                        		
                        		double theta = Math.atan2(dy, dx);
                        		
                        		double x, y, rho = theta + phi;
                        		
                                for(int j = 0; j < 2; j++)
                                {
                                    x = tmpArrows.x2 - barb * Math.cos(rho);
                                    y = tmpArrows.y2 - barb * Math.sin(rho);
                                    g2d.draw(new Line2D.Double(tmpArrows.x2, tmpArrows.y2, x, y));
                                    rho = theta - phi;
                                }
                    		}
                    		
                    		
                    		
                            if (a.aa.getLabel() != null) {
                            	g.setFont(new Font("Helvetica", Font.BOLD,  12));
                            	if (a.aa.getCardinality() == null){
                                    g2d.drawString(a.aa.getLabel(),
                                            (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                                            (int) (a.getLabelYPosition() * trans.getScaleY()));
                            	}else{
                                    g2d.drawString(a.aa.getLabel()+a.aa.getCardinality().toString(),
                                            (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                                            (int) (a.getLabelYPosition() * trans.getScaleY()));
                            	}
                            }  		
                    	}
                    	

                        //Zohreh: Draw the highlighted arrows 
                        if (getHighlightedArrow()!= null) {
                      		g2d.setColor(Color.black);
                    		g2d.setStroke(HIGHLIGHT_STROKE);
                    		tmpArrows = getHighlightedArrow().getScaledArrow(scaleFactor);
                    		g2d.draw(tmpArrows);
         	
                    		double dy = tmpArrows.y2  - tmpArrows.y1;
                    		double dx = tmpArrows.x2  - tmpArrows.x1;
                    		
                    		double phi = Math.toRadians(40);
                    		
                    		int barb = 20;
                    		
                    		double theta = Math.atan2(dy, dx);
                    		
                    		double x, y, rho = theta + phi;
                    		
                            for(int j = 0; j < 2; j++)
                            {
                                x = tmpArrows.x2 - barb * Math.cos(rho);
                                y = tmpArrows.y2 - barb * Math.sin(rho);
                                g2d.draw(new Line2D.Double(tmpArrows.x2, tmpArrows.y2, x, y));
                                rho = theta - phi;
                            }
                        }
                        
                        
                        Rectangle space = new Rectangle(naz.getBounds());
                        space.setLocation((int) (naz.getBounds().getX()+naz.getBounds().getWidth()), (int) naz.getBounds().getY());
                        g.setColor(Color.red);
                        g2d.fill(space);
                    
                }
            		 
            	
            	
            	
            	
            	
            	
            	
                ///////////////////////////////////////////
            	for (ConcreteArrow a : cDiagram.getArrows()){
            		
            		if(a.aa.get_type().equals("solid")){
                		//Line2D.Double tmpArrow = new Line2D.Double();
                		//g2d.setStroke(DEFAULT_CONTOUR_STROKE);
            			
                		g2d.setColor(Color.GREEN);  	                		
                		//System.out.println("X1=" + a.x_s+ "Y1=" +a.y_s+ "X2=" + a.x_t + "Y2=" + a.y_t);

                		
                		tmpArrow = a.getScaledArrow(scaleFactor);
                		g2d.draw(tmpArrow);
                		
                		//System.out.println("X1=" + tmpArrow.getX1() + "Y1=" +tmpArrow.getY1() + "X2=" + tmpArrow.getX2() + "Y2=" + tmpArrow.getY2());
                		
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
            		}
            		
            		if(a.aa.get_type().equals("dashed")){
                		//Line2D.Double tmpArrow = new Line2D.Double();
                		g2d.setStroke(dashed);
                		g2d.setColor(Color.black);  	
                		tmpArrow = a.getScaledArrow(scaleFactor);
                		g2d.draw(tmpArrow);
                		
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
            		}
            		
            		
            		
                    if (a.aa.getLabel() != null) {
                    	g.setFont(new Font("Helvetica", Font.BOLD,  12));
                    	if (a.aa.getCardinality() == null){
                            g2d.drawString(a.aa.getLabel(),
                                    (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                                    (int) (a.getLabelYPosition() * trans.getScaleY()));
                    	}else{
                            g2d.drawString(a.aa.getLabel()+a.aa.getCardinality().toString(),
                                    (int) (a.getLabelXPosition() * trans.getScaleX())+5,
                                    (int) (a.getLabelYPosition() * trans.getScaleY()));
                    	}
                    }  		
            	}
            	
            }
            
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        resizeContents();
    }

    @Override
    public String toString() {
        // We've got to force the Component to paint
        // particularly when we're calling toString from a non GUI app
        paint(svgGenerator);

        StringWriter w = new StringWriter();

        try {
            svgGenerator.stream(w);
        } catch (SVGGraphics2DIOException sg2ie) {
            return "<!-- SVG Generation Failed -->";
        }
        return w.toString();
    }

    private void initComponents() {
        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);
    }

    protected ConcreteZone getHighlightedZone() {
        return highlightedZone;
    }

    protected void setHighlightedZone(ConcreteZone highlightedZone) {
        if (this.highlightedZone != highlightedZone) {
            setHighlightedContour(null);
            setHighlightedFoot(null);
            
            //Zohreh
            setHighlightedArrow(null);
            
            this.highlightedZone = highlightedZone;
            repaint();
        }
    }

    protected CircleContour getHighlightedContour() {
        return highlightedContour;
    }

    protected void setHighlightedContour(CircleContour highlightedContour) {
        if (this.highlightedContour != highlightedContour) {
            setHighlightedZone(null);
            setHighlightedFoot(null);
            
            //Zohreh
            setHighlightedArrow(null);
            
            this.highlightedContour = highlightedContour;
            repaint();
        }
    }

    protected ConcreteSpiderFoot getHighlightedFoot() {
        return highlightedFoot;
    }

    protected void setHighlightedFoot(ConcreteSpiderFoot foot) {
        if (this.highlightedFoot != foot) {
            setHighlightedZone(null);
            setHighlightedContour(null);
            
            //Zohreh
            setHighlightedArrow(null);
            
            this.highlightedFoot = foot;
            repaint();
        }
    }
    
    
    //Zohreh
    protected ConcreteArrow getHighlightedArrow() {
        return highlightedArrow;
    }
    
    //Zohreh
    protected void setHighlightedArrow(ConcreteArrow arrow) {
        if (this.highlightedArrow != arrow) {
            setHighlightedZone(null);
            setHighlightedContour(null);
            setHighlightedFoot(null);
            this.highlightedArrow = arrow;
            repaint();
        }
    }

    /**
     * This method sets the given diagram as the one to be displayed. <p>It
     * refreshes the {@link CirclesPanelEx#setPreferredSize(java.awt.Dimension)
     * preferred size} of this panel and requests a refresh of the drawing area
     * accordingly.</p>
     */
    private void resetDiagram(ConcreteDiagrams diagram) {
        this.diagram = diagram;
        if (diagram == null) {
            // NOTE: Currently we display nothing if there is no diagram
            this.setPreferredSize(null);
        } else {
            this.setPreferredSize(new Dimension(diagram.getSize(), diagram.getSize()));
        }
        // We have to redraw the entire area...
        resizeContents();
    }

    private void resizeContents() {
        if (diagram != null) {
            // Get the current width of this diagram panel and resize contents...
            int size = diagram.getSize();
            if (size > 0) {
                setScaleFactor(Math.min((float) this.getWidth() / size, (float) this.getHeight() / size));
            }
        }
    }

    /**
     * Compares the width and height of this panel and tries to scale the
     * concrete diagram's box so that it nicely fits the contents of the panel.
     */
    private void recalculateTransform() {
        this.trans.setToScale(scaleFactor, scaleFactor);
    }

    /**
     * Puts the scaled coordinates, width and height of {@code inCircle} into
     * the {@code outCircle} (without changing {@code inCircle}).
     */
    protected static void transformCircle(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        translateCircle(scaleFactor, inCircle, outCircle);
        scaleCircle(scaleFactor, inCircle, outCircle);
    }
    
    
    /**
     * Translates the circle to match the scale factor. The changed
     * coordinates are written to the {@code outCircle} (without changing
     * {@code inCircle}).
     */
    private static void translateCircle(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        outCircle.x = inCircle.x * scaleFactor;
        outCircle.y = inCircle.y * scaleFactor;
    }

    /**
     * Translates the circle without scaling so that its centre coincides with
     * the centre if the circle was translated and then scaled. The new
     * coordinates are written to the {@code outCircle} (without changing
     * {@code inCircle}).
     */
    protected static void translateCircleCentre(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        final double correctionFactor = (scaleFactor - 1) / 2;
        outCircle.x = inCircle.x * scaleFactor + inCircle.width * correctionFactor;
        outCircle.y = inCircle.y * scaleFactor + inCircle.height * correctionFactor;
    }

    /**
     * Scales the circle to match the scale factor. The changed
     * width and height are written to the {@code outCircle} (without changing
     * {@code inCircle}).
     */
    private static void scaleCircle(double scaleFactor, Ellipse2D.Double inCircle, Ellipse2D.Double outCircle) {
        outCircle.width = inCircle.width * scaleFactor;
        outCircle.height = inCircle.height * scaleFactor;
    }

    /**
     * Scales the given circle while preserving the location of its centre.
     */
    protected static void scaleCircleCentrally(Double circle, double scale) {
        circle.x -= circle.width * (scale - 1) / 2;
        circle.y -= circle.height * (scale - 1) / 2;
        circle.width *= scale;
        circle.height *= scale;
    }

    /**
     * Issues a repaint of the content of this panel within the bounds of the
     * given shape.
     */
    private void repaintShape(Shape shape) {
        if (shape != null) {
            repaint(shape.getBounds());
        }
    }

    /**
     * Returns the horizontal centring translation of the diagram.
     */
    protected int getCenteringTranslationX() {
        return (this.getWidth() - (int) Math.round(diagram.getSize() * scaleFactor)) / 2;
    }

    /**
     * Returns the vertical centring translation of the diagram.
     */
    protected int getCenteringTranslationY() {
        return (this.getHeight() - (int) Math.round(diagram.getSize() * scaleFactor)) / 2;
    }

    @Override
    protected Graphics getComponentGraphics(Graphics g) {
        return svgGenerator;
    }

}
