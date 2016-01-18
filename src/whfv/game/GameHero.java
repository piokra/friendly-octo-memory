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

import java.util.LinkedList;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import whfv.collision.ConvexCollidingShape;
import whfv.game.processors.GameObjectCollisionForcer;
import whfv.game.processors.GameObjectForcer;
import whfv.game.processors.GameObjectMover;
import whfv.hotkeys.Hotkey;
import whfv.hotkeys.HotkeyTask;
import whfv.position.Position;


public class GameHero extends GameDefaultPhysical {

    public GameHero(Position mPosition, double mMass, double mElasticity, ConvexCollidingShape mCollidingShape, RectangleShape mDrawShape) {
        super(mPosition, mMass, mElasticity, mCollidingShape, mDrawShape);
        getProcessors().addFirst(setUpWalker());
        //getProcessors().add(new GameObjectCollisionForcer(this));
    }
    
    
    private GameObjectMover setUpWalker() {
        GameObjectMover ret = new GameObjectForcer(this, true, 100);
        LinkedList<HotkeyTask> tasksDown = new LinkedList<>();
        for (Direction value : Direction.values()) {
            tasksDown.add((HotkeyTask) (KeyEvent key) -> {
                ret.walk(value);
            });
        }
        LinkedList<HotkeyTask> tasksUp = new LinkedList<>();
        for (Direction value : Direction.values()) {
            tasksUp.add((HotkeyTask) (KeyEvent key) -> {
                ret.stop(value);
            });
        }
        LinkedList<Keyboard.Key> keys = new LinkedList<>();
        keys.add(Keyboard.Key.W);
        keys.add(Keyboard.Key.S);
        keys.add(Keyboard.Key.D);
        keys.add(Keyboard.Key.A);
        LinkedList<Hotkey> hotkeysDown = new LinkedList<>();
        for (Keyboard.Key key : keys) {
            hotkeysDown.add(new Hotkey(key, true));
        }
        LinkedList<Hotkey> hotkeysUp = new LinkedList<>();
        for (Keyboard.Key key : keys) {
            hotkeysUp.add(new Hotkey(key, false));
        }
        for (int i = 0; i < 4; i++) {
            asHotkeyProcessor().addHotkeyedTask(hotkeysDown.get(i), tasksDown.get(i));
            asHotkeyProcessor().addHotkeyedTask(hotkeysUp.get(i), tasksUp.get(i));
        }

        return ret;
    }

    
}
