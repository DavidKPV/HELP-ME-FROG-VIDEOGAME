package com.helpmefrog.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.helpmefrog.game.box2d.Box2DScreen;
import com.helpmefrog.game.scene2d.Scene2DScreen;

// HEREDAR DE LA CLASE GAME EN LUGAR DE APLICATIONADAPTER NOS PERMITE UTILIZAR MÚLTIPES PANTALLAS
public class HelpMeFrogGame extends Game {

	// LA CLASE ASSETMANAGER PERMITE SIMPLIFICAR LA CARGA DE RECURSOS COMO TODOS LOS PARÁMETROS DE UN TEXTURA
	private AssetManager assetManager;

	// REGISTRAMOS TODAS LAS PANTALLAS QUE HAYAMOS HECHO DEL VIDEOJUEGO PARA QUE SE PUEDAN MANIPULAR
	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;

	// CONSTRUCTOR
	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void create () {

		// INSTANCIAMOS EL ASSETMANAGER
		assetManager = new AssetManager();
		// CARGAMOS LOS RECURSOS
		assetManager.load("frog.png", Texture.class);
		assetManager.load("trampauno.png", Texture.class);
		assetManager.load("pasture.png", Texture.class);
		assetManager.load("wood.png", Texture.class);
		assetManager.load("game_over.png", Texture.class);
		assetManager.load("audio/help-me-frog_song.ogg", Music.class);
		assetManager.load("audio/die.mp3", Sound.class);
		assetManager.load("audio/jump.mp3", Sound.class);
		assetManager.finishLoading();

		// INSTANCIAMOS LAS VENTANAS DEL VIDEOJUEGO
		gameScreen = new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);

		setScreen(gameOverScreen);
	}

}
