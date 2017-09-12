package de.fredo.spiele.drinkers.Animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Wuerfel implements Disposable {

	private Texture texture;
	private Animation walkAnimation;
	private Array<TextureRegion> frames;
	private TextureRegion region;
	private float statetime;
	private float secondstatetime;
	private boolean initExplode;
	private Explode explode;

	public Wuerfel() {
		texture = new Texture("animation/wuerfelanimation.png");
		region = new TextureRegion(texture);
		frames = new Array<TextureRegion>();
		int frameWidth = region.getRegionWidth() / 14;
		for (int i = 0; i < 14; i++) {
			frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth,
					region.getRegionHeight()));
		}
		walkAnimation = new Animation(0.1f, frames, PlayMode.NORMAL);
		initExplode = false;
	}

	// public void prepareWuerfel() {
	// finished = false;
	// isdrawing = true;
	// wuerfelAnimation.prepareAnimation();
	// }

	// public void playAnimation(float dt) {
	// wuerfelAnimation.play(dt);
	// if (wuerfelAnimation.getState()) {
	// finished = true;
	// isdrawing = false;
	// }
	// }

	public void render(SpriteBatch sb, float delta) {
		statetime += delta;
		if (!walkAnimation.isAnimationFinished(statetime)) {
			sb.draw(walkAnimation.getKeyFrame(statetime), 400, 300, 850, 200);
		} else {
			if (!initExplode) {
				initExplode = true;
				explode = new Explode();
			}
			explode.render(sb, delta);
		}
	}

	// public boolean finished() {
	// return finished;
	// }

	// public boolean isfinished() {
	// return finished;
	// }

	// public TextureRegion getWuerfelPicture() {
	// return wuerfelAnimation.getFrame();
	// }

	@Override
	public void dispose() {
		texture.dispose();
		frames.clear();
		// wuerfelAnimation.dispose();
	}

}
