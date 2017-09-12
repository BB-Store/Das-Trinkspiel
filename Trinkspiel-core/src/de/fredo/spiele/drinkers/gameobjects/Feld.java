package de.fredo.spiele.drinkers.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Feld implements Disposable {
	
	private String name;
	//private int position;
	private int index;
	private Texture pic;
	private String text;
	private Vector2 position;
	private int width;
	private int height;
	private String arrow;
	private String effect;
	private int value;
	private String type;

	public Feld(String name, int index, Texture pic, int x, int y, String text, String arrow, String effect, int value, String type) {
		this.name = name;
		this.index = index;
		this.pic = pic;
		this.text = text;
		this.arrow = arrow;
		this.effect = effect;
		this.value = value;
		this.type = type;
		position = new Vector2();
		position.set(x, y);
		width = 250;
		height= 100;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Texture getPic() {
		return pic;
	}

	public void setPic(Texture pic) {
		this.pic = pic;
	}
	
	public String feldAktion(){
		return text;
	}
	
	public void setText(String text){
		this.text = text;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	

	public String getArrow() {
		return arrow;
	}

	public void setArrow(String arrow) {
		this.arrow = arrow;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void dispose() {
		pic.dispose();
	}
	

	
	

	
	

	
	


	
	

	

	
	
	
	
	

}
