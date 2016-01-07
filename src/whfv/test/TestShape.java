/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.test;

import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import whfv.Drawable;

/**
 *
 * @author Uzytkownik
 */
public class TestShape implements Drawable {
    final CircleShape mCS;
    public TestShape( TestRotatingShape trs ) {
        mCS = new CircleShape(100,5);
        mCS.setRotation(trs.mCS.getRotation());
        mCS.setPosition(trs.mCS.getPosition());
        
    }
    @Override
    public void draw(RenderTarget target, RenderStates states) {
        mCS.draw(target, states);
    }
    
}
