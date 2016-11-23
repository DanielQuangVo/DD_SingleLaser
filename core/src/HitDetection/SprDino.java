package HitDetection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import java.util.Vector;

public class SprDino extends Sprite {

    Texture txDino, txDeadDino;
    Vector2 vPos, vDir, vGrav, vPrevPos;
    private Sprite sprDino;
    boolean bJump, bGrav, bGoThrough, bPlatformCarry, bMove;
    float fGround;

    SprDino(Texture _txDino, Texture _txDeadDino) {
        txDino = _txDino;
        txDeadDino = _txDeadDino;
        sprDino = new Sprite(txDino);
        vPos = new Vector2(0, 0);
        vDir = new Vector2(0, 0);
        vGrav = new Vector2(0, 0);
        vPrevPos = new Vector2(0, 0);
        fGround = 0;
        bGrav = false;
        bGoThrough = false;
        bPlatformCarry = false;
        bMove = false;
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
        if(bPlatformCarry && bMove == false){
            vDir.set((float) -0.5 ,0);
        }
        vDir.add(vGrav);
        vPos.add(vDir);
        sprDino.setPosition(vPos.x, vPos.y);
    }

    void Animate(Texture _txDinoState) {
        sprDino.setTexture(_txDinoState);
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
