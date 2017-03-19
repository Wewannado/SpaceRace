package cat.xtec.ioc.objects;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import cat.xtec.ioc.helpers.GameAssetManager;
import cat.xtec.ioc.utils.Settings;


class Laser extends Actor {

    private Rectangle laserCollision;
    private Vector2 position;
    private int width, height;
    private int direccion;
    private ScrollHandler scrollHandler;
    boolean outOfScreen=false;

    Laser(float x, float y, int width, int height, ScrollHandler scrollHandler) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.scrollHandler = scrollHandler;
        laserCollision = new Rectangle();
        setBounds(position.x, position.y, width, height);

    }

    private TextureRegion getBalaTexture() {
        return GameAssetManager.laser;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        this.position.x += 400 * delta;

        laserCollision.set(position.x, position.y, width, height + 2);
        colisionLaserAsteroide(scrollHandler.getAsteroids());
        if (position.x + width > Settings.GAME_WIDTH) {
            outOfScreen = true;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(getBalaTexture(), position.x, position.y, width, height);
    }

    private boolean colisionLaserAsteroide(ArrayList<Asteroid> asteroides) {
        for (Asteroid asteroid : asteroides) {
            if (asteroid.collidesLaser(this)) {
                this.remove();
                asteroid.setVisible(false);
                asteroid.setContact(false);
                return true;
            }
        }
        return false;
    }

    Rectangle getLaserCollision() {
        return laserCollision;
    }
}