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
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.graalvm.compiler.phases.common.NodeCounterPhase;

public class GameOverScreen extends BaseScreen {
    // UTILIZANDO SCENE2D UI

    private Stage stage;
    // ESTILO
    private Skin skin;
    private Image gameover;
    // CLASE DEL BOTÓN CON TEXTO
    private TextButton tbRetry;

    public GameOverScreen(final HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);
        // INSTANCIAMOS EL STAGE
        stage = new Stage(new FitViewport(640, 360));
        // INSTANCIAMOS LA SKIN PARA QUE NOS ASIGNE EL ESTILO MEDIANTE EL JSON DE LA CARPETA SKIN
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        // CREAR LA IMAGEN GAME OVER
        gameover = new Image(helpMeFrogGame.getAssetManager().get("game_over.png", Texture.class));
        // GENERO EL BOTÓN
        tbRetry = new TextButton("Reintentar", skin);

        // SE LE DÁ FUNCIONALIDAD AL BOTÓN DE REINICIO
        tbRetry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                helpMeFrogGame.setScreen(helpMeFrogGame.gameScreen);
            }
        });


        // CENTRAMO EL BOTÓN A LA MITAD
        gameover.setPosition(320- gameover.getWidth() / 2, 320 - gameover.getHeight());
        // COLOCAMOS EL TAMAÑO AL BOTÓN
        tbRetry.setSize(200, 100);
        tbRetry.setPosition(220, 50);

        // AGREGAMOS EL BOTÓN Y EL GAME OVER AL STAGE
        stage.addActor(tbRetry);
        stage.addActor(gameover);
    }

    // DENTRO DEL MÉTODO SHOW DEBE DE ESTAR EL PROCESADOR DE ENTRADA PARA QUE EL BOTÓN DE REINTENTAR
    // TENGA ACCIÓN DE REINICIAR EL JUEGO (SOLO SE MESTRE LA ACCIÓN DE CLIC)
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        // AL MOMENTO QUE SE OCULTE LA PANTALLA HAY QUE QUITAR EL STAGE DEL PROCESADOR DE ENTRADAS
        Gdx.input.setInputProcessor(null);
    }

    // LAS TEXTURAS NO SON NECESARIAS DISPOSEAR SIEMPRE Y CUANDO SE DISPOSEE EL STAGE
    @Override
    public void dispose() {
        stage.dispose();
    }

    // PARA MOSTRAR TODO LO CREADO
    @Override
    public void render(float delta) {
        // LIMPIAMOS EL BUFFER DE BITS
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // AGREGAMOS EL COLOR DEL CIELO A LA PANTALLA
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);

        // PARA ACTUALIZAR EL STAGE CON TODO LO CREADO
        stage.act();

        // PARA DIBUJAR EL STAGE
        stage.draw();
    }
}
