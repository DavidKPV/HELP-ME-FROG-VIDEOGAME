package com.helpmefrog.game.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorTrapOne extends Actor {

    private TextureRegion trapone;

    // CONSTRUCTOR
    public ActorTrapOne(TextureRegion trapone){
        this.trapone = trapone;
        setSize(trapone.getRegionWidth(), trapone.getRegionHeight());
    }

    // ACTUALIZAR EL ACTOR (DAR MOVIMIENTO CONSTANTE)
    @Override
    public void act(float delta) {
        setX(getX() - 250 * delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(trapone, getX(), getY());
    }
}
