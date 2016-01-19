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

import whfv.game.AnimatedPhysicalObject;
import whfv.game.Projectile;
import whfv.game.damage.MagicalDamage;
import whfv.position.RelativePosition;
import whfv.utill.Vector2d;

public class GameObjectForcerWithAnimationAndShooting extends GameObjectForcerWithAnimation {

    private Vector2d mDirection = new Vector2d(0, 1);

    public GameObjectForcerWithAnimationAndShooting(AnimatedPhysicalObject parent, boolean normalized, double strength) {
        super(parent, normalized, strength);

    }

    @Override
    public void move(Vector2d vector) {
        super.move(vector); //To change body of generated methods, choose Tools | Templates.
        double l = Vector2d.length(vector);
        if (l > 0) {

            mDirection = Vector2d.normalized(vector);
        } else {
            mDirection = new Vector2d(0,1);
        }
    }

    public void shoot() {
        Projectile projectile = new Projectile(new RelativePosition(Vector2d.mul(mDirection, 64),
                getParent().getPosition()), Vector2d.mul(mDirection, 10),
                1000, new MagicalDamage(100));
        getParent().getParent().addGameObject(projectile);
    }

}
