package de.fredo.spiele.drinkers.main;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import de.fredo.spiele.drinkers.Interfaces.ActionResolver;
import de.fredo.spiele.drinkers.screens.Menuscreen;


public class MainActivity extends Game {
	public ActionResolver actionResolver;
	
	public MainActivity(ActionResolver aResolver) {
		actionResolver = aResolver;
		
	}
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new Menuscreen(this));
	}


}
