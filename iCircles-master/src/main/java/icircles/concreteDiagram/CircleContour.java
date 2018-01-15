package icircles.concreteDiagram;

/*
 * @author Jean Flower <jeanflower@rocketmail.com>
 * Copyright (c) 2012
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of the iCircles Project.
 */

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import icircles.abstractDescription.AbstractCurve;

public class CircleContour {

    static Logger logger = Logger.getLogger(CircleContour.class.getName());

    Ellipse2D.Double circle;
    double cx;
    double cy;
    double radius;
    double nudge = 0.1;
    
    Color col;
    Stroke stroke;
    
//    Area bigInterior;
//    Area smallInterior; 
    // TODO this caching of the smallInterior somehow became out of date.
    // I have just suppressed it, which slow things down. but we need to understand why and reinstate the cache.

    public AbstractCurve ac;

    public CircleContour(double cx,
            double cy, double radius, 
            AbstractCurve ac) {
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.ac = ac;
        circle = makeEllipse(cx, cy, radius);
    }

    public CircleContour(CircleContour c) {
		this.cx = c.cx;
		this.cy = c.cy;
		this.radius = c.radius;
		this.ac = c.ac;
		circle = makeEllipse(cx, cy, radius);
		this.col = c.col;
	}



	public void shift(double x, double y) {
        cx += x;
        cy += y;
        circle = makeEllipse(cx, cy, radius);
//        bigInterior = null;
//        smallInterior = null;
    }

    private void scaleAboutZero(double scale) {
        cx *= scale;
        cy *= scale;
        radius *= scale;
        circle = makeEllipse(cx, cy, radius);
//        bigInterior = null;
//        smallInterior = null;
    }

    private Ellipse2D.Double makeEllipse(double x, double y, double r) {
        return new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r);
    }

    public Ellipse2D.Double getCircle() {
        return circle;
    }

    public Area getBigInterior() {
//        if (bigInterior == null) {
          Area  bigInterior = new Area(makeEllipse(cx, cy, radius + nudge));
//        }
        return bigInterior;
    }

    public Area getSmallInterior() {
        //if (smallInterior == null) {
        Area    smallInterior = new Area(makeEllipse(cx, cy, radius - nudge));
        //}
        return smallInterior;
    }

    public String debug() {
        if (logger.getEffectiveLevel().isGreaterOrEqual(Level.DEBUG)) {
            return "circle " + ac.getLabel() + " at (" + cx + "," + cy + ") rad " + radius;
        } else {
            return "";
        }
    }

    public Shape getFatInterior(double fatter) {
        return new Area(makeEllipse(cx, cy, radius + fatter));
    }

    
    public double getLabelXPosition() {
//        return cx + 0.8 * radius;
    	  //Zohreh
    	  return cx + 0.7 * radius;
    	
    }

    public double getLabelYPosition() {
        return cy - 0.75 * radius;
    }
    

    public int getMinX() {
        return (int) (cx - radius);
    }

    public int getMaxX() {
        return (int) (cx + radius) + 1;
    }

    public int getMinY() {
        return (int) (cy - radius);
    }

    public int getMaxY() {
        return (int) (cy + radius) + 1;
    }
    public static void fitCirclesToSize(ArrayList<CircleContour> circles, int size)
    {
        // work out a suitable size
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (CircleContour cc : circles) {
            if (cc.getMinX() < minX) {
                minX = cc.getMinX();
            }
            if (cc.getMinY() < minY) {
                minY = cc.getMinY();
            }
            if (cc.getMaxX() > maxX) {
                maxX = cc.getMaxX();
            }
            if (cc.getMaxY() > maxY) {
                maxY = cc.getMaxY();
            }
        }

        double midX = (minX + maxX) * 0.5;
        double midY = (minY + maxY) * 0.5;
        for (CircleContour cc : circles) {
            cc.shift(-midX, -midY);
        }

        double width = maxX - minX;
        double height = maxY - minY;
        double biggest_HW = Math.max(height, width);
        double scale = (size * 0.95) / biggest_HW;
        for (CircleContour cc : circles) {
            cc.scaleAboutZero(scale);
        }

        for (CircleContour cc : circles) {
            cc.shift(size * 0.5, size * 0.5);
        }
    }
    
    static Rectangle2D.Double makeBigOuterBox(ArrayList<CircleContour> circles)
    {
    	if(circles == null){
    		System.out.println("Tell me");
    	}
    	
    	if(circles.size()==0)
    		return new Rectangle2D.Double(0, 0, 1000, 1000);
    	
        // work out a suitable size
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (CircleContour cc : circles) {
            if (cc.getMinX() < minX) {
                minX = cc.getMinX();
            }
            if (cc.getMinY() < minY) {
                minY = cc.getMinY();
            }
            if (cc.getMaxX() > maxX) {
                maxX = cc.getMaxX();
            }
            if (cc.getMaxY() > maxY) {
                maxY = cc.getMaxY();
            }
        }
        int width = maxX - minX;
        int height = maxY - minX;
        
        return new Rectangle2D.Double((int)(minX - 2*width), (int)(minY - 2*height), 5*width, 5*height);
    }

	public Color color() {
		return col;
	}

	public void setColor(Color color) {
		col = color;
	}

	public double get_cx() {
		return cx;
	}

	public double get_cy() {
		return cy;
	}

	public double get_radius() {
		return radius;
	}

	public void setStroke(Stroke s) {
		stroke = s;
	}
	public Stroke stroke() {
		return stroke;
	}
}
