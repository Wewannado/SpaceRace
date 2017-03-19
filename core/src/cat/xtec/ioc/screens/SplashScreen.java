package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cat.xtec.ioc.SpaceRace;
import cat.xtec.ioc.helpers.GameAssetManager;
import cat.xtec.ioc.helpers.SplashScreenInputHandler;
import cat.xtec.ioc.utils.Settings;


public class SplashScreen implements Screen, ChangeListener{

    private Stage stage;
    private SpaceRace game;

    private Label.LabelStyle textTitleStyle, textLVLStyle;
    private Label textTitol;
    private TextButton textLVL1, textLVL2, textLVL3;
    private int microContador = 0;

    public SplashScreen(SpaceRace game)  {

        this.game = game;

        Gdx.app.log("Splassssh!!!", " ");
        // Creem l'stage i assginem el viewport
        stage = new Stage(game.getViewport());

        // Afegim el fons
        stage.addActor(new Image(GameAssetManager.background));

        // Creem l'estil de l'etiqueta i l'etiqueta
        textTitleStyle = new Label.LabelStyle(GameAssetManager.fontTitle, null);
        textLVLStyle = new Label.LabelStyle(GameAssetManager.fontLVL, null);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = GameAssetManager.fontLVL;

        textTitol = new Label("SpaceRace", textTitleStyle);
        textLVL1 = new TextButton("Level 1", textButtonStyle);
        textLVL2 = new TextButton("Level 2", textButtonStyle);
        textLVL3 =new TextButton("Level 3", textButtonStyle);

        // Creem el contenidor necessari per aplicar-li les accions
        Container containerTitol = new Container(textTitol);
        containerTitol.setTransform(true);
        containerTitol.center();
        containerTitol.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT / 4);

        //Nivells
        Container containerLVL2 = new Container(textLVL2);
        containerLVL2.setTransform(true);
        containerLVL2.center();
        containerLVL2.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT - Settings.GAME_HEIGHT / 4);

        Container containerLVL1 = new Container(textLVL1);
        containerLVL1.setTransform(true);
        containerLVL1.center();
        containerLVL1.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT - (Settings.GAME_HEIGHT / 4) - textLVL2.getHeight() - 10);

        Container containerLVL3 = new Container(textLVL3);
        containerLVL3.setTransform(true);
        containerLVL3.center();
        containerLVL3.setPosition(Settings.GAME_WIDTH / 2, Settings.GAME_HEIGHT - (Settings.GAME_HEIGHT / 4) + textLVL2.getHeight() + 10);

        // Afegim les accions de escalar: primer es fa gran i després torna a l'estat original ininterrompudament
        containerTitol.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 1), Actions.scaleTo(1, 1, 1))));


        //Afegim a l'stage els actors
        stage.addActor(containerTitol);
        stage.addActor(containerLVL2);
        stage.addActor(containerLVL1);
        stage.addActor(containerLVL3);

        //Afegim els listerners als bottons
        containerLVL1.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SplashScreen.this.getGame().dificultat=1;
                SplashScreen.this.getGame().setScreen(new GameScreen(SplashScreen.this.getStage().getBatch(), SplashScreen.this.getGame()));
            }
        });
        containerLVL2.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SplashScreen.this.getGame().dificultat=2;
                SplashScreen.this.getGame().setScreen(new GameScreen(SplashScreen.this.getStage().getBatch(), SplashScreen.this.getGame()));
            }
        });
        containerLVL3.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                SplashScreen.this.getGame().dificultat=3;
                SplashScreen.this.getGame().setScreen(new GameScreen(SplashScreen.this.getStage().getBatch(), SplashScreen.this.getGame()));
            }
        });

        // Creem la imatge de la nau i li assignem el moviment en horitzontal
        Image spacecraft = new Image(GameAssetManager.spacecraft);
        float y = Settings.GAME_HEIGHT / 4 + textTitol.getHeight();
        spacecraft.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.moveTo(0 - spacecraft.getWidth(), y), Actions.moveTo(Settings.GAME_WIDTH, y, 5))));

        stage.addActor(spacecraft);

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.draw();
        stage.act(delta);
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

    public Stage getStage() {
        return stage;
    }

    public SpaceRace getGame() {
        return game;
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {

    }
}
