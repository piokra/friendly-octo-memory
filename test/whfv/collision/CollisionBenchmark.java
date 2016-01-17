/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import whfv.collision.qt.BestFitQuadTree;
import whfv.utill.Rect2D;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class CollisionBenchmark {

    public CollisionBenchmark() {
        CCSG c = new CCSG();
        Vector2d otherCorner = new Vector2d(1000, 1000);
        test100 = c.generateObjects(100, Vector2d.VECTOR_ZERO, otherCorner, 50);
        test500 = c.generateObjects(500, Vector2d.VECTOR_ZERO, otherCorner, 50);
        test1000 = c.generateObjects(1000, Vector2d.VECTOR_ZERO, otherCorner, 50);

    }

    private ArrayList<ConvexCollidingShape> test100;
    private ArrayList<ConvexCollidingShape> test500;
    private ArrayList<ConvexCollidingShape> test1000;

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

    private Vector2d v(double x, double y) {
        return new Vector2d(x, y);
    }

    private class BruteForce implements TimeMeasured {

        private final ArrayList<ConvexCollidingShape> mel;

        BruteForce(ArrayList<ConvexCollidingShape> col) {
            mel = col;
        }

        @Override
        public void task() {
            int collisions = 0;
            TreeSet<ConvexCollidingShape> differentCollisions = new TreeSet<>();
            for (ConvexCollidingShape convexCollidingShape : mel) {
                for (ConvexCollidingShape convexCollidingShape1 : mel) {
                    if (convexCollidingShape != convexCollidingShape1) {
                        if (convexCollidingShape.collides(convexCollidingShape1)) {
                            collisions++;

                        }
                    }
                }
            }
            //System.out.println("Different Collisions: " + differentCollisions.size());
            System.out.println("Collsions: " + collisions);
        }

    }

    @Test
    public void bruteForceTest() {
        BruteForce first = new BruteForce(test100);
        BruteForce second = new BruteForce(test500);
        BruteForce third = new BruteForce(test1000);
        System.out.println("Time100: " + first.measureTime());
        System.out.println("Time500: " + second.measureTime());
        System.out.println("Time1000: " + third.measureTime());

    }

    private class CCSP implements Collidable, Comparable<Collidable> {

        public final ConvexCollidingShape shape;

        public CCSP(ConvexCollidingShape shape) {
            this.shape = shape;
        }

        @Override
        public CollidingShape getCollidingShape() {
            return shape;
        }

        @Override
        public int compareTo(Collidable o) {
            if (o == null) {
                return -1;
            }
            return shape.compareTo((ConvexCollidingShape) (o.getCollidingShape()));

        }

    }

    private class BFQTForce implements TimeMeasured {

        private final ArrayList<ConvexCollidingShape> mel;
        private final BestFitQuadTree bfqt;

        public BFQTForce(ArrayList<ConvexCollidingShape> mel, BestFitQuadTree bfqt) {
            this.mel = mel;
            this.bfqt = bfqt;
        }

        @Override
        public void task() {
            int collisions = 0;
            for (ConvexCollidingShape convexCollidingShape : mel) {
                for (Collidable likelyCollision : bfqt.getLikelyCollisions(new CCSP((convexCollidingShape)))) {
                    if ((ConvexCollidingShape) likelyCollision.getCollidingShape() != convexCollidingShape) {
                        if (convexCollidingShape.collides((ConvexCollidingShape) likelyCollision.getCollidingShape())) {
                            collisions++;
                        }
                    }
                }
            }
            //System.out.println("Different Collisions: " + differentCollisions.size());
            System.out.println("Collsions: " + collisions);
        }

    }

    @Test
    public void bfqtTest() {
        BestFitQuadTree bfqt = new BestFitQuadTree(3, new Rect2D(Vector2d.VECTOR_ZERO, new Vector2d(1000, 1000)));
        for (ConvexCollidingShape convexCollidingShape : test1000) {
            CCSP ccsp = new CCSP(convexCollidingShape);
            bfqt.add(ccsp);

        }
        ConvexCollidingShape whole = new ConvexCollidingShape(new Vector2d[]{v(0, 0), v(1000, 0), v(1000, 1000), v(0, 1000)});
        Collection<Collidable> shapes = bfqt.getLikelyCollisions(new CCSP(whole));
        System.out.println("Shapes size: " + shapes.size());
        BFQTForce test = new BFQTForce(test1000, bfqt);
        System.out.println("bfqtTest: " + test.measureTime());
        ArrayList<ConvexCollidingShape> ccsss = new ArrayList<>();
        for (Collidable shape : shapes) {
            ccsss.add((ConvexCollidingShape) shape.getCollidingShape());
        }
        BruteForce bft = new BruteForce(ccsss);
        System.out.println("BFTQ: " + bft.measureTime());
    }

    //@Test
    public void colDisplay() {
        ArrayList<ConvexShape> shapes = new ArrayList<>();
        for (ConvexCollidingShape convexCollidingShape : test100) {
            shapes.add(convexCollidingShape.toJSFMLConvexShape());
        }
        for (ConvexShape shape : shapes) {
            shape.setFillColor(Color.BLUE);

        }
        RenderWindow r = new RenderWindow();
        r.create(new VideoMode(1000, 1000), "test");
        System.out.println(shapes.size());
        while (r.isOpen()) {
            r.clear();
            for (ConvexShape shape : shapes) {
                r.draw(shape);
            }
            r.display();
            for (Event e : r.pollEvents()) {
                if (e.type == Event.Type.CLOSED) {
                    r.close();
                }
            }
        }
    }

}
