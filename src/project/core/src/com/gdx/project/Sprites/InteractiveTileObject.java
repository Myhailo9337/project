/**This class describes an abstract interactive object on the map.
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii;
 * File: InteractiveTileObject.java
 * */

package com.gdx.project.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;

    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        BodyDef definition = new BodyDef();
        FixtureDef fixture = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        definition.type = BodyDef.BodyType.StaticBody;
        definition.position.set((bounds.getX() + bounds.getWidth()/2)/Student.PPM*2, (bounds.getY() + bounds.getHeight()/2)/Student.PPM*2);
        body = world.createBody(definition);
        shape.setAsBox(bounds.getWidth()/1/Student.PPM,bounds.getHeight()/1/Student.PPM);
        fixture.shape = shape;
        body.createFixture(fixture);
    }
}
