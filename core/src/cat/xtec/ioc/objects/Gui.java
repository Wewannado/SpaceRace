package cat.xtec.ioc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import cat.xtec.ioc.helpers.GUIAssetManager;
import cat.xtec.ioc.helpers.GameAssetManager;
import cat.xtec.ioc.screens.GameScreen;
import cat.xtec.ioc.utils.Settings;


public class Gui {
    private Stage stage;
    private Group actors = new Group();
    private GUIAssetManager guiAssetManager;
    private GameScreen gamescreen;
    private ImageButton fireButton;
    private Label energy;
    private ShapeRenderer shapeRenderer;

    public Gui(GameScreen gamescreen) {
        this.gamescreen = gamescreen;
        stage = new Stage(gamescreen.getGame().getViewport());
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());

        guiAssetManager = new GUIAssetManager();

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        afegirBotoDisparar();
        afegirBarraMunicio();

        //TODO implementar funcionalitat gasolina
        afegirBarraGasolina();

        //TODO Implementar funcionalitats del Joystick
        //afegirJoystick();
        shapeRenderer.end();
        stage.addActor(actors);
    }

    private void afegirBotoDisparar() {
        TextureRegionDrawable fireDrawable;
        fireDrawable = new TextureRegionDrawable(GUIAssetManager.fire);
        fireButton = new ImageButton(fireDrawable); //Set the button up
        fireButton.setHeight(Settings.GAME_HEIGHT * 25 / 100); //** Button Height **//
        fireButton.setWidth(Settings.GAME_HEIGHT * 25 / 100); //** Button Width **//
        fireButton.setPosition(0, Settings.GAME_HEIGHT - fireButton.getWidth());
        stage.addActor(fireButton); //Add the button to the stage to p

    }

    private void afegirBarraMunicio() {
        energy = new Label("Energia " + gamescreen.getGame().getEnergia(), new Label.LabelStyle(GameAssetManager.fontLVL, null));
        //Creem el container per la puntuacio
        Container containerEnergia = new Container(energy);
        containerEnergia.setTransform(true);
        containerEnergia.setPosition(Settings.GAME_HEIGHT * 25 / 100 + 50, Settings.GAME_HEIGHT - 15);
        //Afegim un actor boto de disparar al grup d'actors
        stage.addActor(containerEnergia);

    }

    private void afegirBarraGasolina() {

        //Afegim un actor boto de disparar al grup d'actors


    }

    /**
     * Draft for Joystick functionality
     * Not actually used code.
     */
    private void afegirJoystick() {
        //Create a touchpad skin
        Touchpad touchpad;
        Touchpad.TouchpadStyle touchpadStyle;
        Skin touchpadSkin;
        Drawable touchBackground;
        Drawable touchKnob;

        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture(Gdx.files.internal("joystickBack.png")));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture(Gdx.files.internal("joystickDot2.png")));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin

        touchBackground = touchpadSkin.getDrawable("touchBackground");//touchBackground
        touchKnob = touchpadSkin.getDrawable("touchKnob");//touchKnob
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        Gdx.input.setInputProcessor(stage);
        //Create new TouchPad with the created style

        touchpad = new Touchpad(10, touchpadStyle);
        //setBounds(x,y,width,height)

        touchpad.setBounds(Settings.GAME_WIDTH - Settings.GAME_HEIGHT * 25 / 100, Settings.GAME_HEIGHT - Settings.GAME_HEIGHT * 25 / 100, Settings.GAME_HEIGHT * 25 / 100, Settings.GAME_HEIGHT * 25 / 100);

        //Create a Stage and add TouchPad
        stage.addActor(touchpad);

        //Gdx.input.setInputProcessor(stage);
    }

    public Stage getGUIStage() {
        return stage;
    }

    public void actualitzarControls() {
        energy.setText("Energia " + gamescreen.getGame().getEnergia());
    }
}


