package HitDetection;

import com.badlogic.gdx.Game;
public class GamPlatforms  extends Game {
 @Override
       public void create() {
       this.setScreen(new ScrPlatform(this));
       }
 @Override
       public void render() {
       super.render();
       }       

}