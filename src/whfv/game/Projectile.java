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

import com.sun.xml.internal.ws.api.model.MEP;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2f;
import whfv.Animation;
import whfv.Timer;
import whfv.collision.CollidingShape;
import whfv.collision.Collision;
import whfv.collision.ConvexCollidingShape;
import whfv.game.damage.Damage;
import whfv.game.damage.DefaultDrawableHealthProcessor;
import whfv.game.damage.Health;
import whfv.game.processors.GameObjectCollisionCollector;
import whfv.game.processors.GameObjectCollisionDamager;
import whfv.game.processors.GameObjectVelocityProcessor;
import whfv.game.processors.GameTimedObjectProcessor;
import whfv.position.Position;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class Projectile extends DefaultGameObject implements AnimatedGameObject,TimedGameObject, PhysicalGameObject {

    private final ConvexCollidingShape mCollidingShape;
    private Vector2d mVelocity;
    private final Timer mTimer;
    private final Damage mDamage;
    private final ConvexShape mShape;
    private final DefaultDrawableHealthProcessor mHealth = new DefaultDrawableHealthProcessor(this, null, 100, 100);
    private final LinkedList<Collision> mCollisions = new LinkedList<>();
    public Projectile(Position pos, Vector2d velocity, double time, Damage damage) {
        super(pos);
        mVelocity = velocity;
        mDamage = damage;
        mTimer = new Timer(time);
        Rect2D rect = new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(16,16));
        mCollidingShape = rect.toConvexCollidinShape();
        mShape = mCollidingShape.toJSFMLConvexShape();
        getProcessors().add(new GameObjectVelocityProcessor(this));
        getProcessors().add(new GameTimedObjectProcessor(this));
        getProcessors().add(new GameObjectCollisionCollector(this));
        getProcessors().add(mHealth);
        getProcessors().add(new GameObjectCollisionDamager(this, mDamage, mDamage));
        
    }
    
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2d v = getPosition().getCoordinates();
        mShape.setPosition((float) v.x, (float) v.y);
        mShape.draw(target, states);
    }

    @Override
    public CollidingShape getCollidingShape() {
        return mCollidingShape;
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
    public double getMass() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getElasticity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addForce(Vector2d force) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Queue<? extends Vector2d> getUnprocessedForces() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector2d getVelocity() {
        return mVelocity;
    }

    @Override
    public void setVelocity(Vector2d vel) {
        mVelocity = vel;
    }

    @Override
    public Timer getTimer() {
        return mTimer;
    }

    @Override
    public void expire() {
        die();
    }

    @Override
    public Animation getAnimation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Health getHealth() {
        return mHealth;
    }

    @Override
    public void die() {
        getParent().queueRemoveGameObject(this);
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        super.transform(homoTransformation); //To change body of generated methods, choose Tools | Templates.
        Vector2d p = getPosition().getCoordinates();
        mCollidingShape.transform(Matrix3x3d.matMatMul(Linear2DHTransformations.
                translationMatrix(p.x, p.y), homoTransformation));
    }
    
}
