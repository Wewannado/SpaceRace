package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.GameAssetManager;
import cat.xtec.ioc.helpers.GameScreenInputHandler;
import cat.xtec.ioc.objects.Asteroid;
import cat.xtec.ioc.objects.Gui;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.Spacecraft;
import cat.xtec.ioc.utils.Settings;

public class GameScreen implements Screen {

    private Container containerPuntuacio;
    private int animacion;
    private GameState currentState;
    private SpaceRace game;
    // Objectes necessaris
    private Stage stage;
    private Spacecraft spacecraft;
    private ScrollHandler scrollHandler;

    private Label score;
    private Label.LabelStyle scoreStyle;
    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;
    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;
    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;
    private Gui gui;
    private Stage guiStage;
    public float spacecraft_Velocity;
    public float asteroid_Velocity;

    public GameScreen(Batch prevBatch, SpaceRace game) {


        // Iniciem la música
        GameAssetManager.music.play();
        this.game = game;

        //Incialitzem les velocitats depenent del nivell.
        switch (game.dificultat){
            case 1:
                this.spacecraft_Velocity=Settings.EASY_SPACECRAFT_VELOCITY;
                this.asteroid_Velocity=Settings.EASY_ASTEROID_SPEED;
                break;
            case 2:
                this.spacecraft_Velocity=Settings.MEDIUM_SPACECRAFT_VELOCITY;
                this.asteroid_Velocity=Settings.MEDIUM_ASTEROID_SPEED;
                break;
            case 3:
                this.spacecraft_Velocity=Settings.HARD_SPACECRAFT_VELOCITY;
                this.asteroid_Velocity=Settings.HARD_ASTEROID_SPEED;
                break;
        }

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(game.getViewport(), prevBatch);
        gui = new Gui(this);
        guiStage = gui.getGUIStage();

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        spacecraft = new Spacecraft(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT, this);
        scrollHandler = new ScrollHandler(this);
        scoreStyle = new Label.LabelStyle(GameAssetManager.fontLVL, null);

        score = new Label("Puntuacio " + game.getPuntuacio(), scoreStyle);
        //Creem el container per la puntuacio
        containerPuntuacio = new Container(score);
        containerPuntuacio.setTransform(true);
        containerPuntuacio.center();


        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        stage.addActor(spacecraft);


        // Donem nom a l'Actor
        spacecraft.setName("spacecraft");

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(GameAssetManager.fontTitle, "Are you\nready?");

        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new GameScreenInputHandler(this));


    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(spacecraft.getX(), spacecraft.getY(), spacecraft.getWidth(), spacecraft.getHeight());

        // Recollim tots els Asteroid
        ArrayList<Asteroid> asteroids = scrollHandler.getAsteroids();
        Asteroid asteroid;

        for (int i = 0; i < asteroids.size(); i++) {

            asteroid = asteroids.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(asteroid.getX() + asteroid.getWidth() / 2, asteroid.getY() + asteroid.getWidth() / 2, asteroid.getWidth() / 2);
        }
        shapeRenderer.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();


        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case CRASHED:
                //Gdx.app.log("CRASHED", "");
                updateCrashed(delta);
                break;
            case EXPLODING:
                //   Gdx.app.log("EXPLODING","");
                updateExploding(delta);
                break;
            case RUNNING:
                //Gdx.app.log("RUNNING", "");
                stage.addActor(containerPuntuacio);
                updateRunning(delta);
                break;
            case READY:
                //Gdx.app.log("READY", "");
                updateReady();
                break;
            case GAMEOVER:
                // Gdx.app.log("Fi del Joc","");
                updateGameOver(delta);
                break;

        }

    }

    private void updateReady() {

        // Dibuixem el text al centre de la pantalla
        batch.begin();
        GameAssetManager.fontTitle.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();
    }

    private void updateRunning(float delta) {
        stage.act(delta);
        score.setText("Puntuacio " + game.getPuntuacio());
        containerPuntuacio.setPosition(score.getWidth() / 2 + 10, 10);

        if (scrollHandler.collides(spacecraft)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            GameAssetManager.explosionSound.play();
            stage.getRoot().findActor("spacecraft").remove();
            game.restaPerMort();
            currentState = GameState.EXPLODING;


        }
        gui.actualitzarControls();
        guiStage.act(delta);
        guiStage.draw();
    }

    private void updateExploding(float delta) {
        stage.act(delta);
        batch.begin();

        batch.draw(GameAssetManager.explosionAnim.getKeyFrame(explosionTime, false), (spacecraft.getX() + spacecraft.getWidth() / 2) - 32, spacecraft.getY() + spacecraft.getHeight() / 2 - 32, 64, 64);
        batch.end();
        if (GameAssetManager.explosionAnim.getKeyFrameIndex(explosionTime) == 15) {
            animacion++;
            if (animacion > 15) {
                if (game.getPuntuacio() > 0) {
                    Gdx.app.log("Puntuacio:", "La puntuacio es " + game.getPuntuacio());
                    currentState = GameState.CRASHED;
                } else {
                    Gdx.app.log("Puntuacio:", "La puntuacio es " + game.getPuntuacio());
                    currentState = GameState.GAMEOVER;
                }
            }
        }
        explosionTime += delta;
    }

    private void updateCrashed(float delta) {
        stage.act(delta);
        textLayout.setText(GameAssetManager.fontTitle, "Crashed!");
        batch.begin();
        GameAssetManager.fontTitle.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        batch.end();


    }

    private void updateGameOver(float delta) {
        stage.act(delta);
        textLayout.setText(GameAssetManager.fontTitle, "Game Over! :'(");
        batch.begin();
        GameAssetManager.fontTitle.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        batch.end();

    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(GameAssetManager.fontTitle, "Are you\nready?");
        // Cridem als restart dels elements.
        spacecraft.reset();
        scrollHandler.reset();

        // Posem l'estat a 'Ready'

        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(spacecraft);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();


    }

    public Spacecraft getSpacecraft() {
        return spacecraft;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
    }

    public SpaceRace getGame() {
        return game;
    }

    /**
     * Retorna a la SplashScren
     */
    public void tornarPrincipal() {
        game.resetVariables();
        dispose();
        game.setScreen(new SplashScreen(game));

    }

    // Els estats del joc
    public enum GameState {
        READY, RUNNING, EXPLODING, CRASHED, GAMEOVER
    }


}
