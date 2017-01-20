package HitDetection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class SprLaser extends Sprite {

    Vector2 vStart, vEnd, vTarget, vPart, vVelocity;
    float fVelocity, fTolerance, fAngle, fSpeed, fMouseX, fMouseY;
    boolean bLaser;
    Line2D lineLaser;
    Sprite sprLaser;
    Texture txLaser;

    public SprLaser(Texture _txLaser, Vector2 _vDinoPos, Float _fMouseX, Float _fMouseY) {
        vVelocity = new Vector2(0, 0);
        fSpeed = 20;
        vPart = new Vector2(120, 120);
        vStart = new Vector2(_vDinoPos.x + vPart.x, _vDinoPos.y + vPart.y);
        vEnd = vStart;
        vTarget = new Vector2(_fMouseX, _fMouseY);
        txLaser = _txLaser;
        sprLaser = new Sprite(txLaser);
        sprLaser.setSize(10, 5);
    }

    void update(Vector2 vDinoPos, Float __fMouseX, Float __fMouseY) {
        vTarget.set(__fMouseX, __fMouseY);
        vStart.set(vDinoPos.x + vPart.x, vDinoPos.y + vPart.y);
        fAngle = (float) Math.atan2(vTarget.y - vStart.y, vTarget.x - vStart.x);
        if(fAngle < 0){
            fAngle+=360;
        }
        vVelocity.set((float) Math.cos(fAngle) * fSpeed, (float) Math.sin(fAngle) * fSpeed);
        fAngle *= MathUtils.radiansToDegrees;
        vEnd.set(vEnd.x + vVelocity.x, vEnd.y + vVelocity.y);
        sprLaser.setSize(vEnd.x, 5);
        System.out.println(fAngle);
        System.out.println("vStart"+vStart.x+" "+vStart.y);
        System.out.println("vEnd"+vEnd.x+" "+vEnd.y);


    }

    public Sprite getSprite() {
        return sprLaser;
    }

    public float getX() {
        return vStart.x;
    }

    public float getY() {
        return vStart.y;
    }
}