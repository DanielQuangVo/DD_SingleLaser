//https://github.com/Mrgfhci/Drop
package HitDetection;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

public class ScrPlatform implements Screen, InputProcessor {

    Game game;
    SpriteBatch batch;
    Texture txDeadDino, txDino, txPlat, txSheet, txDinFor1, txDinoFor2, txBack1, txBack2, txJumpRight, txJumpLeft, txDead;
    Texture[] txHitPoint;
    SprDino sprDino;
    SprPlatform sprPlatform;
    private Array<SprPlatform> arsprPlatform;
    int nHitType, HitPlatform, nAni;

    public ScrPlatform(Game _game) {
        txHitPoint = new Texture[6];
        for (int i = 0; i < 6; i++) {
            txHitPoint[i] = new Texture("target" + i + ".jpg");
        }
        nHitType = 0;
        HitPlatform = 0;
        nAni = 0;
        game = _game;
        batch = new SpriteBatch();
        txDino = new Texture("Forward.png");
        txDeadDino = new Texture("dead.png");
        txPlat = new Texture("Platform.png");
        txDinFor1 = (new Texture(Gdx.files.internal("0.png")));
        txDinoFor2 = (new Texture(Gdx.files.internal("1.png")));
        txBack1 = (new Texture(Gdx.files.internal("3.png")));
        txBack2 = (new Texture(Gdx.files.internal("4.png")));
        txJumpRight = (new Texture(Gdx.files.internal("2.png")));
        txJumpLeft = (new Texture(Gdx.files.internal("5.png")));
        txDead = (new Texture(Gdx.files.internal("6.png")));
        Gdx.input.setInputProcessor((this));
        Gdx.graphics.setDisplayMode(600, 400, false);
        sprDino = new SprDino(txDino, txHitPoint);
        sprPlatform = new SprPlatform(txPlat);
        arsprPlatform = new Array<SprPlatform>();
        arsprPlatform.add(sprPlatform);
    }

    @Override
    public void show() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (SprPlatform sprPlatform : arsprPlatform) {
            sprPlatform.update();
        }
        sprDino.PositionSet();
        HitDetection();
        sprDino.gravity();
        if (nAni == 0) {
            sprDino.Animate(txDinFor1);
        } else if (nAni == 1) {
            sprDino.Animate(txDinoFor2);
        } else if (nAni == 2) {
            sprDino.Animate(txBack2);
        } else if (nAni == 5) {
            sprDino.Animate(txBack1);
        } else if (nAni == 6) {
            sprDino.Animate(txDead);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprDino.Animate(txJumpRight);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprDino.Animate(txJumpLeft);
        }
        sprDino.update(nAni);
        batch.begin();
        for (SprPlatform sprPlatform : arsprPlatform) {
            batch.draw(sprPlatform.getSprite(), sprPlatform.getX(), sprPlatform.getY());
        }
        batch.draw(sprDino.getSprite(), sprDino.getX(), sprDino.getY());
        for (SprHitPoint _sprHitPoint : sprDino.arsprHitPoint) {
            batch.draw(_sprHitPoint.getSprite(), _sprHitPoint.getX(), _sprHitPoint.getY());
        }
        SpawnPlatform();
        batch.end();

    }

    void SpawnPlatform() {
        Iterator<SprPlatform> iter = arsprPlatform.iterator();
        while (iter.hasNext()) {
            SprPlatform sprPlatform = iter.next();
            if (sprPlatform.canSpawn() && (arsprPlatform.size < 2)) {
                sprPlatform = new SprPlatform(txPlat);
                arsprPlatform.add(sprPlatform);
            }
            if (sprPlatform.isOffScreen()) {
                iter.remove();
            }
        }
    }

    void HitDetection() {
        nHitType = nHitPlatform();
        if (nHitType == 0) {
            //System.out.println("NO HIT");
            sprDino.bPlatformCarry = false;
            sprDino.fGround = 0;
            sprDino.bGrav = true;
            sprDino.bGoThrough = false;
            if (sprDino.bJump == false) {
                sprDino.vGrav.set(0, (float) -0.4);
            }
        } else if (nHitType == 1) {
            sprDino.bGoThrough = false;
            //System.out.println("dead");
        } else if (nHitType == 2) {
            sprDino.bGoThrough = false;
            if (sprDino.bGrav && sprDino.vDir.x < 0) {
                sprDino.bJump = false;
                sprDino.bGrav = false;
            }
            if (sprDino.bMove == false && sprDino.bGrav == false) {
                sprDino.bPlatformCarry = true;
            }
            sprDino.fGround = sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight() - 1;
            sprDino.vPos.y = sprDino.fGround;
            //System.out.println("land");
        } else if (nHitType == 3) {
            sprDino.bGoThrough = true;
            //System.out.println("pass through");
        } else if (nHitType == 4) {
            sprDino.bGoThrough = false;
            //System.out.println("I'm on the ground and the block hit me");
        } else if (nHitType == 5) {
            sprDino.bGoThrough = true;
            nAni = 6;
        }
    }

    public int nHitPlatform() {
        Iterator<SprPlatform> iter = arsprPlatform.iterator();
        while (iter.hasNext()) {
            SprPlatform sprPlatform = iter.next();
            if (sprDino.getSprite().getBoundingRectangle().overlaps(sprPlatform.getSprite().getBoundingRectangle())) {
                if (sprDino.vPrevPos.y >= (sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight())) {
                    return sprDino.nHitDetection(sprPlatform, 2);
                } else if (sprDino.vPos.y == sprPlatform.vPrevPos.y + sprPlatform.getSprite().getHeight() - 1) {
                    return sprDino.nHitDetection(sprPlatform, 2);
                } else if (sprDino.vPrevPos.y + sprDino.getSprite().getHeight() <= sprPlatform.vPrevPos.y) {
                    return sprDino.nHitDetection(sprPlatform, 3);
                } else if (sprDino.bGrav && sprDino.bGoThrough == false) {
                    return sprDino.nHitDetection(sprPlatform, 1);
                } else if (sprDino.bGoThrough == true) {
                    return sprDino.nHitDetection(sprPlatform, 3);
                } else if (sprDino.bGrav == false) {
                    return sprDino.nHitDetection(sprPlatform, 4);
                }
            }
        }
        return sprDino.nHitDetection(sprPlatform, 0);
    }

    @Override
    public void resize(int i, int i1) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        sprPlatform.getSprite().getTexture().dispose();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.SPACE && sprDino.bGrav == false) {
            sprDino.vPos.add(0, 1);
            sprDino.vDir.set((float) sprDino.vDir.x, 25);
            sprDino.vGrav.set(0, (float) -0.4);
            sprDino.bGrav = true;
            sprDino.bJump = true;
            nAni = 3;
        } else if (keycode == Input.Keys.A) {
            sprDino.bMove = true;
            sprDino.vDir.set(-10, (float) sprDino.vDir.y);
            nAni = 2;
        } else if (keycode == Input.Keys.D) {
            sprDino.bMove = true;
            sprDino.vDir.set(10, (float) sprDino.vDir.y);
            nAni = 1;
        } else if (keycode == Input.Keys.E) {
            System.exit(3);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A) {
            sprDino.bMove = false;
            sprDino.vDir.set(0, (float) sprDino.vDir.y);
            nAni = 5;

        } else if (keycode == Input.Keys.D) {
            sprDino.bMove = false;
            sprDino.vDir.set(0, (float) sprDino.vDir.y);
            nAni = 0;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean scrolled(int i) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
