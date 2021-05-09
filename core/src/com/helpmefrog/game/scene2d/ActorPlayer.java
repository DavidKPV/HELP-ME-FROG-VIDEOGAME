package com.helpmefrog.game.scene2d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActorPlayer extends Actor {

    private Texture player;
    private boolean live = true;

    // GETTER Y SETTER PARA COMPROBAR SI ESTÁ VIVO O NO LA RANITA
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    // CONSTRUCTOR
    public ActorPlayer(Texture player){
        this.player = player;
        setSize(player.getWidth(), player.getHeight());
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(player, getX(), getY());
    }
}
