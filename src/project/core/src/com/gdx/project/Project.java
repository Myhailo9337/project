/**This class loads the actual game and controls the process of rendering so that the user can play the game.
 * Authors: Okhrimennko Mykahilo, Serdiuk Andrii.
 * File: Project.java
 * */

package com.gdx.project;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Project extends Game {

	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {

	}
}
