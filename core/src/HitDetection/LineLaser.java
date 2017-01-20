package HitDetection;

import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Float;

public class LineLaser extends Line2D {

    Vector2 vStart, vEnd, vTarget;
    Point2D pStart, pEnd;
    float fVelocity, fTolerance, fAngle, fSpeed;
    boolean bLaser;
    Line2D lineLaser;

    public LineLaser(Vector2 _vTarget, Vector2 _vStart) {
        vStart = _vStart;
        vEnd = vStart;
        pStart = new Point2D.Float(vStart.x, vStart.y);
        pEnd = new Point2D.Float(vEnd.x, vEnd.y);
        vTarget = _vTarget;
    }

    void update() {
        pStart.setLocation(vStart.x, vStart.y);
        pEnd.setLocation(vEnd.x, vEnd.y);
    }

    @Override
    public double getX1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getY1() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point2D getP1() {
        return pStart;
    }

    @Override
    public double getX2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getY2() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Point2D getP2() {
        return pEnd;
    }

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D getBounds2D() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}