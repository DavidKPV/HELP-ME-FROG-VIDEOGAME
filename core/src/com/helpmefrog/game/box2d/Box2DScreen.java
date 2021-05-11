package com.helpmefrog.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Box2DScreen extends BaseScreen {

    // CONSTRUCTOR
    public Box2DScreen(HelpMeFrogGame helpMeFrogGame) {
        super(helpMeFrogGame);
    }

    // CREAR UN MUNDO
    private World world;
    // INSTANCIA DE BOX 2D PARA VER MATERIALES DENTRO DEL MUNDO
    private Box2DDebugRenderer renderer;
    // CREAMOS UNA CÁMARA (FUNCIONA MUY PROFUNDAMENTE CON MATRICES PARA EL TAMAÑO DE TODOS LOS ELEMENTOS)
    private OrthographicCamera camera;
    // CREAMOS UN BODY Y SU FIXTURE (DEFINICIÓN) PARA CREAR AL PERSONAJE
    private Body frog, floor, tip;
    private Fixture frogfixture, floorFixture, tipFixture;

    // PARA COMPROBAR QUE EL PERSONAJE HAYA TOCADO EL SUELO ANTES DE PERMITIRLE UN NUEVO SALTO
    private boolean needJump, isJumpingFrog, isLiveFrog = true;

    @Override
    public void show() {
        // LOS PARÁMETROS QUE UTILIZA SON GRAVEDAD Y TRUE PARA QUE NO HAGA NADA SI NO HAY NADA QUE SIMULAR
        world = new World(new Vector2(0, -10), true);
        renderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(16, 9);
        // MOVEMOS LA CÁMARA PARA QUE SE ENCUENTRE EL PISO UN METRO MÁS ABAJO
        camera.translate(0, 1);

        // LEER CONTACTOS Y/O COLISIONES PARA EVITAR QUE EL PERSONAJE TENGA LA LIBERTAD DE SALTAR SOBRE EL SALTO MISMO XD
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // OBTENEMOS DENTRO DE LAS VARIABLES FICTURES LAS FIXTURAS QUE ESTÁN TENIENDO CONTACTO
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

                // IFS QUE COMPROBARÁN QUE FIXTURA LE PERTENECE A "FIXTURA A" O "FICTURA B"
                if((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("floor")) ||
                        (fixtureA.getUserData().equals("floor") && fixtureB.getUserData().equals("player"))){
                    if(Gdx.input.isTouched()){
                        needJump = true;
                    }
                    isJumpingFrog = false;
                }

                if((fixtureA.getUserData().equals("player") && fixtureB.getUserData().equals("trap")) ||
                        (fixtureA.getUserData().equals("trap") && fixtureB.getUserData().equals("player"))){
                    isLiveFrog = false;
                }
            }

            @Override
            public void endContact(Contact contact) {
                // OBTENEMOS DENTRO DE LAS VARIABLES FICTURES LAS FIXTURAS QUE ESTÁN TENIENDO CONTACTO
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();

                // IFS QUE COMPROBARÁN QUE FIXTURA LE PERTENECE A "FIXTURA A" O "FICTURA B"
                if(fixtureA == frogfixture && fixtureB == floorFixture){
                    isJumpingFrog = true;
                }

                if(fixtureA == floorFixture && fixtureB == frogfixture){
                    isJumpingFrog = true;
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        // CREAMOS UN BODY DEF PARA INDICAR LA CREACIÓN (DEFINICIONES) DEL PERSONAJE Y SE LO ASIGNAMOS AL BODY
        frog = world.createBody(createFrogBodyDef());
        floor = world.createBody(createFloorBodyDef());
        tip = world.createBody(createTipBodyDef(6f));


        // CREAMOS EL FIXTURE DEL SUELO PARA QUE SE PUEDA OBSERVAR
        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox(500, 1);
        floorFixture = floor.createFixture(floorShape, 1);
        floorShape.dispose();

        // CREAMOS LA FICTURE DE LA PUNTA
        tipFixture = createTipFixture(tip);

        // USER DATA PARA SABER SI EL PERSONAJE HA CHOCADO CON LA PUNTA
        frogfixture.setUserData("player");
        floorFixture.setUserData("floor");
        tipFixture.setUserData("trap");
    }

    // FUNCIONES PARA CREAR LA DEFINCIÓN DEL BODY DE TODOS LOS OBJETOS A DIBUJAR
    private BodyDef createFrogBodyDef(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0.5f);
        // INDICAMOS QUE EL OBJETO SE MOVERÁ DE FORMA DINÁMICA
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        return  bodyDef;
    }

    private BodyDef createFloorBodyDef(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, -1);
        // INDICAMOS QUE EL OBJETO NO SE MOVERÁ Y ESTARÁ ESTÁTICO
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }

    private BodyDef createTipBodyDef(float x){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, 0.5f);
        // INDICAMOS QUE EL OBJETO NO SE MOVERÁ Y ESTARÁ ESTÁTICO
        bodyDef.type = BodyDef.BodyType.StaticBody;
        return bodyDef;
    }

    // MÉTODO PARA CREAR LA FIXTURE DE LA PUNTA QUE SE VA A UTILIZAR
    private Fixture createTipFixture(Body tip){
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.5f, -0.5f);
        vertices[1] = new Vector2(0.5f, -0.5f);
        vertices[2] = new Vector2(0, 0.5f);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        Fixture fix = tip.createFixture(shape, 1);
        shape.dispose();
        return fix;
    }

    @Override
    public void dispose() {
        tip.destroyFixture(tipFixture);
        floor.destroyFixture(floorFixture);
        frog.destroyFixture(frogfixture);
        world.destroyBody(frog);
        world.destroyBody(floor);
        world.destroyBody(tip);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // SI DEBE SALTAR
        if(needJump){
            needJump = false;
            jumpFrog();
        }

        // VERIFICAMOS SE SE ESTÁ TOCANDO LA PANTALLA PARA QUE PUEDA ESTAR SALTANDO CONTINUAMENTE
        if(Gdx.input.justTouched() && !isJumpingFrog){
            needJump = true;
        }

        // APLICAR MOVIMIENTO AL PERSONAJE PARA QUE SE DIRIJA A LAS TRAMPAS QUE DEBE DE SALTAR
        // OBTENEMOS LA VELOCIDAD EN Y QUE MANEJA EL SALTO PARA QUE SE VEA PROPORCIONAL
        if(isLiveFrog) {
            float velicityY = frog.getLinearVelocity().y;
            frog.setLinearVelocity(8, velicityY);
        }


        // SIMULAR EL MUNDO (ACTUALIZA FÍSICAS) LOS VALORES PUESTOS SON DE ACUERDO A LA DOCUMENTACIÓN
        world.step(delta, 6, 2);

        // LA CÁMARA DEBE ACTUALIZARSE
        camera.update();

        // SE DIBUJA EL MUNDO (ES NECESARIO PRIMERO ACTUALIZAR LAS FÍSICAS QUE DIBUJAR)
        renderer.render(world, camera.combined);
    }

    // MÉTODO QUE REALIZARÁ LOS SALTOS DEL PERSONAJE
    private void jumpFrog(){
        Vector2 position = frog.getPosition();
        frog.applyLinearImpulse(0, 6, position.x, position.y, true);
    }
}
