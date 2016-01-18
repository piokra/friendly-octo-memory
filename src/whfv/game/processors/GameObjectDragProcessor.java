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
public class GameObjectDragProcessor implements GameObjectProcessor{

    private final PhysicalGameObject mParent;
    private final double mMultiplier;
    public GameObjectDragProcessor(PhysicalGameObject parent, double multiplier) {
        mParent = parent;
        mMultiplier= multiplier;
    }

    
    
    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        Vector2d dir = Vector2d.neg(mParent.getVelocity());
        double l = Vector2d.length(dir);
        mParent.addForce(Vector2d.mul(dir, l*l*mMultiplier));
        
    }
    
}
