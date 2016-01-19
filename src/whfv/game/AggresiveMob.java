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
package whfv.game;

import org.jsfml.graphics.RectangleShape;
import whfv.collision.ConvexCollidingShape;
import whfv.game.damage.PhysicalDamage;
import whfv.game.processors.GameAggresiveMobProcessor;
import whfv.game.processors.GameObjectCollisionDamager;
import whfv.position.Position;

/**
 *
 * @author Pan Piotr
 */
public class AggresiveMob extends GameDefaultPhysical {

    private GameAggresiveMobProcessor mGAMP = null;

    public AggresiveMob(Position mPosition, double mMass, double mElasticity, ConvexCollidingShape mCollidingShape, RectangleShape mDrawShape) {
        super(mPosition, mMass, mElasticity, mCollidingShape, mDrawShape);
        getProcessors().add(new GameObjectCollisionDamager(this, new PhysicalDamage(20), new PhysicalDamage(100)));
    }

    @Override
    public void addMe(GameWorld world) {
        super.addMe(world); //To change body of generated methods, choose Tools | Templates.
        if (mGAMP != null) {
            getProcessors().remove(mGAMP);
        }
        mGAMP = new GameAggresiveMobProcessor(world.getGoodGuys(), this);
        getProcessors().add(mGAMP);

    }

}
