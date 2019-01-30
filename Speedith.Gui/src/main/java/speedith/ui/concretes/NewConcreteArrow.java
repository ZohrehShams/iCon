//package speedith.ui.concretes;
//
//
//
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
//import java.awt.geom.QuadCurve2D;
//import java.util.List;
//
//import speedith.ui.abstracts.NewAbstractArrow;
//
//public class NewConcreteArrow implements Cloneable {
//	
//	public static final double CHAR_WIDTH = 12;
//	private static final int HEAD_LENGTH = 15;
//	private static final double PHI = Math.PI / 6;
//	
//	private NewAbstractArrow abstractArrow;
//    private Ellipse2D.Double source;
//    private Ellipse2D.Double target;
//    
//    private double x1;
//    private double x2;
//    private double y1;
//    private double y2;
//    private double cx;
//    private double cy;
//    private double theta;
//    
//    private QuadCurve2D.Double left;
//    private QuadCurve2D.Double right;
//    
//	
//    public NewConcreteArrow(NewAbstractArrow abstractArrow) {
//        this.abstractArrow = abstractArrow;
//        this.left = new QuadCurve2D.Double();
//        this.right = new QuadCurve2D.Double();
//    }
//    
//    
//    public NewAbstractArrow getAbstractArrow() {
//        return abstractArrow;
//    }
//    
//    public void setSource(Ellipse2D.Double source) {
//        this.source = source;
//    }
//
//    public void setTarget(Ellipse2D.Double target) {
//        this.target = target;
//    }
//
//    public Ellipse2D.Double getSource() {
//        return source;
//    }
//
//    public Ellipse2D.Double getTarget() {
//        return target;
//    }
//    
//    
//    
//    public void init() {
//        List<Point2D.Double> intersections = getClosestPoints(source, target);
//        x1 = intersections.get(0).x;
//        y1 = intersections.get(0).y;
//        x2 = intersections.get(1).x;
//        y2 = intersections.get(1).y;
//        calculateSecondaryValues();
//    }
//
//    private void calculateSecondaryValues() {
//        double midX = x1 + (x2 - x1) / 2;
//        double midY = (Math.min(y1, y2) + Math.abs(y2 - y1) / 2);
//
//        //get random control point
//        cx = midX;
//        cy = midY - 20; // make arrow curved
//        theta = Math.atan2(y2 - cy, x2 - cx);
//    }
//    
//    
//    public QuadCurve2D.Double getCurve() {
//        QuadCurve2D.Double curve = new QuadCurve2D.Double();
//        curve.setCurve(x1, y1, cx, cy, x2, y2);
//        if (cx > 100000) {
//            cx = 0;
//
//        }if (cy > 100000) {
//            cy = 0;
//
//        }if (x2 > 100000) {
//            x2 = 0;
//
//        }if (y2 > 100000) {
//            y2 = 0;
//
//        }
//        curve.setCurve(x1, y1, cx, cy, x2, y2);
//        curve.subdivide(left, right);
//        return curve;
//    }
//
//    public Line2D getArrowhead(int sign) {
//        double x = x2 - HEAD_LENGTH * Math.cos(theta + PHI *sign);
//        double y = y2 - HEAD_LENGTH * Math.sin(theta + PHI *sign);
//        Line2D line = new Line2D.Double();
//        line.setLine(x2,y2,x,y);
//        return line;
//    }
//
//    public Point2D.Double getLabelPosition() {
//        double x = (left.getX2() + 2);
//        double y = (int) right.getY1();
//        double gradient = getGradient(x1, y1, x2, y2);
//        if (gradient > -0.09 && gradient < 0.09) {
//            x = getCenteredLabelPosition(left.getX2() + 2, getLabel());
//            y= (int) (right.getY1() - 3);
//        } else if (gradient > -1 && gradient < 1) {
//            y= (int) (right.getY1() - 45 * gradient);
//        }
//        return new Point2D.Double(x,y);
//    }
//
//    public Point2D.Double getCardinalityLabelPosition() {
//        Point2D.Double labelPosition = getLabelPosition();
//        return new Point2D.Double((int) (labelPosition.x + getLabel().length() * CHAR_WIDTH),labelPosition.y + 4);
//    }
//    
//
//    @Override
//    public ConcreteArrow clone() {
//        ConcreteArrow clone;
//        try {
//            clone = (ConcreteArrow) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new RuntimeException(e);
//        }
//        return clone;
//    }
//
//}
