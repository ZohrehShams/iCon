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

import icircles.abstractDescription.AbstractDescription;
import icircles.util.CannotDrawException;

import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

public class ConcreteDiagram implements ConcreteDiagrams{

    static Logger logger = Logger.getLogger(ConcreteDiagram.class.getName());

    Rectangle2D.Double box;
    ArrayList<CircleContour> circles;
    ArrayList<ConcreteZone> shadedZones;
    ArrayList<ConcreteZone> unshadedZones;
    ArrayList<ConcreteSpider> spiders;
    private Font font;

    public ConcreteDiagram(Rectangle2D.Double box,
            ArrayList<CircleContour> circles,
            ArrayList<ConcreteZone> shadedZones,
            ArrayList<ConcreteZone> unshadedZones,
            ArrayList<ConcreteSpider> spiders) {
        this.box = box;
        this.circles = circles;
        this.shadedZones = shadedZones;
        this.unshadedZones = unshadedZones;
        this.spiders = spiders;
    }

    public ArrayList<CircleContour> getCircles() {
        return circles;
    }

    public double checksum() {
        return circles_checksum() + shading_checksum() + spiders_checksum();
    }

    private double circles_checksum() {

        double result = 0.0;
        if (circles == null) {
            return result;
        }

        for (CircleContour c : circles) {
            logger.debug("build checksum for contour at coords (" + c.cx
              + ", " + c.cy + ") radius " + c.radius + "\n");
            result += c.cx * 0.345 + c.cy * 0.456 + c.radius * 0.567 + c.ac.checksum() * 0.555;
            result *= 1.2;
        }
        return result;
    }

    private double shading_checksum() {

        double result = 0.0;
        for (ConcreteZone cz : shadedZones) {
            logger.debug("build checksum for shading\n");

            result += cz.abr.checksum() * 1000.0;
        }
        return result;
    }

    private double spiders_checksum() {

        double result = 0.0;
        if (spiders == null) {
            return result;
        }

        for (ConcreteSpider s : spiders) {
            logger.debug("build checksum for spider\n");

            result += s.checksum();
            result *= 1.2;
        }
        return result;
    }

    public ArrayList<ConcreteZone> getShadedZones() {
        return shadedZones;
    }

    public ArrayList<ConcreteZone> getUnshadedZones() {
        return unshadedZones;
    }

    @Override
    public Rectangle2D.Double getBox() {
        return box;
    }

    /**
     * This can be used to obtain a drawing of an abstract diagram.
     *
     * @param ad the description to be drawn
     * @param size the size of the drawing panel
     * @return the concrete diagram based on the abstract diagram and the drawing panel size.
     * @throws CannotDrawException thrown if the abstract description fails {@link AbstractDescription#checksOk() checks}
     */
    public static ConcreteDiagram makeConcreteDiagram(AbstractDescription ad, int size) throws CannotDrawException {
        // TODO
        if (!ad.checksOk()) {
            // not drawable
            throw new CannotDrawException("badly formed diagram spec");
        }
        DiagramCreator dc = new DiagramCreator(ad);
        return dc.createDiagram(size);
    }

    public ArrayList<ConcreteSpider> getSpiders() {
        return spiders;
    }

    public void setFont(Font f) {
        font = f;
    }

    public Font getFont() {
        return font;
    }
    

    @Override
    public int getSize() {
        return (int) Math.ceil(box.height);
    }

    // <editor-fold defaultstate="collapsed" desc="Diagram Element Lookup by Coordinates">
    /**
     * Returns the {@link ConcreteSpiderFoot spider foot} located at the given
     * coordinates. <p>Returns {@code null} if no foot is located on the given
     * coordinates.</p>
     *
     * @param p the coordinates at which to look for a spider's foot. <p>These
     * are the coordinates in the diagram's own local coordinate system. Thus,
     * if you look up elements with a point in the coordinate system of {@link
     * icircles.gui.CirclesPanelEx a panel} then you first have to convert the coordinates
     * of the point with {@link
     * icircles.gui.CirclesPanelEx#toDiagramCoordinates(java.awt.Point)} and then use the
     * resulting point as an argument to this method.</p>
     * @return the {@link ConcreteSpiderFoot spider foot} located at the given
     * coordinates. <p>Returns {@code null} if no foot is located on the given
     * coordinates.</p>
     */
    public ConcreteSpiderFoot getSpiderFootAtPoint(Point p) {
        if (getSpiders() != null) {
            for (ConcreteSpider s : getSpiders()) {
                for (ConcreteSpiderFoot f : s.feet) {
                    double dist = Math.sqrt((p.x - f.getX()) * (p.x - f.getX())
                            + (p.y - f.getY()) * (p.y - f.getY()));
                    if (dist < ConcreteSpiderFoot.FOOT_RADIUS + 2) {
                        return f;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Does same as {@link ConcreteDiagram#getSpiderFootAtPoint(java.awt.Point)},
     * however, the hit-test is performed by scaling the distance with the
     * scaling factor first.
     *
     * @param p the coordinates at which to look for a spider's foot. <p>These
     * are the coordinates in the diagram's own local coordinate system. Thus,
     * if you look up elements with a point in the coordinate system of {@link
     * icircles.gui.CirclesPanelEx a panel} then you first have to convert the coordinates
     * of the point with {@link
     * icircles.gui.CirclesPanelEx#toDiagramCoordinates(java.awt.Point)} and then use the
     * resulting point as an argument to this method.</p>
     * @param scaleFactor the scale factor with which to multiply the distance
     * between the given point and particular spiders.
     * @return the {@link ConcreteSpiderFoot spider foot} located at the given
     * coordinates. <p>Returns {@code null} if no foot is located on the given
     * coordinates.</p>
     */
    public ConcreteSpiderFoot getSpiderFootAtPoint(Point p, double scaleFactor) {
        final double threshold = (ConcreteSpiderFoot.FOOT_RADIUS + 2)/scaleFactor;
        if (getSpiders() != null) {
            for (ConcreteSpider s : getSpiders()) {
                for (ConcreteSpiderFoot f : s.feet) {
                    double dist = Math.sqrt((p.x - f.getX()) * (p.x - f.getX())
                            + (p.y - f.getY()) * (p.y - f.getY()));
                    if (dist < threshold) {
                        return f;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the {@link CircleContour circle contour} that is located in the
     * <span style="font-style:italic;">vicinity</span> of the given point.
     * <p>The vicinity is dependent upon the given {@code tolerance}.</p>
     *
     * @param p the coordinates at which to look for a circle contour. <p>These
     * are the coordinates in the diagram's own local coordinate system. Thus,
     * if you look up elements with a point in the coordinate system of {@link
     * icircles.gui.CirclesPanelEx a panel} then you first have to convert the coordinates
     * of the point with {@link
     * icircles.gui.CirclesPanelEx#toDiagramCoordinates(java.awt.Point)} and then use the
     * resulting point as an argument to this method.</p>
     * @param tolerance the distance from the contour which is still considered
     * a hit.
     * @return the {@link CircleContour circle contour} that is located <span
     * style="font-style:italic;">near</span> the given point. <p>Returns {@code
     * null} if no circle contour is located near the given coordinates.</p>
     */
    public CircleContour getCircleContourAtPoint(Point p, double tolerance) {
        if (getCircles() != null) {
            for (CircleContour cc : getCircles()) {
                double dist = Math.sqrt((p.x - cc.get_cx()) * (p.x - cc.get_cx())
                        + (p.y - cc.get_cy()) * (p.y - cc.get_cy()));
                if (dist > cc.get_radius() - tolerance && dist < cc.get_radius() + tolerance) {
                    return cc;
                }
            }
        }
        return null;
    }

    /**
     * Returns the {@link ConcreteZone zone} that contains the given point.
     * @param p the coordinates at which to look for the zone. <p>These
     * are the coordinates in the diagram's own local coordinate system. Thus,
     * if you look up elements with a point in the coordinate system of {@link
     * icircles.gui.CirclesPanelEx a panel} then you first have to convert the coordinates
     * of the point with {@link
     * icircles.gui.CirclesPanelEx#toDiagramCoordinates(java.awt.Point)} and then use the
     * resulting point as an argument to this method.</p>
     * @return the {@link ConcreteZone zone} that contains the given point.
     * <p>Returns {@code null} if no zone is located at the given
     * coordinates.</p>
     */
    public ConcreteZone getZoneAtPoint(Point p) {
        for (ConcreteZone zone : this.unshadedZones) {
            if (zone.getShape(box).contains(p)) {
                return zone;
            }
        }
        for (ConcreteZone zone : this.shadedZones) {
            if (zone.getShape(box).contains(p)) {
                return zone;
            }
        }
        return null;
    }
    // </editor-fold>
}
