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

import whfv.game.Direction;
import whfv.game.GameObject;
import whfv.utill.Vector2d;

public class GameObjectMover implements GameObjectProcessor {

    private final GameObject mParent;
    protected final boolean mNormalized;
    protected final double mStrength;
    private double mNorth = 0;
    private double mSouth = 0;
    private double mWest = 0;
    private double mEast = 0;

    public GameObjectMover(GameObject parent, boolean normalized, double strength) {
        mParent = parent;
        mNormalized = normalized;
        mStrength = strength;
    }

    @Override
    public void process(double timestep) {
        Vector2d move = new Vector2d((mEast - mWest), (mSouth - mNorth));
        if (mNormalized) {
            move = Vector2d.normalized(move);
        }
        move = Vector2d.mul(move, mStrength*timestep);
        move(move);
    }
    public void move(Vector2d vec) {
        mParent.getPosition().move(vec);
    }
    @Override
    public GameObject getParent() {
        return mParent;
    }

    public void walk(Direction dir) {
        switch (dir) {
            case NORTH:
                walkNorth();
                return;
            case SOUTH:
                walkSouth();
                return;
            case WEST:
                walkWest();
                return;
            case EAST:
                walkEast();
                return;
        }
    }

    public void stop(Direction dir) {
        switch (dir) {
            case NORTH:
                stopNorth();
                return;
            case SOUTH:
                stopSouth();
                return;
            case WEST:
                stopWest();
                return;
            case EAST:
                stopEast();
                return;
        }
    }

    protected void walkNorth() {
        mNorth = 1;
    }

    protected void stopNorth() {
        mNorth = 0;
    }

    protected void walkWest() {
        mWest = 1;
    }

    protected void stopWest() {
        mWest = 0;
    }

    protected void walkSouth() {
        mSouth = 1;
        
    }

    protected void stopSouth() {
        mSouth = 0;
    }

    protected void walkEast() {
        mEast = 1;
    }

    protected void stopEast() {
        mEast = 0;
    }
}
