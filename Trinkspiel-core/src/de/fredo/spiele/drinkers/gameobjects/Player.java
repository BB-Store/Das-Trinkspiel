package de.fredo.spiele.drinkers.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Player extends Sprite {
	
	private String name;
	private Vector2 position;
	private int index;
	private boolean finished;
	
	public Player(String name) {
		this.name = name;
		position = new Vector2();
		index = 0;
		finished = false;
	}

	@Override
	public void setTexture(Texture texture) {
		// TODO Auto-generated method stub
		super.setTexture(texture);
	}


	@Override
	public void draw(Batch batch) {
		batch.draw(getTexture(), getX()+20, getY());
		super.draw(batch);
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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	
	
	
	

}
