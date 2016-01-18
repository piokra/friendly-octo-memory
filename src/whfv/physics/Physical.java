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
package whfv.physics;

import java.util.Collection;
import java.util.Queue;
import whfv.collision.Collidable;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public interface Physical extends Collidable {
    
    double getMass();
    double getElasticity();
    void addForce(Vector2d force);
    Queue<? extends Vector2d> getUnprocessedForces();
    Vector2d getVelocity();
    void setVelocity(Vector2d vel);
    
}
