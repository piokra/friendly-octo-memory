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

import java.util.Collection;
import whfv.game.GameObject;
import whfv.game.PhysicalGameObject;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameAggresiveMobProcessor implements GameObjectProcessor {

    private final Collection<? extends PhysicalGameObject> mTarget;
    private final PhysicalGameObject mParent;

    public GameAggresiveMobProcessor(Collection<? extends PhysicalGameObject> mTarget, PhysicalGameObject mParent) {
        this.mTarget = mTarget;
        this.mParent = mParent;
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        Vector2d parentPos = mParent.getPosition().getCoordinates();
        for (PhysicalGameObject physicalGameObject : mTarget) {
            Vector2d targetPos = physicalGameObject.getPosition().getCoordinates();
            Vector2d r = Vector2d.sub(parentPos, targetPos);
            double l = Vector2d.length(r);
            if(l<1000) {
                Vector2d rn = Vector2d.normalized(r);
                mParent.addForce(Vector2d.mul(rn, -2000/(l+100)));
                //System.out.println(rn);
            } 
        }
        //mParent.setVelocity(Vector2d.add(Vector2d.randomVector(0.1), mParent.getVelocity()));
    }
    
}
