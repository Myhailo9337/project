/**This class describes a class which implements the interface called Screen, and allows the user to see the game screen.
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii
 * File: PlayScreen.java
 * */

package com.gdx.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdx.project.Sprites.Student;


public class PlayScreen implements Screen {
    private Project game;
    private OrthographicCamera camera = new OrthographicCamera();
    public FitViewport gamePort = new FitViewport(1000/Student.PPM,650/Student.PPM, camera);

    private TmxMapLoader mapLoader = new TmxMapLoader();
    private TiledMap map = mapLoader.load("Maps/third_storey.tmx");
    private final MapProperties properties = map.getProperties();
    private OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(map, 2/Student.PPM);
    final int pixelMapWidth;
    final int pixelMapHeight;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Student player;
    private int noInputCounter = 0;

    public PlayScreen(Project game){
        this.game = game;
        camera.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,0), true);
        debugRenderer = new Box2DDebugRenderer();
        new WorldCreator(world, map);
        player = new Student(world, this);
        final int mapWidth = properties.get("width",Integer.class);
        final int mapHeight = properties.get("height", Integer.class);
        final int tilePixelWidth = properties.get("tilewidth",Integer.class);
        final int tilePixelHeight = properties.get("tileheight",Integer.class);
        pixelMapWidth = tilePixelWidth*mapWidth;
        pixelMapHeight = tilePixelHeight*mapHeight;
        System.out.println(pixelMapWidth);
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
         update(delta);
         Gdx.gl.glClearColor(0,0,0,1);
         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
         renderer.render();
         //Зображення прямокутників навколо об'єктів.
         //debugRenderer.render(world, camera.combined);
         game.batch.setProjectionMatrix(camera.combined);
         game.batch.begin();
         player.draw(game.batch);
         game.batch.end();
    }

    /**This method updates the game world on each iteration of the game loop.
     * */
    public void update(float deltaTime){
        handleInput(deltaTime);
        world.step(1/60f,6,2);
        player.update(deltaTime);
        Vector2 position = player.body.getPosition();
        if(position.x - gamePort.getWorldWidth()/2 >= 0 && (position.x + gamePort.getWorldWidth()/2)*Student.PPM/2 <= pixelMapWidth)
           camera.position.x = position.x;
        if(position.y - gamePort.getWorldHeight()/2 >=0 && (position.y + gamePort.getWorldHeight()/2)*Student.PPM/2 <= pixelMapHeight)
           camera.position.y = position.y;
        camera.update();
        renderer.setView(camera);
    }
    /**This method makes the character move on the map when the user presses corresponding keys on their keyboard.
     **/
    public void handleInput(float deltaTime){
        if(Gdx.input.isKeyPressed(Input.Keys.W) && player.body.getLinearVelocity().y <= 4)
            player.body.applyLinearImpulse(new Vector2(0, 1f), player.body.getWorldCenter(), true);
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && player.body.getLinearVelocity().x <=4 && !Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.body.setAwake(false);
            player.body.applyLinearImpulse(new Vector2(4.25f, 0F), player.body.getWorldCenter(), true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && player.body.getLinearVelocity().x >= -4 && !Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.body.setAwake(false);
            player.body.applyLinearImpulse(new Vector2(-4.25f, 0F), player.body.getWorldCenter(), true);
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.S) && player.body.getLinearVelocity().y >= -4)
            player.body.applyLinearImpulse(new Vector2(0, -1f), player.body.getWorldCenter(), true);
        else if(!Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D) &&
                !Gdx.input.isKeyPressed(Input.Keys.W) && ++noInputCounter >= 15) {
            player.body.setAwake(false);
            noInputCounter = 0;
        }
    }

    /**This method returns the file which contains information about main character`s images.
     **/
    public TextureAtlas getAtlas() {
        return  new TextureAtlas("Characters/Character.pack");
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       map.dispose();
       renderer.dispose();
       world.dispose();
       debugRenderer.dispose();
    }
}
