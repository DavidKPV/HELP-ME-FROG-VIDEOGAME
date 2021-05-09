package com.helpmefrog.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.helpmefrog.game.Constants.PIXELS_IN_METER;

public class PlayerEntity extends Actor {

    // PROPIEDADES A UTILIZAR
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive = true, jumping = false;

    // CONSTRUCTOR
    public PlayerEntity(World world, Texture texture, Vector2 position){
        this.world = world;
        this.texture = texture;

        // CREAMOS LA DEFINCIÓN DEL BODY
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        // INDICAMOS QUE EL OBJETO SE MOVERÁ DE FORMA DINÁMICA
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // ASIGNAMOS LAS DEFINICIONES DEL OBJETO AL BODY
        body = world.createBody(bodyDef);

        // CREAMOS LA FIGURA EN DONDE ESTARÁ LA FIXTURE PARA QUE SE PUEDA VISUALIZAR
        // EL PERSONAJE DENTRO DEL VIDEOJUEGO
        PolygonShape frogShape = new PolygonShape();
        frogShape.setAsBox(0.5f, 0.5f); // MEDIDAS EN METROS
        fixture = body.createFixture(frogShape, 3);
        frogShape.dispose();

        // DAMOS EL TAMAÑO
        setSize(PIXELS_IN_METER - 30, PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                        (body.getPosition().y -0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    // MÉTODO PARA ELIMINAR Y LIMPIAR TODO LO CREADO UNA VEZ QUE SE CIERRE EL JUEGO
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
