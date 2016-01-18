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
public class GameObjectForceProcessor implements GameObjectProcessor {
    private final PhysicalGameObject mParent;

    public GameObjectForceProcessor(PhysicalGameObject parent) {
        this.mParent = parent;
    }

    
    
    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        double x=0,y=0;
        double mass = (mParent.getMass());
        double invmass = 0;
        if(mass>10e-6) {
            invmass = 1/mass;
        }
        while(!mParent.getUnprocessedForces().isEmpty()) {
            Vector2d f = mParent.getUnprocessedForces().poll();
            if(f!=null) {
                onForceProcess(f);
                x+=f.x*timestep*invmass;
                y+=f.y*timestep*invmass;
            }
        }
        mParent.setVelocity(Vector2d.add(mParent.getVelocity(), new Vector2d(x,y)));
    }
    
    protected void onForceProcess(Vector2d force) {
        
    }
}
