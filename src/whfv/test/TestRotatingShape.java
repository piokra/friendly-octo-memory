/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.test;

import org.jsfml.graphics.CircleShape;
import org.jsfml.system.Vector2f;
import whfv.DrawClonable;
import whfv.DrawClonable;
import whfv.Drawable;
import whfv.Drawable;
import whfv.Processable;
import whfv.Processable;

/**
 *
 * @author Uzytkownik
 */
public class TestRotatingShape implements DrawClonable, Processable {

    final CircleShape mCS = new CircleShape(100,5);
    @Override
    public Drawable cloneDrawable() {
        return new TestShape(this);
    }

    @Override
    public void process(double timestep) {
        mCS.rotate((float) (10*timestep));
        mCS.setPosition(Vector2f.add(mCS.getPosition(), new Vector2f((float)timestep*10,(float)timestep*10)));
    }
    
}
