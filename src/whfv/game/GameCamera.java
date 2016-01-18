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

import java.util.LinkedList;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Window;
import whfv.game.processors.GameCameraMousePositionLinkProcessor;
import whfv.game.processors.GameObjectProcessor;
import whfv.position.Position;
import whfv.position.Size;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameCamera implements GameObject{

    private GameWorld mParent;
    private final Position mPosition;
    private final Size mWindowSize;
    private final Window mWindow;
    private final LinkedList<GameObjectProcessor> mGameObjectProcessors = new LinkedList<>();
    private Matrix3x3d mTransform = Matrix3x3d.IDENTITY;

    public GameCamera(Position mPosition, Size mWindowSize, Window mWindow) {
        this.mPosition = mPosition;
        this.mWindowSize = mWindowSize;
        this.mWindow = mWindow;
        mGameObjectProcessors.add(new GameCameraMousePositionLinkProcessor(this, mWindow));
    }

    
    
    
    @Override
    public void addMe(GameWorld world) {
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
        return mGameObjectProcessors;
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
        for (GameObjectProcessor mGameObjectProcessor : mGameObjectProcessors) {
            mGameObjectProcessor.process(timestep);
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        RectangleShape rs = new RectangleShape(new Vector2f(100,100));
        rs.setFillColor(new Color(255, 0, 0, 100));
        Vector2d pos = mPosition.getPosition();
        pos = Vector2d.add(pos,Vector2d.mul(mWindowSize.getSize(),0.5));
        rs.setPosition(new Vector2f((float)pos.x, (float) pos.y));
        rs.draw(target, states);
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        mPosition.transform(homoTransformation);
        mTransform = homoTransformation;
    }

    @Override
    public Matrix3x3d getTransform() {
        return mTransform;
    }
    
}
