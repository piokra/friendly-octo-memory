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
package whfv.game.processors;

import whfv.collision.Collision;
import whfv.collision.CollisionWithNormals;
import whfv.collision.Collisions;
import whfv.game.GameObject;
import whfv.game.PhysicalGameObject;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameObjectCollisionForcer implements GameObjectProcessor {

    private final PhysicalGameObject mParent;

    public GameObjectCollisionForcer(PhysicalGameObject mParent) {
        this.mParent = mParent;
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        for (Collision collision : mParent.getCollisions()) {
            double l = Vector2d.length(mParent.getVelocity());
            CollisionWithNormals cwn = Collisions.getCollisionWithNormals(collision);
            PhysicalGameObject second = (PhysicalGameObject) cwn.second;
            if (Vector2d.dot(mParent.getVelocity(), cwn.firstNormal) < 0.01) {
                mParent.addForce(Vector2d.mul(cwn.firstNormal, second.getElasticity() * (l + 0.85)/timestep*mParent.getMass()));
                //second.addForce(Vector2d.mul(cwn.secondNormal, second.getElasticity() * (l + 1)/timestep*mParent.getMass()));
            }

        }
    }

}
