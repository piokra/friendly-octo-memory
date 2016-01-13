/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import whfv.UI.LinearLayout;
import whfv.position.AbsolutePosition;
import static whfv.utill.Linear2DHTransformations.*;
import whfv.utill.Matrix3x3d;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class DrawingTest {

    RenderWindow r = new RenderWindow(VideoMode.getFullscreenModes()[0],"yoloylo", RenderWindow.FULLSCREEN);

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

    @Test
    public void drawLinearLayout() {
        Rect2D rect = new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(100, 100));
        BlackRectangle br = new BlackRectangle(new AbsolutePosition(new Vector2d(10, 10)));
        LinearLayout llv = new LinearLayout(new Vector2d(0, 1));
        for (int j = 0; j < 10; j++) {
            
            LinearLayout ll = new LinearLayout(new Vector2d(1, 0));
            llv.addView(ll);
            //ll.setPosition(new AbsolutePosition(Vector2d.VECTOR_ZERO));
            for (int i = 0; i < 5; i++) {
                BlackRectangle brr;
                ll.addView(brr = new BlackRectangle(new AbsolutePosition(Vector2d.VECTOR_ZERO)));
                System.out.println(brr.getPosition().getCoordinates());
            }
            
            System.out.println(ll.getBoundingRectangle());
        }
        double theta = 0;
        
        
        
        while (r.isOpen()) {

            r.clear();
            llv.transform(combine(scalingMatrix(1+abs(sin(theta)), 1+abs(cos(theta))),combine(horizontalSheerMatrix(2), rotationMatrix(theta))));
            theta+=0.0001;
            r.draw(llv);
            r.display();
            for (Event e : r.pollEvents()) {
                br.processEvent(e);
                if (e.type == Type.KEY_RELEASED) {
                    r.close();
                }
            }
        }
    }
}
