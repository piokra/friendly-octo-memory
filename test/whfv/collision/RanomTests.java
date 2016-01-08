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
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector3d;

/**
 *
 * @author Pan Piotr
 */
public class RanomTests {
    
    public RanomTests() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void randomTest() {
        Matrix3x3d instnce = new Matrix3x3d(Vector3d.VECTOR_ZERO,Vector3d.VECTOR_ZERO,Vector3d.VECTOR_ZERO);
        ConvexCollidingShape c = new ConvexCollidingShape(null);
    }
}
