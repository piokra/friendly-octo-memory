/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.Transform;
import org.jsfml.window.event.Event;
import whfv.collision.CCSG;
import whfv.collision.ConvexCollidingShape;
import static whfv.utill.Linear2DHTransformations.*;
import whfv.utill.Matrix3x3d;
import static whfv.utill.Matrix3x3d.*;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class TransformingShape implements Processable, Drawable, MouseHoverable {

    private ConvexCollidingShape ccs;
    private final ConvexShape cs;
    double theta = 0;
    private final Vector2d[] points;
    Matrix3x3d transformation = Matrix3x3d.IDENTITY;

    public TransformingShape(ConvexCollidingShape ccs) {
        points = ccs.getPoints();
        this.ccs=ccs;
        cs = ccs.toJSFMLConvexShape();
    }
    
    
    @Override
    public void process(double timestep) {
        theta += timestep/10;
        transformation = Matrix3x3d.matMatMul(translationMatrix(theta*10, theta*10),matMatMul(horizontalSheerMatrix(2),rotationMatrix(theta)));
        ccs = new ConvexCollidingShape(points, transformation);
    }

    @Override
    public void draw(RenderTarget target, RenderStates states) {
        RenderStates statesn= new RenderStates(Matrix3x3d.toSFMLTransform(transformation));
        cs.draw(target, statesn);
    }
    boolean on = false;
    @Override
    public boolean isMouseOver() {
        return on;
    }

    @Override
    public void onMouseOver() {
        on=true;
        cs.setFillColor(Color.BLUE);
    }

    @Override
    public void onMouseOff() {
        on=false;
        cs.setFillColor(Color.MAGENTA);
    }

    @Override
    public ConvexCollidingShape getMouseCollidingShape() {
        return ccs;
    }

    @Override
    public Event processEvent(Event e) {
        if(e.type == Event.Type.MOUSE_MOVED) {
            checkMouseMovement(e.asMouseEvent());
        }
        return e;
    }
    
}
