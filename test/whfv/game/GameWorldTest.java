/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.Font;
import org.jsfml.graphics.Image;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.TextureCreationException;
import org.jsfml.system.Vector2f;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import whfv.Animation;
import whfv.collision.ConvexCollidingShape;
import whfv.position.AbsolutePosition;
import whfv.position.AbsoluteSize;
import whfv.position.RelativePosition;
import whfv.resources.AnimationType;
import whfv.resources.FontType;
import whfv.resources.ImageType;
import whfv.resources.ResourceBank;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class GameWorldTest {

    private static final Random RANDOM = new Random(System.nanoTime());
    RenderWindow r = new RenderWindow(new VideoMode(500, 500), "Yoloylo");

    final Vector2f[] points = new Vector2f[]{new Vector2f(-16, -24), new Vector2f(16, -24),
        new Vector2f(16, 24), new Vector2f(-16, 24)};

    Animation animation = initAnimation();

    Animation initAnimation() {
        try {
            return (Animation) ResourceBank.getResource(AnimationType.TYPE, "hero.ani");
        } catch (IOException ex) {
            System.out.println("FAILED");
            Logger.getLogger(GameWorldTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image;
        try {
            image = (Image) ResourceBank.getResource(ImageType.TYPE, "hero.png");
            image.createMaskFromColor(Color.WHITE);
            Texture texture = new Texture();
            texture.loadFromImage(image);
            
            Animation ani = new Animation(texture, 48, 32, 10, new ConvexShape(points));
            ani.addState("IDLE", 4);
            ani.addState("WALK_SOUTH", 0);
            ani.addState("WALK_WEST", 1);
            ani.addState("WALK_EAST", 2);
            ani.addState("WALK_NORTH", 3);
            return ani;
        } catch (IOException ex) {
            Logger.getLogger(GameWorldTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();

        } catch (TextureCreationException ex) {
            Logger.getLogger(GameWorldTest.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }

    }

    public GameWorldTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void concurrencyTest() {
        GameWorld g = new GameWorld(Rect2D.ZERO, Vector2d.VECTOR_ZERO, 5, 5);
        g.start();
        LinkedList<GameObject> ll = new LinkedList<>();
        g.addGameObject(new GameObjectImpl());
        while (r.isOpen()) {

            r.clear(Color.BLUE);
            r.draw(g);
            r.display();
            for (Event e : r.pollEvents()) {
                if (e.type == Event.Type.CLOSED) {
                    r.close();
                }
                if (e.type == Event.Type.KEY_PRESSED) {
                    if (e.asKeyEvent().key == Key.E) {
                        GameObject go = new GameObjectImpl();
                        ll.add(go);
                        g.addGameObject(go);
                    } else if (e.asKeyEvent().key == Key.R) {
                        if (!ll.isEmpty()) {
                            GameObject go = ll.removeFirst();
                            g.removeProcessable(go);
                        }
                    }
                }
            }
        }
        g.stop();

    }

    @Test
    public void heroTest() {
        GameWorld g = new GameWorld(new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(500, 500)), new Vector2d(500, 500), 0.33, 5);
        Rect2D rekt = new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(50, 50));
        GameDefaultPhysical gh = new GameHero(new AbsolutePosition(new Vector2d(250, 250)),
                100, 1, rekt.toConvexCollidinShape(), new Animation(animation, new ConvexShape(points)));
        GameDefaultPhysical block = new GameDefaultPhysical(new AbsolutePosition(new Vector2d(100, 100)),
                1000, 0.1, rekt.toConvexCollidinShape(), new RectangleShape(new Vector2f(50, 50)));
        g.start();
        g.addGameObject(gh);
        g.addGameObject(block);
        LinkedList<GameObject> ll = new LinkedList<>();
        ll.add(block);
        while (r.isOpen()) {

            r.clear(new Color(122, 122, 122));
            r.draw(g);
            g.process(0.33);
            r.display();
            for (Event e : r.pollEvents()) {
                if (e.type == Event.Type.CLOSED) {
                    r.close();
                }
                g.processEvent(e);
                if (e.type == Event.Type.KEY_PRESSED) {
                    if (e.asKeyEvent().key == Key.E) {
                        GameObject go = new GameDefaultPhysical(new AbsolutePosition(new Vector2d(RANDOM.nextDouble() * 500, RANDOM.nextDouble() * 500)),
                                10, 1, rekt.toConvexCollidinShape(), new RectangleShape(new Vector2f(50, 50)));
                        ll.add(go);
                        g.addGameObject(go);
                    } else if (e.asKeyEvent().key == Key.R) {
                        if (!ll.isEmpty()) {
                            GameObject go = ll.removeFirst();
                            g.queueRemoveGameObject(go);
                        }
                    }
                }
            }
        }
        g.stop();

    }

    @Test
    public void cameraTest() {
        GameWorld g = new GameWorld(new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(500, 500)), new Vector2d(500, 500), 0.33, 5);
        Rect2D rekt = new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(50, 50));
        Vector2d[] pointsd = new Vector2d[]{ new Vector2d(-16, -24), new Vector2d(16, -24),
            new Vector2d(16, 24), new Vector2d(-16, 24)};
        
        GameDefaultPhysical gh = new GameHero(new AbsolutePosition(new Vector2d(250, 250)),
                100, 1, new ConvexCollidingShape(pointsd), new Animation(animation, new ConvexShape(points)));
        GameDefaultPhysical block = new GameDefaultPhysical(new AbsolutePosition(new Vector2d(100, 100)),
                1000, 0.1, rekt.toConvexCollidinShape(), new RectangleShape(new Vector2f(50, 50)));
        g.start();
        g.addGameObject(gh);
        g.addGameObject(block);
        LinkedList<GameObject> ll = new LinkedList<>();
        ll.add(block);
        AbsoluteSize as = new AbsoluteSize(new Vector2d(500, 500));

        GameCamera gc = new GameCamera(new RelativePosition(Vector2d.VECTOR_ZERO, gh.getPosition()), as, r);
        g.addGameObject(gc);
        g.setGameCamera(gc);
        while (r.isOpen()) {

            r.clear(new Color(166, 122, 122));
            r.draw(g);
            g.process(0.33);
            r.display();
            for (Event e : r.pollEvents()) {
                if (e.type == Event.Type.CLOSED) {
                    r.close();
                }
                g.processEvent(e);
                if (e.type == Event.Type.KEY_PRESSED) {
                    if (e.asKeyEvent().key == Key.E) {
                        GameObject go = new GameDefaultPhysical(new AbsolutePosition(new Vector2d(RANDOM.nextDouble() * 500, RANDOM.nextDouble() * 500)),
                                10, 1, rekt.toConvexCollidinShape(), new RectangleShape(new Vector2f(50, 50)));
                        ll.add(go);
                        g.addGameObject(go);
                    } else if (e.asKeyEvent().key == Key.R) {
                        if (!ll.isEmpty()) {
                            GameObject go = ll.removeFirst();
                            g.queueRemoveGameObject(go);
                        }
                    }
                }
            }
        }
        g.stop();
    }

}
