package com.helpmefrog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.helpmefrog.game.entities.FloorEntity;
import com.helpmefrog.game.entities.PlayerEntity;
import com.helpmefrog.game.entities.TrapEntity;

import java.util.ArrayList;
import java.util.List;

import static com.helpmefrog.game.Constants.PIXELS_IN_METER;
import static com.helpmefrog.game.Constants.SPEED_GAME;


public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private PlayerEntity player;

    // PISO Y TRAMPAS
    private List<FloorEntity> floor = new ArrayList<FloorEntity>();
    private List<TrapEntity> trap = new ArrayList<>();

    // DECLARAMOS LOS SONIDOS DE ACCIONES
    private Sound jumpSound, dieSound;
    // DECLARAMOS LA MÚSICA DEL VIDEOJUEGO
    private Music music_game;

    public GameScreen(final HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);
        // OBTENEMOS LOS SONIDOS DE LAS ACCIONES Y DEL VIDEOJUEGO EN SI
        jumpSound = helpMeFrogGame.getAssetManager().get("audio/jump.mp3");
        dieSound = helpMeFrogGame.getAssetManager().get("audio/die.mp3");
        music_game = helpMeFrogGame.getAssetManager().get("audio/help-me-frog_song.ogg");

        // DEFINIMOS EL STAGE
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0, -10), true);

        // CREAMOS EL CONTACT LISTENER PARA MANIPULAR LAS COLISONES DE NUESTRO JUEGUITO
        world.setContactListener(new ContactListener() {

            // MÉTODO QUE VERIFICARÁ LAS COLISIONES ENTRE LOS OBJETOS A y B
            private boolean areCollided(Contact contact, Object userA, Object userB){
                return (contact.getFixtureA().getUserData().equals(userA) && contact.getFixtureB().getUserData().equals(userB)) ||
                        (contact.getFixtureA().getUserData().equals(userB) && contact.getFixtureB().getUserData().equals(userA));
            }

            @Override
            public void beginContact(Contact contact) {
                // VERIFICAMOS SI EL JUGADOR HACE CONTACTO CON EL SUELO
                if(areCollided(contact, "player", "floor")){
                    player.setJumping(false);
                    // VALIDACIÓN PARA HACER QUE MIENTRAS SE MANTENGA PULSADO, CONTINUE CON EL BRINCO
                    if(Gdx.input.isTouched()){
                        jumpSound.play();
                        player.setAlwaysjump(true);
                    }
                }

                // PARA LA COLISIÓN ENTRE LOS OBSTÁCULOS Y EL PERSONAJE
                if(areCollided(contact, "player", "trap")){
                    // SI CHOCA EL PERSONAJE
                    if(player.isAlive()) {
                        dieSound.play();
                        player.setAlive(false);
                        // DETENEMOS LA MÚSICA DEL VIDEOJUEGO
                        music_game.stop();

                        // SE MMUESTRA LA PANTALLA DE GAME OVER
                        // CAMBIAMOS MEDIANTE UNA ACCIÓN DE ANIMACIÓN
                        stage.addAction(
                                // INDICAMOS QUE QUEREMOS ACCIONES EN SECUENCIA
                                Actions.sequence(
                                        // ESPERAR DURANTE SEGUNDO Y MEDIO
                                        Actions.delay(1.5f),
                                        // SALTAR A LA PANTALLA DE GAME OVER
                                        Actions.run(new Runnable() {
                                            @Override
                                            public void run() {
                                                helpMeFrogGame.setScreen(helpMeFrogGame.gameOverScreen);
                                            }
                                        })
                                )
                        );
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    @Override
    public void show() {
        // OBTENEMOS LAS TEXTURAS
        Texture playerTexture = helpMeFrogGame.getAssetManager().get("frog.png");
        Texture floorTexture = helpMeFrogGame.getAssetManager().get("pasture.png");
        Texture trapTexture = helpMeFrogGame.getAssetManager().get("trampauno.png");

        // ASIGNAMOS LAS TEXTURAS Y LOS ACTORES QUE INTERVIENEN EN EL VIDEOJUEGO
        player = new PlayerEntity(world, playerTexture, new Vector2(1, 1));

        // PISO GENERAL
        int main = 0;
        for(int con = 0; con <= 100; con++){
            floor.add(new FloorEntity(world, floorTexture, main, 10, 0.5f));
            main += 10;
        }

        // ESCALONES DE DE PISO
        floor.add(new FloorEntity(world, floorTexture, 12, 10, 1));
        floor.add(new FloorEntity(world, floorTexture, 30, 5, 1));
        floor.add(new FloorEntity(world, floorTexture, 40, 10, 1));
        floor.add(new FloorEntity(world, floorTexture, 58, 5, 1));
        floor.add(new FloorEntity(world, floorTexture, 59, 10, 1));

        // TRAMPAS GENERALES
        trap.add(new TrapEntity(world, trapTexture, 6, 0.5f));
        trap.add(new TrapEntity(world, trapTexture, 15, 1));
        trap.add(new TrapEntity(world, trapTexture, 25, 0.5f));
        trap.add(new TrapEntity(world, trapTexture, 34, 1));
        /*trap.add(new TrapEntity(world, trapTexture, 6, 0.5f));
        trap.add(new TrapEntity(world, trapTexture, 6, 0.5f));
        trap.add(new TrapEntity(world, trapTexture, 6, 0.5f));
        trap.add(new TrapEntity(world, trapTexture, 6, 0.5f));
        trap.add(new TrapEntity(world, trapTexture, 6, 0.5f));*/

        // AÑADIMOS LOS ACTORES DEL JUEGO (LOS HACEMOS VISIBLES)
        stage.addActor(player);
        for(FloorEntity floor : floor){
            stage.addActor(floor);
        }
        for(TrapEntity trapEntity : trap) {
            stage.addActor(trapEntity);
        }

        // CUANDO COMIENCE EL JUEGO
        music_game.setVolume(0.50f);
        music_game.play();
    }

    @Override
    public void hide() {
        // DETENEMOS LA MÚSICA DEL VIDEOJUEGO
        music_game.stop();

        player.detach();
        // ELIMINAMOS TODOS LOS RECURSOS CREADOS DEL VIDEOJUEGO
        for(FloorEntity floor : floor){
            floor.detach();
            floor.remove();
        }

        for(TrapEntity trapEntity : trap){
            trapEntity.detach();
            trapEntity.remove();
        }

        // REMOVE SE UTILIZA PARA ELIMINAR UN ACTOR DEL STAGE
        player.remove();
    }

    @Override
    public void render(float delta) {
        // LIMPIAMOS EL BUFFER DE BITS
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // AGREGAMOS EL COLOR DEL CIELO A LA PANTALLA
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);

        // INDICAMOS QUE LA CÁMARA DEL VIDEOJUEGO DEBE DE DESPLAZARSE A TAL VELOCIDAD QUE
        // CONCUERDE CON LA VELOCIDAD DEL PERSONAJE
        // Y QUE SE MUEVA SOLO SI SE HA AVANZADO ALGUNOS METROS Y SI CONTINUA VIVO
        if(player.getX() > 150 && player.isAlive()) {
            stage.getCamera().translate(SPEED_GAME * delta * PIXELS_IN_METER, 0, 0);
        }

        // PARA COLOCAR EL SONIDO EN CASO DE HACER UN SALTO
        if(Gdx.input.justTouched()){
            jumpSound.play();
            player.jump();
        }

        // ACTUALIZAR ACTORES
        stage.act();

        // ACTUALIZAMOS EL MUNDO Y APLICAMOS AL MISMO TIEMPO LA GRAVEDAD
        world.step(delta, 6, 2);

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}
