package com.helpmefrog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.graalvm.compiler.phases.common.NodeCounterPhase;

public class GameMainScreen extends BaseScreen {

    // UTILIZAMOS LA LIBRERÍA DE SCENE 2D UI PARA IMPLEMENTAR BOTONES Y/O LETRAS

    private Stage stage;
    // ESTILO
    private Skin skin;
    private Image nameGame, iconGame;
    // CLASE DEL BOTÓN CON TEXTO
    private TextButton textButton;

    // CONSTRUCTOR
    public GameMainScreen(final HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);
        // INSTANCIAMOS EL STAGE
        stage = new Stage(new FitViewport(640, 360));
        // INSTANCIAMOS LA SKIN PARA QUE PARA EL ESTILO QUE OBTENDREMOS DESDE LA CARPETA DE SKINS
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        // CREAMOS LA IMAGEN QUE TRAE EL NOMBRE DEL VIDEOJUEGO Y LA IMAGEN PRINCIPAL DEL VIDEOJUEGO
        nameGame = new Image(helpMeFrogGame.getAssetManager().get("name_game.png", Texture.class));
        iconGame = new Image(helpMeFrogGame.getAssetManager().get("main_game.png", Texture.class));

        // GENERAMOS EL BOTÓN A PARTIR DE LA "PIEL" CREADA
        textButton = new TextButton("JUGAR", skin);

        // COLOCAMOS EN POSICIÓN EL NOMBRE DEL VIDEOJUEGO Y EL ÍCONO DEL JUEGO
        nameGame.setPosition(320 - nameGame.getWidth() / 2, 250 - nameGame.getHeight() / 2);
        iconGame.setSize(200, 180);
        iconGame.setPosition( 50, 20 );

        // POSICIONAMOS EL BOTÓN DE INICIO
        textButton.setSize(200, 70);
        textButton.setPosition(350, 50);

        // DAMOS FUNCIONALIDAD AL BOTÓN DE INICIO
        textButton.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                helpMeFrogGame.setScreen(helpMeFrogGame.gameScreen);
            }
        });


        // AGREGAMOS LOS ACTORES AL STAGE
        stage.addActor(nameGame);
        stage.addActor(iconGame);
        stage.addActor(textButton);
    }

    // DENTRO DEL MÉTODO SHOW DEBE DE ESTAR EL PROCESADOR DE ENTRADA PARA QUE EL BOTÓN DE INICIO
    // TENGA ACCIÓN PARA COMENZAR EL JUEGO (SOLO SE MUESTRE LA ACCIÓN DE CLIC)
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // AL MOMENTO QUE SE OCULTE LA PANTALLA HAY QUE QUITAR EL STAGE DEL PROCESADOR DE ENTRADAS
        Gdx.input.setInputProcessor(null);
    }

    // LIMPIAMOS EL STAGE PARA EVITAR SOBRECARGAS EN LA TARJETA DE VIDEO
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    // LIMPIAMOS EL BUFFER DE BIT ANTES DE QUE SE REPRODUZCA TODO LO CREDO PARA LA PANTALLA
    @Override
    public void render(float delta) {
        // LIMPIAMOS EL BUFFER DE BITS
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // AGREGAMOS EL COLOR DEL CIELO PINTANDO TODA LA PANTALLA
        Gdx.gl.glClearColor(1, 1, 1, 0.84f);

        // ACTUALIZAMOS EL SRAGE
        stage.act();

        // DIBUJAMOS EL STAGE
        stage.draw();
    }
}
