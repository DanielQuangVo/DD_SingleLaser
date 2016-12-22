package HitDetection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class SprDino extends Sprite {

    Texture txDino, txDeadDino;
    Texture[] txHitPoint;
    Vector2 vPos, vDir, vGrav, vPrevPos, vHitPoint;
    Vector2[] vHitPoints;
    private Sprite sprDino;
    boolean bJump, bGrav, bGoThrough, bPlatformCarry, bMove;
    float fGround;
    SprHitPoint sprHitPoint;
    Array<SprHitPoint> arsprHitPoint;

    SprDino(Texture _txDino, Texture _txDeadDino, Texture[] _txHitPoint) {
        txHitPoint = _txHitPoint;
        txDino = _txDino;
        txDeadDino = _txDeadDino;
        sprDino = new Sprite(txDino);
        vPos = new Vector2(0, 0);
        vDir = new Vector2(0, 0);
        vGrav = new Vector2(0, 0);
        vPrevPos = new Vector2(0, 0);
        fGround = 0;
        vHitPoint = new Vector2(0, 0);
        bGrav = false;
        bGoThrough = false;
        bPlatformCarry = false;
        bMove = false;
        arsprHitPoint = new Array<SprHitPoint>();
        vHitPoints = new Vector2[6];
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                vHitPoints[i] = new Vector2(50, 0);
            }
            if (i == 1) {
                vHitPoints[i] = new Vector2(66, 33);
            }
            if (i == 2) {
                vHitPoints[i] = new Vector2(0, 35);
            }
            if (i == 3) {
                vHitPoints[i] = new Vector2(140, 75);
            }
            if (i == 4) {
                vHitPoints[i] = new Vector2(170, 110);
            }
            if (i == 5) {
                vHitPoints[i] = new Vector2(115, 135);
            }
            vHitPoint.set(vHitPoints[i].x + vPos.x, vHitPoints[i].y + vPos.y);
            sprHitPoint = new SprHitPoint(txHitPoint[i], vHitPoint);
            arsprHitPoint.add(sprHitPoint);
        }
    }

    void gravity() {
        vPrevPos.set(vPos);
        if (vPos.y < 0) {
            vPos.set(vPos.x, 0);
            vDir.set(vDir.x, 0);
            vGrav.set(0, 0);
            bGrav = false;
            bJump = false;
        }
        if (bGrav) {
            vGrav.set(0, (float) (vGrav.y * 1.1));
        }
        if (vPos.y == fGround) {
            vDir.set(vDir.x, 0);
            vGrav.set(0, 0);
            vPos.set(vPos.x, fGround);
            bJump = false;
            bGrav = false;
        }
    }

    void update() {
        if (bPlatformCarry && bMove == false) {
            vDir.set((float) -0.5, 0);
        } else if (bPlatformCarry == false && bMove == false) {
            vDir.set(0, vDir.y);
        }
        vDir.add(vGrav);
        vPos.add(vDir);
        PositionSet();
        Iterator<SprHitPoint> iter = arsprHitPoint.iterator();
        for (int x = 0; iter.hasNext(); x++) {
            SprHitPoint sprHitPoint = iter.next();
            vHitPoint.set(vPos.x + vHitPoints[x].x, vPos.y + vHitPoints[x].y);
            sprHitPoint.update(vHitPoint);
        }

    }

    void PositionSet() {
        sprDino.setPosition(vPos.x, vPos.y);
    }

    void Animate(Texture _txDinoState) {
        sprDino.setTexture(_txDinoState);
    }

    void HitDetection(SprPlatform sprPlatform) {
        Iterator<SprHitPoint> iter = arsprHitPoint.iterator();
        for (int x = 0; iter.hasNext(); x++) {
            SprHitPoint sprHitPoint = iter.next();
            if (sprHitPoint.getSprite().getBoundingRectangle().overlaps(sprPlatform.getSprite().getBoundingRectangle())) {
                if (x == 0) {
                    System.out.println("feet");
                }
                if (x == 1) {
                    System.out.println("belly");
                }
                if (x == 2) {
                    System.out.println("tail");
                }
                if (x == 3) {
                    System.out.println("chin");
                }
                if (x == 4) {
                      System.out.println("nose");
                }
                if (x == 5) {
                      System.out.println("forehead");
                }
            }
        }
    }

    public Sprite getSprite() {
        return sprDino;
    }

    //@Override
    public float getX() {
        return vPos.x;
    }

    //@Override
    public float getY() {
        return vPos.y;
    }
}
