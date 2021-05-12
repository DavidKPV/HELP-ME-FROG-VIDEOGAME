package com.helpmefrog.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.helpmefrog.game.Constants;

import jdk.vm.ci.meta.Constant;

import static com.helpmefrog.game.Constants.IMPULSE_JUMP;
import static com.helpmefrog.game.Constants.PIXELS_IN_METER;
import static com.helpmefrog.game.Constants.SPEED_GAME;

public class PlayerEntity extends Actor {

    // PROPIEDADES A UTILIZAR
    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive = true, jumping = false, alwaysjump;

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
        // COLOCAMOS UN USER DATA A LA FIXTURE PARA CONTROLAR LAS COLISIONES O CONTACTOS
        fixture.setUserData("player");
        frogShape.dispose();

        // DAMOS EL TAMAÑO
        setSize(PIXELS_IN_METER - 20, PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * PIXELS_IN_METER,
                        (body.getPosition().y -0.5f) * PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        // SI HEMOS TOCADO LA PANTALLA
        if(alwaysjump){
            alwaysjump = false;
            jump();
        }

        // AVANZAR AL JUGADOR SI ESTÁ VIVO
        // APLICAR MOVIMIENTO AL PERSONAJE PARA QUE SE DIRIJA A LAS TRAMPAS QUE DEBE DE SALTAR
        // OBTENEMOS LA VELOCIDAD EN Y QUE MANEJA EL SALTO PARA QUE SE VEA PROPORCIONAL
        if(alive){
            float velicityY = body.getLinearVelocity().y;
            body.setLinearVelocity(SPEED_GAME, velicityY);
        }

        // HACER QUE CADA QUE SALTE BAJE RÁPIDO PARA EVITAR UN SALTO PROLONGADO
        if(jumping){
            body.applyForceToCenter(0, -IMPULSE_JUMP * 1.15f, true);
        }
    }

    // MÉTODO QUE HARÁ SALTAR A LA RANITA
    public void jump(){
        if(!jumping && alive){
            jumping = true;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, IMPULSE_JUMP, position.x, position.y, true);
        }
    }

    // MÉTODO PARA ELIMINAR Y LIMPIAR TODO LO CREADO UNA VEZ QUE SE CIERRE EL JUEGO
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    // GETTERS Y SETTERS PARA CONTROLAR LA VIDA Y LOS SALTOS

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isAlwaysjump() {
        return alwaysjump;
    }

    public void setAlwaysjump(boolean alwaysjump) {
        this.alwaysjump = alwaysjump;
    }
}
