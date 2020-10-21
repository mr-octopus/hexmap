package com.breathe.hexmap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class HexMapGame extends ApplicationAdapter implements InputProcessor {
    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    Sprite sprite;
    TiledMapTileLayer objectLayer;
    TextureRegion textureRegion;
    HexMap hexMap;
    ShapeRenderer shapeRenderer;
    HexCell selectedCell;
    List<HexCell> circle;

    public static void debug(String text){
        System.out.println(text);
    }
    @Override
    public void create() {

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("plains.tmx");
        tiledMapRenderer = new HexagonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
        texture = new Texture(Gdx.files.internal("tileset.png"));
        objectLayer = (TiledMapTileLayer) tiledMap.getLayers().get("objects");
        textureRegion = new TextureRegion(texture, 32, 34);
        TextureMapObject tmo = new TextureMapObject(textureRegion);
        tmo.setX(0);
        tmo.setY(0);
        objectLayer.getObjects().add(tmo);


        hexMap = new HexMap(objectLayer);
        shapeRenderer = new ShapeRenderer();

        selectedCell = hexMap.getCell(10,10);
        circle = hexMap.getCircleForCell(selectedCell);
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        shapeRenderer.circle(selectedCell.x, selectedCell.y, 10);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                HexCell cell = hexMap.getCell(i, j);
                shapeRenderer.circle(cell.x, cell.y, 2);
            }
        }
        shapeRenderer.setColor(1, 1, 0, 1);
        for (HexCell cell : circle) {
            shapeRenderer.circle(cell.x, cell.y, 10);
        }




        shapeRenderer.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
            System.exit(0);
        }
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, -32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, 32);
        if (keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        selectedCell = hexMap.getHexCellForPos(screenX, screenY);
        circle = hexMap.getCircleForCell(selectedCell);
        debug(selectedCell.toString());

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}