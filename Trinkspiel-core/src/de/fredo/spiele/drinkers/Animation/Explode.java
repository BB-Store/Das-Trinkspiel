package de.fredo.spiele.drinkers.Animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Explode implements Disposable {
	
	private Texture texture;
	private Animation explodeAnimation;
	private Array<TextureRegion> frames;
	private TextureRegion region;
	private float statetime;

	public Explode() {
		texture = new Texture("animation/explode.png");
		region = new TextureRegion(texture);
		frames = new Array<TextureRegion>();
		int frameWidth = region.getRegionWidth() / 14;
		for (int i = 0; i < 14; i++) {
			frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth,
					region.getRegionHeight()));
		}
		explodeAnimation = new Animation(0.1f, frames, PlayMode.NORMAL);

	}
	

	public void render(SpriteBatch sb, float delta) {
		statetime += delta;
		if (!explodeAnimation.isAnimationFinished(statetime)) {
			sb.draw(explodeAnimation.getKeyFrame(statetime), 400, 300, 850, 200);
		}

		else {
			dispose();
		}
	}


	
	
	public Animation getExplodeAnimation() {
		return explodeAnimation;
	}


	@Override
	public void dispose() {
		texture.dispose();
	}

}
