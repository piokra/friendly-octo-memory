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

import whfv.game.PhysicalGameObject;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameObjectForcer extends GameObjectMover {
    
    private final PhysicalGameObject mPhysical;
    public GameObjectForcer(PhysicalGameObject parent, boolean normalized, double strength) {
        super(parent, normalized, strength);
        mPhysical = parent;
    }

    @Override
    public void move(Vector2d vec) {
        mPhysical.addForce(vec);
    }
    
    
}
