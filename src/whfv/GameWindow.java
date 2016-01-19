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
package whfv;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.KeyEvent;
import whfv.UI.View;
import whfv.collision.ConvexCollidingShape;
import whfv.game.GameCamera;
import whfv.game.GameHero;
import whfv.game.GameMobSpawner;
import whfv.game.GameResettingHero;
import whfv.game.GameWorld;
import whfv.game.damage.Damage;
import whfv.game.visual.DamageDealt;
import whfv.hotkeys.Hotkey;
import whfv.hotkeys.HotkeyProcessor;
import whfv.hotkeys.HotkeyTask;
import whfv.position.AbsolutePosition;
import whfv.position.AbsoluteSize;
import whfv.position.RelativePosition;
import whfv.resources.AnimationType;
import whfv.resources.FontType;
import whfv.resources.ResourceBank;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;
import whfv.utill.Vector3d;

/**
 *
 * @author Pan Piotr
 */
public class GameWindow implements Runnable {

    private final RenderWindow mWindow;
    private GameWorld mGameWorld;
    private final HotkeyProcessor mHotkeyProcessor = new HotkeyProcessor();
    private View mInterface;
    private boolean mBreak = false;
    private Font mFont;
    private Animation mAnimation;

    public GameWindow() throws IOException {
        mWindow = new RenderWindow(VideoMode.getDesktopMode(), "WHFV", RenderWindow.FULLSCREEN);
        mHotkeyProcessor.addHotkeyedTask(new Hotkey(Keyboard.Key.F4, true,
                true, false, false), new CloseWindow());
        mHotkeyProcessor.addHotkeyedTask(new Hotkey(Keyboard.Key.ESCAPE, true),
                new CloseWindow());
        mAnimation = (Animation) ResourceBank.getResource(AnimationType.TYPE, "hero.ani");
        mFont = (Font) ResourceBank.getResource(FontType.TYPE, "SEGOEUI.TTF");
        mGameWorld = generateWorld();
    }

    public void resetWorld() {
        breakWindow();
    }

    public GameWorld generateWorld() {
        Vector2d screenSize = new Vector2d(mWindow.getSize().x, mWindow.getSize().y);
        GameWorld gameWorld = new GameWorld(new Rect2D(new Vector2d(-10e6, -10e6),
                new Vector2d(10e6, 10e6)), screenSize, 0.33, 25);

        Vector2d[] points = new Vector2d[]{new Vector2d(-16, -24), new Vector2d(16, -24),
            new Vector2d(16, 24), new Vector2d(-16, 24)};
        Animation ani;
        ConvexCollidingShape ccs = new ConvexCollidingShape(points);

        GameHero hero = new GameResettingHero(new AbsolutePosition(screenSize),
                100, 1, ccs, new Animation(mAnimation, ccs.toJSFMLConvexShape()), this);
        gameWorld.addGameObject(hero);
        GameCamera gc = new GameCamera(new RelativePosition(Vector2d.VECTOR_ZERO, hero.getPosition()), new AbsoluteSize(screenSize), mWindow);
        gameWorld.setGameCamera(gc);
        gameWorld.addGameObject(new DamageDealt(new AbsolutePosition(screenSize), new Damage(100), mFont));
        gameWorld.addGameObject(gc);
        Vector3d distance = new Vector3d(500, 500, 1);
        for (int i = 0; i < 10; i++) {

            double theta = Math.random() * 6.28;
            Matrix3x3d rotation = Linear2DHTransformations.rotationMatrix(theta);
            Vector3d newPoints = Matrix3x3d.matVecMul(rotation, distance);
            gameWorld.addGameObject(new GameMobSpawner(new RelativePosition(new Vector2d(newPoints.x, newPoints.y), hero.getPosition())));
        }
        return gameWorld;
    }

    @Override
    public void run() {
        mGameWorld.start();
        while (mWindow.isOpen() && (!mBreak)) {
            mWindow.clear(new Color(66 + (int) (Math.random() * 50 - 25), 128
                    + (int) (Math.random() * 50 - 25), 230 + (int) (Math.random() * 90 - 70)));
            mWindow.draw(mGameWorld);
            mWindow.display();
            mGameWorld.process(0);

            for (Event pollEvent : mWindow.pollEvents()) {
                mHotkeyProcessor.processEvent(pollEvent);
                mGameWorld.processEvent(pollEvent);
            }

        }

        mGameWorld.stop();
        System.out.println("Done.");
        if (!mWindow.isOpen()) {
            return;
        }
        mGameWorld = generateWorld();
        run();

    }

    public void breakWindow() {
        mBreak = !mBreak;

    }

    private class CloseWindow implements HotkeyTask {

        @Override
        public void doTask(KeyEvent key) {
            mWindow.close();
        }
    }

    private class BreakWindow implements HotkeyTask {

        @Override
        public void doTask(KeyEvent key) {
            breakWindow();
        }

    }
}
