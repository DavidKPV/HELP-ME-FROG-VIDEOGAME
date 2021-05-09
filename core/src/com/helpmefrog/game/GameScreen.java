package com.helpmefrog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.helpmefrog.game.entities.FloorEntity;
import com.helpmefrog.game.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends BaseScreen {

    private Stage stage;
    private World world;
    private PlayerEntity player;
    private List<FloorEntity> floor = new ArrayList<FloorEntity>();

    public GameScreen(HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);
        // DEFINIMOS EL STAGE
        stage = new Stage(new FitViewport(640, 360));
        world = new World(new Vector2(0, -10), true);
    }

    @Override
    public void show() {
        // OBTENEMOS LAS TEXTURAS
        Texture playerTexture = helpMeFrogGame.getAssetManager().get("frog.png");
        Texture floorTexture = helpMeFrogGame.getAssetManager().get("pasture.png");

        // ASIGNAMOS LAS TEXTURAS Y LOS ACTORES QUE INTERVIENEN EN EL VIDEOJUEGO
        player = new PlayerEntity(world, playerTexture, new Vector2(1, 2));
        floor.add(new FloorEntity(world, floorTexture, 0, 1000, 0.5f));

        stage.addActor(player);
        for(FloorEntity floor : floor){
            stage.addActor(floor);
        }
    }

    @Override
    public void hide() {
        player.detach();
        for(FloorEntity floor : floor){
            floor.detach();
            floor.remove();
        }
        // REMOVE SE UTILIZA PARA ELIMINAR UN ACTOR DEL STAGE
        player.remove();
    }

    @Override
    public void render(float delta) {
        // AGREGAMOS EL COLOR DEL CIELO A LA PANTALLA
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);

        // LIMPIAMOS EL BUFFER DE BITS
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
