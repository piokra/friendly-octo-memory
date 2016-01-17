/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import java.util.LinkedList;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.KeyEvent;
import whfv.collision.CollidingShape;
import whfv.collision.Collision;
import whfv.collision.ConvexCollidingShape;
import whfv.hotkeys.Hotkey;
import whfv.hotkeys.HotkeyProcessor;
import whfv.hotkeys.HotkeyTask;
import whfv.hotkeys.Hotkeyable;
import whfv.position.Position;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameHero implements PhysicalGameObject, Hotkeyable {

    private Position mPosition;
    private double mMass;
    private Vector2d mVelocity;
    private GameWorld mParent;
    private ConvexCollidingShape mCollidingShape;
    private RectangleShape mDrawShape;
    private final LinkedList<GameObjectProcessor> mGameObjectProcessors = new LinkedList<>();
    private final HotkeyProcessor mHotkeyProcessor = new HotkeyProcessor();
    private Matrix3x3d mTransform = Matrix3x3d.IDENTITY;

    public GameHero(Position mPosition, double mMass, Vector2d mVelocity, ConvexCollidingShape mCollidingShape, RectangleShape mDrawShape) {
        this.mPosition = mPosition;
        this.mMass = mMass;
        this.mVelocity = mVelocity;
        this.mCollidingShape = mCollidingShape;
        this.mDrawShape = mDrawShape;
        mGameObjectProcessors.add(setUpWalker());
    }

    protected GameObjectMover setUpWalker() {
        GameObjectMover ret = new GameObjectMover(this, true, 5);
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
            mHotkeyProcessor.addHotkeyedTask(hotkeysDown.get(i), tasksDown.get(i));
            mHotkeyProcessor.addHotkeyedTask(hotkeysUp.get(i), tasksUp.get(i));
        }

        return ret;
    }

    @Override
    public void addMe(GameWorld world) {
        world.addDrawable(this);
        world.addProcessable(this);
        world.addHotkeyable(this);
    }

    @Override
    public void removeMe(GameWorld world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Position getPosition() {
        return mPosition;
    }

    @Override
    public void setPosition(Position position) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void process(double timestep) {
        for (GameObjectProcessor mGameObjectProcessor : mGameObjectProcessors) {
            mGameObjectProcessor.process(timestep);
        }
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        Vector2d pos = mPosition.getCoordinates();
        Matrix3x3d ptransform = Matrix3x3d.matMatMul(mTransform, Linear2DHTransformations.translationMatrix(pos.x, pos.y));
        Transform transform = Matrix3x3d.toSFMLTransform(ptransform);
        RenderStates newStates = new RenderStates(states.blendMode, Transform.combine(states.transform, transform), states.texture, states.shader);
        mDrawShape.draw(target, newStates);
    }

    @Override
    public CollidingShape getCollidingShape() {
        return mCollidingShape;
    }

    @Override
    public double getMass() {
        return mMass;
    }

    @Override
    public void addForce(Vector2d force) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector2d getVelocity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Vector2d getAcceleration() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        if (homoTransformation != null) {
            return;
        }
        mTransform = homoTransformation;
    }

    @Override
    public void transform(Matrix2x2d transformation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HotkeyProcessor asHotkeyProcessor() {
        return mHotkeyProcessor;
    }

}
