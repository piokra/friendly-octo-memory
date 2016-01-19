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
import whfv.game.TimedGameObject;

/**
 *
 * @author Pan Piotr
 */
public class GameTimedObjectProcessor implements GameObjectProcessor {
    private final TimedGameObject mParent;
    public GameTimedObjectProcessor(TimedGameObject parent) {
        mParent = parent;
    }
    @Override
    public GameObject getParent() {
        return mParent;
    }

    @Override
    public void process(double timestep) {
        Timer t = mParent.getTimer();
        t.passTime(timestep);
        mParent.checkTimer();
        
    }
    
    
}
