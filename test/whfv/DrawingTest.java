/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class DrawingTest {

    RenderWindow r = new RenderWindow(new VideoMode(500, 500), "TestingStuff");

    public DrawingTest() {
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
        r.close();
    }

    @Test
    public void drawRectangle() {
        r.clear();
        Rect2D rect = new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(100, 100));
        r.draw(rect.toJSFMLShape());
        r.display();
        while (r.isOpen()) {
            for (Event e : r.pollEvents()) {
                if (e.type == Type.KEY_RELEASED) {
                    r.close();
                }
            }
        }
    }

    @Test
    public void drawTransformin() {
        Rect2D rect = new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(100, 100));
        TransformingShape ts = new TransformingShape(rect.toConvexCollidinShape());

        while (r.isOpen()) {
            ts.process(0.01);
            r.clear();
            r.draw(ts);
            r.display();
            for (Event e : r.pollEvents()) {
                ts.processEvent(e);
                if (e.type == Type.KEY_RELEASED) {
                    r.close();
                }
            }
        }
    }
}
