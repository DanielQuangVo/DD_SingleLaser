package HitDetection;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SprHitPoint extends Sprite {
    Texture txHitPoint;
    private Sprite _sprHitPoint;
    Vector2 vPos, vPrevPos, vPart;

    SprHitPoint(Texture _txHitPoint, Vector2 _vPart) {
        vPos = new Vector2(0,0);
        vPart =new Vector2(_vPart.x,_vPart.y);
        vPos.set(vPart);
        txHitPoint = _txHitPoint;
        _sprHitPoint = new Sprite(txHitPoint);
        vPrevPos = new Vector2(0,0);
    }


    void update(Vector2 _vDinoPos) {
        vPrevPos.set(vPos);
        vPos.set(_vDinoPos.x+vPart.x,_vDinoPos.y+vPart.y);
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
