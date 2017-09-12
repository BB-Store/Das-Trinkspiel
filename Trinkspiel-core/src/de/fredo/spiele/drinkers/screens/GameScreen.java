package de.fredo.spiele.drinkers.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.fredo.spiele.drinkers.Animation.Explode;
import de.fredo.spiele.drinkers.Animation.Wuerfel;
import de.fredo.spiele.drinkers.gameobjects.Feld;
import de.fredo.spiele.drinkers.gameobjects.Player;
import de.fredo.spiele.drinkers.main.MainActivity;
import de.fredo.spiele.drinkers.simplexml.LevelPack;

public class GameScreen implements Screen, InputProcessor {

	private static final int MAXINDEX = 49;
	private static final int WIDTH = 1750;
	private static final int HEIGHT = 700;
	// Stage
	private de.fredo.spiele.drinkers.main.MainActivity game;
	private SpriteBatch batch;
	private BitmapFont font = new BitmapFont();

	private Stage stage;

	private Label.LabelStyle labelskin;
	private Skin skin;

	// Game
	private OrthographicCamera cam;
	private Array<Player> player;
	private Wuerfel wuerfel;
	private Explode explode;
	private Random random = new Random();
	private int playerCount;
	private int counter;
	private int wuerfelzahl;

	// Test
	private boolean test_Modus;
	private int index;
	private boolean ansicht;
	//

	// Textures
	private Texture background;
	private Texture feld;
	private Texture arrowLeft;
	private Texture arrowRight;
	private Texture arrowUp;
	private Texture arrowDown;
	private Array<Feld> felder;
	private Vector2 position;

	// Simple xml
	private LevelPack levelPack;
	private boolean dialogOpen;
	private Skin skinDialog;
	private float waitingTime;
	private Texture wuerfelimage;
	private boolean removeWuerfelzahl;

	public GameScreen(MainActivity game, int playCount) {
		this.game = game;
		this.playerCount = playCount;
		init();
	}

	private void init() {
		batch = new SpriteBatch();
		// wuerfel = new Wuerfel();
//		playerCount = 5;
		counter = 0;
		removeWuerfelzahl = false;
		dialogOpen = false;
		stage = new Stage(new StretchViewport(1750, 700));
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 1750, 700);
		labelskin = new Label.LabelStyle();
		labelskin.font = new BitmapFont(Gdx.files.internal("font/title.fnt"),
				Gdx.files.internal("font/title.png"), false);
		skin = new Skin(Gdx.files.internal("font/uiskin.json"),
				new TextureAtlas("font/uiskin.atlas"));
		skin.getFont("default-font").scale(2);
		skin.getFont("default-font").getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// skinDialog = new Skin(Gdx.files.internal("font/dialog.json"));
		loadTextures();
		// initFelder();
		// justForTesting();
		Gdx.app.debug("TEST3", "Texturen geladen");
		Gdx.input.setInputProcessor(this);
		rebuildStage();
		loadGame();
		createPlayer();

	}

	private void createPlayer() {
		player = new Array<Player>();
		for (int i = 0; i < playerCount; i++) {
			Player p = new Player("Spieler" + Integer.toString(i + 1));
			Texture tex = new Texture("player/spieler"
					+ Integer.toString(i + 1) + ".png");
			p.setTexture(tex);
			p.setPosition(felder.get(p.getIndex()).getPosition().x + i * 45,
					felder.get(p.getIndex()).getPosition().y + i * 5);
			player.add(p);
		}
		Gdx.app.debug("TEST2", "Spieler gehen");
	}

	private void loadGame() {
		String langueage = java.util.Locale.getDefault().toString();
		Gdx.app.debug("TEST", langueage);
		// if (langueage.equals("de_DE")) {
		levelPack = game.actionResolver.loadLevel("xml/spielde.xml");
		// } else {
		// Load englisch version
		// }

		initGame();
	}

	private void initGame() {
		// spielbrett = levelPack.getSpielbrett().get(1);
		initFelder();
	}

	private void initFelder() {
		felder = new Array<Feld>();
		for (de.fredo.spiele.drinkers.simplexml.Level f : levelPack.getLevels()) {
			String name = f.getName();
			int index = f.getIndex();
			int x = f.getPositionX();
			int y = f.getPositionY();
			String text = f.getName();
			String arrow = f.getArrow();
			String effect = f.getEffect();
			int value = f.getValue();
			String type = f.getType();
			loadFieldTexture(type);
			Feld feldObject = new Feld(name, index, feld, x, y, text, arrow,
					effect, value, type);
			felder.add(feldObject);
		}
	}

	private void loadFieldTexture(String type) {
		switch (type) {
		case "drink":
			feld = new Texture("field/drinkfield.png");
			break;
		case "shot":
			feld = new Texture("field/shotfield2.png");
			break;
		case "general":
			feld = new Texture("field/generalfield.png");
			break;
		case "start":
			feld = new Texture("field/startfield.png");
			break;
		case "ziel":
			feld = new Texture("field/zielfield.png");
			break;
		default:
			Gdx.app.debug("Type", "Missing: " + type);
			break;
		}

		// if (type.equals("drink")) {
		// feld = new Texture("field/drinkfield.png");
		// } else if (type.equals("shot")) {
		// feld = new Texture("field/shotfield2.png");
		// } else if (type.equals("shot")) {
		// feld = new Texture("field/drinkfield.png");
		// } else if (type.equals("start")) {
		// feld = new Texture("field/drinkfield.png");
		// } else if (type.equals("ziel")) {
		// feld = new Texture("field/drinkfield.png");
		// } else {
		// feld = new Texture("field/shotfield.png");
		// }

	}

	private void loadTextures() {
		background = new Texture("background.png");
		arrowDown = new Texture("field/pfeilunten.png");
		arrowUp = new Texture("field/pfeiloben.png");
		arrowLeft = new Texture("field/pfeillinks.png");
		arrowRight = new Texture("field/pfeilrechts.png");
	}

	private void rebuildStage() {
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(1750, 700);
		// stack.add(addPlayButton());
	}

	private Table addPlayButton() {
		Table layer = new Table();
		layer.top();

		TextButton ansicht_btn = new TextButton("Ansicht", skin);
		ansicht_btn.getStyle().font.scale(1);
		layer.add(ansicht_btn).width(500).height(150);
		ansicht_btn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
			}
		});

		TextButton play_btn = new TextButton("Play", skin);
		play_btn.getStyle().font.scale(1);
		layer.add(play_btn).width(750).height(150);
		play_btn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				// movePlayer();
			}
		});

		TextButton beenden_btn = new TextButton("Ansicht", skin);
		beenden_btn.getStyle().font.scale(1);
		layer.add(beenden_btn).width(500).height(150);
		beenden_btn.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				spielBeenden();
			}
		});

		return layer;
	}

	protected void spielBeenden() {
		// TODO Auto-generated method stub

	}

	protected void movePlayer(int moveforward) {
		stage.clear();
		// int wuerfelzahl;
		// 0: Dialog-Task ist nicht offen --> neuer Spielzug möglich
		removeWuerfelzahl = true;
		if (moveforward == 0) {
			wuerfelzahl = random.nextInt(5) + 1;
		} else {
			return;
		}
		// Animation
		if (wuerfel != null) {
			wuerfel.dispose();
			wuerfelimage.dispose();
		}
		wuerfel = new Wuerfel();
		waitingTime = 0.0f;
		String wuerfelergebnisString = "wuerfel" + Integer.toString(wuerfelzahl);
		wuerfelimage = new Texture("wurf/" + wuerfelergebnisString + ".png");

	}

	private void continueMovePlayer() {
		Gdx.app.debug("Wuerfel", "Zahl: " + Integer.toString(wuerfelzahl));
		Player p = player.get(counter);
		if (p.getIndex() + wuerfelzahl < MAXINDEX) {
			p.setIndex(p.getIndex() + wuerfelzahl);
		} else {
			p.setIndex(MAXINDEX - 1);
		}
		p.setPosition(felder.get(p.getIndex()).getPosition().x + counter * 45,
				felder.get(p.getIndex()).getPosition().y + counter * 5);
		// führe Spieleraktion aus
		openDialog(p);

		if (counter < playerCount - 1) {
			counter++;
		} else {
			counter = 0;
		}
	}

	private void openDialog(Player p) {
		dialogOpen = true;
		showCardText(felder.get(p.getIndex()).feldAktion(), p);
		if (felder.get(p.getIndex()).getType().equals("general")) {
			Gdx.app.debug("General", "Ein Generalfeld");
			if (felder.get(p.getIndex()).getEffect().equals("movedown")) {
				Gdx.app.debug("movedown", "ein MoveDownfeld");
				int minus = felder.get(p.getIndex()).getValue();
				//bug
				movePlayer(minus);
			} else if (felder.get(p.getIndex()).getEffect().equals("moveup")) {
				int plus = felder.get(p.getIndex()).getValue();
				Gdx.app.debug("moveup", Integer.toString(p.getIndex()));
				//bug
				movePlayer(plus);
			} else if (felder.get(p.getIndex()).getEffect().equals("swap")) {
				int randomPlayer = random.nextInt(playerCount - 1) + 1;
				int randomPlayerIndex = player.get(randomPlayer - 1).getIndex();
				
				player.get(randomPlayer - 1).setIndex(p.getIndex());
				p.setIndex(randomPlayerIndex);

				// DEBUG CONSOLE
				Gdx.app.debug(
						"SWAP",
						"Die Zufallszahl: "
								+ Integer.toString(randomPlayer - 1));
				Gdx.app.debug(
						"SWAP",
						"Spieler" + Integer.toString(p.getIndex())
								+ "Tauscht mit Spieler"
								+ Integer.toString(randomPlayer));
				Gdx.app.debug("swap vorher",
						Integer.toString(player.get(randomPlayer).getIndex()));
				Gdx.app.debug("randomPlayer: ", Integer.toString(randomPlayer));
				Gdx.app.debug("randomPlayerIndex: ",
						Integer.toString(randomPlayerIndex));
				// ----------------------------------------------------

								
				//SET POSITION FROM BOTH PLAYER
				player.get(randomPlayer - 1).setPosition(
						felder.get(player.get(randomPlayer - 1).getIndex())
								.getPosition().y + (randomPlayer - 1) * 10,
						felder.get(player.get(randomPlayer - 1).getIndex())
								.getPosition().x + (randomPlayer - 1) * 30);

				p.setPosition(felder.get(p.getIndex()).getPosition().x
						+ counter * 30,
						felder.get(p.getIndex()).getPosition().y + counter * 10);

				// DEBUG CONSOLE
				Gdx.app.debug("swap nachher", Integer.toString(p.getIndex()));
				Gdx.app.debug("swap nachher",
						Integer.toString(player.get(randomPlayer).getIndex()));
				// --------------------
			}

		}
	}

	private void showCardText(String text, Player p) {
		Gdx.input.setInputProcessor(stage);

		// Table
		Table table = new Table(skin);
		table.add(new Label(p.getName(), skin));
		table.row();
		table.add(new Label(text, skin)).padTop(150);
		table.row();
		table.add(new Label("Oder du bist behindert", skin));
		// table.row();
		// table.add(new TextButton("OK", skin));
		table.setTouchable(Touchable.enabled);
		// Table end

		final Dialog dialog = new Dialog("", skin);
		// dialog.text(text, labelskin);
		// dialog.row();
		// dialog.text(text, labelskin);
		dialog.debugTable();
		dialog.fadeDuration = 1.5f;
		dialog.setBackground(new TextureRegionDrawable(new TextureRegion(
				new Texture(Gdx.files.internal("field/background_start.png")))));
		// Label txtLabel = new Label(text, labelskin);
		// dialog.row();
		// TextButton ok = new TextButton(text, skin);
		// ok.setStyle(skin.get("green", TextButtonStyle.class));
		dialog.add(table).width(1750).height(700);
		// dialog.button("Done");
		// dialog.getButtonTable().add(ok).width(1750).height(700).top()
		// .padBottom(50);

		table.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				CloseDialog(dialog);
			}
		});

		// table.addListener(new ChangeListener() {
		//
		// @Override
		// public void changed(ChangeEvent event, Actor actor) {
		// CloseDialog(dialog);
		// }
		// });
		dialog.show(stage);
	}

	protected void CloseDialog(Dialog dialog) {
		dialog.hide();
		Gdx.input.setInputProcessor(this);
		dialogOpen = false;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		// batch.draw(background, 0, 0, 2000, 1400);
		for (Feld f : felder) {
			batch.draw(f.getPic(), f.getPosition().x, f.getPosition().y,
					f.getWidth(), f.getHeight());
			if (!f.getArrow().equals("null")) {
				drawArror(f);
			}
		}
		for (Player p : player) {
			p.draw(batch);
		}
		if (wuerfel != null) {
			wuerfel.render(batch, delta);
			waitingTime += delta;
			if (waitingTime >= 3.0f) {
				batch.draw(wuerfelimage,WIDTH/2 - 200, HEIGHT/2-100, 400, 200);
			}
		}

		// if (wuerfel.startDrawing()) {
		// wuerfel.playAnimation(delta);
		// batch.draw(wuerfel.getWuerfelPicture(), 400, 300, 850, 200);
		// if (wuerfel.finished()) {
		// continueMovePlayer();
		// }
		// }
		batch.end();

		stage.act(delta);
		stage.draw();
		Table.drawDebug(stage);
	}

	private void drawArror(Feld f) {
		switch (f.getArrow()) {
		case "right":
			batch.draw(arrowRight, f.getPosition().x + 15, f.getPosition().y,
					arrowRight.getWidth() / 2, arrowRight.getHeight() / 2);
			break;
		case "left":
			batch.draw(
					arrowLeft,
					f.getPosition().x + f.getWidth()
							- (arrowLeft.getWidth() / 2 + 15),
					f.getPosition().y + (f.getHeight() / 2 - 5),
					arrowLeft.getWidth() / 2,
					arrowLeft.getHeight() / 2 + f.getHeight()
							- (arrowLeft.getHeight() + 10));
			break;
		case "down":
			batch.draw(
					arrowDown,
					f.getPosition().x + 15,
					f.getPosition().y + (f.getHeight() / 2 - 5),
					arrowDown.getWidth() / 2,
					arrowDown.getHeight() / 2 + f.getHeight()
							- (arrowLeft.getHeight() + 10));
			break;
		case "up":
			batch.draw(arrowUp,
					f.getPosition().x + f.getWidth()
							- (arrowUp.getWidth() / 2 + 15),
					f.getPosition().y + 5, arrowUp.getWidth() / 2,
					arrowUp.getHeight() / 2);
			break;
		default:
			Gdx.app.debug("Pfeile", "Pfeil fehlt: " + f.getArrow());
			break;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

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
		for (int i = 0; i < 5; i++)
			player.get(i).getTexture().dispose();
		batch.dispose();
		feld.dispose();
		arrowLeft.dispose();
		arrowDown.dispose();
		arrowUp.dispose();
		arrowRight.dispose();
		font.dispose();
		skin.dispose();
		background.dispose();
		// wuerfel.dispose();
		if (wuerfelimage != null) {
			wuerfelimage.dispose();
		}
		labelskin.font.dispose();
		skinDialog.dispose();
		for (Feld f : felder) {
			f.dispose();
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (removeWuerfelzahl) {
			waitingTime = 0;
			wuerfel.dispose();
			wuerfel = null;
			continueMovePlayer();
			removeWuerfelzahl = false;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (!dialogOpen && !removeWuerfelzahl) {
			movePlayer(0);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
