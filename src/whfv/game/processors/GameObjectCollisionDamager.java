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
import whfv.game.GameObject;
import whfv.game.PhysicalGameObject;
import whfv.game.damage.Damage;

/**
 *
 * @author Pan Piotr
 */
public class GameObjectCollisionDamager implements GameObjectProcessor {
    private final PhysicalGameObject mParent;
    private Damage mToSelf;
    private Damage mToOther;

    public GameObjectCollisionDamager(PhysicalGameObject mParent, Damage mToSelf, Damage mToOther) {
        this.mParent = mParent;
        this.mToSelf = mToSelf;
        this.mToOther = mToOther;
    }
    
    
    
    protected void setDamageToSelf(Damage damage) {
        mToSelf = damage;
    }
    
    protected void setDamageToOther(Damage damage) {
        mToOther = damage;
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        for (Collision collision : mParent.getCollisions()) {
            PhysicalGameObject pgo = (PhysicalGameObject) collision.second; // only pgo found in here
            pgo.getHealth().dealDamage(mToOther);
            mParent.getHealth().dealDamage(mToSelf);
        }
    }
    
}
