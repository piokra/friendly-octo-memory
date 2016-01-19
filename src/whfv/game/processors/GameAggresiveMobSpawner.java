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

import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;
import whfv.Timer;
import whfv.collision.ConvexCollidingShape;
import whfv.game.AggresiveMob;
import whfv.game.GameObject;
import whfv.position.AbsolutePosition;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameAggresiveMobSpawner implements GameObjectProcessor {

    private static final Vector2d[] POINTS = new Vector2d[]{new Vector2d(-25, -25),
        new Vector2d(25, -25), new Vector2d(25, 25), new Vector2d(-25, 25)};

    private double mCurrentMaxCooldown;
    private final double mCooldownDropPerSpawn;
    private Timer mTimer;
    private GameObject mParent;

    public GameAggresiveMobSpawner(GameObject parent, double maxCooldown, double cooldownDropPerSpawn) {
        mParent = parent;
        mCurrentMaxCooldown = maxCooldown;
        mCooldownDropPerSpawn = cooldownDropPerSpawn;
        mTimer = new Timer(mCurrentMaxCooldown);
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        mTimer.passTime(timestep);
        if (mTimer.getTimeRemaining() < 0) {
            mCurrentMaxCooldown -= mCooldownDropPerSpawn;
            mTimer = new Timer(timestep + Math.random() * 100);
            Vector2d coords = mParent.getPosition().getCoordinates();
            ConvexCollidingShape ccs = new ConvexCollidingShape(POINTS);
            RectangleShape rs = new RectangleShape(new Vector2f(50, 50));
            rs.setPosition(new Vector2f(-25, -25));
            mParent.getParent().addGameObject(new AggresiveMob(new AbsolutePosition(coords),
                    1, 1, ccs, rs));
        }
    }

}
