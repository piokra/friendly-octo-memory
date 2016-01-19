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

import whfv.Timer;
import whfv.game.GameObject;
import whfv.game.Projectile;
import whfv.game.damage.MagicalDamage;
import whfv.position.RelativePosition;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameGunProcessor implements GameObjectProcessor {

    private Timer mTimer;
    private final double mCooldown;
    private final GameObject mParent;
    private boolean mReady = true;

    public GameGunProcessor(double cooldown, GameObject parent) {
        mTimer = new Timer(cooldown);
        mParent = parent;
        mCooldown = cooldown;
    }

    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        mTimer.passTime(timestep);
        if(mTimer.getTimeRemaining()<0) {
            mReady = true;
        }
    }

    public void shoot(Vector2d dir) {
        if(!mReady) {
            return;
        }
        Projectile projectile = new Projectile(new RelativePosition(Vector2d.mul(dir, 64),
                getParent().getPosition()), Vector2d.mul(dir, 10),
                1000, new MagicalDamage(100));
        getParent().getParent().addGameObject(projectile);
        mReady = false;
        mTimer = new Timer(mCooldown);
    }

}
