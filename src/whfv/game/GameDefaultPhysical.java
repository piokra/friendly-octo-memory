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

import whfv.game.processors.GameObjectProcessor;
import whfv.game.processors.GameObjectMover;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import whfv.collision.Collidable;
import whfv.collision.CollidingShape;
import whfv.collision.Collision;
import whfv.collision.ConvexCollidingShape;
import whfv.game.processors.GameObjectBasicTorqueProcessor;
import whfv.game.processors.GameObjectCollisionCollector;
import whfv.game.processors.GameObjectCollisionForcer;
import whfv.game.processors.GameObjectDragProcessor;
import whfv.game.processors.GameObjectForceProcessor;
import whfv.game.processors.GameObjectForcer;
import whfv.game.processors.GameObjectVelocityProcessor;
import whfv.holder.Holdable;
import whfv.holder.SpatialHoldable;
import whfv.hotkeys.Hotkey;
import whfv.hotkeys.HotkeyProcessor;
import whfv.hotkeys.HotkeyTask;
import whfv.hotkeys.Hotkeyable;
import whfv.position.Position;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameDefaultPhysical implements PhysicalGameObject, Hotkeyable {

    private Position mPosition;
    private final double mMass;
    private Vector2d mVelocity = Vector2d.VECTOR_ZERO;
    private GameWorld mParent;
    private final double mElasticity;
    private ConvexCollidingShape mCollidingShape;
    private RectangleShape mDrawShape;
    private final LinkedList<GameObjectProcessor> mGameObjectProcessors = new LinkedList<>();
    private final HotkeyProcessor mHotkeyProcessor = new HotkeyProcessor();
    private Matrix3x3d mTransform = Matrix3x3d.IDENTITY;
    private final ConcurrentLinkedQueue<Vector2d> mUnprocessedForces = new ConcurrentLinkedQueue<>();
    private final LinkedList<Collision> mCollisions = new LinkedList<>();
    private SpatialHoldable<Collidable> mQuadTreeHolder;

    public GameDefaultPhysical(Position mPosition, double mMass, double mElasticity,
            ConvexCollidingShape mCollidingShape, RectangleShape mDrawShape) {
        this.mPosition = mPosition;
        this.mMass = mMass;
        this.mElasticity = mElasticity;
        this.mCollidingShape = mCollidingShape;
        this.mDrawShape = mDrawShape;

        mGameObjectProcessors.add(new GameObjectCollisionCollector(this));
        mGameObjectProcessors.add(new GameObjectBasicTorqueProcessor(this, 0.77));
        mGameObjectProcessors.add(new GameObjectCollisionForcer(this));
        mGameObjectProcessors.add(new GameObjectDragProcessor(this, 0.03));
        mGameObjectProcessors.add(new GameObjectForceProcessor(this));
        mGameObjectProcessors.add(new GameObjectVelocityProcessor(this));
        mCollidingShape.transform(Linear2DHTransformations.translationMatrix(mPosition.getCoordinates().x, mPosition.getCoordinates().y));

    }

    @Override
    public void addMe(GameWorld world) {
        mParent = world;
        world.addDrawable(this);
        world.addProcessable(this);
        world.addHotkeyable(this);
        mQuadTreeHolder = world.addPhysical(this);
    }

    @Override
    public void removeMe(GameWorld world) {
        world.removeDrawable(this);
        world.removeProcessable(this);
        world.removeHotkeyable(this);
        world.removePhysical(mQuadTreeHolder);
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
        Vector2d pos = mPosition.getCoordinates();
        Matrix3x3d ptransform = Matrix3x3d.matMatMul(
                Linear2DHTransformations.translationMatrix(pos.x, pos.y), mTransform);
        Transform transform = Matrix3x3d.toSFMLTransform(ptransform);
        RenderStates newStates = new RenderStates(states.blendMode,
                Transform.combine(states.transform, transform),
                states.texture, states.shader);
        mDrawShape.draw(target, newStates);
    }

    @Override
    public CollidingShape getCollidingShape() {
        return mCollidingShape;
    }

    @Override
    public double getMass() {
        return mMass;
    }

    @Override
    public void addForce(Vector2d force) {
        mUnprocessedForces.add(force);
    }

    @Override
    public Vector2d getVelocity() {
        return mVelocity;
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        if (homoTransformation == null) {
            return;
        }
        Vector2d p = mPosition.getCoordinates();
        mTransform = homoTransformation;
        mCollidingShape.transform(Matrix3x3d.matMatMul(Linear2DHTransformations.
                translationMatrix(p.x, p.y), homoTransformation));

    }

    @Override
    public Matrix3x3d getTransform() {
        return mTransform;
    }

    @Override
    public HotkeyProcessor asHotkeyProcessor() {
        return mHotkeyProcessor;
    }

    @Override
    public void addCollision(Collision c) {

        mCollisions.add(c);

    }

    @Override
    public Collection<? extends Collision> getCollisions() {
        return mCollisions;
    }

    @Override
    public void clearCollisions() {
        mCollisions.clear();
    }

    @Override
    public double getElasticity() {
        return mElasticity;
    }

    @Override
    public Queue<? extends Vector2d> getUnprocessedForces() {
        return mUnprocessedForces;
    }

    @Override
    public void setVelocity(Vector2d vel) {
        mVelocity = vel;
    }

    @Override
    public void onFrameFinish() {

        if (mQuadTreeHolder != null) { // checks if this has been assigned to a world yet
            mQuadTreeHolder = (SpatialHoldable<Collidable>) mQuadTreeHolder.update();
        }
    }

}
