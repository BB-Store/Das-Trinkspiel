package de.fredo.spiele.drinkers.simplexml;

import org.simpleframework.xml.Attribute;

public class Level {

	@Attribute
    private String name;
	
	@Attribute
    private int index;

    @Attribute
    private int positionX;

    @Attribute
    private int positionY;
    
    @Attribute
	private String arrow;
	
	@Attribute
	private String effect;
	
	@Attribute
	private int value;
	
	@Attribute
	private String type;

	public String getName() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}
	
	public String getArrow() {
		return arrow;
	}

	public String getEffect() {
		return effect;
	}

	public int getValue() {
		return value;
	}

	public String getType() {
		return type;
	}
    
    
}
