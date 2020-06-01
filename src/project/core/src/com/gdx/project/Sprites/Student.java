/**This class describes the main character of the game on the map.
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii.
 * File: Student.java
 * */

package com.gdx.project.Sprites;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.gdx.project.PlayScreen;

public class Student extends Sprite {
    public enum State {STANDING, RUNNING}

    public State currentState = State.STANDING;
    public State previousState = State.STANDING;
    public World world;
    public Body body;
    public static final float PPM = 100;
    private TextureRegion playerStand;
    private Animation<TextureRegion> playerRun;

    private boolean runningRight = true;
    private float stateTimer = 0;


    public Student(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("player_stand"));
        this.world = world;
        definePlayer(screen.gamePort.getWorldWidth()/2);
        playerStand = new TextureRegion(getTexture(), 739,113,80,110);
        setBounds(0,0,80/PPM,110/PPM);
        setRegion(playerStand);
        Array<TextureRegion> array = new Array<TextureRegion>();
        array.add(new TextureRegion(getTexture(), 903,113,80,110));
        array.add(new TextureRegion(getTexture(),903, 1, 80,110));
        playerRun = new Animation(0.125f,array);

    }

    /**This method defines physical properties of the main character on the map.
     * */
    private void definePlayer(float originX) {
        BodyDef definition = new BodyDef();
        definition.position.set(originX,225/PPM);
        definition.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(definition);
        FixtureDef fixture = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30/PPM);

        fixture.shape = shape;
        body.createFixture(fixture);
    }
    /**This method updates character`s state.
     * */
    public void update(float deltaTime){
        setPosition(body.getPosition().x - getWidth()/2 + 5/PPM, body.getPosition().y - getHeight()/2 + 5/PPM);
        setRegion(getFrame(deltaTime));
    }

    /**This method returns the frame that should be rendered next.
     * */
    public TextureRegion getFrame(float deltaTime) {
        currentState = getState();
        TextureRegion region;
        if(currentState == State.RUNNING)
                region = playerRun.getKeyFrame(stateTimer,true);
        else
                region = playerStand;
        if((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer += deltaTime : 0;
        previousState = currentState;
        return region;
    }
    /**This method returns the state of the main character in order to render the animation.
     * */
    private State getState() {
        if(body.getLinearVelocity().x != 0)
            return State.RUNNING;
        return State.STANDING;
    }

}
