package HitDetection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;
import java.util.Vector;

public class SprDino extends Sprite {

    Texture txDino, txDeadDino;
    Vector2 vPos, vDir, vGrav, vPrevPos;
    private Sprite sprDino;
    boolean bJump, bGrav, bGoThrough, bPlatformCarry, bMove;
    float fGround;
    ShapeRenderer shapeRenderer;
    Rectangle2D.Double HitPoint;
    Array<Rectangle2D.Double> arHitPoints;

    SprDino(Texture _txDino, Texture _txDeadDino) {
        shapeRenderer = new ShapeRenderer();
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
        HitPoint = new Rectangle2D.Double(vPos.x + vPos.x * 0.2577319588, vPos.y, sprDino.getWidth() * 0.3865979381, sprDino.getHeight() * 0.1602564103);
        arHitPoints = new Array<Rectangle2D.Double>();
        arHitPoints.add(HitPoint);
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
        HitPoints();
    }

    void HitPoints() {
        Gdx.gl.glEnable(GL20.GL_ARRAY_BUFFER_BINDING);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Rectangle2D.Double HitPoint : arHitPoints) {
            HitPoint = new Rectangle2D.Double(vPos.x + (vPos.x * 0.2577319588), vPos.y, sprDino.getWidth() * 0.3865979381, sprDino.getHeight() * 0.1602564103);
            shapeRenderer.rect((float) HitPoint.x, (float) HitPoint.y, (float) HitPoint.width, (float) HitPoint.height);
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    void PositionSet() {
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
