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
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import org.jsfml.window.event.MouseButtonEvent;
import whfv.Animation;
import whfv.EventProcessor;
import whfv.HasAnimation;
import whfv.collision.ConvexCollidingShape;
import whfv.game.damage.Health;
import whfv.game.processors.GameGunProcessor;
import whfv.game.processors.GameObjectAnimationProcesser;
import whfv.game.processors.GameObjectAnimationStateProcesser;
import whfv.game.processors.GameObjectCollisionForcer;
import whfv.game.processors.GameObjectForcer;
import whfv.game.processors.GameObjectForcerWithAnimation;
import whfv.game.processors.GameObjectForcerWithAnimationAndShooting;
import whfv.game.processors.GameObjectMover;
import whfv.hotkeys.Hotkey;
import whfv.hotkeys.HotkeyTask;
import whfv.position.Position;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;

public class GameHero extends GameDefaultPhysical implements HasAnimation, AnimatedPhysicalObject, EventProcessor {

    private final Animation mAnimation;
    private final GameGunProcessor mGunProcessor;

    public GameHero(Position mPosition, double mMass, double mElasticity, ConvexCollidingShape mCollidingShape, Animation mAnimation) {
        super(mPosition, mMass, mElasticity, mCollidingShape, null);
        getProcessors().addFirst(setUpWalker());
        getProcessors().add(new GameObjectAnimationProcesser(this));
        mGunProcessor = new GameGunProcessor(10, this);
        getProcessors().add(mGunProcessor);
        //getProcessors().add(new GameObjectAnimationStateProcesser(this));
        this.mAnimation = mAnimation;
        //getProcessors().add(new GameObjectCollisionForcer(this));
    }

    @Override
    public void addMe(GameWorld world) {
        super.addMe(world); //To change body of generated methods, choose Tools | Templates.
        world.addGoodGuy(this);
        world.addEventProcessor(this);
    }

    @Override
    public void removeMe(GameWorld world) {
        super.removeMe(world); //To change body of generated methods, choose Tools | Templates.
        world.removeGoodGuy(this);
        world.removeEventProcessor(this);
    }

    private GameObjectMover setUpWalker() {
        GameObjectForcerWithAnimationAndShooting ret = new GameObjectForcerWithAnimationAndShooting(this, true, 500);
        LinkedList<HotkeyTask> tasksDown = new LinkedList<>();
        for (Direction value : Direction.values()) {
            tasksDown.add((HotkeyTask) (KeyEvent key) -> {
                ret.walk(value);
            });
        }
        HotkeyTask shootTask = (HotkeyTask) (KeyEvent key) -> {
            ret.shoot();
        };
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
        asHotkeyProcessor().addHotkeyedTask(new Hotkey(Keyboard.Key.SPACE, false), shootTask);
        return ret;
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2d pos = getPosition().getCoordinates();
        Matrix3x3d ptransform = Matrix3x3d.matMatMul(
                Linear2DHTransformations.translationMatrix(pos.x, pos.y), getTransform());
        Transform transform = Matrix3x3d.toSFMLTransform(ptransform);
        RenderStates newStates = new RenderStates(states.blendMode,
                Transform.combine(transform, states.transform),
                states.texture, states.shader);
        mAnimation.draw(target, newStates);
    }

    @Override
    public Animation getAnimation() {
        return mAnimation;
    }

    @Override
    public void die() {
        getParent().queueRemoveGameObject(this);
    }

    @Override
    public Event processEvent(Event e) {
        if (e.type == Event.Type.MOUSE_BUTTON_PRESSED) {
            Vector2i vec = e.asMouseButtonEvent().position;
            Vector2d pos = getPosition().getCoordinates();
            Vector2d rel = new Vector2d(vec.x - pos.x, vec.y - pos.y);
            mGunProcessor.shoot(Vector2d.normalized(rel));
            return null;
        }
        return e;
    }

}
