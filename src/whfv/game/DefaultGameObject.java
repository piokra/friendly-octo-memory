/*
 * Copyright (C) 2016 Pan Piotr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package whfv.game;

import java.util.Iterator;
import java.util.LinkedList;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import whfv.game.processors.GameObjectProcessor;
import whfv.position.Position;
import whfv.utill.Matrix3x3d;

/**
 *
 * @author Pan Piotr
 */
public abstract class DefaultGameObject implements GameObject {

    private GameWorld mParent;
    private final LinkedList<GameObjectProcessor> mProcessors = new LinkedList<>();
    private final Position mPosition;
    private Matrix3x3d mTransform = Matrix3x3d.IDENTITY;
    public DefaultGameObject(Position pos) {
        mPosition=pos;
    }
    
    @Override
    public void addMe(GameWorld world) {
        mParent = world;
        world.addProcessable(this);
        world.addDrawable(this);
    }

    @Override
    public void removeMe(GameWorld world) {
        world.removeProcessable(this);
        world.removeDrawable(this);
    }

    @Override
    public GameWorld getParent() {
        return mParent;
    }

    @Override
    public LinkedList<GameObjectProcessor> getProcessors() {
        return mProcessors;
    }

    @Override
    public Position getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Position position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void process(double timestep) {
        
        for (GameObjectProcessor mProcessor : mProcessors) {
            mProcessor.process(timestep);
        }
        
    }


    @Override
    public void transform(Matrix3x3d homoTransformation) {
        mTransform = homoTransformation;
    }

    @Override
    public Matrix3x3d getTransform() {
        return mTransform;
    }
    
}
