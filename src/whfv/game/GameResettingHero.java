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

import whfv.Animation;
import whfv.GameWindow;
import whfv.collision.ConvexCollidingShape;
import whfv.position.Position;

/**
 *
 * @author Pan Piotr
 */
public class GameResettingHero extends GameHero {
    private final GameWindow mWindow;
    public GameResettingHero(Position mPosition, double mMass, double mElasticity, ConvexCollidingShape mCollidingShape, Animation mAnimation, GameWindow mWindow) {
        super(mPosition, mMass, mElasticity, mCollidingShape, mAnimation);
        this.mWindow = mWindow;
    }

    @Override
    public void die() {
        super.die(); 
        mWindow.breakWindow();
    }
    
    
    
}
