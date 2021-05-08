package com.helpmefrog.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

// HEREDAR DE LA CLASE GAME EN LUGAR DE APLICATIONADAPTER NOS PERMITE UTILIZAR MÚLTIPES PANTALLAS
public class HelpMeFrogGame extends Game {
	
	@Override
	public void create () {
		setScreen(new MainScreen(this));
	}

}
