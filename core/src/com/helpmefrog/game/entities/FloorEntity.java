package com.helpmefrog.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.helpmefrog.game.Constants.PIXELS_IN_METER;

public class FloorEntity extends Actor {

    // PROPIEDADES A UTILIZAR
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    /*
        X DONDE ESTÁ EL BORDE IZQUIERDO DEL SUELO (EN METROS)
        WIDTH ANCHURA DEL SUELO EN METROS
        Y DONDE ESTÁ EL BORDE SUPERIOR DEL SUELO (EN METROS)


        X POSICIÓN HORIZONTAL DEL CENTRO DE LA TRAMPA (EN METROS)
        Y POSICIÓN VERTICAL DE LA BASE DE LA TRAMPA (EN METROS)

     */

    // CONSTRUCTOR
    public FloorEntity(World world, Texture texture, float x, float width, float y){
        this.world = world;
        this.texture = texture;

        // CREAMOS LA DEFINICIÓN DEL BODY DEL SUELO (SE COLOCA EN LA POSICIÓN QUE CORRESPONDE)
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + width / 2, y - 0.5f);
        // INDICAMOS QUE EL OBJETO NO SE MOVERÁ Y ESTARÁ ESTÁTICO
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);

        // SE LE DÁ FORMA A LA CAJA
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(width / 2, 0.5f);
        fixture = body.createFixture(floorShape, 1);
        floorShape.dispose();

        // BRINDAMOS EL TAMAÑO
        setSize(width * PIXELS_IN_METER, PIXELS_IN_METER);
        setPosition((x - width / 2) * PIXELS_IN_METER, (y - 1) * PIXELS_IN_METER);
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
