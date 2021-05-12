package com.helpmefrog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LoadingScreen extends BaseScreen{
    // UTILIZAMOS SCENE 2D UI PARA CREAR UNA PEQUEÑA PANTALLA DE CARGA

    private Stage stage;
    // ESTILO
    private Skin skin;
    // LETRA
    private Label label;

    public LoadingScreen(HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);

        // INSTANCIAMOS EL STAG, CREAMOS EL SKIN, DAMOS CARACTERÍSTICAS AL LABEL
        stage = new Stage(new FitViewport(640, 360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        // DAMOS EL CONTENIDO AL LABEL
        label = new Label("Cargando...", skin);

        // POSICIONAMOS EL LABEL AL CENTRO DE LA PANTALLA
        label.setPosition(320 - label.getWidth() / 2, 180 - label.getHeight() / 2);

        // AGREGAMOS EL LABEL AL STAGE
        stage.addActor(label);
    }

    @Override
    public void render(float delta) {
        // LIMPIAMOS EL BUFFER DE BITS Y COLOREAMOS LA PANTALLA DE NEGRO
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        // REALIZAR LA CARGA DE TODOS LOS ELEMENTOS VISUALES QUE SE ENCUENTRAN EN LA CLASE PRINCIPAL
        // HELPMEFROGGAME.JAVA
        // EL MÉTODO UPDATE REGRESA TRUE CUANDO SE HAN TERMINADO DE CARGAR LOS ELEMENTOS
        if(helpMeFrogGame.getAssetManager().update()){
            // PRIMERO ARREGLAMOS LOS PIXELES

            helpMeFrogGame.finishLoading();
        }
        else{
            // OBTENER EL PROGRESO DE LA CARGA
            int progress = (int) (helpMeFrogGame.getAssetManager().getProgress() * 100);
            label.setText("Cargando... "+progress+ "%");
        }

        // ACTUALIZAMOS EL STAGE
        stage.act();

        // DIBUJAMOS EL STAGE
        stage.draw();
    }

    // DISPOSEAMOS EL STAGE PARA QUE NO SE ALMACENE NADA EN LA TARJETA DE VIDEO
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    // MÉTODO QUE ARREGLA EL DESENFOQUE DE LOS ACTORES
    private void fixTextures(){

    }
}
