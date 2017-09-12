package de.fredo.spiele.drinkers.simplexml;

import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;

public class LevelPack {
	
	@Attribute
    private String name;
    @ElementList
    private List<Level> levels;

    public String getName() {
        return name;
    }

    public List<Level> getLevels() {
        return levels;
    }

}
