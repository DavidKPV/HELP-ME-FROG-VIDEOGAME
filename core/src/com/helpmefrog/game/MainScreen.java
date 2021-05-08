package com.helpmefrog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.helpmefrog.game.actors.ActorPlayer;
import com.helpmefrog.game.actors.ActorTrapOne;

import org.graalvm.compiler.phases.common.NodeCounterPhase;

public class MainScreen extends BaseScreen {

    // CONSTRUCTOR
    public MainScreen(HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);
        player = new Texture("frog.png");
        trapone = new Texture("trampauno.png");
        regionTrapOne = new TextureRegion(trapone, 0, 45, 128, 83);
    }

    private Stage stage;

    // ACTORES
    private ActorPlayer actorPlayer;
    private ActorTrapOne actorTrapOne;

    // TEXTURAS
    private Texture player, trapone;
    private TextureRegion regionTrapOne;

    // VALORES DE LA PANTALLA
    int alto = Gdx.graphics.getHeight();
    int ancho = Gdx.graphics.getWidth();

    @Override
    public void show() {
        stage = new Stage();
        // MODO DEPURACIÓN (VER LOS BORDES DE CADA TEXTURA)
        stage.setDebugAll(true);

        // INSTANCIA DE ACTORES
        actorPlayer = new ActorPlayer(player);
        actorTrapOne = new ActorTrapOne(regionTrapOne);

        // AÑADIMOS ACTORES
        stage.addActor(actorPlayer);
        stage.addActor(actorTrapOne);

        // DEFINIR LA POSICIÓN DEL JUGADOR
        actorPlayer.setPosition(20, 100);
        actorTrapOne.setPosition(ancho/2, 100);
    }

    // LIBERRAR EL ESPACIO DE LA MEMORIA DE VIDEO CADA QUE SE SALGA DE LA PANTALLA Y NO HASTA QUE SALGA DEL JUEGO
    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        // AGREGAMOS EL COLOR DEL CIELO A LA PANTALLA
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);

        // LIMPIAMOS EL BUFFER DE BITS
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // ACTUALIZAR ACTORES
        stage.act();

        ifColison();

        stage.draw();
    }

    // METODO QUE DETECTARÁ CUANDO OCURRA UNA COLISÓN ENTRE ACTORES
    private void ifColison(){
        if(actorPlayer.isLive() && (actorPlayer.getX() + actorPlayer.getWidth() > actorTrapOne.getX())){
            actorPlayer.setLive(false);

            System.out.println("Colision");
        }
    }

    // LIBERRAR EL ESPACIO DE LA MEMORIA DE VIDEO CADA QUE SE SALGA DEL JUEGO
    @Override
    public void dispose() {
        player.dispose();
        trapone.dispose();
    }
}
