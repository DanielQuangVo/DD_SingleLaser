package HitDetection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SprHitPoint extends Sprite {
    String sFile;
    Texture txHitPoint;
    Image imgHitPoint;
    private Sprite _sprHitPoint;
    Vector2 vPos, vPrevPos;

    SprHitPoint(Texture _txHitPoint, Vector2 _vPos) {
        vPos = new Vector2(0,0);
        vPos.set(_vPos);
        txHitPoint = _txHitPoint;
        _sprHitPoint = new Sprite(txHitPoint);
        vPrevPos = new Vector2(500,50);
    }


    void update(Vector2 __vPos) {
        vPrevPos.set(vPos);
        vPos.set(__vPos);
        _sprHitPoint.setPosition(vPos.x, vPos.y);
    }

    public Sprite getSprite() {
        return _sprHitPoint;
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
