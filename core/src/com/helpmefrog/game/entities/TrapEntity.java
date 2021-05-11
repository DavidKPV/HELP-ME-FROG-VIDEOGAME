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

public class TrapEntity extends Actor {

    // 
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    /*

        X POSICIÓN HORIZONTAL DEL CENTRO DE LA TRAMPA (EN METROS)
        Y POSICIÓN VERTICAL DE LA BASE DE LA TRAMPA (EN METROS)

     */

    // CONSTRUCTOR
    public TrapEntity (World world, Texture texture, float x, float y){
        this.world = world;
        this.texture = texture;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y + 0.5f);
        // INDICAMOS QUE EL OBJETO NO SE MOVERÁ Y ESTARÁ ESTÁTICO
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        // CREAMOS EL ARREGLO DE VECTORES QUE PERMITIRÁN LA CREACIÓN DE UN TRIÁNGULO
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);

        PolygonShape shape = new PolygonShape();
        //shape.set(vertices);
        shape.setAsBox(0.1f, 0.1f);
        fixture = body.createFixture(shape, 1);
        // COLOCAMOS UN USER DATA A LA FIXTURE PARA CONTROLAR LAS COLISIONES O CONTACTOS
        fixture.setUserData("trap");
        shape.dispose();

        // DAMOS LA POSICIÓN Y EL TAMAÑO DE LA PUNTA
        setPosition((x - 0.5f) * PIXELS_IN_METER, y * PIXELS_IN_METER);
        setSize(PIXELS_IN_METER, PIXELS_IN_METER - 30);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }


}
