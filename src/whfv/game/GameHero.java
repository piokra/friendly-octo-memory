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
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import whfv.Animation;
import whfv.HasAnimation;
import whfv.collision.ConvexCollidingShape;
import whfv.game.processors.GameObjectAnimationProcesser;
import whfv.game.processors.GameObjectAnimationStateProcesser;
import whfv.game.processors.GameObjectCollisionForcer;
import whfv.game.processors.GameObjectForcer;
import whfv.game.processors.GameObjectForcerWithAnimation;
import whfv.game.processors.GameObjectMover;
import whfv.hotkeys.Hotkey;
import whfv.hotkeys.HotkeyTask;
import whfv.position.Position;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;

public class GameHero extends GameDefaultPhysical implements HasAnimation, AnimatedPhysicalObject {

    private final Animation mAnimation;

    public GameHero(Position mPosition, double mMass, double mElasticity, ConvexCollidingShape mCollidingShape, Animation mAnimation) {
        super(mPosition, mMass, mElasticity, mCollidingShape, null);
        getProcessors().addFirst(setUpWalker());
        getProcessors().add(new GameObjectAnimationProcesser(this));
        //getProcessors().add(new GameObjectAnimationStateProcesser(this));
        this.mAnimation = mAnimation;
        //getProcessors().add(new GameObjectCollisionForcer(this));
    }

    private GameObjectMover setUpWalker() {
        GameObjectMover ret = new GameObjectForcerWithAnimation(this, true, 100);
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

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2d pos = getPosition().getCoordinates();
        Matrix3x3d ptransform = Matrix3x3d.matMatMul(
                Linear2DHTransformations.translationMatrix(pos.x, pos.y), getTransform());
        Transform transform = Matrix3x3d.toSFMLTransform(ptransform);
        RenderStates newStates = new RenderStates(states.blendMode,
                Transform.combine(transform,states.transform),
                states.texture, states.shader);
        
        mAnimation.draw(target, newStates);
    }

    @Override
    public Animation getAnimation() {
        return mAnimation;
    }

}
