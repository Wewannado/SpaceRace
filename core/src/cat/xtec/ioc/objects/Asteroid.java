package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;

import java.util.Random;

import cat.xtec.ioc.helpers.GameAssetManager;
import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class Asteroid extends Scrollable {

    private Random r;
    private boolean contact;
    private int assetAsteroid;
    private Circle collisionCircle;

    Asteroid(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);

        // Creem el cercle
        collisionCircle = new Circle();

        /* Accions */
        r = new Random();
        assetAsteroid = r.nextInt(15);

        setOrigin();

        // Rotacio
        RotateByAction rotateAction = new RotateByAction();
        rotateAction.setAmount(-90f);
        rotateAction.setDuration(0.2f);

        // Accio de repetició
        RepeatAction repeat = new RepeatAction();
        repeat.setAction(rotateAction);
        repeat.setCount(RepeatAction.FOREVER);

        // Equivalent:
        // this.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(-90f, 0.2f)));

        this.addAction(repeat);

    }

    private void setOrigin() {

        this.setOrigin(width / 2 + 1, height / 2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Actualitzem el cercle de col·lisions (punt central de l'asteroid i el radi.
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);


    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y = new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

        assetAsteroid = r.nextInt(15);
        setOrigin();
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(GameAssetManager.asteroid[assetAsteroid], position.x, position.y, this.getOriginX(), this.getOriginY(), width, height, this.getScaleX(), this.getScaleY(), this.getRotation());
    }

    // Retorna true si hi ha col·lisió
    boolean collides(Spacecraft nau) {

        // Comprovem si han col·lisionat sempre i quan l'asteroid estigui a la mateixa alçada que la spacecraft
        return position.x <= nau.getX() + nau.getWidth() && (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
    }


    boolean collidesLaser(Laser laser) {
        System.out.println((Intersector.overlaps(collisionCircle, laser.getLaserCollision())));
        return (Intersector.overlaps(collisionCircle, laser.getLaserCollision()));

    }


    public boolean isContact() {
        return contact;
    }

    void setContact(boolean contact) {
        this.contact = contact;
    }
}
