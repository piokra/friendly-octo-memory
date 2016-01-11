/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import java.util.ArrayList;
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
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class CollisionBenchmark {
    
    public CollisionBenchmark() {
       CCSG c = new CCSG();
       Vector2d otherCorner = new Vector2d(1000,1000);
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
    private class BruteForce implements TimeMeasured {
        private final ArrayList<ConvexCollidingShape> mel;
        BruteForce(ArrayList<ConvexCollidingShape> col) {
           mel=col;
        }
        @Override
        public void task() {
            int collisions = 0;
            TreeSet<ConvexCollidingShape> differentCollisions = new TreeSet<>();
            for (ConvexCollidingShape convexCollidingShape : mel) {
                for (ConvexCollidingShape convexCollidingShape1 : mel) {
                    if(convexCollidingShape!=convexCollidingShape1) {
                        if(convexCollidingShape.collides(convexCollidingShape1)) {
                            collisions++;
                            differentCollisions.add(convexCollidingShape);
                            differentCollisions.add(convexCollidingShape1);
                        }
                    }
                }
            }
            System.out.println("Different Collisions: " + differentCollisions.size());
            System.out.println("Collsions: "+collisions);
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
    
    @Test
    public void colDisplay() {
        ArrayList<ConvexShape> shapes = new ArrayList<>();
        for (ConvexCollidingShape convexCollidingShape : test100) {
            shapes.add(convexCollidingShape.toJSFMLConvexShape());
        }
        for (ConvexShape shape : shapes) {
            shape.setFillColor(Color.BLUE);
            
        }
        RenderWindow r = new RenderWindow();
        r.create(new VideoMode(1000,1000), "test");
        System.out.println(shapes.size());
        while(r.isOpen()) {
            r.clear();
            for (ConvexShape shape : shapes) {
                r.draw(shape);
            }
            r.display();
            for(Event e : r.pollEvents()) {
                if(e.type == Event.Type.CLOSED) {
                    r.close();
                }
            }
        }
    }
    
}
