package de.fredo.spiele.drinkers.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.fredo.spiele.drinkers.main.MainActivity;

public class Menuscreen implements Screen {

	private MainActivity game;
	private Stage stage;
	private Skin skin;
	private SelectBox<Integer> playerCount;

	public Menuscreen(MainActivity game) {
		this.game = game;
		init();
	}

	private void init() {
		stage = new Stage(new StretchViewport(1000, 800));
		skin = new Skin(Gdx.files.internal("font/uiskin.json"),
				new TextureAtlas("font/uiskin.atlas"));
		skin.getFont("default-font").scale(2);
		skin.getFont("default-font").getRegion().getTexture();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	private void rebuildStage() {
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(1000, 800);
		stack.add(addMainBackground());
		stack.add(addPlayButtonNormal());
		stack.add(addTitle());
		stack.add(addPlayerSelection());
	}

	private Actor addPlayerSelection() {
		Table layer = new Table();
		layer.top().padRight(400).padTop(50);
		playerCount = new SelectBox<>(skin);
		playerCount.setItems(2, 3, 4, 5);
		playerCount.setSelected(1);
		playerCount.setColor(Color.RED);
		playerCount.sizeBy(2);
		layer.add(playerCount).width(150).height(100);
		return layer;
	}

	private Table addTitle() {
		Table layer = new Table();
		layer.top();
		Image image = new Image(new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("menu/char.png")))));
		layer.add(image).width(200).height(150).padTop(15);
		return layer;
	}

	private Table addPlayButtonNormal() {
		Table layer = new Table();
		layer.center();
		Button play = new Button(new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("menu/play.png")))));
		layer.add(play).width(240).height(200);
		play.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.debug("Screen Wechsel", "TEST");
				startNormalGame();
			}
		});
		return layer;
	}

	protected void startNormalGame() {
		Gdx.app.debug("Screen Wechsel", "DONE");
		game.setScreen(new GameScreen(game, playerCount.getSelected()));
		this.dispose();
	}

	private Table addMainBackground() {
		Table layer = new Table();
		Image image = new Image(new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("menu/huuh.png")))));
		layer.add(image).width(1000).height(800);
		return layer;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		
	}

}
