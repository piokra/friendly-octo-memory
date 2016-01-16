/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
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
public class GameWorldTest {
    RenderWindow r = new RenderWindow(new VideoMode(500, 500), "Yoloylo");
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
        g.addGameObject(new GameObjectImpl());
        while(r.isOpen()) {

            r.clear(Color.BLUE);
            r.draw(g);
            r.display();
            for (Event e : r.pollEvents()) {
                if(e.type==Event.Type.CLOSED) {
                    r.close();
                } 
                if(e.type==Event.Type.KEY_PRESSED) {
                    if(e.asKeyEvent().key == Key.E) {
                        g.addGameObject(new GameObjectImpl());
                    }
                }
            }
        }
        g.stop();

    }
    
}
