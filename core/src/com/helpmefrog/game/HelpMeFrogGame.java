package com.helpmefrog.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

// HEREDAR DE LA CLASE GAME EN LUGAR DE APLICATIONADAPTER NOS PERMITE UTILIZAR MÚLTIPES PANTALLAS
public class HelpMeFrogGame extends Game {

	// LA CLASE ASSETMANAGER PERMITE SIMPLIFICAR LA CARGA DE RECURSOS COMO TODOS LOS PARÁMETROS DE UN TEXTURA
	private AssetManager assetManager;

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
		assetManager.finishLoading();

		setScreen(new GameScreen(this));
	}

}
