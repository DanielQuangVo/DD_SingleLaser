package HitDetection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class SprDino extends Sprite {

    Texture txDino, txDeadDino;
    Texture[] txHitPoint;
    Vector2 vPos, vDir, vGrav, vPrevPos, vHitPoint;
    private Sprite sprDino;
    boolean bJump, bGrav, bGoThrough, bPlatformCarry, bMove;
    float fGround;
    int[] nHitType;
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
        nHitType = new int[6];
        vHitPoint.set(50, 0);
        sprHitPoint = new SprHitPoint(txHitPoint[0], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(66, 33);
        sprHitPoint = new SprHitPoint(txHitPoint[1], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(0, 35);
        sprHitPoint = new SprHitPoint(txHitPoint[2], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(140, 75);
        sprHitPoint = new SprHitPoint(txHitPoint[3], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(170, 110);
        sprHitPoint = new SprHitPoint(txHitPoint[4], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        vHitPoint.set(115, 135);
        sprHitPoint = new SprHitPoint(txHitPoint[5], vHitPoint);
        arsprHitPoint.add(sprHitPoint);
        for (int i = 0; i < 6; i++) {
            nHitType[i] = 0;
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
            sprHitPoint.update(vPos);
        }

    }

    void PositionSet() {
        sprDino.setPosition(vPos.x, vPos.y);
    }

    void Animate(Texture _txDinoState) {
        sprDino.setTexture(_txDinoState);
    }

    int nHitDetection(SprPlatform sprPlatform, int nGeneralHitType) {
        HitDetectionPoints(sprPlatform);
        if (nGeneralHitType == 0) {
            return 0;
        } else if (nGeneralHitType == 1 ) {
            return 1;
        } else if (nGeneralHitType == 2) {
            if (nHitType[0] == 2) {
                return 2;
            }
        } else if (nGeneralHitType == 3) {
            if (nHitType[4] == 3 && nHitType[0] == 0 && nHitType[1] == 0 && nHitType[2] == 0 && nHitType[3] == 0 && nHitType[5] == 0) {
                System.out.println("decapitation");
                return 3;
            }
            return 3;
        } else if (nGeneralHitType == 4) {
            return 4;
        }
        return 0;
    }

    void HitDetectionPoints(SprPlatform sprPlatform) {
        Iterator<SprHitPoint> iter = arsprHitPoint.iterator();
        for (int x = 0; iter.hasNext(); x++) {
            SprHitPoint sprHitPoint = iter.next();
            if (sprHitPoint.getSprite().getBoundingRectangle().overlaps(sprPlatform.getSprite().getBoundingRectangle())) {

                if (sprHitPoint.vPrevPos.y >= (sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight())) {
                    nHitType[x] = 2;
                } else if (sprHitPoint.vPos.y == sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight() - 1) {
                    nHitType[x] = 2;
                } else if (sprHitPoint.vPrevPos.y + sprHitPoint.getSprite().getHeight() <= sprPlatform.vPrevPos.y) {
                    nHitType[x] = 3;
                } else if (bGrav && bGoThrough == false) {
                    nHitType[x] = 1;
                } else if (bGoThrough == true) {
                    nHitType[x] = 3;
                } else if (bGrav == false) {
                    nHitType[x] = 4;
                }
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
//                System.out.println(nHitType[x]);
//                System.out.println(sprHitPoint.vPos.x + " " + sprHitPoint.vPos.y);
            } else {
                nHitType[x] = 0;
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
