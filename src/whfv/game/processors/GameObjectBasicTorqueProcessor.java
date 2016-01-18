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

import whfv.game.GameObject;
import whfv.game.PhysicalGameObject;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameObjectBasicTorqueProcessor implements GameObjectProcessor {
    private final PhysicalGameObject mParent;
    private final double mMultiplier;

    public GameObjectBasicTorqueProcessor(PhysicalGameObject parent, double qMi) {
        mParent = parent;
        mMultiplier=qMi;
    }
    
    
    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        Vector2d vel = mParent.getVelocity();
        double l = Vector2d.length(vel);
        vel = Vector2d.neg(Vector2d.normalized(vel));
        if(l<1) {
            mParent.addForce(Vector2d.mul(vel, mParent.getMass()*0.1*mMultiplier));
        } else {
            mParent.addForce(Vector2d.mul(vel, mParent.getMass()*mMultiplier));
        }
    }
    
}
