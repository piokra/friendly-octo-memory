/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import whfv.utill.Linear2DHTransformations;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;
import whfv.utill.Vector3d;

/**
 *
 * @author Pan Piotr
 */
public class ConvexCollidingShapeTest {

    private final Vector2d[] somePoints = new Vector2d[]{v(0, 0), v(1, 0), v(0, 1)};
    private final ConvexCollidingShape someShape = new ConvexCollidingShape(somePoints);
    private final Vector2d[] otherPoints = new Vector2d[]{v(0.1, 0.1), v(0.2, 0.1), v(0.2, 0.2), v(0.1, 0.2)};
    private final ConvexCollidingShape otherShape = new ConvexCollidingShape(otherPoints);

    public ConvexCollidingShapeTest() {
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

    private Vector2d v(double x, double y) {
        return new Vector2d(x, y);
    }

    /**
     * Test of checkPointsOrder method, of class ConvexCollidingShape.
     */
    @Test
    public void testCheckPointsOrder() {
        System.out.println("checkPointsOrder");
        Vector2d[] points1 = new Vector2d[]{v(0, 0), v(0, 1), v(1, 1), v(1, 0)};
        Vector2d[] points2 = new Vector2d[]{v(0, 0), v(1, 0), v(1, 1), v(0, 1)};
        ConvexCollidingShape instance = new ConvexCollidingShape(points1);
        double expResult1 = 1;
        double expResult2 = -1;
        double result1 = instance.checkPointsOrder(points1);
        double result2 = instance.checkPointsOrder(points2);
        System.out.println("Result1: " + result1);
        System.out.println("Result2: " + result2);
        assertEquals(expResult1, result1, 0.0);
        assertEquals(expResult2, result2, 0.0);

    }

    /**
     * Test of countSingleNormal method, of class ConvexCollidingShape.
     */
    @Test
    public void testCountSingleNormal() {
        System.out.println("countSingleNormal");
        Vector2d segment = v(100, 0);
        double order = 1;
        ConvexCollidingShape instance = someShape;
        Vector2d expResult = v(0, 1);
        Vector2d expResult2 = v(0, -1);
        Vector2d result2 = instance.countSingleNormal(segment, -order);
        Vector2d result = instance.countSingleNormal(segment, order);
        System.out.println(result);
        assertEquals(expResult, result);
        assertEquals(expResult2, result2);
    }

    /**
     * Test of generateNormals method, of class ConvexCollidingShape.
     */
    @Test
    public void testGenerateNormals_double() {
        System.out.println("generateNormals");

        ConvexCollidingShape instance = someShape;
        double order = instance.checkPointsOrder(somePoints);
        System.out.println("Order: " + order);
        Vector2d[] expResult = new Vector2d[]{v(0, -1), v(Math.sqrt(2) / 2, Math.sqrt(2) / 2), v(-1, 0)};
        Vector2d[] result = instance.generateNormals(order);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of checkMinMaxAlongNormal method, of class ConvexCollidingShape.
     */
    @Test
    public void testCheckMinMaxAlongNormal() {
        System.out.println("checkMinMaxAlongNormal");

        ConvexCollidingShape ccs = otherShape;
        Vector2d normal = v(1, 0);
        ConvexCollidingShape instance = someShape;
        boolean result = instance.checkMinMaxAlongNormal(ccs, normal);
        assertEquals(true, result);

    }

    /**
     * Test of checkSinglePointAlongNormal method, of class
     * ConvexCollidingShape.
     */
    @Test
    public void testCheckSinglePointAlongNormal() {
        System.out.println("checkSinglePointAlongNormal");
        Vector2d pointTrue = v(0.499, 0.499);
        Vector2d pointFalse = v(1, 1);
        Vector2d normal = v(Math.sqrt(2) / 2, Math.sqrt(2) / 2);
        Vector2d lineOrigin = v(1, 0);
        ConvexCollidingShape instance = someShape;
        boolean resultTrue = instance.checkSinglePointAlongNormal(pointTrue, normal, lineOrigin);
        boolean resultFalse = instance.checkSinglePointAlongNormal(pointFalse, normal, lineOrigin);
        assertEquals(true, resultTrue);
        assertEquals(false, resultFalse);
    }

    /**
     * Test of collides method, of class ConvexCollidingShape.
     */
    @Test
    public void testCollides_ConvexCollidingShape() {
        System.out.println("collides");
        ConvexCollidingShape ccsTrue = otherShape;
        Matrix3x3d translationMatrix = Linear2DHTransformations.translationMatrix(1, 0);
        ConvexCollidingShape ccsFalse = new ConvexCollidingShape(otherPoints, translationMatrix);
        ConvexCollidingShape instance = someShape;
        boolean trueResult = instance.collides(ccsTrue);
        boolean falseResult = instance.collides(ccsFalse);
        assertEquals(true, trueResult);
        assertEquals(false, falseResult);
    }

    /**
     * Test of collides method, of class ConvexCollidingShape.
     */
    @Test
    public void testCollides_Vector2d() {
        System.out.println("collides");
        Vector2d pointTrue = v(0.33, 0.33);
        Vector2d pointFalse = v(0.501, 0.5);
        ConvexCollidingShape instance = someShape;

        boolean resultTrue = instance.collides(pointTrue);
        boolean resultFalse = instance.collides(pointFalse);
        assertEquals(true, resultTrue);
        assertEquals(false, resultFalse);

    }

    @Test
    public void testCountMinimumDistanceNormal() {
        System.out.println("countMinimumDistanceNormal");

        ConvexCollidingShape instance = someShape;
        ConvexCollidingShape oneShape = otherShape;
        ConvexCollidingShape translatedShape1 = new ConvexCollidingShape(somePoints, Linear2DHTransformations.translationMatrix(1, 0));
        ConvexCollidingShape translatedShape2 = new ConvexCollidingShape(somePoints, Linear2DHTransformations.translationMatrix(-1, 0));

        Vector2d resultOne = instance.countMinimumDistanceNormal(oneShape);
        Vector2d resultTwo = instance.countMinimumDistanceNormal(translatedShape1);
        Vector2d resultThree = instance.countMinimumDistanceNormal(translatedShape2);
        System.out.println(resultOne);
        System.out.println(resultTwo);
        System.out.println(resultThree);
        assertEquals(v(0,-1),resultOne);
        assertEquals(v(Math.sqrt(2)/2,Math.sqrt(2)/2),resultTwo);
        assertEquals(v(-1,0),resultThree);
    }
}
