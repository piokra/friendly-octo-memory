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

import whfv.game.AnimatedGameObject;
import whfv.game.AnimatedPhysicalObject;
import whfv.game.PhysicalGameObject;
import whfv.utill.Pair;
import whfv.utill.Vector2d;

public class GameObjectForcerWithAnimation extends GameObjectForcer {

    protected static final Pair[] STATES_WITH_DIRECTIONS = new Pair[]{new Pair("WALK_NORTH", new Vector2d(0, -1)),
        new Pair("WALK_SOUTH", new Vector2d(0, 1)),
        new Pair("WALK_EAST", new Vector2d(1, 0)),
        new Pair("WALK_WEST", new Vector2d(-1, 0))};
    private final AnimatedGameObject mParent;
    public GameObjectForcerWithAnimation(AnimatedPhysicalObject parent, boolean normalized, double strength) {
        super(parent, normalized, strength);
        mParent = parent;
    }

    @Override
    public void move(Vector2d vector) {
        super.move(vector);
        double l = Vector2d.length(vector);

        double max = Double.NEGATIVE_INFINITY;
        String state = "IDLE";
        if (l > 0.01) {
            for (Pair pair : STATES_WITH_DIRECTIONS) {
                Vector2d vec = (Vector2d) pair.second;
                double dot = Vector2d.dot(vec, vector);
                if (dot > max) {
                    max = dot;
                    state = (String) pair.first;
                }
            }
        }
        mParent.getAnimation().setState(state);
    }

}
