/**This class describes an object on the map that the player cannot move through.
 * Authors: Okhrimenko Mykhailo, Seriuk Andrii;
 * File: Obstacle.java
 * */

package com.gdx.project.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Obstacle extends InteractiveTileObject{

    public Obstacle(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
    }
}
