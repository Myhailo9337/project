/**This class initializes all the physical objects on the map.
 * Authors: Okhrimenko Mykhailo, Serdiuk Andrii.
 * File: WorldCreator.java
 * */


package com.gdx.project;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.project.Sprites.Obstacle;


public class WorldCreator {

    public WorldCreator(World world, TiledMap map){

        for(MapObject object : map.getLayers().get(0).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            new Obstacle(world, map, rectangle);
        }

    }
}
