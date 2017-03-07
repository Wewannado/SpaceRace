package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.utils.Settings;


public class SplashScreen implements Screen {

    private Stage stage;
    private SpaceRace game;

    private Label.LabelStyle textTitleStyle, textLVLStyle;
    private Label textTitol, textLVL1, textLVL2, textLVL3;


    public SplashScreen(SpaceRace game) {

        this.game = game;

        Gdx.app.log("Splassssh!!!"," ");
        // Creem l'stage i assginem el viewport
        stage = new Stage(game.getViewport());

        // Afegim el fons
        stage.addActor(new Image(AssetManager.background));

        // Creem l'estil de l'etiqueta i l'etiqueta
        textTitleStyle = new Label.LabelStyle(AssetManager.fontTitle, null);
        textLVLStyle = new Label.LabelStyle(AssetManager.fontLVL, null);

        textTitol = new Label("SpaceRace", textTitleStyle);
        textLVL1 = new Label("Level 1", textLVLStyle);
        textLVL2 = new Label("Level 2", textLVLStyle);
        textLVL3 = new Label("Level 3", textLVLStyle);

        // Creem el contenidor necessari per aplicar-li les accions
        Container containerTitol = new Container(textTitol);
        containerTitol.setTransform(true);
        containerTitol.center();
        containerTitol.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 4);

        //Nivells
        Container containerLVL2 = new Container(textLVL2);
        containerLVL2.setTransform(true);
        containerLVL2.center();
        containerLVL2.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT-Settings.GAME_HEIGHT / 4);

        Container containerLVL1 = new Container(textLVL1);
        containerLVL1.setTransform(true);
        containerLVL1.center();
        containerLVL1.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT-(Settings.GAME_HEIGHT / 4)-textLVL2.getHeight()-10);

        Container containerLVL3 = new Container(textLVL3);
        containerLVL3.setTransform(true);
        containerLVL3.center();
        containerLVL3.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT-(Settings.GAME_HEIGHT / 4)+textLVL2.getHeight()+10);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        containerTitol.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));


        //Afegim a l'stage els actors
        stage.addActor(containerTitol);
        stage.addActor(containerLVL2);
        stage.addActor(containerLVL1);
        stage.addActor(containerLVL3);


        // Creem la imatge de la nau i li assignem el moviment en horitzontal
        Image spacecraft = new Image(AssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 4 + textTitol.getHeight();
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(0 - spacecraft.getWidth(), y), Actions.moveTo(Settings.GAME_WIDTH, y, 5))));

        stage.addActor(spacecraft);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        stage.draw();
        stage.act(delta);

        // Si es fa clic en la pantalla, canviem la pantalla
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(stage.getBatch(), game));
            dispose();
        }

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

    }
}
