package de.fredo.spiele.drinkers.Interfaces;

import de.fredo.spiele.drinkers.simplexml.LevelPack;

public interface ActionResolver {

	public LevelPack loadLevel(String level);

	public String getString(String key, String def);
}
